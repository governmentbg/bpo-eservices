package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.domain.trademark.ApplicationExtent;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMark;

@Component
public class LimitedTrademarkFactory implements UIFactory<LimitedTradeMark, TMDetailsForm> {

	
	@Autowired
	private TrademarkFactory trademarkFactory;
	
	@Autowired
	private GoodsServicesFactory goodsServicesFactory;
	
	@Override
	public LimitedTradeMark convertTo(TMDetailsForm form) {
		TradeMark tradeMark = trademarkFactory.convertTo(form);
		LimitedTradeMark limitedTrademark = new LimitedTradeMark();
		try {
			PropertyUtils.copyProperties(limitedTrademark, tradeMark);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		//Mapping the limited goods and services to the core object of the TradeMark
		if(form.getGoodsAndServices() != null && !form.getGoodsAndServices().isEmpty()){
			List<ClassDescription> classes = new ArrayList<ClassDescription>();	
			for (GoodAndServiceForm gsform : form.getGoodsAndServices()) {
				     
	            ClassDescription clazz = goodsServicesFactory.convertTo(gsform);
	            classes.add(clazz);
	        }
			limitedTrademark.setLimitedClassDescriptions(classes);
		}
		
		//Mapping the Extent of the application to the coe object
		boolean extent = form.isExtent();
			
		if(extent){
			limitedTrademark.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
		}
		else{
			limitedTrademark.setApplicationExtent(ApplicationExtent.PARTIAL_GOODS_AND_SERVICES);
		}
		
		return limitedTrademark;
	}

	@Override
	public TMDetailsForm convertFrom(LimitedTradeMark core) {
		TMDetailsForm tmDetailsForm = trademarkFactory.convertFrom(core);
		
		//limitedClassDescriptions : List<ClassDescription>
		Set<GoodAndServiceForm> limitedGSs = new TreeSet<GoodAndServiceForm>();
		if(core.getLimitedClassDescriptions() != null){
			
			for (ClassDescription classes : core.getLimitedClassDescriptions()) {
				     
	            GoodAndServiceForm gsForm = goodsServicesFactory.convertFrom(classes);
	            
	            if (gsForm != null) {
	            	limitedGSs.add(gsForm);
		        }            
			}
			tmDetailsForm.setGoodsAndServices(limitedGSs);
		}

		//convert applicationExtent : ApplicationExtent
		if(core.getApplicationExtent() != null){
			switch (core.getApplicationExtent()) {
			case ALL_GOODS_AND_SERVICES:
				tmDetailsForm.setExtent(true);
				break;
			case PARTIAL_GOODS_AND_SERVICES:
				tmDetailsForm.setExtent(false);
				break;
			default:
				tmDetailsForm.setExtent(new Boolean(null));
				break;
			}
		}
		
		return tmDetailsForm;
	}
	
}
