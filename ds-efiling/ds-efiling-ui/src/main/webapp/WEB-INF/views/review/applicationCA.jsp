<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<form:form id="applicationCAForm" cssClass="formEF" modelAttribute="applicationCAForm">
	<c:set var="sectionId" value="applicationCA" scope="request" />
	<input type="hidden" id="formReturned" value="true"/>	
	<form:hidden id="hiddenFormId" path="id"/>
			
		<span>
			<c:set var="person" />
			<component:personSelect items="${flowBean.persons}"  
						path="person" itemLabel="name"
					  	itemValue="id"
					  	id="person"	
					  	divClass="select-persons"					
						labelTextCode="Persons"/>
		</span>
		<button id="applicationCACreateExist" type="button">
	        <i class="create-icon"></i>
	        <spring:message code="Load"/>
	    </button>	
		<span class="separator"><spring:message code="person.form.separator.or"/></span>
	    <button id="applicationCACreateButton" class="create-new-button" type="button">
	        <i class="create-icon"></i>
	        <spring:message code="applicant.form.action.createNew"/>
	    </button>	
	        
        <hr>
	
		<div id="newApplicationCA" style="display: none">
			<ul class="controls">
			    <li>
			        <a class="cancelButton applicationCA">
			        	<spring:message code="applicationCA.form.action.cancelAdd.top"/>
			        </a>
			    </li>
			    <li>		        	
			        <button class="addApplicationCA applicationCA" type="button">			            	
			            <spring:message code="applicationCA.form.action.save"/>
			        </button>
			    </li>
			</ul>    
			<jsp:include page="/WEB-INF/views/partials/correspondenceAddressItem.jsp">
		           <jsp:param name="path" value="correspondenceAddressForm"/>
				    <jsp:param name="itemFilterValue" value="isApplicant"/>
			</jsp:include>
			
			<br>
			
			<ul class="controls">
			    <li>
			        <a class="cancelButton applicationCA">
			        	<spring:message code="applicationCA.form.action.cancelAdd.top"/>
			        </a>
			    </li>
			    <li>		        	
			        <button class="addApplicationCA applicationCA" type="button">			            	
			            <spring:message code="applicationCA.form.action.save"/>
			        </button>
			    </li>
			</ul> 			
					
		</div>

</form:form>	   

