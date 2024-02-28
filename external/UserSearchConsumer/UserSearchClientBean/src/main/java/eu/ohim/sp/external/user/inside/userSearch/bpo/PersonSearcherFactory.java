package eu.ohim.sp.external.user.inside.userSearch.bpo;

import java.util.HashMap;
import java.util.Map;

public class PersonSearcherFactory {
	private DSEfilingPersonSearcher dsEfilingPersonSearcher;
	private TMEfilingPersonSearcher tMEfilingPersonSearcher;
	private PTEfilingPersonSearcher pTEfilingPersonSearcher;
	private static PersonSearcherFactory instance;

	private PersonSearcherFactory () {
		dsEfilingPersonSearcher = new DSEfilingPersonSearcher();
		tMEfilingPersonSearcher = new TMEfilingPersonSearcher();
		pTEfilingPersonSearcher = new PTEfilingPersonSearcher();

	}
	public static PersonSearcherFactory getInstance() {
		if (instance == null) {
			synchronized (PersonSearcherFactory.class) {
				if (instance == null) {
					instance = new PersonSearcherFactory();
				}
			}
		}
		return instance;
	}

	public PersonSearcher getSearcher(String module) {
		if ("dsefiling".equals(module) || module.startsWith("ds-")) {
			return dsEfilingPersonSearcher;
		} else if ("tmefiling".equals(module) || module.startsWith("tm-") || module.startsWith("ol-")) {
			return tMEfilingPersonSearcher;
		} else if ("ptefiling".equals(module) || module.startsWith("pt-") || module.startsWith("um-") || module.startsWith("ep-") || module.startsWith("sv-") || module.startsWith("spc-") || module.startsWith("is-")) {
			return pTEfilingPersonSearcher;
		}else {
			throw new RuntimeException("Unknown module..." + module);
		}
	}
}
