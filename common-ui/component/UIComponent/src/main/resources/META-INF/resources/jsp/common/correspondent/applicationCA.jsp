<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<form:form id="applicationCAForm" cssClass="formEF" modelAttribute="applicationCAForm">
	<c:set var="sectionId" value="applicationCA" scope="request" />
	<input type="hidden" id="formReturned" value="true"/>	
	<form:hidden id="hiddenFormId" path="id"/>
		
		<div id="correspondentButtons">
		<c:set var="showLoadButton" value="false"/>
		<span>
			<c:set var="person" />
			<c:choose>
				<c:when test="${fn:endsWith(flowModeId, '-change')}">
					<c:if test="${not empty flowBean.holders}">
						<c:set var="showLoadButton" value="true"/>
						<component:personSelect items="${flowBean.holders}"
												path="person" itemLabel="name"
												itemValue="id"
												id="person"
												divClass="select-persons"
												labelTextCode="applicationCA.form.persons"/>
					</c:if>
				</c:when>
				<c:when test="${fn:endsWith(flowModeId, '-transfer') || fn:endsWith(flowModeId, '-licence') || fn:endsWith(flowModeId, '-compulsorylicence') || fn:endsWith(flowModeId, '-rem')  || fn:endsWith(flowModeId, '-bankruptcy')
						|| fn:endsWith(flowModeId, '-security')}">
					<c:if test="${not empty flowBean.assignees}">
						<c:set var="showLoadButton" value="true"/>
						<component:personSelect items="${flowBean.assignees}"
												path="person" itemLabel="name"
												itemValue="id"
												id="person"
												divClass="select-persons"
												labelTextCode="applicationCA.form.persons"/>
					</c:if>
				</c:when>
				<c:when test="${flowModeId eq 'tm-changerep'}">
					<c:if test="${not empty flowBean.tmOwnersAndRepresentatives}">
						<c:set var="showLoadButton" value="true"/>
						<component:personSelect items="${flowBean.tmOwnersAndRepresentatives}"
												path="person" itemLabel="name"
												itemValue="id"
												id="person"
												divClass="select-persons"
												labelTextCode="applicationCA.form.persons"/>
					</c:if>
				</c:when>
				<c:when test="${flowModeId eq 'ds-changerep'}">
					<c:if test="${not empty flowBean.dsOwnersAndRepresentatives}">
						<c:set var="showLoadButton" value="true"/>
						<component:personSelect items="${flowBean.dsOwnersAndRepresentatives}"
												path="person" itemLabel="name"
												itemValue="id"
												id="person"
												divClass="select-persons"
												labelTextCode="applicationCA.form.persons"/>
					</c:if>
				</c:when>
				<c:when test="${fn:endsWith(flowModeId, '-changeca')}">
					<c:if test="${not empty flowBean.changeCAPersons}">
						<c:set var="showLoadButton" value="true"/>
						<component:personSelect items="${flowBean.changeCAPersons}"
												path="person" itemLabel="name"
												itemValue="id"
												id="person"
												divClass="select-persons"
												labelTextCode="applicationCA.form.persons"/>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty flowBean.persons}">
						<c:set var="showLoadButton" value="true"/>
						<component:personSelect items="${flowBean.persons}"
												path="person" itemLabel="name"
												itemValue="id"
												id="person"
												divClass="select-persons"
												labelTextCode="applicationCA.form.persons"/>
					</c:if>
				</c:otherwise>

			</c:choose>
		</span>
			<c:if test="${showLoadButton}">
				<button id="applicationCACreateExist" type="button">
					<i class="create-icon"></i>
					<spring:message code="applicationCA.form.action.load"/>
				</button>
				<span class="separator"><spring:message code="person.form.separator.or"/></span>

			</c:if>
	    <button id="applicationCACreateButton" class="create-new-button" type="button">
	        <i class="create-icon"></i>
	        <spring:message code="applicant.form.action.createNew"/>
	    </button>	
	        
        <hr>
		</div>
		<div id="newApplicationCA" style="display: none">
			<jsp:include page="correspondenceAddressItem.jsp">
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

