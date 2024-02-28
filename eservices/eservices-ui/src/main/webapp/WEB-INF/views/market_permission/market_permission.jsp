<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<section class="patent">
    <c:set var="sectionId" value="market_permission" scope="request"/>

    <header>
        <a id="market_permission" class="anchorLink">
        </a>
        <h2><spring:message code="market.permission.title"></spring:message></h2>
    </header>

    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm addbox" method="POST">
        <c:set var="sectionId" value="market_permission" scope="request"/>

        <div style="display: block">
            <label><spring:message code="market.permission.firstPermissionBGLabel"/></label>
            <div class="inline-fields half_pct">
                <component:input path="marketPermission.firstPermissionBGNumber" labelTextCode="market.permission.firstPermissionBGNumber" formTagClass="onerow-fields" checkRender="true"/>
            </div>
            <div class="right-fields">
                <component:input formTagClass="field-date" path="marketPermission.firstPermissionBGDate" checkRender="true" labelTextCode="market.permission.firstPermissionBGDate"/>
            </div>
        </div>
        <div style="display: block">
            <label><spring:message code="market.permission.firstPermissionEULabel"/></label>
            <div class="inline-fields half_pct">
                <component:input path="marketPermission.firstPermissionEUNumber" labelTextCode="market.permission.firstPermissionEUNumber" formTagClass="onerow-fields" checkRender="true"/>
            </div>
            <div class="right-fields">
                <component:input formTagClass="field-date" path="marketPermission.firstPermissionEUDate" checkRender="true" labelTextCode="market.permission.firstPermissionEUDate"/>
            </div>
        </div>

    </form:form>
</section>

