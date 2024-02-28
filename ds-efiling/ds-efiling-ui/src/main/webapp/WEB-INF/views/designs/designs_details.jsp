<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<!-- DS Class Integration Changes Start -->
<input type="hidden" name="execution" value="${empty param.execution?param.flowKey:param.execution}" />
<!-- DS Class Integration Changes End -->
<section class="designs" id="designsDetails">
	
    <header>
    	<a href="#designsDetails" class="anchorLink"></a>
        <h2><spring:message code="designs.section.title"/><span class="requiredTag">*</span></h2>
    </header>

	<form:form cssClass="mainForm formEF" id="designsDetailsForm" modelAttribute="flowBean">
		<c:set var="sectionId" value="designsDetails" scope="request"/>

		<component:generic checkRender="true" path="designCount">
			<label>
				<spring:message code="designs.field.numberOfDesigns" />
	    		<span class="design-count" id="designCount">${flowBean.designs.size()}</span>
			</label>
		</component:generic>
    	<component:textarea path="mainForm.productDescription" checkRender="true"
        	labelTextCode="designs.field.productDescription" id="productDescription" formTagClass="description-textarea"/>

		<component:textarea path="mainForm.applicationVerbalElementsEn" checkRender="true" labelTextCode="designs.field.applicationVerbalElementsEn"
						 formTagClass="description-textarea"/>
	</form:form>

    <div id="designsListDiv"><jsp:include page="designs_list.jsp"/></div>

	<jsp:include page="designs_duplicate_form.jsp"/>

	<span id="designsErrorDiv"></span>
	
    <button class="btn add-button" id="addAnotherDesignBtn">
    	<i class="add-icon"></i>
    	<spring:message code="designs.button.add"/>
    </button>

    <div class="addbox" id="addDesignForm" style="display: none;"></div>

	<span id="design_form_error_load" class="hidden">
        <spring:message code="design.form.error.load"/>
    </span>
    
	<span id="design_form_error_submit" class="hidden">
        <spring:message code="design.form.error.submit"/>
    </span>
    
    <span id="design_form_error_duplicate_reach_max" class="hidden">
    	<spring:message code="error.ef.max.DesignForm" />
    </span>
	<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
		/design/register-design/design-details
	</div>
</section>
