package bg.duosoft.taxcalculator.controller;

import bg.duosoft.taxcalculator.util.*;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.fee.FeeCalculationService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = "")
public class CalculateFeesController {

    private static Logger LOG = LoggerFactory.getLogger(CalculateFeesController.class);

    @Autowired
    private FeeCalculationService feeCalculationService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String mainPage(Model model, HttpServletRequest request) {

        model.addAttribute("applicationTypes", ApplicationTypes.getTmApplicationTypes());
        model.addAttribute("objectTypes", ObjectTypes.getObjectTypes());
        model.addAttribute("objectTypesOwedTaxes", ObjectTypesOwedTaxes.getObjectTypes());

        return "mainPage";
    }

    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    public String test(Model model, HttpServletRequest request, Locale locale,
                       @RequestParam String applicationType,
                       @RequestParam String params) throws Exception {

        List<Object> list;
        try {
            list = CalcPatramListBuilder.listBuilder(applicationType, params, locale);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            throw new Exception(messageSource.getMessage("error.convert_params", null, locale));
        }

        List<Fee> fees;

        if (list.size() == 1 && list.get(0) instanceof ManualFee) {
            fees = ((ManualFee) list.get(0)).getFees();
        } else {
            try {
                fees = feeCalculationService.calculateFees(applicationType, list);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(e.getMessage());
                throw new Exception(messageSource.getMessage("error.lost_connection", null, locale));
            }
        }
        float totalFee = 0;
        List<Fee> feesToRemove = new ArrayList<>();
        for (int i = 0; i < fees.size(); i++) {
            if (fees.get(i).getAmount() == 0) {
                feesToRemove.add(fees.get(i));
            } else {
                totalFee += fees.get(i).getAmount();
            }

            if (applicationType.equals("tmrenewal") && i != 0 && fees.get(i).getFeeType().getNameKey().startsWith("basic")) {
                Fee tempFee = fees.get(i);
                fees.set(i, fees.get(0));
                fees.set(0, tempFee);
            }

        }
        fees.removeAll(feesToRemove);


        if (applicationType.equals("ptgeneric") && !fees.isEmpty()) {
            if(fees.get(0).getFeeType().getNameKey().equals("firstTimeLimitExtension")){
                fees.get(0).getFeeType().setNameKey("ptFirstTimeLimitExtension");
            } else if(fees.get(0).getFeeType().getNameKey().equals("secondTimeLimitExtension")){
                fees.get(0).getFeeType().setNameKey("ptSecondTimeLimitExtension");
            }
        }

        model.addAttribute("fees", fees);
        model.addAttribute("totalFee", totalFee);

        return "feeSection";
    }

    @RequestMapping(value = "/appBox", method = RequestMethod.GET)
    public String test(@RequestParam String applicationType, @RequestParam String objectType) {
        return "moduleSections" + "/" + objectType + "/" + applicationType;
    }

    @RequestMapping(value = "/serviceBar", method = RequestMethod.GET)
    public String serviceBar(Model model, Locale locale, @RequestParam String objectType) throws Exception {

        Map<String, String> applicationTypes;
        switch (objectType) {
            case "trademark":
                applicationTypes = ApplicationTypes.getTmApplicationTypes();
                break;
            case "design":
                applicationTypes = ApplicationTypes.getDsApplicationTypes();
                break;
            case "patent":
                applicationTypes = ApplicationTypes.getPtApplicationTypes();
                break;
            case "utility_model":
                applicationTypes = ApplicationTypes.getUmApplicationTypes();
                break;
            case "european_patent":
                applicationTypes = ApplicationTypes.getEpApplicationTypes();
                break;
            case "protection_certificate":
                applicationTypes = ApplicationTypes.getPcApplicationTypes();
                break;
            case "plants_and_breeds":
                applicationTypes = ApplicationTypes.getSvApplicationTypes();
                break;
            default:
                String message = messageSource.getMessage("error.unrecognized_object", null, locale);
                LOG.error(message);
                throw new Exception(message);
        }

        model.addAttribute("applicationTypes", applicationTypes);
        return "serviceBar";
    }

}
