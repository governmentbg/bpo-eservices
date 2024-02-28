package eu.ohim.sp.external.services.applicant;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Applicant;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 27/01/14
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class ApplicantTest {
    @Test
    public void testGetApplicantAutocomplete() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetApplicantAutocomplete getApplicantAutocomplete = objectFactory.createGetApplicantAutocomplete();
        getApplicantAutocomplete.setModule("TM");
        getApplicantAutocomplete.setOffice("EM");
        getApplicantAutocomplete.setText("text");
        getApplicantAutocomplete.setNumberOfRows(5);
        getApplicantAutocomplete = new GetApplicantAutocomplete("EM", "text", 5, "TM");

        GetApplicantAutocompleteResponse getApplicantAutocompleteResponse = objectFactory.createGetApplicantAutocompleteResponse();
        getApplicantAutocompleteResponse.setReturnedObject("sth");
    }

    @Test
    public void testGetApplicant() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetApplicant getApplicant = objectFactory.createGetApplicant();
        getApplicant.setModule("TM");
        getApplicant.setOffice("EM");
        getApplicant.setApplicantId("123132");
        GetApplicant getApplicantConst = new GetApplicant("TM", "EM", "123132");
        Assert.assertEquals(getApplicant.getModule(), getApplicantConst.getModule());
        Assert.assertEquals(getApplicant.getOffice(), getApplicantConst.getOffice());
        Assert.assertEquals(getApplicant.getApplicantId(), getApplicantConst.getApplicantId());

        GetApplicantResponse getApplicantResponse = objectFactory.createGetApplicantResponse();
        getApplicantResponse.setReturnedObject(new Applicant());

    }

    @Test
    public void testSearchApplicant() {
        ObjectFactory objectFactory = new ObjectFactory();
        SearchApplicant searchApplicant = objectFactory.createSearchApplicant();
        searchApplicant.setModule("TM");
        searchApplicant.setOffice("EM");
        searchApplicant.setApplicantId("123132");
        searchApplicant.setApplicantCountry("UK");
        searchApplicant.setApplicantName("name");
        searchApplicant.setNumberOfResults(5);
        SearchApplicant searchApplicantConst = new SearchApplicant("TM", "EM", "123132", "name", "UK", 5);
        Assert.assertEquals(searchApplicant.getModule(), searchApplicantConst.getModule());
        Assert.assertEquals(searchApplicant.getOffice(), searchApplicantConst.getOffice());
        Assert.assertEquals(searchApplicant.getApplicantId(), searchApplicantConst.getApplicantId());
        Assert.assertEquals(searchApplicant.getApplicantCountry(), searchApplicantConst.getApplicantCountry());
        Assert.assertEquals(searchApplicant.getNumberOfResults(), searchApplicantConst.getNumberOfResults());
        Assert.assertEquals(searchApplicant.getApplicantName(), searchApplicantConst.getApplicantName());

        SearchApplicantResponse searchApplicantResponse = objectFactory.createSearchApplicantResponse();
        searchApplicantResponse.setReturnedObject(new ArrayList<Applicant>());
    }

    @Test
    public void testSaveApplicant() {
        ObjectFactory objectFactory = new ObjectFactory();
        SaveApplicant saveApplicant = objectFactory.createSaveApplicant();
        saveApplicant.setUser("user");
        saveApplicant.setApplicant(new Applicant());
        saveApplicant.setModule("TM");
        saveApplicant.setOffice("EM");
        SaveApplicant saveApplicantConst = new SaveApplicant("TM", "EM", "user", new Applicant());
        Assert.assertEquals(saveApplicant.getModule(), saveApplicantConst.getModule());
        Assert.assertEquals(saveApplicant.getOffice(), saveApplicantConst.getOffice());
        Assert.assertEquals(saveApplicant.getUser(), saveApplicantConst.getUser());

        SaveApplicantResponse saveApplicantResponse = objectFactory.createSaveApplicantResponse();
        saveApplicantResponse.setReturnedObject(new Result());
    }

    @Test
    public void testMatchApplicant() {
        ObjectFactory objectFactory = new ObjectFactory();
        MatchApplicant matchApplicant = objectFactory.createMatchApplicant();
        matchApplicant.setApplicant(new Applicant());
        matchApplicant.setModule("TM");
        matchApplicant.setOffice("EM");
        matchApplicant.setNumberOfResults(5);
        MatchApplicant saveApplicantConst = new MatchApplicant("TM", "EM", new Applicant(), 5);
        Assert.assertEquals(matchApplicant.getModule(), saveApplicantConst.getModule());
        Assert.assertEquals(matchApplicant.getOffice(), saveApplicantConst.getOffice());
        Assert.assertEquals(matchApplicant.getNumberOfResults(), saveApplicantConst.getNumberOfResults());

        MatchApplicantResponse matchApplicantResponse = objectFactory.createMatchApplicantResponse();
        matchApplicantResponse.setReturnedObject(new ArrayList<Applicant>());
    }

}
