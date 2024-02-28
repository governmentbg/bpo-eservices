package bg.duosoft.documentmanager.ui;

import bg.duosoft.documentmanager.service.DocumentUtilityService;
import eu.ohim.sp.core.domain.resources.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Raya
 * 18.01.2021
 */
@Controller
public class DocumentDetailsController {

    @Autowired
    private DocumentUtilityService documentUtilityService;

    @GetMapping("/printDocumentDetails")
    public String printDocumentDetails(Model model, @RequestParam(required = false) String appId, @RequestParam(required = false) String docId){
        if((appId == null || appId.isEmpty()) && (docId == null || docId.isEmpty())){
            model.addAttribute("messages", Arrays.asList("You need to specify filing number or document ID "));
            return "start";
        }
        Document found = null;
        if(appId != null && !appId.isEmpty()) {
            found = documentUtilityService.getDocumentByAppId(appId);
        } else if(docId != null && !docId.isEmpty()){
            found = documentUtilityService.getDocumentById(docId);
        }
        if(found == null){
            model.addAttribute("messages", Arrays.asList("Document not found. Please check the entered details"));
            return "start";
        }
        model.addAttribute("customProps", found.getCustomProperties());
        model.addAttribute("docId", found.getDocumentId());
        if(found.getFileFormat().equalsIgnoreCase("text/xml") || found.getFileFormat().equalsIgnoreCase("application/xml")) {
            try {
                model.addAttribute("data", new String(found.getData(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return "printDetails.html";
    }

    @GetMapping("/downloadDocument/{docId}")
    @ResponseBody
    public ResponseEntity<Resource> downloadDocument(@PathVariable String docId){
        Document found = documentUtilityService.getDocumentById(docId);
        ResponseEntity<Resource> responseEntity = null;
        if(found != null && found.getData() != null) {
            Resource resource = new ByteArrayResource(found.getData());
            responseEntity = ResponseEntity.ok().contentType(MediaType.parseMediaType(found.getFileFormat())).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resolveFileName(found.getFileName(), found.getFileFormat())
                   +"\"").body(resource);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    private String resolveFileName(String fileName, String mimeType){
        if(fileName.indexOf('.') != -1){
            return fileName;
        } else {
            return fileName+ documentUtilityService.resolveFileExtension(mimeType);
        }
    }
}
