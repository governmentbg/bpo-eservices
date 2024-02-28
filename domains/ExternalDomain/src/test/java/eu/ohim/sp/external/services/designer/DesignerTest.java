package eu.ohim.sp.external.services.designer;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Designer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 27/01/14
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public class DesignerTest {

    @Test
    public void testGetDesignerAutocomplete() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetDesignerAutocomplete getDesignerAutocomplete = objectFactory.createGetDesignerAutocomplete();
        getDesignerAutocomplete.setModule("TM");
        getDesignerAutocomplete.setOffice("EM");
        getDesignerAutocomplete.setText("text");
        getDesignerAutocomplete.setNumberOfRows(5);

        GetDesignerAutocompleteResponse getDesignerAutocompleteResponse = objectFactory.createGetDesignerAutocompleteResponse();
        getDesignerAutocompleteResponse.setReturnedObject("sth");
    }

    @Test
    public void testGetDesigner() {
        ObjectFactory objectFactory = new ObjectFactory();
        GetDesigner getDesigner = objectFactory.createGetDesigner();
        getDesigner.setModule("TM");
        getDesigner.setOffice("EM");
        getDesigner.setDesignerId("123132");
        GetDesigner getDesignerConst = new GetDesigner("TM", "EM", "123132");
        Assert.assertEquals(getDesigner.getModule(), getDesignerConst.getModule());
        Assert.assertEquals(getDesigner.getOffice(), getDesignerConst.getOffice());
        Assert.assertEquals(getDesigner.getDesignerId(), getDesignerConst.getDesignerId());

        GetDesignerResponse getDesignerResponse = objectFactory.createGetDesignerResponse();
        getDesignerResponse.setReturnedObject(new Designer());
    }

    @Test
    public void testSearchDesigner() {
        ObjectFactory objectFactory = new ObjectFactory();
        SearchDesigner searchDesigner = objectFactory.createSearchDesigner();
        searchDesigner.setModule("TM");
        searchDesigner.setOffice("EM");
        searchDesigner.setDesignerId("123132");
        searchDesigner.setDesignerCountry("UK");
        searchDesigner.setDesignerName("name");
        searchDesigner.setNumberOfResults(5);
        SearchDesigner searchDesignerConst = new SearchDesigner("TM", "EM", "123132", "name", "UK", 5);
        Assert.assertEquals(searchDesigner.getModule(), searchDesignerConst.getModule());
        Assert.assertEquals(searchDesigner.getOffice(), searchDesignerConst.getOffice());
        Assert.assertEquals(searchDesigner.getDesignerId(), searchDesignerConst.getDesignerId());
        Assert.assertEquals(searchDesigner.getDesignerCountry(), searchDesignerConst.getDesignerCountry());
        Assert.assertEquals(searchDesigner.getNumberOfResults(), searchDesignerConst.getNumberOfResults());
        Assert.assertEquals(searchDesigner.getDesignerName(), searchDesignerConst.getDesignerName());

        SearchDesignerResponse searchDesignerResponse = objectFactory.createSearchDesignerResponse();
        searchDesignerResponse.setReturnedObject(new ArrayList<Designer>());
    }

    @Test
    public void testSaveDesigner() {
        ObjectFactory objectFactory = new ObjectFactory();
        SaveDesigner saveDesigner = objectFactory.createSaveDesigner();
        saveDesigner.setUser("user");
        saveDesigner.setDesigner(new Designer());
        saveDesigner.setModule("TM");
        saveDesigner.setOffice("EM");
        SaveDesigner saveDesignerConst = new SaveDesigner("TM", "EM", "user", new Designer());
        Assert.assertEquals(saveDesigner.getModule(), saveDesignerConst.getModule());
        Assert.assertEquals(saveDesigner.getOffice(), saveDesignerConst.getOffice());
        Assert.assertEquals(saveDesigner.getUser(), saveDesignerConst.getUser());

        SaveDesignerResponse saveDesignerResponse = objectFactory.createSaveDesignerResponse();
        saveDesignerResponse.setReturnedObject(new Result());
    }

    @Test
    public void testMatchDesigner() {
        ObjectFactory objectFactory = new ObjectFactory();
        MatchDesigner matchDesigner = objectFactory.createMatchDesigner();
        matchDesigner.setDesigner(new Designer());
        matchDesigner.setModule("TM");
        matchDesigner.setOffice("EM");
        matchDesigner.setNumberOfResults(5);
        MatchDesigner saveDesignerConst = new MatchDesigner("TM", "EM", new Designer(), 5);
        Assert.assertEquals(matchDesigner.getModule(), saveDesignerConst.getModule());
        Assert.assertEquals(matchDesigner.getOffice(), saveDesignerConst.getOffice());
        Assert.assertEquals(matchDesigner.getNumberOfResults(), saveDesignerConst.getNumberOfResults());

        MatchDesignerResponse matchDesignerResponse = objectFactory.createMatchDesignerResponse();
        matchDesignerResponse.setReturnedObject(new ArrayList<Designer>());
    }

}
