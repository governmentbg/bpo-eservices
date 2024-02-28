package eu.ohim.sp.common.ui.form.classification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import eu.ohim.sp.common.ui.form.resources.StoredFile;

public class AddedTermForm {

	private List<TermForm> terms = LazyList.decorate(new ArrayList<StoredFile>(),
			FactoryUtils.instantiateFactory(TermForm.class));

	public List<TermForm> getTerms() {
		return terms;
	}

	public void setTerms(List<TermForm> terms) {
		this.terms = terms;
	}
	
}
