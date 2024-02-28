<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%--<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>--%>
<%@ taglib uri="/WEB-INF/tags/component/fsp-tags.tld" prefix="tags" %>

<section class="patent">

    <form:form id="ptDetailsForm" cssClass="formEF" modelAttribute="patentDetailsForm">
        <c:set var="sectionId" value="patent_details" scope="request"/>
        <c:set var="imported" value="${patentDetailsForm.imported}" scope="request"/>


        <input type="hidden" id="formReturned" value="true"/>

        <form:hidden id="hiddenFormId" path="id"/>
        <form:hidden id="importedId" path="imported"/>
        <form:hidden id="patentGroupStatus" path="patentGroupStatus"/>
        <form:hidden id="expirationDate" path="expirationDate"/>
        <form:hidden id="entitlementDate" path="entitlementDate"/>
        <form:hidden id="unpublishedHidden" path="unpublished"/>

        <component:generic path="formWarnings" checkRender="false">
            <c:set var="errors">
                <form:errors path="formWarnings"></form:errors>
            </c:set>
            <c:if test="${!empty errors}">
                <p class="flMessageError permanentMessage" id="patentErrorMessageServer">${errors}</p>
            </c:if>
        </component:generic>

        <div style="display: block">
            <div class="inline-fields half_pct">
                <component:input path="applicationNumber" labelTextCode="patent.form.field.applicationNumber.spc"
                                 formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
                    <%--                checkRender="true"--%>
            </div>
            <div class="right-fields">
                <component:input path="applicationDate" labelTextCode="patent.form.field.applicationDate.spc"
                                 formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
            </div>
        </div>
        <div style="display: block">
            <div class="inline-fields half_pct">
                <component:input path="registrationNumber" labelTextCode="patent.form.field.registrationNumber.spc"
                                 formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
            </div>
            <div class="right-fields">
                <component:input path="registrationDate" labelTextCode="patent.form.field.registrationDate.spc"
                                 formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
            </div>
        </div>

        <div style="display: block">
            <div class="inline-fields half_pct">
                <component:input path="patentCurrentStatus" labelTextCode="patent.form.field.patentCurrentStatus.spc"
                                 formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
            </div>
            <div class="right-fields">
                <component:input path="patentCurrentStatusDate"
                                 labelTextCode="patent.form.field.patentCurrentStatusDate.spc"
                                 formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
            </div>
        </div>

        <component:textarea path="patentTitle" labelTextCode="patent.form.field.patentTitle.spc"
                            formTagClass="description-textarea" readOnly="true" checkRender="true"></component:textarea>

        <component:textarea path="patentTitleSecondLang" labelTextCode="patent.form.field.patentTitleSecondLang.spc"
                            formTagClass="description-textarea" readOnly="true" checkRender="true"></component:textarea>

        <component:textarea path="patentAbstract" labelTextCode="patent.form.field.patentAbstract.spc"
                            formTagClass="description-textarea" readOnly="true" checkRender="true"></component:textarea>

        <component:textarea path="patentAbstractSecondLang"
                            labelTextCode="patent.form.field.patentAbstractSecondLang.spc"
                            formTagClass="description-textarea" readOnly="true" checkRender="true"></component:textarea>

        <hr>
        <h3><spring:message code="ep_pt.form.owners.title.spc"></spring:message></h3>
        <c:set var="i" value="0"/>
        <c:forEach var="owner" items="${patentDetailsForm.applicants}" varStatus="app">
            <label>${owner.name} (${owner.id})</label>
            <component:textarea path="applicants[${i}].domicile" readOnly="true" formTagClass="owner-fields-long"/>
            <form:hidden id="applicants[${i}].imported" path="applicants[${i}].imported"/>
            <form:hidden id="applicants[${i}].id" path="applicants[${i}].id"/>
            <form:hidden id="applicants[${i}].name" path="applicants[${i}].name"/>
            <form:hidden id="applicants[${i}].domicileCountry" path="applicants[${i}].domicileCountry"/>
            <form:hidden id="applicants[${i}].contactPerson" path="applicants[${i}].contactPerson"/>
            <form:hidden id="applicants[${i}].internalApplicantId" path="applicants[${i}].internalApplicantId"/>

            <form:hidden id="applicants[${i}].address.street" path="applicants[${i}].address.street"/>
            <form:hidden id="applicants[${i}].address.houseNumber" path="applicants[${i}].address.houseNumber"/>
            <form:hidden id="applicants[${i}].address.city" path="applicants[${i}].address.city"/>
            <form:hidden id="applicants[${i}].address.stateprovince" path="applicants[${i}].address.stateprovince"/>
            <form:hidden id="applicants[${i}].address.postalCode" path="applicants[${i}].address.postalCode"/>
            <form:hidden id="applicants[${i}].address.country" path="applicants[${i}].address.country"/>

            <form:hidden id="applicants[${i}].phone" path="applicants[${i}].phone"/>
            <form:hidden id="applicants[${i}].fax" path="applicants[${i}].fax"/>
            <form:hidden id="applicants[${i}].email" path="applicants[${i}].email"/>
            <form:hidden id="applicants[${i}].website" path="applicants[${i}].website"/>
            <form:hidden id="applicants[${i}].nationalOfficialBusinessRegister"
                         path="applicants[${i}].nationalOfficialBusinessRegister"/>
            <form:hidden id="applicants[${i}].receiveCorrespondenceViaEmail"
                         path="applicants[${i}].receiveCorrespondenceViaEmail"/>
            <form:hidden id="applicants[${i}].taxIdNumber" path="applicants[${i}].taxIdNumber"/>
            <form:hidden id="applicants[${i}].consentForPublishingPersonalInfo"
                         path="applicants[${i}].consentForPublishingPersonalInfo"/>
            <form:hidden id="applicants[${i}].partOfEEA" path="applicants[${i}].partOfEEA"/>
            <form:hidden id="applicants[${i}].type" path="applicants[${i}].type"/>
            <form:hidden id="applicants[${i}].currentUserIndicator" path="applicants[${i}].currentUserIndicator"/>

            <c:set var="i" value="${i+1}"/>
        </c:forEach>

        <hr>
        <h3><spring:message code="form.representatives.title.spc"></spring:message></h3>
        <c:set var="i" value="0"/>
        <c:forEach var="repr" items="${patentDetailsForm.representatives}" varStatus="r">
            <label>${repr.name} (${repr.id})</label>
            <form:hidden id="representatives[${i}].imported" path="representatives[${i}].imported"/>
            <form:hidden id="representatives[${i}].id" path="representatives[${i}].id"/>
            <form:hidden id="representatives[${i}].name" path="representatives[${i}].name"/>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>

        <hr>
        <h3><spring:message code="form.inventors.title.spc"></spring:message></h3>
        <c:set var="i" value="0"/>
        <c:forEach var="inventor" items="${patentDetailsForm.inventors}" varStatus="inv">
            <label>${inventor.name} (${inventor.id})</label>
            <form:hidden id="inventors[${i}].imported" path="inventors[${i}].imported"/>
            <form:hidden id="inventors[${i}].id" path="inventors[${i}].id"/>
            <form:hidden id="inventors[${i}].firstName" path="inventors[${i}].firstName"/>
            <form:hidden id="inventors[${i}].middleName" path="inventors[${i}].middleName"/>
            <form:hidden id="inventors[${i}].surname" path="inventors[${i}].surname"/>
            <form:hidden id="inventors[${i}].address.street" path="inventors[${i}].address.street"/>
            <form:hidden id="inventors[${i}].address.houseNumber" path="inventors[${i}].address.houseNumber"/>
            <form:hidden id="inventors[${i}].address.city" path="inventors[${i}].address.city"/>
            <form:hidden id="inventors[${i}].address.stateprovince" path="inventors[${i}].address.stateprovince"/>
            <form:hidden id="inventors[${i}].address.postalCode" path="inventors[${i}].address.postalCode"/>
            <form:hidden id="inventors[${i}].address.country" path="inventors[${i}].address.country"/>
            <form:hidden id="inventors[${i}].phone" path="inventors[${i}].phone"/>
            <form:hidden id="inventors[${i}].fax" path="inventors[${i}].fax"/>
            <form:hidden id="inventors[${i}].email" path="inventors[${i}].email"/>
            <form:hidden id="inventors[${i}].website" path="inventors[${i}].website"/>
            <form:hidden id="inventors[${i}].nationalOfficialBusinessRegister"
                         path="inventors[${i}].nationalOfficialBusinessRegister"/>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>

        <hr>
        <h3><spring:message code="form.ipc.title.spc"></spring:message></h3>
        <c:set var="i" value="0"/>
        <c:forEach var="ipcClass" items="${patentDetailsForm.ipcClasses}" varStatus="ipc">
            <label>${ipcClass}</label>
            <form:hidden id="ipcClasses[${i}]" path="ipcClasses[${i}]"/>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>
    </form:form>
</section>
