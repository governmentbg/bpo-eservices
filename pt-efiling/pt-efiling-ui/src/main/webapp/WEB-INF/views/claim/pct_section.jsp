<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 13.5.2019 г.
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="pct claim" id="pct">

    <div class="claim-section-checkbox">
        <label for="pctCheckbox" class="claim-label-checkbox"><spring:message code="pct.section.checkbox"/> </label>
        <input type="checkbox" id="pctCheckbox" class="claim-checkbox single-selectable">
    </div>

    <div id="pctWrapperDiv" style="display: none">
        <div id="pctCardList">
            <jsp:include page="pct_card_list.jsp"/>
        </div>


        <sec:authorize access="hasRole('PCT_Add')" var="security_pct_add" />

        <c:if test="${security_pct_add || !configurationServiceDelegator.securityEnabled}">

            <c:if test="${!flowBean.patentTemplateImported}">
                <button type="button" id="pctTrigger" class="add-button" data-toggle="button">
                    <i class="add-icon"></i>
                    <spring:message code="pct.section.button"/>
                </button>
            </c:if>

            <c:if test="${flowBean.patentTemplateImported}">
                <div class="alert alert-info"><spring:message code="patentTemplateImported.imported.can.not.edit"/></div>
            </c:if>
            <div id="tabpct" class="addbox" style="display:none">

                <div id="pctSection">
                </div>
                <br />
            </div>
        </c:if>
    </div>

    <input type="hidden" id="maxpct"
           value="${configurationServiceDelegator.getValue('claim.pct.add.maxNumber', 'general')}">
    <script type="text/javascript">
        initializeClaimSection('pct');
    </script>

</section>

<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /patent/register-patent/previous-apps
</div>