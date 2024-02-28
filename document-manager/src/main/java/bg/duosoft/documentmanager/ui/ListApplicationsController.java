package bg.duosoft.documentmanager.ui;

import bg.duosoft.documentmanager.service.ESListUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 22.03.2021
 * Time: 13:47
 */
@Controller
public class ListApplicationsController {

    @Autowired
    private ESListUtilityService esListUtilityService;

    @GetMapping("/getUnpublishedApps")
    public String getUnpublishedApps(Model model, @RequestParam String since){
        model.addAttribute("unpublished", esListUtilityService.findUnpublishedApplications(since));
        return "unpublishedApps.html";
    }
}
