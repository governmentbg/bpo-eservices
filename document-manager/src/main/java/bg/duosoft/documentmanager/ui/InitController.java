package bg.duosoft.documentmanager.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Raya
 * 18.01.2021
 */
@Controller
public class InitController {

    private static Logger logger = LoggerFactory.getLogger(InitController.class);

    @GetMapping("/")
    public String start(){
        return "start";
    }
}
