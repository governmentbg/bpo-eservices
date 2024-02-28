package eu.ohim.sp.dsefiling.ui.webflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.webflow.DateConversionService;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

import javax.annotation.PostConstruct;

/**
 * Application conversion service.
 * @author serrajo
 */
public class DSConversionService extends DateConversionService {

	@Autowired
	private DSFlowBean flowBean;
	
	@Autowired
	private DSDesignsServiceInterface designsService;

	@PostConstruct
    public void installFormatters() {
		super.addDefaultConverters();
    	addConverter(getStringToDesignConverter());
    }
    
    /**
	 * Get the converter to convert design id to design. 
	 * @return design.
	 * java.lang.String to type java.util.List<eu.ohim.sp.common.ui.form.DesignForm>

	 */
	public Converter<String, DesignForm> getStringToDesignConverter() {
		return new Converter<String, DesignForm>() {
			@Override
			public DesignForm convert(String id) {
				return designsService.getDesign(flowBean, id);
			}
		};
	}
	
}
