/*
 * ResourceService:: ReportService 23/10/13 12:53 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.resources;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.resource.ResourceService;
import eu.ohim.sp.core.resource.ResourceServiceLocal;
import eu.ohim.sp.core.resource.ResourceServiceRemote;
import eu.ohim.sp.external.resource.ResourceClientOutside;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;


/**
 * ResourceService implementation
 * @author soriama
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class ResourceServiceBean implements ResourceServiceRemote, ResourceServiceLocal {

    @Inject @ResourceClientOutside
    ResourceService resourceClient;

    @Override
    public String getMessage(String messageKey) {
        return resourceClient.getMessage(messageKey);
    }
}
