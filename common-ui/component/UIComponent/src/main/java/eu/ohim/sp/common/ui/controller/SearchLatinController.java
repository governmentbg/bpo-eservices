package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.classification.BPOClassificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchLatinController extends SearchAbstractController {

    private static final Logger logger = Logger.getLogger(SearchLatinController.class);

    @Autowired
    private BPOClassificationService bpoClassificationService;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


    @PreAuthorize("hasRole('Latin_Search')")
    @RequestMapping(value = {"autocompleteLatin"}, headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String autocompleteServiceApp(@RequestParam(required = true, value = "args") String args) {
        return autocompleteService(args);
    }


    public String autocompleteService(String args) {
        logger.info("START search");
        int maxNumberOfResults = Integer.parseInt(configurationServiceDelegator
                .getValueFromGeneralComponent(
                        ConfigurationServiceDelegatorInterface
                                .LATIN_AUTOCOMPLETE_MAXRESULTS));

        String results = "";
        results = bpoClassificationService.autocompletePlantLatinClassification(args, maxNumberOfResults);
        logger.info("END search");
        return results;
    }
}
