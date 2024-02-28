<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:form id="errorBoxForm" modelAttribute="flowBean">
	<c:set var="errors"><form:errors path="*" /></c:set>
	<c:if test="${!empty errors}">
		<div id="errorBox">
			<p class="header">
				<spring:message code="general.errorBox.header" />
			</p>
			<p class="closeButton">
				<a href="#"><spring:message code="general.errorBox.closeBox" /></a>
			</p>
			<p>
				<spring:message code="general.errorBox.title" />
			</p>
			<dl>
						
<!-- 			READ THE POSSIBLE ERRORS IN EACH SECTION (IF THERE ARE NO ERRORS FOR THIS SPECIFIC SECTION, NOTHING IS DISPLAYED FOR IT -->
	<!-- 			CLAIM SECTION -->
				<c:set var="errors"><form:errors path="mainForm.commonApplicationSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dd>${errors}</dd>
				</c:if>

				<c:set var="errors"><form:errors path="mainForm.claimSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#claim"><spring:message code="general.errorBox.claim" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			REFERENCE SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.referenceSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#markreference"><spring:message code="general.errorBox.reference" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			MARK REPRESENTATION SECTION -->
				<c:set var="errors"><form:errors path="mainForm.markRepresentationSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#typemark"><spring:message code="general.errorBox.markRepresentation.${flowModeId}" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			FOREIGN REGISTRATION SECTION -->
				<c:set var="errors"><form:errors path="mainForm.foreignRegistrationDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#foreignRegistration"><spring:message code="general.errorBox.foreignRegistration" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			PERSONAL DATA SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.personalDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<c:set var="person_section" value="representative"/>
					<c:choose>
						<c:when test="${errors.contains('applicant')}"><c:set var="person_section" value="applicant"/></c:when>
						<c:when test="${errors.contains('representative')}"><c:set var="person_section" value="representative"/></c:when>
					</c:choose>
					<dt>
						<a href="#${person_section}"><spring:message code="general.errorBox.personalData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				<!-- CORRESPONDANCE ADDRESS DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.applicantionCADataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#applicationCA"><spring:message code="general.errorBox.applicationCAData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			PAYMENT DETAILS SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.paymentDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#paymentSection"><spring:message code="general.errorBox.paymentData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>				
	<!-- 			LANGUAGE SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.languageSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#languages"><spring:message code="general.errorBox.language" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			SIGNATURE DATA SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.signatureSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#signature"><spring:message code="general.errorBox.signature" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			GOODS AND SERVICES SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.gsSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#goodsandservices"><spring:message code="general.errorBox.goodsAndServices.${flowModeId}" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			GOODS DESCRIPTION SECTION -->
				<c:set var="errors"><form:errors path="mainForm.goodsDescriptionDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#goodsAndServicesDescriptions"><spring:message code="general.errorBox.goodsDescription" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
<!-- 			DIVISIONAL APPLICATION SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.divisionalSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#divisionalApplicationSection"><spring:message code="general.errorBox.divisionalApplication" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			OTHER ATTACHMENTS SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.otherAttachments*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#otherAttachments"><spring:message code="general.errorBox.otherAttachments" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
			</dl>
		</div> 
	</c:if>
</form:form>