<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
<div id="gshelperCardList">
    
    <c:if test="${not empty flowBean.gsHelpers}">
       
        <table id="addedGshelpers" class="table">

            <tr>
                <th><spring:message code="gshelper.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="gshelper.table.header.tmApplicationNumber"/><a class="sorter" data-val='tmAppNum'/></th>
                <th><spring:message code="gshelper.table.header.extent"/><a class="sorter" data-val='extent'/></th>
                <th><spring:message code="gshelper.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${gshelpersSize}"/>
            <c:forEach var="gshelper" items="${flowBean.gsHelpers}" varStatus="gshelperId">
                <c:set var="i" value="${i+1}"/>
                <tr id="gshelper_id_${gshelper.id}">                
                    <jsp:include page="/WEB-INF/views/gshelper/gshelper_card_row.jsp">
                    	<jsp:param name="rowId" value="${i}" />
                    	<jsp:param name="gshelperId" value="${gshelper.id}"/> 
                    	<jsp:param name="tmApplicationNumber" value="${gshelper.tmApplicationNumber}" />
                    	<jsp:param name="extent" value="${gshelper.extent}" />
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>