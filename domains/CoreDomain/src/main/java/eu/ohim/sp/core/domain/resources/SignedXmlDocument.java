package eu.ohim.sp.core.domain.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by georgi.georgiev on 20.10.2014.
 */
public class SignedXmlDocument implements Serializable {
    private Document document;
    private String issuer;
    private String name;
    private String email;
    private String civilId;
    private String unifiedIdCode;
    private Date validFrom;
    private Date validTo;

    public SignedXmlDocument(Document document, String issuer, String name, String email, String civilId, String unifiedIdCode, Date validFrom, Date validTo) {
        this.document = document;
        this.issuer = issuer;
        this.name = name;
        this.email = email;
        this.civilId = civilId;
        this.unifiedIdCode = unifiedIdCode;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Document getDocument() {
        return document;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCivilId() {
        return civilId;
    }

    public String getUnifiedIdCode() {
        return unifiedIdCode;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }
}
