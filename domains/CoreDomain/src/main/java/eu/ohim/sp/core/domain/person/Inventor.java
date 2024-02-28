package eu.ohim.sp.core.domain.person;

/**
 * Created by Raya
 * 11.04.2019
 */
public class Inventor extends PersonRole {

    private boolean waiver;

    private boolean belongsToAGroup;

    private String groupName;

    private boolean importedFromApplicant;

    public boolean isWaiver() {
        return waiver;
    }

    public void setWaiver(boolean waiver) {
        this.waiver = waiver;
    }

    public boolean isBelongsToAGroup() {
        return belongsToAGroup;
    }

    public void setBelongsToAGroup(boolean belongsToAGroup) {
        this.belongsToAGroup = belongsToAGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isImportedFromApplicant() {
        return importedFromApplicant;
    }

    public void setImportedFromApplicant(boolean importedFromApplicant) {
        this.importedFromApplicant = importedFromApplicant;
    }
}
