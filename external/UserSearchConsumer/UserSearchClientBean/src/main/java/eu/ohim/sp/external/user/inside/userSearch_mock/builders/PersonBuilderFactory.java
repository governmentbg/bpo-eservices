package eu.ohim.sp.external.user.inside.userSearch_mock.builders;

import java.util.HashMap;
import java.util.Map;

public class PersonBuilderFactory {

	private Map<String, PersonBuilder> builderMap = new HashMap<String, PersonBuilder>();

	EServicesPersonBuilder eServicesPersonBuilder;
	DSEfilingPersonBuilder dsEfilingPersonBuilder;
	TMEfilingPersonBuilder tmEfilingPersonBuilder;

	public PersonBuilderFactory() {
		eServicesPersonBuilder = new EServicesPersonBuilder();
		dsEfilingPersonBuilder = new DSEfilingPersonBuilder();
		tmEfilingPersonBuilder = new TMEfilingPersonBuilder();
		builderMap.put("dsefiling", dsEfilingPersonBuilder);
		builderMap.put("eservices", eServicesPersonBuilder);
		builderMap.put("tmefiling", tmEfilingPersonBuilder);
	}

	public PersonBuilder getBuilder(String module) {
		PersonBuilder result = null;

		if (builderMap.containsKey(module)) {
			result = builderMap.get(module);
		} else {
			result = eServicesPersonBuilder;
		}
		return result;
	}
}
