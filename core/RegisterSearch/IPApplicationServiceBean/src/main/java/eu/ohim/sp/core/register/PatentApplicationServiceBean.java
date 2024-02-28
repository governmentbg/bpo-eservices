package eu.ohim.sp.core.register;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.patent.PCT;
import eu.ohim.sp.core.domain.patent.ParallelApplication;
import eu.ohim.sp.core.domain.patent.PatentTransformation;
import eu.ohim.sp.core.domain.patent.PatentView;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 12.07.2019
 */
@Stateless
public class PatentApplicationServiceBean implements PatentApplicationServiceLocal, PatentApplicationServiceRemote {

    @EJB(lookup="java:global/businessRulesLocal")
    private RulesService businessRulesService;

    private static final Logger LOGGER = Logger.getLogger(PatentApplicationServiceBean.class);

    private static final String CLAIM_SET = "claim_set";
    private static final String PATENT_VIEW_SET = "patent_view_set";
    private static final String CORRESPONDENCE_ADDRESS_SET = "correspondenceAddress_set";


    @Override
    public ErrorList validatePatentTransformation(String module, PatentTransformation transformation, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validate of "  +transformation.getClass() + " START");
            if (transformation != null) {
                LOGGER.debug("		- Validating PatentTransformation : "
                    + transformation.getApplicationNumber());
            }
        }

        return validateClaim(module, rulesInformation, transformation);
    }

    @Override
    public ErrorList validatePCT(String module, PCT pct, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validate of "  +pct.getClass() + " START");
            if (pct != null) {
                LOGGER.debug("		- Validating PCT : "
                    + pct.getApplicationNumber());
            }
        }

        return validateClaim(module, rulesInformation, pct);
    }

    @Override
    public ErrorList validateParallelApplication(String module, ParallelApplication parallelApplication, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validate of "  +parallelApplication.getClass() + " START");
            if (parallelApplication != null) {
                LOGGER.debug("		- Validating ParallelApplication : "
                    + parallelApplication.getApplicationNumber());
            }
        }

        return validateClaim(module, rulesInformation, parallelApplication);
    }

    @Override
    public ErrorList validateDivisionalApplication(String module, DivisionalApplicationDetails divisional, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validate of "  +divisional.getClass() + " START");
            if (divisional != null) {
                LOGGER.debug("		- Validating DivisionalApplicationDetails : "
                    + divisional.getInitialApplicationNumber());
            }
        }

        return validateClaim(module, rulesInformation, divisional);
    }

    private ErrorList validateClaim(String module, RulesInformation rulesInformation, Object validatableTarget){
        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        if (section != null) {
            // Prepares the objects to insert in the session
            objects.add(section);
            objects.add(validatableTarget);
            objects.add(rulesInformation);

            // Starts the check
            errors = businessRulesService.validate(module, CLAIM_SET, objects);
            LOGGER.debug(" <<< validate of "+validatableTarget.getClass()+" END");
        }
        return errors;
    }

    @Override
    public ErrorList validatePatentView(String module, PatentView patentView, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validatePatentViewForm START");
            if (patentView != null) {
                LOGGER.debug("		- Validating PatentViewForm  " );
            }
        }

        // Variable declaration
        List<Object> objects = new ArrayList<>();
        ErrorList errorsResult = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        if (section != null) {
            // Prepares the objects to insert in the session
            objects.add(section);
            objects.add(patentView);
            objects.add(rulesInformation);

            // Starts the check
            errorsResult = businessRulesService.validate(module, PATENT_VIEW_SET, objects);
            LOGGER.debug(" <<< validatePatentViewForm END");
        }

        return errorsResult;

    }

    @Override
    public ErrorList validateApplicationCA(String module, ContactDetails contactDetails, RulesInformation rulesInformation) {
        if (contactDetails != null) {
            LOGGER.debug(">>> Validate ContactDetails : " + contactDetails);
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(contactDetails);
        objects.add(rulesInformation);

        // Starts the check
        ErrorList errors = businessRulesService.validate(module, CORRESPONDENCE_ADDRESS_SET, objects);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("<<< Validate ContactDetails END");
        }
        return errors;
    }
}
