package eu.ohim.sp.common.ui.form.person;

/**
 * Created by velosma on 10/02/2017.
 */
public interface PreviousRepresentativeForm {

    ChangePersonType getChangeType();
    void setChangeType(ChangePersonType changeType);

    String getPreviousPersonId();
    void setPreviousPersonId(String previousPersonId);

    String getPreviousPersonName();
    void setPreviousPersonName(String previousPersonName);

    String getPreviousPersonAddress();
    void setPreviousPersonAddress(String previousPersonAddress);

    String getUpdatedId();
}
