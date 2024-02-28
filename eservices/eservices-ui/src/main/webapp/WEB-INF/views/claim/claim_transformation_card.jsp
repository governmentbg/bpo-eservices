<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="flBox noBG">
    <form:form modelAttribute="transformationForm" id="transformationFormCard">
        <p class="flAlignRight">
            <a class="flButton flAlignRight editTransformation" data-id="${transformationForm.id}"><spring:message
                    code="claim.action.edit"/></a>
        </p>

        <h4 class="boxTitle"><span>${transformationForm.id}</span>. <span><spring:message code="claim.title.transformation"/></span>
        </h4>

		<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
        <div class="card alignCenter">
            <div class="fl3lcols">
                <div>
                    <span class=""><spring:message code="claim.transformation.field.irNumber"/>: </span> <span
                        class=""><b>${transformationForm.irNumber}</b></span>
                </div>
                <div>
                    <span class=""><spring:message code="claim.transformation.field.cancelDate"/>: </span> <span
                        class=""><b><fmt:formatDate type="date" pattern="${dateFormat}"
                                                    value="${transformationForm.cancelationDate}"/></b></span>
                </div>
                <div>

                    <span class=""><spring:message code="claim.transformation.field.irDate"/>: </span> <span
                        class=""><b><fmt:formatDate type="date" pattern="${dateFormat}" value="${transformationForm.irDate}"/></b></span>
                </div>
            </div>
        </div>
    </form:form>
</div>