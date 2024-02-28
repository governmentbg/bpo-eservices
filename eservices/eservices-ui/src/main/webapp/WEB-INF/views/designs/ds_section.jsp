<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="trademark" id="ds_section">

 	<header>
        <a id="ds_section_" class="anchorLink">
        </a>

        <h2><spring:message code="ds.details.title"></spring:message></h2>
    </header>

	<c:set var="sectionId" value="ds_section" scope="request"/>
    
 	<jsp:include page="/WEB-INF/views/designs/ds_card_list.jsp"/>

	<div id="importDS" class="addbox" style="display: none">
		<jsp:include page="/WEB-INF/views/designs/ds_import.jsp"/>
	</div> 

    <div id="tabDS" class="addbox" style="display: none">
    	<ul class="controls">
	        <li>
	            <a class="cancelButton design"><spring:message
	                    code="applicant.form.action.cancelAdd.top"/></a>
	        </li>
	        <li>
	            <button class="addDS addDSDetails" type="button">
	                <i class="add-icon"> </i>
	                <spring:message code="applicant.form.action.add.top"/>
	            </button>
	        </li>
		</ul>
		<br />
		<br />
    	<div id="dsSection">
    	
    	</div>
    <%--	<div id="lorcanoSection">
    		<jsp:include page="/WEB-INF/views/designs/lorcano.jsp"/>
		</div> --%>
		<br />
		<ul class="controls">
	        <li>
	            <a class="cancelButton design"><spring:message
	                    code="applicant.form.action.cancelAdd.top"/></a>
	        </li>
	        <li>
	            <button class="addDS addDSDetails" type="button">
	                <i class="add-icon"> </i>
	                <spring:message code="applicant.form.action.add.top"/>
	            </button>
	        </li>
		</ul>
		<br />
	</div>

	<c:choose>
		<c:when test="${flowModeId == 'ds-licence'}">
			<input type="hidden" id="maxDSs" value="${configurationServiceDelegator.getValue('licensedDesign.maxNumber', 'general')}">
		</c:when>
		<c:otherwise>
			<input type="hidden" id="maxDSs" value="${configurationServiceDelegator.getValue('ds.add.maxNumber', 'general')}">
		</c:otherwise>
	</c:choose>
	
    <script type="text/javascript">
        checkMaxDSs();
    </script>
        
</section>
