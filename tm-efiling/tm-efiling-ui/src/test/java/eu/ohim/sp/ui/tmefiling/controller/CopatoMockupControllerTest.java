package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class CopatoMockupControllerTest {

	@Test
	public void paymentIFrame() {
		CopatoMockupController controller = new CopatoMockupController();
		ModelAndView r = controller.paymentIFrame();
		assertNotNull(r);
		assertEquals("payment/copato_mock_window", r.getViewName());
	}
}
