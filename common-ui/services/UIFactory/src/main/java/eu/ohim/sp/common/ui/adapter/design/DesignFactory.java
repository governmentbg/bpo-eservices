package eu.ohim.sp.common.ui.adapter.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.design.ProductIndication;

@Component
public class DesignFactory implements UIFactory<Design, DesignForm> {

	@Autowired
	private ProductIndicationFactory productIndicationFactory;
	
	@Autowired
	private DesignViewFactory designViewFactory;
	
	@Override
	public Design convertTo(DesignForm form) {
		Design core = new Design();

		if (form != null) {
			core.setPublicationDefermentIndicator(BooleanUtils.toBoolean(form.getRequestDeferredPublication()));
			core.setPublicationDefermentTillDate(form.getDefermentTillDate());
			core.setVerbalElements(form.getVerbalElements());
			core.setVerbalElementsEn(form.getVerbalElementsEn());
			core.setDescription(form.getDescription());
			core.setDistinctiveFeatures(form.getDistinctiveFeatures());
			core.setColours(form.getColours());

			List<DesignView> designViews = new ArrayList<DesignView>();
			for (DesignViewForm designViewForm : form.getViews()) {
				designViews.add(designViewFactory.convertTo(designViewForm));
			}
			if((form instanceof ESDesignDetailsForm) && 
					designViews!=null && 
					designViews.size()>0 &&
					designViews.get(0).getView()!=null &&
					designViews.get(0).getView().getDocument()!=null &&
					form.getImported() && ((ESDesignDetailsForm) form).getUnpublished() != null && ((ESDesignDetailsForm) form).getUnpublished()){
				designViews.get(0).getView().getDocument().setUri(((ESDesignDetailsForm)form).getImageRepresentationURI());
			}
			
			core.setViews(designViews);
			
			if(form.getLocarno() != null && !form.getLocarno().isEmpty()){
				List<ProductIndication> indicationProducts = new ArrayList<ProductIndication>();
				for (LocarnoAbstractForm locarnoAbstractForm : form.getLocarno()) {
					indicationProducts.add(productIndicationFactory.convertTo(locarnoAbstractForm));
				}
				core.setProductIndications(indicationProducts);
			}
            
			core.setDivisionalApplication(form.isDivisonalApplicationIndicator());
			
			core.setDesignerWaiverIndicator(form.isDesignerWaiverIndicator());

			if(form.getNumber() != null) {
				core.setDesignIdentifier(form.getNumber().toString());
			}
		}

		return core;
	}

	@Override
	public DesignForm convertFrom(Design core) {
		DesignForm form = null;

		if (core != null) {
			form = new DesignForm();

			form.setRequestDeferredPublication(core.isPublicationDefermentIndicator());
			form.setDefermentTillDate(core.getPublicationDefermentTillDate());
			form.setVerbalElements(core.getVerbalElements());
			form.setVerbalElementsEn(core.getVerbalElementsEn());
			form.setDescription(core.getDescription());
			form.setDistinctiveFeatures(core.getDistinctiveFeatures());
			form.setColours(core.getColours());
			
			List<DesignViewForm> viewForms = new ArrayList<DesignViewForm>();
			if(core.getViews() != null) {
				for (DesignView designView : core.getViews()) {
					viewForms.add(designViewFactory.convertFrom(designView));
				}
			}
			form.setViews(viewForms);
			
			List<LocarnoAbstractForm> locarnoForms = new ArrayList<LocarnoAbstractForm>();

            Optional<? extends List<ProductIndication>> pis = Optional.ofNullable(core.getProductIndications());
            pis.ifPresent(p -> p.stream()
                    .forEach(pi ->
                            locarnoForms.add(productIndicationFactory.convertFrom(pi))));

			form.setLocarno(locarnoForms);
			
			form.setDivisonalApplicationIndicator(core.isDivisionalApplication());
			
			form.setDesignerWaiverIndicator(core.isDesignerWaiverIndicator());
		}
		
		return form;
	}

}
