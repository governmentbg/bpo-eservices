package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.TreeSet;

@Controller
public class AddAppealController extends AddAbstractController {
	
	@Autowired
	private ESFlowBean flowBean;
	
	@Autowired
    private FlowScopeDetails flowScopeDetails;
	
	@PreAuthorize("hasRole('Appeal_Add')")
    @RequestMapping(value = "addAppeal", method = RequestMethod.POST)
    public ModelAndView onSubmitAppeal(@ModelAttribute("appeal") AppealForm command, BindingResult result) {
		AddParameters addParameters = new AddParameters(AppealForm.class, "appeal", "appeal/appeal_card_list", "appeal/appeal", getMaxNumber());
		if(command.getGsHelper().getExtent()){
			Set<GoodAndServiceForm> originalGAndS = new TreeSet<GoodAndServiceForm>();
			for(TMDetailsForm tmd: flowBean.getTmsList()){
				if(tmd.getOriginalGS() != null && tmd.getApplicationNumber() != null && command.getGsHelper().getTmApplicationNumber() != null &&
					tmd.getApplicationNumber().equals(command.getGsHelper().getTmApplicationNumber())){
					for(GoodAndServiceForm gsForm: tmd.getOriginalGS()){
						try {
							originalGAndS.add(gsForm.clone());
						} catch (CloneNotSupportedException e) {
							throw new SPException("Failed to find duplicate object", e, "error.genericError");
						}
					}
				}

			}
			command.getGsHelper().setGoodsAndServices(originalGAndS);
		} else {
			if(!flowBean.getGoodsServices().isEmpty()){
				command.getGsHelper().setGoodsAndServices(flowBean.getGoodsServices());

			}
		}
		ModelAndView mav = onSubmit(command, flowBean, addParameters, result);
		return mav;
	}
	
	@RequestMapping(value = "addAppeal", method = RequestMethod.GET)
	public ModelAndView formBackingAppeal(@RequestParam(required = false, value = "id") String id) {
		AddParameters addParameters = new AddParameters(AppealForm.class, "appeal", "appeal/appeal", "appeal/appeal", getMaxNumber());
		if(id != null && !id.equals("")){
			GSHelperForm commandHelper = flowBean.getObject(AppealForm.class, id).getGsHelper();
			if(commandHelper != null){
				boolean tmExists = false;
				if(commandHelper.getTmApplicationNumber() != null){
					for(TMDetailsForm tmd : flowBean.getTmsList()){
						if(tmd.getApplicationNumber() != null && tmd.getApplicationNumber().equals(commandHelper.getTmApplicationNumber())){
							tmExists = true;
							break;
						}
					}
				}
				if(tmExists) {
					Set<GoodAndServiceForm> gs = new TreeSet<GoodAndServiceForm>();
					flowBean.setGoodsServices(gs);
					if(commandHelper.getGoodsAndServices() != null){
						for(GoodAndServiceForm gsForm: commandHelper.getGoodsAndServices()){
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
	
	@Override
	public String[] resolveMaxNumberProperty(){
		return new String[]{ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT,
                ConfigurationServiceDelegatorInterface.APPEAL_ADD_MAXNUMBER};
	}
}
