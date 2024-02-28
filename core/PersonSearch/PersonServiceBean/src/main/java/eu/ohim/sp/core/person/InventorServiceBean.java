package eu.ohim.sp.core.person;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.person.InventorClient;
import eu.ohim.sp.external.person.PersonSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 05.06.2019
 */
@Stateless
public class InventorServiceBean implements InventorServiceRemote, InventorServiceLocal{

    private Logger logger = Logger.getLogger(InventorService.class);

    @EJB(lookup="java:global/businessRulesLocal")
    private RulesService businessRulesService;

    @Inject
    @PersonSearchClientInside
    private InventorClient adapter;

    /**
     * The constant DESIGNERLIST
     */
    private static final String INVENTORSET = "inventor_set";

    @Override
    public String getInventorAutocomplete(String module, String office, String text, int numberOfRows) {
        // this method should not call the external service if the input is
        // invalid
        return text == null || numberOfRows <= 0 ? null : adapter.getInventorAutocomplete(module, office, text, numberOfRows);
    }

    @Override
    public Inventor getInventor(String module, String office, String inventorId) {
        // this method should not call the external service if the input is
        // invalid
        return inventorId == null ? null : adapter.getInventor(module, office, inventorId);
    }

    @Override
    public ErrorList validateInventor(String module, Inventor inventor, RulesInformation rulesInformation) {
        if (inventor != null) {
            logger.debug(">>> Validate Inventor: " + inventor.getKind() + ", "
                + inventor.getIncorporationCountry());
        }

        //Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(inventor);
        objects.add(rulesInformation);

        // Starts the check
        ErrorList errors = businessRulesService.validate(module, INVENTORSET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Inventor END");
        }

        return errors;

    }

    @Override
    public List<Inventor> matchInventor(String module, String office, Inventor inventor, int numberOfResults) {
        return inventor == null || numberOfResults <= 0 ? null : adapter.matchInventor(module, office, inventor, numberOfResults);
    }
}
