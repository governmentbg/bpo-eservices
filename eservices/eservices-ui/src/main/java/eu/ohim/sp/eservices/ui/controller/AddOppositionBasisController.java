package eu.ohim.sp.eservices.ui.controller;

import java.util.*;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsListForm;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightDetails;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESDesignService;
import eu.ohim.sp.eservices.ui.service.ESTrademarkService;
import eu.ohim.sp.eservices.ui.util.AutomaticImportUtils;
import eu.ohim.sp.eservices.ui.util.ESDesignDetailsComparator;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;
import eu.ohim.sp.eservices.ui.util.Result;
import eu.ohim.sp.eservices.ui.util.TaxonomyUtil;

/**
 * Controller in charge of handling the adding and the editing of the applicants.
 * @author ionitdi
 */
@Controller
public class AddOppositionBasisController extends AddAbstractController{

	/**
	 * Logger for this class and subclasses
	 */
	private static final Logger logger = Logger.getLogger(AddOppositionBasisController.class);

	/**
	 * session bean
	 */
	@Autowired
	private ESFlowBean flowBean;

	@Autowired
	private ESTrademarkService esTrademarkService;

	@Autowired
	private PersonServiceInterface personService;

	@Autowired
	private FlowScopeDetails flowScopeDetails;

	@Autowired
	private ESDesignService esDesignService;

	private Integer maxNumOpposition = -1;
	private Integer maxNumObjection = -1;
	private Integer maxNumRevocation = -1;


	@RequestMapping(value = "getErrorEarlierEntitlement", method = RequestMethod.POST)
	public ModelAndView getErrorEE(@ModelAttribute("OppositionBasisForm") OppositionBasisForm command){


		ModelAndView model = new ModelAndView(EServicesConstants.OP_BA_EA_RI) ;
		String legalActVersionParam = command.getLegalActVersion().getCode();
		String earlierEntitleMent = command.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
		model.addObject(EServicesConstants.LEGALACT_VP, legalActVersionParam);
		model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);
		model.addObject(EServicesConstants.FORMEDIT,true);
		model.addObject(EServicesConstants.OP_BA_FORM,command);


		return model;

	}

	@RequestMapping(value = "getErrorEarlierTM", method = RequestMethod.POST)
	public ModelAndView getErrorETM(@ModelAttribute("OppositionBasisForm") OppositionBasisForm command){

		ModelAndView model = new ModelAndView(EServicesConstants.OP_BA_EA_TD);
		String earlierEntitleMent = command.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
		model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);
		model.addObject(EServicesConstants.FORMEDIT,true);
		model.addObject(EServicesConstants.OP_BA_FORM,command);

		return model;

	}


	@RequestMapping(value = "getEditEarlierEntitlement", method = RequestMethod.POST)
	public ModelAndView getEditEE(@RequestParam(required = true, value = "id") String id ){

		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM,
				EServicesConstants.OP_BA_EA_RI, EServicesConstants.OP_BA_EA_RI, getMaxNumberEarlierRight()));

		flowBean.setDssTempList(new ArrayList<ESDesignDetailsForm>());

		OppositionBasisForm ob =  (OppositionBasisForm) model.getModel().get(EServicesConstants.OP_BA_FORM);
		if(ob.getRelativeGrounds().getEarlierEntitlementRightInf() != null && ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails() != null &&
				ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails() != null) {
			flowBean.setGoodsServices(ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails().getGoodsAndServices());
			flowBean.setOriginalGS(ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails().getOriginalGS());
		}

		if(ob.getRelativeGrounds().getEarlierEntitlementRightInf()!=null && ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails() != null
				&& ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns() != null){
			flowBean.setDssTempList(ob.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierDesigns());
		}
		String legalActVersionParam = ob.getLegalActVersion().getCode();
		String earlierEntitleMent = ob.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
		model.addObject(EServicesConstants.LEGALACT_VP, legalActVersionParam);
		model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);
		model.addObject(EServicesConstants.FORMEDIT,true);


		return model;

	}


	@RequestMapping(value = "getEditEarlierTM", method = RequestMethod.POST)
	public ModelAndView getEditETM(@RequestParam(required = true, value = "id") String id ){

		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM,
				EServicesConstants.OP_BA_EA_TD, EServicesConstants.OP_BA_EA_TD, getMaxNumber()));
		OppositionBasisForm ob =  (OppositionBasisForm) model.getModel().get(EServicesConstants.OP_BA_FORM);
		String earlierEntitleMent = ob.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
		model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);
		model.addObject(EServicesConstants.FORMEDIT,true);

		return model;

	}

	@RequestMapping(value = "getEditEarlierDS", method = RequestMethod.GET)
	public ModelAndView getEditEDS(@RequestParam(required = true, value = "id") String id ){

		ModelAndView model = new ModelAndView("designs/ds_details_section");
		flowBean.clearLocarno();
		if(id!= null && !id.equals("") && flowBean.getDssTempList() != null){
			ESDesignDetailsForm modelAttr = findTempDS(id);
			if(modelAttr != null){
				flowBean.getLocarno().addAll(modelAttr.getLocarno());
				model.addObject(EServicesConstants.DS_DF, modelAttr);
			} else {
				model.setViewName("errors");
			}
		}

		model.addObject(EServicesConstants.FORMEDIT,true);

		return model;

	}

	@RequestMapping(value="changeEarlierDesignChecked", method = RequestMethod.POST)
	public ModelAndView changeEarlierDesignChecked(@RequestParam(required = true, value="selected")Boolean selected, @RequestParam(required = true, value = "id") String id){
		if(id!= null && !id.equals("") && flowBean.getDssTempList() != null){
			ESDesignDetailsForm modelAttr = findTempDS(id);
			if(modelAttr != null){
				modelAttr.setSelected(selected);
			}
		}
		return null;
	}

	@RequestMapping(value = "addTMEarlierDetails", method = RequestMethod.GET)
	public ModelAndView formBackingTMDetails(@RequestParam(required = false, value = "id") String id, @RequestParam(required = true, value = EServicesConstants.EarlierEM)String earlierEntitleMent)
	{

		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, EServicesConstants.OP_BA_EA_TD, EServicesConstants.OP_BA_EA_TD, getMaxNumber()));
		model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);


		return model;
	}

	private ESDesignDetailsForm findTempDS(String id){
		if(id!= null && !id.equals("") && flowBean.getDssTempList() != null){
			for(ESDesignDetailsForm form: flowBean.getDssTempList()){
				if(form.getDesignIdentifier() != null && form.getDesignIdentifier().equalsIgnoreCase(id)){
					return form;
				}
			}
		} else {
			return null;
		}
		return null;
	}


	/**
	 *Recovers the indicated earlier trademark from the TradeMarkService, validated it,
	 *populate the form and defines the view to be show to the user.
	 *
	 * @param command
	 *            object that contains the Priority information.
	 * @param result
	 *            manage the validation results of the form object
	 * @return Priority object view with the new priority added
	 */
	@RequestMapping(value = "importTrademarkEarlier", method = RequestMethod.POST)
	public ModelAndView importTMDetails(@ModelAttribute(EServicesConstants.OP_BA_FORM) OppositionBasisForm command, BindingResult result,
										@RequestParam(value = "importId", required = false) String tradeMarkId, @RequestParam(required = true, value = EServicesConstants.EarlierEM)String earlierEntitleMent,
										@RequestParam(required = true, value = "officeImport")String officeImport, @RequestParam(value = "languagesFilter", required = false) String languagesFilter) {

		ModelAndView model;

		if (tradeMarkId != null && !(StringUtils.isEmpty(officeImport)) && !officeImport.equals("NONE")) {
			try{
				TMDetailsForm tm = esTrademarkService.getTradeMark(tradeMarkId, officeImport);
				if (languagesFilter!=null && tm!=null){
					if(tm.getApplicationLanguage() != null && !tm.getApplicationLanguage().equals("")){
						languagesFilter += ","+tm.getApplicationLanguage();
					}
					tm.setGoodsAndServices(TaxonomyUtil.filterGoodsAndServices(tm.getGoodsAndServices(), languagesFilter));
					tm.setOriginalGS(TaxonomyUtil.filterGoodsAndServices(tm.getOriginalGS(), languagesFilter));
				}


				if (tm == null) {
					// trademark not found or more than one were returned
					model = new ModelAndView("tm_details/tm_import_warning");
				} else {

					model = new ModelAndView(EServicesConstants.OP_BA_EA_TD);
					AddParameters addParameters = new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM,
							"topposition/basis/earlier_trademark_details", EServicesConstants.OP_BA_EA_TD, getMaxNumber());

					tm.setImported(true);
					tm.setExtent(true);
					if (command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails()==null){
						command.getRelativeGrounds().getEarlierEntitlementRightInf().setEarlierEntitlementRightDetails(new EarlierEntitlementRightDetails());
					}
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setEarlierTradeMarkDetails(tm);

					if (tm.getApplicants()!=null && tm.getApplicants().size()>0){
						command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setApplicantHolder(tm.getApplicants().get(0));
					}
					else
					{
						command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setApplicantHolder(null);
					}

					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setCountry(tm.getRegistrationOffice());
					command.setImported(true);
					command.setCheckBdBlocking(false);
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setFilterCountriesEarlierTradeMark(Boolean.TRUE);
					validateCommand(command, result, addParameters);
					command.setCheckBdBlocking(true);
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setFilterCountriesEarlierTradeMark(Boolean.FALSE);
					if (result.hasErrors()) {
						model.addObject("errorImportCountry",true);
					}
					flowBean.setGoodsServices(tm.getGoodsAndServices());
					flowBean.setOriginalGS(tm.getOriginalGS());
					flowBean.setOppositionBasis(command);
					model.addObject(EServicesConstants.OP_BA_FORM,command);
					model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);
				}
			}
			catch (SPException e) {
				model = new ModelAndView("tm_details/tm_import_warning");
			}
		} else {

			// blank form
			model = new ModelAndView(EServicesConstants.OP_BA_EA_TD);
			flowBean.setOppositionBasis(command);
			model.addObject(EServicesConstants.OP_BA_FORM,command);
			model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);

		}

		return model;

	}

	@RequestMapping(value = "importDesignEarlier", method = RequestMethod.POST)
	public ModelAndView importDSDetails(
			@RequestParam(value = "importId", required = false) String designId,
			@ModelAttribute(EServicesConstants.OP_BA_FORM) OppositionBasisForm command,
			BindingResult result) {

		ModelAndView modelAndView;
		AddParameters addParameters = new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, "opposition/basis/ds_earlier_card_list","opposition/basis/ds_earlier_card_list", getMaxNumber());
		if (designId != null) {

            designId = designId.substring(0, designId.lastIndexOf('-') != -1 ? designId.lastIndexOf('-') : designId.length());

			if(flowBean.getDssTempList() != null) {
				for(ESDesignDetailsForm design: flowBean.getDssTempList()){
					if(design.getRegistrationNumber() != null && design.getRegistrationNumber().equals(designId) ){
						modelAndView = new ModelAndView("opposition/basis/ds_earlier_exists_warning");
						return modelAndView;
					}
				}
			}

			try{
				ESDesignDetailsListForm designDetailsListForm = esDesignService.getDesignApplication(designId);
				List<ESDesignDetailsForm> dsDetailsForms = null;
				if(designDetailsListForm != null) {
					if (designDetailsListForm.containsApplications() && designDetailsListForm.hasMultipleApplications()) {
						modelAndView = new ModelAndView("opposition/basis/ds_choose_application");
						modelAndView.addObject("applicationsList", designDetailsListForm.getListApplications());
						modelAndView.addObject("registrationNumber", designId);
						return modelAndView;
					} else {
						dsDetailsForms = designDetailsListForm.getDesignDetailsFormList();
					}
				}

				modelAndView = postEarlierDesignImportProcess(dsDetailsForms, addParameters, result);
			}
			catch (SPException e) {
				modelAndView = new ModelAndView(EServicesConstants.DS_IW);
			}
		} else {
			modelAndView = new ModelAndView("opposition/basis/ds_earlier_import");
			modelAndView.addObject(EServicesConstants.OP_BA_FORM, command);
		}

		return modelAndView;
	}

	@RequestMapping(value = "importDesignEarlierByApplicationId", method = RequestMethod.POST)
	public ModelAndView importDesignEarlierByApplicationId(
			@RequestParam(value = "applicationId") String applicationId,
			@ModelAttribute(EServicesConstants.OP_BA_FORM) OppositionBasisForm command,
			BindingResult result) {
		AddParameters addParameters = new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, "opposition/basis/ds_earlier_card_list", "opposition/basis/ds_earlier_card_list", getMaxNumber());
		if(flowBean.getDssTempList() != null) {
			for(ESDesignDetailsForm design: flowBean.getDssTempList()){
				if(design.geteSDesignApplicationData() != null && design.geteSDesignApplicationData().getApplicationNumber() != null && design.geteSDesignApplicationData().getApplicationNumber().equals(applicationId) ){
					return new ModelAndView("opposition/basis/ds_earlier_exists_warning");
				}
			}
		}
		try{
			ESDesignDetailsListForm designDetailsListForm = esDesignService.getDesignApplicationByApplicationId(applicationId);
			List<ESDesignDetailsForm> dsDetailsForms = null;
			if(designDetailsListForm != null && designDetailsListForm.containsApplications() && !designDetailsListForm.hasMultipleApplications()) {
				dsDetailsForms = designDetailsListForm.getDesignDetailsFormList();
			}
			return postEarlierDesignImportProcess(dsDetailsForms, addParameters, result);
		} catch (SPException e) {
			return new ModelAndView(EServicesConstants.DS_IW);
		}
	}

	private ModelAndView postEarlierDesignImportProcess(List<ESDesignDetailsForm> dsDetailsForms, AddParameters addParameters, BindingResult result){
		if(dsDetailsForms != null && dsDetailsForms.size()>0) {
			if(flowBean.getDssTempList() == null){
				flowBean.setDssTempList(new ArrayList<ESDesignDetailsForm>());
			}
			flowBean.getDssTempList().addAll(dsDetailsForms);
			Collections.sort(flowBean.getDssTempList(), new ESDesignDetailsComparator());
			flowBean.getDssTempList().stream().forEach(
					dsDetailsForm -> {
						dsDetailsForm.setImported(true);
						dsDetailsForm.setId(dsDetailsForm.getDesignIdentifier());
						validateCommand(dsDetailsForm, result, addParameters);
					}
			);
		}
		return new ModelAndView("opposition/basis/ds_earlier_card_list");
	}


	/**
	 *
	 *
	 * @param id the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 * @throws Exception if it fails to load the applicant
	 */
	@RequestMapping(value = "addOppositionBasis", method = RequestMethod.GET)
	public ModelAndView formBackingOppositionBasis(@RequestParam(required = false, value = "id") String id, @RequestParam(required = false, value = EServicesConstants.LEGALACT) String legalAct, @RequestParam(required = false, value = EServicesConstants.GROUNDCAT) String groundCategory)
	{
		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, EServicesConstants.OP_BA_DT_SE, EServicesConstants.OP_BA_DT_SE, getMaxNumber()));

		flowBean.setDssTempList(new ArrayList<ESDesignDetailsForm>());

		model.addObject(EServicesConstants.LEGALACT,legalAct);
		model.addObject("groundCat", groundCategory);
		return model;
	}



	@RequestMapping(value = "confirmLegalActVersion", method = RequestMethod.GET)
	public ModelAndView refreshGroundCategory(@RequestParam(required = false, value = "id") String id, @RequestParam(required = false, value = EServicesConstants.LEGALACT) String legalAct, @RequestParam(required = false, value = EServicesConstants.GROUNDCAT) String groundCategory)
	{
		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, EServicesConstants.OP_BA_DT_SE, EServicesConstants.OP_BA_DT_SE, getMaxNumber()));
		model.addObject(EServicesConstants.LEGALACT, legalAct);
		model.addObject(EServicesConstants.GROUNDCAT, groundCategory);
		return model;
	}
	@RequestMapping(value = "changeEarlierEntitlement", method = RequestMethod.GET)
	public ModelAndView refreshEarlierEntitlementDetails(@RequestParam(required = false, value = "id") String id, @RequestParam(required = true, value = EServicesConstants.LEGALACT_VP)String legalActVersionParam,
														 @RequestParam(required = true, value = EServicesConstants.EarlierEM)String earlierEntitleMent)
	{
		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM,
				EServicesConstants.OP_BA_EA_RI, EServicesConstants.OP_BA_EA_RI, getMaxNumberEarlierRight()));
		model.addObject(EServicesConstants.LEGALACT_VP, legalActVersionParam);
		model.addObject(EServicesConstants.EarlierEM, earlierEntitleMent);


		return model;
	}



	/**
	 * Adds or edits on the collection stored in the session
	 *
	 * @param command object that contains the Applicant information.
	 * @param result  manage the validation results of the form object
	 * @return Applicant object view with the new applicant added
	 */
	@RequestMapping(value = "addOppositionBasis", method = RequestMethod.POST)
	public ModelAndView onSubmitOppositionBasis(@ModelAttribute(EServicesConstants.OP_BA_FORM) OppositionBasisForm command,
												BindingResult result,
												@RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches, String countries)
	{
		boolean isEdit = flowBean.existsObject(OppositionBasisForm.class, command.getId());
		List<String>countriesList = new ArrayList<String>();
		AddParameters addParameters = new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, "opposition/opposition_basis_card_list",EServicesConstants.OP_BA_DT_SE, getMaxNumber());

		if (command.getAbsoluteGrounds()!=null ) {
			command.setGroundCategory(GroundCategoryKind.ABSOLUTE_GROUNDS);
		}
		else if (command.getRevocationGrounds()!=null ) {
			command.setGroundCategory(GroundCategoryKind.REVOCATION_GROUNDS);
		}
		else if (command.getRelativeGrounds()!=null && command.getRelativeGrounds().getEarlierEntitlementRightInf()!=null) {
			command.setGroundCategory(GroundCategoryKind.RELATIVE_GROUNDS);
			boolean earlierTMGroundSelected = false;
			boolean earlierDSGroundSelected = false;
			if(command.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition() != null && command.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getRelativeGroundText() != null &&
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getRelativeGroundText().size()>0){

				String gr = command.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getRelativeGroundText().stream()
						.filter(ground -> ground != null)
						.filter(ground ->
								(ground.equals("ds.invalidityGround.29.1.1.12") ||ground.equals("ds.invalidityGround.29.1.1.13" )
										|| ground.equals("ds.invalidityGround.29.1.1.13a") || ground.equals("ds.invalidityGround.29.1.3a")
										|| ground.equals("ds.invalidityGround.29.1.3b")) || ground.equals("ds.invalidityGround.29.2.2a")
						).findFirst().orElse("");
				if(gr.equals("ds.invalidityGround.29.1.1.12") ||gr.equals("ds.invalidityGround.29.1.1.13" )
						|| gr.equals("ds.invalidityGround.29.1.1.13a") || gr.equals("ds.invalidityGround.29.1.3a")
						|| gr.equals("ds.invalidityGround.29.1.3b")){
					earlierDSGroundSelected = true;
				}
				if(gr.equals("ds.invalidityGround.29.2.2a")){
					earlierTMGroundSelected = true;
				}
			}
			if(flowBean.getDssTempList() != null && flowBean.getDssTempList().size() >0){
				if(command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails() == null) {
					command.getRelativeGrounds().getEarlierEntitlementRightInf().setEarlierEntitlementRightDetails(new EarlierEntitlementRightDetails());
				}
				if(earlierDSGroundSelected){
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setEarlierDesigns(flowBean.getDssTempList());
				} else {
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setEarlierDesigns(new ArrayList<ESDesignDetailsForm>());
				}
			}

			if (command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails()!= null ){
				//Insert the goods and services in the respective attribute
				if(flowScopeDetails.getFlowModeId().equals("ds-invalidity") && !earlierTMGroundSelected){
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setEarlierTradeMarkDetails(null);
					command.getRelativeGrounds().getEarlierEntitlementRightInf().setEntitlementOpponent(null);
				} else {
					TMDetailsForm tmDetails = command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails();
					if(tmDetails != null){
						tmDetails.setGoodsAndServices(flowBean.getGoodsServices());
						tmDetails.setOriginalGS(flowBean.getOriginalGS());
						checkRepresentationAttachment(tmDetails);
					}
				}
			}
			if (command.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition()!=null){
				if (countries!= null) {
					countriesList = Arrays.asList(countries.split(";"));
					command.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().setgROCountryReputationClaimed(countriesList);
				}
			}

			command.setCheckBdBlocking(true);
		}


		ModelAndView model = addFormCheckMatches(command, result, addParameters, ignoreMatches);
		if (result.hasErrors() && !(result.getFieldError("formWarnings")!=null && result.getErrorCount()==1)){
			String legalAct= command.getLegalActVersion().getCode();
			GroundCategoryKind groundCategory= command.getGroundCategory();
			String earlierRightType = null;
			flowBean.setReputationCountries(new ArrayList<String>());
			if (command.getRelativeGrounds()!=null){
				earlierRightType = command.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
				flowBean.setReputationCountries(countriesList);
			}

			model.addObject(EServicesConstants.LEGALACT,legalAct);
			model.addObject(EServicesConstants.GROUNDCAT, groundCategory);
			model.addObject(EServicesConstants.LEGALACT_VP, legalAct);
			model.addObject(EServicesConstants.EarlierEM, earlierRightType);
			model.addObject("formErrors",true);

			return model;

		} else if(!result.hasErrors()){
			if (command.getRelativeGrounds() != null && command.getRelativeGrounds().getEarlierEntitlementRightInf() != null &&  command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails()!= null ){
				//Insert the goods and services in the respective attribute
				TMDetailsForm tmDetails = command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails();
				ApplicantForm appl =  command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getApplicantHolder();
				if(tmDetails != null && appl !=null && !flowScopeDetails.getFlowModeId().equals("ds-invalidity")){
					if(tmDetails.getImported() && !isEdit && appl != null){
						List<ApplicantForm> list = new ArrayList<ApplicantForm>();
						list.add(appl);
						AutomaticImportUtils.copyOwnersToApplicants(list,
								flowBean, personService, flowScopeDetails);
					}
				}
			}
		}
		flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
		flowBean.setOriginalGS(new TreeSet<GoodAndServiceForm>());
		flowBean.setDssTempList(new ArrayList<ESDesignDetailsForm>());

		return model;
	}

	@RequestMapping(value = "confirmLegalActVersion", method = RequestMethod.POST)
	public ModelAndView onSubmitLegalActVersion(@ModelAttribute("OppositionBasisForm") OppositionBasisForm command,
												BindingResult result,
												@RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches)
	{
		AddParameters addParameters = new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, EServicesConstants.OP_BA_DT_SE, EServicesConstants.OP_BA_DT_SE, getMaxNumber());
		return addLegalActVersionCheckMatches(command, result, addParameters, ignoreMatches);
	}


	/**
	 * Entry point to get an applicant form for edit.
	 * This method figures out the type of the applicant and uses another method that can handle
	 * that type of applicant.
	 * If the type is not found or there is no such applicant with that id, it returns null.
	 * @param id the id of the applicant
	 * @return the view containing the applicant form to edit
	 */
	@RequestMapping(value = "getOppositionBasisForEdit", method = RequestMethod.GET)
	public ModelAndView getOB(@RequestParam(required = true, value = "id") String id  )
	{

		ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, EServicesConstants.OP_BA_DT_SE, EServicesConstants.OP_BA_DT_SE, getMaxNumber()));
		OppositionBasisForm ob =  (OppositionBasisForm) model.getModel().get(EServicesConstants.OP_BA_FORM);
		String legalAct= ob.getLegalActVersion().getCode();
		GroundCategoryKind groundCategory= ob.getGroundCategory();
		String earlierRightType = null;
		flowBean.setReputationCountries(new ArrayList<String>());
		if (ob.getRelativeGrounds()!=null){
			earlierRightType = ob.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
			List <String> countries = new ArrayList<String>();
			if (ob.getRelativeGrounds().getEarlierEntitlementRightInf()!=null && ob.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition() != null
					&& ob.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgROCountryReputationClaimed()!=null){
				countries=ob.getRelativeGrounds().getEarlierEntitlementRightInf().getGroundsRelativeForOpposition().getgROCountryReputationClaimed();
			}
			flowBean.setReputationCountries(countries);
		}

		model.addObject(EServicesConstants.LEGALACT,legalAct);
		model.addObject(EServicesConstants.GROUNDCAT, groundCategory);
		model.addObject(EServicesConstants.LEGALACT_VP, legalAct);
		model.addObject(EServicesConstants.EarlierEM, earlierRightType);
		model.addObject(EServicesConstants.FORMEDIT,true);


		return model;

	}

	private ModelAndView selectLegalActVersion(OppositionBasisForm command, BindingResult result, AddParameters addParameters)
	{
		return onSubmit(command, flowBean, addParameters, result);
	}


	private ModelAndView addLegalActVersionCheckMatches(OppositionBasisForm command, BindingResult result, AddParameters addParameters,
														Boolean ignoreMatches)
	{
		return selectLegalActVersion(command, result, addParameters);
	}

	@RequestMapping(value = "addCountry", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result addCountry(String country) {
		flowBean.addReputationCountry(country);
		return new Result(EServicesConstants.SUCCESS);
	}



	@RequestMapping(value = "removeCountry", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result removeCountry(
			@RequestParam(required = true, value = "inputCountry") String inputCountry,
			@RequestParam(required = true, value = "country") String country) {
		List <String> listCountries = flowBean.getReputationCountries();
		if (listCountries!=null) {
			listCountries.remove(country);
		}
		return new Result(EServicesConstants.SUCCESS);
	}


	@RequestMapping(value = "countries", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Collection<String> countries() {
		logger.debug("START countries");
		Collection<String> countriesData = new ArrayList<String>();
		countriesData.addAll(flowBean.getReputationCountries());
		countriesData.addAll(flowBean.getReputationCountries());
		logger.debug("END countries");
		return countriesData;
	}

	@Override
    protected Integer getMaxNumber() {
	    if(flowScopeDetails.getFlowModeId().equalsIgnoreCase("tm-objection")){
            if(maxNumObjection == -1){
                this.maxNumberToAdd = -1;
                maxNumObjection = super.getMaxNumber();
            }
            return maxNumObjection;
        } else if(flowScopeDetails.getFlowModeId().equalsIgnoreCase("tm-revocation")) {
            if(maxNumRevocation == -1){
                this.maxNumberToAdd = -1;
                maxNumRevocation = super.getMaxNumber();
            }
            return maxNumRevocation;
        } else {
            if(maxNumOpposition == -1){
                this.maxNumberToAdd = -1;
                maxNumOpposition = super.getMaxNumber();
            }
            return maxNumOpposition;
        }
    }


	@Override
	protected String[] resolveMaxNumberProperty() {
		if(flowScopeDetails.getFlowModeId().equalsIgnoreCase("tm-objection")){
			return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT,
					ConfigurationServiceDelegatorInterface.OBJB_ADD_MAXNUMBER};
		}

		if(flowScopeDetails.getFlowModeId().equalsIgnoreCase("tm-revocation")){
			return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT,
					ConfigurationServiceDelegatorInterface.REVOB_ADD_MAXNUMBER};
		}

		return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT,
				ConfigurationServiceDelegatorInterface.OB_ADD_MAXNUMBER};
	}

	public Integer getMaxNumberEarlierRight()
	{
		return getMaxNumber();
	}


	public ModelAndView onSubmit(OppositionBasisForm command, FlowBean flowBean, AddParameters addParameters, BindingResult result){

		logger.info("START onSubmit");
		if (command == null){
			throw new SPException("Command is null", EServicesConstants.ERROR_GE);
		}
		if (flowBean == null){
			throw new SPException("Flow is null", EServicesConstants.ERROR_GE);
		}
		if (addParameters == null){
			throw new SPException("AddParameters is null", EServicesConstants.ERROR_GE);
		}
		if (result == null){
			throw new SPException("Binding result is null", EServicesConstants.ERROR_GE);
		}


		ModelAndView modelAndView = null;

		if (!command.getImported() || command.getValidateImported()) {
			boolean validObject = validateCommand(command, result, addParameters);
			if (!validObject) {
				modelAndView = new ModelAndView(addParameters.getFormView());
				if (flowBean.existsObject(addParameters.getCommandClass(), command.getId())) {
					modelAndView.addObject("entityPosition",
							1 + flowBean.getIndex(addParameters.getCommandClass(), command.getId()));
				} else {
					modelAndView.addObject("entityPosition", 1 + flowBean
							.getCollection(addParameters.getCommandClass()).size());
				}
				return modelAndView;
			}
		}

		//if validation result is good, perform validation for warning messages
		if(command.getRelativeGrounds()!=null &&  command.getRelativeGrounds().getEarlierEntitlementRightInf() !=null &&  command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails()!=null){
			command.setCheckBdBlocking(false);
			//change temporary the imported flag to make validation of warning messages
			Boolean isImported = command.getImported();
			command.setImported(true);
			command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setFilterCountriesEarlierTradeMark(Boolean.TRUE);
			AddParameters addParametersTemp = new AddParameters(OppositionBasisForm.class, EServicesConstants.OP_BA_FORM, EServicesConstants.OP_BA_DT_SE, EServicesConstants.OP_BA_DT_SE, getMaxNumber());
			validateCommand(command, result, addParametersTemp);
			command.setImported(isImported);
			command.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().setFilterCountriesEarlierTradeMark(Boolean.FALSE);
		}
		modelAndView = new ModelAndView(addParameters.getSuccessView());
		logger.debug("add " + addParameters.getCommandClass());

		try {
			String id = command.getId();

			if (!flowBean.existsObject(addParameters.getCommandClass(), id)) {
				flowBean.addObject(command);
			} else {
				flowBean.replaceObject(command, id);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new SPException("Failed to find any object", e, EServicesConstants.ERROR_GE);
		}

		return modelAndView;
	}

	/**
	 * Check for attached files
	 * @param form
	 */
	private void checkRepresentationAttachment(TMDetailsForm form) {
		if(!form.getImported()) {
			if(form.getRepresentationAttachment()!=null) {
				String docId=(form.getRepresentationAttachment().getStoredFiles()!=null?(form.getRepresentationAttachment().getStoredFiles().size()>0? "getDocument.htm?documentId="+form.getRepresentationAttachment().getStoredFiles().get(0).getDocumentId():""):"");
				form.setImageRepresentationURI(docId);
			} else {
				form.setImageRepresentationURI("");
			}
		} else {
			if(form.getImageRepresentationURI()!=null && !"".equals(form.getImageRepresentationURI())) {
				StoredFile sf = new StoredFile();
				sf.setContentType(EServicesConstants.MIME_IMAGE_PNG);
				if(form.getRepresentationAttachment()==null) {
					form.setRepresentationAttachment(new FileWrapper());
				}
				form.getRepresentationAttachment().getStoredFiles().add(sf);
			}
		}
	}
}
