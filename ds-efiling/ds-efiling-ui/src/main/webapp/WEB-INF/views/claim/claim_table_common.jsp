<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:if test="${(not empty flowBean.priorities) or (not empty flowBean.exhpriorities)}">

    <table class="table">
        <tr>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.type"/><a class="sorter" data-val="type"></a></th>
            <th><spring:message code="claim.table.header.country"/><a class="sorter" data-val="country"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <th><spring:message code="claim.table.header.id"/><a class="sorter" data-val="id"></a></th>
            <th><spring:message code="claim.table.header.document"/><a class="sorter" data-val="files"></a></th>
            <c:if test="${functions:isMultipleDesignApplication(flowBean)}">
            	<th><spring:message code="claim.table.header.designNumbers"/><a class="sorter" data-val="designNumbers"></a></th>
            </c:if>	
            <th><spring:message code="claim.table.header.options"/></th>
        </tr>

        <c:forEach var="priorities" items="${flowBean.priorities}" varStatus="rowCounter">
            <tr class="priority">
                <td class="number" data-val="number">${rowCounter.count}</td>
                <td data-val="type"><c:choose>
						<c:when test="${ priorities.type eq 'Internal priority'|| priorities.type eq 'Paris convention'}">
							<c:if test="${priorities.type eq 'Internal priority'}">
								<spring:message code="claim.title.priority.internal" />
							</c:if>
							<c:if test="${priorities.type eq 'Paris convention'}">
								<spring:message code="claim.title.priority" />
							</c:if>
						</c:when>
						<c:otherwise>
							<spring:message code="claim.title.priority" />
						</c:otherwise>
					</c:choose></td>
                <td data-val="country">${priorities.country}</td>
                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                <td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${priorities.date}"/></td>
                <td data-val="id">
                	<spring:eval var="storeprioritiesnumber" expression="priorities.number"/>
									<c:choose>
										<c:when test="${storeprioritiesnumber.length()>10}">
											${storeprioritiesnumber.substring(0,10)}...
										</c:when>
										<c:otherwise>
											${storeprioritiesnumber}
										</c:otherwise>
									</c:choose>
                </td>
                <td data-val="files">
                	<c:choose>
                		<c:when test="${not empty priorities.fileWrapperCopy.storedFiles || not empty priorities.fileWrapperTranslation.storedFiles || not empty priorities.filePriorityCertificate.storedFiles}">
                			<spring:message code="claim.table.documents.yes"/>
                			<c:forEach items="${priorities.fileWrapperCopy.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
		                	<c:forEach items="${priorities.fileWrapperTranslation.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
		                	<c:forEach items="${priorities.filePriorityCertificate.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
						</c:when>
						<c:otherwise>
							<spring:message code="claim.table.documents.tofollow"/>
						</c:otherwise>
					</c:choose>                                
                </td>
                <c:if test="${functions:isMultipleDesignApplication(flowBean)}">
                	<td data-val="designNumbers">
                		${functions:getLinkedDesignsNumbers(priorities)}
                	</td>
                </c:if>
                <td data-val="options">
                   	<a class="edit-icon editPriorityCommon" data-id="${priorities.id}" data-priority-num="${rowCounter.count}"></a>
					<a class="remove-icon removePriorityCommon" data-id="${priorities.id}"></a>
                </td>
            </tr>
        </c:forEach>
       
        <c:forEach var="exhpriorities" items="${flowBean.exhpriorities}" varStatus="rowCounter">
            <tr class="exhpriority">
                <td class="number" data-val="number">${rowCounter.count}</td>
                <td data-val="type"><spring:message code="claim.title.exhibition"/></td>
                <td data-val="country">-</td>
                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                <td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${exhpriorities.firstDate}"/></td>
                <td data-val="id">-</td>
                <td data-val="files">
                	<c:choose>
                		<c:when test="${not empty exhpriorities.fileWrapper.storedFiles}">
	                		<spring:message code="claim.table.documents.yes"/>
		                	<c:forEach items="${exhpriorities.fileWrapper.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
		                </c:when>
						<c:otherwise>
							<spring:message code="claim.table.documents.tofollow"/>
						</c:otherwise>
					</c:choose>    
                </td>
                <c:if test="${functions:isMultipleDesignApplication(flowBean)}">
                	<td data-val="designNumbers">
                		${functions:getLinkedDesignsNumbers(exhpriorities)}
                	</td>
                </c:if>
                <td data-val="options">
                	<a class="edit-icon editExhibitionCommon" data-id="${exhpriorities.id}" data-exhibition-num="${rowCounter.count}"></a>
					<a class="remove-icon removeExhibitionCommon" data-id="${exhpriorities.id}"></a>
                </td>
            </tr>
        </c:forEach>
   
    </table>
</c:if>

<input type="hidden" id="prioritiesSizeValue" value="<fmt:formatNumber value="${flowBean.getPriorities().size()}" minIntegerDigits="2" />" />
<input type="hidden" id="exhibitionsSizeValue" value="<fmt:formatNumber value="${flowBean.getExhpriorities().size()}" minIntegerDigits="2" />" />
