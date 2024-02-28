package eu.ohim.sp.common.ui.adapter.userdoc;


import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.userdoc.UserdocForm;
import eu.ohim.sp.core.domain.userdoc.Userdoc;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 27.04.2022
 * Time: 13:38
 */
@Component
public class UserdocFactory implements UIFactory<Userdoc, UserdocForm> {

    @Override
    public Userdoc convertTo(UserdocForm form) {
        Userdoc core = new Userdoc();
        core.setDocNbr(form.getDocNbr());
        core.setDocLog(form.getDocLog());
        core.setDocOrigin(form.getDocOrigin());
        core.setDocSeries(form.getDocSeries());
        core.setUserdocType(form.getUserdocType());
        core.setUserdocTypeName(form.getUserdocTypeName());
        core.setExternalSystemId(form.getExternalSystemId());
        core.setFilingDate(form.getFilingDate());
        core.setDocSeqNbr(form.getDocSeqNbr());
        core.setDocSeqSeries(form.getDocSeries());
        core.setParentProcessNbr(form.getParentProcessNbr());
        core.setParentProcessType(form.getParentProcessType());
        core.setProcessNbr(form.getProcessNbr());
        core.setProcessType(form.getProcessType());

        return core;
    }

    @Override
    public UserdocForm convertFrom(Userdoc core) {
        UserdocForm form = new UserdocForm();
        form.setDocNbr(core.getDocNbr());
        form.setDocLog(core.getDocLog());
        form.setDocOrigin(core.getDocOrigin());
        form.setDocSeries(core.getDocSeries());
        form.setUserdocType(core.getUserdocType());
        form.setUserdocTypeName(core.getUserdocTypeName());
        form.setExternalSystemId(core.getExternalSystemId());
        form.setFilingDate(core.getFilingDate());
        form.setDocSeqNbr(core.getDocSeqNbr());
        form.setDocSeqSeries(core.getDocSeries());
        form.setId(String.format("%s/%s/%s/%s",
                form.getDocOrigin(), form.getDocLog(), form.getDocSeries().toString(), form.getDocNbr().toString()));

        form.setParentProcessNbr(core.getParentProcessNbr());
        form.setParentProcessType(core.getParentProcessType());
        form.setProcessNbr(core.getProcessNbr());
        form.setProcessType(core.getProcessType());
        return form;
    }
}
