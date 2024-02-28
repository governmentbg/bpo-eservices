package bg.duosoft.data.repository;

import bg.duosoft.data.entity.CountEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: ggeorgiev
 * Date: 19.12.2022
 * Time: 13:45
 */
@Service
public class CountRepository {
    private static final String SELECT_APPLICATIONS_COUNT = "select count(*) applications_count, efiling.get_base_efiling_type(dscformtype) base_efiling_type\n" +
            "from efiling.dform f \n" +
            "join efiling.cformtype ft on ft.idformtype = f.tyapplication \n" +
            "join efiling.cformstatus sts on sts.idformstatus = f.idstatus \n" +
            "where dscformstatus = :status \n" +
            "and f.idfiling not in (select idfiling from app_management.autoaccept_application_details where status = 'W')\n" +
            "group by efiling.get_base_efiling_type(dscformtype)";
    private static final String SELECT_SUBMITTED_APPLICATIONS_WITH_PROBLEMS = "select count(distinct f.idfiling) applications_count, efiling.get_base_efiling_type(dscformtype) base_efiling_type\n" +
            "from efiling.dform f \n" +
            "join efiling.cformtype ft on ft.idformtype = f.tyapplication \n" +
            "join efiling.cformstatus sts on sts.idformstatus = f.idstatus \n" +
            "join app_management.accept_application_problems aap on aap.idfiling = f.idfiling\n" +
            "where aap.status = :applicationProblemStatus and dscformstatus = :applicationStatus\n" +
            "group by efiling.get_base_efiling_type(dscformtype)";
    @PersistenceContext
    protected EntityManager em;
    public Map<String, Long> getSubmittedApplicationsCount() {
        TypedQuery<CountEntity> query = (TypedQuery<CountEntity>) em.createNativeQuery(SELECT_APPLICATIONS_COUNT, CountEntity.class);
        query.setParameter("status", "STATUS_SUBMITTED");
        return query.getResultList().stream().collect(Collectors.toMap(r -> r.getBaseEfilingType(), r -> r.getApplicationsCount()));
    }
    public Map<String, Long> getAcceptedApplicationsWithProblems() {
        TypedQuery<CountEntity> query = (TypedQuery<CountEntity>) em.createNativeQuery(SELECT_SUBMITTED_APPLICATIONS_WITH_PROBLEMS, CountEntity.class);
        query.setParameter("applicationProblemStatus", "E");
        query.setParameter("applicationStatus", "TRANSFER_OK");
        return query.getResultList().stream().collect(Collectors.toMap(r -> r.getBaseEfilingType(), r -> r.getApplicationsCount()));
    }

}
