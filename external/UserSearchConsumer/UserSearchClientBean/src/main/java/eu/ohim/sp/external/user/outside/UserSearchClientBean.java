package eu.ohim.sp.external.user.outside;

import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.user.UserSearchClientOutside;
import eu.ohim.sp.external.user.outside.ws.client.UserWSClient;
import eu.ohim.sp.external.user.outside.ws.client.UserWSClientService;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.user.Criteria;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserGroup;
import eu.ohim.sp.core.domain.user.UserRole;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.user.BOUser;
import eu.ohim.sp.external.domain.user.FOUser;
import eu.ohim.sp.external.services.user.GetUser;
import eu.ohim.sp.external.services.user.GetUserResponse;
import eu.ohim.sp.external.services.user.SearchUser;
import eu.ohim.sp.external.services.user.SearchUserResponse;
import eu.ohim.sp.external.ws.exception.UserFaultException;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.xml.bind.JAXBElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The User Adapter Service class
 */
@Dependent
@UserSearchClientOutside
public class UserSearchClientBean extends AbstractWSClient implements UserSearchService {

    private static final Logger LOGGER = Logger.getLogger(UserSearchClientBean.class);
    public static final String ERROR_WS_SOAP = "		> ERROR WS SOAP: ";

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual client to the web service
     */
    private UserWSClient userWebServiceRef;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;


    public static final String USER_ADAPTER_NAME = "user";

	@PostConstruct
	public final void init() {
        super.init(USER_ADAPTER_NAME);

        List<String> customConverters = new ArrayList<String>();
        customConverters.add("mapping.xml");
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(customConverters);

        if (getAdapterEnabled()) {
            userWebServiceRef = new UserWSClientService(getWsdlLocation()).getManageUserPort();
        }
	}

	@Override
	public List<? extends User> searchUser(String module, String office, Map searchCriteria) {
		LOGGER.debug(" - searchUser START");
		List<User> userList = new ArrayList<User>();

		if (getAdapterEnabled()) {
			SearchUserResponse externalUserList = null;
			try {
                ParamsMap externalCriteria = mapper.map((searchCriteria==null?new Criteria():searchCriteria),
                        ParamsMap.class);

                SearchUser searchUser = new SearchUser(module, office, externalCriteria);
                externalUserList = userWebServiceRef
						.searchUser(searchUser);

                if (externalUserList != null
                        && externalUserList.getUser().size() > 0) {
                    for (JAXBElement<? extends eu.ohim.sp.external.domain.user.User> object : externalUserList.getUser()) {
                        userList.add(mapUser(object));
                    }
                } else {
                    LOGGER.error("		> WARNING: searchUser. The call to the ws returns a null value.");
                }
			} catch (UserFaultException exc) {
				LOGGER.error(ERROR_WS_SOAP + exc.getMessage(), exc);
			}
		} else {
			LOGGER.info("		> WARNING: searchUser: adapter is not enabled. Then there is no call to the ws.");
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
                        eu.ohim.sp.core.domain.user.User.class);
            }
        }
        return userCore;
    }

    @Override
	public User getUser(String module, String office, String username) {
		LOGGER.debug(" - getUserDetails START");
        User user = null;

        if (getAdapterEnabled()) {
			try {
                GetUserResponse getUserDetailsResponse = userWebServiceRef.getUser(new GetUser(module, office, username));

                if (getUserDetailsResponse != null) {
                    user = mapUser(getUserDetailsResponse.getUser());
                } else {
                    LOGGER.error("		> WARNING: getUserDetails. The call to the ws returns a null value.");
                }

            } catch (UserFaultException exc) {
				LOGGER.error(ERROR_WS_SOAP + exc.getMessage(), exc);
			}
		} else {
			LOGGER.info("		> WARNING: getUser: adapter is not enabled. Then there is no call to the ws.");
		}
		LOGGER.debug(" - getUserDetails END");

		return user;
	}

	public List<UserRole> getAllRoles(String module, String office) {
		LOGGER.debug(" - getAllRoles START");
		List<eu.ohim.sp.external.domain.user.UserRole> externalUserRoleList = null;
		List<UserRole> userRoleList = null;

		if (getAdapterEnabled()) {
			try {
				externalUserRoleList = userWebServiceRef.getAllRoles(module, office);
			} catch (UserFaultException exc) {
				LOGGER.error(ERROR_WS_SOAP + exc.getMessage(), exc);
			}

			if (externalUserRoleList != null) {
                userRoleList = new ArrayList<UserRole>();
                for (eu.ohim.sp.external.domain.user.UserRole userRole : externalUserRoleList) {
                    userRoleList.add(mapper.map(userRole,
                            UserRole.class));
                }
			} else {
				LOGGER.error("		> WARNING: getAllRoles. The call to the ws returns a null value.");
			}

		} else {
			LOGGER.info("		> WARNING: getAllRoles: adapter is not enabled. Then there is no call to the ws.");
		}

		LOGGER.debug(" - getAllRoles END");

		return userRoleList;
	}

	public List<UserGroup> getAllGroups(String module, String office) {
		LOGGER.debug(" - getAllGroups START");
		List<eu.ohim.sp.external.domain.user.UserGroup> externalUserGroupList = null;
		List<UserGroup> userGroupList = null;

		if (getAdapterEnabled()) {
			try {
				externalUserGroupList = userWebServiceRef.getAllGroups(module, office);
			} catch (UserFaultException exc) {
				LOGGER.error(ERROR_WS_SOAP + exc.getMessage(), exc);
			}

			if (externalUserGroupList != null) {
                userGroupList = new ArrayList<UserGroup>();
                for (eu.ohim.sp.external.domain.user.UserGroup userGroup : externalUserGroupList) {
                    userGroupList.add(mapper.map(userGroup,
                            UserGroup.class));
                }
			} else {
				LOGGER.error("		> WARNING: getAllGroups. The call to the ws returns a null value.");
			}

		} else {
			LOGGER.info("		> WARNING: getAllGroups: adapter is not enabled. Then there is no call to the ws.");
		}

		LOGGER.debug(" - getAllGroups END");

		return userGroupList;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }
}
