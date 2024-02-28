package bg.duosoft.documentmanager.ui;

import bg.duosoft.documentmanager.service.DocumentUtilityService;
import bg.duosoft.documentmanager.service.ReceiptUtilityService;
import bg.duosoft.documentmanager.utils.ErrorMsgUtils;
import eu.ohim.sp.core.domain.resources.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raya
 * 19.01.2021
 */
@Controller
public class ReceiptRegeneratorController {

    private static Logger logger = LoggerFactory.getLogger(ReceiptRegeneratorController.class);

    @Autowired
    @Qualifier("tmReceiptService")
    private ReceiptUtilityService tmReceiptUtility;

    @Autowired
    @Qualifier("dsReceiptService")
    private ReceiptUtilityService dsReceiptUtility;

    @Autowired
    @Qualifier("ptReceiptService")
    private ReceiptUtilityService ptReceiptUtility;

    @Autowired
    @Qualifier("esReceiptService")
    private ReceiptUtilityService esReceiptUtility;

    @Autowired
    private DocumentUtilityService documentUtilityService;

    private Map<String, ReceiptUtilityService> receiptUtilitiesMap;

    @PostConstruct
    public void init(){
        receiptUtilitiesMap = new HashMap<>();
        receiptUtilitiesMap.put(tmReceiptUtility.getModule(), tmReceiptUtility);
        receiptUtilitiesMap.put(dsReceiptUtility.getModule(), dsReceiptUtility);
        receiptUtilitiesMap.put(ptReceiptUtility.getModule(), ptReceiptUtility);
        receiptUtilitiesMap.put(esReceiptUtility.getModule(), esReceiptUtility);
    }

    @PostMapping("/regenerateReceipt")
    @ResponseBody
    public ResponseEntity<Resource> regenerateReceipt(@RequestParam String appId, @RequestParam String module, @RequestParam String receiptType){
        try {
            byte[] receiptData = receiptUtilitiesMap.get(module).generateReceipt(appId, receiptType);
            Resource resource = new ByteArrayResource(receiptData);
            ResponseEntity<Resource> responseEntity = ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+appId+receiptType+".pdf\"").body(resource);
            return responseEntity;
        } catch (Exception e){
            logger.error("regenerateReceipt error", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/regenerateAndReplaceReceipt")
    public String regenerateAndReplaceReceipt(RedirectAttributes redirectAttributes, @RequestParam String appId, @RequestParam String module, @RequestParam String receiptType){

        if(receiptType.equalsIgnoreCase("accepted")){
            redirectAttributes.addFlashAttribute(ControllerConst.MESSAGES_ATTR,
                ErrorMsgUtils.createErrorMessages("Accepted applications are not kept in repo. Can not replace"));
            return "redirect:/";
        } else {
            byte[] receiptData = receiptUtilitiesMap.get(module).generateReceipt(appId, receiptType);
            Document updated = documentUtilityService.updateReceiptContent(appId, receiptData);
            return "redirect:/printDocumentDetails?docId="+updated.getDocumentIdentifier();
        }
    }
}
