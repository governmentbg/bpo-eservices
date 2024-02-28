<%@ page
		import="eu.ohim.sp.common.ui.form.application.SignatoryKindForm"%>
<%@ page import="java.util.Arrays"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>

<h1>${add_mode}</h1>

<c:set var="sectionId" value="gshelper" scope="request" />
<c:choose>

	<c:when test="${empty flowBean.tmsList }">
		<div class="alert alert-info">
			<spring:message code="gshelper.form.must.add.trademark" />
		</div>
		<ul class="controls">
			<li><a class="cancelButton gshelper"> <spring:message
					code="gshelper.form.action.cancelAdd.top" />
			</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<ul class="controls">
			<li><a class="cancelButton gshelper"> <spring:message
					code="gshelper.form.action.cancelAdd.top" />
			</a></li>
			<li>
				<button class="addGshelper gshelper" type="button">
					<spring:message code="gshelper.form.action.save" />
				</button>
			</li>
		</ul>
		<form:form class="mainForm formEf" modelAttribute="gshelper">
			<input type="hidden" id="formReturned" value="true" />

			<c:set var="inclusiveFiling" value="false"/>
			<c:if test="${flowModeId =='tm-renewal' || flowModeId =='tm-transfer'
			||flowModeId =='tm-opposition' || flowModeId =='tm-objection'
			|| flowModeId =='tm-invalidity' || flowModeId =='tm-revocation'}">
				<c:set var="inclusiveFiling" value="true"/>
			</c:if>
			<form:hidden id="inclusiveFilingField" path="inclusive" value="${ inclusiveFiling}" />

			<form:hidden id="hiddenFormId" path="id" />

			<br/>
			<component:generic path="formMessages" checkRender="true">
				<c:set var="errors">
					<form:errors path="formMessages"></form:errors>
				</c:set>
				<c:if test="${!empty errors}">
					<c:forEach items="${errors}" var="message">
						<p class="flMessageError permanentMessage"
						   id="${path}ErrorMessageServer">${message}</p>
					</c:forEach>
				</c:if>
			</component:generic>

			<component:select path="tmApplicationNumber" disabled="${edit_mode}"
							  id="tmApplicationNumberGshelper" items="${flowBean.tmsList}"
							  itemValue="applicationNumber" itemLabel="applicationNumber"
							  labelTextCode="gshelper.form.tmApplicationNumber"
							  containsEmptyValue="false" formTagClass="appnum-select" />

			<component:generic path="extent" checkRender="true">
				<div id="gshelper_extent">
					<label><spring:message
							code="${flowModeId}.tm.details.field.extent" /></label>
					<p>
						<form:radiobutton path="extent" class="fullGshelper" value="true" />
						<span style="margin-left: 10px;"><spring:message
								code="${flowModeId}.tm.details.field.completeExtent" /></span>
					</p>
					<p>
						<form:radiobutton path="extent" class="partialGshelper"
										  value="false" />
						<span style="margin-left: 10px;"><spring:message
								code="${flowModeId}.tm.details.field.partialExtent" /></span>
					</p>
				</div>
			</component:generic>
			<div id="gsHelperGSCommentDiv">
				<component:textarea path="goodsServicesComment"
									formTagClass="textarea-fields" checkRender="true"
									labelTextCode="${flowModeId}.tm.details.field.gs.comment" tipCode="gs.comments.tip"/>
			</div>
		</form:form>

		<c:if test="${flowModeId == 'tm-surrender' || flowModeId == 'tm-withdrawal' }">
			<div id="surrenderDeclarationFull" style="display: none" class="alert alert-danger">
				<spring:message code="${flowModeId}.gshelper.form.declaration.full" />
			</div>
		</c:if>
		<div id="surrenderDeclarationPartial" style="display: none"  class="alert alert-danger">
			<spring:message code="${flowModeId}.gshelper.form.declaration.partial" />
		</div>

		<div id="gshelperGS" class="gshelperGS" style="display: none">
			<label><spring:message
					code="${flowModeId}.gshelper.form.gs" /></label>
			<jsp:include page="/WEB-INF/views/goods_services/goodsservices.jsp" />
			<br/>

		</div>


		<ul class="controls">
			<li><a class="cancelButton gshelper"> <spring:message
					code="gshelper.form.action.cancelAdd.top" />
			</a></li>
			<li>
				<button class="addGshelper gshelper" type="button">
					<spring:message code="gshelper.form.action.save" />
				</button>
			</li>
		</ul>
	</c:otherwise>
</c:choose>

