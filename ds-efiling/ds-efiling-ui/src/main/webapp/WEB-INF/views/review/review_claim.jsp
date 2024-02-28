<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div class="your-details">
	<header>
		<spring:message code="review.claim.details"/>
		<a class="edit navigateBtn" data-val="Update_Claim"><spring:message code="review.update"/></a>
	</header>
	
	<div>

		<c:if test="${not empty flowBean.divisionalApplications}">
			<table class="table">
				<caption><spring:message code="review.divisionalApplication.info.title" /></caption>
				<thead>
					<tr>
						<th><spring:message code="divisionalApplication.table.header.number"/></th>
						<th><spring:message code="divisionalApplication.table.header.date"/></th>
						<th><spring:message code="divisionalApplication.table.header.document"/></th>
						<th><spring:message code="divisionalApplication.table.header.designNumbers"/></th>
					</tr>	
				</thead>
				<tbody>
					<tr>
						<td><c:out value="${flowBean.divisionalApplication.numberDivisionalApplication}" /></td>
						<td><fmt:formatDate type="date" pattern="${dateFormat}" value="${flowBean.divisionalApplication.dateDivisionalApplication}"/></td>
						<td>
							<c:choose>
			                	<c:when test="${not empty flowBean.divisionalApplication.fileDivisionalApplication.storedFiles}">
				                	<spring:message code="divisionalApplication.table.documents.yes"/>
					            	<c:forEach items="${flowBean.divisionalApplication.fileDivisionalApplication.storedFiles}" var="file">
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
						<td>
							<c:out value="${functions:getLinkedDesignsNumbers(flowBean.divisionalApplication)}" />
						</td>
					</tr>
				</tbody>
			</table>
    	</c:if> 
		
		<c:if test="${(not empty flowBean.priorities) or  (not empty flowBean.exhpriorities)}">

		    <table class="table">
		    	<caption><spring:message code="review.claim.info.title"/></caption>
		    	<thead>
		        	<tr>
		            	<th><spring:message code="claim.table.header.number"/></th>
		            	<th><spring:message code="claim.table.header.type"/></th>
		            	<th><spring:message code="claim.table.header.country"/></th>
		            	<th><spring:message code="claim.table.header.date"/></th>
		            	<th><spring:message code="claim.table.header.id"/></th>   
		            	<th><spring:message code="claim.table.header.designNumbers"/></th>
		            	<th><spring:message code="claim.table.header.document"/></th>
		        	</tr>
		    	<thead>
				<tbody>
		        	<c:forEach var="priorities" items="${flowBean.priorities}" varStatus="rowCounter">
		            	<tr class="priority">
		                	<td class="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
		                	<td><spring:message code="claim.title.priority"/></td>
		                	<td>${priorities.country}</td>
		                	<td><fmt:formatDate type="date" pattern="${dateFormat}" value="${priorities.date}"/></td>
		                	<td>${priorities.number}</td>
		                	<td><c:out value="${functions:getLinkedDesignsNumbers(priorities)}" /></td>
		                	<td>                	
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
		            	</tr>
		        	</c:forEach>

			        <c:forEach var="exhpriorities" items="${flowBean.exhpriorities}" varStatus="rowCounter">
			            <tr class="exhpriority">
			                <td class="number"><fmt:formatNumber value="${rowCounter.count}" minIntegerDigits="2" /></td>
			                <td><spring:message code="claim.title.exhibition"/></td>
			                <td>-</td>
			                <td><fmt:formatDate type="date" pattern="${dateFormat}" value="${exhpriorities.firstDate}"/></td>
			                <td>-</td>
			                <td>-</td>
			                <td>
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
			            </tr>
			        </c:forEach>
				<tbody>
		    </table>
		</c:if>		
	</div>	
</div>