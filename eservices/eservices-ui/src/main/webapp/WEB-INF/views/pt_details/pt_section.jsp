<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 12.12.2019 г.
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="patent" id="pt_section">
    <c:set var="sectionId" value="patent" scope="request"/>

    <header>
        <a id="patent" class="anchorLink">
        </a>

        <h2><spring:message code="pt.details.title.${formUtil.getMainType()}"></spring:message></h2>
    </header>

    <jsp:include page="/WEB-INF/views/pt_details/pt_card_list.jsp"/>

    <component:generic path="hideSelectPublished" checkRender="true">
        <c:set var="hidePtTrigger" value="true" scope="request"/>
    </component:generic>
    <c:if test="${!hidePtTrigger}">
        <button id="ptTrigger" class="add-button" data-toggle="button" type="button">
            <i class="add-icon"></i>
            <spring:message code="pt.section.button.patent.${formUtil.getMainType()}"/>
        </button>
    </c:if>

    <jsp:include page="/WEB-INF/views/unpublished/unpublished_app_select.jsp">
        <jsp:param value="${sectionId}" name="sectionId"/>
        <jsp:param value="PT" name="objectType"/>
        <jsp:param value="${formUtil.getMainType()}" name="mainObject"/>
    </jsp:include>

    <c:if test="${!hidePtTrigger}">
        <div id="importPT" class="addbox" style="display: none">
            <jsp:include page="/WEB-INF/views/pt_details/pt_import.jsp"/>
        </div>
    </c:if>

    <div id="tabPT" class="addbox" style="display: none">
        <ul class="controls">
            <li>
                <a class="cancelButton patent"><spring:message
                        code="applicant.form.action.cancelAdd.top"/></a>
            </li>
            <li>
                <button class="addPT" type="button">
                    <i class="add-icon"> </i>
                    <spring:message code="applicant.form.action.add.top"/>
                </button>
            </li>
        </ul>
        <br />
        <br />
        <div id="ptSection">

        </div>
        <br />
        <ul class="controls">
            <li>
                <a class="cancelButton patent"><spring:message
                        code="applicant.form.action.cancelAdd.top"/></a>
            </li>
            <li>
                <button class="addPT" type="button">
                    <i class="add-icon"> </i>
                    <spring:message code="applicant.form.action.add.top"/>
                </button>
            </li>
        </ul>
        <br />
    </div>

    <input type="hidden" id="maxPTs" value="${configurationServiceDelegator.getValue('patent.add.maxNumber', 'general')}">
    <script type="text/javascript">
        checkMaxPTs();
    </script>

</section>

