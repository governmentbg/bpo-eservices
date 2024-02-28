package eu.ohim.sp.dsefiling.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.design.DesignFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignViewFactory;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.common.ui.controller.AddApplicationCAController;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Manage the addition and removal of designs with the objects that contain them in the flowBean, like DesignerForm.
 * @author serrajo
 */
@Service(value="importDesignsService")
public class DSImportDesignsService implements DSImportDesignsServiceInterface {

	private static final Logger LOGGER = Logger.getLogger(AddApplicationCAController.class);
	
    @Autowired
    private DesignSearchService designService;

    @Autowired
    private DSDesignsServiceInterface dsDesignsService;
    
	@Autowired
	private DesignFactory designFactory;
	
    @Autowired
    private DSFlowBeanFactory dsFlowBeanFactory;

	@Autowired
	private DesignViewFactory designViewFactory;	

    @Autowired
    private DSFileService fileService;

    
	@Value("${sp.office}")
    private String office;

	/**
	 * {@inheritDoc}
	 */
    @Override
    public DesignForm importDesign(String designId, DSFlowBean flowBean) {
    	Design design = designService.getDesign(office, designId);
    	return importDesign(design, flowBean);
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public DesignForm importDesign(Design design, DSFlowBean flowBean) {
    	DesignForm designForm = designFactory.convertFrom(design);
        if(designForm == null)
        {
            return null;
        }
    	designForm.getViews().clear();

    	for (DesignView designView : design.getViews()) {
    		try {
    			Document document = designView.getView().getDocument();
    			if (document != null && document.getData() != null) {
    				DesignViewForm designViewForm = designViewFactory.convertFrom(designView);
					String filename = (document.getFileName()!=null) ? document.getFileName() : String.valueOf(UUID.randomUUID());
    				StoredFile storedFile = fileService.addFile(flowBean.getIdreserventity(), filename, document.getFileFormat(), document.getData(), true);
    				FileWrapper fileWrapper = new FileWrapper();
    				fileWrapper.getStoredFiles().add(storedFile);
    				designViewForm.setView(fileWrapper);
    				designForm.getViews().add(designViewForm);
    			}
    		} catch(IOException ioe) {
    			LOGGER.error("Error importing design view for design");
    			return null;
    		}
    	}
    	return designForm;
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void importDesignApplication(String designId, String applicationId, DSFlowBean flowBean) {
    	String idreserventity = flowBean.getIdreserventity();
    	String firstLang = flowBean.getFirstLang();
		flowBean.clear();
		DesignApplication designApplication = null;
		try{
			designApplication = designService.getDesignApplication(office, designId, applicationId, true);
		}catch(Exception exp){
			throw  new SPException("Error while importing design", exp, "general.messages.design.cannotImport");
		}

		if(designApplication == null || designApplication.getApplicationNumber() == null){ //not found
			throw new NoResultsException("Design not found", null, "error.import.noObjectFound.design");
		}
		DSFlowBean flowBeanImported = dsFlowBeanFactory.convertFrom(designApplication);

		flowBean.setIdreserventity(idreserventity);
		flowBean.setFirstLang(firstLang);
		flowBean.addAll(flowBeanImported.getApplicants());
		flowBean.addAll(flowBeanImported.getRepresentatives());
		flowBean.addAll(flowBeanImported.getDesigners());

		DesignForm designForm;
		for (Design design : designApplication.getDesignDetails()){
			designForm = importDesign(design, flowBean);
			if (designForm != null) {
				flowBean.addObject(designForm);
				dsDesignsService.addDesignToLists(designForm, flowBean);
			}
		}
	}
    public DSPriorityForm importPriority(String designId,String office, DSFlowBean flowBean) {
        DesignApplication designApplication = designService.getDesignApplication(office, designId, null, true);
        Priority priority = new Priority();
        if(designApplication != null){
            priority.setFilingDate(designApplication.getApplicationDate());
            priority.setFilingNumber(designApplication.getApplicationNumber());
            priority.setFilingOffice(office);
            if(designApplication.getApplicants() != null && designApplication.getApplicants().size()>0 ){
                PersonName applicantName = designApplication.getApplicants().get(0).getName();
                if(applicantName.getOrganizationName() == null || ("").equals(applicantName.getOrganizationName())){
                    priority.setApplicantName(new StringBuffer(applicantName.getFirstName() != null ? applicantName.getFirstName() : "")
                        .append(" ").append(applicantName.getMiddleName() != null ? applicantName.getMiddleName() : "").append(" ")
                        .append(applicantName.getLastName() != null ? applicantName.getLastName() : "").toString());
                }else{
                    priority.setApplicantName(applicantName.getOrganizationName());
                }
            }
            List<Priority> priorities = new ArrayList<Priority>(1);
            priorities.add(priority);
            List<PriorityForm> prioritiesForm = dsFlowBeanFactory.coreToPriorityForms(priorities);
            List<DSPriorityForm> dsPriorityForms = new ArrayList<DSPriorityForm>(1);
            java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat("dd/MM/yyyy");
            for (PriorityForm p : prioritiesForm) {
                DSPriorityForm p_aux = ((DSPriorityForm) p);
                p_aux.setDesignsLinked(flowBean.getDesigns());
                p_aux.setDateStringFormat(p_aux.getDate() != null ? sdf.format(p_aux.getDate()) : "");
                dsPriorityForms.add(p_aux);
            }
            if (dsPriorityForms != null && dsPriorityForms.size() > 0) {
                return dsPriorityForms.get(0);
            } else {
                return null;
            }
        }
        return null;
    }
}
