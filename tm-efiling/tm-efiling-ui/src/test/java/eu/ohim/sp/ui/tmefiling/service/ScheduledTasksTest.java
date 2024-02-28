package eu.ohim.sp.ui.tmefiling.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import eu.ohim.sp.ui.tmefiling.scheduler.ScheduledTasks;
import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;


@RunWith(MockitoJUnitRunner.class)
public class ScheduledTasksTest {
	
	@Mock
	private GoodsServicesServiceInterface service;
	
	
	@InjectMocks
	private ScheduledTasks scheduledTasks;
		
	
	@Before
	public void before(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void test() {
		
		scheduledTasks.setNumberOfClasses("45");
		//scheduledTasks.setGoodsServicesService(mock(GoodsServicesServiceInterface.class));
		String lang= "hu,en,asdfa";
		int times = lang.split(",").length;
		scheduledTasks.setPreloadLanguages(lang);
		assertEquals("45", scheduledTasks.getNumberOfClasses());
		assertEquals(lang, scheduledTasks.getPreloadLanguages());
		
		scheduledTasks.preloadNiceClassHeadings();
		
		for(int j=0; j < times; j++) {
			String l = lang.split(",")[j];
		for(int i =  1; i <=45; i++) {
			verify(service, times(1)).importNiceClassHeading(i+"", l);
		}
		
		
		}
	}
	
	@Test
	public void testNulls() {
		ScheduledTasks scheduledTasks = new ScheduledTasks();
		assertEquals(null, scheduledTasks.getNumberOfClasses());
		assertEquals(null, scheduledTasks.getPreloadLanguages());
		scheduledTasks.preloadNiceClassHeadings();
		
	}
}
