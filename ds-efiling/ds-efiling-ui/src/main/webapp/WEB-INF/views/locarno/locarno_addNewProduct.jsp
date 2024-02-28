<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div class="modal-dialog">
<div class="modal-content">
<header>
	<h1>
		<spring:message code="design.form.classes.create" />
	</h1>
	<a class="close-icon" data-dismiss="modal"></a>
</header>

<section class="modal-body">

	<div class="alert alert-info locarno-warning-message">
		<spring:message code="design.form.classes.modal.warning" />
	</div>
  
 	<form:form id="formAddNewProduct" cssClass="formEF" modelAttribute="locarnoForm">
 		
 		<c:set var="sectionId" value="locarnoNewProduct" scope="request" />

		<%-- For sending the id given to the locarno form when a product is edited. --%>
		<form:hidden id="hiddenFormId" path="id" />
		<component:select items="${flowBean.locarnoClasses}" path="locarnoClassification.locarnoClass.clazz" id="selectLocarnoClass" 
				itemLabel="clazz" itemValue="clazz" itemFilter="" checkRender="true" labelTextCode="design.form.classes.modal.label.product.classification"
				containsEmptyValue="true" />
		<component:select items="${flowBean.locarnoSubclasses}" path="locarnoClassification.locarnoClass.subclass" id="selectLocarnoSubclass" 
				itemLabel="subclass" itemValue="subclass" checkRender="true"
				containsEmptyValue="${not empty flowBean.locarnoSubclasses}" />

		<component:input path="locarnoClassification.indication" checkRender="true" labelTextCode="design.form.classes.modal.label.product.myIndication" />
 		
    </form:form>
    
</section>
    
<footer>
    <ul>
        <li><a data-dismiss="modal"><spring:message code="design.form.classes.model.footer.buttonCancel" /></a></li>
        <li><button id="buttonSaveAddNewProduct"><spring:message code="design.form.classes.model.footer.buttonSave" /></button></li>
    </ul>
</footer>
</div>
</div>