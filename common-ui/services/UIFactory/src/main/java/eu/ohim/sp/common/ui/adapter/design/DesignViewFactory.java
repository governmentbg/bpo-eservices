package eu.ohim.sp.common.ui.adapter.design;

import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.AttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.design.DesignView;

/**
 * Factory for design views.
 * @author serrajo
 */
@Component
public class DesignViewFactory implements UIFactory<DesignView, DesignViewForm> {

	@Autowired
	private AttachedDocumentFactory fileFactory;

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;
	
	@Autowired
	private DocumentFactory documentFactory;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DesignView convertTo(DesignViewForm form) {
		DesignView core = new DesignView();
		
		if (form != null) {
			core.setSequence(form.getSequence());
			core.setDescription(form.getDescription());
			core.setPublishInColour(form.isPublishInColour());
			core.setPublishInBlackWhite(form.isPublishInBlackWhite());
			core.setPublicationSize(form.getPublicationSize());
			core.setType(form.getType());
			if (form.getView() != null && CollectionUtils.isNotEmpty(form.getView().getStoredFiles())) {
				StoredFile storedFile = form.getView().getStoredFiles().get(0);
				core.setView(fileFactory.convertTo(storedFile));
				core.setViewThumbnail(fileFactory.convertTo(storedFile.getThumbnail()));
			}
		}

		return core;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DesignViewForm convertFrom(DesignView core) {
		DesignViewForm form = new DesignViewForm();

		if (core != null) {
			form.setSequence(core.getSequence());
			form.setDescription(core.getDescription());
			form.setPublishInColour(core.isPublishInColour());
			form.setPublishInBlackWhite(core.isPublishInBlackWhite());
			form.setPublicationSize(core.getPublicationSize());
			form.setType(core.getType());
			if (core.getView() != null) {
				FileWrapper fileWrapper = listAttachedDocumentFactory.convertFrom(Arrays.asList(core.getView())); 
				form.setView(fileWrapper);
				
				if (fileWrapper != null && core.getViewThumbnail() != null && core.getViewThumbnail().getDocument() != null) {
					fileWrapper.getStoredFiles().get(0).setThumbnail(documentFactory.convertFrom(core.getViewThumbnail().getDocument()));
				}	
			}
			
		}
		
		return form;
	}

}
