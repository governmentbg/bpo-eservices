package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.common.ui.form.trademark.MarkViewFormList;
import eu.ohim.sp.common.ui.form.validation.DragDropError;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.util.MarkViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Raya
 * 15.08.2019
 */
@Controller
public class AddMarkViewController extends AddAbstractController {

    @Autowired
    private TMFlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


    private Integer maxNumber = -1;

    // Models
    private static final String MODEL_MARK_VIEW_FORM = "markViewForm";

    // Views
    private static final String VIEW_ADD_MARKVIEW = "marks/views/view_add";
    private static final String VIEW_LIST_MARKVIEWS = "marks/views/view_list";
    private static final String VIEW_VIEW_MARKVIEW = "marks/views/view_view";


    public Integer getMaxNumber() {
        if(maxNumber == -1) {
            maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.MARK_VIEW_ADD_MAXNUMBER,
                ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        }
        return maxNumber;
    }

    @RequestMapping(value = "addMarkView", method = RequestMethod.GET)
    public ModelAndView addMarkView() {
        AddParameters addParameters = new AddParameters(MarkViewForm.class, MODEL_MARK_VIEW_FORM, null, VIEW_ADD_MARKVIEW, getMaxNumber());
        ModelAndView mav = innerFormBackingObject(null, flowBean, addParameters);
        mav.addObject("sectionId", MarkViewUtil.createViewsSectionId(flowBean.getMainForm().getMarkType()));
        return mav;
    }


    @PreAuthorize("hasRole('MarkView_Add')")
    @RequestMapping(value = "saveMarkView", method = RequestMethod.POST)
    public ModelAndView saveMarkView(@RequestBody MarkViewFormList markViewForms, BindingResult result) {
        AddParameters addParameters = new AddParameters(MarkViewForm.class, MODEL_MARK_VIEW_FORM, VIEW_LIST_MARKVIEWS, VIEW_ADD_MARKVIEW, getMaxNumber());
        ModelAndView modelAndView = null;

        if(markViewForms == null || markViewForms.getMarkViewForms() == null ||
            markViewForms.getMarkViewForms().size() == 0){
            throw new SPException("No views to save. Request body is empty");
        }
        Optional<Boolean> isEditOptional = markViewForms.getMarkViewForms().stream().map(e -> e.getId()!= null && !e.getId().isEmpty()).reduce((e1, e2) -> e1&&e2);
        boolean isEdit = false;
        if(isEditOptional.isPresent()){
            isEdit = isEditOptional.get();
        }

        List<DragDropError> dragDropErrors = new ArrayList<>();
        int lastIndex = 0;
        for(MarkViewForm markViewForm: markViewForms.getMarkViewForms()) {
            markViewForm.setMarkType(flowBean.getMainForm().getMarkType());

            markViewForm.setValidateImported(true);
            modelAndView = onSubmit(markViewForm, flowBean, addParameters, result);

            if(result.hasErrors() && result.getFieldErrors().size() > lastIndex){

                for(int i = lastIndex; i < result.getFieldErrors().size(); i++) {
                    FieldError fieldError = result.getFieldErrors().get(i);
                    dragDropErrors.add(new DragDropError(new StringBuilder(fieldError.getField()).append("_").append(markViewForm.getView().getStoredFiles().get(0).getDocumentId()).toString(),
                        fieldError.getDefaultMessage(), fieldError.getCode()));

                }

                lastIndex = result.getFieldErrors().size();
            }
        }

        if(dragDropErrors.size()>0){
            addParameters = new AddParameters(MarkViewForm.class, MODEL_MARK_VIEW_FORM, null, VIEW_ADD_MARKVIEW, getMaxNumber());
            addParameters.setMaximumEntities(null);
            modelAndView = innerFormBackingObject(null, flowBean, addParameters);
            modelAndView.addObject("errorList", dragDropErrors);
        }

        if (VIEW_LIST_MARKVIEWS.equals(modelAndView.getViewName())) {
            MarkViewUtil.addViewsSequences(flowBean);
        } else if(!isEdit){
            markViewForms.getMarkViewForms().stream().filter(e -> e.getId() != null && !e.getId().isEmpty()).forEach(e->
                flowBean.removeObject(MarkViewForm.class, e.getId())
            );
        }

        modelAndView.addObject("sectionId", MarkViewUtil.createViewsSectionId(flowBean.getMainForm().getMarkType()));

        return modelAndView;
    }

    @RequestMapping(value = "getMarkView", method = RequestMethod.GET)
    public ModelAndView getMarkView() {
        ModelAndView mav = innerFormBackingObject(null, flowBean, new AddParameters(MarkViewForm.class, MODEL_MARK_VIEW_FORM, null, VIEW_ADD_MARKVIEW, null));
        mav.addObject("sectionId", MarkViewUtil.createViewsSectionId(flowBean.getMainForm().getMarkType()));
        return mav;
    }


    @RequestMapping(value = "viewMarkView", method = RequestMethod.GET)
    public ModelAndView viewMarkView(@RequestParam(required = true, value = "id") String id) {
        MarkViewForm markViewForm = flowBean.getObject(MarkViewForm.class, id);
        ModelAndView modelAndView = new ModelAndView(VIEW_VIEW_MARKVIEW);
        modelAndView.addObject(MODEL_MARK_VIEW_FORM, markViewForm);
        modelAndView.addObject("sectionId", MarkViewUtil.createViewsSectionId(flowBean.getMainForm().getMarkType()));
        return modelAndView;
    }

}
