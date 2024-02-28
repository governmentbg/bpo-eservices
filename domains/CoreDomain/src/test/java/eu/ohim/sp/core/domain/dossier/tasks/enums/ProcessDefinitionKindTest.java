package eu.ohim.sp.core.domain.dossier.tasks.enums;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ProcessDefinitionKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ProcessDefinitionKind.class);
	}

	@Test
	public void testValues() {
		ProcessDefinitionKind kindAdhoc = ProcessDefinitionKind.TRADEMARK_ADHOC;
		ProcessDefinitionKind kindLetter = ProcessDefinitionKind.TRADEMARK_LETTER;
		ProcessDefinitionKind kindMain = ProcessDefinitionKind.TRADEMARK_MAIN;
		ProcessDefinitionKind kindSubProcess = ProcessDefinitionKind.TRADEMARK_SUBPROCESS;

		assertEquals(kindAdhoc.getValue(),
				ProcessDefinitionKind.TRADEMARK_ADHOC.getValue());
		assertEquals(kindLetter.getValue(),
				ProcessDefinitionKind.TRADEMARK_LETTER.getValue());
		assertEquals(kindMain.getValue(),
				ProcessDefinitionKind.TRADEMARK_MAIN.getValue());
		assertEquals(kindSubProcess.getValue(),
				ProcessDefinitionKind.TRADEMARK_SUBPROCESS.getValue());

	}

}
