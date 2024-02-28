/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.service.interfaces;

import java.util.List;

import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

/**
 * Service used to call the services 
 * @author karalch
 *
 */
public interface ImportServiceInterface {

	/**
	 * Service that is used to import application in the section 
	 * of previous CTM
	 * @param office the code of the office
	 * @param tradeMarkId the id of the trade mark
	 * @param flowModeId indicates the flow mode (wizard, oneform...)
	 * @return the imported trade mark transformed into flowBean and filtered
	 */
	FlowBeanImpl getTradeMark(String office, String tradeMarkId, Boolean extraImport, String flowModeId, FlowBeanImpl originalFlowBean);

	/**
	 * Service that is used to import application in the section 
	 * of priorities
	 * @param office the code of the office
	 * @param tradeMarkId the id of the trade mark
	 * @return the imported trade mark transformed into flowBean and filtered
	 */
	FlowBeanImpl getTradeMarkPriority(FlowBeanImpl originalFlowBean, String office, String tradeMarkId,
			Boolean extraImport, String flowModeId);

	/**
	 * Service that is used to import application in the section 
	 * of seniorities
	 * @param office the code of the office
	 * @param tradeMarkId the id of the trade mark
	 * @return the imported trade mark transformed into flowBean and filtered
	 */
	FlowBeanImpl getTradeMarkSeniority(FlowBeanImpl originalFlowBean, String office, String tradeMarkId,
			Boolean extraImport, String flowModeId);

	/**
	 * Service that is used to import application in the section 
	 * of transformations
	 * @param office the code of the office
	 * @param tradeMarkId the id of the trade mark
	 * @return the imported trade mark transformed into flowBean and filtered
	 */
	FlowBeanImpl getTradeMarkTransformation(FlowBeanImpl originalFlowBean, String office,
			String tradeMarkId, Boolean extraImport, String flowModeId);
	
	/**
	 * Autocomplete service that is looking for trademark containing text
	 * @param office the code of the office
	 * @param text the inserted text used to autocomplete
	 * @param numberOfResults number of results expected by the service
	 * @return a list of json objects string
	 */
	String getTradeMarkAutocomplete(String office, String text,
			Integer numberOfResults, boolean previousCTM);

	/**
	 * Service that gets all the similar trademarks according to some rules
	 * of the external service
	 * @param office the office that will get the similar trademarks
	 * @param flowBeanImpl the object representing the data of the trademark
	 * @return similar mark Forms list
	 */
    List<SimilarMarkForm> getSimilarTradeMarks(String office, FlowBeanImpl flowBeanImpl);
    
    FlowBeanImpl getCTMConversion(FlowBeanImpl originalFlowBean, String office,
    		String tradeMarkId, Boolean extraImport, String flowModeId);
    
}
