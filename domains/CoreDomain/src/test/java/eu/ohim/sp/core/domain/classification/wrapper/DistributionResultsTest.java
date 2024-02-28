package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.HashSet;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class DistributionResultsTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DistributionResults.class);
	}

	@Test
	public void testEquals() {
		DistributionResults distributionResults = new DistributionResults();
		distributionResults
				.setClassificationTerms(new HashSet<ClassificationTerm>());
		distributionResults.getClassificationTerms().add(
				new ClassificationTerm());

		assertEquals(distributionResults.getClassificationTerms().size(), 1);
	}

}
