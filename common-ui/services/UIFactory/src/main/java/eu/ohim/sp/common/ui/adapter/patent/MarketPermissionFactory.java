package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.MarketPermissionForm;
import eu.ohim.sp.core.domain.patent.MarketPermission;
import org.springframework.stereotype.Component;

@Component
public class MarketPermissionFactory implements UIFactory<MarketPermission, MarketPermissionForm> {

    @Override
    public MarketPermission convertTo(MarketPermissionForm form) {
        MarketPermission core = new MarketPermission();
        if (form == null) {
            return core;
        }
        core.setFirstPermissionBGNumber(form.getFirstPermissionBGNumber());
        core.setFirstPermissionBGDate(form.getFirstPermissionBGDate());
        core.setFirstPermissionEUNumber(form.getFirstPermissionEUNumber());
        core.setFirstPermissionEUDate(form.getFirstPermissionEUDate());
        return core;
    }

    @Override
    public MarketPermissionForm convertFrom(MarketPermission core) {
        MarketPermissionForm form = new MarketPermissionForm();
        if (core == null) {
            return form;
        }
        form.setFirstPermissionBGNumber(core.getFirstPermissionBGNumber());
        form.setFirstPermissionBGDate(core.getFirstPermissionBGDate());
        form.setFirstPermissionEUNumber(core.getFirstPermissionEUNumber());
        form.setFirstPermissionEUDate(core.getFirstPermissionEUDate());
        return form;
    }
}
