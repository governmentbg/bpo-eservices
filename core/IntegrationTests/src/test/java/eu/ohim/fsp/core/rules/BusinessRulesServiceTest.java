package eu.ohim.sp.core.rules;

import eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote;
import eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList;
import eu.ohim.sp.core.configuration.domain.fees.xsd.FeeType;
import eu.ohim.sp.core.domain.trademark.MarkKind;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * User: jaraful
 * Date: 04/07/13
 * Time: 09:29
 */


public class BusinessRulesServiceTest {

	private final String _PACKAGE_ = "eu.ohim.sp.core.rules";

	BusinessRulesServiceRemote businessRulesService = null;
	SystemConfigurationServiceRemote configurationService = null;

	@Before
	public void setup() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			final Context context = new InitialContext(jndiProperties);
			configurationService = (SystemConfigurationServiceRemote) context.lookup("ejb:core-configuration-management/SystemConfigurationService//SystemConfigurationService!eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote");
			businessRulesService = (BusinessRulesServiceRemote) context.lookup("ejb:core-businessrule-management/BusinessRulesService//BusinessRulesService!eu.ohim.sp.core.rules.BusinessRulesServiceRemote");
		} catch (NamingException e) {

		}
	}

	@Test
	public void testFee() {
		FeeList feeList = null;
		feeList = configurationService.getObject("fees-configuration", _PACKAGE_+"."+"tmefiling", FeeList.class);

		Mapper mapper = new DozerBeanMapper();
		eu.ohim.sp.core.domain.payment.FeeList feeListCore = mapper.map(feeList,
				eu.ohim.sp.core.domain.payment.FeeList.class);
		for(FeeType feeType : feeList.getFeeTypeList()){
			feeListCore.getFeeTypeList().add(mapper.map(feeType, eu.ohim.sp.core.domain.payment.FeeType.class));
		}

		TradeMark tradeMark = new TradeMark();
		tradeMark.setMarkRightKind(MarkKind.INDIVIDUAL);

		if (feeList == null) {
			// TODO Insert here the error code
		}

		// Creates the generic object list to call the BRS
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(feeListCore);
		objectList.add(tradeMark);

		Map<String, Object> resultMap = businessRulesService.calculate("tmefiling", "fee_set", objectList);

		System.out.println("end");
	}
}
