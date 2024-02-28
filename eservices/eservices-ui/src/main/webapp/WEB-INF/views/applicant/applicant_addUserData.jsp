<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<header>
	<h1>
		<spring:message code="applicant.form.user.add"/>
	</h1>
	<a class="close-icon" data-dismiss="modal"></a>
</header>
  
<section class="modal-body">

	<jsp:include page="/WEB-INF/views/applicant/applicant_user_card_list.jsp"/>
	
</section>
  
<footer>
	<ul>
    	<li><a data-dismiss="modal"><spring:message code="applicant.form.user.model.footer.buttonCancel" /></a></li>
		<li><button data-dismiss="modal" id="buttonSaveAddApplicantUser"><spring:message code="applicant.form.user.model.footer.buttonSave" /></button></li>
	</ul>
</footer>
