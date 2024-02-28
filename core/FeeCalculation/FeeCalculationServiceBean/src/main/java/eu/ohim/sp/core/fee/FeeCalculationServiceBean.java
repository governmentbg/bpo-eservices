/*
 *  FeeManagementService:: FeeManagementService 07/10/13 21:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.fee;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeList;
import eu.ohim.sp.core.domain.payment.FeeType;
import eu.ohim.sp.core.rules.RulesService;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class FeeCalculationServiceBean implements FeeCalculationServiceLocal, FeeCalculationServiceRemote {
	
	private static final Logger LOGGER = Logger.getLogger(FeeCalculationServiceBean.class);

	@EJB(lookup="java:global/configurationLocal")
	private ConfigurationService configurationService;

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService rulesService;

	private static final String FEE_SET = "fee_set";

	private static final String PACKAGE = "eu.ohim.sp.core.rules";
	
	/**
	 * {@inheritDoc}
	 */
	public List<Fee> calculateFees(String module, List<Object> objects){
		LOGGER.debug(" >>> calculateFees START");

		// Gets the list from the configuration service
		eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList feeList =
                configurationService.getObject("fees-configuration", PACKAGE+"."+module, eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList.class);

        LOGGER.debug(" - List of fees obtained");

		// Converts from XSD to Core
		Mapper mapper = new DozerBeanMapper();
		FeeList feeListCore = mapper.map(feeList, FeeList.class);
		for(eu.ohim.sp.core.configuration.domain.fees.xsd.FeeType feeType : feeList.getFeeTypeList()){
			feeListCore.getFeeTypeList().add(mapper.map(feeType, FeeType.class));
		}

		objects.add(feeListCore);
		LOGGER.debug("		> Objects added");
		
		// Fires the business rules
		LOGGER.debug("		> Fire the rules");
		Map<String, Object> resultMap = rulesService.calculate(module, FEE_SET, objects);

		if(resultMap == null){
			LOGGER.debug("		> Error: the result map is empty");
			return null;
		}

		// Turn the result into a Fee list
		List<Object> objectList = new ArrayList<Object>(resultMap.values());
		List<Fee> resultList = new ArrayList<Fee>();

		for(Object object : objectList){
			if(object instanceof Fee){
				resultList.add((Fee) object);
			}
		}

		if(resultList.isEmpty()){
			LOGGER.debug("		> Error: No fee objects could be found on the result");
		}

		LOGGER.debug(" <<< calculateFees END");
		return resultList;
	}

	public Map<String, FeeList> getAllModulesFeesConfigured(Boolean includeConfigurationFees){
		Map<String, FeeList> allFees = new HashMap<>();
		Collection<String> modulesList =
				configurationService.getValueList("rules_map", "eu.ohim.sp.core.rules");
		for(String module: modulesList) {
			eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList feeList = null;
			try {
				feeList = configurationService.getObject("fees-configuration", PACKAGE + "." + module, eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList.class);
			} catch (Exception e){
				LOGGER.debug("fess-configuration reading problem for module "+module);
				continue;
			}
			if (feeList != null) {
				Mapper mapper = new DozerBeanMapper();
				FeeList feeListCore = mapper.map(feeList, FeeList.class);
				for (eu.ohim.sp.core.configuration.domain.fees.xsd.FeeType feeType : feeList.getFeeTypeList()) {
					feeListCore.getFeeTypeList().add(mapper.map(feeType, FeeType.class));
				}

				if (feeListCore.getFeeTypeList() != null && feeListCore.getFeeTypeList().size() > 0) {
					if (includeConfigurationFees != null && !includeConfigurationFees) {
						List<FeeType> filteredFees = feeListCore.getFeeTypeList().stream().filter(
								fee -> fee.getConfigFee() == null || !fee.getConfigFee()
						).collect(Collectors.toList());
						feeListCore.getFeeTypeList().clear();
						feeListCore.getFeeTypeList().addAll(filteredFees);
					}
					allFees.put(module, feeListCore);
				}
			}
		}

		return allFees;
	}
}
