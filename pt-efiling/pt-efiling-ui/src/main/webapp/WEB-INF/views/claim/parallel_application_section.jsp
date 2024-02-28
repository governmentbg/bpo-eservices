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

<section class="parallelApplication claim" id="parallelApplication">
    <spring:eval scope="request" var="euipo_office" expression="@propertyConfigurer.getProperty('euipo.office')"/>
    <spring:eval scope="request" var="wipo_office" expression="@propertyConfigurer.getProperty('wipo.office')"/>

    <div class="claim-section-checkbox">
        <label for="parallelApplicationCheckbox" class="claim-label-checkbox"><spring:message code="parallelApplication.section.checkbox"/> </label>
        <input type="checkbox" id="parallelApplicationCheckbox" class="claim-checkbox single-selectable">
    </div>

    <div id="parallelApplicationWrapperDiv" style="display: none">
        <div id="parallelApplicationCardList">
            <jsp:include page="parallel_application_card_list.jsp"/>
        </div>


        <sec:authorize access="hasRole('ParallelApplication_Add')" var="security_parallelApplication_add" />

        <c:if test="${security_parallelApplication_add || !configurationServiceDelegator.securityEnabled}">

            <button type="button" id="parallelApplicationTrigger" class="add-button" data-toggle="button">
                <i class="add-icon"></i>
                <spring:message code="parallelApplication.section.button"/>
            </button>

            <div id="tabparallelApplication" class="addbox" style="display:none">
                <div id="parallelApplicationTypeDiv" style="display: none">
                    <jsp:include page="parallel_application_choosetype.jsp"/>
                </div>

                <div id="parallelApplicationSection">

                </div>
                <br />
            </div>
        </c:if>
    </div>

    <input type="hidden" id="maxparallelApplication"
           value="${configurationServiceDelegator.getValue('claim.parallelApplication.add.maxNumber', 'general')}">
    <input type="hidden" id="euipo_office"
           value="${euipo_office}">
    <input type="hidden" id="wipo_office"
           value="${wipo_office}">

    <script type="text/javascript">
        initializeClaimSection('parallelApplication');
    </script>

</section>