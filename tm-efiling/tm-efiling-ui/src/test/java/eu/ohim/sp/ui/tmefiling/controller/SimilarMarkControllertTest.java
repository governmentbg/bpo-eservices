package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ImportServiceInterface;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SimilarMarkControllertTest {

	@InjectMocks
	private SimilarMarksController similarMarkController;
	@Mock
	private FlowBeanImpl flowBean;

	@Mock
	private TradeMarkSearchService tradeMarkSearchService;

	@Mock
	private FlowBeanImpl trademarkFlowBean;

	@Mock
	private FlowBeanFactory flowBeanFactory;

	@Mock
	private ImportServiceInterface importService;

	private String officeCode = "EM";

	@Before
	public void before() {

		MockitoAnnotations.initMocks(this);
		org.springframework.test.util.ReflectionTestUtils.setField(similarMarkController, "officeCode", officeCode);

	}

	@Test
	public void getSimilarMarksTestDefault() {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = new ArrayList<>();
		similarMarksList.add(new SimilarMarkForm());
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);

		String markDenomination = "mark";
		ModelAndView r = similarMarkController.getSimilarMarks(markDenomination);
		assertNotNull(r);
		assertEquals(similarMarksList, r.getModelMap().get("similarMarks"));

	}

	@Test
	public void getSimilarMarksTestEmptyList() {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = new ArrayList<>();

		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);

		String markDenomination = "mark";
		ModelAndView r = similarMarkController.getSimilarMarks(markDenomination);
		assertNotNull(r);
		assertEquals("general.messages.similarMarks.noResultsFound", r.getModelMap().get("errorCode"));

	}

	@Test
	public void getSimilarMarksTestNull() {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = null;
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);

		String markDenomination = "mark";
		ModelAndView r = similarMarkController.getSimilarMarks(markDenomination);
		assertNotNull(r);
		assertEquals("general.messages.similarMarks.noResultsFound", r.getModelMap().get("errorCode"));

	}

	public static final Object[] getBlankMarkDenominations() {
		return new String[] { null, "", " ", "   " };
	}

	@Test
	@Parameters(method = "getBlankMarkDenominations")
	public void getSimilarMarksTestBlankMark(String mark) {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = null;
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);
		String markDenomination = "mark";
		when(mainForm.getWordRepresentation()).thenReturn(markDenomination);
		ModelAndView r = similarMarkController.getSimilarMarks(mark);
		assertNotNull(r);
		assertEquals(similarMarksList, r.getModelMap().get("similarMarks"));

	}

	@Test
	@Parameters(method = "getBlankMarkDenominations")
	public void getSimilarMarksTestBlankMarkAndNullWordRepresentation(String mark) {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = new ArrayList<>();
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);

		when(mainForm.getWordRepresentation()).thenReturn(null);
		ModelAndView r = similarMarkController.getSimilarMarks(mark);
		assertNotNull(r);

	}

	@Test
	public void getSimilarMarkListTest() throws JsonGenerationException, JsonMappingException, IOException {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = new ArrayList<>();
		similarMarksList.add(new SimilarMarkForm());
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);

		when(mainForm.getWordRepresentation()).thenReturn(null);
		String markDenomination = "mark";
		String r = similarMarkController.getSimilarMarksList(markDenomination);
		assertNotNull(r);
		String jsonexpected = new ObjectMapper().writeValueAsString(similarMarksList);
		assertEquals(jsonexpected, r);
	}

	@Test
	@Parameters(method = "getBlankMarkDenominations")
	public void getSimilarMarkListTestBlank(String mark)
			throws JsonGenerationException, JsonMappingException, IOException {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = new ArrayList<>();
		similarMarksList.add(new SimilarMarkForm());
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);
		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList);

		when(mainForm.getWordRepresentation()).thenReturn("mark");

		String r = similarMarkController.getSimilarMarksList(mark);
		assertNotNull(r);
		String jsonexpected = new ObjectMapper().writeValueAsString(similarMarksList);
		assertEquals(jsonexpected, r);
	}

	@Test
	@Parameters(method = "getBlankMarkDenominations")
	public void getSimilarMarkListTestBlankNullWordRepresentation(String mark)
			throws JsonGenerationException, JsonMappingException, IOException {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);
		List<SimilarMarkForm> similarMarksList = new ArrayList<>();
		similarMarksList.add(new SimilarMarkForm());
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);

		when(mainForm.getWordRepresentation()).thenReturn(null);

		String r = similarMarkController.getSimilarMarksList(mark);
		assertNull(r);

	}

	

	@Test(expected = SPException.class)
	public void getSimilarMarkListTestBlankThrowException() {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);

		List<SimilarMarkForm> similarMarksList = new ArrayList<>();
		similarMarksList.add(new SimilarMarkForm());
		
		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);

		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList).thenReturn(similarMarksList)
				.thenThrow(new RuntimeException());

		when(mainForm.getWordRepresentation()).thenReturn(null);

		similarMarkController.getSimilarMarksList("asdf");

	}

	@Test
	public void getSimilarMarkListTestEmptylist() {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);

		List<SimilarMarkForm> similarMarksList = new ArrayList<>();

		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);

		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList).thenReturn(similarMarksList)
				.thenThrow(new RuntimeException());

		when(mainForm.getWordRepresentation()).thenReturn(null);

		String r = similarMarkController.getSimilarMarksList("asdf");
		assertNull(r);

	}

	@Test
	public void getSimilarMarkListTestNulllist() {

		MainForm mainForm = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainForm);

		List<SimilarMarkForm> similarMarksList = null;

		when(importService.getSimilarTradeMarks(officeCode, flowBean)).thenReturn(similarMarksList);

		when(trademarkFlowBean.getSimilarMarks()).thenReturn(similarMarksList).thenReturn(similarMarksList)
				.thenThrow(new RuntimeException());

		when(mainForm.getWordRepresentation()).thenReturn(null);

		String r = similarMarkController.getSimilarMarksList("asdf");
		assertNull(r);

	}

}
