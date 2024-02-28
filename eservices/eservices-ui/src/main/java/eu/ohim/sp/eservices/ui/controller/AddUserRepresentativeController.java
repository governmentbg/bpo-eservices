package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileAttachmentStatus;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AddUserRepresentativeController {

    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private RepresentativeFactory representativeFactory;



    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addUserRepresentative", method = RequestMethod.POST)
    public ModelAndView addUserPersonDetails(@RequestParam(value = "selectedRepresentativeUser", required = false) List<String> selectedRepresentativeUser)
    {
        if (CollectionUtils.isNotEmpty(selectedRepresentativeUser)){
            if (CollectionUtils.isNotEmpty(flowBean.getUserRepresentativesForm())){
                for (String selected : selectedRepresentativeUser){
                    for (RepresentativeForm representativeForm : flowBean.getUserRepresentativesForm()){
                        if (representativeForm.getId().equalsIgnoreCase(selected)){
                            personService.addPersonFormDetails(flowBean, representativeForm, flowScopeDetails.getFlowModeId());
                        }
                    }
                }
            }
        }

        ModelAndView model = new ModelAndView("representative/representative_card_list");

        return model;
    }

    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addUserRepresentativeNaturalPersonDetails", method = RequestMethod.POST)
    public ModelAndView addUserNaturalPersonDetails(@RequestParam(required = false) FileAttachmentStatus defaultPOWOption)
    {
        return addPersonDetailsByKind(defaultPOWOption, RepresentativeKind.OTHER);
    }

    @PreAuthorize("hasRole('Representative_Add')")
    @RequestMapping(value = "addUserRepresentativeAssociationDetails", method = RequestMethod.POST)
    public ModelAndView addUserAssociationDetails(@RequestParam(required = false) FileAttachmentStatus defaultPOWOption)
    {
        return addPersonDetailsByKind(defaultPOWOption, RepresentativeKind.ASSOCIATION);
    }


    private ModelAndView addPersonDetailsByKind(FileAttachmentStatus defaultPOWOption, RepresentativeKind... kinds){
        ModelAndView model = new ModelAndView("representative/representative_card_list");
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Representative> representatives = userPersonDetails.getRepresentatives();
        int count = 0;
        List <RepresentativeForm> representativeForms = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(representatives)) {
            for (Representative representative : representatives) {
                if (Arrays.asList(kinds).contains(representative.getRepresentativeKind())){
                    RepresentativeForm representativeForm = representativeFactory.convertFrom(representative);
                    if(defaultPOWOption != null){
                        representativeForm.getRepresentativeAttachment().setAttachmentStatus(defaultPOWOption);
                    }
                    if(!flowBean.existsObject(RepresentativeForm.class, representativeForm.getId())) {
                        count++;
                        representativeForm.setImported(true);
                        representativeForm.setCurrentUserIndicator(true);
                        representativeForms.add(representativeForm);
                    } else {
                        representativeForm = flowBean.getObject(RepresentativeForm.class, representativeForm.getId());
                        representativeForm.setCurrentUserIndicator(true);
                        representativeForm.setImported(true);
                    }
                }
            }
        }
        if (count==1){
            personService.addPersonFormDetails(flowBean, representativeForms.get(0) , flowScopeDetails.getFlowModeId());
            model = new ModelAndView("representative/representative_card_list");
        }
        else if (count>1){
            flowBean.setUserRepresentativesForm(new ArrayList<RepresentativeForm>());
            flowBean.getUserRepresentativesForm().addAll(representativeForms);
            model = new ModelAndView("representative/representative_addUserData");
        }
        return model;
    }
}
