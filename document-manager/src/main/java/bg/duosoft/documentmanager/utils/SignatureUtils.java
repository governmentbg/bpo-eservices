package bg.duosoft.documentmanager.utils;

import org.apache.log4j.Logger;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.Constants;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.security.x509.X500Name;

import javax.security.auth.x500.X500Principal;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignatureUtils {
	private static Logger log = Logger.getLogger(SignatureUtils.class);

    public static String OID_EMAIL = "1.2.840.113549.1.9.1";
	public static final int DIRECTORY_NAME = 4;
	public static final String REG_EXP_ALT_EGN = ".*(OID\\.2\\.5\\.4\\.3\\.100\\.1\\.1=)(\\d{10})\\D.*";
	public static final String REG_EXP_EGN = ".*(EGN:|EGNT:|PID:|UID=EGN)(\\d{10})\\D.*";
	public static final String REG_EXP_BULSTAT = ".*(?:BULSTAT:|B:|2\\.5\\.4\\.10\\.100\\.1\\.1=|OU=EIK)(\\d{9}(\\d{4})?)\\D.*";

    static {
        Provider bcProv = new BouncyCastleProvider();
        bcProv.put("Alg.Alias.Signature.SHA1with1.2.840.113549.1.1.5","SHA1WithRSAEncryption");
        bcProv.put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1withRSA", "SHA256WithRSAEncryption");
        Security.addProvider(bcProv);
        org.apache.xml.security.Init.init();
    }

    public static X509Certificate verifyXMLSignature(Document doc, String baseURI) throws Exception {
		X509Certificate cert = null;
		boolean valid = false;
		
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new DSContext("ds", Constants.SignatureSpecNS));        
        XPathExpression xexpr = xpath.compile("//ds:Signature[1]");
        Element sigElement = (Element)xexpr.evaluate(doc, XPathConstants.NODE);
		
		if (sigElement == null) 
			throw new XMLSignatureException("Signature selement is not found!");
		XMLSignature signature = new XMLSignature(sigElement, baseURI);
		KeyInfo ki = signature.getKeyInfo();
		if (ki == null)
			throw new XMLSignatureException("Did not find a key info");
			
		cert = signature.getKeyInfo().getX509Certificate();
		if (cert == null) {
			throw new XMLSignatureException("Unknown signature!");
		}
		return cert;
    }
    
	public static X509Certificate verifyXMLSignature(InputStream is, String baseURI) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			return verifyXMLSignature(db.parse(is), baseURI);
		} finally {
			try {is.close();} catch(IOException ignore) {}
		}

	}
	
	public static String getAltSubject(X509Certificate cert) throws CertificateParsingException {
		String subj = null;
		Collection<List<?>> cl = cert.getSubjectAlternativeNames();
		if (cl != null) {
			for (List<?> lst : cl) {
				Integer id = (Integer)lst.get(0);
				if (id != null && id.intValue() == DIRECTORY_NAME) {
					X500Principal princ = new X500Principal((String)lst.get(1));
					subj = princ.getName(X500Principal.RFC1779);
				}
			}
		}
		return subj;
	}

	public static String getEgn(X509Certificate cert) throws CertificateParsingException {
		String egn = null;
		Pattern p = null;
		String subject = getAltSubject(cert);
		if (subject != null) {
			p = Pattern.compile(REG_EXP_ALT_EGN);
		} else {
			subject = cert.getSubjectDN().getName();			
			p = Pattern.compile(REG_EXP_EGN);
		}
		Matcher m = p.matcher(subject);
		if (m.matches()) {
			egn = m.group(2);
		} else {
			log.debug("egn cannot be extracted");
		}
		return egn;
	}

	
	public static String getBulstat(X509Certificate cert) {
		String bulstat = null;
		String subject = cert.getSubjectDN().getName();
		Pattern p = Pattern.compile(REG_EXP_BULSTAT, Pattern.DOTALL);
		int group = 1;
		Matcher m = p.matcher(subject);
		if (m.matches()) {
			bulstat = m.group(group);
		} else {
			log.debug("bulstat cannot be extracted");
		}
		return bulstat;
	}

	public static String getIssuerName(X509Certificate cert) throws IOException {
        String issuerDN = cert.getIssuerX500Principal().getName();
        return new X500Name(issuerDN).getCommonName();
    }

    public static String getEmail(X509Certificate cert) throws IOException {
        String email = null;
        email = getOIDValue(cert, OID_EMAIL);
        return email;
    }

    public static String getCommonName(X509Certificate cert) throws IOException {
        String subject = cert.getSubjectX500Principal().getName();
        return new X500Name(subject).getCommonName();
    }

	public static String getOIDValue(X509Certificate cert, String oid) throws IOException {
		X500Principal subj = cert.getSubjectX500Principal();
		byte[] bytes = subj.getEncoded();
		ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(bytes));
		ASN1Sequence sequence = (ASN1Sequence)aIn.readObject();

		for (int i = 0; i < sequence.size(); i++) {
			ASN1Set set = (ASN1Set) sequence.getObjectAt(i);
			for (int j = 0; j < set.size(); j++) {
				ASN1Sequence seq = (ASN1Sequence)set.getObjectAt(j);
				String id = seq.getObjectAt(0).toString();
				if (id.equals(oid)) {
					return((ASN1String) seq.getObjectAt(1)).getString();
				}
			}
		}
		return null;
	}

}

class DSContext implements NamespaceContext {
	private String prefix;
	private String namespaceURI;
	private Map<String, Set> prefixesByURI = new HashMap<String, Set>();
	
	public DSContext(String prefix, String namespaceURI) {
		this.prefix = prefix;
		this.namespaceURI = namespaceURI;
		
		Set<String> set = new HashSet<String>();
		set.add(prefix);
		prefixesByURI.put(namespaceURI, set);
	}

	public String getNamespaceURI(String prefix) {
		return (prefix != null && prefix.equals(this.prefix)) ? namespaceURI : null; 
	}

	public String getPrefix(String namespaceURI) {
		return (namespaceURI != null && namespaceURI.equals(this.namespaceURI)) ? prefix : null;
	}

	public Iterator getPrefixes(String namespaceURI) {
	    if (namespaceURI == null)
	        throw new IllegalArgumentException("namespaceURI cannot be null");
	      if (prefixesByURI.containsKey(namespaceURI)) {
	        return ((Set) prefixesByURI.get(namespaceURI)).iterator();
	      } else {
	        return Collections.EMPTY_SET.iterator();
	      }
	}
		
}
