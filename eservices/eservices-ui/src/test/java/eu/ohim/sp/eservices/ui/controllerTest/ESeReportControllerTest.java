package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.resources.FileContent;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.controller.ESeReportController;
import eu.ohim.sp.eservices.ui.controller.Template;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.FormUtil;



public class ESeReportControllerTest {
	
	@Mock
	private ESFlowBean flowBean;

	@Mock
	private ReportService reportService;

	@Mock
	private ESFlowBeanFactory flowBeanFactory;

	@Mock
	private FileServiceInterface fileService;

	@Mock
	private FormUtil formUtil;
	
	@Mock
	private ApplicationService applicationService;
	
	
	@InjectMocks
	ESeReportController eSeReportController=new ESeReportController();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void	getReceipt(){
		
		
		MainForm mainForm=Mockito.mock(MainForm.class);
		
		mainForm.setAddSecondSign(true);
		
		Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);
		
		FileWrapper fileWrapper=new FileWrapper();
		
		
		StoredFile storedFile=new   StoredFile();
		
		List<StoredFile> storedFile1=new ArrayList<StoredFile>();
		
		storedFile1.add(storedFile);
				
		fileWrapper.setStoredFiles(storedFile1);
		
		Mockito.when(flowBean.getMainForm().getFileWrapperImage()).thenReturn(fileWrapper);
	
		FileContent fileContent=new FileContent();
		fileContent.setName("name");
		
		Mockito.when(fileService.getFileStream(
				flowBean.getIdreserventity())).thenReturn(fileContent);
		
//		Mockito.when(flowBean.getMainForm().getFileWrapperImage()
//		.getStoredFiles()).thenReturn(storedFile1);
		
		HttpServletRequest request=Mockito.mock(HttpServletRequest.class);
		
		HttpServletResponse response=Mockito.mock(HttpServletResponse.class);
		
		Template template=new Template();
		
		template.setTemplate("template");
		
		TMeServiceApplication tmsa = new TMeServiceApplication();		
		Mockito.when(applicationService.fillApplicationDocuments(tmsa)).thenReturn(tmsa);

		
		eSeReportController.getReceipt(request, response, template);
		Mockito.when(flowBean.getMainForm().getFileWrapperImage()).thenReturn(fileWrapper);
		Mockito.when(flowBean.getMainForm().getFileWrapperImage().getStoredFiles()).thenReturn(null);
		eSeReportController.getReceipt(request, response, template);
		Mockito.when(flowBean.getMainForm().getFileWrapperImage()).thenReturn(null);
		eSeReportController.getReceipt(request, response, template);
		Mockito.when(flowBean.getMainForm()).thenReturn(null);
		eSeReportController.getReceipt(request, response, template);
				
		Mockito.when(flowBeanFactory.convertTo(flowBean)).thenReturn(tmsa);
		eSeReportController.getReceipt(request, response, template);
		List<LimitedTradeMark> tradeMarks = new ArrayList<LimitedTradeMark>();
		tmsa.setTradeMarks(tradeMarks);
		LimitedTradeMark mark = Mockito.mock(LimitedTradeMark.class);
		List<ImageSpecification> isl = new ArrayList<ImageSpecification>();
		ImageSpecification is = Mockito.mock(ImageSpecification.class);
		isl.add(is);
		Document representation = Mockito.mock(Document.class);
		representation.setUri("uri");
		Mockito.when(is.getRepresentation()).thenReturn(representation);
		Mockito.when(mark.getImageSpecifications()).thenReturn(isl);
		tradeMarks.add(mark );
		eSeReportController.getReceipt(request, response, template);
		DSeServiceApplication dssa = new DSeServiceApplication();
		Mockito.when(flowBeanFactory.convertTo(flowBean)).thenReturn(dssa);
		eSeReportController.getReceipt(request, response, template);
		
		List<DesignApplication> designDetails = new ArrayList<DesignApplication>();
		dssa.setDesignDetails(designDetails);
		DesignApplication da = Mockito.mock(DesignApplication.class);
		List<Design> detailsL = new ArrayList<Design>();
		Design detail = Mockito.mock(Design.class);
		List<DesignView> views = new ArrayList<DesignView>();
		DesignView dview= Mockito.mock(DesignView.class);
		Document document= Mockito.mock(Document.class);
		Mockito.when(document.getUri()).thenReturn("uri");
		AttachedDocument view = Mockito.mock(AttachedDocument.class);
		Mockito.when(dview.getView()).thenReturn(view);
		Mockito.when(view.getDocument()).thenReturn(document);
		views.add(dview);
		Mockito.when(detail.getViews()).thenReturn(views);
		detailsL.add(detail);
		Mockito.when(da.getDesignDetails()).thenReturn(detailsL);
		designDetails.add(da);
				
		

		
		
		tradeMarks.add(Mockito.mock(LimitedTradeMark.class));
		eSeReportController.getReceipt(request, response, template);
	}

	
	
	
	
	
	
	
	
	
}
