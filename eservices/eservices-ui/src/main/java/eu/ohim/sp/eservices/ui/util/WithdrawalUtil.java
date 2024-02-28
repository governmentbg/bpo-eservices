package eu.ohim.sp.eservices.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

@Component("withdrawalUtil")
public class WithdrawalUtil {

	private static final List<String> withdrawalStates = new ArrayList<String>(Arrays.asList("Filed", "Application filed",
			"Application examination", "Application pre-publication", "Application published",
			"Application under examination", "Application pending", "Application opposed"));

	private static final List<String> surrenderStates = new ArrayList<String>(
			Arrays.asList("Registered", "Application registered", "Registration published registered",
					"Cancellation pending", "Registration cancellation pending"));

	private static boolean applicable(ESFlowBean bean, String tm, List<String> states) {
		
		if (tm == null || "".equals(tm)) {
			tm = bean.getTmsList().get(0).getApplicationNumber();
		}

		final String applicationNumber = tm;

		Optional<TMDetailsForm> e = bean.getTmsList().stream()
				.filter(x -> x.getApplicationNumber().equals(applicationNumber)).findFirst();

		if (e.isPresent()) {
			return !states.contains(e.get().getTradeMarkStatus());
		}

		return false;
	}
	
	private static String check(ESFlowBean bean, String tm, List<String> states) {
		if (tm == null || "".equals(tm)) {
			tm = bean.getTmsList().get(0).getApplicationNumber();
		}

		final String applicationNumber = tm;
		
		Optional<TMDetailsForm> e = bean.getTmsList().stream()
				.filter(x -> x.getApplicationNumber().equals(applicationNumber)).findFirst();

		if (e.isPresent()) {
			return states.contains(e.get().getTradeMarkStatus()) ? "checked" : "";
		}

		return "";
	}
	
	public static boolean withdrawalIsApplicable(ESFlowBean bean, String tm) {
		return applicable(bean, tm, surrenderStates);
	}

	public static boolean surrenderIsApplicable(ESFlowBean bean, String tm) {
		return applicable(bean, tm, withdrawalStates);
	}
	
	public static String isSurrenderChecked(ESFlowBean bean, String tm) {
		return check(bean, tm, surrenderStates);
	}
	
	public static String isWithdrawalChecked(ESFlowBean bean, String tm) {
		return check(bean, tm, withdrawalStates);
	}
}
