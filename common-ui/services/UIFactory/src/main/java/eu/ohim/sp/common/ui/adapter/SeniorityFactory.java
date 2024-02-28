/*******************************************************************************
 * * $Id:: SeniorityFactory.java 113496 2013-04-22 15:03:04Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * Factory that converts between UI {@link SeniorityForm} object 
 * and core {@link Seniority} object.
 * @author karalch
 *
 */
@Component
public class SeniorityFactory implements UIFactory<Seniority, SeniorityForm> {

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;

    /**
     * Receives as a parameter a UI {@link SeniorityForm} object and converts it to a
     * core {@link Seniority} object.
     *
     * @param form the UI SeniorityForm to convert
     * @return the core Seniority object
     */
	@Override
	public Seniority convertTo(SeniorityForm form) {
        Seniority seniority = new Seniority();

        seniority.setKind(EnumAdapter.formSeniorityKindToCoreSeniorityKind(form.getNature()));
        seniority.setOffice(form.getCountry());
        seniority.setRegistrationDate(form.getPriorityDate());
        seniority.setFilingDate(form.getFilingDate());
        seniority.setRegistrationNumber(form.getFilingNumber());
        seniority.setRegistrationDate(form.getRegistrationDate());
        seniority.setRegistrationNumber(form.getRegistrationNumber());

        seniority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        seniority.getAttachedDocuments().addAll(listAttachedDocumentFactory.convertTo(form.getFileWrapperCopy()));
        seniority.getAttachedDocuments().addAll(
        		listAttachedDocumentFactory.convertTo(form.getFileWrapperTranslation()));
        seniority.getAttachedDocuments().addAll(
        		listAttachedDocumentFactory.convertTo(form.getFileSeniorityCertificate()));

        //seniority.setImported(form.getImported());
        return seniority;
	}

	/**
	 * Receives as a parameter a core {@link Seniority} object and converts it to a
	 * {@link SeniorityForm} object.
	 *
	 * @param seniority the seniority to convert
	 * @return the seniority form object
	 */
	@Override
	public SeniorityForm convertFrom(Seniority seniority) {
		if (seniority == null) {
			return new SeniorityForm();
		}

		SeniorityForm form = new SeniorityForm();

		form.setCountry(seniority.getOffice());
		form.setNature(EnumAdapter.coreSeniorityKindToFormSeniorityKind(seniority.getKind()));
		form.setPriorityDate(seniority.getRegistrationDate());
		form.setFilingDate(seniority.getFilingDate());
		form.setFilingNumber(seniority.getRegistrationNumber());
		form.setRegistrationDate(seniority.getRegistrationDate());
		form.setRegistrationNumber(seniority.getRegistrationNumber());

		if (seniority.getAttachedDocuments() != null && !seniority.getAttachedDocuments().isEmpty()) {
			form.setFileWrapperCopy(listAttachedDocumentFactory.convertFrom(seniority.getAttachedDocuments()));
		}
		
		//form.setImported(seniority.isImported());

		return form;
	}

}
