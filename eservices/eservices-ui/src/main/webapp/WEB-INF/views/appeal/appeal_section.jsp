<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="appeal">
	<c:set var="sectionId" value="appeal" scope="request"/>
	
	<header>
        <a id="appeal" class="anchorLink">
        </a>

        <h2><spring:message code="appeal.section.title"></spring:message></h2>
    </header>
	
	<jsp:include page="/WEB-INF/views/appeal/appeal_card_list.jsp"/>
    <sec:authorize access="hasRole('Appeal_Add')" var="security_appeal_add" />
    
     <c:if test="${security_appeal_add || !configurationServiceDelegator.securityEnabled}">		
		 <button id="appealTrigger" class="add-button" data-toggle="button" type="button">
		      <i class="add-icon"></i>
		      <spring:message code="appeal.form.button.appeal"/>
		 </button>

        <div id="tabAppeal" class="addbox" style="display:none; padding-bottom: 50px">
			
			<div id="appealSection">	
		   </div>		
		    
	    </div>      
    </c:if>
    
    <div class="hidden" id="saveAppealMsg"><spring:message code="appeal.form.action.save"/></div>
    
    <input type="hidden" id="maxAppeals" value="${configurationServiceDelegator.getValue('appeal.add.maxNumber', 'general')}">
    <script type="text/javascript">
        checkMaxAppeals();
    </script>
    
</section>