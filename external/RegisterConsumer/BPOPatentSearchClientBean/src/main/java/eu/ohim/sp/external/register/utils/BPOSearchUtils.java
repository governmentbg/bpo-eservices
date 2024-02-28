package eu.ohim.sp.external.register.utils;

import javax.xml.ws.BindingProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 14.04.2022
 * Time: 12:53
 */
public class BPOSearchUtils {

    public static final String CONNECTION_TIMEOUT_SETTING = "javax.xml.ws.client.connectionTimeout";
    public static final String RECEIVE_TIMEOUT_SETTING = "javax.xml.ws.client.receiveTimeout";
    public static final String CONNECTION_TIMEOUT_VALUE = "6000";
    public static final String RECEIVE_TIMEOUT_VALUE = "12000";
    public static final String RECEIVE_TIMEOUT_AUTOCOMPLETE_VALUE = "7000";

    public static void setSearchTimeout(Object webServiceRef){
        ((BindingProvider) webServiceRef).getRequestContext().put(BPOSearchUtils.CONNECTION_TIMEOUT_SETTING, BPOSearchUtils.CONNECTION_TIMEOUT_VALUE);
        ((BindingProvider) webServiceRef).getRequestContext().put(BPOSearchUtils.RECEIVE_TIMEOUT_SETTING, BPOSearchUtils.RECEIVE_TIMEOUT_VALUE);
    }

    public static void setAutocompleteTimeout(Object webServiceRef){
        ((BindingProvider) webServiceRef).getRequestContext().put(BPOSearchUtils.CONNECTION_TIMEOUT_SETTING, BPOSearchUtils.CONNECTION_TIMEOUT_VALUE);
        ((BindingProvider) webServiceRef).getRequestContext().put(BPOSearchUtils.RECEIVE_TIMEOUT_SETTING, BPOSearchUtils.RECEIVE_TIMEOUT_AUTOCOMPLETE_VALUE);
    }
}
