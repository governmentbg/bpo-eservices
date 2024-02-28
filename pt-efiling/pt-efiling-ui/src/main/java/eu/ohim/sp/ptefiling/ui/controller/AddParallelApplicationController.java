package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya
 * 15.05.2019
 */
@Controller
public class AddParallelApplicationController extends AddAbstractController {

    @Autowired
    private PTFlowBean ptFlowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Value("${euipo.office}")
    private String euipoOffice;

    @Value("${wipo.office}")
    private String wipoOffice;


    private static final String VIEW_ADD_PARALLEL_APP = "claim/parallel_application_details";
    private static final String VIEW_TABLE_PARALLEL_APP = "claim/parallel_application_card_list";
    private static final String VIEW_IMPORT_PARALLEL_APP = "claim/parallel_application_import";
    private static final String MODEL_PARALLEL_APP = "parallelApplicationForm";
    private static final String PARALLEL_APP_COUNTRY = "country";


    @RequestMapping(value = "parallelApplicationChoosetype", method = RequestMethod.GET)
    public ModelAndView parallelApplicationChoosetype(@RequestParam(value = PARALLEL_APP_COUNTRY) String country) {
        ModelAndView modelAndView = new ModelAndView(VIEW_IMPORT_PARALLEL_APP);
        modelAndView.addObject(PARALLEL_APP_COUNTRY, country);
        return modelAndView;
    }

    @RequestMapping(value = "addNationalParallelApplication", method = RequestMethod.GET)
    public ModelAndView addNationalParallelApplication(@RequestParam(required = false, value = "id") String id) {
        return getParallelApplicationForm(id, PTNationalParallelApplicationForm.class, configurationServiceDelegator.getOffice());
    }

    @PreAuthorize("hasRole('ParallelApplication_Add')")
    @RequestMapping(value = "addNationalParallelApplication", method = RequestMethod.POST)
    public ModelAndView saveParallelApplicationNational(@ModelAttribute(MODEL_PARALLEL_APP) PTNationalParallelApplicationForm parallelApplicationForm, BindingResult result) {
        return submitParallelApplication(parallelApplicationForm, result);
    }

    @RequestMapping(value = "addWOParallelApplication", method = RequestMethod.GET)
    public ModelAndView addWOParallelApplication(@RequestParam(required = false, value = "id") String id) {
        return getParallelApplicationForm(id, PTInternationalParallelApplicationForm.class, wipoOffice);
    }

    @PreAuthorize("hasRole('ParallelApplication_Add')")
    @RequestMapping(value = "addWOParallelApplication", method = RequestMethod.POST)
    public ModelAndView saveParallelApplicationWO(@ModelAttribute(MODEL_PARALLEL_APP) PTInternationalParallelApplicationForm parallelApplicationForm, BindingResult result) {
        return submitParallelApplication(parallelApplicationForm, result);
    }

    @RequestMapping(value = "addEMParallelApplication", method = RequestMethod.GET)
    public ModelAndView addEMParallelApplication(@RequestParam(required = false, value = "id") String id) {
        return getParallelApplicationForm(id, PTEuropeanParallelApplicationForm.class, euipoOffice);
    }

    @PreAuthorize("hasRole('ParallelApplication_Add')")
    @RequestMapping(value = "addEMParallelApplication", method = RequestMethod.POST)
    public ModelAndView saveEMParallelApplication(@ModelAttribute(MODEL_PARALLEL_APP) PTEuropeanParallelApplicationForm parallelApplicationForm, BindingResult result) {
        return submitParallelApplication(parallelApplicationForm, result);
    }

    private ModelAndView getParallelApplicationForm(String id, Class<? extends PTParallelApplicationForm> parallelAppClass, String office){
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
            ConfigurationServiceDelegatorInterface.CLAIM_PARALLEL_APPLICATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        AddParameters addParameters = new AddParameters(parallelAppClass, MODEL_PARALLEL_APP, null, VIEW_ADD_PARALLEL_APP, maxNumber);
        ModelAndView modelAndView = innerFormBackingObject(id, ptFlowBean, addParameters);
        modelAndView.addObject(PARALLEL_APP_COUNTRY, office);
        return modelAndView;
    }

    private ModelAndView submitParallelApplication(PTParallelApplicationForm form, BindingResult result){
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
            ConfigurationServiceDelegatorInterface.CLAIM_PARALLEL_APPLICATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        AddParameters addParameters = new AddParameters(form.getClass(), MODEL_PARALLEL_APP, VIEW_TABLE_PARALLEL_APP, VIEW_ADD_PARALLEL_APP, maxNumber);
        ModelAndView modelAndView = onSubmit(form, ptFlowBean, addParameters, result);
        if(result.hasErrors()){
            modelAndView.addObject(PARALLEL_APP_COUNTRY, form.getCountryCode());
        }
        return modelAndView;
    }
}
