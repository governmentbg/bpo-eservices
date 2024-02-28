<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
<div id="appealCardList">
    
    <c:if test="${not empty flowBean.appeals}">
        <table id="addedAppeals" class="table">

            <tr>
                <th><spring:message code="appeal.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="appeal.table.header.kind"/><a class="sorter" data-val='kind'/></th> 
                <th><spring:message code="appeal.table.header.decisionDate"/><a class="sorter" data-val='appeal_date'/></th>
                <c:if test="${fn:endsWith(flowModeId, '-appeal') }">
                	<th><spring:message code="appeal.table.header.decisionNumber"/><a class="sorter" data-val='appeal_num'/></th>
                	
                	<th><spring:message code="appeal.table.header.opposition.filing.date"/><a class="sorter" data-val='appeal_oppo_filing'/></th>
                </c:if>
                
                <th><spring:message code="appeal.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${appealsSize}"/>
            <c:forEach var="appeal" items="${flowBean.appeals}" varStatus="appealId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="appealDate">
                	<fmt:formatDate type="date" pattern="${dateFormat}" value="${appeal.decisionDate}"/>
                </c:set>
                <c:set var="oppoDate">
                	<fmt:formatDate type="date" pattern="${dateFormat}" value="${appeal.oppositionFilingDate}"/>
                </c:set>
                <tr id="appeal_id_${appeal.id}">                
                    <jsp:include page="/WEB-INF/views/appeal/appeal_card_row.jsp">
                    	<jsp:param name="rowId" value="${i}" />
                    	<jsp:param name="appealId" value="${appeal.id}"/> 
                    	<jsp:param name="kind" value="${appeal.appealKind != null ? appeal.appealKind.labelCode : 'Unknown'}"/> 
                        <jsp:param name="number" value="${appeal.decisionNumber}" />
                        <jsp:param name="date" value="${appealDate}" />
                        <jsp:param name="oppoDate" value="${oppoDate}" />
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>