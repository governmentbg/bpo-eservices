package eu.ohim.sp.eservices.ui.controller;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.util.WithdrawalUtil;

@Controller
public class AddGSHelperController extends AddAbstractController {
	@Autowired
	private ESFlowBean flowBean;

	@Autowired
	private FlowScopeDetails flowScopeDetails;


	@PreAuthorize("hasRole('GSHelper_Add')")    
	@RequestMapping(value = "addGSHelper", method = RequestMethod.POST)
	public ModelAndView onSubmitSurrender(@ModelAttribute("gshelper") GSHelperForm command, BindingResult result) {
		AddParameters addParameters = new AddParameters(GSHelperForm.class, "gshelper", "gshelper/gshelper_card_list", "gshelper/gshelper", getMaxNumber());
		if(command.getExtent()){
			Set<GoodAndServiceForm> originalGAndS = new TreeSet<GoodAndServiceForm>();
			if(command.getInclusive()){
				for(TMDetailsForm tmd: flowBean.getTmsList()){
					if(tmd.getOriginalGS() != null && tmd.getApplicationNumber() != null && command.getTmApplicationNumber() != null && tmd.getApplicationNumber().equals(command.getTmApplicationNumber())){
						for(GoodAndServiceForm gsForm: tmd.getOriginalGS()){
							try {
								originalGAndS.add(gsForm.clone());
							} catch (CloneNotSupportedException e) {
								throw new SPException("Failed to find duplicate object", e, "error.genericError");
							}
						}
					}
				}

			}
			command.setGoodsAndServices(originalGAndS);
		} else {
			if(!flowBean.getGoodsServices().isEmpty()){
				command.setGoodsAndServices(flowBean.getGoodsServices());

			}
		}
		ModelAndView mav = onSubmit(command, flowBean, addParameters, result);
		this.flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
		flowBean.setOriginalGS(new TreeSet<GoodAndServiceForm>());
		return mav;
	}

	@RequestMapping(value = "addGSHelper", method = RequestMethod.GET)
	public ModelAndView formBackingSurrender(@RequestParam(required = false, value = "id") String id) {
		AddParameters addParameters = new AddParameters(GSHelperForm.class, "gshelper", "gshelper/gshelper", "gshelper/gshelper", getMaxNumber());
		if(id != null && !id.equals("")){
			GSHelperForm command = flowBean.getObject(GSHelperForm.class, id);
			if(command != null){
				boolean tmExists = false;
				if(!command.getTmApplicationNumber().isEmpty()){
					for(TMDetailsForm tmd : flowBean.getTmsList()){
						if(tmd.getApplicationNumber() != null && tmd.getApplicationNumber().equals(command.getTmApplicationNumber())){
							tmExists = true;
							break;
						}
					}
				}
				if(tmExists) {
					Set<GoodAndServiceForm> gs = new TreeSet<GoodAndServiceForm>();
					flowBean.setGoodsServices(gs);
					if(command.getGoodsAndServices() != null && !command.getGoodsAndServices().isEmpty()){
						for(GoodAndServiceForm gsForm: command.getGoodsAndServices()){
							try {
								flowBean.getGoodsServices().add(gsForm.clone());
							} catch (CloneNotSupportedException e) {
								throw new SPException("Failed to find duplicate object", e, "error.genericError");
							}
						}
					}
				} else {
					flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
				}
			}
		} else {
			flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
		}
		ModelAndView mav = innerFormBackingObject(id, flowBean, addParameters);
		return mav;
	}
	
	@RequestMapping(value = "refreshGSHelper", method = RequestMethod.GET)
	public ModelAndView formBackingSurrender(@RequestParam(required = false, value = "id") String id, @RequestParam(required = false, value = "tm") String tmApplicationNumber) {
		ModelAndView mav = formBackingSurrender(id);
		mav.addObject("tmString", tmApplicationNumber);
		GSHelperForm helper = GSHelperForm.class.cast(mav.getModel().get("gshelper"));
		helper.setTmApplicationNumber(tmApplicationNumber);
		return mav;
	}

	@RequestMapping(value = "refreshGSHelperGS", method = RequestMethod.GET)
	@ResponseBody
	public String refreshRemainingGS(@RequestParam(required = false, value = "tmNum") String tmNum, @RequestParam(required = false, value = "edit") Boolean edit) {
		for(TMDetailsForm tmd: flowBean.getTmsList()){
			if(tmd.getOriginalGS() != null && tmd.getApplicationNumber() != null && tmNum != null && tmd.getApplicationNumber().equals(tmNum)){
				if(edit == null || edit == false){
					Set<GoodAndServiceForm> originalGAndS = new TreeSet<GoodAndServiceForm>();
					flowBean.setGoodsServices(originalGAndS);

					for(GoodAndServiceForm gsForm: tmd.getOriginalGS()){
						try {
							flowBean.getGoodsServices().add(gsForm.clone());
						} catch (CloneNotSupportedException e) {
							throw new SPException("Failed to find duplicate object", e, "error.genericError");
						}
					}
				}
				flowBean.setOriginalGS(tmd.getOriginalGS());
			}
		}
		return "success";
	}

	@Override
	public String[] resolveMaxNumberProperty(){
		return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT, 
				ConfigurationServiceDelegatorInterface.TM_ADD_MAXNUMBER};
	}
}
