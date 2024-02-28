<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="exhibition claim" id="exhibition">

    <div class="claim-section-checkbox">
        <label for="exhibitionCheckbox" class="claim-label-checkbox"><spring:message code="exhibition.section.checkbox"/> </label>
        <input type="checkbox" id="exhibitionCheckbox" class="claim-checkbox">
    </div>

    <div id="exhibitionWrapperDiv" style="display: none">
        <div id="exhibitionCardList">
            <jsp:include page="exhibition_card_list.jsp"/>
        </div>


        <sec:authorize access="hasRole('Exhibition_Priority_Add')" var="security_exhibition_add" />

        <c:if test="${security_exhibition_add || !configurationServiceDelegator.securityEnabled}">

            <c:if test="${!flowBean.earlierAppImported}">
                <button type="button" id="exhibitionTrigger" class="add-button" data-toggle="button">
                    <i class="add-icon"></i>
                    <spring:message code="exhibition.section.button"/>
                </button>
            </c:if>
            <c:if test="${flowBean.earlierAppImported}">
                <div class="alert alert-info"><spring:message code="earlier.app.imported.can.not.edit"/></div>
            </c:if>

            <div id="tabexhibition" class="addbox" style="display:none">

                <div id="exhibitionSection">
                </div>
                <br />
            </div>
        </c:if>
    </div>

    <input type="hidden" id="maxexhibition"
           value="${configurationServiceDelegator.getValue('claim.exhibition.add.maxNumber', 'general')}">
    <script type="text/javascript">
        initializeClaimSection('exhibition');
    </script>

</section>