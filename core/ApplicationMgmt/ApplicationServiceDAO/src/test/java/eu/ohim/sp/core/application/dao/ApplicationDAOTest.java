package eu.ohim.sp.core.application.dao;

import eu.ohim.sp.core.application.DraftApplication;
import eu.ohim.sp.core.application.DraftStatus;
import eu.ohim.sp.core.application.Status;
import eu.ohim.sp.core.application.TypeApplication;
import eu.ohim.sp.core.domain.application.ApplicationStatus;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/02/14
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationDAOTest {

    @InjectMocks
    private ApplicationDAO applicationDAO;

    @Mock
    private EntityManager em;

    @Mock
    private Query queryTypeApplication;

    @Mock
    private Query queryStatus;

    @Mock
    private Query queryDraftApplication;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateStatus() {
        eu.ohim.sp.core.domain.application.DraftApplication draftApplicationCore = new eu.ohim.sp.core.domain.application.DraftApplication();
        draftApplicationCore.setOffice("EM");
        draftApplicationCore.setUsername("username");
        draftApplicationCore.setProvisionalId("provisionalId");
        draftApplicationCore.setCurrentStatus(new eu.ohim.sp.core.domain.application.DraftStatus());
        draftApplicationCore.getCurrentStatus().setStatus(ApplicationStatus.STATUS_SUBMITTED);
        draftApplicationCore.setTyApplication("TM");

        Mockito.when(em.createNamedQuery("TypeApplication.getTypeByDescription")).thenReturn(queryTypeApplication);
        Mockito.when(queryTypeApplication.setParameter(Matchers.eq("description"), Matchers.eq("TM"))).thenReturn(queryTypeApplication);
        TypeApplication typeApplication = new TypeApplication();
        typeApplication.setTypeApplication("TM");
        typeApplication.setId(new Long(1));
        Mockito.when(queryTypeApplication.getSingleResult()).thenReturn(typeApplication);

        Mockito.when(em.createNamedQuery("DraftStatus.getStatus")).thenReturn(queryStatus);
        Mockito.when(queryStatus.setParameter(Matchers.eq("code"), Matchers.eq(ApplicationStatus.STATUS_SUBMITTED.getCode()))).thenReturn(queryStatus);
        Status status = new Status();
        status.setCode(ApplicationStatus.STATUS_SUBMITTED.getCode());
        status.setId(new Long(1));
        Mockito.when(queryStatus.getSingleResult()).thenReturn(status);

        Mockito.when(em.createNamedQuery("DraftApplication.findApplicationByProvisionalId")).thenReturn(queryDraftApplication);
        Mockito.when(queryDraftApplication.setParameter(Matchers.eq("provisionalId"), Matchers.eq("provisionalId"))).thenReturn(queryDraftApplication);
        DraftApplication draftApplicationPersisited = new DraftApplication();
        draftApplicationPersisited.setProvisionalId("provisionalId");
        draftApplicationPersisited.setId(new Long(1));
        draftApplicationPersisited.setDtCreated(new Date());
        draftApplicationPersisited.setApplicationId("applicationId");
        Mockito.when(queryDraftApplication.getSingleResult()).thenReturn(draftApplicationPersisited);

        DraftApplication draftApplication = applicationDAO.updateStatus(draftApplicationCore);
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getUsername(), draftApplicationCore.getUsername());
        Assert.assertEquals(draftApplication.getCurrentStatus().getCode(), draftApplicationCore.getCurrentStatus().getStatus().getCode());
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getProvisionalId(), draftApplicationCore.getProvisionalId());
        Assert.assertEquals(draftApplicationPersisited.getDtCreated(), draftApplication.getDtCreated());
        Assert.assertEquals(draftApplicationPersisited.getApplicationId(), draftApplication.getApplicationId());
    }



    @Test
    public void testUpdateStatusNotExisting() {
        eu.ohim.sp.core.domain.application.DraftApplication draftApplicationCore = new eu.ohim.sp.core.domain.application.DraftApplication();
        draftApplicationCore.setOffice("EM");
        draftApplicationCore.setUsername("username");
        draftApplicationCore.setProvisionalId("provisionalId");
        draftApplicationCore.setCurrentStatus(new eu.ohim.sp.core.domain.application.DraftStatus());
        draftApplicationCore.getCurrentStatus().setStatus(ApplicationStatus.STATUS_SUBMITTED);
        draftApplicationCore.setTyApplication("TM");

        Mockito.when(em.createNamedQuery("DraftApplication.findApplicationByProvisionalId")).thenReturn(queryDraftApplication);
        Mockito.when(queryDraftApplication.setParameter(Matchers.eq("provisionalId"), Matchers.eq("provisionalId"))).thenReturn(queryDraftApplication);
        Mockito.when(queryDraftApplication.getSingleResult()).thenReturn(null);

        Mockito.when(em.createNamedQuery("TypeApplication.getTypeByDescription")).thenReturn(queryTypeApplication);
        Mockito.when(queryTypeApplication.setParameter(Matchers.eq("description"), Matchers.eq("TM"))).thenReturn(queryTypeApplication);
        TypeApplication typeApplication = new TypeApplication();
        typeApplication.setTypeApplication("TM");
        typeApplication.setId(new Long(1));
        Mockito.when(queryTypeApplication.getSingleResult()).thenReturn(typeApplication);

        Mockito.when(em.createNamedQuery("DraftStatus.getStatus")).thenReturn(queryStatus);
        Mockito.when(queryStatus.setParameter(Matchers.eq("code"), Matchers.eq(ApplicationStatus.STATUS_SUBMITTED.getCode()))).thenReturn(queryStatus);
        Status status = new Status();
        status.setCode(ApplicationStatus.STATUS_SUBMITTED.getCode());
        status.setId(new Long(1));
        Mockito.when(queryStatus.getSingleResult()).thenReturn(status);


        DraftApplication draftApplication = applicationDAO.updateStatus(draftApplicationCore);
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getUsername(), draftApplicationCore.getUsername());
        Assert.assertEquals(draftApplication.getCurrentStatus().getCode(), draftApplicationCore.getCurrentStatus().getStatus().getCode());
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getProvisionalId(), draftApplicationCore.getProvisionalId());
    }


    @Test
    public void testGetDraftApplication() {
        DraftApplication draftApplication = new DraftApplication();

        draftApplication.setOffice("EM");
        draftApplication.setId(new Long(1));
        draftApplication.setApplicationId("004016648");
        draftApplication.setCurrentStatus(new Status());
        draftApplication.getCurrentStatus().setId(new Long(1));
        draftApplication.getCurrentStatus().setCode(ApplicationStatus.STATUS_CC_PAYMENT_DONE.getCode());
        draftApplication.getCurrentStatus().setDescription("Payment Done");
        draftApplication.setPaymentId("paymentId");
        draftApplication.setProvisionalId("EMEF2013000000000001");
        draftApplication.setDtCreated(new Date());
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication("TM");
        draftApplication.setUsername("username");
        draftApplication.setStatuses(new HashSet<DraftStatus>());

        DraftStatus draftStatus = new DraftStatus();
        draftStatus.setMessage("init");
        draftStatus.setId(new Long(1));
        draftStatus.setCode(new Status());
        draftStatus.getCode().setCode(ApplicationStatus.STATUS_INITIALIZED.getCode());
        draftStatus.setModifiedDate(draftApplication.getDtCreated());
        draftApplication.getStatuses().add(draftStatus);

        draftStatus = new DraftStatus();
        draftStatus.setMessage("payment done");
        draftStatus.setId(new Long(2));
        draftStatus.setCode(new Status());
        draftStatus.getCode().setCode(ApplicationStatus.STATUS_CC_PAYMENT_DONE.getCode());
        draftStatus.setModifiedDate(new Date());
        draftApplication.getStatuses().add(draftStatus);

        eu.ohim.sp.core.domain.application.DraftApplication draftApplicationCore = applicationDAO.getDraftApplication(draftApplication);

        Assert.assertEquals(draftApplication.getApplicationId(), draftApplicationCore.getApplicationId());
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getPaymentId(), draftApplicationCore.getPaymentId());
        Assert.assertEquals(draftApplication.getProvisionalId(), draftApplicationCore.getProvisionalId());
        Assert.assertEquals(draftApplication.getDtCreated(), draftApplicationCore.getDtCreated());
        Assert.assertEquals(draftApplication.getUsername(), draftApplicationCore.getUsername());
        Assert.assertEquals(draftApplication.getCurrentStatus().getCode(), draftApplicationCore.getCurrentStatus().getStatus().getCode());
        Assert.assertEquals(draftApplication.getTyApplication().getTypeApplication(), draftApplicationCore.getTyApplication());
        //Assert.assertEquals(draftApplication.getStatuses().size(), draftApplicationCore.getStatuses().size());

        //It seems to have different behavior when it is running alone as test, than in the full test
//        Iterator<DraftStatus> iterator = draftApplication.getStatuses().iterator();
//        while (iterator.hasNext()) {
//            DraftStatus draftStatusIterator = iterator.next();
//            Iterator<eu.ohim.sp.core.domain.application.DraftStatus> iteratorCore = draftApplicationCore.getStatuses().iterator();
//            boolean exists = false;
//            while (iteratorCore.hasNext()) {
//                eu.ohim.sp.core.domain.application.DraftStatus draftStatusCore = iteratorCore.next();
//                if (draftStatusIterator.getModifiedDate().equals(draftStatusCore.getModifiedDate())
//                        && draftStatusIterator.getCode().getCode().equals(draftStatusCore.getStatus().getCode())) {
//                    exists = true;
//                }
//            }
//            Assert.assertTrue(exists);
//        }

    }
}
