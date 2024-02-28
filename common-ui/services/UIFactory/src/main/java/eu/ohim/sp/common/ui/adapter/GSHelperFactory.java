package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.core.domain.trademark.ApplicationExtent;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.GSHelperDetails;

@Component
public class GSHelperFactory implements UIFactory<GSHelperDetails, GSHelperForm>{

	@Autowired
    private GoodsServicesFactory goodsServicesFactory;
	
	@Override
	public GSHelperDetails convertTo(GSHelperForm form) {
		if(form == null) {
			return null;
		}
		GSHelperDetails core = new GSHelperDetails();
		if(form.getExtent()){
			core.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
		} else {
			core.setApplicationExtent(ApplicationExtent.PARTIAL_GOODS_AND_SERVICES);
		}
		
		core.setTmApplicationNumber(form.getTmApplicationNumber());
		core.setClassDescriptions(new ArrayList<ClassDescription>());
		if (form.getGoodsAndServices()!=null && !form.getGoodsAndServices().isEmpty()){			
			for (GoodAndServiceForm item : form.getGoodsAndServices()) {
				core.getClassDescriptions().add(goodsServicesFactory.convertTo(item));
			}
		}
		core.setInclusive(form.getInclusive());
		core.setGoodsServicesComment(form.getGoodsServicesComment());
		
		return core;
	}

	@Override
	public GSHelperForm convertFrom(GSHelperDetails core) {
		if(core == null) {
			return null;
		}
		GSHelperForm form = new GSHelperForm();
		switch(core.getApplicationExtent()){
		case ALL_GOODS_AND_SERVICES: form.setExtent(true); break;
		case PARTIAL_GOODS_AND_SERVICES: form.setExtent(false); break;
		}
		form.setTmApplicationNumber(core.getTmApplicationNumber());
		Set<GoodAndServiceForm> gsInForm = new TreeSet<GoodAndServiceForm>();
		if(core.getClassDescriptions() != null){
			for(ClassDescription clDecription: core.getClassDescriptions()){
				GoodAndServiceForm gs = goodsServicesFactory.convertFrom(clDecription);
				if(gs!=null){
					gsInForm.add(gs);
				}
			}
			form.setGoodsAndServices(gsInForm);
		}
		form.setInclusive(core.isInclusive());
		form.setGoodsServicesComment(core.getGoodsServicesComment());
		return form;
	}

}
