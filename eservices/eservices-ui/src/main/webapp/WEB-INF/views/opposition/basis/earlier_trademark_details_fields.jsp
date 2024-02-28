<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

		<c:set var="additionalLabelIfGI" value=""></c:set>
		<c:if test="${sectionId == 'sec_earlierGI_earlier_trademark_details'}">
			<c:set var="additionalLabelIfGI" value=".earlierGI"></c:set>
		</c:if>

		<component:generic path="formWarnings" checkRender="true">
	    	<c:set var="errors">
	        	<form:errors path="formWarnings"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:set var="first" value="true"/>
		    <c:forEach items="${errors}" var="message">
		    	<c:if test="${first eq 'true'}">
		       		<div id="customWarningMessageTM" class="hidden">${message}</div>
		       		<c:set var="first" value="false"/>
		       	</c:if>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>

		<component:generic path="formMessages" checkRender="true">
			<c:set var="errors">
				<form:errors path="formMessages"></form:errors>
			</c:set>
			<c:if test="${!empty errors}">
				<c:forEach items="${errors}" var="message">
					<p class="flMessageError permanentMessage" id="${path}ErrorMessageServer">${message}</p>
				</c:forEach>
			</c:if>
		</component:generic>
	    
	 
		<c:set var ="defaultValueFilter" value=""/>
		<c:if test="${empty formEdit && empty formErrors}">
			<c:set var="defaultValueFilter" value="${configurationServiceDelegator.getOffice()}"/>
		</c:if>

		<div style="display:none">
			<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imported" id="importedId" checkRender="true"/>
		</div>
		<form:hidden id="appLang" value="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationLanguage}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationLanguage"/>

		<c:if test="${!imported && param.earlierEntitlementRight eq 'nonRegistered'}">
			<div class="alert alert-info">
				<spring:message code="nonRegistered.application.filed.alert"/>
			</div>
		</c:if>


		<c:set var="additionalLabel" value="${param.earlierEntitlementRight eq 'nonRegistered' ? '.nonRegistered':'' }"/>
		<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationNumber" checkRender="true" labelTextCode="earlierRight.details.field.applicationNumber${additionalLabel}"/>
		<c:if test="${param.earlierEntitlementRight eq 'earlierTradeMark'}">
			<div class="tip">
				<spring:message code="earlierRight.details.field.applicationNumber.earlierTradeMark.tip"/>
			</div>
		</c:if>

	    <c:if test="${imported}">
	    	<label class="mark-name">
				<c:if test="${not empty oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationRepresentationAlternative}">
					<c:out value="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationRepresentationAlternative.originalValue}"/></label>
				</c:if>
	    	<form:hidden id="applicationRepresentationId" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationRepresentationAlternative"/>
	    	<form:hidden id="imageRepresentationURIFormId" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imageRepresentationURI"/>
	    </c:if>	    	    
	    
	    <c:if test="${!imported}">
	    	<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationRepresentation" checkRender="true" labelTextCode="earlierRight.details.field.earlierRightRepresentation.representation${additionalLabelIfGI}" formTagClass="onerow-fields inputBig"/>
	    	<input type="hidden" name="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.imageRepresentationURI"/>
	    </c:if>
	    
	    <div id="representationAttachment" class="fileuploadInfo collectiveSelectors">            
        	<component:file pathFilename="fileWrapperImage" fileWrapperPath="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.representationAttachment"
                        fileWrapper="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.representationAttachment}"
			labelCode="earlierRight.details.field.representationAttachment" inputFileLabelCode="earlierRight.details.button.attach.image" showPreLabelInputFileButton="false"/>
		</div>		
		
	    <component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.applicationDate" checkRender="true" labelTextCode="earlierRight.details.field.applicationDate" formTagClass="field-date"/>
	    
		<component:select items="${configurationServiceDelegator['tradeMarkKinds']}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.tradeMarkKind" itemLabel="value"
                 itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
                 labelTextCode="tm.details.field.tmKind" formTagClass="onerow-fields"/>

		<c:if test="${sectionId == 'sec_earlierGI_earlier_trademark_details'}">
			<component:select items="${configurationServiceDelegator['giTypes']}" itemLabel="value"
							  path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.tradeMarkType"
							  itemValue="code" checkRender="true" itemFilter="enabled"
							  labelTextCode="tm.details.field.giType" formTagClass="onerow-fields"/>
		</c:if>

		<c:if test="${sectionId != 'sec_earlierGI_earlier_trademark_details'}">
			<component:select items="${configurationServiceDelegator['tradeMarkTypes']}" itemLabel="value"
							  path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.tradeMarkType"
							  itemValue="code" checkRender="true" itemFilter="enabled"
							  labelTextCode="tm.details.field.tmType" formTagClass="onerow-fields"/>
		</c:if>



		<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.tradeMarkName" checkRender="true" labelTextCode="earlierRight.details.field.tradeMarkName"/>

 		<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.registrationNumber" checkRender="true" labelTextCode="earlierRight.details.field.registrationNumber"/> 
		<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.registrationDate" checkRender="true" labelTextCode="earlierRight.details.field.registrationDate" formTagClass="field-date"/> 
		
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.filterCountriesNonRegisteredTM" checkRender="true">
			<component:select items="${configurationServiceDelegator['countries']}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.country" id="addressCountryReg" itemLabel="value"
                 itemValue="code" checkRender="true"                 
                 labelTextCode="earlierRight.details.field.country" defaultValue="${defaultValueFilter}" sortValues="true" />
		</component:generic>
		
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.filterCountriesAll" checkRender="true">
			<component:select items="${configurationServiceDelegator['countries']}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.country" id="addressCountryReg" itemLabel="value"
                 itemValue="code" checkRender="true"                 
                 labelTextCode="earlierRight.details.field.country" defaultValue="${defaultValueFilter}" sortValues="true"/>	
		</component:generic>
		 
       	<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.filterCountriesParisConvention" checkRender="true">
			<component:select items="${configurationServiceDelegator['countries']}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.country" id="addressCountryReg" itemLabel="value"
                 itemValue="code" checkRender="true"                 
                 labelTextCode="earlierRight.details.field.country" defaultValue="${defaultValueFilter}" sortValues="true" itemFilter="isParisConvention" />	
		</component:generic>
		
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.filterCountriesEarlierWellKnow" checkRender="true">
			<component:select items="${configurationServiceDelegator['countries']}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.country" id="addressCountryReg" itemLabel="value"
                 itemValue="code" checkRender="true"                 
                 labelTextCode="earlierRight.details.field.country" sortValues="true" itemFilter="isEarlierWellKnow" />	
		</component:generic>
		
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.filterCountriesEarlierTradeMark" checkRender="true">
			<component:select items="${configurationServiceDelegator['countries']}" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.country" id="addressCountryReg" itemLabel="value"
							  itemValue="code" checkRender="true" itemFilter="enabled"
							  labelTextCode="earlierRight.details.field.country${additionalLabelIfGI}" defaultValue="${defaultValueFilter}" sortValues="true"/>
		</component:generic>
       	
       	
       	
       	
       	<!-- Begin fields out of TradeMark -->
       	<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.priorityDate" checkRender="true" labelTextCode="earlierRight.details.field.priorityDate" formTagClass="field-date"/>
		
		<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.areaActivity" checkRender="true" labelTextCode="earlierRight.details.field.areaActivity" />
		
		
		<component:filterTextSelect id="earlierEntitlementRightTypes-select" 
	 				 items="${configurationServiceDelegator['typeRightEarlierTradeMarks']}" 
	 				 path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.typeRight"
                     labelTextCode="earlierRight.details.field.typeRight"
                     itemLabel="value" 
                     itemValue="code" 
                     checkRender="true" 
                     fieldFilter="earlierEntitlementRightType"
                     fieldFilterText="${param.earlierEntitlementRight}"
                     anotherFieldRender="showTypeRightDetails"
                     anotherFieldComponent="input"
                     anotherFieldFilter="true"
                     anotherFieldId="inputHiddenTypeRight"
                     importInAnotherField="true"
                     /> 
		
		<c:set var="hasRightType" value="display:block"/>
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.typeRight" checkRender="true">
			<c:set var="hasRightType" value="display:none"/>	
		</component:generic>

		<div id="divTypeRightDescription" style="${hasRightType}"> 
			<component:textarea path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.typeRightDetails"
							formTagClass="textareaBig"	checkRender="true" labelTextCode="earlierRight.details.field.typeRightDetails"/>
		</div>
		<!-- End fields out of TradeMark -->
       	<input type="hidden" id="inputImported" value="${imported}"/>
		<form:hidden path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.applicantHolder.id" />
		<form:hidden path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.applicantHolder.imported" />
       	
       	<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.applicantHolder.name" checkRender="true" labelTextCode="earlierRight.details.field.applicantHolderName"/>
	    <jsp:include page="/WEB-INF/views/partials/address.jsp">
	        <jsp:param name="path" value="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.applicantHolder.address"/>
	        <jsp:param name="itemFilterValue" value="isApplicant"/>
    	</jsp:include>

		<c:if test="${imported}">
			<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.extent" checkRender="true">
				<div id="extent">
					<label><spring:message code="${flowModeId}.${param.earlierEntitlementRight}.earlierRight.details.field.extent"/></label>
					<p>
						<form:radiobutton path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.extent"  class="allGS" value="true"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.earlierRight.details.field.completeExtent" /></span>
					</p>
					<p>
						<form:radiobutton path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.extent"  class="someGS" value="false"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.earlierRight.details.field.partialExtent"/></span>
					</p>

				</div>
			</component:generic>
		</c:if>
		<div id="earlierTMGSCommentDiv">
			<component:textarea path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.goodsServicesComment" formTagClass="textarea-fields" checkRender="true"
								labelTextCode="${flowModeId}.earlierRight.details.field.gs.comment" tipCode="gs.comments.tip"/>
		</div>