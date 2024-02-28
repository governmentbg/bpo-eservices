package eu.ohim.sp.dsefiling.ui.controller.bind;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * Custom binding initializer for binding designs lists. 
 * @author serrajo
 */
public class DSBindingInitializer implements WebBindingInitializer {

	@Autowired
	private DSFlowBean flowBean;

	@Autowired
	private DSDesignsServiceInterface designsService;

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(List.class, "designsNotLinked", new CustomCollectionEditor(List.class, true) {
			@Override
			protected Object convertElement(Object id) {
				return designsService.getDesign(flowBean, (String) id);
			}
		});
		
		binder.registerCustomEditor(List.class, "designsLinked", new CustomCollectionEditor(List.class, true) {
			@Override
			protected Object convertElement(Object id) {
				return designsService.getDesign(flowBean, (String) id);
			}
		});
	}

}
