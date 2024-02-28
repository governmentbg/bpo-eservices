<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


   <div id="offline_payment_info">
      <p><spring:message code="general.messages.paylater.info"/></p>
   </div>
   <div id="payLaterAttachment" class="fileuploadInfo flFormRow w80 alignCenter ">
      <component:file labelCode="review.payment.pay.later.label"
                   labelClass="payLaterLabel"
                   pathFilename="payLaterAttachment"
                   fileWrapperPath="payLaterAttachment"
                   fileWrapper="${flowBean.paymentForm.payLaterAttachment}"/>
   </div>