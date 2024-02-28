package eu.ohim.sp.common.ui.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContextUtils;

import eu.ohim.sp.common.logging.model.ApplicationType;
import eu.ohim.sp.common.logging.model.PaymentType;
import eu.ohim.sp.common.logging.model.StatisticalBasics;
import eu.ohim.sp.common.logging.model.StatisticalInfo;
import eu.ohim.sp.common.ui.flow.section.PaymentFlowBean;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.security.authorisation.domain.SPUser;


/**
 * @author ionitdi
 */
public  abstract class StatisticalLoggingInfoProvider
{
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private PaymentFlowBean flowBean;

    public StatisticalInfo getStatisticalInfo()
    {
        StatisticalInfo statInfo = new StatisticalInfo();

        StatisticalBasics basics = new StatisticalBasics();

        basics.setTimestamp(String.valueOf(new Date().getTime()));

        basics.setIP(getIp());

        basics.setUserLanguage(getLanguage());

        basics.setApplicationType(getApplicationType(flowScopeDetails.getFlowModeId()));

        basics.setUsername(getUsername());

        basics.setPaymentType(getPaymentType());

        statInfo.setBasicInfo(basics);

        return statInfo;
    }

    private String getIp()
    {
        return request.getRemoteAddr();
    }

    public abstract ApplicationType getApplicationType(String flowModeId);

    private String getLanguage()
    {
        return RequestContextUtils.getLocaleResolver(request).resolveLocale(request).getLanguage();
    }

    private String getUsername()
    {
        SPUser user  = AuthenticationUtil.getAuthenticatedUser();
        if(user != null)		
        	return user.getUsername();
        else
        	return "";
    }

    private PaymentType getPaymentType()
    {
        if(flowBean.getPaymentForm() == null
                || flowBean.getPaymentForm().getPaymentMethod() == null)
        {
            return null;
        }
        if(flowBean.getPaymentForm().getPaymentMethod().equals("CREDIT_CARD"))
        {
            return PaymentType.CREDIT_CARD;
        }
        if(flowBean.getPaymentForm().getPaymentMethod().equals("BANK_TRANSFER"))
        {
            return PaymentType.BANK_TRANSFER;
        }
        if(flowBean.getPaymentForm().getPaymentMethod().equals("CURRENT_ACCOUNT"))
        {
            return PaymentType.CURRENT_ACCOUNT;
        }
        return null;
    }
}
