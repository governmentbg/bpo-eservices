package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.common.*;
import eu.ohim.sp.external.domain.design.Design;
import eu.ohim.sp.external.domain.design.DesignApplication;
import org.apache.log4j.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DesignInjector extends AbstractConsumerService implements Serializable{

	private static final long serialVersionUID = -2078092999276280610L;

	/** urls **/
	private static final String DSVIEW_DESIGN_DATA = System.getProperty("url.dsview.design");
	private static final String DSVIEW_DESIGN_APPLICATION_DATA = System.getProperty("url.dsview.designapplication");

	/** smooks transform file **/
	private final static String TRANSFORM_FILE_DESIGN = "META-INF/smooks-DesignSearchAdapter-getDesign.xml";
	private final static String TRANSFORM_FILE_DESIGN_APPLICATION = "META-INF/smooks-DesignSearchAdapter-getDesignApplication.xml";

	/** tranformer **/
	private final static String TRANSFORMER_DESIGN = "design";
	private final static String TRANSFORMER_DESIGN_APPLICATION = "design_application";

	/** url selectors **/
	private final static String URL_DESIGN_DATA = "ds_data";
	private final static String URL_DESIGN_APPLICATION_DATA = "ds_app_data";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(DesignInjector.class);

	private String _getUrl(final String selector, final Object... arguments) {
		StringBuilder url = new StringBuilder();
		try {
			switch (selector) {
				case URL_DESIGN_DATA:
					url.append(this.DSVIEW_DESIGN_DATA)
							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&did=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_DESIGN_APPLICATION_DATA:
					url.append(this.DSVIEW_DESIGN_APPLICATION_DATA)
							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"));
					if(arguments[1] != null) {
						url.append("&did=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					}
					if(arguments[2] != null) {
						url.append("&an=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					}
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

	/**
	 * Get the concrete transformer for the Design transformation with smooks
	 * @return Design transformer
	 */
	private ConsumerResponseTransformer<Design, String> getDesignTransformer() {
		String TRANSFORMER = TRANSFORMER_DESIGN;
		String TRANSFORMER_FILE = TRANSFORM_FILE_DESIGN;

		if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
		final ConsumerResponseTransformer<Design, String> transformer =
				new XMLConsumerResponseTransformer<Design>(
						Design.class,
						TRANSFORMER_FILE
				);
		addTransformer(TRANSFORMER, transformer);
		return transformer;
	}

	/**
	 * Get the concrete transformer for the DesignApplication transformation with smooks
	 * @return DesignApplication transformer
	 */
	private ConsumerResponseTransformer<DesignApplication, String> getDesignApplicationTransformer() {
		String TRANSFORMER = TRANSFORMER_DESIGN_APPLICATION;
		String TRANSFORMER_FILE = TRANSFORM_FILE_DESIGN_APPLICATION;

		if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
		final ConsumerResponseTransformer<DesignApplication, String> transformer =
				new XMLConsumerResponseTransformer<DesignApplication>(
						DesignApplication.class,
						TRANSFORMER_FILE
				);
		addTransformer(TRANSFORMER, transformer);
		return transformer;
	}

	/**
	 * Get Design orchestration process
	 * @param office Office
	 * @param designId Design id
	 * @return External design
	 */
	public Design getDesign(final String office, final String designId) {
		final String url = _getUrl(URL_DESIGN_DATA, office, designId);
		LOGGER.info(">>> Consuming REST service: " + url +" <<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got Design, Transforming it <<<");
		final ConsumerResponseTransformer<Design, String> transformer = getDesignTransformer();
		return doTransform(transformer, response);
	}

	/**
	 * Get DesignApplication orchestration process
	 * @param office Office
	 * @param designId Design id
	 * @return External design application
	 */
	public DesignApplication getDesignApplication(final String office, final String designId, final String applicationId) {
		final String url = _getUrl(URL_DESIGN_APPLICATION_DATA, office, designId, applicationId);
		LOGGER.info(">>> Consuming REST service: " + url +" <<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got DesignApplication, Transforming it <<<");
		final ConsumerResponseTransformer<DesignApplication, String> transformer = getDesignApplicationTransformer();
		return doTransform(transformer, response);
	}
}
