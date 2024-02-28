package bg.duosoft.documentmanager.ui;

import bg.duosoft.documentmanager.service.DocumentUtilityService;
import bg.duosoft.documentmanager.utils.ErrorMsgUtils;
import eu.ohim.sp.core.domain.resources.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Raya
 * 13.01.2021
 */
@Controller
public class XMLReplaceController {

    private static Logger logger = LoggerFactory.getLogger(ReceiptRegeneratorController.class);

    @Autowired
    private DocumentUtilityService documentUtilityService;

    @PostMapping("/replaceDocument")
    public String replaceDocument(@RequestParam("file") MultipartFile file, @RequestParam String appId,
                                   RedirectAttributes redirectAttributes) {

        Document found = documentUtilityService.getDocumentByAppId(appId);
        if(found != null && !found.getCustomProperties().get("applicationType").equalsIgnoreCase("TM") &&
            !found.getCustomProperties().get("applicationType").equalsIgnoreCase("DS")) {
            redirectAttributes.addFlashAttribute(ControllerConst.MESSAGES_ATTR,
                ErrorMsgUtils.createErrorMessages("Document is  not a TM/DS document. It could be signed and should not be replaced!"));
            return "redirect:/";
        } else if (found != null){
            if(!file.getContentType().equalsIgnoreCase("application/xml") && !file.getContentType().equalsIgnoreCase("text/xml")){
                redirectAttributes.addFlashAttribute(ControllerConst.MESSAGES_ATTR, ErrorMsgUtils.createErrorMessages("Uploaded file must be xml!"));
                return "redirect:/";
            } else {
                try {
                    found.setData(file.getBytes());
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute(ControllerConst.MESSAGES_ATTR, ErrorMsgUtils.createErrorMessages(e));
                    return "redirect:/";
                }
                documentUtilityService.updateDocument(found);
                return "redirect:/printDocumentDetails?appId="+appId;
            }
        } else {
            redirectAttributes.addFlashAttribute(ControllerConst.MESSAGES_ATTR,
                Arrays.asList("Document not found!"));
            return "redirect:/";
        }
    }

}
