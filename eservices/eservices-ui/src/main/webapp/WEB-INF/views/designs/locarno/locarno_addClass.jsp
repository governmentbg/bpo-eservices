<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div class="modal-dialog">
<div class="modal-content">
<header>
	<h1>
		<spring:message code="design.form.classes.add"/>
	</h1>
	<a class="close-icon" data-dismiss="modal"></a>
</header>
  
<section class="modal-body">

	<form:form id="formSearchClass" cssClass="formEF" modelAttribute="locarnoSearchForm">
	
		<c:set var="sectionId" value="locarnoAddClass" scope="request" />

  		<component:generic path="formMessages" checkRender="true">
              <c:set var="errors">
                     <form:errors path="formMessages"></form:errors>
              </c:set>         
              <c:if test="${!empty errors}">
	               <c:forEach items="${errors}" var="message">
	               	<p class="flMessageError permanentMessage" id="${path}ErrorMessageServer">${message}</p>
	               </c:forEach>
              </c:if>              
        </component:generic>   

		<div class="class-search">
			<component:input path="searchData.indication" checkRender="true" labelTextCode="design.form.classes.modal.label.product.term" />
		</div>
		
		<div class="class-search">
			<component:select items="${flowBean.locarnoClasses}" path="searchData.locarnoClass.clazz" id="selectLocarnoClass" 
					itemLabel="clazz" itemValue="clazz" itemFilter="" checkRender="true" labelTextCode="design.form.classes.modal.label.product.classification"
					containsEmptyValue="true" />
			<component:select items="${flowBean.locarnoSubclasses}" path="searchData.locarnoClass.subclass" id="selectLocarnoSubclass" 
					itemLabel="subclass" itemValue="subclass" checkRender="true"
					containsEmptyValue="${not empty flowBean.locarnoSubclasses}" />
		</div>
		
		<div class="class-search-button">
			<button id="buttonSearchLocarnoClassifications" class="btn" type="button">
				<spring:message code="design.form.classes.modal.button.search" />
			</button>
 		</div>
 		
    </form:form>

	<div id="locarnoClassificationsList">
		
	</div>
	
</section>
  
<footer>
	<ul>
    	<li><a data-dismiss="modal"><spring:message code="design.form.classes.model.footer.buttonCancel" /></a></li>
		<li><button id="buttonSaveAddClass"><spring:message code="design.form.classes.model.footer.buttonSave" /></button></li>
	</ul>
</footer>
</div>
</div>