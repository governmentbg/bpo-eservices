<%@page import="eu.ohim.sp.common.ui.form.payment.PayerKindForm" %>
<%@page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
   
        <div class="row">
            <div class="span3">
                <component:select items="${configurationServiceDelegator['activePaymentMethods']}" labelTextCode="payment.method"
                                  path="paymentMethod"
                                  id="payer-method"
                                  itemLabel="value"
                                  itemValue="code" checkRender="true" dataaux="payerDetails"/>
                 <form:hidden id="hiddenCheckPayerDetails" path="checkPayerDetails" />
            </div>
            <div class="span3" id="payerDiv" class="hide">          
                 <component:select path="selectedPayer" 
                                   id="payer-type" 
                                   items="${flowBean.getPayerTypes(configurationServiceDelegator['activePayerTypes'])}" 
                                   itemValue="code" itemLabel="description" labelTextCode="payment.payer" 
                                   containsEmptyValue="true" dataaux="payerTypeCode"/> 
                 <form:hidden id="hiddenPayerType" path="payerType" />
            </div>
        </div>
        <!--span-->
        <div id="paymentDetails" class="hide">
            <div class="row">
                <div class="span8">
                    <legend class="mkTit"><spring:message code="payment.payer.details"/></legend>
                </div>
                <div class="row">
                	<c:set var="otherCode" value="<%= PayerKindForm.OTHER.getCode()%>" />
                	<c:set var="imported" value="${!(flowBean.paymentForm.payerType eq otherCode)}" scope="request"/>
                	<c:if test="${(flowBean.paymentForm.payerType eq otherCode) or (not empty flowBean.paymentForm.name) }">
                    	<component:personInput path="name" checkRender="true" id="firstname1" labelTextCode="payment.payer.field.firstName"
                        	                   formTagClass="span3" divClass="span3"/>
                    </c:if>
                    <c:if test="${(not (flowBean.paymentForm.payerType eq otherCode)) and (empty flowBean.paymentForm.name) }">
                    	<form:hidden path="name" />
                    </c:if>
                    <c:if test="${(flowBean.paymentForm.payerType eq otherCode) or (not empty flowBean.paymentForm.surname) }">
                    	<component:personInput path="surname" checkRender="true" id="surname1" labelTextCode="payment.payer.field.lastName"
                        	                   formTagClass="span3" divClass="span3"/>
                    </c:if>
                    <c:if test="${(not (flowBean.paymentForm.payerType eq otherCode)) and (empty flowBean.paymentForm.surname) }">
                    	<form:hidden path="surname" />
                    </c:if>
                    <c:if test="${(flowBean.paymentForm.payerType eq otherCode) or (not empty flowBean.paymentForm.company) }">
                    	<component:personInput path="company" checkRender="true" id="companyName1"
                                           	   labelTextCode="payment.payer.field.company"
                                               formTagClass="span3" divClass="span3"/>
                    </c:if>
                    <c:if test="${(not (flowBean.paymentForm.payerType eq otherCode)) and (empty flowBean.paymentForm.company) }">
                    	<form:hidden path="company" />
                    </c:if>
                </div>
                <div class="row">
                    <component:personInput path="email" checkRender="true" id="email1" labelTextCode="payment.payer.field.email"
                                           formTagClass="span3" divClass="span3"/>
                    <component:personInput path="address.street" checkRender="true" divClass="span3" formTagClass="span3"
                                           labelTextCode="person.address.field.streetAddress"/>
                    <component:personInput path="address.houseNumber" checkRender="true" divClass="span3" formTagClass="span3"
                                           labelTextCode="person.address.field.houseNumber"/>
                </div>
                <div class="row">
                    <component:personInput path="address.city" checkRender="true" divClass="span3" formTagClass="span3"
                                           labelTextCode="person.address.field.city"/>
                    <component:personInput path="address.postalCode" checkRender="true" divClass="span3" formTagClass="span3"
                                           labelTextCode="person.address.field.postalCode"/>

                    <component:personSelect items="${configurationServiceDelegator['countries']}" path="address.country"
                                            itemLabel="value"
                                            itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
                                            divClass="span3"
                                            labelTextCode="person.address.field.country"/>
                </div>
                <div class="row">
                    <component:personInput path="address.stateprovince" checkRender="true" divClass="span3" formTagClass="span3"
                                           labelTextCode="person.address.field.stateProvince"/>
                </div>
            </div>
        </div>
        <c:set var="imported" value="" scope="request"/>
    