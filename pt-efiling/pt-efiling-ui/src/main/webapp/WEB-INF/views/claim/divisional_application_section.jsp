<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<section class="divisionalApplication claim" id="divisionalApplication">

    <div class="claim-section-checkbox">
        <label for="divisionalApplicationCheckbox" class="claim-label-checkbox"><spring:message code="divisionalApplication.section.checkbox"/> </label>
        <input type="checkbox" id="divisionalApplicationCheckbox" class="claim-checkbox single-selectable">
    </div>

    <div id="divisionalApplicationWrapperDiv" style="display: none">
        <div id="divisionalApplicationCardList">
            <jsp:include page="divisional_application_card_list.jsp"/>
        </div>


        <sec:authorize access="hasRole('DivisionalApplication_Add')" var="security_divisional_add" />

        <c:if test="${security_divisional_add || !configurationServiceDelegator.securityEnabled}">

            <button type="button" id="divisionalApplicationTrigger" class="add-button" data-toggle="button">
                <i class="add-icon"></i>
                <spring:message code="divisionalApplication.section.button"/>
            </button>

            <div id="tabdivisionalApplication" class="addbox" style="display:none">
                <div id="createDivisionalOptionsDiv" style="display: none">
                    <jsp:include page="divisional_application_import.jsp"/>
                </div>
                <div id="divisionalApplicationSection">

                </div>
                <br />
            </div>
<%--            ONLY FOR SV-EFILING DEMO! Import will be implemented later -> then the upper div should be used instead of the lower--%>
<%--            <div id="tabdivisionalApplication" class="addbox" style="display:none">--%>
<%--                <div id="createDivisionalOptionsDiv" style="display: none">--%>
<%--                    <jsp:include page="divisional_application_import.jsp"/>--%>
<%--                </div>--%>
<%--                <div id="divisionalApplicationSection">--%>
<%--                    <c:if test="${flowModeId eq 'sv-efiling'}">--%>
<%--                        <script type="text/javascript">--%>
<%--                            if( $('#leftmenu').is(':empty') ) {--%>
<%--                                loadDivisionalApplicationManual();--%>
<%--                            }--%>
<%--                        </script>--%>
<%--                    </c:if>--%>
<%--                </div>--%>
<%--                <br />--%>
<%--            </div>--%>
        </c:if>
    </div>

    <input type="hidden" id="maxdivisionalApplication"
           value="${configurationServiceDelegator.getValue('claim.divisionalApplication.add.maxNumber', 'general')}">
    <script type="text/javascript">
        initializeClaimSection('divisionalApplication');
    </script>

</section>