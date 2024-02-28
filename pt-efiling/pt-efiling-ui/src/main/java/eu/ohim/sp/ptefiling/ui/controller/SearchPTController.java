package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.ptefiling.ui.service.interfaces.PTPatentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 19.04.2022
 * Time: 17:55
 */
@Controller
public class SearchPTController {

    @Autowired
    private PTPatentServiceInterface ptPatentServiceInterface;

    @RequestMapping(value = "autocompletePatent", headers="Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String autocomplete(
            @RequestParam(required = false, value = "args") String id,
            @RequestParam(required = true, value = "office") String office,
            @RequestParam(required = true, value = "flowModeId") String flowModeId) {

        return ptPatentServiceInterface.autocompletePatent(office, id, 10, flowModeId);
    }

}
