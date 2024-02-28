<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
<div id="licenceCardList">
    
    <c:if test="${not empty flowBean.licences}">
        <h4><spring:message code="licence.form.table.title"/></h4>
        <table id="addedLicences" class="table">

            <tr>
                <th><spring:message code="licence.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="licence.table.header.kind"/><a class="sorter" data-val='kind'/></th>            
                <th><spring:message code="licence.table.header.territory"/><a class="sorter" data-val='territory'/></th>
                <th><spring:message code="licence.table.header.endDate"/><a class="sorter" data-val='endDate'/></th>
                <c:if test="${flowModeId == 'tm-licence' }">
                	<th><spring:message code="licence.table.header.extent"/><a class="sorter" data-val='sublicence'/></th>
                </c:if>
                <th><spring:message code="licence.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${licencesSize}"/>
            <c:forEach var="licence" items="${flowBean.licences}" varStatus="licenceId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="endDate">
                	<fmt:formatDate type="date" pattern="${dateFormat}" value="${licence.periodLimitationEndDate}"/>
                </c:set>
                <tr id="licence_id_${licence.id}">                
                    <jsp:include page="/WEB-INF/views/licence/licence_card_row.jsp">
                    	<jsp:param name="rowId" value="${i}" />
                    	<jsp:param name="licenceId" value="${licence.id}"/> 
                    	<jsp:param name="sublicence" value="${licence.subLicenceIndicator}" />
                    	<jsp:param name="kind" value="${licence.licenceKind != null ? licence.licenceKind.toString() : 'Unknown'}"/> 
                        <jsp:param name="territory" value="${licence.territoryLimitationIndicator}" />
                        <jsp:param name="endDate" value="${endDate}" />
                        <jsp:param name="extent" value="${licence.gsHelper.extent}" />
                        <jsp:param name="upToExpiration" value="${licence.periodLimitationIndicator}" />
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>