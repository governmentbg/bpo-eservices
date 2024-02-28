package eu.ohim.sp.ui.tmefiling.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TriggerContext;

import eu.ohim.sp.ui.tmefiling.scheduler.trigger.OnOffTrigger;

public class OnOffTriggerTest {

	@Mock
	private TriggerContext triggerContext;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testEnable() {
		OnOffTrigger t = new OnOffTrigger(1000);
		assertFalse(t.isEnabled());
		t.setEnabled(true);
		assertTrue(t.isEnabled());

		assertNotNull(t.nextExecutionTime(triggerContext));

	}

	@Test
	public void testDisable() {
		OnOffTrigger t = new OnOffTrigger(1000);
		assertFalse(t.isEnabled());
		t.setEnabled(true);
		assertTrue(t.isEnabled());
		t.setEnabled(false);
		assertFalse(t.isEnabled());
		assertNull(t.nextExecutionTime(triggerContext));

	}
}
