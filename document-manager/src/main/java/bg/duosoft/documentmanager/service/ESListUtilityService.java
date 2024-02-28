package bg.duosoft.documentmanager.service;

import eu.ohim.sp.core.domain.trademark.IPApplication;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 22.03.2021
 * Time: 13:37
 */
public interface ESListUtilityService {

    List<IPApplication> findUnpublishedApplications(String since);
}
