<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<c:choose>

	<c:when test="${not empty flowBean.locarnoClassifications}">
	
		<c:set var="sectionId" value="locarnoAddClass" scope="request"/>
		
		<form:form id="formAddClass" cssClass="formEF" modelAttribute="locarnoSearchForm">
		
		    <table id="tableLocarnoClassifications" class="table-stripped">
				<thead>
					<tr>
						<th class="class-check"></th>
						<th><spring:message code="design.form.classes.table.header.class"/></th>
						<th><spring:message code="design.form.classes.table.header.product.indication"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${flowBean.locarnoClassificationsCollection}" var="classification">
						<tr>
							<td class="class-check">
								<input name="locarnoClassificationsSelected" type="checkbox" value="${classification.id}" />
							</td>
							<td>${classification.locarnoClass.classFormatted}</td>
							<td>${classification.indication}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form:form>
		
	</c:when>
	
	<c:otherwise>
		
		<br />
		
		<div class="alert alert-error">
			<spring:message code="design.form.classes.search.noResultsFound" />
		</div>
		
	</c:otherwise>
	
</c:choose>	