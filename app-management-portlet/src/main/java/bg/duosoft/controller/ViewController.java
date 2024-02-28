package bg.duosoft.controller;

import bg.duosoft.config.UrlConfig;
import bg.duosoft.model.ApplicationCount;
import bg.duosoft.service.CountService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "VIEW")
public class ViewController {

    @Autowired
    private CountService countService;

    @Autowired
    private UrlConfig urlConfig;

    @RenderMapping
    public String defaultRender(RenderRequest request, Model model) {
        model.addAttribute("urlMap", urlConfig.urlMap());
        return "view";
    }

    @ResourceMapping(value = "updateApplicationResult")
    @ResponseBody
    public void updateApplicationResult(ResourceResponse response) throws IOException {

        JSONObject json = JSONFactoryUtil.createJSONObject();
        Map<String, ApplicationCount> applicationsCount = countService.getApplicationsCount();
        for (Map.Entry<String, ApplicationCount> e : applicationsCount.entrySet()) {
            JSONObject row = JSONFactoryUtil.createJSONObject();
            json.put(e.getKey(), row);
            row.put("submittedCount", e.getValue().getSubmittedCount());
            row.put("errorsCount", e.getValue().getErrorsCount());
        }
        response.getWriter().write(json.toString());
    }
}
