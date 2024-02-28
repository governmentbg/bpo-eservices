package eu.ohim.sp.common.ui.form.design;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class DesignViewForm extends AbstractImportableForm implements DSDesignForm {

	private static final long serialVersionUID = -5962471880982730290L;

	private int sequence;
	private String description;
	private boolean publishInColour;
	private boolean publishInBlackWhite;
	private String publicationSize;
	private String type;
	
	private FileWrapper view;
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublishInColour() {
		return publishInColour;
	}

	public void setPublishInColour(boolean publishInColour) {
		this.publishInColour = publishInColour;
	}

	public boolean isPublishInBlackWhite() {
		return publishInBlackWhite;
	}

	public void setPublishInBlackWhite(boolean publishInBlackWhite) {
		this.publishInBlackWhite = publishInBlackWhite;
	}

	public String getPublicationSize() {
		return publicationSize;
	}

	public void setPublicationSize(String publicationSize) {
		this.publicationSize = publicationSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
     * Get the view
     *
     * @return the view
     */
    public FileWrapper getView() {
        return view;
    }

    /**
     * Set the view
     *
     * @param view the view
     */
    public void setView(FileWrapper view) {
        this.view = view;
    }
    
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.DESIGN_VIEWS;
	}

	@Override
	public AbstractForm clone() {
		DesignViewForm designViewForm = new DesignViewForm();
		designViewForm.setId(id);
		designViewForm.setImported(getImported());
		designViewForm.setSequence(sequence);
		designViewForm.setDescription(description);
		designViewForm.setPublishInColour(publishInColour);
		designViewForm.setPublishInBlackWhite(publishInBlackWhite);
		designViewForm.setPublicationSize(publicationSize);
		designViewForm.setType(type);
		designViewForm.setView(view);
		return designViewForm;
	}

}
