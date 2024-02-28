<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="licence" id="licence">
	<c:set var="sectionId" value="licence" scope="request"/>
	
	<header>
        <a id="licence" class="anchorLink">
        </a>

        <h2><spring:message code="licence.section.title"></spring:message></h2>
    </header>
	
	<jsp:include page="/WEB-INF/views/licence/licence_card_list.jsp"/>
    <sec:authorize access="hasRole('Licence_Add')" var="security_licence_add" />
    
     <c:if test="${security_licence_add || !configurationServiceDelegator.securityEnabled}">		
		 <button id="licenceTrigger" class="add-button" data-toggle="button" type="button">
		      <i class="add-icon"></i>
		      <spring:message code="licence.form.button.licence"/>
		 </button>

        <div id="tabLicence" class="addbox" style="display:none">	
			
			<div id="licenceSection">	
		   </div>		
		    
	    </div>      
    </c:if>
    
    <div class="hidden" id="saveLicenceMsg"><spring:message code="licence.form.action.save"/></div>
    
    <input type="hidden" id="maxLicences" value="${configurationServiceDelegator.getValue('licence.add.maxNumber', 'general')}">
    <script type="text/javascript">
        checkMaxLicences();
    </script>
    
</section>