package eu.ohim.sp.common.ui.adapter.userdoc;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.userdoc.FilteredUserdocsForm;
import eu.ohim.sp.core.domain.userdoc.FilteredUserdocs;
import eu.ohim.sp.core.domain.userdoc.UserdocRelationRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 29.04.2022
 * Time: 10:46
 */
@Component
public class FilteredUserdocsFactory implements UIFactory<FilteredUserdocs, FilteredUserdocsForm> {

    @Autowired
    private UserdocFactory userdocFactory;

    @Override
    public FilteredUserdocs convertTo(FilteredUserdocsForm form) {
        FilteredUserdocs core = new FilteredUserdocs();
        if(form == null){
            return core;
        }
        if(form.getUserdocFormList() != null && form.getUserdocFormList().size() >0){
            core.setUserdoc(form.getUserdocFormList().stream().map(udform -> userdocFactory.convertTo(udform)).collect(Collectors.toList()));
        }
        if(form.getUserdocRelationRestriction() != null){
            core.setUserdocRelationRestriction(UserdocRelationRestriction.valueOf(form.getUserdocRelationRestriction()));
        }
        core.setMainObject(form.getMainObject());

        return core;
    }

    @Override
    public FilteredUserdocsForm convertFrom(FilteredUserdocs core) {
        FilteredUserdocsForm form = new FilteredUserdocsForm();
        if(core == null) {
            return form;
        }
        if(core.getUserdoc() != null){
            form.getUserdocFormList().addAll(core.getUserdoc().stream().map(ud -> userdocFactory.convertFrom(ud)).collect(Collectors.toList()));
        }
        form.setUserdocRelationRestriction(core.getUserdocRelationRestriction().name());
        form.setMainObject(core.getMainObject());

        return form;
    }
}
