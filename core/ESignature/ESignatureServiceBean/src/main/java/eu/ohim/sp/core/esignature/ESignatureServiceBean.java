/*
 *  SignatureService:: SignatureService 08/10/13 12:24 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.esignature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import eu.ohim.sp.common.encryption.utils.EncryptionUtils;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.resources.Document;

/**
 * eSignature Service implementation
 * 
 * @author masjose
 * 
 */
@Stateless
@Interceptors({ExceptionHandlingInterceptor.class})
public class ESignatureServiceBean implements ESignatureServiceLocal, ESignatureServiceRemote {

    private static final String CONFIGURATION_BO_COMPONENT = "eu.ohim.sp.tmbo";
    private static final String CONFIGURATION_ESIGNATURE_URL_PARAM = "esignature.external.service.url";

	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    @Override
    public Map<String, Serializable> prepareSignatureRequest(Document[] listDocuments, String username,
            String urlCallback) {

        Map<String, Serializable> externalServiceInvocationParameters = new HashMap<String, Serializable>();
        String urlExternalService = configurationService.getValue(CONFIGURATION_ESIGNATURE_URL_PARAM,
                CONFIGURATION_BO_COMPONENT);
        externalServiceInvocationParameters.put(URL_EXTERNAL_SERVICE_PARAM, urlExternalService);
        externalServiceInvocationParameters.put(CALLBACK_URL_PARAM, urlCallback);
        externalServiceInvocationParameters.put(USER_PARAM, username);
        // The user language is not needed for BackOffice. If it's necessary for other fsp modules, create a
        // Configuration parameter like the url of external service
        externalServiceInvocationParameters.put(USERLANGUAGE_PARAM, null);

        // Process Documents
        if (listDocuments != null) {
            for (Document doc : listDocuments) {
                byte[] docData = doc.getData();
                byte[] hashData = EncryptionUtils.getSha1Hash(docData);
                doc.setData(hashData);
            }
            externalServiceInvocationParameters.put(DOCUMENT_PARAM, listDocuments);
        }

        return externalServiceInvocationParameters;
    }
}