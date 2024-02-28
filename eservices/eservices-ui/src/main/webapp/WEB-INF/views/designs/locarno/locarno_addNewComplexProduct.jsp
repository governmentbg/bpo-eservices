<%@page import="eu.ohim.sp.common.ui.form.design.LocarnoComplexKindForm"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="singleProductValue" value="<%= LocarnoComplexKindForm.SINGLE_PRODUCT.getValue() %>" />
<c:set var="setCompositionValue" value="<%= LocarnoComplexKindForm.SET_COMPOSITION.getValue() %>" />

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
	 
 	<form:form id="formAddNewComplexProduct" cssClass="formEF" modelAttribute="locarnoComplexForm">
 		
 		<c:set var="sectionId" value="locarnoNewComplexProduct" scope="request" />

		<%-- For sending the id given to the locarno complex form when a product is edited. --%>
		
		<input type="hidden" id ="hiddenFormIdNC" value="${locarnoId}"/>
		<component:generic path="classes" checkRender="true">
		
			<c:set var="errorsClasses"><form:errors path="classes"></form:errors></c:set>
				<%--<!-- Set the error class if it is necessary -->--%>
    			<c:if test="${!empty errorsClasses}">
        		<c:set var="formTagClass" value="error"/>
    		</c:if>
			<div class="class-search">
			 	<label for="selectLocarnoComplexProductClass">
			 		<spring:message code="design.form.classes.modal.label.product.classification" />
			 		<span class="requiredTag">*</span>
			 	</label>
			 	
			 	<select name="locarnoClass" id="selectLocarnoClass">
			 		<option value="">
			 			<spring:message code="selectBox.SELECT" />
			 		</option>
			 		<c:forEach items="${flowBean.locarnoClasses}" var="locarnoClass">
			 			<option value="${locarnoClass.clazz}">${locarnoClass.clazz}</option>
			 		</c:forEach>
			 	</select>
			 	
			 	<select name="locarnoSubclass" id="selectLocarnoSubclass">
			 		
				</select>
			</div>	
			<div class="class-search-button">
				<button id="buttonAddLocarnoComplexProductClass" class="btn" type="button">
					<spring:message code="design.form.classes.model.button.add" />
				</button>
 			</div>

        	<c:if test="${!empty errorsClasses}">
       			<c:forEach items="${errorsClasses}" var="message">
           			<p class="flMessageError" id="${partialId}ErrorMessageServer">${message}</p>
       			</c:forEach>
   			</c:if>
 			
 			<div>
	 			<label>
	        		<spring:message code="design.form.classes.modal.label.product.myClasses" /><span class="requiredTag">*</span>
	        	</label>
	        	
	        	<div class="addedClasses">
	        		<ul id="listAddedLocarnoComplexProductClasses">
	        			<c:forEach items="${locarnoComplexForm.classes}" var="locarnoClass">
	        				<li><input type="hidden" name="classes" value="${locarnoClass.classFormatted}" />${locarnoClass.classFormatted}<a class="remove-icon"></a></li>
	        			</c:forEach>
	        		</ul>
	        	</div>
	        	
		        <component:generic path="type" checkRender="true">
		        
					<c:set var="errorsType"><form:errors path="type"></form:errors></c:set>
					<%--<!-- Set the error class if it is necessary -->--%>
    				<c:if test="${!empty errorsType}">
        				<c:set var="formTagClass" value="error"/>
    				</c:if>
							        
					<div class="typeClasses">
			    		<ul>
							<li>
								<label for="singleProduct">
									<input type="radio" name="type" value="${singleProductValue}" <c:if test="${not empty locarnoComplexForm.type && locarnoComplexForm.type.value eq singleProductValue}">checked</c:if> id="singleProduct" />
									<spring:message code="design.form.classes.modal.label.product.type.product" />
								</label>
							</li>
							<li>
								<label for="setComposition">
									<input type="radio" name="type" value="${setCompositionValue}" <c:if test="${not empty locarnoComplexForm.type && locarnoComplexForm.type.value eq setCompositionValue}">checked</c:if> id="setComposition" />
									<spring:message code="design.form.classes.modal.label.product.type.setComposition" />
								</label>
							</li>
						</ul>
     				</div>
		       		<c:if test="${!empty errorsType}">
        				<c:forEach items="${errorsType}" var="message">
            				<p class="flMessageError" id="${partialId}ErrorMessageServer">${message}</p>
        				</c:forEach>
    				</c:if>
				</component:generic>
		
			</div>

		</component:generic>
		
		<component:input path="indication" checkRender="true" labelTextCode="design.form.classes.modal.label.product.myIndication" />
 		
    </form:form>
    
</section>
    
<footer>
    <ul>
        <li><a data-dismiss="modal"><spring:message code="design.form.classes.model.footer.buttonCancel" /></a></li>
        <li><button id="buttonSaveAddNewComplexProduct"><spring:message code="design.form.classes.model.footer.buttonSave" /></button></li>
    </ul>
</footer>
</div>
</div>