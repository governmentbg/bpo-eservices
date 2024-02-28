package eu.ohim.sp.external.domain.application;

import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 28/1/2014
 * Time: 3:51 πμ
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationTest {
    @Test
    public void testDraftApplication() {
        ObjectFactory objectFactory = new ObjectFactory();
        DraftApplication draftApplication = objectFactory.createDraftApplication();
        draftApplication.setApplicationId("004016648");
        draftApplication.setDateCreated(new Date());
        draftApplication.setCurrentStatus(objectFactory.createDraftStatus());
        draftApplication.getCurrentStatus().setMessage("message");
        draftApplication.getCurrentStatus().setModifiedDate(new Date());
        draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_SUBMITTED);
        draftApplication.setOffice("EM");
        draftApplication.setProvisionalId("EMEF0000000001");
        draftApplication.setUsername("username");
        draftApplication.setPaymentId("payment");
        draftApplication.setStatuses(new ArrayList<DraftStatus>());

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.application.DraftApplication draftApplicationCore = dozerBeanMapper.map(draftApplication, eu.ohim.sp.core.domain.application.DraftApplication.class);

        Assert.assertEquals(draftApplication.getApplicationId(), draftApplicationCore.getApplicationId());
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getPaymentId(), draftApplicationCore.getPaymentId());
        Assert.assertEquals(draftApplication.getProvisionalId(), draftApplicationCore.getProvisionalId());
        Assert.assertEquals(draftApplication.getUsername(), draftApplicationCore.getUsername());
        Assert.assertEquals(draftApplication.getCurrentStatus().getMessage(), draftApplicationCore.getCurrentStatus().getMessage());
        Assert.assertEquals(draftApplication.getCurrentStatus().getStatus().value(), draftApplicationCore.getCurrentStatus().getStatus().name());
        Assert.assertEquals(draftApplication.getCurrentStatus().getModifiedDate(), draftApplicationCore.getCurrentStatus().getModifiedDate());
        // to be checked
        //Assert.assertEquals(draftApplication.getDateCreated(), draftApplicationCore.getDtCreated());

        draftApplication = new DraftApplication("username", "applicationId", "provisionalId", "office", new ArrayList<DraftStatus>(), new DraftStatus(new Date(), "message", ApplicationStatus.STATUS_EXPORTED), "paymentId", new Date());

        draftApplicationCore = dozerBeanMapper.map(draftApplication, eu.ohim.sp.core.domain.application.DraftApplication.class);
        Assert.assertEquals(draftApplication.getApplicationId(), draftApplicationCore.getApplicationId());
        Assert.assertEquals(draftApplication.getOffice(), draftApplicationCore.getOffice());
        Assert.assertEquals(draftApplication.getPaymentId(), draftApplicationCore.getPaymentId());
        Assert.assertEquals(draftApplication.getProvisionalId(), draftApplicationCore.getProvisionalId());
        Assert.assertEquals(draftApplication.getUsername(), draftApplicationCore.getUsername());
        Assert.assertEquals(draftApplication.getCurrentStatus().getMessage(), draftApplicationCore.getCurrentStatus().getMessage());
        Assert.assertEquals(draftApplication.getCurrentStatus().getStatus().value(), draftApplicationCore.getCurrentStatus().getStatus().name());
        Assert.assertEquals(draftApplication.getCurrentStatus().getModifiedDate(), draftApplicationCore.getCurrentStatus().getModifiedDate());

        // to be checked
        //Assert.assertEquals(draftApplication.getDateCreated(), draftApplicationCore.getDtCreated());
    }


    @Test
    public void testApplicationNumber() {
        ObjectFactory objectFactory = new ObjectFactory();
        NumberingResult numberingResult = objectFactory.createNumberingResult();
        numberingResult.setNumber("number");
        numberingResult.setTimestamp(new Date());

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        ApplicationNumber applicationNumber = dozerBeanMapper.map(numberingResult, ApplicationNumber.class);
        Assert.assertEquals(applicationNumber.getNumber(), numberingResult.getNumber());
        //TO BE FIXED
        //Assert.assertEquals(applicationNumber.getDate(), numberingResult.getTimestamp());
    }

}
