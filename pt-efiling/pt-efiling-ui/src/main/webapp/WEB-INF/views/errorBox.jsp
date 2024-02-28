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
					<dd>${errors}</dd>
				</c:if>

				<!-- PATENT DETAILS SECTION -->
				<c:set var="errors"><form:errors path="mainForm.spcPatentSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#spcPatentSection"><spring:message code="general.errorBox.patent.spc" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>

				<!-- PATENT SECTION -->
				<c:set var="errors"><form:errors path="mainForm.patentSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#patentSection"><spring:message code="general.errorBox.patent" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>

				<!-- TECHNICAL QUESTIONNAIRE SECTION -->
				<c:set var="errors"><form:errors path="mainForm.technicalQuestionnaireSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#technicalQuestionnaireSection"><spring:message code="general.errorBox.technicalQuestionnaire" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
				
				<!-- CLAIM SECTION -->
				<c:set var="errors"><form:errors path="mainForm.claimSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#claim"><spring:message code="general.errorBox.claim" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>

				
				<!-- APPLICANT DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.applicantDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#applicant"><spring:message code="general.errorBox.applicantData" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
				
				
				<!-- REPRESENTATIVE DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.representativeDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#representative"><spring:message code="general.errorBox.representativeData" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
				
				
				<!-- CORRESPONDANCE ADDRESS DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.applicantionCADataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#applicationCA"><spring:message code="general.errorBox.applicationCAData" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
				
				
				<!-- INVENTOR DATA SECTION -->
				<c:set var="errors"><form:errors path="mainForm.inventorDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#inventors"><spring:message code="general.errorBox.${flowModeId == 'sv-efiling' ? 'authorData' : 'inventorData'}" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>

				
				<!-- SUPPLEMENTARY INFORMATION SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.otherAttachments*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#otherAttachments"><spring:message code="general.errorBox.otherAttachments" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>


				<!-- SIGNATURE DATA SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.signatureSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#signature"><spring:message code="general.errorBox.signature" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
				
				
				<!-- TERMS AND CONDITIONS SECTION -->
				<c:set var="errors"><form:errors path="mainForm.termsAndConditionsSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#termsAndConditions"><spring:message code="general.errorBox.termsAndConditions" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
				
				
				<!-- PAYMENT DETAILS SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.paymentDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#paymentSection"><spring:message code="general.errorBox.paymentData" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>	
				
				<!-- ENTITLEMENT SECTION -->
				<c:set var="errors"><form:errors path="mainForm.entitlementSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#entitlement"><spring:message code="general.errorBox.entitlement" /></a>
					</dt>
					<dd>${errors}</dd>
				</c:if>
			
			</dl>
		</div> 
	</c:if>
</form:form>