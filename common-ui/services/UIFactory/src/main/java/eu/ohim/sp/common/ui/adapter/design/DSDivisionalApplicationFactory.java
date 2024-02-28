package eu.ohim.sp.common.ui.adapter.design;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.ProductIndication;

@Component
public class DSDivisionalApplicationFactory implements UIFactory<DesignDivisionalApplicationDetails, DSDivisionalApplicationForm> {

	@Autowired
	private DesignFactory designFactory;
	
	@Autowired
	private ProductIndicationFactory indicationProductFactory;
	
	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;

	@Override
	public DesignDivisionalApplicationDetails convertTo(DSDivisionalApplicationForm form) {
		DesignDivisionalApplicationDetails toReturn = new DesignDivisionalApplicationDetails();
		
		if (StringUtils.isNotEmpty(form.getInfoDivisionalApplication())) {
			Text text = new Text();
			text.setValue(form.getInfoDivisionalApplication());
			toReturn.setComment(text);
		}
		
		toReturn.setInitialApplicationDate(form.getDateDivisionalApplication());
		toReturn.setInitialApplicationNumber(form.getNumberDivisionalApplication());
		toReturn.setAttachment(listAttachedDocumentFactory.convertTo(form.getFileDivisionalApplication()));
		
		if (CollectionUtils.isNotEmpty(form.getDesignsLinked())) {
			List<Design> designs = new ArrayList<Design>();
			for(DesignForm designForm : form.getDesignsLinked()){
				designs.add(designFactory.convertTo(designForm));
			}
			toReturn.setDesigns(designs);
		}
		
		if (CollectionUtils.isNotEmpty(form.getLocarno())) {
			List<ProductIndication> indicationProducts = new ArrayList<ProductIndication>();
			for(LocarnoAbstractForm ip:form.getLocarno()){
				indicationProducts.add(indicationProductFactory.convertTo(ip));
			}
			toReturn.setProductIndications(indicationProducts);
		}
		
		return toReturn;
	}

	@Override
	public DSDivisionalApplicationForm convertFrom(DesignDivisionalApplicationDetails core) {
		DSDivisionalApplicationForm form = new DSDivisionalApplicationForm();
		
		if (core != null) {
			if (core.getComment() != null) {
				form.setInfoDivisionalApplication(core.getComment().getValue());
			}
			
			form.setDateDivisionalApplication(core.getInitialApplicationDate());
			form.setNumberDivisionalApplication(core.getInitialApplicationNumber());
			form.setFileDivisionalApplication(listAttachedDocumentFactory.convertFrom(core.getAttachment()));
			
			if (CollectionUtils.isNotEmpty(core.getProductIndications())) {
				for (ProductIndication productIndication : core.getProductIndications()) {
					form.getLocarno().add(indicationProductFactory.convertFrom(productIndication));
				}
			}
		}
		
		return form;
	}

}
