package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.person.PersonRole;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by marcoantonioalberoalbero on 26/5/17.
 */
public class FactoryUtils {

    public static <T extends PersonRole, U extends PersonForm> U personCore2UIphones(T p, U f) {
        Optional.of(p)
                .map(T::getPhones)
                .map(a -> a.stream().findFirst().orElse(new Phone()))
                .map(Phone::getNumber)
                .ifPresent(val -> f.setPhone(val));
        return f;
    }

    public static <T extends PersonRole, U extends PersonForm> T personUI2CorePhones(T p, U f) {
        if(f.getPhone() != null) {
            Phone ph = new Phone();
            ph.setNumber(f.getPhone());
            ArrayList<Phone> phones = new ArrayList();
            phones.add(ph);
            p.setPhones(phones);
        }
        return p;
    }
}
