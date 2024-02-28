package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.flow.section.LanguagesFlowBean;
import eu.ohim.sp.common.ui.form.resources.ColourForm;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.colour.xsd.Colour;
import eu.ohim.sp.core.configuration.domain.colour.xsd.Colourlist;
import eu.ohim.sp.ui.tmefiling.controller.ColorController;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.form.MainForm;

public class ColorControllerTest {

	@InjectMocks
	ColorController colorController;

	@Mock
	TMFlowBean flowBean;

	@Mock
	private LanguagesFlowBean languagesFlowBean;

	@Mock
	private ConfigurationService systemConfigurationService;

	@Before
	public void before() {
		flowBean = mock(FlowBeanImpl.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addColourTest() {
		ColourForm colourForm = mock(ColourForm.class);
		BindingResult result = mock(BindingResult.class);

		String format = "format";
		String value = "value";

		when(result.hasErrors()).thenReturn(false);
		when(colourForm.getFormat()).thenReturn(format);
		when(colourForm.getValue()).thenReturn(value);
		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		when(flowBean.getMainForm().getColourList()).thenReturn(new ArrayList<>());

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.addColour(colourForm, result, sectionId);
		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().size() == 1);
		assertEquals(format, flowBean.getMainForm().getColourList().get(0).getFormat());
		assertEquals(value, flowBean.getMainForm().getColourList().get(0).getValue());
	}

	@Test
	public void addColourTestDouble() {
		ColourForm colourForm = mock(ColourForm.class);
		BindingResult result = mock(BindingResult.class);

		String format = "format";
		String value = "value";

		when(result.hasErrors()).thenReturn(false);
		when(colourForm.getFormat()).thenReturn(format);
		when(colourForm.getValue()).thenReturn(value);
		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		when(flowBean.getMainForm().getColourList()).thenReturn(new ArrayList<>());

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.addColour(colourForm, result, sectionId);
		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		colorController.addColour(colourForm, result, sectionId);

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().size() == 1);
		assertEquals(format, flowBean.getMainForm().getColourList().get(0).getFormat());
		assertEquals(value, flowBean.getMainForm().getColourList().get(0).getValue());
	}

	@Test
	public void saveColourTest() {
		ColourForm colourForm = mock(ColourForm.class);
		BindingResult result = mock(BindingResult.class);

		String format = "format";
		String value = "value";

		when(result.hasErrors()).thenReturn(false);
		when(colourForm.getFormat()).thenReturn(format);
		when(colourForm.getValue()).thenReturn(value);
		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = new ArrayList<>();
		ColourForm e = new ColourForm();
		String formatOld = "format";
		e.setFormat(formatOld);
		String valueOld = "value";
		e.setValue(valueOld);
		colorList.add(e);

		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.saveColour(formatOld, valueOld, format, value, sectionId, colourForm,
				result);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().size() == 1);
		assertEquals(format, flowBean.getMainForm().getColourList().get(0).getFormat());
		assertEquals(value, flowBean.getMainForm().getColourList().get(0).getValue());
	}

	@Test
	public void removeColourTest() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = new ArrayList<>();
		ColourForm e = new ColourForm();
		String formatOld = "old_format";
		e.setFormat(formatOld);
		String valueOld = "old_value";
		e.setValue(valueOld);
		colorList.add(e);

		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.removeColour(formatOld, valueOld, sectionId);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().size() == 0);

	}

	@Test
	public void removeColourTestNotFound() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = new ArrayList<>();
		ColourForm e = new ColourForm();
		String formatOld = "old_format";
		e.setFormat(formatOld);
		String valueOld = "old_value";
		e.setValue(valueOld);
		colorList.add(e);

		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.removeColour("newformat", "newValue", sectionId);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().size() == 1);
		assertEquals(formatOld, flowBean.getMainForm().getColourList().get(0).getFormat());
		assertEquals(valueOld, flowBean.getMainForm().getColourList().get(0).getValue());

	}

	@Test
	public void removeAllColoursTest() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = new ArrayList<>();
		ColourForm e = new ColourForm();
		String formatOld = "old_format";
		e.setFormat(formatOld);
		String valueOld = "old_value";
		e.setValue(valueOld);
		colorList.add(e);

		flowBean.getMainForm().setColourList(colorList);

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.removeAllColours(sectionId);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().isEmpty());

	}

	@Test
	public void autocompleteTestLanguageNull() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		Colour colour = mock(Colour.class);
		List<Colourlist> colourList = new ArrayList<>();
		Colourlist hun = new Colourlist("hu", Arrays.asList("yellow", "red", "white", "green", "black", "gray"));
		colourList.add(hun);
		when(colour.getColourList()).thenReturn(colourList);
		when(systemConfigurationService.getObject(ColorController.COLOUR_CONFIGURATION, ColorController.MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class)).thenReturn(colour);
		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("valami");

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void autocompleteTest() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		Colour colour = mock(Colour.class);
		List<Colourlist> colourList = new ArrayList<>();
		Colourlist hun = new Colourlist("hu", Arrays.asList("yellow", "red", "white", "green", "black", "gray"));
		colourList.add(hun);
		when(languagesFlowBean.getFirstLang()).thenReturn("hu");
		when(colour.getColourList()).thenReturn(colourList);
		when(systemConfigurationService.getObject(ColorController.COLOUR_CONFIGURATION, ColorController.MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class)).thenReturn(colour);
		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("valami");

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void autocompleteTestNoLang() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		Colour colour = mock(Colour.class);
		List<Colourlist> colourList = new ArrayList<>();
		Colourlist hun = new Colourlist("en", Arrays.asList("yellow", "red", "white", "green", "black", "gray"));
		colourList.add(hun);
		when(languagesFlowBean.getFirstLang()).thenReturn("hu");
		when(colour.getColourList()).thenReturn(colourList);
		when(systemConfigurationService.getObject(ColorController.COLOUR_CONFIGURATION, ColorController.MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class)).thenReturn(colour);
		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("valami");

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void autocompleteTestFound() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		Colour colour = mock(Colour.class);
		List<Colourlist> colourList = new ArrayList<>();
		Colourlist hun = new Colourlist("hu", Arrays.asList("yellow", "red", "white", "green", "black", "gray"));
		colourList.add(hun);
		when(languagesFlowBean.getFirstLang()).thenReturn("hu");
		when(colour.getColourList()).thenReturn(colourList);
		when(systemConfigurationService.getObject(ColorController.COLOUR_CONFIGURATION, ColorController.MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class)).thenReturn(colour);
		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("yell");

		assertNotNull(response);
		assertEquals("[\"yellow\"]", response);

	}

	@Test
	public void autocompleteTestNullText() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		Colour colour = mock(Colour.class);
		List<Colourlist> colourList = new ArrayList<>();
		Colourlist hun = new Colourlist("hu", Arrays.asList("yellow", "red", "white", "green", "black", "gray"));
		colourList.add(hun);
		when(languagesFlowBean.getFirstLang()).thenReturn("hu");
		when(colour.getColourList()).thenReturn(colourList);
		when(systemConfigurationService.getObject(ColorController.COLOUR_CONFIGURATION, ColorController.MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class)).thenReturn(colour);
		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete(null);

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void autocompleteTestColourNull() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(systemConfigurationService.getObject(ColorController.COLOUR_CONFIGURATION, ColorController.MODULE,
				eu.ohim.sp.core.configuration.domain.colour.xsd.Colour.class)).thenReturn(null);
		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("valami");

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void autocompleteTestColoursNull() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("value");

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void autocompleteTestColoursNullAndWrongSystemParam() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(systemConfigurationService.getValue("minimum.characters.autocomplete", "general")).thenReturn("ZXX100");

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;
		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String response = colorController.autocomplete("value");

		assertNotNull(response);
		assertEquals("[]", response);

	}

	@Test
	public void removeColourTestNullColourList() {

		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(false);

		when(flowBean.getMainForm()).thenReturn(mock(MainForm.class));
		List<ColourForm> colorList = null;

		String formatOld = "old_format";
		String valueOld = "old_value";

		when(flowBean.getMainForm().getColourList()).thenReturn(colorList);

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.removeColour(formatOld, valueOld, sectionId);

		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNull(flowBean.getMainForm().getColourList());

	}

	@Test
	public void addColourTestColourListNull() {
		ColourForm colourForm = mock(ColourForm.class);
		BindingResult result = mock(BindingResult.class);

		String format = "format";
		String value = "value";

		when(result.hasErrors()).thenReturn(false);
		when(colourForm.getFormat()).thenReturn(format);
		when(colourForm.getValue()).thenReturn(value);
		MainForm mainForm = new MainForm();
		when(flowBean.getMainForm()).thenReturn(mainForm);
		mainForm.setColourList(null);
		// when(flowBean.getMainForm().getColourList()).thenReturn(null);

		String sectionId = "sectionid";
		ModelAndView returnValue = colorController.addColour(colourForm, result, sectionId);
		assertNotNull(returnValue);
		assertNotNull(returnValue.getModelMap().get("sectionId"));
		assertEquals(sectionId, returnValue.getModelMap().get("sectionId"));

		assertNotNull(flowBean.getMainForm().getColourList());
		assertTrue(flowBean.getMainForm().getColourList().size() == 1);
		assertEquals(format, flowBean.getMainForm().getColourList().get(0).getFormat());
		assertEquals(value, flowBean.getMainForm().getColourList().get(0).getValue());
	}
}
