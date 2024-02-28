package eu.ohim.sp.eservices.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.form.userdoc.FilteredUserdocsForm;
import eu.ohim.sp.common.ui.form.userdoc.UserdocForm;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

import java.util.List;

public interface BackOfficeServiceInterface {

    String unpublishedAppsAutocomplete(String user, String flowModeId, String text);

    TMDetailsForm getUnpublishedTradeMark(String user, String tradeMarkId, String provisionalId);
    List<ESDesignDetailsForm> getUnpublishedDesignApplication(String user, String designAppId, String provisionalId);
    ESPatentDetailsForm getUnpublishedPatent(String user, String patentId, String provisionalId);

    FilteredUserdocsForm getUserdocsForApplication(ESFlowBean esFlowBean);
}
