<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="colourSection">

    <input type="hidden" name="sectionId" id="sectionId" value="${sectionId}"/>
	<div class="col-400">
		<component:checkbox path="mainForm.colourChecked"
							checkRender="true" id="addColourCheck"
							labelTextCode="mark.color.doesItHave" formTagClass="" labelClass="correspondence-address"/>
	</div>
		
	<c:if test="${flowBean.mainForm.colourChecked == true}">
		<span id="colourSection_internal" style="display:inline">
	</c:if>
	<c:if test="${flowBean.mainForm.colourChecked == false}">
		<span id="colourSection_internal" style="display:none">
	</c:if>
		<jsp:include page="../mark_colormark_internal.jsp"/>
	</span>

</div>