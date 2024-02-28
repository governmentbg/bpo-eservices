package bg.duosoft.service;

import bg.duosoft.data.repository.CountRepository;
import bg.duosoft.model.ApplicationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountService {

    public static final String TRADEMARK_TYPE = "TM";
    public static final String PATENT_TYPE = "PT_EFILING";
    public static final String UTILITY_MODEL_TYPE = "UM_EFILING";
    public static final String EU_PATENT_TYPE = "EP_EFILING";
    public static final String SPC_TYPE = "SPC_EFILING";
    public static final String SORT_BREED_TYPE = "SV_EFILING";
    public static final String GEO_INDICATION_TYPE = "GI_EFILING";
    public static final String INTEGRAL_SCHEMA_TYPE = "IS_EFILING";
    public static final String DESIGN_TYPE = "DS";
    public static final String ESERVICES_TYPE = "ES_EFILING";
    private static final String STATUS_SUBMITTED = "STATUS_SUBMITTED";

    @Autowired
    private CountRepository repository;
    public Map<String, ApplicationCount> getApplicationsCount() {



        Map<String, Long> submittedApplicationsCount = repository.getSubmittedApplicationsCount();
        Map<String, Long> acceptedWithProblems = repository.getAcceptedApplicationsWithProblems();
        Map<String, ApplicationCount> res = new HashMap<>();
        addToResult(TRADEMARK_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(DESIGN_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(PATENT_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(UTILITY_MODEL_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(EU_PATENT_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(SPC_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(SORT_BREED_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(GEO_INDICATION_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(INTEGRAL_SCHEMA_TYPE, res, submittedApplicationsCount, acceptedWithProblems);
        addToResult(ESERVICES_TYPE, res, submittedApplicationsCount, acceptedWithProblems);

        return res;
    }
    private void addToResult(String type, Map<String, ApplicationCount> res, Map<String, Long> submittedApplicationsCount, Map<String, Long> acceptedWithProblems) {
        res.put(type, new ApplicationCount(type, submittedApplicationsCount.getOrDefault(type, (long)0), acceptedWithProblems.getOrDefault(type, (long)0)));
    }

}
