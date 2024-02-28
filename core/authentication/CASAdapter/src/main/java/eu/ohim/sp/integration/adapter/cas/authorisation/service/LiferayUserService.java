package eu.ohim.sp.integration.adapter.cas.authorisation.service;

import eu.ohim.sp.integration.adapter.cas.authorisation.service.rest.LiferayRestTemplate;
import eu.ohim.sp.integration.adapter.cas.authorisation.service.rest.objects.GetCompanyResult;
import eu.ohim.sp.integration.adapter.cas.authorisation.service.rest.objects.GetUserResult;
import eu.ohim.sp.integration.adapter.cas.authorisation.service.rest.objects.UserGroup;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LiferayUserService extends AbstractCasAssertionUserDetailsService {

    @Value("${liferay.remote.user.name}")
    private String remoteUser;
    @Value("${liferay.remote.user.password}")
    private String remotePassword;
    @Value("${liferay.virtual.host}")
    private String virtualHost;

    @Value("${liferay.service.url}/company/get-company-by-virtual-host/virtual-host/{virtual_host}")
    private String getCompanyByVirtualHostUrl;

    @Value("${liferay.service.url}/user/get-user-by-screen-name/company-id/{company_id}/screen-name/{screen_name}")
    private String getUserByScreenNameUrl;

    @Value("${liferay.service.url}/usergroup/get-user-user-groups/user-id/{user_id}")
    private String getUserUserGroupsUrl;

    private static final String EMPTY_PASSWORD = "EMPTY_PASSWORD";

    private RestTemplate restTemplate;
    @PostConstruct
    private void init() {
        restTemplate = new LiferayRestTemplate(remoteUser, remotePassword);
    }

    @Override
    protected UserDetails loadUserDetails(Assertion assertion) {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GetCompanyResult company = restTemplate.getForObject(getCompanyByVirtualHostUrl, GetCompanyResult.class, virtualHost);
        GetUserResult user = restTemplate.getForObject(getUserByScreenNameUrl, GetUserResult.class, company.getCompanyId(), assertion.getPrincipal().getName());
        ResponseEntity<UserGroup[]> res = restTemplate.getForEntity(getUserUserGroupsUrl, UserGroup[].class, user.getUserId());
        return new User(assertion.getPrincipal().getName(), EMPTY_PASSWORD, true, true, true, true, Arrays.stream(res.getBody()).map(g -> new SimpleGrantedAuthority(g.getName())).collect(Collectors.toList()));
    }

}
