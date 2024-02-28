<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark" id="tm_section">
	<c:set var="sectionId" value="tm_section" scope="request"/>

 	<header>
        <a id="tm_section" class="anchorLink">
        </a>

        <h2><spring:message code="tm.details.title"></spring:message></h2>
    </header>
    
    <jsp:include page="/WEB-INF/views/tm_details/tm_card_list.jsp"/>
    
    <button id="tmTrigger" class="add-button" data-toggle="button" type="button">
       <i class="add-icon"></i>
       <spring:message code="tm.section.button.trademark"/>
	</button>

	<jsp:include page="/WEB-INF/views/unpublished/unpublished_app_select.jsp">
		<jsp:param value="${sectionId}" name="sectionId"/>
		<jsp:param value="TM" name="objectType"/>
		<jsp:param value="TM" name="mainObject"/>
	</jsp:include>
	
	<div id="importTM" class="addbox" style="display: none">
		<jsp:include page="/WEB-INF/views/tm_details/tm_import.jsp"/>
	</div>

    <div id="tabTM" class="addbox" style="display: none">
        <c:set var="actionButtonClassName" value="addTMDetails" />
		<c:if test="${flowModeId eq 'tm-invalidity' or flowModeId eq 'ds-invalidity' or flowModeId eq 'tm-objection' or
		              flowModeId eq 'tm-opposition' or flowModeId eq 'tm-revocation'}">
            <c:set var="actionButtonClassName" value="addOppoTMDetails" />
		</c:if>
    	<ul class="controls">
	        <li>
	            <a class="cancelButton trademark"><spring:message
	                    code="applicant.form.action.cancelAdd.top"/></a>
	        </li>
	        <li>
	            <button class="addTM ${actionButtonClassName}" type="button">
	                <i class="add-icon"> </i>
	                <spring:message code="applicant.form.action.add.top"/>
	            </button>
	        </li>
		</ul>
		<br />
		<br />
    	<div id="tmSection">
    	
    	</div>


		<br />
		<ul class="controls">
	        <li>
	            <a class="cancelButton trademark"><spring:message
	                    code="applicant.form.action.cancelAdd.top"/></a>
	        </li>
	        <li>
	            <button class="addTM ${actionButtonClassName}" type="button">
	                <i class="add-icon"> </i>
	                <spring:message code="applicant.form.action.add.top"/>
	            </button>
	        </li>
		</ul>
		<br />
	</div>

	<c:if test="${flowModeId == 'tm-generic'}">
		<div style="margin-top: 20px" class="alert alert-info">
			<spring:message code="select.userdoc.relation.info"/>
		</div>
	</c:if>

	<c:choose>
		<c:when test="${flowModeId == 'tm-opposition' || flowModeId == 'tm-invalidity' || flowModeId == 'tm-revocation'}">
	 		<input type="hidden" id="maxTMs" value="${configurationServiceDelegator.getValue('opposedTradeMark.maxNumber', 'general')}">
	     </c:when>
		<c:when test="${flowModeId == 'tm-licence'}">
			<input type="hidden" id="maxTMs" value="${configurationServiceDelegator.getValue('licensedTradeMark.maxNumber', 'general')}">
		</c:when>
	     <c:otherwise>
	     	<input type="hidden" id="maxTMs" value="${configurationServiceDelegator.getValue('tm.add.maxNumber', 'general')}">
	     </c:otherwise>
	</c:choose>
    <script type="text/javascript">
        checkMaxTMs();
    </script>
        
</section>
