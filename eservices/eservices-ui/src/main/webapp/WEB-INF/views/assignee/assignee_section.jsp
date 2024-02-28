<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="sectionId" value="${functions:getPersonDataSetionId(flowModeId)}" scope="request"/>

<section class="assignee" id="${sectionId}">

    <header>
        <a href="${sectionId}" class="anchorLink">
        </a>

        <h2><spring:message code="assignee.section.title.${flowModeId}"></spring:message></h2>
    </header>

    <div  id="assigneeCardList">
        <jsp:include page="/WEB-INF/views/assignee/assignee_card_list.jsp"/>
    </div>
    <sec:authorize access="hasRole('Assignee_Add')" var="security_assignee_add" />
    
    <c:if test="${security_assignee_add || !configurationServiceDelegator.securityEnabled}">

        <c:if test="${!fn:endsWith(flowModeId, '-invdenial')}">
            <button id="assigneeTrigger" class="add-button" data-toggle="button" type="button">
                <i class="add-icon"></i>
                <spring:message code="person.button.assignee.${flowModeId}"/>
            </button>
        </c:if>

        <div id="tabAssignee" class="addbox" style="display: none">
            <c:if test="${!fn:endsWith(flowModeId, '-invdenial')}">
                <jsp:include page="/WEB-INF/views/assignee/assignee_import.jsp"/>
            </c:if>
            <div id="assigneeSection">
            </div>
        </div>

        <div id="assigneeMatches">
            <jsp:include page="/WEB-INF/views/assignee/assigneeMatches.jsp"/>
        </div>
    </c:if>
    <div class="hidden" id="saveAssigneeMsg"><spring:message code="assignee.form.action.save"/></div>
    
        
    <c:set var="maxAss" value="assignee.add.maxNumber"/>
    
    <input type="hidden" id="maxAssignees"
       value="${configurationServiceDelegator.getValue(maxAss, 'eu.ohim.sp.core.person')}">    
        <script type="text/javascript">
        	checkMaxAssignees();
        </script>
    
	
   
</section>
