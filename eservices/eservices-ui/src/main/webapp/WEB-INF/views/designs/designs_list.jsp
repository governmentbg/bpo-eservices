<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<c:if test="${maximumEntitiesReached}">
	<span id="maximumEntitiesReached" class="hidden"></span>
</c:if>

<c:if test="${not empty flowBean.designs}">
	<table class="innerLocarno">
	    <caption><spring:message code="designs.table.title"/></caption>
	    <tr>
	        <th class="span1"><spring:message code="designs.table.header.number"/></th>
	        <th><spring:message code="designs.table.header.views"/></th>
	        <th><spring:message code="designs.table.header.locarnoClassification" /></th>
	        <th><spring:message code="designs.table.header.productIndication" /></th>
	        <th><spring:message code="designs.table.header.deferred" /></th> 
	        <th class="span2"><spring:message code="designs.table.header.options"/></th>
	    </tr>
	    
	    <c:forEach var="design" items="${flowBean.designs}" varStatus="designsStatus">
	    	
	    	<c:set var="htmlNumber" value="${designsStatus.count}" />
			<c:set var="htmlViews" value="${design.views.size()}" />	    	
		    <c:set var="htmlDeferred">
		    	<c:choose>
       				<c:when test="${design.requestDeferredPublication}">
       					<spring:message code="designs.table.deferred.yes" />
       				</c:when>
       				<c:otherwise>
       					<spring:message code="designs.table.deferred.no" />
       				</c:otherwise>
       			</c:choose>
		    </c:set>
	    	<c:set var="htmlOptions">
	    		<a class="view-icon" id="viewDesignBtn" data-val="${design.id}" data-rownum="${designsStatus.count}" title="<spring:message code="designs.table.options.view"/>"></a>
		        <a class="duplicate-icon" id="duplicateDesignBtn" data-val="${design.id}" title="<spring:message code="designs.table.options.duplicate"/>"></a>
		        <a class="edit-icon" id="editDesignBtn" data-val="${design.id}" data-rownum="${designsStatus.count}" title="<spring:message code="designs.table.options.edit"/>"></a>
		        <a class="remove-icon" id="removeDesignBtn" data-val="${design.id}" title="<spring:message code="designs.table.options.delete"/>"></a>
		    </c:set>
		    
		    <c:set var="cssClass" value="${designsStatus.count % 2 eq 0 ? 'odd' : 'even'}" />
	    	<c:choose>
		    	<c:when test="${empty design.locarno or design.locarno.size() == 1}">
		    		<tr class="designCountRow ${cssClass} designRow${design.id}">
		        		<td>
		        			<c:out value="${htmlNumber}" escapeXml="false" />
		        		</td>
		        		<td class="design-views-thumbnails">
		        			<c:out value="${htmlViews}" escapeXml="false" />
		        		</td>
		        		<td>
		        			<c:forEach var="locarno" items="${design.locarno}">
								<c:out value="${locarno.locarnoClassFormatted}" />
							</c:forEach>
		        		</td>
                        <td>
                            <c:forEach var="locarno" items="${design.locarno}">
                                <c:out value="${locarno.locarnoIndication}" />
                            </c:forEach>
                        </td>
		        		<td>
		        			<c:out value="${htmlDeferred}" escapeXml="false" />
		        		</td>
		        		<td>
		        			<c:out value="${htmlOptions}" escapeXml="false" />
		        		</td>
		        	</tr>
		    	</c:when>
		    	<c:otherwise>
		    		<c:set var="differentLocarnoClassifications" value="${functions:getDifferentLocarnoClassifications(design)}" />
		    		<c:set var="rowspan" value="${differentLocarnoClassifications.size()}" />
		    		<tr class="designCountRow ${cssClass} designRow${design.id}">
		    			<td rowspan="${rowspan}">
		        			<c:out value="${htmlNumber}" escapeXml="false" />
		        		</td>
		        		<td rowspan="${rowspan}" class="design-views-thumbnails">
		        			<c:out value="${htmlViews}" escapeXml="false" />
		        		</td>
		        		
		        		<c:forEach var="differentLocarnoClassification" items="${differentLocarnoClassifications}" varStatus="status">
		        			<c:if test="${status.count eq 1}">
		        				<td>
		        					<c:out value="${differentLocarnoClassification.key}" />
		        				</td>
                                <td>
                                    <c:out value="${differentLocarnoClassification.value}" />
                                </td>
		        			</c:if>
						</c:forEach>
		        		<td rowspan="${rowspan}">
		        			<c:out value="${htmlDeferred}" escapeXml="false" />
		        		</td>
						<td rowspan="${rowspan}">
		        			<c:out value="${htmlOptions}" escapeXml="false" />
		        		</td>
		        	</tr>
		        	<c:forEach var="differentLocarnoClassification" items="${differentLocarnoClassifications}" varStatus="status">
		        		<c:if test="${status.count gt 1}">
		        			<tr class="${cssClass} designRow${design.id}">
		        				<td>
		        					<c:out value="${differentLocarnoClassification.key}" />
		        				</td>
                                <td>
                                    <c:out value="${differentLocarnoClassification.value}" />
                                </td>
		        			</tr>
		        		</c:if>
		        	</c:forEach>
		    	</c:otherwise>
	    	</c:choose>
	    </c:forEach>
	</table>

</c:if>
