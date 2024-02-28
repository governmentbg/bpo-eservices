/*
 *  SignatureService:: SignatureService 15/10/13 16:15 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.esignature;

import java.io.Serializable;
import java.util.Map;

import eu.ohim.sp.core.domain.resources.Document;

/**
 * Core management service for managing the eSignature
 * 
 * @author masjose
 * 
 */
public interface ESignatureService {

    /** Parameter name for External eSignature Service Url **/
    String URL_EXTERNAL_SERVICE_PARAM = "urlExternalService";

    /** Parameter name for callback Url after document signature **/
    String CALLBACK_URL_PARAM = "callbackUrl";

    /** Parameter name for BO Username who is invoking the service **/
    String USER_PARAM = "user";

    /** Parameter name for BO Username who is invoking the service **/
    String USERLANGUAGE_PARAM = "userLanguage";

    /** Parameter name for Document List to sign **/
    String DOCUMENT_PARAM = "document";

    /**
     * Prepare the request from the eSignature external service. The final http request must be executed by the
     * client using these parameters
     * 
     * @param listDocuments a list of documents that will be encoded
     * @param username username
     * @param urlCallback url callback
     * @return Map with the parameters: URL_EXTERNAL_SERVICE_PARAM, URL_CALLBACK_PARAM, USERNAME_PARAM,
     *         LIST_DOCUMENTS_PARAM
     */
    Map<String, Serializable> prepareSignatureRequest(Document[] listDocuments, String username, String urlCallback);

}