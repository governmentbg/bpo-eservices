package eu.ohim.sp.common.ui.form.userdoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 29.04.2022
 * Time: 10:43
 */
public class FilteredUserdocsForm {

    private List<UserdocForm> userdocFormList;
    private String userdocRelationRestriction;
    private String mainObject;

    public FilteredUserdocsForm() {
        userdocFormList = new ArrayList<>();
    }


    public List<UserdocForm> getUserdocFormList() {
        return userdocFormList;
    }

    public void setUserdocFormList(List<UserdocForm> userdocFormList) {
        this.userdocFormList = userdocFormList;
    }

    public String getUserdocRelationRestriction() {
        return userdocRelationRestriction;
    }

    public void setUserdocRelationRestriction(String userdocRelationRestriction) {
        this.userdocRelationRestriction = userdocRelationRestriction;
    }

    public String getMainObject() {
        return mainObject;
    }

    public void setMainObject(String mainObject) {
        this.mainObject = mainObject;
    }
}
