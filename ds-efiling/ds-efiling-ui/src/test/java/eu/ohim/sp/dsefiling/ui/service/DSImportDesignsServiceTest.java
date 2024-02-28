package eu.ohim.sp.dsefiling.ui.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.design.DesignFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignViewFactory;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * @author ionitdi
 */
public class DSImportDesignsServiceTest
{
    DSFlowBeanImpl flowBean;

    @Mock
    DesignSearchService designService;

    @Mock
    DSDesignsServiceInterface dsDesignsService;

    @Mock
    DesignFactory designFactory;

    @Mock
    DSFlowBeanFactory dsFlowBeanFactory;

    @Mock
    DesignViewFactory designViewFactory;

    @Mock
    DSFileService fileService;

    @InjectMocks
    DSImportDesignsService importDesignsService = new DSImportDesignsService();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        flowBean = new DSFlowBeanImpl();
    }

    @Test
    public void importDesignTest1()
    {
        DesignForm form = new DesignForm();
        form.setId("1");

        Design core = new Design();
        core.setId(1);
        core.setViews(new ArrayList<DesignView>());

        DesignView coreView = new DesignView();
        coreView.setDescription("view 1");
        coreView.setView(new AttachedDocument());
        coreView.getView().setDocument(new Document());

        core.getViews().add(coreView);

        when(designService.getDesign(any(String.class), eq("1"))).thenReturn(core);
        when(designFactory.convertFrom(core)).thenReturn(form);

        DesignForm result = importDesignsService.importDesign("1", flowBean);

        assertEquals("1", result.getId());
    }

    @Test
    public void importDesignTest2()
    {
        DesignForm form = new DesignForm();
        form.setId("1");
        form.setViews(new ArrayList<DesignViewForm>());
        DesignViewForm view = new DesignViewForm();
        view.setDescription("view 1");
        form.getViews().add(view);

        Design core = new Design();
        core.setId(1);
        core.setViews(new ArrayList<DesignView>());

        DesignView coreView = new DesignView();
        coreView.setDescription("view 1");
        coreView.setView(new AttachedDocument());
        coreView.getView().setDocument(new Document());
        coreView.getView().getDocument().setData(new byte[10]);

        core.getViews().add(coreView);

        when(designService.getDesign(any(String.class), eq("1"))).thenReturn(core);
        when(designFactory.convertFrom(core)).thenReturn(form);
        when(designViewFactory.convertFrom(any(DesignView.class))).thenReturn(new DesignViewForm());

        DesignForm result = importDesignsService.importDesign("1", flowBean);

        assertEquals("1", result.getId());

    }

    @Test
    public void importDesignTest3() throws IOException
    {
        DesignForm form = new DesignForm();
        form.setId("1");
        form.setViews(new ArrayList<DesignViewForm>());
        DesignViewForm view = new DesignViewForm();
        view.setDescription("view 1");
        form.getViews().add(view);

        Design core = new Design();
        core.setId(1);
        core.setViews(new ArrayList<DesignView>());

        DesignView coreView = new DesignView();
        coreView.setDescription("view 1");
        coreView.setView(new AttachedDocument());
        coreView.getView().setDocument(new Document());
        byte[] docData = new byte[10];
        coreView.getView().getDocument().setData(new byte[10]);

        core.getViews().add(coreView);

        when(designService.getDesign(any(String.class), eq("1"))).thenReturn(core);
        when(designFactory.convertFrom(core)).thenReturn(form);

        when(fileService.addFile(any(String.class), any(String.class), any(String.class), eq(docData), any(Boolean.class))).thenThrow(new IOException());

        DesignForm result = importDesignsService.importDesign("1", flowBean);

        assertNull(result);
    }

    @Test
    public void importDesignApplicationTest1()
    {
        DSFlowBeanImpl importedFlow = new DSFlowBeanImpl();
        DesignApplication core = new DesignApplication();
        core.setApplicationNumber("fds");
        Design design = new Design();

        core.setDesignDetails(new ArrayList<Design>());
        core.getDesignDetails().add(design);

        when(dsFlowBeanFactory.convertFrom(any(DesignApplication.class))).thenReturn(importedFlow);
        when(designService.getDesignApplication(any(String.class), eq("1"), any(String.class), eq(Boolean.TRUE))).thenReturn(core);

        importDesignsService.importDesignApplication("1", null, flowBean);

    }

    @Test
    public void importDesignApplicationTest2()
    {
        DSFlowBeanImpl importedFlow = new DSFlowBeanImpl();
        DesignForm designForm = new DesignForm();

        DesignViewForm viewForm = new DesignViewForm();
        viewForm.setDescription("view 1");

        DesignApplication core = new DesignApplication();
        core.setApplicationNumber("fds");
        Design design = new Design();

        DesignView view = new DesignView();
        view.setDescription("view 1");

        view.setView(new AttachedDocument());
        view.getView().setDocument(new Document());
        view.getView().getDocument().setData(new byte[10]);

        design.setViews(new ArrayList<DesignView>());
        design.getViews().add(view);

        core.setDesignDetails(new ArrayList<Design>());
        core.getDesignDetails().add(design);

        when(dsFlowBeanFactory.convertFrom(any(DesignApplication.class))).thenReturn(importedFlow);
        when(designService.getDesignApplication(any(String.class), eq("1"), any(String.class), eq(Boolean.TRUE))).thenReturn(core);
        when(designFactory.convertFrom(design)).thenReturn(designForm);
        when(designViewFactory.convertFrom(any(DesignView.class))).thenReturn(viewForm);

        importDesignsService.importDesignApplication("1", null, flowBean);

        assertEquals("view 1", flowBean.getDesigns().get(0).getViews().get(0).getDescription());
    }
}
