package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya
 * 13.08.2019
 */
@Controller
public class EarlierAppImportRepresentativeController {
    @Autowired
    private PTFlowBean ptFlowBean;

    @Autowired
    private PersonServiceInterface personServiceInterface;

    @Autowired(required=false)
    private FlowScopeDetails flowScopeDetails;

    @RequestMapping(value = "importRepresentativesFromEarlierApp")
    public ModelAndView importRepresentativesFromEarlierApp(){
        if(ptFlowBean.isEarlierAppImported() && ptFlowBean.getEarlierAppRepresentatives() != null){
            ptFlowBean.getEarlierAppRepresentatives().stream().forEach( id -> {
                if (!ptFlowBean.existsObject(RepresentativeForm.class, id)) {
                    ptFlowBean.addObject(personServiceInterface.importRepresentative(id, flowScopeDetails.getFlowModeId()));
                }
            });
        }

        return new ModelAndView("representative/representative_card_list");
    }
}
