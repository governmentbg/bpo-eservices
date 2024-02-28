package eu.ohim.sp.dsefiling.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.dsefiling.ui.action.DSFlowBeanAction;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * 
 * @author serrajo
 */
@Controller
public class DSDesignsController {
    
    private static final String VIEW_TABLE_DIVISIONAL_APPLICATION = "divisionalApplication/divisional_application_table";
	private static final String VIEW_CLAIMS_TABLE = "claim/claim_table_common";
    private static final String VIEW_DESIGNERS_TABLE = "designers/designer_card_list";
    private static final String VIEW_APPLICANTS_TABLE = "applicant/applicant_card_list";
    
	@Autowired
    private DSFlowBean flowBean;
	
	@Autowired
	private DSDesignsServiceInterface designsService;
   
	@Autowired
	private DSFlowBeanAction flowBeanAction;
	
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "isDesignLinked", method = RequestMethod.GET)
    @ResponseBody
    public String isDesignLinked(@RequestParam(required = true) String id) {
    	DesignForm designForm = flowBean.getObject(DesignForm.class, id);
    	Boolean isLinked = designForm != null && designsService.isDesignLinkedInSomeForm(designForm, flowBean);
    	return isLinked.toString();
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "getDivisionalApplicationTable", method = RequestMethod.POST)
    public ModelAndView getDivisionalApplicationTable() {
    	return new ModelAndView(VIEW_TABLE_DIVISIONAL_APPLICATION);
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "getClaimsTable", method = RequestMethod.POST)
    public ModelAndView getClaimsTable() {
    	return new ModelAndView(VIEW_CLAIMS_TABLE);
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "getDesignersTable", method = RequestMethod.POST)
    public ModelAndView getDesignersTable() {
    	return new ModelAndView(VIEW_DESIGNERS_TABLE);
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "addDesignerFromApplicant", method = RequestMethod.POST)
    public ModelAndView addDesignerFromApplicant() {
    	Boolean isThere = flowBeanAction.addDesignerFromApplicant(flowBean);
    	ModelAndView modelAndView = new ModelAndView(VIEW_DESIGNERS_TABLE);
    	modelAndView.getModel().put("isThere", isThere);
    	return modelAndView;
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "updateApplicantFromDesigner", method = RequestMethod.POST)
    public ModelAndView updateApplicantFromDesigner() {
    	flowBeanAction.updateApplicantFromDesigner(flowBean);
    	return new ModelAndView(VIEW_APPLICANTS_TABLE);
    }

}
