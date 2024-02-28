package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.core.user.UserSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller in charge of handling the adding and the editing of the signatures.
 * @author ionitdi
 */
@Controller
public class AddSignatureController extends AddAbstractController{

	//signature
	public static final String SG_FM = "signatureForm";
	public static final String SG_SM = "signature/signature_multiple";
	public static final String SG_IW = "signature/signature_import_warning";
	public static final String SG_CL = "signature/signature_card_list";
    
    @Autowired
    private UserSearchService userService;
    
    @Autowired
    private PersonServiceInterface personService;
    
    @Autowired
    private ApplicantFactory applicantFactory;

	@Autowired
	private FlowScopeDetails flowScopeDetails;
    
	@Value("${sp.office}")
    private String office;
    
	@Value("${sp.module}")
    private String mod;
    /**
     * 
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addSignature", method = RequestMethod.GET)
    public ModelAndView formBackingSignature(@RequestParam(required = false, value = "id") String id){
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(SignatureForm.class, SG_FM,
                                                                                    SG_SM, SG_SM, getMaxNumber()));    
        return model;
    }


    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Signature information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @RequestMapping(value = "addSignature", method = RequestMethod.POST)
    public ModelAndView onSubmitSignature(@ModelAttribute(SG_FM) SignatureForm command,
    										BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches){
        AddParameters addParameters = new AddParameters(SignatureForm.class, SG_FM, SG_CL,SG_SM, getMaxNumber());
        return addFormCheckMatches(command, result, addParameters, ignoreMatches);
    }
      
    /**
     * @param id the id of the signature
     * @return the view containing the signature form to edit
     */
    @RequestMapping(value = "getSignatureForEdit", method = RequestMethod.GET)
    public ModelAndView getSignature(@RequestParam(required = true, value = "id") String id  ){
    	
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(SignatureForm.class, SG_FM,
                SG_SM, SG_SM, getMaxNumber()));
        model.addObject("formEdit",true);
        return model;
       
    }
    
    /**
     * @param command the signature
     * @return the view containing the signature form to edit
     */
    @RequestMapping(value = "importSignature", method = RequestMethod.POST)
    public ModelAndView importSignature(@ModelAttribute(SG_FM) SignatureForm command, BindingResult result) {

    		ModelAndView modelAndView;
    		
    		SPUser userBean = AuthenticationUtil.getAuthenticatedUser();
        	if (userBean != null) {

        		try{
					User user = personService.getUser(flowScopeDetails.getFlowModeId());

    	    		SignatureForm signatureForm = new SignatureForm(); //call service for get signature detais from user
    	    		signatureForm.setFullName(user.getFullName());

    	    		BeanUtils.copyProperties(signatureForm, command);   		
    	    		AddParameters addParameters = new AddParameters(SignatureForm.class, SG_FM,
    	                    SG_CL, SG_SM, getMaxNumber());

    	    		if (command == null) {
    	    			// signature not found or more than one were returned
    	    			modelAndView = new ModelAndView(SG_IW);
    	    		} else {
    	    			modelAndView = loadImportedSignature(command, result, addParameters);
    	    			return modelAndView;
    	    		}
        		}
        		catch (SPException e) {
        			modelAndView = new ModelAndView(SG_IW);
    			}

        	} else {
        		// blank form
        		modelAndView = new ModelAndView(SG_SM);
    			modelAndView.addObject(SG_FM, new SignatureForm());
        	}

            return modelAndView;

    	}
    
    /**
     *Auxiliary method to add elements need it in the the @flowBean from the Signature imported 
     *and create the ModelAndView to be returned.
     * @return ModelAndView
     */
    private ModelAndView loadImportedSignature(SignatureForm importedSignature, BindingResult result, AddParameters addParameters){
    	importedSignature.setCheckBdBlocking(false);
    	importedSignature.setImported(true);
    	importedSignature.setValidateImported(true);
    	return addFormCheckMatches(importedSignature, result, addParameters, true);
    	
    }
    
    @Override
    protected String[] resolveMaxNumberProperty() {
    	return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT, 
    	                  ConfigurationServiceDelegatorInterface.SIGNATURE_ADD_MAXNUMBER};
    }

}
