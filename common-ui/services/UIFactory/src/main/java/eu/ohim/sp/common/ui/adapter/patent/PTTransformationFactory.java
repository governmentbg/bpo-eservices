package eu.ohim.sp.common.ui.adapter.patent;


import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PTConversionForm;
import eu.ohim.sp.common.ui.form.patent.PTInternationalTransformationForm;
import eu.ohim.sp.common.ui.form.patent.PTNationalTransformationForm;
import eu.ohim.sp.common.ui.form.patent.PTTransformationForm;
import eu.ohim.sp.core.domain.patent.PatentTransformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 11.04.2019
 */
@Component
public class PTTransformationFactory implements UIFactory<PatentTransformation, PTTransformationForm> {

    @Override
    public PatentTransformation convertTo(PTTransformationForm form) {
        PatentTransformation core = new PatentTransformation();
        if(form == null){
            return core;
        }
        core.setApplicationDate(form.getApplicationDate());
        core.setApplicationNumber(form.getApplicationNumber());
        core.setCountryCode(form.getCountryCode());
        core.setHolderName(form.getHolderName());
        core.setPublicationDate(form.getPublicationDate());
        core.setPublicationNumber(form.getPublicationNumber());
        core.setPayedFees(form.isPayedFees());
        core.setImported(form.getImported());

        return core;
    }

    @Override
    public PTTransformationForm convertFrom(PatentTransformation core) {
        PTTransformationForm form = null;
        if(core == null){
            return new PTNationalTransformationForm();
        }
        if(core.getCountryCode() != null && !core.getCountryCode().isEmpty()){
            switch (core.getCountryCode()){
                case "WO": form = new PTInternationalTransformationForm(); break;
                case "EM": form = new PTConversionForm(); break;
                default: form = new PTNationalTransformationForm(); break;
            }
        } else {
            form = new PTNationalTransformationForm();
        }
        form.setApplicationDate(core.getApplicationDate());
        form.setApplicationNumber(core.getApplicationNumber());
        form.setCountryCode(core.getCountryCode());
        form.setHolderName(core.getHolderName());
        form.setPublicationDate(core.getPublicationDate());
        form.setPublicationNumber(core.getPublicationNumber());
        form.setPayedFees(core.isPayedFees());
        form.setImported(core.getImported());

        return form;
    }
}
