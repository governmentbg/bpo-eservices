package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.common.*;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeMarkInjector extends AbstractConsumerService implements Serializable{

	private static final long serialVersionUID = -2078092999276280610L;
	private final Boolean MINIMAL = true;

	/** urls **/
	private static final String TMVIEW_TRADEMARK_SEARCH = System.getProperty("url.tmview.trademark.search");
	private static final String TMVIEW_TRADEMARK_DATA = System.getProperty("url.tmview.trademark.data");
	private static final String PRECLEARANCE_REPORT = System.getProperty("url.tmview.preclearance.report");

	/** xpath lookup util **/
	private XPathFactory xpathFactory = XPathFactory.newInstance();
	private XPath xpath = xpathFactory.newXPath();

	/** smooks transform file **/
	private final static String TRANSFORM_FILE_TRADEMARK = "META-INF/smooks-TrademarkSearchAdapter-getTrademark.xml";
	private final static String TRANSFORM_FILE_TRADEMARK_MIN = "META-INF/smooks-TrademarkSearchAdapter-getTrademarkMinimal.xml";
	private final static String TRANSFORM_FILE_PRECLEARANCE = "META-INF/smooks-TrademarkSearchAdapter-getPreclearanceReport.xml";

	/** tranformer **/
	private final static String TRANSFORMER_TRADEMARK = "trademark";
	private final static String TRANSFORMER_TRADEMARK_MIN = "trademark_min";
	private final static String TRANSFORMER_PRECLEARANCE = "preclearance";

	/** url selectors **/
	private final static String URL_TRADEMARK_SEARCH = "tm_search";
	private final static String URL_TRADEMARK_AUTOCOMPLETE = "tm_autocomplete";
	private final static String URL_TRADEMARK_DATA = "tm_data";
	private final static String URL_PRECLEARANCE_REPORT = "tm_preclearance";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(TradeMarkInjector.class);

	private String _getUrl(final String selector, final Object... arguments) {
		StringBuilder url = new StringBuilder();
		try {
			switch (selector) {
				case URL_TRADEMARK_SEARCH:
					url.append(this.TMVIEW_TRADEMARK_SEARCH)
							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&an=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_TRADEMARK_AUTOCOMPLETE:
					url.append(this.TMVIEW_TRADEMARK_SEARCH)
							.append("/autocomplete?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&word=").append(URLEncoder.encode((String)arguments[1], "UTF-8"))
							.append("&rows=").append(arguments[2]);
					break;
				case URL_TRADEMARK_DATA:
					url.append(this.TMVIEW_TRADEMARK_DATA)
							.append("/").append(URLEncoder.encode((String)arguments[0], "UTF-8"));
					break;
				case URL_PRECLEARANCE_REPORT:
					url.append(this.PRECLEARANCE_REPORT)
							.append("?trademarkName=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&niceClasses=").append(URLEncoder.encode((String)arguments[1], "UTF-8"))
							.append("&format=").append(URLEncoder.encode((String)arguments[2], "UTF-8"));
					break;
			}
		} catch (UnsupportedEncodingException e) {
			throw new SPException(e);
		}
		return url.toString();
	}

	private DocumentBuilder _getDocumentBuilder() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setValidating(false);
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SPException(e);
		}
		return builder;
	}

	private String _getST13(final String office, final String tradeMarkId) {
		String url = _getUrl(URL_TRADEMARK_SEARCH, office, tradeMarkId);
		String xml = ((StringConsumerResponse)new RestXmlConsumerRequest(url).doRequest()).getResponse();
		String msg;
		try {
			org.w3c.dom.Document xmlDocument = _getDocumentBuilder().parse(new InputSource(new StringReader(xml)));
			msg = xpath.compile("//*/TradeMarkURI/text()").evaluate(xmlDocument);
		} catch (XPathExpressionException e) {
			throw new SPException(e);
		} catch (IOException e) {
			throw new SPException(e);
		} catch (SAXException e) {
			throw new SPException(e);
		}
		String[] parts = msg.split("/");
		return parts[parts.length-1];
	}

	/**
	 * Get the concrete transformer for the trademark transformation with smooks
	 * @return Trademark transformer
	 */
	private ConsumerResponseTransformer<TradeMark, String> getTrademarkTransformer(Boolean minimal) {
		String TRANSFORMER = TRANSFORMER_TRADEMARK;
		String TRANSFORMER_FILE = TRANSFORM_FILE_TRADEMARK;
		if(minimal) {
			TRANSFORMER = TRANSFORMER_TRADEMARK_MIN;
			TRANSFORMER_FILE = TRANSFORM_FILE_TRADEMARK_MIN;
		}
		if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
		final ConsumerResponseTransformer<TradeMark, String> transformer =
				new XMLConsumerResponseTransformer<TradeMark>(
						TradeMark.class,
						TRANSFORMER_FILE
				);
		addTransformer(TRANSFORMER, transformer);
		return transformer;
	}

	/**
	 * Get the concrete transformer for the preclearance report transformation with smooks
	 * @return List of Trademarks
	 */
	private ConsumerResponseTransformer<List<TradeMark>, String> getPreclearanceTransformer() {
		if (hasTransformer(TRANSFORMER_PRECLEARANCE)) return getTransformer(TRANSFORMER_PRECLEARANCE);
		final ConsumerResponseTransformer<List<TradeMark>, String> transformer =
				new XMLConsumerResponseTransformer<List<TradeMark>>(
						(Class<List<TradeMark>>)(Class<?>)List.class,
						TRANSFORM_FILE_PRECLEARANCE
				);
		addTransformer(TRANSFORMER_PRECLEARANCE, transformer);
		return transformer;
	}

	/**
	 * Get Trademark orchestration process
	 * @param office Office
	 * @param tradeMarkId Trademark id
	 * @return External trademark
	 */
	public TradeMark getTrademark(final String office, final String tradeMarkId) {
		LOGGER.info(">>> Getting st13 trademark code <<<");
		final String st13 = _getST13(office, tradeMarkId);
		LOGGER.info(">>> Done: " + st13 + " <<<");
		final String url = _getUrl(URL_TRADEMARK_DATA, StringUtils.isEmpty(st13)?tradeMarkId:st13);
		LOGGER.info(">>> Consuming REST service: " + url +" <<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		doCheckResponseForNotFoundMark(response.getResponse());
		LOGGER.info(">>> Done, we've got TradeMark, Transforming it <<<");
		final ConsumerResponseTransformer<TradeMark, String> transformer = getTrademarkTransformer(!MINIMAL);
		return doTransform(transformer, response);
	}

	private void doCheckResponseForNotFoundMark(String responseValue){
		if(responseValue != null ){
			Pattern notFoundPattern = Pattern.compile(".*<MarkVerbalElementText.*>NOT FOUND</MarkVerbalElementText>.*" +
					"<GoodsServicesDescription.*>not specified</GoodsServicesDescription>.*");
			Matcher macter = notFoundPattern.matcher(responseValue);
			if(macter.matches()){
				throw new RuntimeException("NOT FOUND mark returned. This mark can not be imported in application because it is not a valid mark.");
			}
		}
	}

	/**
	 * Get Trademark MINI orchestration process
	 * @param office Office
	 * @param tradeMarkId Trademark id
	 * @return External trademark
	 */
	public TradeMark getTrademarkMinimal(final String office, final String tradeMarkId) {
		final String url = _getUrl(URL_TRADEMARK_SEARCH, office, tradeMarkId);
		LOGGER.info(">>> Consuming REST service: " + url +" <<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got TradeMark, Transforming it <<<");
		final ConsumerResponseTransformer<TradeMark, String> transformer = getTrademarkTransformer(MINIMAL);
		return doTransform(transformer, response);
	}

	/**
	 * Get an array of coincident trademarks, just consume and response string without transform
	 * @param office office
	 * @param text text to search
	 * @param numberOfResults number of results
	 * @return json array with the coincidences
	 */
	public String getTrademarkAutocomplete(String office, String text, int numberOfResults) {
		String url = _getUrl(URL_TRADEMARK_AUTOCOMPLETE, office, text, numberOfResults);
		return ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
	}

	/**
	 * Get the preclearance report
	 * @param trademarkName tm name
	 * @param niceClasses nice class to inspect
	 * @param format format
	 * @return List of trademarks affected by trademark
	 */
	public List<TradeMark> getPreclearanceReport(final String trademarkName, final String niceClasses, final String format) {
		final String url = _getUrl(URL_PRECLEARANCE_REPORT, trademarkName, niceClasses, format);
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got Preclearance Report, Transforming it <<<");
		final ConsumerResponseTransformer<List<TradeMark>, String> transformer = getPreclearanceTransformer();
		return doTransform(transformer, response);
	}

}
