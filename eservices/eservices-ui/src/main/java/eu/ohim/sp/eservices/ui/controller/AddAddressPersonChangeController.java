/*******************************************************************************
 * * $Id:: AddRepresentativeController.java 50771 2012-11-14 15:10:27Z karalch   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.controller;

import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.getViewByRepresentativeType;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.getView;
import static eu.ohim.sp.common.ui.util.PersonChangeControllerHelper.PersonChangeViewKind.ADDRESS;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.util.PersonChangeControllerHelper;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller in charge of handling the adding and the editing of the change of persons.
 *
 * @author velosma
 */
@Controller
public class AddAddressPersonChangeController extends AddAbstractController {

    private static final Logger logger = Logger.getLogger(AddAddressPersonChangeController.class);

    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;

    @Autowired
    private ConfigurationService systemConfigurationService;

    @Autowired
    private FlowScopeDetails flowScopeDetails;


    @RequestMapping(value = "getChangePersonFromTmDs", method = RequestMethod.GET)
    public ModelAndView getChangePersonFromTmDs(@RequestParam(required = false, value = "id") String id,
            @RequestParam ChangePersonType changeType) throws CloneNotSupportedException {

        RepresentativeForm form = flowBean.getImportedRepresentative(id).clone();

        if (changeType != null && form.getChangeType() == null) {
            form.setChangeType(changeType);
        }

        form.setPreviousPersonId(form.getId());
        form.setPreviousPersonName(form.getName());
        form.setPreviousPersonAddress(form.getAddress().getFullAddress());

        // Choose view to show
        PersonChangeControllerHelper.ViewInfo viewInfo = null;
        if (ChangePersonType.CHANGE_REPRESENTATIVE_ADDRESS.equals(changeType) ||
                ChangePersonType.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS.equals(changeType) ||
                ChangePersonType.CHANGE_CORRESPONDENT_ADDRESS.equals(changeType)) {
            viewInfo = getView(ADDRESS);
        } else {
            viewInfo = getViewByRepresentativeType(form.getType());
        }

        ModelAndView model = new ModelAndView(viewInfo.getViewName());
        model.addObject(viewInfo.getFormName(), form);
        return model;
    }

}