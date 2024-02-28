package eu.ohim.sp.eservices.ui.controllerTest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eu.ohim.sp.eservices.ui.controller.Template;

public class TemplateTest {

	@Mock
	private String template;
	
	@InjectMocks
	Template templateTest=new Template();
	   
	@Test
	public void getTemplate(){
		
		
		templateTest.getTemplate();
		
	}
	
	@Test
	public void setTemplate(){
		
		
		templateTest.setTemplate("template");
		
	}
	
	
}
