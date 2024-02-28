<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="flBox noBG">
	<form:form modelAttribute="exhibitionForm"  id="exhibitionFormCard">
		<p class="flAlignRight">
			<a class="flButton flAlignRight editExhibition" data-id="${exhPriorityForm.id}" ><spring:message code="claim.action.edit" /></a>
		</p>

		<h4 class="boxTitle">
			<span>${exhPriorityForm.id}</span>. <span><spring:message code="claim.title.exhibition" /></span>
		</h4>
		<div class="card alignCenter">
<!-- 			<h5>Name</h5> -->
			<div class="fl2lcols">
				<div>
					<span class=""><spring:message code="claim.exhibition.field.name" />: </span>
					<span class=""><b>${exhPriorityForm.exhibitionName}</b></span>

				</div>
				<div>
					<span class=""><spring:message code="claim.exhibition.field.firstDisclsure" />: </span>
					<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
					<span class=""><b><fmt:formatDate type="date" pattern="${dateFormat}"  value="${exhPriorityForm.firstDate}" /></b></span>
				</div>
			</div>
		</div>
	</form:form>		
</div>