package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.patent.PTConversionForm;
import eu.ohim.sp.common.ui.form.patent.PTInternationalTransformationForm;
import eu.ohim.sp.common.ui.form.patent.PTNationalTransformationForm;
import eu.ohim.sp.common.ui.form.patent.PTTransformationForm;
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
 * 14.05.2019
 */
@Controller
public class AddTransformationController extends AddAbstractController {

    @Autowired
    private PTFlowBean ptFlowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Value("${euipo.office}")
    private String euipoOffice;

    @Value("${wipo.office}")
    private String wipoOffice;


    private static final String VIEW_ADD_TRANSFORMATION = "claim/transformation_details";
    private static final String VIEW_TABLE_TRANSFORMATION = "claim/transformation_card_list";
    private static final String VIEW_IMPORT_TRANSFORMATION = "claim/transformation_import";
    private static final String MODEL_TRANSFORMATION = "transformationForm";
    private static final String TRANSFORMATION_COUNTRY = "country";


    @RequestMapping(value = "transformationChoosetype", method = RequestMethod.GET)
    public ModelAndView transformationChoosetype(@RequestParam(value = TRANSFORMATION_COUNTRY) String country) {
        ModelAndView modelAndView = new ModelAndView(VIEW_IMPORT_TRANSFORMATION);
        modelAndView.addObject(TRANSFORMATION_COUNTRY, country);
        return modelAndView;
    }

    @RequestMapping(value = "addNationalTransformation", method = RequestMethod.GET)
    public ModelAndView addNationalTransformation(@RequestParam(required = false, value = "id") String id) {
        return getTransformationForm(id, PTNationalTransformationForm.class, configurationServiceDelegator.getOffice());
    }

    @PreAuthorize("hasRole('Transformation_Add')")
    @RequestMapping(value = "addNationalTransformation", method = RequestMethod.POST)
    public ModelAndView saveTransformationNational(@ModelAttribute(MODEL_TRANSFORMATION) PTNationalTransformationForm transformationForm, BindingResult result) {
        return submitTransformation(transformationForm, result);
    }

    @RequestMapping(value = "addWOTransformation", method = RequestMethod.GET)
    public ModelAndView addWOTransformation(@RequestParam(required = false, value = "id") String id) {
        return getTransformationForm(id, PTInternationalTransformationForm.class, wipoOffice);
    }

    @PreAuthorize("hasRole('Transformation_Add')")
    @RequestMapping(value = "addWOTransformation", method = RequestMethod.POST)
    public ModelAndView saveTransformationWO(@ModelAttribute(MODEL_TRANSFORMATION) PTInternationalTransformationForm transformationForm, BindingResult result) {
        return submitTransformation(transformationForm, result);
    }

    @RequestMapping(value = "addConversion", method = RequestMethod.GET)
    public ModelAndView addConversion(@RequestParam(required = false, value = "id") String id) {
        return getTransformationForm(id, PTConversionForm.class, euipoOffice);
    }

    @PreAuthorize("hasRole('Transformation_Add')")
    @RequestMapping(value = "addConversion", method = RequestMethod.POST)
    public ModelAndView saveConversion(@ModelAttribute(MODEL_TRANSFORMATION) PTConversionForm transformationForm, BindingResult result) {
        return submitTransformation(transformationForm, result);
    }

    private ModelAndView getTransformationForm(String id, Class<? extends PTTransformationForm> transformClass, String office){
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
            ConfigurationServiceDelegatorInterface.CLAIM_TRANSFORMATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        AddParameters addParameters = new AddParameters(transformClass, MODEL_TRANSFORMATION, null, VIEW_ADD_TRANSFORMATION, maxNumber);
        ModelAndView modelAndView = innerFormBackingObject(id, ptFlowBean, addParameters);
        modelAndView.addObject(TRANSFORMATION_COUNTRY, office);
        return modelAndView;
    }

    private ModelAndView submitTransformation(PTTransformationForm form, BindingResult result){
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
            ConfigurationServiceDelegatorInterface.CLAIM_TRANSFORMATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        AddParameters addParameters = new AddParameters(form.getClass(), MODEL_TRANSFORMATION, VIEW_TABLE_TRANSFORMATION, VIEW_ADD_TRANSFORMATION, maxNumber);
        ModelAndView modelAndView = onSubmit(form, ptFlowBean, addParameters, result);
        if(result.hasErrors()){
            modelAndView.addObject(TRANSFORMATION_COUNTRY, form.getCountryCode());
        }
        return modelAndView;
    }

}
