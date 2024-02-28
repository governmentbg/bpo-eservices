<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${(not empty flowBean.priorities) 
	or (not empty flowBean.seniorities) 
	or (not empty flowBean.exhpriorities)
	or (not empty flowBean.transformations)
	or (not empty flowBean.conversions)}">

    <table class="table">
        <tr>
            <th><spring:message code="claim.table.header.number"/><a class="sorter" data-val="number"></a></th>
            <th><spring:message code="claim.table.header.type"/><a class="sorter" data-val="type"></a></th>
            <th><spring:message code="claim.table.header.country"/><a class="sorter" data-val="country"></a></th>
            <th><spring:message code="claim.table.header.date"/><a class="sorter" data-val="date"></a></th>
            <th><spring:message code="claim.table.header.id"/><a class="sorter" data-val="id"></a></th>
            <th><spring:message code="claim.table.header.document"/><a class="sorter" data-val="files"></a></th>
            <th><spring:message code="claim.table.header.options"/></th>
        </tr>

        <c:forEach var="priorities" items="${flowBean.priorities}" varStatus="rowCounter">
            <tr class="priority">
                <td class="number" data-val="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
                <td data-val="type"><spring:message code="claim.title.priority"/></td>
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
                		<c:when test="${priorities.fileWrapperCopy.attachment || priorities.fileWrapperTranslation.attachment || priorities.filePriorityCertificate.attachment}">
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
                <td data-val="options">
                   	<a class="edit-icon editPriorityCommon" data-id="${priorities.id}"></a>
					<a class="remove-icon removePriorityCommon" data-id="${priorities.id}"></a>
                </td>
            </tr>
        </c:forEach>
        <c:forEach var="seniorities" items="${flowBean.seniorities}" varStatus="rowCounter">
            <tr class="seniority">
                <td class="number" data-val="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
                <td data-val="type"><spring:message code="claim.title.seniority"/></td>
                <td data-val="country">${seniorities.country}</td>
                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                <td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${seniorities.filingDate}"/></td>
                <td data-val="id">
                <spring:eval var="storeclaims" expression="seniorities.filingNumber"/>
									<c:choose>
										<c:when test="${storeclaims.length()>10}">
											${storeclaims.substring(0,10)}...
										</c:when>
										<c:otherwise>
											${storeclaims}
										</c:otherwise>
									</c:choose>
                </td>
                <td data-val="files">
                	<c:choose>
                		<c:when test="${seniorities.fileWrapperCopy.attachment || seniorities.fileWrapperTranslation.attachment || seniorities.fileSeniorityCertificate.attachment}">
	                		<spring:message code="claim.table.documents.yes"/>
		                	<c:forEach items="${seniorities.fileWrapperCopy.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
		                	<c:forEach items="${seniorities.fileWrapperTranslation.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
		                	<c:forEach items="${seniorities.fileSeniorityCertificate.storedFiles}" var="file">
		                		<span class="file-size">${file.canonicalFileSize!=null && file.canonicalFileSize.length()<=40?file.canonicalFileSize:file.canonicalFileSize.substring(0,40)}</span>
		                	</c:forEach>
		                </c:when>
						<c:otherwise>
							<spring:message code="claim.table.documents.tofollow"/>
						</c:otherwise>
					</c:choose>          
                </td>
                <td data-val="options">
                	<a class="edit-icon editSeniorityCommon" data-id="${seniorities.id}"></a>
					<a class="remove-icon removeSeniorityCommon" data-id="${seniorities.id}"></a>
                </td>
            </tr>
        </c:forEach>
        <c:forEach var="exhpriorities" items="${flowBean.exhpriorities}" varStatus="rowCounter">
            <tr class="exhpriority">
                <td class="number" data-val="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
                <td data-val="type"><spring:message code="claim.title.exhibition"/></td>
                <td data-val="country">-</td>
                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                <td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${exhpriorities.firstDate}"/></td>
                <td data-val="id">-</td>
                <td data-val="files">
                	<c:choose>
                		<c:when test="${exhpriorities.fileWrapper.attachment}">
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
                <td data-val="options">
                	<a class="edit-icon editExhibitionCommon" data-id="${exhpriorities.id}"></a>
					<a class="remove-icon removeExhibitionCommon" data-id="${exhpriorities.id}"></a>
                </td>
            </tr>
        </c:forEach>
		<spring:eval var="ctmTransformationOffice" expression="@propertyConfigurer.getProperty('tmefiling.ctm.transformation.office.code')" />
		<c:forEach var="transformations" items="${flowBean.transformations}" varStatus="rowCounter">
			<c:choose>
				<c:when test="${!(transformations.transformationCountryCode eq ctmTransformationOffice)}">
					<tr class="transformation">
						<td class="number" data-val="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
						<td data-val="type"><spring:message code="claim.title.transformation"/></td>
						<td data-val="country">WO</td>
						<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
						<td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${transformations.irDate}"/></td>
						<td data-val="id">
							<spring:eval var="storeclaims" expression="transformations.irNumber"/>
							<c:choose>
								<c:when test="${storeclaims.length()>10}">
									${storeclaims.substring(0,10)}...
								</c:when>
								<c:otherwise>
									${storeclaims}
								</c:otherwise>
							</c:choose>
						</td>
						<td data-val="files"></td>
						<td data-val="options">
							<a class="edit-icon editTransformationCommon" data-id="${transformations.id}"></a>
							<a class="remove-icon removeTransformationCommon" data-id="${transformations.id}"></a>
						</td>
					</tr>
				</c:when>
				<c:when test="${transformations.transformationCountryCode eq ctmTransformationOffice}">
					<tr class="ctmtransformation">
						<td class="number" data-val="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
						<td data-val="type"><spring:message code="claim.title.ctm.transformation"/></td>
						<td data-val="country">-</td>
						<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
						<td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${transformations.irDate}"/></td>
						<td data-val="id">${transformations.irNumber}</td>
						<td data-val="files"></td>
						<td data-val="options">
							<a class="edit-icon editCtmTransformationCommon" data-id="${transformations.id}"></a>
							<a class="remove-icon removeCtmTransformationCommon" data-id="${transformations.id}"></a>
						</td>
					</tr>
				</c:when>
			</c:choose>
		</c:forEach>
        <c:forEach var="conversions" items="${flowBean.conversions}" varStatus="rowCounter">
            <tr class="conversion">
                <td class="number" data-val="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
                <td data-val="type"><spring:message code="claim.title.conversion"/></td>
                <td data-val="country">EM</td>
                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
                <td data-val="date"><fmt:formatDate type="date" pattern="${dateFormat}" value="${conversions.conversionApplicationDate}"/></td>
                <td data-val="id">
                <spring:eval var="storeclaims" expression="conversions.conversionApplicationNumber"/>
									<c:choose>
										<c:when test="${storeclaims.length()>10}">
											${storeclaims.substring(0,10)}...
										</c:when>
										<c:otherwise>
											${storeclaims}
										</c:otherwise>
									</c:choose>
                </td>
                <td data-val="files"></td>
                <td data-val="options">
                	<a class="edit-icon editConversionCommon" data-id="${conversions.id}"></a>
					<a class="remove-icon removeConversionCommon" data-id="${conversions.id}"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<input type="hidden" id="prioritiesSizeValue" value="<fmt:formatNumber value="${flowBean.getPriorities().size()}" minIntegerDigits="2" />" />
<input type="hidden" id="senioritiesSizeValue" value="<fmt:formatNumber value="${flowBean.getSeniorities().size()}" minIntegerDigits="2" />" />
<input type="hidden" id="exhibitionsSizeValue" value="<fmt:formatNumber value="${flowBean.getExhpriorities().size()}" minIntegerDigits="2" />" />
<input type="hidden" id="transformationsSizeValue" value="<fmt:formatNumber value="${flowBean.getTransformations().size()}" minIntegerDigits="2" />" />
<input type="hidden" id="conversionsSizeValue" value="<fmt:formatNumber value="${flowBean.getConversions().size()}" minIntegerDigits="2" />" />

<script type="text/javascript">
    disableNoButtons();
    $("#prioritiesSize").html("(" + $("#prioritiesSizeValue").val() + ")");
    $("#senioritiesSize").html("(" + $("#senioritiesSizeValue").val() + ")");
    $("#exhibitionsSize").html("(" + $("#exhibitionsSizeValue").val() + ")");
    $("#transformationsSize").html("(" + $("#transformationsSizeValue").val() + ")");
    $("#conversionsSize").html("(" + $("#conversionsSizeValue").val() + ")");
</script>