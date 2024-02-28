package bg.duosoft.documentmanager.utils;

import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.DraftStatus;

import java.util.Date;

/**
 * Created by Raya
 * 19.01.2021
 */
public class DraftApplicationUtils {

    public static Date findApplicationDateInDraftApplication(DraftApplication draftApplication){
        if(draftApplication.getStatuses() != null){
            DraftStatus draftStatus = draftApplication.getStatuses().stream().filter(st -> st.getStatus().equals(ApplicationStatus.STATUS_SUBMITTED)).findFirst().orElse(null);
            if(draftStatus != null){
                return draftStatus.getModifiedDate();
            }
        }
        return null;
    }
}
