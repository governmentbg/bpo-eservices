/*
 *  RegisterService:: DesignService 18/11/13 18:59 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;


import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 13/08/13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public interface DesignSearchService {

    String getDesignAutocomplete(String office, String text, Integer numberOfResults);

    Design getDesign(String office, String designId);

    DesignApplication getDesignApplication(String office, String designId, String applicationId, Boolean extraImport);

    ErrorList validateDesign(String module, Design design, RulesInformation rulesInformation);

    ErrorList validateDesignApplication(String module, DesignApplication designApplication, RulesInformation rulesInformation);

    ErrorList validateDesignView(String module, DesignView designView, RulesInformation rulesInformation);

    ErrorList validateDivisionalApplication(String module, DesignDivisionalApplicationDetails dsDivisionalApplication, RulesInformation rules);

    ErrorList validateApplicationCA(String module,  ContactDetails contactDetails, RulesInformation rulesInformation);

}