package eu.ohim.sp.common.ui.adapter.patent;


import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PCTForm;
import eu.ohim.sp.common.ui.form.patent.TechnicalQuestionnaireForm;
import eu.ohim.sp.core.domain.patent.PCT;
import eu.ohim.sp.core.domain.patent.TechnicalQuestionnaire;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 13.05.2019
 */
@Component
public class TechnicalQuestionnaireFactory implements UIFactory<TechnicalQuestionnaire, TechnicalQuestionnaireForm> {

    @Override
    public TechnicalQuestionnaire convertTo(TechnicalQuestionnaireForm form) {
        TechnicalQuestionnaire core = new TechnicalQuestionnaire();
        if(form == null){
            return core;
        }
        core.setCharacteristicsAbstract(form.getCharacteristicsAbstract());
        core.setResistanceAbstract(form.getResistanceAbstract());
        core.setOriginSupportReproductionAbstract(form.getOriginSupportReproductionAbstract());
        core.setTestConditionsAbstract(form.getTestConditionsAbstract());
        return core;
    }

    @Override
    public TechnicalQuestionnaireForm convertFrom(TechnicalQuestionnaire core) {
        TechnicalQuestionnaireForm form = new TechnicalQuestionnaireForm();
        if(core == null){
            return form;
        }
        form.setTestConditionsAbstract(core.getTestConditionsAbstract());
        form.setOriginSupportReproductionAbstract(core.getOriginSupportReproductionAbstract());
        form.setCharacteristicsAbstract(core.getCharacteristicsAbstract());
        form.setResistanceAbstract(core.getResistanceAbstract());
        return form;
    }
}
