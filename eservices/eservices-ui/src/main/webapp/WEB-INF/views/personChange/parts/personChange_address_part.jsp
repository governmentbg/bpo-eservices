<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<input type="hidden" id="maxCorrespondenceAddresses"
       value="${configurationServiceDelegator.getValue('person.personChange.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">

<jsp:include page="/WEB-INF/views/partials/address.jsp">
    <jsp:param name="path" value="address"/>
    <jsp:param name="itemFilterValue" value="isRepresentative"/>
</jsp:include>

<component:generic path="correspondenceAddresses" checkRender="true">

    <label class="correspondence-address hidden" for="representativeHasCorrespondenceAddress">
        <input id="representativeHasCorrespondenceAddress" name="hasCorrespondenceAddress" type="checkbox">
        <span><spring:message code="person.field.wantCorrespondenceAddress"/></span>
    </label>


    <jsp:include page="/WEB-INF/views/partials/correspondenceAddress.jsp">
        <jsp:param name="path" value="correspondenceAddresses"/>
        <jsp:param name="itemFilterValue" value="isRepresentative"/>
        <jsp:param name="size" value="${personChangeForm.correspondenceAddresses.size()}"/>
    </jsp:include>
</component:generic>

<jsp:include page="/WEB-INF/views/partials/contactdetails.jsp"/>

<div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
    <component:file pathFilename="fileDocumentAttachment" fileWrapperPath="representativeAttachment" labelCode="representative.naturalPerson.field.attachment"
                    fileWrapper="${personChangeForm.representativeAttachment}"/>
</div>
