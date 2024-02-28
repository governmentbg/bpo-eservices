/*******************************************************************************
 * * $Id:: RepresentativeFactory.java 113496 2013-04-22 15:03:04Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.person.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PersonChangeFactory implements UIFactory<PersonChange, RepresentativeForm> {

	@Autowired
	private RepresentativeFactory representativeFactory;

	@Override
	public PersonChange convertTo(RepresentativeForm form) {
        if(form == null)
        {
            return null;
        }

		Representative currentRepresentative = new Representative();

        // id
        if (StringUtils.isNotEmpty(form.getPreviousPersonId())) {
            PersonIdentifier identifier = new PersonIdentifier();
            identifier.setValue(form.getPreviousPersonId());
            currentRepresentative.setIdentifiers(Collections.nCopies(1, identifier));
            currentRepresentative.setPersonNumber(form.getPreviousPersonId());
        }

        // Name
        if (StringUtils.isNotEmpty(form.getPreviousPersonName())) {
            PersonName name = new PersonName();
            name.setFirstName(form.getPreviousPersonName());
            currentRepresentative.setName(name);
        }

        // Full address
        if (StringUtils.isNotEmpty(form.getPreviousPersonAddress())) {
            Address address = new Address();
            address.setStreet(form.getPreviousPersonAddress());
            currentRepresentative.setAddresses(Collections.nCopies(1, address));
        }

        Representative updatedRepresentative = representativeFactory.convertTo(form);

        // Set the correct id for the updated representative
        if (form instanceof PreviousRepresentativeForm) {
            updatedRepresentative.setPersonNumber(((PreviousRepresentativeForm)form).getUpdatedId());
        }

        PersonChange personChange = new PersonChange();
        personChange.setPersonChangeKind(from(form.getChangeType()));
        personChange.setCurrentPerson(currentRepresentative);
        personChange.setUpdatedPerson(updatedRepresentative);

		return personChange;
	}

    @Override
    public RepresentativeForm convertFrom(PersonChange core) {
        if (core == null) {
            return null;
        }

        RepresentativeForm form = null;

        if (core.getUpdatedPerson() != null) {
            form = representativeFactory.convertFrom(core.getUpdatedPerson());
        } else {
            form = new RepresentativeForm();
        }

        if (core.getCurrentPerson() != null) {
            Representative currentRepresentative = core.getCurrentPerson();
            if (currentRepresentative.getIdentifiers() != null &&
                    !currentRepresentative.getIdentifiers().isEmpty() &&
                    currentRepresentative.getRepresentativeKind() == null) {
                form.setPreviousPersonId(currentRepresentative.getIdentifiers().get(0).getValue());
            } else {
                form.setPreviousPersonId(currentRepresentative.getPersonNumber());
            }

            if (currentRepresentative.getName() != null) {
                form.setPreviousPersonName(currentRepresentative.getName().getFirstName());
            }

            if (CollectionUtils.isNotEmpty(currentRepresentative.getAddresses()) &&
                    currentRepresentative.getAddresses().get(0) != null) {
                form.setPreviousPersonAddress(currentRepresentative.getAddresses().get(0).getStreet());
            }

            form.setChangeType(to(core.getPersonChangeKind()));
        }

        return form;
    }

    private static PersonChangeKind from(ChangePersonType type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case ADD_NEW_REPRESENTATIVE:
                return PersonChangeKind.ADD_NEW_REPRESENTATIVE;
            case REPLACE_REPRESENTATIVE:
                return PersonChangeKind.REPLACE_REPRESENTATIVE;
            case REMOVE_REPRESENTATIVE:
                return PersonChangeKind.REMOVE_REPRESENTATIVE;
            case CHANGE_REPRESENTATIVE_ADDRESS:
                return PersonChangeKind.CHANGE_REPRESENTATIVE_ADDRESS;
            case CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS:
                return PersonChangeKind.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS;
            case ADD_NEW_CORRESPONDENT:
                return PersonChangeKind.ADD_NEW_CORRESPONDENT;
            case REPLACE_CORRESPONDENT:
                return PersonChangeKind.REPLACE_CORRESPONDENT;
            case REMOVE_CORRESPONDENT:
                return PersonChangeKind.REMOVE_CORRESPONDENT;
            case CHANGE_CORRESPONDENT_ADDRESS:
                return PersonChangeKind.CHANGE_CORRESPONDENT_ADDRESS;
        }

        return null;
    }

    private static ChangePersonType to(PersonChangeKind type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case ADD_NEW_REPRESENTATIVE:
                return ChangePersonType.ADD_NEW_REPRESENTATIVE;
            case REPLACE_REPRESENTATIVE:
                return ChangePersonType.REPLACE_REPRESENTATIVE;
            case REMOVE_REPRESENTATIVE:
                return ChangePersonType.REMOVE_REPRESENTATIVE;
            case CHANGE_REPRESENTATIVE_ADDRESS:
                return ChangePersonType.CHANGE_REPRESENTATIVE_ADDRESS;
            case CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS:
                return ChangePersonType.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS;
            case ADD_NEW_CORRESPONDENT:
                return ChangePersonType.ADD_NEW_CORRESPONDENT;
            case REPLACE_CORRESPONDENT:
                return ChangePersonType.REPLACE_CORRESPONDENT;
            case REMOVE_CORRESPONDENT:
                return ChangePersonType.REMOVE_CORRESPONDENT;
            case CHANGE_CORRESPONDENT_ADDRESS:
                return ChangePersonType.CHANGE_CORRESPONDENT_ADDRESS;
        }

        return null;
    }

}
