package eu.ohim.sp.core.fees;

import eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.trademark.MarkKind;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.fee.FeeManagementServiceInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * User: jaraful
 * Date: 04/07/13
 * Time: 09:29
 */


public class FeeManagementTest {

	private final String _PACKAGE_ = "eu.ohim.sp.core.rules";

	FeeManagementServiceInterface feeManagementService = null;
	SystemConfigurationServiceRemote configurationService = null;

	@Before
	public void setup() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			final Context context = new InitialContext(jndiProperties);
			configurationService = (SystemConfigurationServiceRemote) context.lookup("ejb:core-configuration-management/SystemConfigurationService//SystemConfigurationService!eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote");
			feeManagementService = (FeeManagementServiceInterface) context.lookup("ejb:core-fee-management/FeeManagementService//FeeManagementService!eu.ohim.sp.core.fee.FeeManagementServiceInterface");
		} catch (NamingException e) {

		}
	}

	@Test
	public void testFee() {
		TradeMark tradeMark = new TradeMark();
		tradeMark.setMarkRightKind(MarkKind.INDIVIDUAL);

		eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList feeList = configurationService.getObject("fees-configuration", _PACKAGE_+".tmefiling", eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList.class);

		// Creates the generic object list to call the BRS
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(tradeMark);

		List<Fee> resultList = feeManagementService.calculateFees("tmefiling", objectList);

        System.out.println(resultList.get(0).getAmount());
        System.out.println(resultList.get(1).getAmount());
        Assert.assertEquals(resultList.size(), 2);
	}
}
