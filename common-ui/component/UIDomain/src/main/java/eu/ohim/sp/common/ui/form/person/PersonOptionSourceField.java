package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Raya
 * 06.11.2020
 */
public enum PersonOptionSourceField {
    FIELD_APPLICANTS_FROM_OWNERS("applicantsFromOwners"),
    FIELD_APPLICANTS_FROM_ASSIGNEES("applicantsFromAssignees"),
    FIELD_APPLICANTS_FROM_HOLDERS("applicantsFromHolders"),
    FIELD_ASSIGNEES_FROM_INVENTORS("assigneesFromInventors"),
    FIELD_CURRENT_USER_INDICATOR("currentUserIndicator");

    private String value;

    PersonOptionSourceField(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static PersonOptionSourceField fromValue(String value){
        Optional<PersonOptionSourceField> optionalConfig =
            Arrays.stream(PersonOptionSourceField.values()).filter(e -> e.getValue().equals(value)).findFirst();
        return optionalConfig.isPresent() ? optionalConfig.get() : null;
    }

    public static List<PersonOptionSourceField> fieldsForSection(AvailableSection availableSection){
        switch (availableSection){
            case APPLICANT: return Arrays.asList(values());
            case REPRESENTATIVE:
            case ASSIGNEE: case ASSIGNEE_SECURITY: case LICENSEE: case REMCREDITOR:
            case HOLDER:
                return Arrays.asList(FIELD_CURRENT_USER_INDICATOR);
            case INVENTOR:
                return Arrays.asList(FIELD_ASSIGNEES_FROM_INVENTORS);
        }
        return null;
    }

}
