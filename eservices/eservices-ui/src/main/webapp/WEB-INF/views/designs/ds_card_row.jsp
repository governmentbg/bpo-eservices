<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='Number'>${param.rowId}</td>
<td data-val='id'>
	<c:out value="${param.dsNumber}" />
</td>
<td data-val='DsHtmlViews'>
	<div class="view-image">
		
			<c:set var="uriImage" value=""/>
			<c:choose>
				<c:when test="${not empty param.documentId}">
					<c:set var="uriImage" value="getDocument.htm?documentId=${param.documentId}"/>
				</c:when>
				<c:otherwise>
					<c:set var="uriImage" value="${param.originalFilename}"/>
				</c:otherwise>
			</c:choose>
			<img class="thumb" src="${uriImage}" id="viewImage${param.documentId}" data-file-documentId="${param.documentId}" data-file-name="${param.originalFilename}">		
	</div>
</td> 
<td data-val='ProductIndication'>
	<c:set var="storedsLocarno" value="${param.dsLocarno}" />
		<c:choose>
			<c:when test="${storedsLocarno.length()>10}">
				<c:out value="${storedsLocarno.substring(0,10)}..." />
			</c:when>
			<c:otherwise>
				<c:out value="${storedsLocarno}" />
			</c:otherwise>
		</c:choose>
</td>


<td data-val='Holders'>${param.dsHolders}</td>
<td data-val='status'>
	<c:if test="${param.status != null && param.status != ''}">
		<spring:message code="designStatus.${param.status}"/>
	</c:if>
</td>
<td data-val='Options'>
	<c:set var="checkboxEnabled" value="true"/>
   	<c:if test="${flowModeId == 'ds-security'|| flowModeId == 'ds-bankruptcy' || flowModeId == 'ds-change' || flowModeId == 'ds-changerep'|| flowModeId == 'ds-changeca'|| flowModeId == 'ds-generic'}">
    	<c:set var="checkboxEnabled" value="false"/>
    </c:if>

	<input type="checkbox" class="dessign_choose"  ${param.selected == true? 'checked="checked"': '' }" ${checkboxEnabled == false ? 'disabled="disabled"': '' } data-target="${param.dsId}"/>

	<a id="editDSButton" class="edit-icon" data-val="${param.dsId}" data-rownum="${param.rowId}"></a>
	<%--<a id="deleteDSButton" class="remove-icon" data-val="${param.dsId}"></a>--%>
</td>