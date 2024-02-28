package eu.ohim.sp.common.ui.adapter.patent;


import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PTDivisionalApplicationForm;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 11.04.2019
 */
@Component
public class PTDivisionalApplicationFactory implements UIFactory<DivisionalApplicationDetails, PTDivisionalApplicationForm> {

    @Override
    public DivisionalApplicationDetails convertTo(PTDivisionalApplicationForm form) {
        DivisionalApplicationDetails core = new DivisionalApplicationDetails();
        if(form == null){
            return core;
        }
        core.setInitialApplicationDate(form.getDateDivisionalApplication());
        core.setInitialApplicationNumber(form.getNumberDivisionalApplication());
        core.setImported(form.getImported());
        return core;
    }

    @Override
    public PTDivisionalApplicationForm convertFrom(DivisionalApplicationDetails core) {
        PTDivisionalApplicationForm form = new PTDivisionalApplicationForm();
        if(core == null){
            return form;
        }
        form.setNumberDivisionalApplication(core.getInitialApplicationNumber());
        form.setDateDivisionalApplication(core.getInitialApplicationDate());
        form.setImported(core.getImported());
        return form;
    }
}
