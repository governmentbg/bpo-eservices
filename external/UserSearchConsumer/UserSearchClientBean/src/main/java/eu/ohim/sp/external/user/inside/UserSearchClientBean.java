package eu.ohim.sp.external.user.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.user.Criteria;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserGroup;
import eu.ohim.sp.core.domain.user.UserRole;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.user.BOUser;
import eu.ohim.sp.external.domain.user.FOUser;
import eu.ohim.sp.external.domain.user.UserPersonDetails;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.services.user.GetUser;
import eu.ohim.sp.external.services.user.GetUserResponse;
import eu.ohim.sp.external.services.user.SearchUser;
import eu.ohim.sp.external.services.user.SearchUserResponse;
import eu.ohim.sp.external.user.UserSearchClientInside;
import eu.ohim.sp.external.user.inside.userSearch.bpo.BpoUserSearchService;
import eu.ohim.sp.external.user.inside.userSearch.bpo.PersonSearcherFactory;
import eu.ohim.sp.external.user.inside.userSearch_mock.UserSearchMock;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The User Adapter Service class
 */
@Dependent
@UserSearchClientInside
public class UserSearchClientBean implements UserSearchService {

    private static final Logger LOGGER = Logger.getLogger(UserSearchClientBean.class);
    public static final String ERROR_WS_SOAP = "		> ERROR WS SOAP: ";

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * User injector
     */
    private BpoUserSearchService userInjector;


    public static final String USER_ADAPTER_NAME = "user";

	@PostConstruct
	public final void init() {
        List<String> customConverters = new ArrayList<String>();
        customConverters.add("mapping.xml");
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(customConverters);
        userInjector = new BpoUserSearchService();
	}

	@Override
    @Interceptors({AdapterSetup.User.class, AdapterEnabled.class})
	public List<? extends User> searchUser(String module, String office, Map searchCriteria) {
		LOGGER.debug(" - searchUser START");
		List<User> userList = new ArrayList<User>();
        SearchUserResponse externalUserList = null;
        ParamsMap externalCriteria = mapper.map((searchCriteria==null?new Criteria():searchCriteria),
                ParamsMap.class);

        SearchUser searchUser = new SearchUser(module, office, externalCriteria);
        externalUserList = userInjector.searchUser(searchUser);

        if (externalUserList != null
                && externalUserList.getUser().size() > 0) {
            for (JAXBElement<? extends eu.ohim.sp.external.domain.user.User> object : externalUserList.getUser()) {
                userList.add(mapUser(object));
            }
        } else {
            LOGGER.error("		> WARNING: searchUser. The call to the ws returns a null value.");
        }
        LOGGER.debug(" - searchUser END");
		return userList;
	}

private User mapUser(JAXBElement<? extends eu.ohim.sp.external.domain.user.User> user) {
        User userCore = null;
        if (user != null
                && user.getValue() != null) {
            if (user.getValue() instanceof BOUser) {
                userCore = mapper.map(user.getValue(),
                        eu.ohim.sp.core.domain.user.BOUser.class);
            } else if (user.getValue() instanceof FOUser) {
                userCore =  mapper.map(user.getValue(),
                        eu.ohim.sp.core.domain.user.FOUser.class);
            } else {
                userCore =  mapper.map(user.getValue(),
                        User.class);
            }
        }
        return userCore;
    }

    @Override
    @Interceptors({AdapterSetup.User.class, AdapterEnabled.class})
	public User getUser(String module, String office, String username) {
		LOGGER.debug(" - getUserDetails START");

        GetUserResponse getUserDetailsResponse = userInjector.getUser(new GetUser(module, office, username));

        User user = mapUser(getUserDetailsResponse.getUser());

		LOGGER.debug(" - getUserDetails END");

		return user;
	}

    @Interceptors({AdapterSetup.User.class, AdapterEnabled.class})
	public List<UserRole> getAllRoles(String module, String office) {
		LOGGER.debug(" - getAllRoles START");

        List<eu.ohim.sp.external.domain.user.UserRole> externalUserRoleList = userInjector
                .getAllRoles(module, office);

        List<UserRole> userRoleList = new ArrayList<UserRole>();
        for (eu.ohim.sp.external.domain.user.UserRole userRole : externalUserRoleList) {
            userRoleList.add(mapper.map(userRole,
                    UserRole.class));
        }

		LOGGER.debug(" - getAllRoles END");
		return userRoleList;
	}

    @Interceptors({AdapterSetup.User.class, AdapterEnabled.class})
	public List<UserGroup> getAllGroups(String module, String office) {
		LOGGER.debug(" - getAllGroups START");

        List<eu.ohim.sp.external.domain.user.UserGroup> externalUserGroupList = userInjector
                .getAllGroups(module, office);

        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        for (eu.ohim.sp.external.domain.user.UserGroup userGroup : externalUserGroupList) {
            userGroupList.add(mapper.map(userGroup,
                    UserGroup.class));
        }

		LOGGER.debug(" - getAllGroups END");
		return userGroupList;
	}
}
