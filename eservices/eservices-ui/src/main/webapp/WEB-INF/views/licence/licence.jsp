<%@ page import="eu.ohim.sp.common.ui.form.application.SignatoryKindForm" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<form:form class="mainForm formEf" modelAttribute="licence">
	<c:set var="sectionId" value="licence" scope="request"/>
	<input type="hidden" id="formReturned" value="true"/>
	
	<form:hidden id="hiddenFormId" path="id"/>
	
	<ul class="controls">
	    <li>
	        <a class="cancelButton licence">
	        	<spring:message code="licence.form.action.cancelAdd.top"/>
	        </a>
	    </li>
	    <li>		        	
	        <button class="addLicence licence" type="button">			            	
	            <spring:message code="licence.form.action.save"/>
	        </button>
	    </li>
	</ul>

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

	<component:generic path="licenceIsCompulsory" checkRender="true">
		<c:set var="compulsoryLicence" value="true" scope="request"/>
	</component:generic>

	<c:if test="${compulsoryLicence}">
		<component:generic path="licenceKind" checkRender="true">
			<div id="licenceKindRadios">
				<label><spring:message code="licence.field.licenceKind"/></label>
				<p><input type="radio" name="licenceKind" value="NONEXCLUSIVE" checked /><span  style="margin-left:10px;"><spring:message code="licence.field.licenceKind.Nonexclusive.compulsory" /></span></p>
				<c:set var="errors"><form:errors path="licenceKind"></form:errors></c:set>
				<c:if test="${!empty errors}">
					<c:forEach items="${errors}" var="message">
						<p class="flMessageError" >${message}</p>
					</c:forEach>
				</c:if>
			</div>
		</component:generic>
	</c:if>
	<c:if test="${!compulsoryLicence}">
	<component:generic path="licenceKind" checkRender="true">
		<div id="licenceKindRadios">
			<label><spring:message code="licence.field.licenceKind"/></label>
			<p><input type="radio" name="licenceKind" value="NONEXCLUSIVE" <c:if test="${licence.licenceKind == null || licence.licenceKind.toString() eq 'Nonexclusive' }">checked</c:if> /><span  style="margin-left:10px;"><spring:message code="licence.field.licenceKind.Nonexclusive" /></span></p>
			<p><input type="radio" name="licenceKind" value="EXCLUSIVE" <c:if test="${licence.licenceKind != null && licence.licenceKind.toString() eq 'Exclusive' }">checked</c:if>/><span  style="margin-left:10px;"><spring:message code="licence.field.licenceKind.Exclusive" /></span></p>
			<c:set var="errors"><form:errors path="licenceKind"></form:errors></c:set>
			<c:if test="${!empty errors}">
		        <c:forEach items="${errors}" var="message">
		            <p class="flMessageError" >${message}</p>
		        </c:forEach>
		    </c:if>
		</div>
	</component:generic>
	</c:if>
	<div id="sublicenceGrant" style="display:none">
		<component:generic path="subLicenceIndicator" checkRender="true">
			<label><spring:message code="licence.field.sublicenceIndicator"/></label>
			<p><input type="radio" name="subLicenceIndicator" value="true" <c:if test="${licence.subLicenceIndicator == null || licence.subLicenceIndicator == true }">checked</c:if> /><span  style="margin-left:10px;"><spring:message code="licence.field.subLicenceIndicator.true" /></span></p>
			<p><input type="radio" name="subLicenceIndicator" value="false" <c:if test="${licence.subLicenceIndicator != null && licence.subLicenceIndicator == false }">checked</c:if>/><span  style="margin-left:10px;"><spring:message code="licence.field.subLicenceIndicator.false" /></span></p>
			<c:set var="errors"><form:errors path="subLicenceIndicator"></form:errors></c:set>
			<c:if test="${!empty errors}">
		        <c:forEach items="${errors}" var="message">
		            <p class="flMessageError" >${message}</p>
		        </c:forEach>
		    </c:if>
		</component:generic>
	</div>
	
	<component:generic path="territoryLimitationIndicator" checkRender="true">
		<div id="territoryRadios">
			<label><spring:message code="licence.field.territoryLimitationIndicator"/></label>
			<p><input type="radio" name="territoryLimitationIndicator" value="false" <c:if test="${licence.territoryLimitationIndicator == null || licence.territoryLimitationIndicator == false }">checked</c:if> /><span  style="margin-left:10px;"><spring:message code="licence.field.territoryLimitationIndicator.false"/></span></p>
			<p><input type="radio" name="territoryLimitationIndicator" value="true" <c:if test="${licence.territoryLimitationIndicator != null && licence.territoryLimitationIndicator == true }">checked</c:if> /><span  style="margin-left:10px;"><spring:message code="licence.field.territoryLimitationIndicator.true"/></span></p>
			<c:set var="errors"><form:errors path="territoryLimitationIndicator"></form:errors></c:set>
			<c:if test="${!empty errors}">
		        <c:forEach items="${errors}" var="message">
		            <p class="flMessageError" >${message}</p>
		        </c:forEach>
		    </c:if>
		</div>
		<div style="display:none" id="licenceTerritoryTextDiv">
			<component:textarea path="territoryLimitationText" checkRender="true" labelTextCode="licence.field.territoryLimitationText"
			tipCode="territory.licence.textarea.tip"/>
		</div>
	</component:generic>
	
	<component:checkbox id="licencePeriodLimitationIndicator" path="periodLimitationIndicator" checkRender="true" labelTextCode="licence.field.periodLimitationIndicator.${flowModeId }"/>
	
	<div id="licencePeriodLimitationEndDate">
		<component:input  formTagClass="field-date" path="periodLimitationEndDate" checkRender="true" labelTextCode="licence.field.periodLimitationEndDate"/>
	</div>

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

<div id="surrenderDeclarationPartial" style="display: none"
	class="alert alert-danger">
	<spring:message code="${flowModeId}.gshelper.form.declaration.partial" />
</div>

<div id="gshelperGS" class="gshelperGS" style="display: none">
	<label><spring:message code="${flowModeId}.gshelper.form.gs" /></label>
	<jsp:include page="/WEB-INF/views/goods_services/goodsservices.jsp" />
	<br />
</div>

<ul class="controls">
	<li><a class="cancelButton licence"> <spring:message
				code="licence.form.action.cancelAdd.top" />
	</a></li>
	<li>
		<button class="addLicence licence" type="button">
			<spring:message code="licence.form.action.save" />
		</button>
	</li>
</ul>