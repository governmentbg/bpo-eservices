/*
 * ApplicationServiceBean:: TMApplicationServiceBean 01/10/13 17:01 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.application;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.application.dao.ApplicationDAO;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.TransactionUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class DefaultApplicationServiceBean extends ApplicationServiceBean implements ApplicationServiceRemote,
        ApplicationServiceLocal {

    @EJB(lookup = "java:global/documentLocal")
    private DocumentService documentService;

    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService systemConfigurationService;

    @Inject
    private ApplicationDAO applicationDAO;

    @Override
    public DocumentService getDocumentService() {
        return documentService;
    }

    @Override
    public IPApplication generateReceipts(String office, String module, IPApplication application) {
        return null;
    }

    @Override
    public IPApplication generateReceipts(String office, String module, IPApplication application, PrefixPdfKind prefixPdfKind) {
        return null;
    }

    @Override
    protected ApplicationDAO getApplicationDAO() {
        return applicationDAO;
    }

    @Override
    protected RulesService getBusinessRulesService() {
        return null;
    }

    @Override
    protected UserSearchService getUserService() {
        return null;
    }

    @Override
    protected ConfigurationService getSystemConfigurationService() {
        return systemConfigurationService;
    }

    @Override
    protected ApplicationClient getApplicationAdapter() {
        return null;
    }

    @Override
    protected TransactionUtil getTransactionUtil() {
        return null;
    }

    @Override
    protected ApplicantService getApplicantService() {
        return null;
    }

    @Override
    protected RepresentativeService getRepresentativeService() {
        return null;
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication) {
        return null;
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication, boolean saveUnsavedDocuments) {
        return null;
    }

    @Override
    protected ApplicationStatus getFinalStatus(String applicationType) {
        return null;
    }
}
