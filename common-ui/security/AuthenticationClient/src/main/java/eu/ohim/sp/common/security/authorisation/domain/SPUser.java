package eu.ohim.sp.common.security.authorisation.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SPUser extends User {

    private static final long serialVersionUID = -2012074700195340724L;

    private boolean fullyInitialized = false;
    private ArrayList<String> roles;
    private ArrayList<String> groups;

    public SPUser(String username, String password, boolean accountNonExpired, boolean accountNonLocked,
            boolean credentialsNonExpired, boolean enabled, Collection<GrantedAuthority> authorities,
            boolean fullyInitialized) {

        super(username, password, true, true, true, true, authorities);
        this.setFullyInitialized(fullyInitialized);

    }

    public boolean isFullyInitialized() {
        return fullyInitialized;
    }

    public void setFullyInitialized(boolean fullyInitialized) {
        this.fullyInitialized = fullyInitialized;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

}
