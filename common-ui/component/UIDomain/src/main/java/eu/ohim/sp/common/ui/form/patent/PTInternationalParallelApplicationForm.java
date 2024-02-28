package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Created by Raya
 * 13.05.2019
 */
public class PTInternationalParallelApplicationForm extends PTParallelApplicationForm {

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.INTERNATIONAL_PARALLEL_APPLICATION;
    }

    @Override
    public PTParallelApplicationForm createNewForm() {
        return new PTInternationalParallelApplicationForm();
    }
}
