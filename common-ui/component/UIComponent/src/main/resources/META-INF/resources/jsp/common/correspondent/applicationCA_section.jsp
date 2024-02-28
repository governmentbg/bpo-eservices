<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="sectionId" value="applicationCA" scope="request" />

<section class="applicationCA" id="applicationCA">

    <header>
        <a href="#applicationCA" class="anchorLink"></a>

        <h2>
            <spring:message code="correspondenceAddress.title.${flowModeId}"/>
            <c:if test="${flowModeId == 'wizard' or flowModeId == 'pt-efiling' or flowModeId == 'um-efiling' or flowModeId == 'ep-efiling' or flowModeId == 'sv-efiling' or flowModeId == 'spc-efiling' or flowModeId == 'gi-efiling' or flowModeId == 'is-efiling'}">
                <span class="requiredTag">*</span>
            </c:if>
        </h2>
    </header>

    <c:if test="${fn:endsWith(flowModeId, '-changeca') or fn:endsWith(flowModeId, '-generic')}">
        <div class="alert alert-info">
            <spring:message code="personChange.CA.warning.message.${flowModeId}"/>
        </div>
    </c:if>

    <jsp:include page="applicationCA_card_list.jsp"/>
    <sec:authorize access="hasRole('ApplicationCA_Add')" var="security_applicant_add" />
    <c:if test="${security_applicant_add || !configurationServiceDelegator.securityEnabled}">        		
     
		<button type="button" id="applicationCATrigger" class="add-button" data-toggle="button">
			<i class="add-icon"></i>
			<spring:message code="person.button.applicationCA"/>
		</button>
			    
	
		<div id="tabApplicationCA" class="addbox" style="display:none">	
			
			<div id="applicationCASection">	
		   </div>		
		    
	    </div>
	 </c:if>   
	 
    <div class="hidden" id="saveApplicationCAMsg"><spring:message code="applicationCA.form.action.save"/></div>

    <input type="hidden" id="maxApplicationCA"
           value="${configurationServiceDelegator.getValue('applicationCA.add.maxNumber', 'general')}">
    <script type="text/javascript">
        checkMaxApplicationCA();
    </script>

    <div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
        /trademark/register-trademark/persons
    </div>
</section>