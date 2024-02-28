<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="claims">
	<header>
		<spring:message code="review.claim.details"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Claim"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div>
		<c:if test="${(not empty flowBean.priorities) 
			or (not empty flowBean.seniorities) 
			or (not empty flowBean.exhpriorities)
			or (not empty flowBean.transformations)}">
		
		    <table class="table">
		        <tr>
		            <th><spring:message code="claim.table.header.number"/><a class="sorter asc"></a></th>
		            <th><spring:message code="claim.table.header.type"/><a class="sorter asc"></a></th>
		            <th><spring:message code="claim.table.header.country"/><a class="sorter"></a></th>
		            <th><spring:message code="claim.table.header.date"/><a class="sorter"></a></th>
		            <th><spring:message code="claim.table.header.id"/><a class="sorter"></a></th>            
		            <th><spring:message code="claim.table.header.document"/><a class="sorter desc"></a></th>
		        </tr>
		
		        <c:forEach var="priorities" items="${flowBean.priorities}" varStatus="rowCounter">
		            <tr class="priority">
		                <td class="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
		                <td><spring:message code="claim.title.priority"/></td>
		                <td>${priorities.country}</td>
		                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
		                <td><fmt:formatDate type="date" pattern="${dateFormat}" value="${priorities.date}"/></td>
		                <td>
							<spring:eval var="storeprioritiesnumber" expression="priorities.number" /> 
			                	<c:choose>
									<c:when test="${storeprioritiesnumber.length()>10}">
												${storeprioritiesnumber.substring(0,10)}...
											</c:when>
									<c:otherwise>
												${storeprioritiesnumber}
									</c:otherwise>
								</c:choose>
						</td>
		                <td>                	
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
		            </tr>
		        </c:forEach>
		        <c:forEach var="seniorities" items="${flowBean.seniorities}" varStatus="rowCounter">
		            <tr class="seniority">
		                <td class="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
		                <td><spring:message code="claim.title.seniority"/></td>
		                <td>${seniorities.country}</td>
		                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
		                <td><fmt:formatDate type="date" pattern="${dateFormat}" value="${seniorities.filingDate}"/></td>
		                <td>
							<td class="name"><spring:eval var="storeprioritiesnumber"
								expression="seniorities.filingNumber" /> 
								<c:choose>
									<c:when test="${storeprioritiesnumber.length()>10}">
											${storeprioritiesnumber.substring(0,10)}...
									</c:when>
									<c:otherwise>
											${storeprioritiesnumber}
									</c:otherwise>
								</c:choose>
						</td>
						</td>
		                <td>
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
		            </tr>
		        </c:forEach>
		        <c:forEach var="exhpriorities" items="${flowBean.exhpriorities}" varStatus="rowCounter">
		            <tr class="exhpriority">
		                <td class="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
		                <td><spring:message code="claim.title.exhibition"/></td>
		                <td>-</td>
		                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
		                <td><fmt:formatDate type="date" pattern="${dateFormat}" value="${exhpriorities.firstDate}"/></td>
		                <td>-</td>
		                <td>
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
		            </tr>
		        </c:forEach>
		        <c:forEach var="transformations" items="${flowBean.transformations}" varStatus="rowCounter">
		            <tr class="transformation">
		                <td class="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
		                <td><spring:message code="claim.title.transformation"/></td>
		                <td>-</td>
		                <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
		                <td><fmt:formatDate type="date" pattern="${dateFormat}" value="${transformations.irDate}"/></td>
		               <td>
		                <spring:eval var="storeprioritiesnumber" expression="transformations.irNumber"/>
									<c:choose>
										<c:when test="${storeprioritiesnumber.length()>10}">
											${storeprioritiesnumber.substring(0,10)}...
										</c:when>
										<c:otherwise>
											${storeprioritiesnumber}
										</c:otherwise>
									</c:choose>
		                </td>
		                <td></td>
		            </tr>
		        </c:forEach>
		    </table>
		</c:if>		
	</div>
</div>