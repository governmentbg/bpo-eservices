<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<jsp:include page="transformation_section_set.jsp"/>

<c:set var="importVisible" value="false"></c:set>
<component:generic path="importTransformation" checkRender="true">
    <c:set var="importVisible" value="true"/>

    <sec:authorize access="hasRole('Transformation_Import')" var="security_transformation_import" />
    <sec:authorize access="hasRole('Transformation_Autocomplete')" var="security_transformation_search" />
    <c:set var="service_transformation_import" value="${configurationServiceDelegator.isServiceEnabled('Transformation_Import')}"/>
    <c:set var="service_transformation_search" value="${configurationServiceDelegator.isServiceEnabled('Transformation_Autocomplete')}"/>
    <c:set var="service_transformation_manual" value="${configurationServiceDelegator.isServiceEnabled('Transformation_Manual')}" />

    <c:set var="security_service_transformation_import"
           value="${(security_transformation_import || !configurationServiceDelegator.securityEnabled) && service_transformation_import}"/>

    <c:if test="${security_service_transformation_import}">
        <input type="hidden" id="previousPatentTransformation_viewUrl"
               value="${configurationServiceDelegator.getValueFromGeneralComponent("service.patent.details.url")}"/>
        <span class="hidden" id="previousPatentTransformation_viewMessage"><spring:message
                code="person.autocomplete.action.viewDetails"/></span>

        <component:import autocompleteUrl="autocompletePatent.htm?office=${country}&flowModeId=pt-"
                          autocompleteServiceEnabled="Transformation_Autocomplete"
                          permissionAutocomplete="${security_transformation_search || !configurationServiceDelegator.securityEnabled}"
                          minimumCharAutocomplete="minimum.characters.autocomplete"
                          id="importTransformation"
                          importButtonTextCode="claim.form.action.import"
                          labelTextCode="claim.transformation.field.import"
                          importButtonClass="btn importTransformation"
                          component="general"
                          textCodeWhenEmpty="claim.transformation.import.whenEmpty"
                          importTextfieldClass="claim-id-input importPatentInput" />
        <input type="hidden" id="transformation_importId" value=""/>
        <input type="hidden" id="transformation_importCall" value="${importTransformationCall}"/>
    </c:if>

    <c:if test="${service_transformation_import && (security_transformation_import || !configurationServiceDelegator.securityEnabled) && service_transformation_manual}">
        <span class="separator"><spring:message code="claim.title.or" /></span>
    </c:if>

    <c:if test="${service_transformation_manual}">
        <button type="button" id="createManualTransformation" class="create-manually-button">
            <i class="create-icon"></i>
            <spring:message code="claim.action.manually" />
        </button>
    </c:if>

    <c:if test="${security_service_transformation_import}">
        <c:if test="${sectionId  == 'national_transformation'}">
            <br>
            <span class="tip"><spring:message code="claim.national_transformation.import.hint"/></span>
        </c:if>
        <c:if test="${sectionId  == 'conversion'}">
            <br>
            <span class="tip"><spring:message code="claim.conversion.import.hint"/></span>
        </c:if>
    </c:if>
</component:generic>

<c:if test="${importVisible == false}">
    <script type="text/javascript">
        callTransformationFormImmediately('${country}');
    </script>
</c:if>