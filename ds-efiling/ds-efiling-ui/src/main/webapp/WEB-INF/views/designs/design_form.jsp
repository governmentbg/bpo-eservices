<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<form:form cssClass="formEF" id="formAddDesign" modelAttribute="designForm">
	<c:set var="sectionId" value="design" scope="request"/>
	<input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>
	<form:hidden path="number" />
	
    <header>
    	<span class="number" id="designCurrentNumber"></span>
        <h3><spring:message code="design.form.title"/></h3>
        <ul class="controls">
        	<li><a id="cancelDesignBtn"><spring:message code="design.form.button.cancel"/></a></li>
            <li>
            	<button type="button" id="addDesignBtn">
                	<spring:message code="design.form.button.save"/>
                </button>
            </li>
		</ul>
	</header>
        
    <span id="designErrorDiv"></span>
        
    <component:textarea path="verbalElements" checkRender="true"
            labelTextCode="design.form.field.verbalElements" id="verbalElements" formTagClass="design-textarea"/>
    <component:generic path="verbalElements" checkRender="true">
		<div class="genericHelp">
			<ul>
	        	<li><component:helpMessage textCode="design.form.field.verbalElements.help" useFlowModeId="false"/></li>
	        </ul>
		</div>
    </component:generic>

	<component:textarea path="verbalElementsEn" checkRender="true" labelTextCode="design.form.field.verbalElementsEn" formTagClass="design-textarea"/>
            
    <component:textarea path="description" checkRender="true"
            labelTextCode="design.form.field.description" id="description" formTagClass="design-textarea"/>
    
    <component:textarea path="distinctiveFeatures" checkRender="true"
            labelTextCode="design.form.field.distinctiveFeatures" id="distinctiveFeatures" formTagClass="design-textarea"/>
	<component:generic path="distinctiveFeatures" checkRender="true">
		<div class="genericHelp">
			<ul>
	        	<li><component:helpMessage textCode="design.form.field.distinctiveFeatures.help" useFlowModeId="false"/></li>
	        </ul>
		</div>
    </component:generic>
            
    <component:textarea path="colours" checkRender="true"
            labelTextCode="design.form.field.colours" id="colours" formTagClass="design-textarea"/>
    
    <component:checkbox path="requestDeferredPublication" checkRender="true"
            labelTextCode="design.form.field.requestDeferredPublication" id="requestDeferredPublicationDesign"/>
        
    <div id="defermentTillDateDesignDiv" style="<c:if test="${not designForm.requestDeferredPublication}">display: none;</c:if>">
		<div class="alert alert-info">
        	<spring:message code="design.form.field.requestDeferredPublication.info" />
		</div> 
        <component:input path="defermentTillDate" checkRender="true" id="defermentTillDateDesign"
            	labelTextCode="design.form.field.defermentTillDate" formTagClass="standard-date-input" showDateHelp="true" />
	</div>

</form:form>



<%-- PRODUCT INDICATION --%>
<c:set var="sectionId" value="designsDetails" scope="request" />
<component:generic path="locarnoEnabled" checkRender="true">

	<form:form modelAttribute="designForm">
		<c:set var="sectionId" value="design" scope="request"/>
		<component:label path="locarno" labelTextCode="design.form.productIndication" labelClass="displayinlineblock"/>
		<component:fastTrackMark sectionId="designsDetails" fieldId="locarnoEnabled" helpMessageKey="fasttrack.help.locarno.intro"/>
	</form:form>
	
	<c:set var="sectionId" value="designsDetails" scope="request" />
	<!-- DS Class Integration Changes Start -->
	<%-- <jsp:include page="../locarno/locarno_section_list.jsp"/> --%>
	<jsp:include page="../locarno/dsclass/locarno_addPI.jsp"/>
	<!-- DS Class Integration Changes End -->
</component:generic>
<%-- END PRODUCT INDICATION --%>

<%-- VIEWS --%>
<form:form modelAttribute="designForm">
	<component:label path="views" labelTextCode="design.form.views" />
	<c:set var="errors">
		<form:errors path="views" />
	</c:set>
	<c:if test="${!empty errors}">

		<c:forEach items="${errors}" var="message">
			<p class="flMessageError" id="ErrorMessageServer">${message}</p>
		</c:forEach>
	</c:if>
</form:form>
<jsp:include page="views/view_list.jsp" />

<a class="btn add-button" href="#designViewSection" id="addViewDesignBtn">
	<i class="add-icon"></i>
	<spring:message code="design.form.views.button.add"/>
</a>
<%-- END VIEWS --%>
	
<footer>
   	<ul class="controls">
		<li><a id="cancelDesignBtn"><spring:message code="design.form.button.cancel"/></a></li>
        <li>
        	<button type="button" id="addDesignBtn">
        		<spring:message code="design.form.button.save"/>
        	</button>
		</li>
	</ul>
</footer>
    
<jsp:include page="views/view_section.jsp" />
    
<jsp:include page="../locarno/locarno_section.jsp" />
