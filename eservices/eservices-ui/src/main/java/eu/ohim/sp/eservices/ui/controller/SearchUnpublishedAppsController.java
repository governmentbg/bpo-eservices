package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.eservices.ui.service.interfaces.BackOfficeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchUnpublishedAppsController {

    @Autowired
    private BackOfficeServiceInterface backOfficeService;

    @RequestMapping(value = "unpublishedAppsAutocomplete", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String unpublishedAppsAutocomplete(@RequestParam String flowModeId, @RequestParam(required = false) String text){
        User user = AuthenticationUtil.getAuthenticatedUser();
        if (user!=null){
            String username=user.getUsername();
            return backOfficeService.unpublishedAppsAutocomplete(username, flowModeId, text);
        } else {
            return "";
        }
    }

}
