package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.trademark.ForeignRegistrationForm;
import eu.ohim.sp.core.domain.trademark.ForeignRegistration;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 05.05.2022
 * Time: 18:12
 */
@Component
public class ForeignRegistrationFactory implements UIFactory<ForeignRegistration, ForeignRegistrationForm> {

    @Override
    public ForeignRegistration convertTo(ForeignRegistrationForm form) {
        ForeignRegistration core = new ForeignRegistration();
        if(form == null){
            return core;
        }

        core.setRegistrationDate(form.getRegistrationDate());
        core.setRegistrationNumber(form.getRegistrationNumber());
        core.setRegistrationCountry(form.getRegistrationCountry());
        return core;
    }

    @Override
    public ForeignRegistrationForm convertFrom(ForeignRegistration core) {
        ForeignRegistrationForm form = new ForeignRegistrationForm();
        if(core == null){
            return form;
        }

        form.setRegistrationDate(core.getRegistrationDate());
        form.setRegistrationNumber(core.getRegistrationNumber());
        form.setRegistrationCountry(core.getRegistrationCountry());
        return form;
    }
}
