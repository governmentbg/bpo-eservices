<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:if test="${not empty flowBean.divisionalApplications}">

	<c:set var="divisionalApplication" value="${flowBean.divisionalApplication}" />
	<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
	
	<c:set var="sectionId" value="divisionalApplicationSection" scope="request" />
	<component:generic checkRender="true" path="fileDivisionalApplication">
		<c:set var="showFileDivisionalApplication" value="true" />
	</component:generic>
	
    <table class="table" id="divisionalApplicationTable">
        <tr>
        	<th><spring:message code="divisionalApplication.table.header.number"/></th>
			<th><spring:message code="divisionalApplication.table.header.date"/></th>
			<c:if test="${showFileDivisionalApplication}">
				<th><spring:message code="divisionalApplication.table.header.document"/></th>
			</c:if>
			<c:if test="${functions:isMultipleDesignApplication(flowBean)}">
				<th><spring:message code="divisionalApplication.table.header.designNumbers"/></th>
			</c:if>
			<th><spring:message code="divisionalApplication.table.header.options"/></th>
        </tr>

		<tr class="divisionalApplicationRow">
			<td><c:out value="${divisionalApplication.numberDivisionalApplication}" /></td>
			<td><fmt:formatDate type="date" pattern="${dateFormat}" value="${divisionalApplication.dateDivisionalApplication}"/></td>
			<c:if test="${showFileDivisionalApplication}">
				<td>
					<c:choose>
				    	<c:when test="${not empty divisionalApplication.fileDivisionalApplication.storedFiles}">
					    	<spring:message code="divisionalApplication.table.documents.yes"/>
					    	<c:forEach items="${divisionalApplication.fileDivisionalApplication.storedFiles}" var="file">
								<span class="file-size">
									${file.canonicalFileSize != null && file.canonicalFileSize.length() <= 40 ? file.canonicalFileSize : file.canonicalFileSize.substring(0,40)}
								</span>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<spring:message code="divisionalApplication.table.documents.no"/>
						</c:otherwise>
					</c:choose>
				</td>
			</c:if>
			<c:if test="${functions:isMultipleDesignApplication(flowBean)}">
				<td>
					<c:out value="${functions:getLinkedDesignsNumbers(flowBean.divisionalApplication)}" />
				</td>
			</c:if>	
			<td data-val="options">
            	<a class="edit-icon editDivisionalApplication" data-id="${divisionalApplication.id}"></a>
				<a class="remove-icon removeDivisionalApplication" data-id="${divisionalApplication.id}"></a>
			</td>
		</tr>
    </table>
</c:if>
