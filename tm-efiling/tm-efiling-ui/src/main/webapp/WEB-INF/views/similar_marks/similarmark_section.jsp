<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="similar-marks">
<c:set var="service_similarMarks_enabled" value="${configurationServiceDelegator.isServiceEnabled('SimilarTM_Service')}"/>
    <header>
        <h2>
            <spring:message code="similarMarks.risks"/>
        </h2>
    </header>
	<c:set var="markType" value="${flowBean.mainForm.markType}"/>
	<c:choose>
		<c:when test="${markType != 'wordmark' && markType != 'other'}">
			<p class="introtext"><spring:message code="general.messages.similarMarks.noWordRepresentation"/></p>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<spring:message code="similar.marks.warning"/>
			</div>
			<c:if test="${service_similarMarks_enabled}">
				<div id="similarMarks_noResults">
					<div class="loading"></div>
				</div>
			</c:if>
			<c:if test="${service_similarMarks_enabled}">
				<script type="text/javascript"
						src="<%=request.getContextPath()%>/resources/scripts/developers/similarmark_wizard.js">
				</script>
			</c:if>
			<span class="hidden" id="similarTrademarksTableHolder">
			<p class="introtext"><spring:message code="similarMarks.ownMarkInfo.title"/></p>
			<div id="ownMarkInfo">
				<table class="table table-striped">
					<tr>
						<th scope="col"><spring:message code="similarMarks.table.header.mark.description"/></th>
						<th scope="col"><spring:message code="similarMarks.table.header.class"/></th>
					</tr>
					<tr>
						<td>${flowBean.mainForm.wordRepresentation}</td>
						<td>
							<c:forEach var="gs" items="${flowBean.goodsAndServices}">
								<span>${gs.classId}</span>
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
			<br><br>
			<jsp:include page="similarmark_table.jsp"/>
			</span>
		</c:otherwise>
	</c:choose>
</section>