<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div>
    <c:set var="userDataApplicantSize" value="0"/>
    <c:set var="prevImportedNatural" value="false"/>
    <c:set var="prevImportedLegal" value="false"/>

    <c:if test="${(flowModeId == 'tm-opposition' || flowModeId == 'tm-invalidity' || flowModeId == 'ds-invalidity' || flowModeId == 'pt-invalidity' || flowModeId == 'spc-invalidity' || flowModeId == 'um-invalidity') && flowBean.getApplicantsCount() > 1}">
        <div class="alert alert-info">
            <spring:message code="applicant.more.fees.${flowModeId }"/>
        </div>
    </c:if>

    <c:if test="${not empty flowBean.userDataApplicants}">
        <h4><spring:message code="person.table.title.yourData"/></h4>
        <table id="userDataApplicants" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            
            <c:set var="i" value="0"/>
            <c:forEach var="applicant" items="${flowBean.userDataApplicants}" varStatus="applicantId">
            	
            	
            	<c:choose>
				    <c:when test="${applicant.type eq 'NATURAL_PERSON' || applicant.type eq 'NATURAL_PERSON_SPECIAL'}">
				        <c:set var="prevImportedNatural" value="true"/>
				    </c:when>
				    <c:when test="${applicant.type eq 'LEGAL_ENTITY'}">
				        <c:set var="prevImportedLegal" value="true"/>
				    </c:when>
				</c:choose>
            	
                <c:set var="i" value="${i+1}"/>
                <tr id="applicant_id_${applicant.id}">
                    <jsp:include page="/WEB-INF/views/applicant/applicant_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${applicant.id}" name="applicantId"/>
                        <jsp:param value="${applicant.name}" name="applicantName"/>
                        <jsp:param value="${applicant.type}" name="applicantType"/>
                        <jsp:param value="${applicant.address.country}" name="applicantCountry"/>
                        <jsp:param value="${applicant.imported}" name="applicantIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
            <c:set var="userDataApplicantSize" value="${i}"/>
        </table>
    </c:if>
    <c:if test="${not empty flowBean.addedApplicants}">
        <h4><spring:message code="person.table.title.applicant"/></h4>
        <table id="addedApplicants" class="table">

            <tr>
                <th><spring:message code="person.table.header.number"/><a class="sorter" data-val='number'/></th>
                <!-- COMMENTED TO HIDE ID (ONLY WORKS ON IMPORTED APPLICANTS)
                <th><spring:message code="person.table.header.id"/><a class="sorter" data-val='id'/></th>
                -->
                <th><spring:message code="person.table.header.type"/><a class="sorter" data-val='type'/></th>
                <th><spring:message code="person.table.header.name"/><a class="sorter" data-val='name'/></th>
                <th><spring:message code="person.table.header.country"/><a class="sorter" data-val='country'/></th>
                <th><spring:message code="person.table.header.options"/></th>
            </tr>
            <c:set var="i" value="${userDataApplicantSize}"/>
            <c:forEach var="applicant" items="${flowBean.addedApplicants}" varStatus="applicantId">
            	<c:if test="${applicant.imported eq true}">
	            	<c:choose>
					    <c:when test="${applicant.type eq 'NATURAL_PERSON' || applicant.type eq 'NATURAL_PERSON_SPECIAL'}">
					        <c:set var="prevImportedNatural" value="true"/>
					    </c:when>
					    <c:when test="${applicant.type eq 'LEGAL_ENTITY'}">
					        <c:set var="prevImportedLegal" value="true"/>
					    </c:when>
					</c:choose>
				</c:if>
                <c:set var="i" value="${i+1}"/>
                <tr id="applicant_id_${applicant.id}">
                    <jsp:include page="/WEB-INF/views/applicant/applicant_card.jsp">
                        <jsp:param value="${i}" name="rowId"/>
                        <jsp:param value="${applicant.id}" name="applicantId"/>
                        <jsp:param value="${applicant.name}" name="applicantName"/>
                        <jsp:param value="${applicant.type}" name="applicantType"/>
                        <jsp:param value="${applicant.address.country}" name="applicantCountry"/>
                        <jsp:param value="${applicant.imported}" name="applicantIsImported"/>
                    </jsp:include>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    <c:set var="sectionId" value="applicant" scope="request"/>

    <c:if test="${configurationServiceDelegator.hasSelectPersonButton(flowModeId, sectionId)}">
        <input type="hidden" class="updatableParent" value="#applicantCardList"/>
        <button style="margin-top:10px" type="button" class="btn fileinput-button personSelectButton" data-section="${sectionId}">
            <i class="list-icon"></i> <spring:message code="person.select.button"/>
        </button>
    </c:if>
</div>