/*
 *  PersonService:: DesignerService 06/09/13 12:59 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.person;

import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.person.PersonChange;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

import java.util.List;

public interface PersonChangeService {

	/**
	 * Validate the PersonChange provided in input by checking all the business
	 * rules that apply. It returns a validation result and a list of errors
	 * that will specify which rule has not passed the validation test.
	 * 
	 * @param module
	 * @param personChange
	 * @param rulesInformation
	 * @return error list with validation result 
	 */
	ErrorList validatePersonChange(String module, PersonChange personChange,
							   RulesInformation rulesInformation);
}
