package bg.duosoft.taxcalculator.controller;

import bg.duosoft.taxcalculator.model.IpFile;
import bg.duosoft.taxcalculator.rest.PaymentLiabilityDetail;
import bg.duosoft.taxcalculator.service.IpFileService;
import bg.duosoft.taxcalculator.service.IpActionService;
import bg.duosoft.taxcalculator.service.PaymentsService;
import bg.duosoft.taxcalculator.util.IpasIdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping(value = "/checkOwedTaxes")
public class CheckOwedTaxesController {

    private static Logger LOG = LoggerFactory.getLogger(CheckOwedTaxesController.class);

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private IpFileService ipFileService;

    @Autowired
    private IpActionService ipActionService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(Model model, HttpServletRequest request, Locale locale,
                        @RequestParam String objectType,
                        @RequestParam String applicationNumberKind,
                        @RequestParam(required = false) String applicationNumber) throws Exception {

        if (Objects.isNull(applicationNumber)) {
            throw new Exception(messageSource.getMessage("error.fill_number", null, locale));
        }

        Long applicationNumberLong;
        try{
            applicationNumberLong = Long.parseLong(applicationNumber.trim());
        } catch (Exception e){
            throw new Exception(messageSource.getMessage("error.fill_number", null, locale));
        }

        Long fileNbr = null;
        Long regNbr = null;
        if (applicationNumberKind.equals("fileNbr"))
            fileNbr = applicationNumberLong;
        else if (applicationNumberKind.equals("regNbr"))
            regNbr = applicationNumberLong;
        else{
            String message = messageSource.getMessage("error.missing_parameters", null, locale);
            LOG.error(message);
            throw new Exception(message);
        }

        IpFile file = ipFileService.getFile(fileNbr, regNbr, objectType);

        if(Objects.isNull(file) || !ipActionService.isValid(file)){
            throw new Exception(messageSource.getMessage("error.no_file_found", null, locale));
        }

        try {
            List<PaymentLiabilityDetail> liabilityDetails = paymentsService.getUnpaidLiabilityDetails(IpasIdentifierGenerator.generateStringIdentifier(file));
            float totalFee = 0;
            for (PaymentLiabilityDetail pld : liabilityDetails){
                totalFee += Float.parseFloat(pld.getAmount().toString());
            }
            model.addAttribute("liabilityDetails", liabilityDetails);
            model.addAttribute("totalFee", totalFee);
            model.addAttribute("ipFile", file);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            throw new Exception(messageSource.getMessage("error.lost_connection", null, locale));
        }

        return "owedTaxes";
    }

}
