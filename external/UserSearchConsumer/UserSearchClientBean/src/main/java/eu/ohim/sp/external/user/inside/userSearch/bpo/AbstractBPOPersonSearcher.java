package eu.ohim.sp.external.user.inside.userSearch.bpo;

import eu.ohim.sp.external.domain.user.FOUser;
import eu.ohim.sp.external.domain.user.User;
import eu.ohim.sp.external.domain.user.UserPersonDetails;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.user.inside.UserSearchClientBean;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.ExpandoResult;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.GetCompanyResult;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.GetUserResult;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractBPOPersonSearcher implements PersonSearcher {

    private static final Logger LOGGER = Logger.getLogger(AbstractBPOPersonSearcher.class);

    private static final String VIRTUAL_HOST = System.getProperty("liferay.virtual.host");
    private static final String SERVICE_URL = System.getProperty("liferay.service.url");
    private static String GET_COMPANY_BY_VIRTUAL_HOST_URL = SERVICE_URL + "/company/get-company-by-virtual-host/virtual-host/{virtual_host}";
    private static String GET_USER_BY_SCREEN_NAME_URL = SERVICE_URL + "/user/get-user-by-screen-name/company-id/{company_id}/screen-name/{screen_name}";
    private static String GET_EXPANDOVALUE_URL = SERVICE_URL + "/expandovalue/get-json-data/company-id/{company_id}/class-name/com.liferay.portal.model.User/table-name/CUSTOM_FIELDS/column-name/{column_name}/class-pk/{user_id}";

    private PersonInjector personInjector = new PersonInjector();

    public User search(String module, String office, String username) {
        LOGGER.debug("Reading company details for virtualHost " + VIRTUAL_HOST);
        GetCompanyResult getCompanyResult = new LiferayRestClient<>(GetCompanyResult.class, GET_COMPANY_BY_VIRTUAL_HOST_URL, VIRTUAL_HOST).callService();
        LOGGER.debug("Reading user details for userName: " + username);
        GetUserResult user = new LiferayRestClient<>(GetUserResult.class, GET_USER_BY_SCREEN_NAME_URL, getCompanyResult.getCompanyId().toString(), username).callService();
        FOUser result = new FOUser();
        result.setUserName(username);
        result.setEmail(user.getEmailAddress());
        result.setFullName(Arrays.asList(user.getFirstName(), user.getMiddleName(), user.getLastName()).stream().filter(f -> f != null).collect(Collectors.joining(" " )));
        result.setUserPersonDetails(new UserPersonDetails());
        Integer userId = user.getUserId();
        getApplicantIds(getCompanyResult.getCompanyId(), userId).ifPresent((list) -> result.getUserPersonDetails().setApplicants(
                list
                        .stream()
                        .map(a -> personInjector.getApplicant(module, office, a))
                        .filter(a -> a != null)
                        .collect(Collectors.toList())));
        getRepresentativeIds(getCompanyResult.getCompanyId(), userId).ifPresent((list) -> result.getUserPersonDetails().setRepresentatives(
                list
                        .stream()
                        .map(a -> personInjector.getRepresentative(module, office, a))
                        .filter(a -> a != null)
                        .collect(Collectors.toList())));
        getDesignerIds(getCompanyResult.getCompanyId(), userId).ifPresent((list) -> result.getUserPersonDetails().setDesigners(
                list
                        .stream()
                        .map(a -> personInjector.getDesigner(module, office, a))
                        .filter(a -> a != null)
                        .collect(Collectors.toList())));
        return result;
    }


    abstract Optional<List<String>> getApplicantIds(Integer companyId, Integer userId);

    abstract Optional<List<String>> getRepresentativeIds(Integer companyId, Integer userId);

    abstract Optional<List<String>> getDesignerIds(Integer companyId, Integer userId);

    protected Optional<List<String>> _getRepresentativeIds(Integer companyId, Integer userId) {
        return getIds(companyId, userId, "rid");
    }

    protected Optional<List<String>> _getApplicantIds(Integer companyId, Integer userId) {
        return getIds(companyId, userId, "aid");
    }

    protected Optional<List<String>> _getDesignerIds(Integer companyId, Integer userId) {
        return getIds(companyId, userId, "did");
    }

    /**
     * @param idName - aid(applicantId)/did(designerId)/rid(representative id)/daid(designs applicant id)
     * @return
     */
    private Optional<List<String>> getIds(Integer companyId, Integer userId, String idName) {
        List<String> result = new ArrayList<String>();
        ExpandoResult expando = new LiferayRestClient<>(ExpandoResult.class, GET_EXPANDOVALUE_URL, companyId.toString(), idName, userId.toString()).callService();
        if (expando.getData() != null && !expando.getData().equals("") && !expando.getData().equals("0")) {
            result.add(expando.getData());
        }
        return result.size() == 0 ? Optional.empty() : Optional.of(result);
    }
}
