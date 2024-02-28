package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.common.*;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.domain.patent.Patent;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import eu.ohim.sp.external.domain.userdoc.FilteredUserdocs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class BackOfficeObjectInjector extends AbstractConsumerService implements Serializable {

    /** urls **/
    private static final String BO_APPLICATIONS_SEARCH = System.getProperty("url.bo.applications.search");
    private static final String BO_TRADEMARK_GET = System.getProperty("url.bo.trademark.get");
    private static final String BO_DESIGNAPP_GET = System.getProperty("url.bo.designApplication.get");
    private static final String BO_PATENT_GET = System.getProperty("url.bo.patent.get");
    private static final String BO_USERDOCS_GET = System.getProperty("url.bo.userdocs.get");
    private static final String BO_BASIC_AUTH_USER = System.getProperty("bo.basic.auth.username");
    private static final String BO_BASIC_AUTH_PASS = System.getProperty("bo.basic.auth.password");


    /** smooks transform file **/
    private final static String TRANSFORM_FILE_BO_TRADEMARK = "META-INF/smooks-BackOfficeSearchAdapter-getTrademark.xml";
    private final static String TRANSFORM_FILE_BO_DESIGNAPP = "META-INF/smooks-BackOfficeSearchAdapter-getDesignApplication.xml";
    private final static String TRANSFORM_FILE_BO_PATENT = "META-INF/smooks-BackOfficeSearchAdapter-getPatent.xml";
    private final static String TRANSFORM_FILE_BO_USERDOC = "META-INF/smooks-BackOfficeSearchAdapter-getUserdocs.xml";


    /** tranformer **/
    private final static String TRANSFORMER_BO_TRADEMARK = "bo_trademark";
    private final static String TRANSFORMER_BO_DESIGNAPP = "bo_designapp";
    private final static String TRANSFORMER_BO_PATENT = "bo_patent";
    private final static String TRANSFORMER_BO_USERDOC = "bo_userdocs";


    /** url selectors **/
    private final static String URL_BO_APPLICATIONS_SEARCH = "bo_apps_search";
    private final static String URL_BO_TRADEMARK_GET = "bo_tm_get";
    private final static String URL_BO_DESIGNAPP_GET = "bo_ds_get";
    private final static String URL_BO_PATENT_GET = "bo_pt_get";
    private final static String URL_BO_USEREDOCS_GET = "bo_useerdocs_get";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BackOfficeObjectInjector.class);


    private String _getUrl(final String selector, final Object... arguments) {
        StringBuilder url = new StringBuilder();
        try {
            switch (selector) {
                case URL_BO_APPLICATIONS_SEARCH:
                    url.append(this.BO_APPLICATIONS_SEARCH)
                            .append("?user=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
                            .append("&appType=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
                    if(arguments.length >2 && arguments[2] != null){
                        url.append("&text=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
                    }
                    break;
                case URL_BO_TRADEMARK_GET:
                    url.append(this.BO_TRADEMARK_GET)
                            .append("?user=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
                            .append("&markId=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
                    break;
                case URL_BO_DESIGNAPP_GET:
                    url.append(this.BO_DESIGNAPP_GET)
                            .append("?user=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
                            .append("&designId=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
                    break;
                case URL_BO_PATENT_GET:
                    url.append(this.BO_PATENT_GET)
                            .append("?user=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
                            .append("&patentId=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
                    break;
                case URL_BO_USEREDOCS_GET:
                    url.append(this.BO_USERDOCS_GET)
                            .append("?foObjectId=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
                            .append("&foObjectFilingYear=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
                            .append("&user=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
                    if(arguments[3] instanceof Map){
                        Map<String, Object> theMap = (Map<String, Object>) arguments[3];
                        theMap.keySet().stream().forEach(key -> {
                            url.append("&").append(key).append("=").append(theMap.get(key).toString());
                        });
                    }

                    break;
            }
        } catch (UnsupportedEncodingException e) {
            throw new SPException(e);
        }
        return url.toString();
    }


    private ConsumerResponseTransformer<TradeMark, String> getBOTrademarkTransformer() {
        String TRANSFORMER = TRANSFORMER_BO_TRADEMARK;
        String TRANSFORMER_FILE = TRANSFORM_FILE_BO_TRADEMARK;
        if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
        final ConsumerResponseTransformer<TradeMark, String> transformer =
                new XMLConsumerResponseTransformer<TradeMark>(
                        TradeMark.class,
                        TRANSFORMER_FILE
                );
        addTransformer(TRANSFORMER, transformer);
        return transformer;
    }

    private ConsumerResponseTransformer<DesignApplication, String> getBODesignAppTransformer() {
        String TRANSFORMER = TRANSFORMER_BO_DESIGNAPP;
        String TRANSFORMER_FILE = TRANSFORM_FILE_BO_DESIGNAPP;
        if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
        final ConsumerResponseTransformer<DesignApplication, String> transformer =
                new XMLConsumerResponseTransformer<DesignApplication>(
                        DesignApplication.class,
                        TRANSFORMER_FILE
                );
        addTransformer(TRANSFORMER, transformer);
        return transformer;
    }

    private ConsumerResponseTransformer<Patent, String> getBOPatentTransformer() {
        String TRANSFORMER = TRANSFORMER_BO_PATENT;
        String TRANSFORMER_FILE = TRANSFORM_FILE_BO_PATENT;
        if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
        final ConsumerResponseTransformer<Patent, String> transformer =
                new XMLConsumerResponseTransformer<Patent>(
                        Patent.class,
                        TRANSFORMER_FILE
                );
        addTransformer(TRANSFORMER, transformer);
        return transformer;
    }

    private ConsumerResponseTransformer<FilteredUserdocs, String> getBOFilteredUserdocsTransformer() {
        String TRANSFORMER = TRANSFORMER_BO_USERDOC;
        String TRANSFORMER_FILE = TRANSFORM_FILE_BO_USERDOC;
        if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
        final ConsumerResponseTransformer<FilteredUserdocs, String> transformer =
                new XMLConsumerResponseTransformer<FilteredUserdocs>(
                        FilteredUserdocs.class,
                        TRANSFORMER_FILE
                );
        addTransformer(TRANSFORMER, transformer);
        return transformer;
    }

    public String getUnpublishedApplicationsAutocomplete(String user, String applicationType, String text) {
        String url = _getUrl(URL_BO_APPLICATIONS_SEARCH, user, applicationType, text);
        return ((StringConsumerResponse)new RestJsonConsumerRequest(url, BO_BASIC_AUTH_USER, BO_BASIC_AUTH_PASS).doRequest()).getResponse();
    }

    public TradeMark getUnpublishedTrademark(final String user, final String tradeMarkId) {
        final String url = _getUrl(URL_BO_TRADEMARK_GET, user, tradeMarkId);
        LOGGER.info(">>> Consuming REST service: " + url +" <<<");
        final ConsumerRequest request = new RestXmlConsumerRequest(url, BO_BASIC_AUTH_USER, BO_BASIC_AUTH_PASS);
        final ConsumerResponse<String> response = doRequest(request);
        LOGGER.info(">>> Done, we've got TradeMark, Transforming it <<<");
        final ConsumerResponseTransformer<TradeMark, String> transformer = getBOTrademarkTransformer();
        return doTransform(transformer, response);
    }

    public DesignApplication getUnpublishedDesignApplication(final String user, final String designId) {
        final String url = _getUrl(URL_BO_DESIGNAPP_GET, user, designId);
        LOGGER.info(">>> Consuming REST service: " + url +" <<<");
        final ConsumerRequest request = new RestXmlConsumerRequest(url, BO_BASIC_AUTH_USER, BO_BASIC_AUTH_PASS);
        final ConsumerResponse<String> response = doRequest(request);
        LOGGER.info(">>> Done, we've got TradeMark, Transforming it <<<");
        final ConsumerResponseTransformer<DesignApplication, String> transformer = getBODesignAppTransformer();
        return doTransform(transformer, response);
    }

    public Patent getUnpublishedPatent(final String user, final String patentId) {
        final String url = _getUrl(URL_BO_PATENT_GET, user, patentId);
        LOGGER.info(">>> Consuming REST service: " + url +" <<<");
        final ConsumerRequest request = new RestXmlConsumerRequest(url, BO_BASIC_AUTH_USER, BO_BASIC_AUTH_PASS);
        final ConsumerResponse<String> response = doRequest(request);
        LOGGER.info(">>> Done, we've got TradeMark, Transforming it <<<");
        final ConsumerResponseTransformer<Patent, String> transformer = getBOPatentTransformer();
        return doTransform(transformer, response);
    }


    public FilteredUserdocs getUserdocs(final String applicationNumber, final String applicationYear, final String user, Map<String, Object> eserviceDetails){
        final String url = _getUrl(URL_BO_USEREDOCS_GET, applicationNumber, applicationYear, user, eserviceDetails);
        LOGGER.info(">>> Consuming REST service: " + url +" <<<");
        final ConsumerRequest request = new RestXmlConsumerRequest(url, BO_BASIC_AUTH_USER, BO_BASIC_AUTH_PASS);
        final ConsumerResponse<String> response = doRequest(request);
        LOGGER.info(">>> Done, we've got Userdocs, Transforming <<<");
        final ConsumerResponseTransformer<FilteredUserdocs, String> transformer = getBOFilteredUserdocsTransformer();
        return doTransform(transformer, response);
    }

}
