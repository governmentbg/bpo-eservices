<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<sec:authorize var="authenticatedUser" access="isAuthenticated()" />

<div id="signatureCardList" class="applicant">
    <c:set var="signatureDetailsSize" value="0"/>
    <c:if test="${not empty flowBean.signatures}">
        <h4><spring:message code="signature.table.title"/></h4>
        <table id="signaturesList" class="table">

            <tr>
                <th><spring:message code="signature.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="signature.details.table.header.signatoryName"/><a class="sorter" data-val='signatoryName'/></th>
                <th><spring:message code="signature.details.table.header.personCapacity"/><a class="sorter" data-val='personCapacity'/></th>
                <th><spring:message code="signature.details.table.header.associatedText"/><a class="sorter" data-val='associatedText'></a></th>
                <th><spring:message code="signature.details.table.header.options"/></th>
            </tr>
            <c:set var="i" value="0"/>
            <c:forEach var="signature" items="${flowBean.signatures}" varStatus="signatureId">
                <c:set var="i" value="${i+1}"/>
                <tr id="signature_id_${signature.id}">
                    <jsp:include page="/WEB-INF/views/signature/signature_card_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${signature.id}" name="signatureId"/>
                        <jsp:param value="${signature.fullName}" name="signatoryNameParam"/>
                        <jsp:param value="${signature.personCapacity.description}" name="personCapacityParam"/>
                        <jsp:param value="${signature.signatureAssociatedText}" name="positionParam"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="signatureListSize" value="${i}"/>
        </table>
    </c:if>
    
    <c:set var="prevImported" value="false"/>
    <c:if test="${not empty flowBean.signatures}">
    	<c:forEach items="${flowBean.signatures}" var="signatureVar">
    		<c:if test="${signatureVar.imported}">
    			<c:set var="prevImported" value="true"/>
    		</c:if>
    	</c:forEach>
    </c:if>
    
    <c:if test="${prevImported eq 'false' && authenticatedUser}">
    	<div style="display:block">
	    <button id="importSignature" type="button" class="btn fileinput-button">
	        <spring:message code="signature.button.import"/>
	    </button>
	    </div>
    </c:if>
    
</div>