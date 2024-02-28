<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div class="trade-mark-details">
    <header>
    	<spring:message code="review.mark.details"/>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_Mark"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
    <div class="trademark">
    <c:set var="tmDetailsSize" value="0"/>
    <c:if test="${not empty flowBean.tmsList}">
       
        <table id="tmsList" class="table" style="padding: 0;">

			<thead>
            	<tr style="width:100%">
                	<th style="width:4%"><spring:message code="tm.details.table.header.number"/></th>
                	<th style="width:16%"><spring:message code="tm.details.table.header.applicationNumber"/></th>
                	<th><spring:message code="tm.details.table.header.representation"/></th>                
                	<c:choose>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-renewal'}">	
				                <th><spring:message code="tm.details.table.header.expirationDate"/></th>
				                <th><spring:message code="tm.details.table.header.tradeMarkKind"/></th>							
					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-opposition'}">	
				                <th><spring:message code="tm.details.table.header.publicationDate"/></th>
				                <th><spring:message code="tm.details.table.header.tradeMarkStatus"/></th>							
					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-objection' || flowScopeDetails.flowModeId == 'tm-appeal'}">
                                <th><spring:message code="tm.details.table.header.tradeMarkStatus"/></th>
                    </c:when>
					<c:otherwise>
				                <th><spring:message code="tm.details.table.header.registrationDate"/></th>
				                <th><spring:message code="tm.details.table.header.tradeMarkKind"/></th>									
					</c:otherwise>
					
					
				</c:choose>

					<th style="width:3%"><spring:message code="tm.details.table.header.GS"/></th>

            	</tr>
            </thead>
            <c:set var="i" value="0"/>

            <c:forEach var="tm" items="${flowBean.tmsList}" varStatus="tmId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="selectedStatus" value="?" />
               	<c:set var="selectedKind" value="?" />
               	<c:forEach var="tmKind" items="${configurationServiceDelegator['tradeMarkKinds']}">
               		<c:if test="${tmKind.code == tm.tradeMarkKind}">
               			<c:set var="selectedKind"><spring:message code="${tmKind.value}" /></c:set>
               		</c:if>
               	</c:forEach>
               	<c:forEach var="tmStatus" items="${configurationServiceDelegator['tradeMarkStatusList']}">
               		<c:if test="${tmStatus.code == tm.tradeMarkStatus}">
               			<c:set var="selectedStatus"><spring:message code="${tmStatus.value}" /></c:set>
               		</c:if>
               	</c:forEach>
                <c:choose>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-renewal'}">	
						<c:set var="date">
							<c:if test="${empty tm.expiryDate}">?</c:if>
							<c:if test="${not empty tm.expiryDate}"><fmt:formatDate type="date" pattern="${dateFormat}" value="${tm.expiryDate}"/></c:if>							
						</c:set>
						<c:set var="kindOrStatus">
							<c:if test="${empty tm.tradeMarkKind}">?</c:if>
							<c:if test="${not empty tm.tradeMarkKind}">${selectedKind}</c:if>
						</c:set>				
					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-opposition'}">						
						<c:set var="date">
							<c:if test="${empty tm.publicationDate}">?</c:if>
							<c:if test="${not empty tm.publicationDate}"><fmt:formatDate type="date" pattern="${dateFormat}" value="${tm.publicationDate}"/></c:if>							
						</c:set>						
						<c:set var="kindOrStatus">
							<c:if test="${empty tm.tradeMarkStatus}">?</c:if>
							<c:if test="${not empty tm.tradeMarkStatus}">${selectedStatus}</c:if>
						</c:set>										
					</c:when>
					<c:when test="${flowScopeDetails.flowModeId == 'tm-objection' || flowScopeDetails.flowModeId == 'tm-appeal'}">
                        <c:set var="kindOrStatus">
                            <c:if test="${empty tm.tradeMarkStatus}">?</c:if>
                            <c:if test="${not empty tm.tradeMarkStatus}">${selectedStatus}</c:if>
                        </c:set>
                    </c:when>
					<c:otherwise>
						<c:set var="date">	
							<c:if test="${empty tm.registrationDate}">?</c:if>
							<c:if test="${not empty tm.registrationDate}"><fmt:formatDate type="date" pattern="${dateFormat}" value="${tm.registrationDate}"/></c:if>														
						</c:set>					
						<c:set var="kindOrStatus">						
							<c:if test="${empty tm.tradeMarkKind}">?</c:if>
							<c:if test="${not empty tm.tradeMarkKind}">${selectedKind}</c:if>
						</c:set>									
					</c:otherwise>
				</c:choose>
                
                <tr id="tm_id_${tm.id}" style="background:#ECF7FC">
                    <jsp:include page="/WEB-INF/views/review/review_mark_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${tm.id}" name="tmId"/>
                        <jsp:param value="${tm.applicationNumber}" name="tmApplication"/>
                        <jsp:param value="${date}" name="tmDate"/>
                        <jsp:param value="${kindOrStatus}" name="tmKind"/>
                        <jsp:param value="${tm.applicationRepresentation}" name="applicant"/>   
                        <jsp:param value="${tm.imageRepresentationURI}" name="tmImgURI"/>
                        <jsp:param value="${flowScopeDetails.flowModeId}" name="flowModeId"/>                                            
                    </jsp:include>   
                 </tr> 
                 <tr id="tableremain${i}" style="display:none; background:white" valign="right">
					<td></td>
                    <td  colspan="5">
		                	<jsp:include page="/WEB-INF/views/review/review_goodsservices.jsp">
		                		<jsp:param value="${i-1}" name="indexTM"/>
		                		<jsp:param value="remain" name="gsType"/>
		                	</jsp:include>
                	</td>
                 </tr>
                 <tr id="tableremoved${i}" style="display:none; background:#F9F9F9" valign="right">
                	<td></td>
                    <td  colspan="5">
	                     <c:if test="${!(empty tm.originalGS)}">
		                	<jsp:include page="/WEB-INF/views/review/review_goodsservices.jsp">
		                		<jsp:param value="${i-1}" name="indexTM"/>
		                		<jsp:param value="removed" name="gsType"/>
		                	</jsp:include>
                		</c:if>
                	</td>
                 </tr>
            </c:forEach>
            <c:set var="tmListSize" value="${i}"/>
        </table>
    </c:if>
    </div>
</div>