package eu.ohim.sp.dsefiling.ui.transformer;

import eu.ohim.sp.common.ui.form.classification.LocarnoTermJSON;
import eu.ohim.sp.common.ui.form.design.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidationServiceTransformer {

	public static IndicationProductForm convertToIndicationProductForm(LocarnoForm lForm) {
		IndicationProductForm indicationForm = new IndicationProductForm();
		indicationForm.setIdentifier("");
		indicationForm.setText(lForm.getLocarnoClassification().getIndication());
		indicationForm.setClassCode(lForm.getLocarnoClassification().getLocarnoClass().getClazz());
		indicationForm.setSubclassCode(lForm.getLocarnoClassification().getLocarnoClass().getSubclass());
		return indicationForm;
	}

	public static List<IndicationProductForm> convertToIndicationProductFormList(LocarnoComplexForm lform){
		List<IndicationProductForm> indicationProductFormList = new ArrayList<>();
		if(lform != null && lform.getClasses() != null && lform.getClasses().size()>0) {
			boolean indicationSet = false;
			for (LocarnoClass locarnoClass: lform.getClasses()){
				IndicationProductForm indicationProductForm = new IndicationProductForm();
				indicationProductForm.setIdentifier("");
				if(!indicationSet){
					indicationProductForm.setText(lform.getIndication());
					indicationSet = true;
				}
				indicationProductForm.setClassCode(locarnoClass.getClazz());
				indicationProductForm.setSubclassCode(locarnoClass.getSubclass());
				indicationProductFormList.add(indicationProductForm);
			}
		}
		return indicationProductFormList;
	}
	
	public static LocarnoForm convertToLocarnoForm(IndicationProductForm form, LocarnoForm previousLocarnoForm){
		previousLocarnoForm.getLocarnoClassification().setGroup(form.isGroup());
		previousLocarnoForm.getLocarnoClassification().setHeading(form.isHeading());
		previousLocarnoForm.getLocarnoClassification().setIndication(form.getText());
		previousLocarnoForm.getLocarnoClassification().setValidation(form.getValidation());
		previousLocarnoForm.getLocarnoClassification().getLocarnoClass().setClazz(form.getClassCode());
		previousLocarnoForm.getLocarnoClassification().getLocarnoClass().setSubclass(form.getSubclassCode());
		if(CollectionUtils.isNotEmpty(form.getSuggestions())){
			previousLocarnoForm.getLocarnoClassification().setSuggestions(convertToLocarnoClassificationList(form.getSuggestions()));
		}
		return previousLocarnoForm;
	}
	public static LocarnoComplexForm convertToLocarnoComplexForm(IndicationProductForm form, LocarnoComplexForm previousLocarnoForm){

		previousLocarnoForm.setValidationCode(form.getValidation());
		if(CollectionUtils.isNotEmpty(form.getSuggestions())){
			previousLocarnoForm.setSuggestions(convertToLocarnoClassificationList(form.getSuggestions()));
		}
		return previousLocarnoForm;
	}
	
	private static List<LocarnoClassification> convertToLocarnoClassificationList(List<IndicationProductForm> list){
		List<LocarnoClassification> classificationList = new ArrayList<>(list.size());

		for (IndicationProductForm indForm : list) {
			LocarnoClassification classification = new LocarnoClassification();
			classification.setGroup(indForm.isGroup());
			classification.setHeading(indForm.isHeading());
			classification.setIndication(indForm.getText());
			classification.setValidation(indForm.getValidation());
			classification.getLocarnoClass().setClazz(indForm.getClassCode());
			classification.getLocarnoClass().setSubclass(indForm.getSubclassCode());
			classificationList.add(classification);
		}
		return classificationList;
	}
	
	public static LocarnoTermJSON generateTermJSON(LocarnoAbstractForm form){
		LocarnoTermJSON termJSON = new LocarnoTermJSON();
		String[] split;
		if(form instanceof LocarnoComplexForm){
			String[] commaSplit = form.getLocarnoClassFormatted().split(",");
			split = commaSplit[0].split("-");
		} else {
			split = form.getLocarnoClassFormatted().split("-");
		}

		termJSON.setId(form.getId());
		termJSON.setIndication(form.getLocarnoIndication());
		termJSON.setLocarnoClassSubclass(form.getLocarnoClassFormatted());
		termJSON.setLocarnoClass(split[0]);
		termJSON.setLocarnoSubclass(split[1]);
		termJSON.setTermStatus(getTermStatus(form.getValidationCode().name()));
		termJSON.setValidationState(form.getValidationCode().name());
		if(CollectionUtils.isNotEmpty(form.getSuggestions())){
			termJSON.setRelatedTerms(generateRelatedTermJSON(form.getSuggestions()));
		}
		return termJSON;
	}
	
	private static List<LocarnoTermJSON> generateRelatedTermJSON(List<LocarnoClassification> list){
		List<LocarnoTermJSON> termList = new ArrayList<>();
		for (LocarnoClassification locarnoTermJSON : list) {
			LocarnoTermJSON term = new LocarnoTermJSON();
			term.setId("");
			term.setIndication(locarnoTermJSON.getIndication());
			term.setLocarnoClassSubclass(locarnoTermJSON.getLocarnoClassFormatted());
			term.setLocarnoClass(locarnoTermJSON.getLocarnoClass().getClazz());
			term.setLocarnoSubclass(locarnoTermJSON.getLocarnoClass().getSubclass());
			if(locarnoTermJSON.getValidation()!=null){
				term.setValidationState(locarnoTermJSON.getValidation().name());
				term.setTermStatus(getTermStatus(locarnoTermJSON.getValidation().name()));
			}
			termList.add(term);
		}
		return termList;
	}
	private static String getTermStatus(String code){
		String termStatus = "";
		switch (code) {
		case "invalid":
			termStatus = "Invalid";
			break;
		case "editable":
			termStatus = "Wrong Locarno Classification";
			break;
		case "notfound":
			termStatus = "Not Found";
			break;
		default:
			termStatus = "Incorrect Term";
			break;
		}
		return termStatus;
	}
}
