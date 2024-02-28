<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="flBox noBG">
    <form:form modelAttribute="seniorityForm" id="seniorityFormCard">
        <p class="flAlignRight">
            <a class="flButton flAlignRight editSeniority" data-id="${seniorityForm.id}"><spring:message code="claim.action.edit"/></a>
        </p>
        <h4 class="boxTitle"><span>${seniorityForm.id}</span>. <span><spring:message code="claim.title.seniority"/></span></h4>

        <div class="card alignCenter">
            <div class="fl2lcols">
                <div>
                    <span class=""><spring:message code="claim.seniority.field.nature"/>: </span>
                    <span class=""><b>${seniorityForm.nature}</b></span>
                </div>
                <div>
                    <span class=""><spring:message code="claim.seniority.field.memberState"/>: </span>
                    <span class=""><b>${seniorityForm.country}</b></span>
                </div>
            </div>
			<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
            <div class="fl2lcols">
                <div>
                    <span class=""><spring:message code="claim.seniority.field.filingDate"/>: </span>
                    
                    <span class=""><b><fmt:formatDate type="date" pattern="${dateFormat}" value="${seniorityForm.filingDate}"/></b></span>
                </div>
                <div>
                    <span class=""><spring:message code="claim.seniority.field.filingNumber"/>: </span>
                    <span class=""><b>${seniorityForm.filingNumber}</b></span>

                </div>
            </div>
            <div class="fl2lcols">
                <div>
                    <span class=""><spring:message code="claim.seniority.field.registrationDate"/>: </span>
                    <span class=""><b><fmt:formatDate type="date" pattern="${dateFormat}" value="${seniorityForm.registrationDate}"/></b></span>
                </div>
                <div>

                    <span class=""><spring:message code="claim.seniority.field.registrationNumber"/>: </span>
                    <span class=""><b>${seniorityForm.registrationNumber}</b></span>
                </div>
            </div>
        </div>
    </form:form>
</div>