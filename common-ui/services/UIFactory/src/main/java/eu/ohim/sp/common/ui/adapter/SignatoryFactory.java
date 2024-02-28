/*******************************************************************************
 * * $Id:: SignatoryFactory.java 113496 2013-04-22 15:03:04Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.core.domain.application.Signatory;
import org.springframework.stereotype.Component;

@Component
public class SignatoryFactory implements UIFactory<Signatory, SignatureForm> {

	/**
     * Receives as a parameter a UI {@link SignatureForm} object and converts it to a
     * core {@link Signatory} object.
     *
     * @param form the UI SignatureForm to convert
     * @return the core Signatory object
     */
	@Override
	public Signatory convertTo(SignatureForm form) {
        if (form == null) {
            return new Signatory();
        }

        Signatory core = new Signatory();

        core.setName(form.getFullName());
        core.setCapacity(EnumAdapter.formSignatoryKindToCoreSignatoryKind(form.getPersonCapacity()));
        core.setPlace(form.getSignaturePlace());
        core.setCapacityText(form.getPosition());
        core.setEmail(form.getEmail());
        core.setUserId(form.getUserId());
        core.setAssociatedText(form.getSignatureAssociatedText());

        return core;
	}

	/**
	 * Receives as a parameter a core {@link Signatory} object and converts it to a
	 * {@link SignatureForm} object.
	 *
	 * @param signatory the signatory to convert
	 * @return the signature form object
	 */
	@Override
	public SignatureForm convertFrom(Signatory signatory) {
		if (signatory == null) {
			return null;
		}
		SignatureForm form = new SignatureForm();

		form.setPersonCapacity(EnumAdapter.coreSignatoryKindToFormSignatoryKind(signatory.getCapacity()));
		form.setFullName(signatory.getName());
		form.setSignaturePlace(signatory.getPlace());
		form.setPosition(signatory.getCapacityText());
		form.setEmail(signatory.getEmail());
		form.setUserId(signatory.getUserId());
        form.setSignatureAssociatedText(signatory.getAssociatedText());
		
		return form;
	}

}
