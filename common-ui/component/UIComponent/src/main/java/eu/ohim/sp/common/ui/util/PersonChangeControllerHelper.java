package eu.ohim.sp.common.ui.util;

import eu.ohim.sp.common.ui.form.person.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by velosma on 09/02/2017.
 */
public class PersonChangeControllerHelper {

    public static String PROFESSIONAL_PRACTITIONER_VIEW_NAME = "personChange/personChange_professionalpractitioner";
    public static String LEGAL_PRACTITIONER_VIEW_NAME = "personChange/personChange_legalpractitioner";
    public static String EMPLOYEE_REPRESENTATIVE_VIEW_NAME = "personChange/personChange_employeerepresentative";
    public static String NATURAL_PERSON_VIEW_NAME = "personChange/personChange_naturalperson";
    public static String LEGAL_ENTITY_VIEW_NAME = "personChange/personChange_legalentity";
    public static String ASSOCIATION_VIEW_NAME = "personChange/personChange_association";
    public static String ADDRESS_VIEW_NAME = "personChange/personChange_address";

    public static String PROFESSIONAL_PRACTITIONER_FORM_NAME = "representativeProfessionalPractitionerForm";
    public static String LEGAL_PRACTITIONER_FORM_NAME = "representativeLegalPractitionerForm";
    public static String EMPLOYEE_REPRESENTATIVE_FORM_NAME = "representativeEmployeeRepresentativeForm";
    public static String NATURAL_PERSON_FORM_NAME = "representativeNaturalPersonForm";
    public static String LEGAL_ENTITY_FORM_NAME = "representativeLegalEntityForm";
    public static String ASSOCIATION_FORM_NAME = "representativeAssociationForm";
    public static String ADDRESS_FORM_NAME = "personChangeForm";

    private static Map<PersonChangeViewKind, ViewInfo> viewInfo;
    static {
        viewInfo = new HashMap<>();
        viewInfo.put(PersonChangeViewKind.PROFESSIONAL_PRACTITIONER, new ViewInfo(RepresentativeKindForm.PROFESSIONAL_PRACTITIONER, PROFESSIONAL_PRACTITIONER_VIEW_NAME, PROFESSIONAL_PRACTITIONER_FORM_NAME, ChangeProfessionalPractitionerForm.class));
        viewInfo.put(PersonChangeViewKind.LEGAL_PRACTITIONER, new ViewInfo(RepresentativeKindForm.LEGAL_PRACTITIONER, LEGAL_PRACTITIONER_VIEW_NAME, LEGAL_PRACTITIONER_FORM_NAME, ChangeLegalPractitionerForm.class));
        viewInfo.put(PersonChangeViewKind.EMPLOYEE_REPRESENTATIVE, new ViewInfo(RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE, EMPLOYEE_REPRESENTATIVE_VIEW_NAME, EMPLOYEE_REPRESENTATIVE_FORM_NAME, ChangeEmployeeRepresentativeForm.class));
        viewInfo.put(PersonChangeViewKind.NATURAL_PERSON, new ViewInfo(RepresentativeKindForm.NATURAL_PERSON, NATURAL_PERSON_VIEW_NAME, NATURAL_PERSON_FORM_NAME, ChangeRepresentativeNaturalPersonForm.class));
        viewInfo.put(PersonChangeViewKind.LEGAL_ENTITY, new ViewInfo(RepresentativeKindForm.LEGAL_ENTITY, LEGAL_ENTITY_VIEW_NAME, LEGAL_ENTITY_FORM_NAME, ChangeRepresentativeLegalEntityForm.class));
        viewInfo.put(PersonChangeViewKind.ASSOCIATION, new ViewInfo(RepresentativeKindForm.ASSOCIATION, ASSOCIATION_VIEW_NAME, ASSOCIATION_FORM_NAME, ChangeRepresentativeAssociationForm.class));
        viewInfo.put(PersonChangeViewKind.ADDRESS, new ViewInfo(null, ADDRESS_VIEW_NAME, ADDRESS_FORM_NAME, ChangeRepresentativeAddressForm.class));
    }

    public static ViewInfo getView(PersonChangeViewKind type) {
        return viewInfo.get(type);
    }

    public static ViewInfo getViewByRepresentativeType(RepresentativeKindForm type) {
        if (type != null) {
            for (Map.Entry<PersonChangeViewKind, ViewInfo> entry : viewInfo.entrySet()) {
                if (type.equals(entry.getValue().getRepresentativeType())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public static ViewInfo getViewByFormClass(Class clazz) {
        if (clazz != null) {
            for (Map.Entry<PersonChangeViewKind, ViewInfo> entry : viewInfo.entrySet()) {
                if (clazz.equals(entry.getValue().getFormClass())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public static RepresentativeForm getFormFromResponse(ModelAndView response) {
        if (response != null) {
            for (Map.Entry<PersonChangeViewKind, ViewInfo> entry : viewInfo.entrySet()) {
                if (response.getModel().get(entry.getValue().getFormName()) != null) {
                    return (RepresentativeForm) response.getModel().get(entry.getValue().getFormName());
                }
            }
        }
        return null;
    }


    public enum PersonChangeViewKind {
        PROFESSIONAL_PRACTITIONER,
        LEGAL_PRACTITIONER,
        EMPLOYEE_REPRESENTATIVE,
        NATURAL_PERSON,
        LEGAL_ENTITY,
        ASSOCIATION,
        ADDRESS
    }

    public static class ViewInfo {
        private RepresentativeKindForm representativeType;
        private String viewName;
        private String formName;
        private Class formClass;

        private ViewInfo(RepresentativeKindForm representativeType, String viewName, String formName, Class formClass) {
            this.representativeType = representativeType;
            this.viewName = viewName;
            this.formName = formName;
            this.formClass = formClass;
        }

        public RepresentativeKindForm getRepresentativeType() {
            return representativeType;
        }

        public String getViewName() {
            return viewName;
        }

        public String getFormName() {
            return formName;
        }

        public Class getFormClass() {
            return formClass;
        }

    }

}
