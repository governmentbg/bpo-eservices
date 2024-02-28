<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="duplicateDesignForm" class="modal fade messagePopup modal-standard">
<div class="modal-dialog">
<div class="modal-content">
	<header>
    	<h1>
			<spring:message code="design.duplicate.form.title"/>
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
	</header>
    <div class="modal-body">
        <span>
        	<spring:message code="design.duplicate.form.question"/>
        </span>
		<div>
			<form class="formEF">
				<input type="hidden" id="designIdToDuplicate" />
				<div>
					<label for="timesToDuplicate">
						<spring:message code="design.duplicate.form.field.times"/>
						<span class="requiredTag">*</span>
					</label>
					<input type="text" name="timesToDuplicate" id="timesToDuplicate" class="mandatory" />
				</div>
				<c:if test="${not empty _csrf.parameterName}">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</c:if>
			</form>
		</div>
		<br />
		<div class="alert alert-info">
			<spring:message code="design.duplicate.form.warning" />
		</div>
	</div>	
   	<footer>
       	<ul>
           	<li>
               	<button type="button" data-dismiss="modal">
                   	<spring:message code="design.duplicate.form.button.cancel"/>
				</button>
           	</li>
           	<li>
               	<button type="button" id="duplicateDesignOkBtn">
                   	<spring:message code="design.duplicate.form.button.duplicate"/>
               	</button>
           	</li>
       	</ul>
   	</footer>
</div>
</div>
</div>
