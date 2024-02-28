package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class ValidateImportTag extends FlowContextAwareTag {

	private static final long serialVersionUID = 189582323393059052L;

	@Override
	protected int doStartTagInternal() throws Exception {		
		SectionViewConfiguration s = (SectionViewConfiguration) getRequestContext().getWebApplicationContext().getBean("sectionViewConfiguration");
		pageContext.getOut().print(s.getValidateImportSection(AvailableSection.fromValue(getSectionId()), getFlowModeId()));
		return (SKIP_BODY);
	}
	
}
