package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.register.TradeMarkSearchService;

public class SearchTrademarkControllerTest {
	@InjectMocks
	private SearchTrademarkController searchTrademarkController;

	@Mock
	private TradeMarkSearchService tradeMarkSearchService;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void autocompleteTest() {
		String id = "id";
		String office = "office";
		Boolean previousCTM = false;
		String result = "result";
		when(tradeMarkSearchService.getTradeMarkAutocomplete(office, id, 50)).thenReturn(result);
		String r = searchTrademarkController.autocomplete(id, office, previousCTM);

		assertNotNull(r);
		assertEquals(result, r);
		verify(tradeMarkSearchService).getTradeMarkAutocomplete(office, id, 50);

	}

	@Test
	public void autocompleteTestNullautocomplete() {
		String id = "id";
		String office = "office";
		Boolean previousCTM = false;
		String result = "";
		Throwable e = mock(SPException.class);
		when(tradeMarkSearchService.getTradeMarkAutocomplete(office, id, 50)).thenThrow(e);
		String r = searchTrademarkController.autocomplete(id, office, previousCTM);

		assertNotNull(r);
		assertEquals(result, r);
		verify(tradeMarkSearchService).getTradeMarkAutocomplete(office, id, 50);

	}
}
