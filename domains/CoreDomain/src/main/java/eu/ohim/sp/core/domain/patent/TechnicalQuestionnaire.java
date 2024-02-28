package eu.ohim.sp.core.domain.patent;

import java.io.Serializable;

public class TechnicalQuestionnaire implements Serializable {

    private String originSupportReproductionAbstract;
    private String testConditionsAbstract;
    private String characteristicsAbstract;
    private String resistanceAbstract;


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
