package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PTEuropeanParallelApplicationForm;
import eu.ohim.sp.common.ui.form.patent.PTInternationalParallelApplicationForm;
import eu.ohim.sp.common.ui.form.patent.PTNationalParallelApplicationForm;
import eu.ohim.sp.common.ui.form.patent.PTParallelApplicationForm;
import eu.ohim.sp.core.domain.patent.ParallelApplication;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 11.04.2019
 */
@Component
public class PTParallelApplicationFactory implements UIFactory<ParallelApplication, PTParallelApplicationForm> {

    @Override
    public ParallelApplication convertTo(PTParallelApplicationForm form) {
        ParallelApplication core = new ParallelApplication();
        if(form == null){
            return core;
        }
        core.setCountryCode(form.getCountryCode());
        core.setApplicationDate(form.getApplicationDate());
        core.setApplicationNumber(form.getApplicationNumber());
        core.setHolderName(form.getHolderName());
        core.setPublicationDate(form.getPublicationDate());
        core.setPublicationNumber(form.getPublicationNumber());
        core.setImported(form.getImported());
        return core;
    }

    @Override
    public PTParallelApplicationForm convertFrom(ParallelApplication core) {
        PTParallelApplicationForm form;
        if(core == null){
            return new PTNationalParallelApplicationForm();
        }
        if(core.getCountryCode() != null && !core.getCountryCode().isEmpty()){
            switch (core.getCountryCode()){
                case "WO": form = new PTInternationalParallelApplicationForm(); break;
                case "EM": form = new PTEuropeanParallelApplicationForm(); break;
                default: form = new PTNationalParallelApplicationForm(); break;
            }
        } else {
            form = new PTNationalParallelApplicationForm();
        }
        form.setApplicationDate(core.getApplicationDate());
        form.setApplicationNumber(core.getApplicationNumber());
        form.setHolderName(core.getHolderName());
        form.setPublicationDate(core.getPublicationDate());
        form.setPublicationNumber(core.getPublicationNumber());
        form.setCountryCode(core.getCountryCode());
        form.setImported(core.getImported());
        return form;
    }
}
