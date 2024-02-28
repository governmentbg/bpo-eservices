package eu.ohim.sp.eservices.ui.mock;

import java.util.List;

import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.person.DesignerService;
import eu.ohim.sp.core.person.DesignerServiceLocal;
import eu.ohim.sp.core.person.DesignerServiceRemote;

public class MockedDesignerService implements DesignerServiceRemote, DesignerServiceLocal {

	public Designer getDesigner(String arg0, String arg1, String arg2) {
		return null;
	}

	public String getDesignerAutocomplete(String arg0, String arg1,
			String arg2, int arg3) {
		return null;
	}

	public List<Designer> matchDesigner11(String arg0, String arg1,
			Designer arg2, int arg3) {
		return null;
	}

	public List<Designer> searchDesigner(String arg0, String arg1, String arg2,
			String arg3, String arg4, int arg5) {
		return null;
	}

	public String getDesignerAutocomplete(String text, int numberOfRows) {
		return null;
	}

	public List<Designer> searchDesigner(String designerId,
			String designerName, String designerNationality, int numberOfResults) {
		return null;
	}

	public Designer getDesigner(String office, String designerId) {
		return null;
	}

	public List<Designer> matchDesigner(Designer designer, int numberOfResults) {
		return null;
	}

	@Override
	public List<Designer> matchDesigner(
			String module, String office,
			Designer designer,
			int numberOfResults) {
		return null;
	}

	@Override
	public ErrorList validateDesigner(String module,
			Designer designer,
			RulesInformation rulesInformation) {
		return null;
	}
	
}
