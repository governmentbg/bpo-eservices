<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<form:form cssClass="formEF" id="formAddDesign" modelAttribute="designForm">
	<c:set var="sectionId" value="design" scope="request"/>
	
	<header>
		<span class="number" id="designCurrentNumber"></span>
    	<h3><spring:message code="design.form.title"/></h3>
    	<ul class="controls">
    		<li><a id="cancelDesignBtn"><spring:message code="design.form.button.cancel"/></a></li>
    	</ul>
	</header>
     
    <component:generic path="verbalElements" checkRender="true">   
    	<label for="verbalElements"><spring:message code="design.form.field.verbalElements" /></label>
		<form:textarea path="verbalElements" id="verbalElements" maxlength="500" cssClass="design-textarea" disabled="true" />
    </component:generic>
    
    <component:generic path="description" checkRender="true">
    	<label for="description"><spring:message code="design.form.field.description" /></label> 
    	<form:textarea path="description" id="description" cssClass="design-textarea" disabled="true" />
    </component:generic>
    
    <component:generic path="distinctiveFeatures" checkRender="true">
    	<label for="distinctiveFeatures"><spring:message code="design.form.field.distinctiveFeatures" /></label>
    	<form:textarea path="distinctiveFeatures" id="distinctiveFeatures" cssClass="design-textarea" disabled="true" />
   	</component:generic>
	
	<component:generic path="colours" checkRender="true">
		<label for="colours"><spring:message code="design.form.field.colours" /></label>
		<form:textarea path="colours" id="colours" cssClass="design-textarea" disabled="true" />
	</component:generic>	
    
    <component:generic path="requestDeferredPublication" checkRender="true">
    	<label for="requestDeferredPublicationDesign">
    		<form:checkbox path="requestDeferredPublication" id="requestDeferredPublicationDesign" disabled="true" />
    		<span><spring:message code="design.form.field.requestDeferredPublication" /></span>
    	</label>
    	
    	<component:generic path="defermentTillDate" checkRender="true">
	    	<div id="defermentTillDateDesignDiv" style="<c:if test="${not designForm.requestDeferredPublication}">display: none;</c:if>">
	        	<div class="alert alert-info">
	        		<spring:message code="design.form.field.requestDeferredPublication.info" />
				</div>
				<label for="defermentTillDateDesign"><spring:message code="design.form.field.defermentTillDate" /></label>
	        	<form:input path="defermentTillDate" id="defermentTillDateDesign" cssClass="standard-date-input" disabled="true" />
	        </div>
        </component:generic>
    </component:generic>
</form:form>
	


<%-- PRODUCT INDICATION --%>
<c:set var="sectionId" value="designsDetails" scope="request" />
<component:generic path="locarnoEnabled" checkRender="true">
	<label><spring:message code="design.form.productIndication" /></label>
	<%-- DS Class Integration Changes Start --%>
	<jsp:include page="../locarno/dsclass/locarno_Table.jsp">
		<jsp:param value="true" name="viewMode" />
	</jsp:include>
	<%-- DS Class Integration Changes End --%>
</component:generic>   
<%-- END PRODUCT INDICATION --%>

<%-- VIEWS --%>
<label><spring:message code="design.form.views" /></label>
<jsp:include page="views/view_list.jsp">
	<jsp:param value="true" name="viewMode" />
</jsp:include>
<%-- END VIEWS --%>

<br />
     
<footer>
   	<ul class="controls">
		<li><a id="cancelDesignBtn"><spring:message code="design.form.button.cancel"/></a></li>
	</ul>
</footer>

<jsp:include page="views/view_section.jsp" />