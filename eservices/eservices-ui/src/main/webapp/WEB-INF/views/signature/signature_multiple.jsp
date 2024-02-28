<%@ page import="eu.ohim.sp.common.ui.form.application.SignatoryKindForm" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">
	<form:form id="signatureForm" cssClass="formEF" modelAttribute="signatureForm">
		<c:set var="imported" value="${signatureForm.imported}" scope="request"/>
		<c:set var="sectionId" value="signature_details_section" scope="request"/>

		<input type="hidden" id="formReturned" value="true"/>
		<form:hidden id="hiddenFormId" path="id"/>
		<form:hidden id="importedId" path="imported"/>

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
		<c:set var="sectionId" value="signature_details_section" scope="request"/>

		<component:input path="fullName" checkRender="true" labelTextCode="signature.signatoryName"
						 formTagClass="name-text"/>

		<component:generic path="personCapacity" checkRender="true">
			<c:set var="enumValues" value="${configurationServiceDelegator.getSignatoryKinds(flowModeId)}"/>
			<component:select path="personCapacity"
							  items="${enumValues}"
							  itemValue="code" itemLabel="description" labelTextCode="signature.capacityOfSignatory"
							  containsEmptyValue="true"
							  formTagClass="capacity-select"/>
		</component:generic>

		<component:input path="position" checkRender="true" labelTextCode="signature.position"
						 formTagClass="name-text"/>

		<component:input path="email" checkRender="true" labelTextCode="signature.email"/>

		<component:input path="userId" checkRender="true" labelTextCode="signature.userId"/>

		<component:generic path="date" checkRender="true">

			<component:input path="date" checkRender="true" labelTextCode="signature.date"
							 formTagClass="standard-date-input"/>

		</component:generic>

		<component:generic path="signaturePlace" checkRender="true">

			<component:input path="signaturePlace" checkRender="true" labelTextCode="signature.place"
							 formTagClass="name-text"/>

		</component:generic>

		<div id="associatedTextDiv" style="display:none">
			<component:input path="signatureAssociatedText" checkRender="true" labelTextCode="review.signature.position"
							 formTagClass="name-text"/>
		</div>


		<ul class="controls">
			<li>
				<a class="cancelButton signature"><spring:message
						code="applicant.form.action.cancelAdd.top"/></a>
			</li>
			<li>
				<button class="addSignature addSign" type="button">
					<i class="add-icon"/>
					<spring:message code="applicant.form.action.add.top"/>
				</button>

			</li>
		</ul>

	</form:form>
</section>