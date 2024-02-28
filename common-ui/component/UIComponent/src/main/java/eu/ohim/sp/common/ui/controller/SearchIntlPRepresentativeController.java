package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.10.2022
 * Time: 18:48
 */
@Controller
public class SearchIntlPRepresentativeController {

    private static final Logger logger = Logger.getLogger(SearchIntlPRepresentativeController.class);

    @Autowired
    private PersonServiceInterface personService;

    @PreAuthorize("hasRole('Representative_Search')")
    @RequestMapping(value = "listIntlPRepresentatives", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listIntlPRepresentatives() {
        logger.info("START search");
        String results = personService.getIntlPRepresentativeList();
        logger.info("END search");
        return results;
    }
}
