package eu.ohim.sp.core.domain.util;

import eu.ohim.sp.filing.domain.utils.LogTimeInterceptor;
import org.junit.Test;

public class LogTimeInterceptorTest {

	@Test(expected=Exception.class)
	public void testLogTimeInterceptorNullExpected() throws Exception {
		LogTimeInterceptor logTimeInterceptor = new LogTimeInterceptor();
		logTimeInterceptor.handleException(null);
	}

}
