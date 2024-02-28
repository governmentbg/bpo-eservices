<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div class="trade-mark-details">
    <header>
    	<spring:message code="ds.details.title"/>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_Design"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
    <div class="trademark">
    <c:if test="${not empty flowBean.dssList}">
       
        <table id="dssList" class="table" style="padding: 0;">

			<thead>
			<c:if test="${flowModeId == 'ds-surrender' }">
				<tr>
					<th colspan="6">
						<div class="alert alert-error">
							<spring:message code="ds-surrender.surrender.declaration"/>
						</div>
					</th>
				</tr>
			</c:if>

			<tr style="width:100%">
                	<th style="width:4%"><spring:message code="ds.details.table.header.number"/></th>
                	<th style="width:16%"><spring:message code="ds.details.table.header.designNumber"/></th>
                	<th><spring:message code="ds.details.table.header.designRepresentation"/></th>
                	<th><spring:message code="ds.details.table.header.productIndication"/></th>
                	<th><spring:message code="ds.details.table.header.ownerName"/></th>
					<th><spring:message code="ds.details.table.header.selected" /></th>
            	</tr>
            </thead>

            <c:forEach var="ds" items="${flowBean.dssList}" varStatus="status">
             	<c:set var="dsHolders" value=""/>
                <c:choose>    	
					<c:when test="${empty ds.eSDesignApplicationData.holders}">
						<c:set var="dsHolders" value=""/>
					</c:when>
					<c:otherwise>
						<c:set var="dsHolders" value="${ds.eSDesignApplicationData.holders.get(0).name}"/>
					</c:otherwise>
				</c:choose>
				
				<c:set var="importedDesign" value="${ds.imported}"/>
				<c:set var="storedFile" value="${ds.representationAttachment.storedFiles.size() > 0 ? ds.representationAttachment.storedFiles[0] : null}" />
				<c:set var="thereIsThumbnail" value="true" />
				<c:set var="documentId" value=""/>
				<c:set var="originalFilename" value =""/>
				<c:if test="${storedFile ne null}">
				<c:choose>
					<c:when test="${thereIsThumbnail and storedFile.thumbnail ne null}">
						<c:set var="documentId" value="${storedFile.thumbnail.documentId}"/>
					</c:when>
					<c:otherwise>
						<c:set var="documentId" value="${storedFile.documentId}"/>
					</c:otherwise>
				</c:choose>
				<c:set var="originalFilename" value ="${storedFile.originalFilename}"/>
				</c:if>
				
				<c:set var="dsLocarno" value=""/>
				<c:if test="${not empty ds.locarno && ds.locarno.size() > 0}">
					<c:forEach var="locarno" items="${ds.locarno}">
						<c:set var="dsLocarno" value="${dsLocarno}${locarno.locarnoIndication};"/>
					</c:forEach>
				</c:if>
            
            
                <tr id="ds_id_${ds.id}" style="background:#ECF7FC">
                	<td data-val='Number'>${status.count}</td>
                	<td data-val='Application'>
                	<spring:eval var="storedesignIdentifier" expression="ds.designIdentifier"/>
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
							<c:if test="${documentId ne ''}">
								<c:set var="uriImage" value=""/>
								<c:choose>
									<c:when test="${not empty documentId}">
										<c:set var="uriImage" value="getDocument.htm?documentId=${documentId}"/>
									</c:when>
									<c:otherwise>
										<c:set var="uriImage" value="${originalFilename}"/>
									</c:otherwise>
								</c:choose>
								<img class="thumb" src="${uriImage}" id="viewImage${documentId}" data-file-documentId="${documentId}" data-file-name="${originalFilename}">
							</c:if>
						</div>
					</td>
                    <td data-val='ProductIndication' class="name">
						<spring:eval var="storeproductIndication" expression="dsLocarno"/>
						<c:choose>
							<c:when test="${storeproductIndication.length()>10}">
								<c:out value="${storeproductIndication.substring(0,10)}..." />
							</c:when>
							<c:otherwise>
								<c:out value="${storeproductIndication}" />
							</c:otherwise>
						</c:choose>
                    </td>
                    <td data-val='Holders'>${dsHolders}</td>
					<td><input type="checkbox" ${ds.selected == true? 'checked="checked"': '' }" disabled="disabled"/>
                 </tr>
            </c:forEach>
        </table>
    </c:if>
    </div>
</div>