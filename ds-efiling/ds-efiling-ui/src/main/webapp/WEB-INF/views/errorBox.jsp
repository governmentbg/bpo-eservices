<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:form id="errorBoxForm" modelAttribute="flowBean">
	<c:set var="errors">
		<form:errors path="*" />
	</c:set>
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
				<!-- READ THE POSSIBLE ERRORS IN EACH SECTION (IF THERE ARE NO ERRORS FOR THIS SPECIFIC SECTION, NOTHING IS DISPLAYED FOR IT -->
				<c:set var="errors"><form:errors path="mainForm.commonApplicationSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dd>${errors}</dd>
				</c:if>

				<!-- LANGUAGE SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.languageSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#languages"><spring:message code="general.errorBox.language" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
				
				<!-- REFERENCE SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.referenceSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#designs_reference"><spring:message code="general.errorBox.reference" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
				
				<!-- DESIGNS SECTION -->
				<c:set var="errors"><form:errors path="mainForm.designDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#designsDetails"><spring:message code="general.errorBox.design" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
					
				<!-- DIVISIONAL APPLICATION SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.divisionalSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#divisionalApplicationSection"><spring:message code="general.errorBox.divisionalApplication" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
				<!-- DEFERMENT OF PUBLICATION SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.defermentofPublicationSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#defermentOfPublication"><spring:message code="general.errorBox.defermentofPublicationSection" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>				
				
				
				<!-- CLAIM SECTION -->
				<c:set var="errors"><form:errors path="mainForm.claimSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#claim"><spring:message code="general.errorBox.claim" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>


				<%--				
				<!-- PERSONAL DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.applicantDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#person"><spring:message code="general.errorBox.personalData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				--%>
				
				
				<!-- APPLICANT DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.applicantDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#applicant"><spring:message code="general.errorBox.applicantData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
				
				<!-- REPRESENTATIVE DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.representativeDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#representative"><spring:message code="general.errorBox.representativeData" /></a>
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
				
				
				<!-- DESIGNER DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.designerDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#designers"><spring:message code="general.errorBox.designerData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

				
				<!-- SUPPLEMENTARY INFORMATION SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.otherAttachments*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#otherAttachments"><spring:message code="general.errorBox.otherAttachments" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>


				<!-- SIGNATURE DATA SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.signatureSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#signature"><spring:message code="general.errorBox.signature" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
				
				<!-- TERMS AND CONDITIONS SECTION -->
				<c:set var="errors"><form:errors path="mainForm.termsAndConditionsSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#termsAndConditions"><spring:message code="general.errorBox.termsAndConditions" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				
				
				<!-- PAYMENT DETAILS SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.paymentDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#paymentSection"><spring:message code="general.errorBox.paymentData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>	
				
				<!-- ENTITLEMENT SECTION -->
				<c:set var="errors"><form:errors path="mainForm.entitlementSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#entitlement"><spring:message code="general.errorBox.entitlement" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
			
			</dl>
		</div> 
	</c:if>
</form:form>