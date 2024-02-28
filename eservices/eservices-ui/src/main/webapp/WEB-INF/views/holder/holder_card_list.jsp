<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div>
    <c:set var="userDataHolderSize" value="0"/>
    <c:set var="prevImportedNatural" value="false"/>
    <c:set var="prevImportedLegal" value="false"/>
    <c:if test="${not empty flowBean.userDataHolders}">
    	        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataHolders" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <!-- <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>-->
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            
            <c:set var="i" value="0"/>
            <c:forEach var="holder" items="${flowBean.userDataHolders}" varStatus="holderId">
            	
            	<c:choose>
				    <c:when test="${holder.type eq 'NATURAL_PERSON' }">
				        <c:set var="prevImportedNatural" value="true"/>
				    </c:when>
				    <c:when test="${holder.type eq 'LEGAL_ENTITY'}">
				        <c:set var="prevImportedLegal" value="true"/>
				    </c:when>
				</c:choose>
            
                <c:set var="i" value="${i+1}"/>
                <tr id="holder_id_${holder.id}">
                    <jsp:include page="/WEB-INF/views/holder/holder_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${holder.id}" name="holderId"/>
                        <jsp:param value="${holder.name}" name="holderName"/>
                        <jsp:param value="${holder.type}" name="holderType"/>
                        <jsp:param value="${holder.address.country}" name="holderCountry"/>
                        <jsp:param value="${holder.imported}" name="holderIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="userDataHolderSize" value="${i}"/>
        </table>
    </c:if>
    
    <c:if test="${not empty flowBean.noUserDataHolders}">

        <c:choose>
            <c:when test="${flowModeId eq 'um-change'  || flowModeId eq 'ep-change'}">
                <h4><spring:message code="person.table.title.holder.um_ep_change"/></h4>
            </c:when>
            <c:otherwise>
                <h4><spring:message code="person.table.title.holder"/></h4>
            </c:otherwise>
        </c:choose>

        <table id="addedHolders" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <!-- <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>-->
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="holder" items="${flowBean.noUserDataHolders}" varStatus="holderId">
            	
            	<c:if test="${holder.imported eq true}">
	            	<c:choose>
					    <c:when test="${holder.type eq 'NATURAL_PERSON'}">
					        <c:set var="prevImportedNatural" value="true"/>
					    </c:when>
					    <c:when test="${holder.type eq 'LEGAL_ENTITY'}">
					        <c:set var="prevImportedLegal" value="true"/>
					    </c:when>
					</c:choose>
				</c:if>
            		
                <c:set var="i" value="${i+1}"/>
                <tr id="holder_id_${holder.id}">
                    <jsp:include page="/WEB-INF/views/holder/holder_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${holder.id}" name="holderId"/>
                        <jsp:param value="${holder.name}" name="holderName"/>
                        <jsp:param value="${holder.type}" name="holderType"/>
                        <jsp:param value="${holder.address.country}" name="holderCountry"/>
                        <jsp:param value="${holder.imported}" name="holderIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    <c:set var="sectionId" value="holder" scope="request"/>

    <c:if test="${configurationServiceDelegator.hasSelectPersonButton(flowModeId, sectionId)}">
        <input type="hidden" class="updatableParent" value="#holderCardList"/>
        <button style="margin-top:10px" type="button" class="btn fileinput-button personSelectButton" data-section="${sectionId}">
            <i class="list-icon"></i> <spring:message code="person.select.button"/>
        </button>
    </c:if>
    
</div>