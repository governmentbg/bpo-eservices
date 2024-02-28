<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="gshelper" id="gshelper">
	<c:set var="sectionId" value="gshelper" scope="request"/>
	
	<header>
        <a id="gshelper" class="anchorLink">
        </a>

        <h2><spring:message code="gshelper.section.title.${flowModeId }"></spring:message></h2>
    </header>
	
	<jsp:include page="/WEB-INF/views/gshelper/gshelper_card_list.jsp"/>
    <sec:authorize access="hasRole('GSHelper_Add')" var="security_gshelper_add" />
    
     <c:if test="${security_gshelper_add || !configurationServiceDelegator.securityEnabled}">		
		 <button id="gshelperTrigger" class="add-button" data-toggle="button" type="button">
		      <i class="add-icon"></i>
		      <spring:message code="gshelper.form.button.gshelper.${flowModeId }"/>
		 </button>

        <div id="tabGshelper" class="addbox" style="display:none">	
			
			<div id="gshelperSection">	
		   	</div>		
		    
	    </div>      
    </c:if>
    
    <div class="hidden" id="saveGshelperMsg"><spring:message code="gshelper.form.action.save"/></div>
    
    <input type="hidden" id="maxGshelpers" value="${configurationServiceDelegator.getValue('tm.add.maxNumber', 'general')}">
    <script type="text/javascript">
    	checkMaxGshelpers();
    </script>
    
</section>