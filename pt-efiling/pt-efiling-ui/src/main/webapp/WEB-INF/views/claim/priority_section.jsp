
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<section class="priority claim" id="priority">

    <div class="claim-section-checkbox">
        <label for="priorityCheckbox" class="claim-label-checkbox"><spring:message code="priority.section.checkbox"/> </label>
        <input type="checkbox" id="priorityCheckbox" class="claim-checkbox">
    </div>

    <div id="priorityWrapperDiv" style="display: none">
        <div id="priorityCardList">
            <jsp:include page="priority_card_list.jsp"/>
        </div>

        <sec:authorize access="hasRole('Priority_Add')" var="security_priority_add" />

        <c:if test="${security_priority_add || !configurationServiceDelegator.securityEnabled}">

            <c:if test="${!flowBean.earlierAppImported && !flowBean.patentTemplateImported}">
                <button type="button" id="priorityTrigger" class="add-button" data-toggle="button">
                    <i class="add-icon"></i>
                    <spring:message code="priority.section.button"/>
                </button>

            </c:if>

            <c:if test="${flowBean.earlierAppImported}">
                <div class="alert alert-info"><spring:message code="earlier.app.imported.can.not.edit"/></div>
            </c:if>
            <c:if test="${flowBean.patentTemplateImported}">
                <div class="alert alert-info"><spring:message code="patentTemplateImported.imported.can.not.edit"/></div>
            </c:if>
            <div id="tabpriority" class="addbox" style="display:none">

                <div id="prioritySection">
                </div>
                <br />
            </div>
        </c:if>
    </div>

    <input type="hidden" id="maxpriority"
           value="${configurationServiceDelegator.getValue('claim.priority.add.maxNumber', 'general')}">
    <script type="text/javascript">
        initializeClaimSection('priority');
    </script>
</section>