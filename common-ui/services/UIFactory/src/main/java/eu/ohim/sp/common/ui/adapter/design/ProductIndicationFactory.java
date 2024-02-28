package eu.ohim.sp.common.ui.adapter.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexKindForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationKind;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;

@Component
public class ProductIndicationFactory implements UIFactory<ProductIndication, LocarnoAbstractForm> {

	@Autowired
	private ValidationCodeFactory validationCodeFactory;

	@Override
	public ProductIndication convertTo(LocarnoAbstractForm form) {
		ProductIndication core = new ProductIndication();
		List<ProductIndicationClass> classes = new ArrayList<ProductIndicationClass>();
		ProductIndicationKind kind = null;
		
		if (form != null) {
			if (form instanceof LocarnoForm) {
				
				LocarnoForm locarnoForm = (LocarnoForm) form;
				LocarnoClassification locarnoClassification = locarnoForm.getLocarnoClassification();
				
				ProductIndicationClass indicationProductClass = new ProductIndicationClass();
				indicationProductClass.setMainClass(locarnoClassification.getLocarnoClass().getClazz());
				indicationProductClass.setSubClass(locarnoClassification.getLocarnoClass().getSubclass());

				List<ProductIndicationTerm> pi_terms = new ArrayList<>();

				Optional<List<String>> t = Optional.ofNullable(locarnoClassification.getLocarnoClass().getIndications());
				t.ifPresent(l -> l.stream()
					.forEach(term -> {
						ProductIndicationTerm term1 = new ProductIndicationTerm();
						term1.setText(term);
						pi_terms.add(term1);
					}));

				indicationProductClass.setTerms(pi_terms);
				
				List<ProductIndicationTerm> terms = new ArrayList<ProductIndicationTerm>();
				ProductIndicationTerm indicationProductTerm = new ProductIndicationTerm();
				indicationProductTerm.setText(locarnoClassification.getIndication());
				terms.add(indicationProductTerm);
				
				indicationProductClass.setTerms(terms);
				classes.add(indicationProductClass);
			} else { 
				LocarnoComplexForm locarnoComplexForm = (LocarnoComplexForm) form;
				
				List<ProductIndicationTerm> terms = new ArrayList<ProductIndicationTerm>();
				ProductIndicationTerm indicationProductTerm = new ProductIndicationTerm();
				indicationProductTerm.setText(locarnoComplexForm.getIndication());
				terms.add(indicationProductTerm);
				
				ProductIndicationClass indicationProductClass;
				for (LocarnoClass locarnoClass : locarnoComplexForm.getClasses()) {
					indicationProductClass = new ProductIndicationClass();
					indicationProductClass.setMainClass(locarnoClass.getClazz());
					indicationProductClass.setSubClass(locarnoClass.getSubclass());
					indicationProductClass.setTerms(terms);
					classes.add(indicationProductClass);
				}
			}
		}

		if (form.getType()!=null) {
			switch (form.getType()) {
				case SINGLE_PRODUCT:
					kind = ProductIndicationKind.SINGLE_PRODUCT;
					break;
				case SET_COMPOSITION:
					kind = ProductIndicationKind.SET_COMPOSITION;
					break;
				default:
					break;
			}
		}
		
		for (ProductIndicationClass pic : classes) {
			List<String> toJoin = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(pic.getTerms())) {
				for (ProductIndicationTerm term : pic.getTerms()){
					toJoin.add(term.getText());
				}
			}
			pic.setDescription(StringUtils.join(toJoin, ", "));
		}
		
		core.setClasses(classes);
		core.setKind(kind);

		if(form.getValidationCode() != null){
			core.setValidationCode(validationCodeFactory.convertTo(form.getValidationCode()));
		}
		return core;
	}	

	@Override
	public LocarnoAbstractForm convertFrom(ProductIndication core) {
		LocarnoAbstractForm form;
		LocarnoComplexKindForm kind;
		int numClasses = CollectionUtils.size(core.getClasses()); 
		
		if (numClasses > 1 || (core.getKind() != null && core.getKind().equals(ProductIndicationKind.SET_COMPOSITION))) {
			LocarnoComplexForm locarnoComplexForm = new LocarnoComplexForm();
			
			List<LocarnoClass> locarnoClasses = new ArrayList<LocarnoClass>();
			LocarnoClass locarnoClass;
			String indication = "";

			for (ProductIndicationClass productIndicationClass : core.getClasses()) {
				locarnoClass = new LocarnoClass();
				locarnoClass.setClazz(productIndicationClass.getMainClass());
				locarnoClass.setSubclass(productIndicationClass.getSubClass());
				List<String> toJoin = new ArrayList<String>();
				for (ProductIndicationTerm productIndicationTerm : productIndicationClass.getTerms()) {
					toJoin.add(productIndicationTerm.getText());
				}
				locarnoClass.setIndications(toJoin);
				locarnoClasses.add(locarnoClass);
				indication = StringUtils.join(toJoin, ", ");
			}
			locarnoComplexForm.setClasses(locarnoClasses);
			locarnoComplexForm.setIndication(indication);



			form = locarnoComplexForm;
		} else if (numClasses == 1) {
			ProductIndicationClass indicationProductClass = core.getClasses().get(0);
			List<ProductIndicationTerm> indicationProductTerms = indicationProductClass.getTerms();

			LocarnoForm locarnoForm = new LocarnoForm();
			LocarnoClassification locarnoClassification = new LocarnoClassification();
			locarnoClassification.getLocarnoClass().setClazz(indicationProductClass.getMainClass());
			locarnoClassification.getLocarnoClass().setSubclass(indicationProductClass.getSubClass());
			locarnoForm.setLocarnoClassification(locarnoClassification);

			if (CollectionUtils.size(indicationProductTerms) == 1) {
				ProductIndicationTerm productIndicationTerm = indicationProductTerms.get(0);
				locarnoClassification.setIndication(productIndicationTerm.getText());
			}

			locarnoForm.setLocarnoClassification(locarnoClassification);
			form = locarnoForm;
		} else {
			form = new LocarnoForm();
		}

		if (core.getKind()!=null) {
			switch (core.getKind()) {
				case SINGLE_PRODUCT:
					kind = LocarnoComplexKindForm.SINGLE_PRODUCT;
					break;
				case SET_COMPOSITION:
					kind = LocarnoComplexKindForm.SET_COMPOSITION;
					break;
				default:
					kind = LocarnoComplexKindForm.SINGLE_PRODUCT;
					break;
			}

		} else {
			kind = LocarnoComplexKindForm.SINGLE_PRODUCT;
		}
		form.setType(kind);

		if(core.getValidationCode() != null){
			form.setValidationCode(validationCodeFactory.convertFrom(core.getValidationCode()));
		}

		return form;
	}

}
