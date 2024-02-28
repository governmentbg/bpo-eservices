<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 18.4.2019 г.
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="declarations" id="declarations">
    <c:set var="sectionId" value="patent_declarations" scope="request" />
    <header>
        <a href="#declarations" class="anchorLink">
        </a>

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

            <component:checkbox path="examinationRequested" checkRender="true" formTagClass="ajaxSubmitDeclarationOption"
                                labelTextCode="application.declaration.field.examinationRequested" id="declaration_examinationRequested"/>
            <component:generic path="examinationRequested" checkRender="true">
                <div class="tip"><spring:message code="application.declaration.examinationRequested.hint"></spring:message></div>
            </component:generic>

            <div style="${flowBean.mainForm.examinationRequested ? '': 'display:none'}" id="examinationRequested_div">
                <component:checkbox path="anticipationOfPublication" checkRender="true" formTagClass="ajaxSubmitDeclarationOption"
                                labelTextCode="application.declaration.field.anticipationOfPublication" id="anticipationOfPublication"/>
            </div>

            <component:checkbox path="postponementOfPublication" checkRender="true"
                                labelTextCode="application.declaration.field.postponementOfPublication" id="declaration_postponementOfPublication"/>

            <div class="fileuploadInfo" style="${flowBean.mainForm.postponementOfPublication ? '': 'display:none'}" id="postponementOfPublication_div">
                <component:file fileWrapper="${flowBean.mainForm.postponementOfPublicationFiles}" fileWrapperPath="postponementOfPublicationFiles" pathFilename="postponementOfPublicationFiles"/>
            </div>

        </div>
    </form:form>
</section>

