package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Created by Raya
 * 10.04.2019
 */
public class PTConversionForm extends PTTransformationForm {

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.CONVERSION;
    }

    @Override
    public PTTransformationForm createNewForm() {
        return new PTConversionForm();
    }
}
