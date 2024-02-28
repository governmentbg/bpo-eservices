package eu.ohim.sp.eservices.ui.serviceTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;
import eu.ohim.sp.eservices.ui.service.DSLocarnoService;
import eu.ohim.sp.eservices.ui.service.interfaces.LocarnoServiceInterface;


public class DSLocarnoServiceTest{

	@Mock
    private LocarnoServiceInterface locarnoService;
    
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}
	 
	@InjectMocks
	DSLocarnoService dSLocarnoService=new DSLocarnoService();
	
	@Test
	public void getLocarnoClasses() {
		String lang="";
		ProductIndicationClass productIndicationClass=Mockito.mock(ProductIndicationClass.class);
		Mockito.when(productIndicationClass.getMainClass()).thenReturn("123Class");
		List<ProductIndicationClass> listProductIndicationClass=new ArrayList<ProductIndicationClass>();
		listProductIndicationClass.add(productIndicationClass);
		
		ProductIndication productIndication=Mockito.mock(ProductIndication.class);
		Mockito.when(productIndication.getClasses()).thenReturn(listProductIndicationClass);
		List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
		productIndications.add(productIndication);
		Mockito.when(locarnoService.getFullClassification(Mockito.anyString())).thenReturn(productIndications);

		Assert.assertNotNull(dSLocarnoService.getLocarnoClasses(lang));
	}
	
	@Test
	public void getLocarnoSubclasses() {
		String clazz="123Class";
		String lang="";
		
		ProductIndicationClass productIndicationClass=Mockito.mock(ProductIndicationClass.class);
		Mockito.when(productIndicationClass.getMainClass()).thenReturn("123Class");
		List<ProductIndicationClass> listProductIndicationClass=new ArrayList<ProductIndicationClass>();
		listProductIndicationClass.add(productIndicationClass);
		
		ProductIndication productIndication=Mockito.mock(ProductIndication.class);
		Mockito.when(productIndication.getClasses()).thenReturn(listProductIndicationClass);
		List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
		productIndications.add(productIndication);
		Mockito.when(locarnoService.getFullClassification(Mockito.anyString())).thenReturn(productIndications);
 		
		Assert.assertNotNull(dSLocarnoService.getLocarnoSubclasses(clazz,lang));
	}
	
	@Test
	public void getLocarnoClassifications() {
		String indication="indication";
		String clazz="123Class"; 
		String subClass="subclass";
		String lang="";
		
		ProductIndicationTerm productIndicationTerm=Mockito.mock(ProductIndicationTerm.class);
		Mockito.when(productIndicationTerm.getText()).thenReturn("text");
		Mockito.when(productIndicationTerm.getIndprodID()).thenReturn("getIndprodID");
		List<ProductIndicationTerm> listProductIndicationTerm=new ArrayList<ProductIndicationTerm>();
		listProductIndicationTerm.add(productIndicationTerm);
		
		ProductIndicationClass productIndicationClass=Mockito.mock(ProductIndicationClass.class);
		Mockito.when(productIndicationClass.getMainClass()).thenReturn("123Class");
		Mockito.when(productIndicationClass.getSubClass()).thenReturn("subclass");
		Mockito.when(productIndicationClass.getTerms()).thenReturn(listProductIndicationTerm);
		List<ProductIndicationClass> listProductIndicationClass=new ArrayList<ProductIndicationClass>();
		listProductIndicationClass.add(productIndicationClass);
		
		ProductIndication productIndication=Mockito.mock(ProductIndication.class);
		Mockito.when(productIndication.getClasses()).thenReturn(listProductIndicationClass);
		List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
		productIndications.add(productIndication);
		Mockito.when(locarnoService.searchProductIndication(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(productIndications);
 		
		Assert.assertNotNull(dSLocarnoService.getLocarnoClassifications(indication,clazz,subClass,lang));
		
	}
	
}
