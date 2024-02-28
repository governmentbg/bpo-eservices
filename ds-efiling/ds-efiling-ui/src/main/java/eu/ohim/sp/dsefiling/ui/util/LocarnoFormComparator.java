package eu.ohim.sp.dsefiling.ui.util;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;

import java.util.Comparator;

public class LocarnoFormComparator implements Comparator<LocarnoAbstractForm>{

	@Override
	public int compare(LocarnoAbstractForm o1, LocarnoAbstractForm o2) {
	    String so1 = o1.getLocarnoClassFormatted() + " " + o1.getLocarnoIndication();
        String so2 = o2.getLocarnoClassFormatted() + " " + o2.getLocarnoIndication();
		return so1.compareTo(so2);
	}

}
