<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.10.2022 г.
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<input type="hidden" id="maxCorrespondenceAddresses"
       value="${configurationServiceDelegator.getValue('person.representative.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">

<form:form id="representativeIntlPRepresentativeForm" modelAttribute="representativeIntlPRepresentativeForm" cssClass="formEF">
    <c:set var="imported" value="${representativeIntlPRepresentativeForm.imported}" scope="request"/>
    <c:set var="sectionId" value="representative_intlprepresentative" scope="request"/>

    <form:hidden path="imported" id="importedRepresentative"/>
    <input type="hidden" id="formReturned" value="true"/>
    <form:hidden id="hiddenFormId" path="id"/>

    <component:validateImport path="validateImported" />

    <input type="hidden" class="reptypehidden" value="${representativeIntlPRepresentativeForm.type}"/>

    <component:input path="firstName" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.firstName"/>
    <component:input path="middleName" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.middlename"/>
    <component:input path="surname" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.surname"/>
    <component:select items="${configurationServiceDelegator['nationalities']}" path="nationality" itemLabel="value"
                      itemValue="code" itemFilter="isRepresentative" checkRender="true"
                      labelTextCode="representative.naturalPerson.field.nationality"/>

    <br>

    <component:generic path="personIdentifierForm.afimi" checkRender="true">
        <div class="identify-info">
            <component:input path="personIdentifierForm.afimi" checkRender="true" formTagClass="identify-big" labelTextCode="person.field.afimi"/>
        </div>
    </component:generic>

    <component:generic path="personIdentifierForm.doy" checkRender="true">
        <div class="identify-info">
            <component:input path="personIdentifierForm.doy" checkRender="true" formTagClass="identify-big" labelTextCode="person.field.doy"/>
        </div>
    </component:generic>

    <component:input path="nationalOfficialBusinessRegister" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.nationalOfficialBusinessRegister"/>
    <component:input path="taxIdNumber" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.taxIdNumber"/>
    <component:input path="businessVatNumber" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.businessVatNumber"/>
    <component:input path="reference" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.reference"/>
    <component:input path="domicile" checkRender="true"
                     labelTextCode="representative.naturalPerson.field.domicile"/>
    <component:select items="${configurationServiceDelegator['countries']}" path="countryOfDomicile"
                      labelTextCode="representative.naturalPerson.field.countryOfDomicile" itemLabel="value"
                      itemValue="code" checkRender="true" itemFilter="isRepresentative"/>
    <component:checkbox path="consentForPublishingPersonalInfo" checkRender="true"
                        labelTextCode="representative.naturalPerson.field.consentForPublishingPersonalInfo"/>
    <jsp:include page="/WEB-INF/views/partials/address.jsp">
        <jsp:param name="path" value="address"/>
        <jsp:param name="itemFilterValue" value="isRepresentative"/>
    </jsp:include>
    <component:generic path="correspondenceAddresses" checkRender="true">

        <label class="correspondence-address" for="representativeHasCorrespondenceAddress">
            <input id="representativeHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
            <span><spring:message code="person.field.wantCorrespondenceAddress"/></span>
        </label>

        <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
            <jsp:param name="path" value="correspondenceAddresses"/>
            <jsp:param name="itemFilterValue" value="isRepresentative"/>
            <jsp:param name="size" value="${representativeIntlPRepresentativeForm.correspondenceAddresses.size()}"/>
        </jsp:include>
    </component:generic>

    <jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

    <div id="representativeAttachment" class="fileuploadInfo collectiveSelectors">
        <component:file pathFilename="representativeAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.naturalPerson.field.attachment"
                        fileWrapper="${representativeIntlPRepresentativeForm.representativeAttachment}"/>
    </div>

    <jsp:include page="/WEB-INF/views/partials/representative_pow_details.jsp" />

    <ul class="controls">
        <li>
            <a class="cancelButton representative"><spring:message
                    code="representative.form.action.cancelAdd.top"/></a>
        </li>
        <li>
            <button class="addRepresentative addRepresentativeIntlPRepresentative" type="button">
                <spring:message code="representative.form.action.save" />
            </button>
        </li>
    </ul>
</form:form>
