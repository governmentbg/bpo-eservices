<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="reference" id="changetype">
    <header>
        <a id="changetype" class="anchorLink"></a>
        <h2>
            <spring:message code="change.type"/>
        </h2>
    </header>
    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
        <c:set var="sectionId" value="changetype" scope="request"/>
        <component:filterTextSelect id="dossierTypes-select"
        	 				 items="${configurationServiceDelegator['dossierChangeTypes']}"
        	 				 path="changeType"
                             labelTextCode="eservice.dossier.change.changeType"
                             itemLabel="value"
                             itemValue="code"
                             checkRender="true"
                             fieldFilter="applicationType"
                             fieldFilterText="${flowModeId}"
                             formTagClass="onerow-fields"
                             anotherFieldRender=""
                             anotherFieldComponent=""
                             anotherFieldFilter="false"
                             anotherFieldId=""
                             importInAnotherField=""
                             />

        <div style="margin-top:20px" class="alert alert-danger">
            <spring:message code="eservice.dossier.change.changeType.alert"/>
        </div>

        <c:choose>
            <c:when test="${flowBean.changeType eq 'dossierAccess'}">
                <div class="alert alert-info" id="changeTypeInfoDiv" style="margin-top:20px;">
                    <spring:message code="eservice.dossier.change.changeType.info.dossierAccess"/>
                </div>
            </c:when>
            <c:when test="${flowBean.changeType eq 'priorityDeclaration'}">
                <div class="alert alert-info" id="changeTypeInfoDiv" style="margin-top:20px;">
                    <spring:message code="eservice.dossier.change.changeType.info.priorityDeclaration"/>
                </div>
            </c:when>
            <c:when test="${flowBean.changeType eq 'providePOW'}">
                <div class="alert alert-info" id="changeTypeInfoDiv" style="margin-top:20px;">
                    <spring:message code="eservice.dossier.change.changeType.info.providePOW.${flowModeId}"/>
                </div>
            </c:when>
        </c:choose>

    </form:form>
</section>
