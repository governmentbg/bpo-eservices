<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="sectionId" value="applicationCA" scope="request" />

<section class="applicationCA" id="applicationCA">

    <header>
        <a href="#applicationCA" class="anchorLink"></a>

        <h2>
            <spring:message code="correspondenceAddress.title"/>
        </h2>
    </header>

    <jsp:include page="/WEB-INF/views/review/applicationCA_card_list.jsp"/>
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
</section>