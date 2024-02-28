/*******************************************************************************
 * * $Id:: RemoveController.java 49264 2012-10-29 13:23:34Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.OpposedTradeMarkForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.form.claim.ConversionForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Generic controller that could be used to remove an object from the collection
 * The request should contain the id of the object that we want to remove
 * 
 * @author ckara
 * 
 */
@Controller
public class RemoveController extends RemoveAbstractController {

	private static final Logger logger = Logger.getLogger(RemoveController.class);

	@Autowired
	private FlowBean flowBean;

	@Autowired
    private FileServiceInterface fileService;
	
	/* --------- PRIORITIES ---------- */
    /* ------------------------------- */

    /**
     * It returns the selected Priority object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the priority
     */
	@RequestMapping(value = "removePriority", method = RequestMethod.GET)
	public ModelAndView handleRemovePriority(@RequestParam(value = "id") String id,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
		
		return handle(id, flowBean, new RemoveParameters(
                PriorityForm.class, claimTable, claimTable, 
                claimTable));    	
	}	
	
	/* --------- SENIORITIES---------- */
    /* ------------------------------- */

    /**
     * It returns the selected Seniority object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the seniority
     */
	@RequestMapping(value = "removeSeniority", method = RequestMethod.GET)
	public ModelAndView handleRemoveSeniority(@RequestParam(value = "id") String id,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
		
		return handle(id, flowBean, new RemoveParameters(
                SeniorityForm.class, claimTable, claimTable, 
                claimTable));    	
	}	
	
	/* --- EXHIBITION PRIORITIES ----- */
    /* ------------------------------- */

    /**
     * It returns the selected Exhibition Priority object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the exhibition priority
     */
	@RequestMapping(value = "removeExhPriority", method = RequestMethod.GET)
	public ModelAndView handleRemoveExhPriority(@RequestParam(value = "id") String id,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
		return handle(id, flowBean, new RemoveParameters(
                ExhPriorityForm.class, claimTable, claimTable, 
                claimTable));    	
	}	
	
	/* ------- IR TRANSFORMATIONS ---- */
    /* ------------------------------- */

    /**
     * It returns the selected IR Transformation object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
	@RequestMapping(value = "removeTransformation", method = RequestMethod.GET)
	public ModelAndView handleRemoveTransformation(@RequestParam(value = "id") String id,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
		return handle(id, flowBean, new RemoveParameters(
                TransformationForm.class, claimTable, claimTable, 
                claimTable));    	
	}
	
	@RequestMapping(value = "removeConversion", method = RequestMethod.GET)
	public ModelAndView handleRemoveConversion(@RequestParam(value = "id") String id,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
		return handle(id, flowBean, new RemoveParameters(
                ConversionForm.class, claimTable, claimTable, 
                claimTable));    	
	}
	
	/* ---------- Trademaks---------- */
    /* ------------------------------- */

    /**
     * Removes the selected trademark from the list of the trademarks of the application
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *    if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeTMDetails", method = RequestMethod.GET)
    public ModelAndView handleRemoveTM(@RequestParam(value = "id") String id) {
        TMDetailsForm tmDetailsForm = flowBean.getObject(TMDetailsForm.class, id);
        ModelAndView mav = handle(id, flowBean, new RemoveParameters(
                TMDetailsForm.class, "tmDetailsForm", "tm_details/tm_card_list",
                "tm-section/tm_failure"));
        if(!flowBean.existsObject(TMDetailsForm.class, id) && tmDetailsForm != null && tmDetailsForm.getRepresentationAttachment() != null &&
            tmDetailsForm.getRepresentationAttachment().getStoredFiles() != null && tmDetailsForm.getRepresentationAttachment().getStoredFiles().size()>0 &&
                tmDetailsForm.getRepresentationAttachment().getStoredFiles().get(0).getDocumentId() != null){
            fileService.removeFile(tmDetailsForm.getRepresentationAttachment().getStoredFiles().get(0));
        }
        return mav;
    }

    @RequestMapping(value = "removeAllDSDetails", method = RequestMethod.GET)
    public ModelAndView handleRemoveAllDS() {
        ModelAndView mav = new ModelAndView("designs/ds_card_list");
        Collection<ESDesignDetailsForm> dsForms = flowBean.getCollection(ESDesignDetailsForm.class);
        if(dsForms != null) {
            List<StoredFile> dsStoredFiles = new ArrayList<>();
            dsForms.stream().filter(ds -> ds.getRepresentationAttachment() != null &&
                    ds.getRepresentationAttachment().getStoredFiles() != null && ds.getRepresentationAttachment().getStoredFiles().size()>0).
                    forEach(ds -> dsStoredFiles.addAll(ds.getRepresentationAttachment().getStoredFiles()));

            dsForms.clear();
            dsStoredFiles.stream().filter(st -> st.getDocumentId() != null).forEach(st -> fileService.removeFile(st));
        }

        return mav;
    }
    
    @RequestMapping(value = "removeDSDetails", method = RequestMethod.GET)
    public ModelAndView handleRemoveDS(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                ESDesignDetailsForm.class, "eSDesignDetailsForm", "designs/ds_card_list",
                "design/ds_failure"));
    }

    @RequestMapping(value = "removePTDetails", method = RequestMethod.GET)
    public ModelAndView handleRemovePT(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
            ESPatentDetailsForm.class, "patentDetailsForm", "pt_details/pt_card_list",
            "pt_details/pt_failure"));
    }
    
    @RequestMapping(value = "removeOpposedTradeMark", method = RequestMethod.GET)
    public ModelAndView handleRemoveTMO(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                OpposedTradeMarkForm.class, "opposedTradeMarkForm", "opposition/tm_opposed_card_list",
                "tmo-section/tmo_failure"));
    }
    
    @RequestMapping(value = "removeOppositionBasis", method = RequestMethod.GET)
    public ModelAndView handleRemoveOB(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                OppositionBasisForm.class, "oppositionBasis", "opposition/opposition_basis_card_list",
                "ob-section/ob_failure"));
    }
    
	/* ---------- Applicants---------- */
    /* ------------------------------- */

    /**
     * It returns the selected Applicants object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeApplicant", method = RequestMethod.GET)
    public ModelAndView handleRemoveApplicant(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                ApplicantForm.class, "applicantForm", "applicant/applicant_card_list",
                "applicant/applicant_failure"));
    }

    @RequestMapping(value = "removeESDesigner", method = RequestMethod.GET)
    public ModelAndView handleRemoveDesigner(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                DesignerForm.class, "designerForm", "designer/designer_card_list",
                "designer/designer_failure"));
    }

    /* ------- Representatives ------- */
    /* ------------------------------- */

    /**
     * It returns the selected Applicants object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeRepresentative", method = RequestMethod.GET)
    public ModelAndView handleRemoveRepresentative(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                RepresentativeForm.class, "representativeForm", "representative/representative_card_list",
                "representative/representative_failure"));
    }
    
    /* ------- Assignees ------- */
    /* ------------------------------- */

    /**
     * It returns the selected Assignees object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeAssignee", method = RequestMethod.GET)
    public ModelAndView handleRemoveAssignee(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                AssigneeForm.class, "assigneeForm", "assignee/assignee_card_list",
                "assignee/assignee_failure"));
    }

    /* ------- Person change ------- */
    /* ------------------------------- */

    /**
     * It returns the selected Change of Person object by id
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     */
    @RequestMapping(value = "removePersonChange", method = RequestMethod.GET)
    public ModelAndView handleRemovePersonChange(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                ChangeRepresentativeNaturalPersonForm.class, "personChangeForm", "personChange/personChange_card_list",
                "personChange/personChange_failure"));
    }
    
    /* ------- Holders ------- */
    /* ------------------------------- */

    /**
     * It returns the selected Holders object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeHolder", method = RequestMethod.GET)
    public ModelAndView handleRemoveHolder(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                HolderForm.class, "holderForm", "holder/holder_card_list",
                "holder/holder_failure"));
    }
    
    /* ------- Signatures ------- */
    /* ------------------------------- */

    /**
     * It returns the selected Signatures object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeSignature", method = RequestMethod.GET)
    public ModelAndView handleRemoveSignature(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                SignatureForm.class, "signatureForm", "signature/signature_card_list",
                "signature/signature_failure"));
    }

    /**
     * It returns the selected CorrespondenceAddress object by id
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeApplicationCA", method = RequestMethod.GET)
    public ModelAndView handleRemoveApplicationCA(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(ApplicationCAForm.class, "applicationCAForm", "common/correspondent/applicationCA_card_list", "applicationCA/applicationCA_failure"));
    }

    @RequestMapping(value = "removeGSHelper", method = RequestMethod.GET)
    public ModelAndView handleRemoveGSHelper(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                GSHelperForm.class, "gshelper", "gshelper/gshelper_card_list", "gshelper/gshelper_failure"));
    }

    /* ------- Licence ------- */
    /* ------------------------------- */

    /**
     * It returns the selected Licences object by id
     *
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the Licence
     */
    @RequestMapping(value = "removeLicence", method = RequestMethod.GET)
    public ModelAndView handleRemoveLicence(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                LicenceForm.class, "licence", "licence/licence_card_list", "licence/licence_failure"));
    }

    @RequestMapping(value = "removeAppeal", method = RequestMethod.GET)
    public ModelAndView handleRemoveAppeal(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(
                AppealForm.class, "appeal", "appeal/appeal_card_list", "appeal/appeal_failure"));
    }

}
