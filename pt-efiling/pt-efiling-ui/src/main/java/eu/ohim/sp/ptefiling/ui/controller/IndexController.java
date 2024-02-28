package eu.ohim.sp.ptefiling.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya
 * 30.01.2019
 */
@Controller
public class IndexController {

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
