package eu.ohim.sp.common.ui.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.application.EntitlementForm;
import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.EntitlementKind;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * Factory for entitlement to apply.
 */
@Component
public class EntitlementFactory implements UIFactory<Entitlement, EntitlementForm>{

	@Autowired
	private ListAttachedDocumentFactory attachedDocumentFactory;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entitlement convertTo(EntitlementForm form) {
		Entitlement toReturn = new Entitlement();
		if (form.isDesignOfficiary()) {
			toReturn.setKind(EntitlementKind.DESIGN_IS_OFFICIARY);
			if (form.getDesignOfficiaryFiles() != null) {
				List<AttachedDocument> documents = attachedDocumentFactory.convertTo(form.getDesignOfficiaryFiles());
				toReturn.setAttachedDocuments(documents);
			}
		} else if (form.isDesignNotOfficiary()) {
			toReturn.setKind(EntitlementKind.DESIGN_IS_NOT_OFFICIARY);
			if (form.getDesignNotOfficiaryFiles() != null) {
				List<AttachedDocument> documents = attachedDocumentFactory.convertTo(form.getDesignNotOfficiaryFiles());
				toReturn.setAttachedDocuments(documents);
			}
		} else if (form.isAccordingToAssociationToCompany()) {
			toReturn.setKind(EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY);
		} else if (form.isDueToSucession()) {
			toReturn.setKind(EntitlementKind.DUE_TO_SUCESSION);
		} else if (form.isNotApplicantsWithWaived()) {
			toReturn.setKind(EntitlementKind.NOT_APPLICANTS_WITH_WAIVED);
		} else if (form.isOtherGrounds()) {
			toReturn.setKind(EntitlementKind.OTHER_GROUNDS);
			toReturn.setDescription(form.getOtherGroundsDescription());
		} else if (form.isTransferOfRights()) {
			toReturn.setKind(EntitlementKind.TRANSFER_OF_RIGHTS);
			toReturn.setDateOfTransfer(form.getDateOfTransfer());
		}
		return toReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public EntitlementForm convertFrom(Entitlement core) {
		EntitlementForm form = new EntitlementForm();
		if (core.getKind() != null){
			switch (core.getKind()) {
				case DESIGN_IS_OFFICIARY:
					form.setDesignOfficiary(true);
					form.setDesignOfficiaryFiles(attachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
					break;
				case DESIGN_IS_NOT_OFFICIARY:
					form.setDesignNotOfficiary(true);
					form.setDesignNotOfficiaryFiles(attachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
					break;		
				case ACCORDING_TO_ASSOCIATION_TO_COMPANY:
					form.setAccordingToAssociationToCompany(true);
					break;	
				case DUE_TO_SUCESSION:
					form.setDueToSucession(true);
					break;	
				case NOT_APPLICANTS_WITH_WAIVED:
					form.setNotApplicantsWithWaived(true);
					break;	
				case TRANSFER_OF_RIGHTS:
					form.setTransferOfRights(true);
					form.setDateOfTransfer(core.getDateOfTransfer());
					break;
				case OTHER_GROUNDS:
					form.setOtherGrounds(true);
					form.setOtherGroundsDescription(core.getDescription());
					break;
			}
		}
		return form;
	}

}
