<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section  class="trademark">
	    
	    <div id="divGroundCategory">
		    <component:generic path="groundCategory" checkRender="true">
			    <label><spring:message code="opposition_basis.details.field.groundCategory"/><span class="requiredTag">*</span></label>
			    <select name="gcategory" id="gcategory">
			        <option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
			        <c:if test="${x > 0}">
			        	<option value="ABSOLUTE_GROUNDS"><spring:message code="opposition_basis.details.groundCategory.absoluteGrounds"/></option>
			        </c:if>
			        <option value="RELATIVE_GROUNDS"><spring:message code="opposition_basis.details.groundCategory.relativeGrounds"/></option>
			    </select>
			</component:generic>
	    </div>
	 
</section>