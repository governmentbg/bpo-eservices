package eu.ohim.sp.ui.tmefiling.util;

import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;

import java.util.Comparator;

/**
 * Created by Raya
 * 15.08.2019
 */
public class MarkViewUtil {

    public static final String VIEW_SECTION_ID_SUFIX = "_markViews";

    public static void addViewsSequences(TMFlowBean flowBean){
        if(flowBean.getMarkViews() != null){
            flowBean.getMarkViews().sort(
                Comparator.comparing(MarkViewForm::getOrderField)
            );

            int i = 1;
            for(MarkViewForm form: flowBean.getMarkViews()){
                form.setSequence(i++);
            }
        }
    }

    public static String createViewsSectionId(String markType){
        return markType+VIEW_SECTION_ID_SUFIX;
    }
}
