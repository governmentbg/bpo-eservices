package eu.ohim.sp.common.ui.adapter.patent;


import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PCTForm;
import eu.ohim.sp.core.domain.patent.PCT;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 13.05.2019
 */
@Component
public class PCTFactory implements UIFactory<PCT, PCTForm> {

    @Override
    public PCT convertTo(PCTForm form) {
        PCT core = new PCT();
        if(form == null){
            return core;
        }
        core.setApplicationDate(form.getApplicationDate());
        core.setApplicationNumber(form.getApplicationNumber());
        core.setHolderName(form.getHolderName());
        core.setPublicationDate(form.getPublicationDate());
        core.setPublicationNumber(form.getPublicationNumber());
        core.setPayedFees(form.isPayedFees());
        return core;
    }

    @Override
    public PCTForm convertFrom(PCT core) {
        PCTForm form = new PCTForm();
        if(core == null){
            return form;
        }
        form.setApplicationDate(core.getApplicationDate());
        form.setApplicationNumber(core.getApplicationNumber());
        form.setHolderName(core.getHolderName());
        form.setPublicationDate(core.getPublicationDate());
        form.setPublicationNumber(core.getPublicationNumber());
        form.setPayedFees(core.isPayedFees());
        return form;
    }
}
