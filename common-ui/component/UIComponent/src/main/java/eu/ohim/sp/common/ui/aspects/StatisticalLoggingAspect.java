package eu.ohim.sp.common.ui.aspects;

import eu.ohim.sp.common.logging.StatisticalLogger;
import eu.ohim.sp.common.logging.model.ActionType;
import eu.ohim.sp.common.logging.model.StatisticalInfo;
import eu.ohim.sp.common.ui.util.LoggedForStatistics;
import eu.ohim.sp.common.ui.util.StatisticalLoggingInfoProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ionitdi
 */
@Aspect
public class StatisticalLoggingAspect {
	@Autowired
	private StatisticalLoggingInfoProvider provider;

	@Autowired
	private StatisticalLogger statisticalLogger;

	@Pointcut("execution(@eu.ohim.sp.common.ui.util.LoggedForStatistics * eu.ohim.sp.*.ui..*(..))")
	public void methodCallIrrelevantParams() {
	}

	@Pointcut("execution(@eu.ohim.sp.common.ui.util.LoggedForStatistics * eu.ohim.sp.*.ui..*(..))")
	public void methodCallWithFlowModeId() {
	}

	@Around("methodCallIrrelevantParams() || methodCallWithFlowModeId()")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		StatisticalInfo statisticalInfo = getStatisticalInfo(proceedingJoinPoint);

		try {
			proceedingJoinPoint.proceed();
		} catch (RuntimeException e) {
			statisticalInfo.getBasicInfo().setAction(ActionType.TRANSACTIONS_FAILED);
		} finally {
			statisticalLogger.log(statisticalInfo);
		}
	}


	protected StatisticalInfo getStatisticalInfo(ProceedingJoinPoint proceedingJoinPoint) {
		StatisticalInfo statisticalInfo = provider.getStatisticalInfo();

		// get logger annotation parameter
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		LoggedForStatistics logAnnotation = signature.getMethod().getAnnotation(LoggedForStatistics.class);
		statisticalInfo.getBasicInfo().setAction(logAnnotation.ActionType());

		// if the annotation specifies that the method is indeed passing the flow mode id as the only string parameter
		// set flow mode id from advised method's first parameter
		if (logAnnotation.passingFlowModeId()) {
			String firstMethodArgument = (String) proceedingJoinPoint.getArgs()[0];
			statisticalInfo.getBasicInfo().setApplicationType(provider.getApplicationType(firstMethodArgument));
		}

		return statisticalInfo;
	}
}