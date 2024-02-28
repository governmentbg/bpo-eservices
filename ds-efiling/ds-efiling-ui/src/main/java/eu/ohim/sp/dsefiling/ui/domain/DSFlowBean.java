package eu.ohim.sp.dsefiling.ui.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.common.ui.flow.section.*;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

public interface DSFlowBean extends FeesFlowBean, FileUploadFlowBean,
		LanguagesFlowBean, PaymentFlowBean, PersonFlowBean, DesignFlowBean, SignaturesFlowBean, FastTrackBean {

	/**
	 * Returns the main form that is stored in the session
	 * 
	 * @return the mainForm
	 */
	@Override
	DSMainForm getMainForm();

	/**
	 * Stores the main form
	 * 
	 * @param mainForm
	 *            the mainForm to set
	 */
	void setMainForm(DSMainForm mainForm);
	
	/**
	 * Clear the views of a design.
	 */
	void clearDesignViews();
	
	/**
	 * Retrieves all the stored views.
	 * 
	 * @return All the stored views.
	 */
	List<DesignViewForm> getDesignViews();

	/**
	 * Clear the designers.
	 */
	void clearDesigners();

	/**
	 * Retrieves all the stored designers.
	 * 
	 * @return All the stored designers.
	 */
	List<DesignerForm> getDesigners();

	/**
	 * 
	 * @return
	 */
	List<DesignerForm> getAddedDesigners();

	/**
	 * 
	 * @return
	 */
	List<DesignerForm> getUserDataDesigners();

	/**
	 * 
	 */
	void clearLocarno();

	/**
	 * 
	 * @return
	 */
	List<LocarnoAbstractForm> getLocarno();
	
	/**
	 * 
	 * @param locarno
	 */
	void setLocarno(List<LocarnoAbstractForm> locarno); 
	
	/**
	 * 
	 * @return
	 */
	Map<String, String> getDesignersFromApplicants();

	/**
	 * 
	 * @return
	 */
	void setLocarnoClasses(List<LocarnoClass> locarnoClasses);
	
	/**
	 * 
	 * @return
	 */
	List<LocarnoClass> getLocarnoClasses();

	/**
	 * 
	 * @param subclasses
	 */
	void setLocarnoSubclasses(List<LocarnoClass> subclasses);

	/**
	 * 
	 * @return
	 */
	List<LocarnoClass> getLocarnoSubclasses();

	/**
	 * Return all de locarno classification.
	 * 
	 * @return
	 */
	Map<String, LocarnoClassification> getLocarnoClassifications();

	/**
	 * 
	 * @param classifications
	 */
	void setLocarnoClassifications(Map<String, LocarnoClassification> classifications);

	/**
	 * 
	 * @return
	 */
	Collection<LocarnoClassification> getLocarnoClassificationsCollection();

	/**
	 * 
	 * @return
	 */
	DSDivisionalApplicationForm getDivisionalApplication();
	
	/**
	 * 
	 * @return
	 */
	List<DSDivisionalApplicationForm> getDivisionalApplications();
	
	/**
	 * 
	 * @return
	 */
	List<DSPriorityForm> getPriorities();
	
	/**
	 * 
	 * @return
	 */
	List<DSExhPriorityForm> getExhpriorities();
	
	/**
	 * Gets other attachments
	 * 
	 * @return FileWrapper other attachments
	 */
	FileWrapper getOtherAttachments();

	/**
	 * Sets other attachments
	 * 
	 * @param otherAttachments
	 */
	void setOtherAttachments(FileWrapper otherAttachments);
	
	/**
	 * 
	 */
	void clearDivisionalApplications();
	
	/**
	 * 
	 */
	void clearPriorities();

	/**
	 * 
	 */
	void clearExhibitions();
	
	/**
	 * 
	 */
	void clearApplicationCA();
	
	/**
	 * 
	 */
	void clearDesignersFromApplicants();
	
	/**
	 * 
	 * @return
	 */
	List<PersonForm> getPersons();

	String getComment() ;

	void setComment(String comment);

	Boolean getWillPayOnline();
	void setWillPayOnline(Boolean willPayOnline);
	
}
