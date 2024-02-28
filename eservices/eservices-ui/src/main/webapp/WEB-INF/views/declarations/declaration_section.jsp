<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="declarations" id="declarations">
    <c:set var="sectionId" value="application_declarations" scope="request" />
    <header>
        <a id="application_declarations" class="anchorLink"></a>
        <a id="declaration" class="anchorLink"></a>

        <h2>
            <spring:message code="declarations.title.${flowModeId}"/>
        </h2>
    </header>

    <form:form class="mainForm formEf" modelAttribute="flowBean.mainForm">
        <div>
            <component:checkbox path="smallCompany" checkRender="true" formTagClass="ajaxSubmitDeclarationOption"
                                labelTextCode="application.declaration.field.smallCompany" id="declaration_smallCompany"/>
            <component:generic path="smallCompany" checkRender="true">
                <div class="tip"><spring:message code="application.declaration.smallCompany.hint"></spring:message></div>
            </component:generic>
            <div class="fileuploadInfo" style="${flowBean.mainForm.smallCompany ? '': 'display:none'}" id="smallCompany_div">
               <component:file fileWrapper="${flowBean.mainForm.smallCompanyFiles}" fileWrapperPath="smallCompanyFiles" pathFilename="smallCompanyFiles"
                               helpMessageKey="application.declaration.smallCompany.hint"/>
            </div>


            <component:checkbox path="licenceAvailability" checkRender="true" formTagClass="ajaxSubmitDeclarationOption"
                                labelTextCode="application.declaration.field.licenceAvailability" id="licenceAvailability"/>

        </div>
    </form:form>
</section>

