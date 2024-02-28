<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="flBox noBG">
	<form:form modelAttribute="priorityForm" id="priorityFormCard">
		<p class="flAlignRight" >
			<a class="flButton flAlignRight editPriority" data-id="${priorityForm.id}"><spring:message code="claim.action.edit" /></a>
		</p>
		<h4 class="boxTitle">
			<span>${priorityForm.id}. <spring:message code="claim.title.priority" /></span>
		</h4>
		<div class="card alignCenter">
<!-- 			<h5>Name</h5> -->
			<div class="fl3lcols">
				<div>
					<span class=""><spring:message code="claim.priority.field.countryOfLastFiling" />: </span>
					<span class=""><b>${priorityForm.country}</b></span>
				</div>
				<div>
					<span class=""><spring:message code="claim.priority.field.onWhatDate" />: </span>
					<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
					<span class=""><b><fmt:formatDate type="date" pattern="${dateFormat}"  value="${priorityForm.date}" /></b></span>
				</div>
				<div>
					<span class=""><spring:message code="claim.priority.field.filingNumber" />: </span>
					<span class=""><b>${priorityForm.number}</b></span>
				</div>
			</div>
		</div>
	</form:form>
</div>