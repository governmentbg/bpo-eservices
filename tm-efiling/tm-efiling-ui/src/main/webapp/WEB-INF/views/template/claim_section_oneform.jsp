<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- SET A SERIES OF VARIABLES WHICH INFORMS WHETHER A RADIOBUTTON SET HAS TO BE SHOWN -->
<tiles:useAttribute id="sections_list" name="claim_content"
	classname="java.util.List" />
<c:set var="showPriority" value="false" />
<c:set var="showSeniority" value="false" />
<c:set var="showExhibition" value="false" />
<c:set var="showTransformation" value="false" />
<c:set var="showConversion" value="false" />

<c:if test="${not empty sections_list}">
	<c:forEach var="section_item" items="${sections_list}">
        <sec:authorize access="hasRole('Priority_Add')" var="security_priority" />
        <sec:authorize access="hasRole('Seniority_Add')" var="security_seniority" />
        <sec:authorize access="hasRole('Exhibition_Priority_Add')" var="security_exhibition" />
        <sec:authorize access="hasRole('Transformation_Add')" var="security_transformation" />
        <sec:authorize access="hasRole('Conversion_Add')" var="security_conversion" />

		<c:if
			test="${security_priority || !configurationServiceDelegator.securityEnabled}">
			<c:if
				test="${section_item eq '/WEB-INF/views/claim/claim_priority_section.jsp'}">
				<c:set var="showPriority" value="true" />
			</c:if>
		</c:if>
		<c:if
			test="${security_seniority || !configurationServiceDelegator.securityEnabled}">
			<c:if
				test="${section_item eq '/WEB-INF/views/claim/claim_seniority_section.jsp'}">
				<c:set var="showSeniority" value="true" />
			</c:if>
		</c:if>
		<c:if
			test="${security_exhibition || !configurationServiceDelegator.securityEnabled}">
			<c:if
				test="${section_item eq '/WEB-INF/views/claim/claim_exhibition_section.jsp'}">
				<c:set var="showExhibition" value="true" />
			</c:if>
		</c:if>
		<c:if
			test="${security_transformation || !configurationServiceDelegator.securityEnabled}">
			<c:if
				test="${section_item eq '/WEB-INF/views/claim/claim_transformation_section.jsp'}">
				<c:set var="showTransformation" value="true" />
			</c:if>
		</c:if>
		<c:if
			test="${security_conversion || !configurationServiceDelegator.securityEnabled}">
			<c:if
				test="${section_item eq '/WEB-INF/views/claim/claim_conversion_section.jsp'}">
				<c:set var="showConversion" value="true" />
			</c:if>
		</c:if>
	</c:forEach>
</c:if>

<section class="claim" id="claim">
	<a id="claim" class="anchorLink"></a>
	<header>
		<h2 class="claim">
			<spring:message code="claim.title" />
		</h2>
	</header>
	<form:form modelAttribute="flowBean.mainForm" id="claimSectionsForm"
		cssClass="mainForm">
		<div id="claimOptions">				
			<div class="claim-table">
				<c:if test="${showPriority}">
					<c:set var="sectionId" value="priority" scope="request" />
					<div class="claim-row">
						<div class="claim-label">
							<i class="claim-label-icon"></i>
							<spring:message code="claim.button.label.priority" />
							<span class="claim-count" id="prioritiesSize">(<fmt:formatNumber
									value="${flowBean.getPriorities().size()}"
									minIntegerDigits="2" />)
							</span>
						</div>
						<button class="claim-button" type="button" id="openPriority"
							name="addprio">
							<i class="claim-button-add"></i>
							<spring:message code="claim.button.add.priority" />
						</button>
						<component:generic path="claimPriority" checkRender="true">
							<label class="later" for="noPriority"><form:checkbox
									id="noPriority" path="claimPriority" /> <spring:message
									code="claim.button.later" /></label>
						</component:generic>
					</div>
				</c:if>

				<c:if test="${showSeniority}">
					<c:set var="sectionId" value="seniority" scope="request" />
					<div class="claim-row">
						<div class="claim-label">
							<i class="claim-label-icon"></i>
							<spring:message code="claim.button.label.seniority" />
							<span class="claim-count" id="senioritiesSize">(<fmt:formatNumber
									value="${flowBean.getSeniorities().size()}"
									minIntegerDigits="2" />)
							</span>
						</div>
						<button class="claim-button" type="button" id="openSeniority"
							name="addsenio">
							<i class="claim-button-add"></i>
							<spring:message code="claim.button.add.seniority" />
						</button>
					</div>
				</c:if>

				<c:if test="${showExhibition}">
					<c:set var="sectionId" value="exhibition" scope="request" />
					<div class="claim-row">
						<div class="claim-label">
							<i class="claim-label-icon"></i>
							<spring:message code="claim.button.label.exhibition" />
							<span class="claim-count" id="exhibitionsSize">(<fmt:formatNumber
									value="${flowBean.getExhpriorities().size()}"
									minIntegerDigits="2" />)
							</span>
						</div>
						<button class="claim-button" type="button" id="openExhibition"
							name="addexhib">
							<i class="claim-button-add"></i>
							<spring:message code="claim.button.add.exhibition" />
						</button>
						<component:generic path="claimExhibitionPriority"
							checkRender="true">
							<label class="later" for="noExhibition"><form:checkbox
									id="noExhibition" path="claimExhibitionPriority" /> <spring:message
									code="claim.button.later" /></label>
						</component:generic>

					</div>
				</c:if>

				<c:if test="${showTransformation}">
					<c:set var="sectionId" value="transformation" scope="request" />
					<div class="claim-row">
						<div class="claim-label">
							<i class="claim-label-icon"></i>
							<spring:message code="claim.button.label.transformation" />
							<span class="claim-count" id="transcount">(<fmt:formatNumber
									value="${flowBean.getTransformations().size()}"
									minIntegerDigits="2" />)
							</span>
						</div>
						<button class="claim-button" type="button"
							id="openTransformation" name="addtrans">
							<i class="claim-button-add"></i>
							<spring:message code="claim.button.add.transformation" />
						</button>
					</div>
				</c:if>
				<c:if test="${showConversion}">
					<c:set var="sectionId" value="conversion" scope="request" />
					<div class="claim-row">
						<div class="claim-label">
							<i class="claim-label-icon"></i>
							<spring:message code="claim.button.label.conversion" />
							<span class="claim-count" id="convcount">(<fmt:formatNumber
									value="${flowBean.getConversions().size()}"
									minIntegerDigits="2" />)
							</span>
						</div>
						<button class="claim-button" type="button"
							id="openConversion" name="addconv">
							<i class="claim-button-add"></i>
							<spring:message code="claim.button.add.conversion" />
						</button>
					</div>
				</c:if>
			</div>
		</div>
	</form:form>
	<div id="claimTableContainer">
		<jsp:include page="../claim/claim_table_common.jsp" />
	</div>

	<div class="claimSections tab-content">
	 	<tiles:useAttribute id="sections_list" name="claim_content" 
	 		classname="java.util.List" /> 
	 	<c:if test="${not empty sections_list}"> 
	 		<c:forEach var="section_item" items="${sections_list}"> 
	 			<tiles:insertAttribute value="${section_item}" /> 
	 		</c:forEach> 
	 	</c:if> 
 	</div>
</section>