<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 14.5.2019 г.
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="transformation claim" id="transformation">
    <spring:eval scope="request" var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
    <spring:eval scope="request" var="wipo_office" expression="@propertyConfigurer.getProperty('wipo.office')"/>

    <div class="claim-section-checkbox">
        <label for="transformationCheckbox" class="claim-label-checkbox"><spring:message code="transformation.section.checkbox.${flowModeId}"/> </label>
        <input type="checkbox" id="transformationCheckbox" class="claim-checkbox single-selectable">
    </div>

    <div id="transformationWrapperDiv" style="display: none">
        <div id="transformationCardList">
            <jsp:include page="transformation_card_list.jsp"/>
        </div>


        <sec:authorize access="hasRole('Transformation_Add')" var="security_transformation_add" />

        <c:if test="${security_transformation_add || !configurationServiceDelegator.securityEnabled}">

            <button type="button" id="transformationTrigger" class="add-button" data-toggle="button">
                <i class="add-icon"></i>
                <spring:message code="transformation.section.button.${flowModeId}"/>
            </button>

            <div id="tabtransformation" class="addbox" style="display:none">
                <div id="transformationTypeDiv" style="display: none">
                    <jsp:include page="transformation_choosetype.jsp"/>
                </div>

                <div id="transformationSection">

                </div>
                <br />
            </div>
        </c:if>
    </div>

    <input type="hidden" id="maxtransformation"
           value="${configurationServiceDelegator.getValue('claim.transformation.add.maxNumber', 'general')}">
    <input type="hidden" id="euipo_office"
           value="${euipo_office}">
    <input type="hidden" id="wipo_office"
           value="${wipo_office}">

    <script type="text/javascript">
        initializeClaimSection('transformation');
    </script>

</section>