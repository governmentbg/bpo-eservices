/*******************************************************************************
 * * $Id:: PriorityFactory.java 113496 2013-04-22 15:03:04Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.ClassDescription;

/**
 * Factory that converts between UI {@link PriorityForm} object and core
 * {@link Priority} object.
 * 
 * @author karalch
 * 
 */
@Component
public class PriorityFactory implements UIFactory<Priority, PriorityForm> {

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;

	@Autowired
	private GoodsServicesFactory goodsServicesFactory;

	/**
	 * Receives as a parameter a UI {@link PriorityForm} object and converts it
	 * to a core {@link Priority} object.
	 * 
	 * @param form
	 *            the UI PriorityForm to convert
	 * @return the core Priority object
	 */
	@Override
	public Priority convertTo(PriorityForm form) {
		Priority priority = new Priority();

		priority.setFilingDate(form.getDate());
		priority.setFilingNumber(form.getNumber());
		priority.setFilingOffice(form.getCountry());
		
		priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		priority.getAttachedDocuments().addAll(
				listAttachedDocumentFactory.convertTo(form.getFileWrapperCopy()));
		priority.getAttachedDocuments().addAll(
				listAttachedDocumentFactory.convertTo(form.getFileWrapperTranslation()));
		priority.getAttachedDocuments().addAll(
				listAttachedDocumentFactory.convertTo(form.getFilePriorityCertificate()));
	
		if (form.getGoodsServices()!=null) {
			priority.setPartialGoodsServices(new LinkedList<>());
			for (GoodAndServiceForm gsForm : form.getGoodsServices()) {
                ClassDescription classDescription = goodsServicesFactory.convertPartialGSTo(gsForm);
				priority.getPartialGoodsServices().add(classDescription);
			}
		}
		
		priority.setPartialIndicator(!(form.getGoodsServices() == null || form.getGoodsServices().size() == 0));

		//priority.setImported(form.getImported());
		return priority;
	}

	/**
	 * Receives as a parameter a core {@link Priority} object and converts it to
	 * a {@link PriorityForm} object.
	 * 
	 * @param core
	 *            the priority to convert
	 * @return the priority form object
	 */
	@Override
	public PriorityForm convertFrom(Priority core) {
		if (core == null) {
			return new PriorityForm();
		}

		PriorityForm form = new PriorityForm();

		form.setDate(core.getFilingDate());
		form.setNumber(core.getFilingNumber());
		form.setCountry(core.getFilingOffice());
		if (core.getAttachedDocuments() != null && !core.getAttachedDocuments().isEmpty()) {
			form.setFileWrapperCopy(listAttachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
			form.getFileWrapperCopy().setAttachment(true);
		}

		if (core.getPartialGoodsServices() != null && !core.getPartialGoodsServices().isEmpty()) {
			form.setGoodsServices(new ArrayList<GoodAndServiceForm>());
			for (ClassDescription classDescription : core.getPartialGoodsServices()) {
				form.getGoodsServices().add(goodsServicesFactory.convertPartialGSFrom(classDescription));
			}
		}
		
		//form.setImported(core.isImported());
		
		return form;
	}

}
