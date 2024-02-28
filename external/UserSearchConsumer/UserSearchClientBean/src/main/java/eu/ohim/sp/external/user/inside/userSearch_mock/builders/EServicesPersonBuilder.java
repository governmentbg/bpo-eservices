package eu.ohim.sp.external.user.inside.userSearch_mock.builders;

import eu.ohim.sp.external.domain.user.User;

public class EServicesPersonBuilder implements PersonBuilder {
	@Override
	public User build() {
		Builder b = new Builder();
		return b.build();
	}
}
