<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="locarnoButtons">
	
	<component:generic path="showLocarnoAddClass" checkRender="true">
		<c:set var="isTherePrevious" value="true" />
		<a href="#section_locarno" data-target="addClass">
			<button id="locarnoSearchBtn" class="create-new-button" type="button">
				<spring:message code="design.form.classes.search" />
			</button>
		</a>
	</component:generic>
	
	<component:generic path="showLocarnoNewProduct" checkRender="true">
		
		<c:if test="${not empty isTherePrevious and isTherePrevious}">
			<span class="separator"><spring:message code="person.form.separator.or" /></span>
		</c:if>

		<c:set var="isTherePrevious" value="true" />
		
		<a href="#section_locarno" data-target="addNewProduct">
			<button id="locarnoAddNewProductBtn" class="create-new-button" type="button">
				<i class="add-icon"></i>
				<spring:message code="design.form.classes.create" />
			</button>
		</a>
	</component:generic>

	<component:generic path="showLocarnoNewComplexProduct" checkRender="true">
		<c:if test="${not empty isTherePrevious and isTherePrevious}">
			<span class="separator"><spring:message code="person.form.separator.or" /></span>
		</c:if>

		<a href="#section_locarno" data-target="addNewComplexProduct">
			<button id="locarnoAddNewComplexProductBtn" class="create-new-button" type="button">
				<i class="add-icon"></i>
				<spring:message code="design.form.classes.create" />
			</button>
		</a>
	</component:generic>
	

</div>
