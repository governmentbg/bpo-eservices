<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<section class="inventor" id="inventors">
    <header>
        <a href="#inventors" class="anchorLink">
        </a>

        <h2><spring:message code="inventor.section.title.${flowModeId}" /></h2>

    </header>

    <c:set var="sectionId" value="inventor" scope="request"/>

    <form:form class="mainForm formEf" modelAttribute="flowBean.mainForm">
        <div>
            <component:checkbox path="inventorsAreReal" checkRender="true"
                                labelTextCode="application.declaration.field.inventorsAreReal.${flowModeId}" id="declaration_inventorsAreReal"/>

            <div class="fileuploadInfo " style="${flowBean.mainForm.inventorsAreReal ? '': 'display:none'}"  id="inventorsAreReal_div">
                <component:file fileWrapper="${flowBean.mainForm.inventorsAreRealFiles}" fileWrapperPath="inventorsAreRealFiles" pathFilename="inventorsAreRealFiles"
                labelCode="application.declaration.file.attach"/>
            </div>
        </div>
    </form:form>

    <jsp:include page="inventor_card_list.jsp"/>
    
    <sec:authorize access="hasRole('Inventor_Add')" var="security_inventor_add" />
    
    <c:if test="${security_inventor_add || !configurationServiceDelegator.securityEnabled}">

        <c:if test="${!flowBean.earlierAppImported && !flowBean.patentTemplateImported}">
            <button type="button" id="inventorTrigger" class="add-button" data-toggle="button">
                <i class="add-icon"></i>
                <spring:message code="person.button.inventor.${flowModeId}"/>
            </button>
        </c:if>
        <c:if test="${flowBean.earlierAppImported}">
            <div class="alert alert-info"><spring:message code="earlier.app.imported.can.not.edit"/></div>
        </c:if>
        <c:if test="${flowBean.patentTemplateImported}">
            <div class="alert alert-info"><spring:message code="patentTemplateImported.imported.can.not.edit"/></div>
        </c:if>

        <div id="tabInventor" class="addbox" style="display:none">
            <jsp:include page="inventor_import.jsp"/>
            <div id="inventorSection">
            </div>
            <br />
        </div>

        <div id="inventorMatches">
            <jsp:include page="inventorMatches.jsp"/>
        </div>
    </c:if>
</section>

<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /patent/register-patent/inventors
</div>