/*
 *  RegisterService:: IPApplicationService 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.claim.ConversionPriority;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.IPPriority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 03/09/13
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public interface IPApplicationService {

    /**
     * Validates the priority according to some rules
     * @param module the module to get the sections
     * @param priority the seniority object that we are interested
     * @param rulesInformation extra rules information
     * @return the list of possible errors
     */
    ErrorList validatePriorityClaim(String module, IPPriority priority, RulesInformation rulesInformation);

    /**
     * Validates the seniority according to some rules
     * @param module the module to get the sections
     * @param seniority the seniority object that we are interested
     * @param rulesInformation extra rules information
     * @return the list of possible errors
     */
    ErrorList validateSeniorityClaim(String module, Seniority seniority, RulesInformation rulesInformation);

    /**
     * Update the registration number field if necessary
     * @param module the module to get the registration number
     * @param seniority the seniority object that we are interested
     * @return the new registration number
     */
    String validateRegistrationNumber(String module, Seniority seniority);

    /**
     * Validates the exhibition priority according to some rules
     * @param module the module to get the sections
     * @param exhibitionPriority the exhibition priority object that we are interested
     * @param rulesInformation extra rules information
     * @return the list of possible errors
     */
    ErrorList validateExhibitionPriorityClaim(String module, ExhibitionPriority exhibitionPriority, RulesInformation rulesInformation);

    /**
     * Validates the transformation according to some rules
     * @param module the module to get the sections
     * @param transformationPriority the transformation priority object that we are interested
     * @param rulesInformation extra rules information
     * @return the list of possible errors
     */
    ErrorList validateTransformation(String module, TransformationPriority transformationPriority, RulesInformation rulesInformation);
    
    ErrorList validateConversion(String module, ConversionPriority conversionPriority, RulesInformation rulesInformation);

    /**
     * Validates an other attachment document
     * @param module the module to get the sections
     * @param document the document to be validated
     * @param rulesInformation extra rules information
     * @return the list of possible errors
     */
    ErrorList validateOtherAttachment(String module, Document document, RulesInformation rulesInformation);

}
