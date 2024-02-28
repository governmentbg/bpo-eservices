<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td data-val='Number'>${param.rowId}</td>
<td data-val='id'>
	<spring:eval var="storedesignIdentifier" expression="param.dsNumber" />
		<c:choose>
			<c:when test="${storedesignIdentifier.length()>10}">
				<c:out value="${storedesignIdentifier.substring(0,10)}..." />
			</c:when>
			<c:otherwise>
				<c:out value="${storedesignIdentifier}" />
			</c:otherwise>
		</c:choose>
</td>
<td data-val='DsHtmlViews'>
	<div class="view-image">
		
			<c:set var="uriImage" value=""/>
			<c:choose>
				<c:when test="${param.importedDesign}">				
					<c:set var="uriImage" value="${param.originalFilename}"/>
				</c:when>
				<c:otherwise>
				 <c:if test="${param.documentId ne ''}">				
					<c:set var="uriImage" value="getDocument.htm?documentId=${param.documentId}"/>
				 </c:if>					
				</c:otherwise>
			</c:choose>
			<img class="thumb" src="${uriImage}" id="viewImage${param.documentId}" data-file-documentId="${param.documentId}" data-file-name="${param.originalFilename}">		
	</div>
</td> 
<td data-val='ProductIndication'>
	<spring:eval var="storedsLocarno" expression="param.dsLocarno" />
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
<td data-val='Options'>
	<input  class="earlier_dessign_choose" type="checkbox"  ${param.selected == true? 'checked="checked"': '' }" data-target="${param.dsNumber}"/>
	
	<a class="editDSEarlierButton edit-icon" data-val="${param.dsNumber}" data-rownum="${param.rowId}"></a>
    
</td>