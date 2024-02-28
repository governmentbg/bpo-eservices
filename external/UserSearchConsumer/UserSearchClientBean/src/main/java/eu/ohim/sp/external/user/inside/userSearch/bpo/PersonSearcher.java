package eu.ohim.sp.external.user.inside.userSearch.bpo;

import eu.ohim.sp.external.domain.user.User;

import java.util.List;

public interface PersonSearcher {
	User search(String module, String office, String username);
}
