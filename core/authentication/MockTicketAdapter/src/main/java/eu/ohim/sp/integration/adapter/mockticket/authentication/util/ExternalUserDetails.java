package eu.ohim.sp.integration.adapter.mockticket.authentication.util;

import java.util.Collection;

public class ExternalUserDetails {

    private String username;
    private Collection<String> authorities;

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }

    public ExternalUserDetails() {

    }

    public ExternalUserDetails(String username, Collection<String> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
