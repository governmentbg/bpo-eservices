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

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller in charge of handling the adding and the editing of the type of change.
 *
 * @author velosma
 */
@Controller
public class AddChangeRequestTypeController extends AddAbstractController {

    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;

    @RequestMapping(value = "addChangeRequestType", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void addChangeRequestType(@RequestParam(required = false) String changeType) {

        flowBean.setChangeType(changeType);

    }

}