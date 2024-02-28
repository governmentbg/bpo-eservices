<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="showHolderLegalEntity" value="false"/>
<c:set var="showHolderNaturalPerson" value="false"/>
<c:if test="${not empty configurationServiceDelegator.getHolderTypes(flowModeId)}">
    <c:forEach var="section_item" items="${configurationServiceDelegator.getHolderTypes(flowModeId)}">
        <c:if test="${section_item eq 'holder_legalentity'}">
            <c:set var="showHolderLegalEntity" value="true"/>
        </c:if>
        <c:if test="${section_item eq 'holder_naturalperson'}">
            <c:set var="showHolderNaturalPerson" value="true"/>
        </c:if>
    </c:forEach>
</c:if>

<span class="importErrorPlaceholder"></span>

<c:set var="sectionId" value="holder" scope="request"/>
<input type="hidden" id="maxCorrespondenceAddresses"
       value="${configurationServiceDelegator.getValue('person.holder.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">
<%-- <input type="hidden" id="service_holderMatches_enabled"
       value="${configurationServiceDelegator.isServiceEnabled('Holder_Match')}"/> --%>

<div id="newHolder">
<%--c:if test="${empty flowBean.addedApplicants}" --%>	
<component:generic path="type" checkRender="true">	
    <label><spring:message code="holder.form.holderType"/><span class="requiredTag">*</span></label>
    <select name="htype" id="htype">
        <option value=""><spring:message code="holder.form.action.select"/></option>
        <c:if test="${showHolderLegalEntity}">
            <option value="LEGAL_ENTITY"><spring:message code="holder.legalEntity.type"/></option>
        </c:if>
        <c:if test="${showHolderNaturalPerson}">
            <option value="NATURAL_PERSON"><spring:message code="holder.naturalPerson.type"/></option>
        </c:if>
    </select>
</component:generic>
<%-- /c:if --%>
</div>

<div id="loadApplicant">
<%-- c:if test="${not empty flowBean.addedApplicants}" --%>	
<component:generic path="reuseApplicantData" checkRender="false">
	<div id="reuse-applicant-data">					
		<label><spring:message code="holder.import.select.import"/></label>
		<ul class="reuse-applicant-data">
			<li><label><input type="radio" name="reuseApplicant" value="true" checked id="reuseApplicantYES"><spring:message code="holder.import.yes"/></label></li>
			<li><label><input type="radio" name="reuseApplicant" value="false" id="reuseApplicantYES"><spring:message code="holder.import.no"/></label></li>
		</ul>
		<div id="selectApplicant">
		<label><spring:message code="holder.import.select"/><span class="requiredTag">*</span></label>
		<select name="selectedApplicant" id="selectedApplicantCombo">
            <c:forEach var="applicant" items="${flowBean.addedApplicants}">
            	<option value="${applicant.id}">${applicant.name}</option>
            </c:forEach>
	    </select>
	    </div>	 
    </div>
</component:generic>
<%-- /c:if --%>
</div>