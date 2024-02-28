<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div id="tmCardList" style="overflow: auto">
	<c:set var="tmDetailsSize" value="0"/>
	<c:if test="${not empty flowBean.tmsList}">
		<!--<c:set var="warningMessages"><form:errors path="tmDetailsForm.formMessages" /></c:set>
		<c:if test="${not empty warningMessages}">
			<c:set var="concatenated" value=""/>
			<c:forEach items="${warningMessages}" var="message">
				<c:set var="concatenated" value="${concatenated}${message}"/>
			</c:forEach>
			<div id="divErrorsWarnings" style="display:none">
			${concatenated}
			</div>
			<script type="text/javascript">
			showMessageModal($("#divErrorsWarnings").html());
			</script>
		</c:if>-->
		<c:if test="${not empty removedOBList}">
			<p class="eServiceMessageWarning"><spring:message code="removedGrounds.message"></spring:message> </p>
		</c:if>
		<table id="tmsList" class="table">
			<tr>
				<th><spring:message code="tm.details.table.header.number"/><a class="sorter" data-val='number'/></th>
				<th><spring:message code="tm.details.table.header.applicationNumber"/><a class="sorter" data-val='id'/></th>
				<c:if test="${flowScopeDetails.flowModeId != 'tm-opposition' && flowScopeDetails.flowModeId != 'tm-objection'
                	&& flowScopeDetails.flowModeId != 'tm-withdrawal' && flowScopeDetails.flowModeId != 'tm-appeal'}">
					<th><spring:message code="tm.details.table.header.registrationNumber"/><a class="sorter" data-val='regNumber'/></th>
				</c:if>
				<th><spring:message code="tm.details.table.header.representation"/><a class="sorter" data-val='applicant'/></th>
				<c:choose>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-renewal'}">
						<th><spring:message code="tm.details.table.header.expirationDate"/><a class="sorter" data-val='date'/></th>
					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-opposition' || flowScopeDetails.flowModeId == 'tm-invalidity' || flowScopeDetails.flowModeId == 'tm-revocation'}">
						<th><spring:message code="tm.details.table.header.publicationDate"/><a class="sorter" data-val='date'/></th>
					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-objection' || flowScopeDetails.flowModeId == 'tm-withdrawal'|| flowScopeDetails.flowModeId == 'tm-appeal'}"/>
					<c:otherwise>
						<th><spring:message code="tm.details.table.header.registrationDate"/><a class="sorter" data-val='date'/></th>
					</c:otherwise>
				</c:choose>
				<th><spring:message code="tm.details.table.header.tradeMarkType"/><a class="sorter" data-val='type'/></th>
				<th><spring:message code="tm.details.table.header.tradeMarkStatus"/><a class="sorter" data-val='kind'/></th>
				<th><spring:message code="tm.details.table.header.options"/></th>
			</tr>

			<c:set var="i" value="0"/>
			<c:forEach var="tm" items="${flowBean.tmsList}" varStatus="tmId">
				<c:set var="i" value="${i+1}"/>
				<c:set var="selectedStatus" value="?" />
				<c:set var="selectedType" value="?" />
				<c:forEach var="tmType" items="${configurationServiceDelegator['tradeMarkTypes']}">
					<c:if test="${tmType.code == tm.tradeMarkType}">
						<c:set var="selectedType"><spring:message code="${tmType.value}" /></c:set>
					</c:if>
				</c:forEach>
				<c:forEach var="tmStatus" items="${configurationServiceDelegator['tradeMarkStatusList']}">
					<c:if test="${tmStatus.code == tm.tradeMarkStatus}">
						<c:set var="selectedStatus"><spring:message code="${tmStatus.value}" /></c:set>
					</c:if>
				</c:forEach>
				<c:choose>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-renewal'}">
						<c:set var="date">
							<c:if test="${empty tm.expiryDate}">?</c:if>
							<c:if test="${not empty tm.expiryDate}"><fmt:formatDate type="date" pattern="${dateFormat}" value="${tm.expiryDate}"/></c:if>
						</c:set>

					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-opposition' || flowScopeDetails.flowModeId == 'tm-invalidity' || flowScopeDetails.flowModeId == 'tm-revocation'}">
						<c:set var="date">
							<c:if test="${empty tm.publicationDate}">?</c:if>
							<c:if test="${not empty tm.publicationDate}"><fmt:formatDate type="date" pattern="${dateFormat}" value="${tm.publicationDate}"/></c:if>
						</c:set>

					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-objection'}"/>
					<c:otherwise>
						<c:set var="date">
							<c:if test="${empty tm.registrationDate}">?</c:if>
							<c:if test="${not empty tm.registrationDate}"><fmt:formatDate type="date" pattern="${dateFormat}" value="${tm.registrationDate}"/></c:if>
						</c:set>

					</c:otherwise>
				</c:choose>

				<c:set var="type">
					<c:if test="${empty tm.tradeMarkType}">?</c:if>
					<c:if test="${not empty tm.tradeMarkType}">${selectedType}</c:if>
				</c:set>
				<c:set var="status">
					<c:if test="${empty tm.tradeMarkStatus}">?</c:if>
					<c:if test="${not empty tm.tradeMarkStatus}">${selectedStatus}</c:if>
				</c:set>

				<tr id="tm_id_${tm.id}">
					<jsp:include page="/WEB-INF/views/tm_details/tm_card_row.jsp">
						<jsp:param value="${i}" name="rowId"/>
						<jsp:param value="${tm.id}" name="tmId"/>
						<jsp:param value="${tm.applicationNumber}" name="tmApplication"/>
						<jsp:param value="${tm.registrationNumber}" name="tmRegistration"/>
						<jsp:param value="${date}" name="tmDate"/>
						<jsp:param value="${type}" name="tmType"/>
						<jsp:param value="${status}" name="tmStatus"/>
						<jsp:param value="${tm.applicationRepresentation}" name="applicant"/>
						<jsp:param value="${tm.imageRepresentationURI}" name="tmImgURI"/>
						<jsp:param value="${tm.representationAttachment.storedFiles[0].contentType}" name="tmRepresentationType"/>
					</jsp:include>
				</tr>
			</c:forEach>
			<c:set var="tmListSize" value="${i}"/>
		</table>
	</c:if>
</div>