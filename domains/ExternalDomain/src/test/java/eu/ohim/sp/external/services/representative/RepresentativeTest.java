package eu.ohim.sp.external.services.representative;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Representative;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 28/1/2014
 * Time: 3:38 πμ
 * To change this template use File | Settings | File Templates.
 */
public class RepresentativeTest {

    @Test
    public void testGetRepresentativeAutocomplete() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetRepresentativeAutocomplete getRepresentativeAutocomplete = objectFactory.createGetRepresentativeAutocomplete();
        getRepresentativeAutocomplete.setModule("TM");
        getRepresentativeAutocomplete.setOffice("EM");
        getRepresentativeAutocomplete.setText("text");
        getRepresentativeAutocomplete.setNumberOfRows(5);
        getRepresentativeAutocomplete = new GetRepresentativeAutocomplete("EM", "text", 5, "TM");

        GetRepresentativeAutocompleteResponse getRepresentativeAutocompleteResponse = objectFactory.createGetRepresentativeAutocompleteResponse();
        getRepresentativeAutocompleteResponse.setReturnedObject("sth");
    }

    @Test
    public void testGetRepresentative() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetRepresentative getRepresentative = objectFactory.createGetRepresentative();
        getRepresentative.setModule("TM");
        getRepresentative.setOffice("EM");
        getRepresentative.setRepresentativeId("123132");
        GetRepresentative getRepresentativeConst = new GetRepresentative("TM", "EM", "123132");
        Assert.assertEquals(getRepresentative.getModule(), getRepresentativeConst.getModule());
        Assert.assertEquals(getRepresentative.getOffice(), getRepresentativeConst.getOffice());
        Assert.assertEquals(getRepresentative.getRepresentativeId(), getRepresentativeConst.getRepresentativeId());

        GetRepresentativeResponse getRepresentativeResponse = objectFactory.createGetRepresentativeResponse();
        getRepresentativeResponse.setReturnedObject(new Representative());

    }

    @Test
    public void testSearchRepresentative() {
        ObjectFactory objectFactory = new ObjectFactory();
        SearchRepresentative searchRepresentative = objectFactory.createSearchRepresentative();
        searchRepresentative.setModule("TM");
        searchRepresentative.setOffice("EM");
        searchRepresentative.setRepresentativeId("123132");
        searchRepresentative.setName("name");
        searchRepresentative.setNationality("UK");
        searchRepresentative.setNumberOfResults(5);
        SearchRepresentative searchRepresentativeConst = new SearchRepresentative("TM", "EM", "123132", "name", "UK", 5);
        Assert.assertEquals(searchRepresentative.getModule(), searchRepresentativeConst.getModule());
        Assert.assertEquals(searchRepresentative.getOffice(), searchRepresentativeConst.getOffice());
        Assert.assertEquals(searchRepresentative.getRepresentativeId(), searchRepresentativeConst.getRepresentativeId());
        Assert.assertEquals(searchRepresentative.getNationality(), searchRepresentativeConst.getNationality());
        Assert.assertEquals(searchRepresentative.getNumberOfResults(), searchRepresentativeConst.getNumberOfResults());

        SearchRepresentativeResponse searchRepresentativeResponse = objectFactory.createSearchRepresentativeResponse();
        searchRepresentativeResponse.setReturnedObject(new ArrayList<Representative>());
    }

    @Test
    public void testSaveRepresentative() {
        ObjectFactory objectFactory = new ObjectFactory();
        SaveRepresentative saveRepresentative = objectFactory.createSaveRepresentative();
        saveRepresentative.setUser("user");
        saveRepresentative.setRepresentative(new Representative());
        saveRepresentative.setModule("TM");
        saveRepresentative.setOffice("EM");
        SaveRepresentative saveRepresentativeConst = new SaveRepresentative("TM", "EM", "user", new Representative());
        Assert.assertEquals(saveRepresentative.getModule(), saveRepresentativeConst.getModule());
        Assert.assertEquals(saveRepresentative.getOffice(), saveRepresentativeConst.getOffice());
        Assert.assertEquals(saveRepresentative.getUser(), saveRepresentativeConst.getUser());

        SaveRepresentativeResponse saveRepresentativeResponse = objectFactory.createSaveRepresentativeResponse();
        saveRepresentativeResponse.setReturnedObject(new Result());
    }

    @Test
    public void testMatchRepresentative() {
        ObjectFactory objectFactory = new ObjectFactory();
        MatchRepresentative matchRepresentative = objectFactory.createMatchRepresentative();
        matchRepresentative.setRepresentative(new Representative());
        matchRepresentative.setModule("TM");
        matchRepresentative.setOffice("EM");
        matchRepresentative.setNumberOfResults(5);
        MatchRepresentative saveRepresentativeConst = new MatchRepresentative("TM", "EM", new Representative(), 5);
        Assert.assertEquals(matchRepresentative.getModule(), saveRepresentativeConst.getModule());
        Assert.assertEquals(matchRepresentative.getOffice(), saveRepresentativeConst.getOffice());
        Assert.assertEquals(matchRepresentative.getNumberOfResults(), saveRepresentativeConst.getNumberOfResults());

        MatchRepresentativeResponse matchRepresentativeResponse = objectFactory.createMatchRepresentativeResponse();
        matchRepresentativeResponse.setReturnedObject(new ArrayList<Representative>());
    }
}
