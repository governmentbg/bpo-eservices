package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.ui.util.TagUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;


import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;

public class SelectableTag extends FlowContextAwareTag {
    /**
     *
     */
    private static final long serialVersionUID = -2290152088501092762L;

    @Override
    protected int doStartTagInternal() throws Exception {
        SectionViewConfiguration s = (SectionViewConfiguration) getRequestContext().getWebApplicationContext().getBean("sectionViewConfiguration");
        pageContext.getOut().print(s.getSelectable(AvailableSection.fromValue(getSectionId()), TagUtil.parseFieldName(getField()), getFlowModeId()));
        return (SKIP_BODY);
    }

}