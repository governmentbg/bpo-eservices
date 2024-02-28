package eu.ohim.sp.core.register;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 18.06.2019
 */
@Stateless
public class BPOPatentSearchServiceBean implements BPOPatentSearchServiceLocal, BPOPatentSearchServiceRemote {

    private Logger logger = Logger.getLogger(BPOPatentSearchServiceBean.class);

    private static final String PATENT_SET = "patent_set";

    @EJB(lookup="java:global/businessRulesLocal")
    private RulesService rulesService;

    @Inject
    @BPOPatentSearchClientInside
    private BPOPatentSearchService adapter;

    @Override
    public Patent getPatentByAppNumber(String appNum) {
        return adapter.getPatentByAppNumber(appNum);
    }

    @Override
    public ErrorList validatePatent(String module, Patent patent, RulesInformation rulesInformation) {
        if (patent != null) {
            logger.debug(">>> Validate Patent: " + patent.getApplicationNumber());
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(patent);
        objects.add(rulesInformation);

        // Starts the check
        ErrorList errors = rulesService.validate(module, PATENT_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Patent END");
        }

        return errors;
    }
}
