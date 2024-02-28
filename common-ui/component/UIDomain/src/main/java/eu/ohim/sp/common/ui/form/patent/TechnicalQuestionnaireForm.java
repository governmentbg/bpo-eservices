package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

public class TechnicalQuestionnaireForm extends AbstractImportableForm {

    private String originSupportReproductionAbstract;
    private String testConditionsAbstract;
    private String characteristicsAbstract;
    private String resistanceAbstract;

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        TechnicalQuestionnaireForm form = new TechnicalQuestionnaireForm();
        form.setId(this.getId());
        form.setImported(this.getImported());
        form.setCharacteristicsAbstract(this.getCharacteristicsAbstract());
        form.setResistanceAbstract(this.getResistanceAbstract());
        form.setOriginSupportReproductionAbstract(this.getOriginSupportReproductionAbstract());
        form.setTestConditionsAbstract(this.getTestConditionsAbstract());
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.TECHNICAL_QUESTIONNAIRE;
    }


    public String getOriginSupportReproductionAbstract() {
        return originSupportReproductionAbstract;
    }

    public void setOriginSupportReproductionAbstract(String originSupportReproductionAbstract) {
        this.originSupportReproductionAbstract = originSupportReproductionAbstract;
    }

    public String getTestConditionsAbstract() {
        return testConditionsAbstract;
    }

    public void setTestConditionsAbstract(String testConditionsAbstract) {
        this.testConditionsAbstract = testConditionsAbstract;
    }

    public String getCharacteristicsAbstract() {
        return characteristicsAbstract;
    }

    public void setCharacteristicsAbstract(String characteristicsAbstract) {
        this.characteristicsAbstract = characteristicsAbstract;
    }

    public String getResistanceAbstract() {
        return resistanceAbstract;
    }

    public void setResistanceAbstract(String resistanceAbstract) {
        this.resistanceAbstract = resistanceAbstract;
    }
}
