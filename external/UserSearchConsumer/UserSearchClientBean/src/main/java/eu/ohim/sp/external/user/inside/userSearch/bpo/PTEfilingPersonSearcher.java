package eu.ohim.sp.external.user.inside.userSearch.bpo;

import java.util.List;
import java.util.Optional;

/**
 * Created by Raya
 * 16.04.2019
 */
public class PTEfilingPersonSearcher extends AbstractBPOPersonSearcher implements PersonSearcher  {

    @Override
    Optional<List<String>> getApplicantIds(Integer companyId, Integer userId) {
        return _getApplicantIds(companyId, userId);
    }

    @Override
    Optional<List<String>> getRepresentativeIds(Integer companyId, Integer userId) {
        return _getRepresentativeIds(companyId, userId);
    }

    @Override
    Optional<List<String>> getDesignerIds(Integer companyId, Integer userId) {
        return _getDesignerIds(companyId, userId);
    }
}
