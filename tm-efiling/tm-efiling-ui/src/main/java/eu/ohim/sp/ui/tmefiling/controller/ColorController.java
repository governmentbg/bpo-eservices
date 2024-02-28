/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import eu.ohim.sp.common.ui.flow.section.LanguagesFlowBean;
import eu.ohim.sp.common.ui.form.resources.ColourForm;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.colour.xsd.Colour;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;

@Controller
public class ColorController {
	private static final Logger logger = Logger.getLogger(MarkController.class);

	public static final int resultNumberByDefault = 3;

	public static final String MINIMUM_CHARACTERS_AUTOCOMPLETE = "minimum.characters.autocomplete";

	public static final String COLOUR_CONFIGURATION = "colour-configuration";

	public static final String GENERAL_COMPONENT = "general";

	public static final String MODULE = "tmefiling";

	public static final String SECTIONID = "sectionId";

	@Autowired
	private TMFlowBean flowBean;

	@Autowired
	private LanguagesFlowBean languagesFlowBean;

	@Autowired
	private ConfigurationService systemConfigurationService;

	@RequestMapping(value = "addColour", method = RequestMethod.POST)
	public ModelAndView addColour(@ModelAttribute("colourForm") ColourForm colourForm, BindingResult result,
			@RequestParam(SECTIONID) String sectionId) {
		ModelAndView model = new ModelAndView("marks/mark_colormark_internal");
	
		if (!result.hasErrors()) {
			addColourToFlowBean(colourForm.getFormat(), colourForm.getValue());
		}
		
		model.addObject(SECTIONID, sectionId);
		return model;
	}

	@RequestMapping(value = "saveColour", method = RequestMethod.POST)
	public ModelAndView saveColour(@RequestParam(required = false, value = "formatOld") String formatOld,
			@RequestParam(required = false, value = "valueOld") String valueOld, @RequestParam("format") String format,
			@RequestParam(required = false, value = "value") String value, @RequestParam(SECTIONID) String sectionId,
			@ModelAttribute("colourForm") ColourForm colourForm, BindingResult result) {

		ModelAndView model = new ModelAndView("marks/mark_colormark_internal");
		
		boolean emptyColor = (value == null || value.isEmpty());
		
		boolean colorValid = false;

		if (emptyColor) {
			model.addObject("emptyColour", true);
		} else {
			for (String color : getColorList()){
				if(color.equalsIgnoreCase(value)){
					colorValid = true;
					break;
				}
			}
			if(!colorValid) {
				model.addObject("colorNotValid", true);
			}
		}

		if (!emptyColor && colorValid && !result.hasErrors()) {
			removeColourFromFlowBean(formatOld, valueOld);
			addColourToFlowBean(format, value);
		}

		model.addObject(SECTIONID, sectionId);
		return model;
	}

	@RequestMapping(value = "removeColour", method = RequestMethod.POST)
	public ModelAndView removeColour(@RequestParam("format") String format, @RequestParam("value") String value,
			@RequestParam(SECTIONID) String sectionId) {
		removeColourFromFlowBean(format, value);
		ModelAndView model = new ModelAndView("marks/mark_colormark_internal");
		model.addObject(SECTIONID, sectionId);
		return model;
	}

	@RequestMapping(value = "removeAllColours", method = RequestMethod.POST)
	public ModelAndView removeAllColours(@RequestParam(SECTIONID) String sectionId) {
		flowBean.getMainForm().setColourList(new ArrayList<ColourForm>());
		ModelAndView model = new ModelAndView("marks/mark_colormark_internal");
		model.addObject(SECTIONID, sectionId);
		return model;
	}

	private boolean existColour(List<ColourForm> colourList, String format, String value) {

		for (ColourForm varColour : colourList) {
				if (varColour.getFormat().equals(format) && varColour.getValue().equalsIgnoreCase(value)) {
					return true;
				}
			}
	
		return false;
	}

	private void addColourToFlowBean(String format, String value) {
		
		List<ColourForm> colourList = flowBean.getMainForm().getColourList();
		
		if (colourList == null) {
			colourList = new ArrayList<ColourForm>();
			flowBean.getMainForm().setColourList(colourList);
		}
		
		
		if (!existColour(colourList, format, value)) {
			ColourForm colourForm = new ColourForm();
			colourForm.setFormat(format);
			colourForm.setValue(value);
			colourList.add(colourForm);
		}
		
	}

	private void removeColourFromFlowBean(String format, String value) {
		
		List<ColourForm> colourList = flowBean.getMainForm().getColourList();
		if (colourList == null) {
			return;
		}

		for (int i = 0; i < colourList.size(); i++) {
			if (colourList.get(i).getFormat().equals(format) && colourList.get(i).getValue().equalsIgnoreCase(value)) {
				colourList.remove(i);
				return;
			}
		}

	}

	private int getMinCharsOFAutoComplete() {
		int numberOfResults;

		try {
			numberOfResults = Integer
					.parseInt(systemConfigurationService.getValue(MINIMUM_CHARACTERS_AUTOCOMPLETE, GENERAL_COMPONENT));
		} catch (NumberFormatException e) {
			numberOfResults = resultNumberByDefault;
		}

		return numberOfResults;
	}

	private List<String> getColorList() {
		Colour colour = systemConfigurationService.getObject(COLOUR_CONFIGURATION, MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class);

		String language = languagesFlowBean.getFirstLang();

		if (colour == null || language == null) {
			return new ArrayList<>();
		}

		for (int i = 0; i < colour.getColourList().size(); i++) {
			if (colour.getColourList().get(i).getCountry().equals(language)) {
				return colour.getColourList().get(i).getValues();
			}
		}

		return new ArrayList<>();

	}

	/**
	 * It returns a set of colors used in the auto complete field. The name of
	 * the colors depends on the language used by the browser to create the new
	 * application
	 * 
	 * @param root
	 *            the root of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "autocompleteColor", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody final String autocomplete(@RequestParam(required = true, value = "args") String root) {
		return getJSONListOfColors(getColorList(), getMinCharsOFAutoComplete(), root);
	}

	private ArrayList<String> getListOfColorsMatched(List<String> colours, String text, int maxResults) {
		if (colours == null || text == null || maxResults < 1) {
			return new ArrayList<String>();
		}

		ArrayList<String> listOfColorsMatched = new ArrayList<String>();
		int added = 0;

		// Searches the colours
		for (int i = 0; i < colours.size() && added < maxResults; i++) {
			if (colours.get(i).toUpperCase().contains(text.toUpperCase())) {
				listOfColorsMatched.add(colours.get(i));
				added++;
			}
		}

		return listOfColorsMatched;
	}

	private String colourList2JSON(ArrayList<String> colours) {
		StringBuffer result = new StringBuffer("[");
		colours.stream().forEach(colour -> {
			result.append("\"" + colour + "\",");
		});
		if(',' == result.charAt(result.length()-1)) {
			result.deleteCharAt(result.length() - 1);
		}
		result.append("]");
		return result.toString();
	}

	private String getJSONListOfColors(List<String> colours, int maxResults, String text) {
		return colourList2JSON(getListOfColorsMatched(colours, text, maxResults));
	}

}
