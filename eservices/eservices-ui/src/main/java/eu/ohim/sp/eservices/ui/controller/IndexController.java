package eu.ohim.sp.eservices.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.engine.model.FlowModel;

import eu.ohim.sp.common.ui.webflow.SpFlowRegistry;;

@Controller
public class IndexController {

	
	@Autowired
	private SpFlowRegistry flowRegistry;
	
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public ModelAndView index() {
		
		List<String> flows = new ArrayList<String>();
		for(String registered: flowRegistry.getFlowDefinitionIds()){
			FlowModel flowModel = flowRegistry.getFlowModelRegistry().getFlowModel(registered);
			if(flowModel.getAbstract() == null){ //discard abstract flows
				flows.add(registered);
			}
		}
		
		return new ModelAndView("index", "flows", flows);
	}
}
