package eu.ohim.sp.common.ui.flow.section;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.patent.PatentForm;

/**
 * Created by Raya
 * 10.04.2019
 */
public interface PatentFlowBean extends FlowBean {

    PatentForm getPatent();

    void setPatent(PatentForm patent);
}
