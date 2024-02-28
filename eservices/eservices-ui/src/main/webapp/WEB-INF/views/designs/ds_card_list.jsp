<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />

<div id="dsCardList">
	<c:if test="${flowModeId == 'ds-surrender' }">
		<div class="alert alert-danger">
			<spring:message code="ds-surrender.surrender.declaration"/>
		</div>
	</c:if>

	<c:if test="${flowModeId == 'ds-invalidity' }">
		<div class="alert alert-danger">
			<spring:message code="ds-invalidity.composition.set.fee.warning"/>
		</div>
	</c:if>

	<c:if test="${flowModeId == 'ds-appeal' }">
		<div class="alert alert-danger">
			<spring:message code="ds-appeal.composition.set.fee.warning"/>
		</div>
	</c:if>

	<c:if test="${flowModeId == 'ds-withdrawal' }">
		<div class="alert alert-danger">
			<spring:message code="ds-withdrawal.composition.set.warning"/>
		</div>
	</c:if>
	
    <c:set var="dsDetailsSize" value="0"/>
    <c:if test="${not empty flowBean.dssList}">
    	<c:set var="warningMessages"><form:errors path="eSDesignDetailsForm.formMessages"/></c:set>
    	<c:if test="${not empty warningMessages}">
    		<c:set var="concatenated" value=""/>
	    	<c:forEach items="${warningMessages}" var="message">
	    		<c:set var="concatenated" value="${concatenated}${message}"/>
			</c:forEach>
			<div id="divErrorsWarnings" style="display:none" class="alert alert-danger">
				${concatenated}
				<br/>
			</div>
			<script type="text/javascript">
                $("#divErrorsWarnings").show();
			</script>
    	</c:if>

		<button id="dsRemoveAll" class="btn fileinput-button" data-toggle="button" type="button" style="margin: 10px 0px">
			<i class="minus-icon"></i>
			<spring:message code="ds.section.button.removeAll"/>
		</button>

        <table id="dssList" class="table">
            <tr>
                <th><spring:message code="ds.details.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="ds.details.table.header.designNumber"/><a class="sorter" data-val='id'/></th>
                <th><spring:message code="ds.details.table.header.designRepresentation"/><a class="sorter" data-val='designRepresentation'/></th>
                <th><spring:message code="ds.details.table.header.productIndication"/><a class="sorter" data-val='productIndication'/></th>                
                <th><spring:message code="ds.details.table.header.ownerName"/><a class="sorter" data-val='ownerName'/></th>
                <th><spring:message code="ds.details.table.header.status"/><a class="sorter" data-val='status'/></th>
                <th><spring:message code="ds.details.table.header.options"/></th>
            </tr>
            
            <c:set var="i" value="0"/>
            <c:forEach var="ds" items="${flowBean.dssList}" varStatus="dsId">
            	<c:set var="importedDesign" value="${ds.imported}"/>
                <c:set var="i" value="${i+1}"/>
                <c:set var="htmlViews" value="${ds.views.size()}" />
				                                   
                <c:set var="dsHolders" value=""/>
                <c:choose>    	
					<c:when test="${empty ds.eSDesignApplicationData.holders}">
						<c:set var="dsHolders" value=""/>
					</c:when>
					<c:otherwise>
						<c:set var="dsHolders" value="${ds.eSDesignApplicationData.holders.get(0).name}"/>
					</c:otherwise>
				</c:choose>
				
				<c:set var="storedFile" value="${ds.representationAttachment.storedFiles.size() > 0 ? ds.representationAttachment.storedFiles[0] : null}" />
				<component:generic checkRender="true" path="view.thumbnail">
					<c:set var="thereIsThumbnail" value="true" />
				</component:generic>
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

                <tr id="ds_id_${ds.id}">
                    <jsp:include page="/WEB-INF/views/designs/ds_card_row.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${ds.id}" name="dsId"/>
                        <jsp:param value="${ds.designIdentifier}" name="dsNumber"/>
                        <jsp:param value="${htmlViews}" name="dsHtmlViews"/>
                        <jsp:param value="${documentId}" name="documentId"/>
                        <jsp:param value="${dsLocarno}" name="dsLocarno"/>
                        <jsp:param value="${dsHolders}" name="dsHolders"/>
                        <jsp:param value="${originalFilename}" name="originalFilename"/>
                        <jsp:param value="${importedDesign}" name="importedDesign"/>
                        <jsp:param value="${ds.selected}" name="selected"/>
                        <jsp:param value="${ds.dsStatus}" name="status"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="dsListSize" value="${i}"/>
        </table>
    </c:if>
    <c:if test="${empty flowBean.dssList }">
		<c:if test="${flowModeId != 'ds-withdrawal' && flowModeId != 'ds-appeal' }">
			<button id="dsTrigger" class="add-button" data-toggle="button" type="button">
				<i class="add-icon"></i>
				<spring:message code="ds.section.button.design"/>
			</button>
		</c:if>

		<c:set var="sectionId" value="ds_section" scope="request"/>
		<jsp:include page="/WEB-INF/views/unpublished/unpublished_app_select.jsp">
			<jsp:param value="${sectionId}" name="sectionId"/>
			<jsp:param value="DS" name="objectType"/>
			<jsp:param value="DS" name="mainObject"/>
		</jsp:include>

	</c:if>
</div>