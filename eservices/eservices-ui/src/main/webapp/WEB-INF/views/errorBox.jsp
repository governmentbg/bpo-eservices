<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
						<a href="#claim"><spring:message code="general.errorBox.basis" /></a>
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
	<!-- 			GENERIC CHANGE TYPE SECTION -->
				<c:set var="errors"><form:errors path="changeType*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#changetype"><spring:message code="general.errorBox.requestType" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			APPEAL SECTION -->
				<c:set var="errors"><form:errors path="mainForm.appealSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#appealkind"><spring:message code="general.errorBox.appeal" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
				<!-- 			DECLARATION SECTION -->
				<c:set var="errors"><form:errors path="mainForm.smallCompanyFiles*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#declaration"><spring:message code="general.errorBox.declaration" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			MARK REPRESENTATION SECTION -->
				<c:set var="errors"><form:errors path="mainForm.markRepresentationSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#tm_section"><spring:message code="general.errorBox.markRepresentation" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

				<c:set var="errors"><form:errors path="mainForm.patentSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#pt_section"><spring:message code="general.errorBox.patentSection.${formUtil.getMainType()}" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

                <c:set var="errors"><form:errors path="mainForm.securityMeasureSection*" /></c:set>
                <c:if test="${!empty errors}">
                    <dt>
                        <a href="#securitymeasure"><spring:message code="general.errorBox.securityMeasureSection" /></a>
                    </dt>
                    <c:forEach items="${errors}" var="message">
                        <dd>${message}</dd>
                    </c:forEach>
                </c:if>

				<c:set var="errors"><form:errors path="marketPermission*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#marketPermission"><spring:message code="general.errorBox.marketPermission" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

                <c:set var="errors"><form:errors path="mainForm.licenceDataSection*" /></c:set>
                <c:if test="${!empty errors}">
                    <dt>
                        <a href="#licence"><spring:message code="general.errorBox.licence" /></a>
                    </dt>
                    <c:forEach items="${errors}" var="message">
                        <dd>${message}</dd>
                    </c:forEach>
                </c:if>

                <c:set var="errors"><form:errors path="mainForm.gsHelperDataSection*" /></c:set>
                <c:if test="${!empty errors}">
                    <dt>
                        <a href="#gshelper"><spring:message code="general.errorBox.gshelper" /></a>
                    </dt>
                    <c:forEach items="${errors}" var="message">
                        <dd>${message}</dd>
                    </c:forEach>
                </c:if>

                <c:set var="errors"><form:errors path="mainForm.groundsSection*" /></c:set>
                <c:if test="${!empty errors}">
                    <dt>
                        <a href="#opposition_basis_section"><spring:message code="general.errorBox.grounds" /></a>
                    </dt>
                    <c:forEach items="${errors}" var="message">
                        <dd>${message}</dd>
                    </c:forEach>
                </c:if>


				<!-- DESIGN SECTION -->
				<c:set var="errors"><form:errors path="mainForm.designSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#ds_section"><spring:message code="general.errorBox.designSection" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

				<!-- DESIGNER SECTION -->
				<c:set var="errors"><form:errors path="mainForm.designerSection*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#designers"><spring:message code="general.errorBox.designerSection" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

	<!-- 			PERSONAL DATA SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.personalDataSection*" /></c:set>
				<c:if test="${!empty errors}">
					<c:set var="person_section" value="${functions:getPersonDataSetionId(flowModeId)}"/>
					<dt>
						<a href="#${person_section}"><spring:message code="general.errorBox.personalData" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			REQUEST PERSON CHANGE SECTION -->
				<c:set var="errors"><form:errors path="personChanges*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#personChange"><spring:message code="general.errorBox.requestForChange" /></a>
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
						<a href="#goodsandservices"><spring:message code="general.errorBox.goodsAndServices" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
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
<!-- 			USERDOC SECTION -->
				<c:set var="errors"><form:errors path="mainForm.userdoc*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<a href="#userdoc"><spring:message code="general.errorBox.userdoc" /></a>
					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>
	<!-- 			OTHER ATTACHMENTS SECTION -->	
				<c:set var="errors"><form:errors path="mainForm.otherAttachments*" /></c:set>
				<c:if test="${!empty errors}">
					<dt>
						<c:choose>
							<c:when test="${fn:endsWith(flowModeId, '-generic')}">
								<a href="#otherAttachments"><spring:message code="general.errorBox.otherAttachments.generic" /></a>
							</c:when>
							<c:otherwise>
								<a href="#otherAttachments"><spring:message code="general.errorBox.otherAttachments" /></a>
							</c:otherwise>
						</c:choose>

					</dt>
					<c:forEach items="${errors}" var="message">
						<dd>${message}</dd>
					</c:forEach>
				</c:if>

                <c:set var="errors"><form:errors path="mainForm.applicantionCADataSection*" /></c:set>
                <c:if test="${!empty errors}">
                    <dt>
                        <a href="#applicationCA"><spring:message code="general.errorBox.applicationCA" /></a>
                    </dt>
                    <c:forEach items="${errors}" var="message">
                        <dd>${message}</dd>
                    </c:forEach>
                </c:if>

			</dl>
		</div> 
	</c:if>
</form:form>