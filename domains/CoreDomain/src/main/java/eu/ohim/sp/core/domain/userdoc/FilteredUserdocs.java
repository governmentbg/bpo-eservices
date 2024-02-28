package eu.ohim.sp.core.domain.userdoc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 29.04.2022
 * Time: 10:18
 */
public class FilteredUserdocs implements Serializable {

    private UserdocRelationRestriction userdocRelationRestriction;
    private List<Userdoc> userdoc;
    private String mainObject;


    public UserdocRelationRestriction getUserdocRelationRestriction() {
        return userdocRelationRestriction;
    }

    public void setUserdocRelationRestriction(UserdocRelationRestriction userdocRelationRestriction) {
        this.userdocRelationRestriction = userdocRelationRestriction;
    }

    public List<Userdoc> getUserdoc() {
        return userdoc;
    }

    public void setUserdoc(List<Userdoc> userdoc) {
        this.userdoc = userdoc;
    }

    public String getMainObject() {
        return mainObject;
    }

    public void setMainObject(String mainObject) {
        this.mainObject = mainObject;
    }
}
