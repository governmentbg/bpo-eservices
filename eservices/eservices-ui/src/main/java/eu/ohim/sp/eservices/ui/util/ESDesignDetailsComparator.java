package eu.ohim.sp.eservices.ui.util;

import java.util.Comparator;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;

public class ESDesignDetailsComparator implements Comparator<ESDesignDetailsForm>{

	@Override
	public int compare(ESDesignDetailsForm o1, ESDesignDetailsForm o2) {
		return o1.getDesignIdentifier().compareTo(o2.getDesignIdentifier());
	}

}
