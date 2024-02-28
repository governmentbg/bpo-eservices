<%@ page import="eu.ohim.sp.common.ui.form.application.AppealKindForm" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:form class="mainForm formEf" modelAttribute="appeal">
	<c:set var="sectionId" value="appeal" scope="request"/>
	<input type="hidden" id="formReturned" value="true"/>
	
	<form:hidden id="hiddenFormId" path="id"/>
	
	<ul class="controls">
	    <li>
	        <a class="cancelButton appeal">
	        	<spring:message code="appeal.form.action.cancelAdd.top"/>
	        </a>
	    </li>
	    <li>		        	
	        <button class="addAppeal appeal" type="button">			            	
	            <spring:message code="appeal.form.action.save"/>
	        </button>
	    </li>
	</ul>
	<br/>
	
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

	<component:generic path="gsHelper.formMessages" checkRender="true">
		<c:set var="errors">
			<form:errors path="gsHelper.formMessages"></form:errors>
		</c:set>
		<c:if test="${!empty errors}">
			<c:forEach items="${errors}" var="message">
				<p class="flMessageError permanentMessage"
				   id="${path}ErrorMessageServer">${message}</p>
			</c:forEach>
		</c:if>
	</component:generic>
	
	<component:generic checkRender="true" path="appealKind">
		<div id="appeal_kind_choice">

			<c:set var="refusal" value="<%=AppealKindForm.APPEAL_AGAINST_REFUSAL%>"/>
			<c:set var="termination" value="<%=AppealKindForm.APPEAL_AGAINST_TERMINATION%>"/>
			<c:set var="opposition" value="<%=AppealKindForm.APPEAL_AGAINST_OPPOSITION_DECISION%>"/>

			<c:choose>
				<c:when test="${fn:startsWith(flowModeId, 'tm-')}">
					<p>
						<form:radiobutton path="appealKind" value="${refusal}"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.${refusal.labelCode}" /></span>
					</p>
					<p>
						<form:radiobutton path="appealKind" value="${termination}"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.${termination.labelCode}" /></span>
					</p>
					<p>
						<form:radiobutton path="appealKind" value="${opposition}"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.${opposition.labelCode}" /></span>
					</p>

				</c:when>
				<c:otherwise>
					<p>
						<form:radiobutton path="appealKind" value="${refusal}"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.${refusal.labelCode}" /></span>
					</p>
					<p>
						<form:radiobutton path="appealKind" value="${termination}"/><span style="margin-left:10px;"><spring:message code="${flowModeId}.${termination.labelCode}" /></span>
					</p>
				</c:otherwise>
			</c:choose>
		</div>
		<form:errors path="appealKind" cssClass="flMessageError"></form:errors>
	</component:generic>
	
	<div id="appealOppositionDiv" style="display:none">
		<component:input id="appealDecisionNumber" path="decisionNumber" checkRender="true" labelTextCode="appeal.form.decisionNumber"
                             formTagClass="name-text"/>
        
        <div class="tip">
			<spring:message code="appeal.decisionNumber.format.warning"/>
		</div>
		       
        <component:input  formTagClass="field-date" path="oppositionFilingDate" checkRender="true" labelTextCode="appeal.form.opposition.filing.date"/>
    </div>
                             
    <component:input  formTagClass="field-date" path="decisionDate" checkRender="true" labelTextCode="appeal.form.decisionDate"/>

	<div id="fileDocumentAttachmentAA"
		class="fileuploadInfo collectiveSelectors">
		<component:file pathFilename="gAExplanationGroundsMarksAttachment"
			fileWrapperPath="gExplanationGrounds"
			labelCode="absoluteGrounds.details.field.explanations"
			fileWrapper="${appeal.gExplanationGrounds}" />
	</div>

	<tags:render checkRender="true" flowModeId="${flowModeId}" sectionId="${sectionId}"
		field="gExplanationGrounds.explanation" var="explRadio" />
		
	<c:set var="displayExpl" value="${ not empty explRadio && explRadio eq true? 'none':'block'}" />
	<div id="explanationCommentDiv" style="display:${displayExpl}" class="attachments">
		<component:textarea path="explanations" checkRender="true" labelTextCode="absoluteGrounds.details.field.explanations" formTagClass="comments"
		tipCode="explanations.textarea.tip"/>
	</div>
	
	<c:if test="${fn:endsWith(flowModeId, '-appeal')}">
		<br/>
		<div id="evidWarningGeneral" class="alert alert-info" style="display:none">
			<spring:message code="appeal.evidence.warning"/>
		</div>
		<div id="evidWarningOppo" class="alert alert-info" style="display:none">
			<spring:message code="appeal.evidence.warning.opposition"/>
		</div>
	</c:if>

	<component:select path="gsHelper.tmApplicationNumber"
					  id="tmApplicationNumberGshelper" items="${flowBean.tmsList}"
					  itemValue="applicationNumber" itemLabel="applicationNumber"
					  labelTextCode="gshelper.form.tmApplicationNumber"
					  containsEmptyValue="false" formTagClass="appnum-select" checkRender="true"/>

	<component:generic path="gsHelper.extent" checkRender="true">
		<div id="gshelper_extent">
			<label><spring:message
					code="${flowModeId}.tm.details.field.extent" /></label>
			<p>
				<form:radiobutton path="gsHelper.extent" class="fullGshelper" value="true" />
				<span style="margin-left: 10px;"><spring:message
						code="${flowModeId}.tm.details.field.completeExtent" /></span>
			</p>
			<p>
				<form:radiobutton path="gsHelper.extent" class="partialGshelper"
								  value="false" />
				<span style="margin-left: 10px;"><spring:message
						code="${flowModeId}.tm.details.field.partialExtent" /></span>
			</p>
		</div>
	</component:generic>
	<div id="gsHelperGSCommentDiv">
		<component:textarea path="gsHelper.goodsServicesComment"
							formTagClass="textarea-fields" checkRender="true"
							labelTextCode="${flowModeId}.tm.details.field.gs.comment" tipCode="gs.comments.tip"/>
	</div>


</form:form>

<div id="gshelperGS" class="gshelperGS" style="display: none">
	<label><spring:message code="${flowModeId}.gshelper.form.gs" /></label>
	<jsp:include page="/WEB-INF/views/goods_services/goodsservices.jsp" />
	<br />
</div>

<ul class="controls">
	<li>
		<a class="cancelButton appeal">
			<spring:message code="appeal.form.action.cancelAdd.top"/>
		</a>
	</li>
	<li>
		<button class="addAppeal appeal" type="button">
			<spring:message code="appeal.form.action.save"/>
		</button>
	</li>
</ul>

