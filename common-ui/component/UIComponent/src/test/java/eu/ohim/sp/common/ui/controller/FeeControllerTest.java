/*******************************************************************************
 * * $Id:: FeeControllerTest.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.core.configuration.ConfigurationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-context.xml",
		"classpath:/test-pojo.xml" })
@Ignore
public class FeeControllerTest {

	@Mock
	private ConfigurationService configurationService;

	@Test
	@Ignore
	public void testGetFeesInformation() {
		when(configurationService.getValue("basicFee",
						"eu.ohim.sp.tmefiling.ui.form")).thenReturn("900");
		when(configurationService.getValue("collectiveFee",
						"eu.ohim.sp.tmefiling.ui.form")).thenReturn("1800");
		when(configurationService.getValue("onlineAppDiscount",
						"eu.ohim.sp.tmefiling.ui.form")).thenReturn("150");
		when(configurationService.getValue("extraClassFeeBasic",
						"eu.ohim.sp.tmefiling.ui.form")).thenReturn("150");
		when(configurationService.getValue("extraClassFeeCollective",
						"eu.ohim.sp.tmefiling.ui.form")).thenReturn("300");
		when(configurationService.getValue("maxClasses",
						"eu.ohim.sp.tmefiling.ui.form")).thenReturn("3");
	}

	@Test
	public void testUpdateFees() {

	}
}
