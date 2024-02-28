/*******************************************************************************
 * * $Id:: UserPersonDetailsTest.java 157090 2013-12-02 11:54:19Z velasca        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.user;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.person.Representative;
import org.junit.Test;

import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class UserPersonDetailsTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(UserPersonDetails.class);
	}

	@Test
	public void testGetListsNotNull() {
		UserPersonDetails userPersonDetails = new UserPersonDetails();
		assertTrue(userPersonDetails.getApplicants() != null);
		assertTrue(userPersonDetails.getDesigners() != null);
		assertTrue(userPersonDetails.getRepresentatives() != null);
	}
	
	@Test
	public void testEquals() {
		List<Applicant> applicantList = new ArrayList<Applicant>();
		applicantList.add(new Applicant());
		List<Designer> designerList = new ArrayList<Designer>();
		designerList.add(new Designer());
		List<Representative> representativeList = new ArrayList<Representative>();
		representativeList.add(new Representative());

		UserPersonDetails userPersonDetails = new UserPersonDetails();
		userPersonDetails.setApplicants(applicantList);
		userPersonDetails.setDesigners(designerList);
		userPersonDetails.setId(1);
		userPersonDetails.setRepresentatives(representativeList);

		assertEquals(userPersonDetails.getApplicants().size(), 1);
		assertEquals(userPersonDetails.getDesigners().size(), 1);
		assertEquals(userPersonDetails.getRepresentatives().size(), 1);
	}

}
