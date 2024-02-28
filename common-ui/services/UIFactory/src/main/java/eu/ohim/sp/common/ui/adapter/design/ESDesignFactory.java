package eu.ohim.sp.common.ui.adapter.design;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.ESDesignApplicationDataForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.Document;

@Component
public class ESDesignFactory implements UIFactory<DesignApplication, ESDesignDetailsForm> {
	
    private final String MIME_IMAGE_PNG = "image/png";	
	@Autowired
	private DesignFactory designFactory;
	
	@Autowired
	private ApplicantFactory applicantFactory;
	
	@Autowired
	private RepresentativeFactory representativeFactory;
	
	@Autowired
	private DesignerFactory designerFactory;
	
	@Autowired
	private FileServiceInterface fileService;
	
	@Override
	public DesignApplication convertTo(ESDesignDetailsForm form) {
		DesignApplication core = new DesignApplication();

		if (form != null) {
			form.setViews(new ArrayList<DesignViewForm>());
			if(form.getRepresentationAttachment() != null && form.getRepresentationAttachment().getStoredFiles() != null){
				for(StoredFile stf: form.getRepresentationAttachment().getStoredFiles()){
					DesignViewForm designView = new DesignViewForm();
					designView.setView(new FileWrapper());
					designView.getView().setStoredFiles(Arrays.asList(stf));
					form.getViews().add(designView);
				}
			}

			Design design = designFactory.convertTo(form);
			design.setSelected(form.getSelected());
			design.setUnpublished(form.getUnpublished());
			design.setDesignIdentifier(form.getDesignIdentifier());
			design.setRegistrationNumber(form.getRegistrationNumber());
			design.setRegistrationDate(form.getRegistrationDate());
			if (form.getRenewalIndicator()!=null && form.getRenewalIndicator()) {
                if (form.getNumberOfRenewals() > 1) {
                       design.setNumberOfRenewals(form.getNumberOfRenewals());
                } else {
                       design.setNumberOfRenewals(1);
                }
			}
			else {
				design.setNumberOfRenewals(0);
			}
			design.setExpiryDate(form.getExpiryDate());
			if(form.getDsStatus() != null){
				switch (form.getDsStatus()){
					case EXPIRED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.EXPIRED);break;
					case CANCELLED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.CANCELLED);break;
					case FILED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.FILED);break;
					case REGISTERED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.REGISTERED);break;
					case REJECTED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.REJECTED);break;
					case UNDEFINED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.UNDEFINED);break;
					case WITHDRAWN:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.WITHDRAWN);break;
					case ENDED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.ENDED);break;
					case REGISTERED_AND_FULLY_PUBLISHED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.REGISTERED_AND_FULLY_PUBLISHED);break;
					case REGISTERED_AND_SUBJECT_TO_DEFERMENT:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.REGISTERED_AND_SUBJECT_TO_DEFERMENT);break;
					case LACK_OF_EFFECTS:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.LACK_OF_EFFECTS);break;
					case DESIGN_SURRENDERED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.DESIGN_SURRENDERED);break;
					case INVALIDITY_PROCEDURE_PENDING:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.INVALIDITY_PROCEDURE_PENDING);break;
					case DESIGN_DECLARED_INVALID:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.DESIGN_DECLARED_INVALID);break;
					case DESIGN_LAPSED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.DESIGN_LAPSED);break;
					case EXPIRING:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.EXPIRING);break;
					case APPLICATION_PUBLISHED:
						design.setCurrentStatus(eu.ohim.sp.core.domain.design.DesignStatusCode.APPLICATION_PUBLISHED);break;
				}
			}
			core.setDesignDetails(new ArrayList<Design>());
			core.getDesignDetails().add(design);
			if (form.geteSDesignApplicationData()!=null){
				design.setApplicationNumber(form.geteSDesignApplicationData().getApplicationNumber());
				core.setApplicationNumber(form.geteSDesignApplicationData().getApplicationNumber());
				core.setApplicationDate(form.geteSDesignApplicationData().getApplicationDate());
				core.setPublicationDate(form.geteSDesignApplicationData().getPublicationDate());
				core.setApplicants(new ArrayList<Applicant>());
				core.setRepresentatives(new ArrayList<Representative>());
				core.setDesigners(new ArrayList<Designer>());
				if(form.geteSDesignApplicationData().getHolders() != null){
					for (ApplicantForm holder:form.geteSDesignApplicationData().getHolders()){
						if(holder.getType() == null) {
							holder.setType(ApplicantKindForm.NATURAL_PERSON);
						}
						Applicant applicant = applicantFactory.convertTo(holder);
						if(applicant!=null) {
							applicant.setDomicileLocality(holder.getDomicile());
						}
						core.getApplicants().add(applicant);
					}
				}
//				if(form.geteSDesignApplicationData().getRepresentatives() != null){
//					for (RepresentativeForm representativeForm:form.geteSDesignApplicationData().getRepresentatives()){
//						Representative representative = representativeFactory.convertTo(representativeForm);
//						core.getRepresentatives().add(representative);
//					}
//				}
				if(form.geteSDesignApplicationData().getDesigners() != null){
					for (DesignerForm designerForm:form.geteSDesignApplicationData().getDesigners()){
						Designer designer = designerFactory.convertTo(designerForm);
						core.getDesigners().add(designer);
					}	
				}
			}		
		}
		return core;
	}

	@Override
	public ESDesignDetailsForm convertFrom(DesignApplication core) {
		ESDesignDetailsForm esform = new ESDesignDetailsForm();

		if (core != null) {
			if (core.getDesignDetails()!=null && core.getDesignDetails().size()>0){
				Design designCore = core.getDesignDetails().get(0);
				esform = populateESDesignFormFromDesign(designCore, core);
				if(designCore.getSelected() != null){
					esform.setSelected(designCore.getSelected().booleanValue());
				}
				esform.setUnpublished(designCore.getUnpublished());
			}
		}
		return esform;
	}
	
	
	public List<ESDesignDetailsForm> convertListFromSingle(DesignApplication core) {
		List<ESDesignDetailsForm> result = new ArrayList<>();
		if(core == null || core.getDesignDetails() == null ||core.getDesignDetails().size() ==0 ){
			return result;
		}
		
		for(Design designCore: core.getDesignDetails()){
			ESDesignDetailsForm esForm = populateESDesignFormFromDesign(designCore, core);
			result.add(esForm);		
			if(designCore.getViews() != null && designCore.getViews().size() > 0){
				List<StoredFile> storedFilesList = new ArrayList<StoredFile>();

				for(DesignView view: designCore.getViews()){
					Document doc = view.getView().getDocument();
					if(doc!= null){
						StoredFile storedFile = new StoredFile();
						storedFile.setOriginalFilename(doc.getUri());
						storedFilesList.add(storedFile);
					}
				}
				FileWrapper imgWraper = new FileWrapper();
				imgWraper.setStoredFiles(storedFilesList);
				esForm.setRepresentationAttachment(imgWraper);
			}
		}

		// Add representatives of the design application to the first result
		if (result.size() > 0 && core.getRepresentatives() != null) {
			result.get(0).setRepresentatives(new ArrayList<>());
			for (Representative representative : core.getRepresentatives()) {
				result.get(0).getRepresentatives().add(representativeFactory.convertFrom(representative));
			}
		}
		
		return result;
	}
	
	private ESDesignDetailsForm populateESDesignFormFromDesign(Design designCore, DesignApplication core){
		ESDesignDetailsForm esform = new ESDesignDetailsForm();
		DesignForm form = designFactory.convertFrom(designCore);
		if (form.getViews()!=null && form.getViews().size()>0){
			esform.setRepresentationAttachment(form.getViews().get(0).getView());
			
			ArrayList<StoredFile> storedFiles = new ArrayList<StoredFile>();
			String documentId = "";
			int i = 0;
			for(DesignView view : designCore.getViews()) {
				i++;
				StoredFile sf = new StoredFile();
				sf.setDescription(view.getDescription());
				if (view.getView()!=null && view.getView().getDocument()!=null ){
					if(designCore.getUnpublished() != null && designCore.getUnpublished()) {
						sf.setContentType(view.getView().getDocument().getFileFormat());
						sf.setFilename(view.getView().getDocument().getName());
						sf.setOriginalFilename(view.getView().getDocument().getFileName());
						sf.setFileSize(view.getView().getDocument().getFileSize());
						documentId = view.getView().getDocument().getDocumentId();
					} else {
						sf.setContentType(view.getView().getDocument().getFileFormat());
						if(sf.getContentType() == null){
							sf.setContentType(MIME_IMAGE_PNG);
						}
						sf.setFilename(view.getView().getDocument().getUri());
						sf.setOriginalFilename(view.getView().getDocument().getUri());
						documentId= view.getView().getDocument().getDocumentId();
					}
				}
				sf.setDocumentId(documentId);
				storedFiles.add(sf);
			}
			if (esform.getRepresentationAttachment()!=null){
				esform.getRepresentationAttachment().setStoredFiles(storedFiles);
			}
		}								
		esform.setLocarno(form.getLocarno());
		esform.setDesignIdentifier(designCore.getDesignIdentifier());
		esform.setRegistrationNumber(designCore.getRegistrationNumber());
		esform.setRegistrationDate(designCore.getRegistrationDate());
		esform.setExpiryDate(designCore.getExpiryDate());
		if (designCore.getNumberOfRenewals()>0){
			esform.setRenewalIndicator(Boolean.TRUE);
		}
		else
			esform.setRenewalIndicator(Boolean.FALSE);
		
		if (designCore.getCurrentStatus()!=null){
			switch (designCore.getCurrentStatus()){
				case EXPIRED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.EXPIRED);break;
				case CANCELLED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.CANCELLED);break;
				case FILED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.FILED);break;
				case REGISTERED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.REGISTERED);break;
				case REJECTED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.REJECTED);break;
				case UNDEFINED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.UNDEFINED);break;
				case WITHDRAWN:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.WITHDRAWN);break;
				case ENDED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.ENDED);break;
				case REGISTERED_AND_FULLY_PUBLISHED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.REGISTERED_AND_FULLY_PUBLISHED);break;
				case REGISTERED_AND_SUBJECT_TO_DEFERMENT:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.REGISTERED_AND_SUBJECT_TO_DEFERMENT);break;
				case LACK_OF_EFFECTS:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.LACK_OF_EFFECTS);break;
				case DESIGN_SURRENDERED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.DESIGN_SURRENDERED);break;
				case INVALIDITY_PROCEDURE_PENDING:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.INVALIDITY_PROCEDURE_PENDING);break;
				case DESIGN_DECLARED_INVALID:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.DESIGN_DECLARED_INVALID);break;
				case DESIGN_LAPSED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.DESIGN_LAPSED);break;
				case EXPIRING:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.EXPIRING);break;
				case APPLICATION_PUBLISHED:
					esform.setDsStatus(eu.ohim.sp.common.ui.form.design.DesignStatusCode.APPLICATION_PUBLISHED);break;
			}
		}
		esform.setNumberOfRenewals(designCore.getNumberOfRenewals());
		esform.seteSDesignApplicationData(new ESDesignApplicationDataForm());
        esform.geteSDesignApplicationData().setApplicantST13(core.getApplicantURI());
        esform.geteSDesignApplicationData().setRepresentativeST13(core.getRepresentativeURI());
		esform.geteSDesignApplicationData().setApplicationNumber(designCore.getApplicationNumber());
		esform.geteSDesignApplicationData().setApplicationDate(core.getApplicationDate());
		esform.geteSDesignApplicationData().setPublicationDate(core.getPublicationDate());
		esform.geteSDesignApplicationData().setHolders(new ArrayList<ApplicantForm>());
		esform.geteSDesignApplicationData().setRepresentatives(new ArrayList<RepresentativeForm>());
		esform.geteSDesignApplicationData().setDesigners(new ArrayList<DesignerForm>());
		if(core.getApplicants() != null){
			for (Applicant holder:core.getApplicants()){
				ApplicantForm applicant = applicantFactory.convertFrom(holder);
				
				if (applicant!=null){	
					if(("".equals(applicant.getName()) || applicant.getName()==null) && holder.getName()!=null) {
						((NaturalPersonForm)applicant).setFirstName(holder.getName().getOrganizationName());
					}
					if(applicant.getAddress() != null){
						StringBuilder infoString = new StringBuilder();
						infoString.append(applicant.getAddress().getStreet() == null? "" : applicant.getAddress().getStreet().trim());
						infoString.append(applicant.getAddress().getHouseNumber() == null? "" : ", "+applicant.getAddress().getHouseNumber().trim());
						infoString.append(applicant.getAddress().getPostalCode() == null? "" : ".\n"+applicant.getAddress().getPostalCode().trim());
						infoString.append(applicant.getAddress().getCity() == null? "" : ".\n"+applicant.getAddress().getCity().trim());
                        infoString.append(applicant.getAddress().getStateprovince() == null? "" : applicant.getAddress().getStateprovince().trim());
						infoString.append(applicant.getAddress().getCountry() == null? "" : " - "+applicant.getAddress().getCountry().trim());
						if(infoString.length() > 0) {
							infoString.append(".");
						} else {
							infoString.append(holder.getDomicileLocality() == null? "" : holder.getDomicileLocality());
						}
						applicant.setDomicile(infoString.toString());
					}						
				}					
				
				esform.geteSDesignApplicationData().getHolders().add(applicant);
			}
		}
		if(core.getRepresentatives() != null){
			for (Representative representative:core.getRepresentatives()){
				RepresentativeForm representativeForm = representativeFactory.convertFrom(representative);
				esform.geteSDesignApplicationData().getRepresentatives().add(representativeForm);
			}
		}
		if (core.getDesigners()!=null){
			for (Designer designer:core.getDesigners()){
				DesignerForm designerForm = designerFactory.convertFrom(designer);
				esform.geteSDesignApplicationData().getDesigners().add(designerForm);
			}	
		}
		
		return esform;
	}
}
