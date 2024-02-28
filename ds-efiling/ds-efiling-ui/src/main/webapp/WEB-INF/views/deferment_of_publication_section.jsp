<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="defermentOfPublication" id="defermentOfPublication">
    <header>
    	<a href="#defermentOfPublication" class="anchorLink"></a>
        <h2><spring:message code="defermentOfPublication.section.title"/></h2>
    </header>

	<form:form cssClass="mainForm formEF" id="defermentOfPublicationForm" modelAttribute="flowBean.mainForm">
		
		<c:set var="sectionId" value="defermentOfPublication" scope="request"/>

    	<component:checkbox path="requestDeferredPublication" checkRender="true"
        	labelTextCode="defermentOfPublication.field.requestDeferredPublication" id="requestDeferredPublication" labelClass="normalFont" />
    	<div id="defermentTillDateDiv" style="<c:if test="${not flowBean.mainForm.requestDeferredPublication}">display: none;</c:if>">
    		<div class="alert alert-info">
				<spring:message code="defermentOfPublication.form.field.requestDeferredPublication.info" />
			</div>
    		<component:input path="defermentTillDate" checkRender="true" id="defermentTillDate"
                    labelTextCode="defermentOfPublication.field.defermentTillDate" formTagClass="standard-date-input" showDateHelp="true" />
    	</div>
    	
	</form:form>
</section>
