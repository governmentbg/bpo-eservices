<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="entitlement" id="entitlement">
    <c:set var="sectionId" value="entitlement" scope="request" />
    <header>
        <a href="#entitlement" class="anchorLink">
        </a>

        <h2>
            <spring:message code="entitlement.title"/><span class="requiredTag">*</span>
        </h2>
    </header>

    <form:form class="mainForm formEf" modelAttribute="flowBean.mainForm.entitlement">
        <div>
            <component:checkbox path="patentOfficiary" checkRender="true"
                                labelTextCode="application.entitlement.field.patentOfficiary.${flowModeId}" id="entitlement_patentOfficiary"/>
            <c:set var="styleDiv" value="display:none;" scope="page"></c:set>
            <c:if test="${flowBean.mainForm.entitlement.patentOfficiary}">
                <c:set var="styleDiv" value="" scope="page"></c:set>
            </c:if>
            <div id="entitlement_patentOfficiary_div" class="fileuploadInfo collectiveSelectors" style="${styleDiv}">
                <component:file labelCode="application.entitlement.field.patentOfficiary.file"
                                pathFilename="patentOfficiaryFiles" fileWrapperPath="patentOfficiaryFiles"
                                fileWrapper="${flowBean.mainForm.entitlement.patentOfficiaryFiles}"/>
            </div>

            <component:checkbox path="patentNotOfficiary" checkRender="true"
                                labelTextCode="application.entitlement.field.patentNotOfficiary.${flowModeId}" id="entitlement_patentNotOfficiary"/>

            <c:set var="styleDiv" value="display:none;" scope="page"></c:set>
            <c:if test="${flowBean.mainForm.entitlement.patentNotOfficiary}">
                <c:set var="styleDiv" value="" scope="page"></c:set>
            </c:if>
            <div id="entitlement_patentNotOfficiary_div" class="fileuploadInfo collectiveSelectors" style="${styleDiv}">
                <component:file labelCode="application.entitlement.field.patentNotOfficiary.file"
                                pathFilename="patentNotOfficiaryFiles"
                                fileWrapperPath="patentNotOfficiaryFiles"
                                fileWrapper="${flowBean.mainForm.entitlement.patentNotOfficiaryFiles}"/>
            </div>

            <component:checkbox path="transferContract" checkRender="true"
                                labelTextCode="application.entitlement.field.transferContract.${flowModeId}" id="entitlement_transferContract"/>
            <c:set var="styleDiv" value="display:none;" scope="page"></c:set>
            <c:if test="${flowBean.mainForm.entitlement.transferContract}">
                <c:set var="styleDiv" value="" scope="page"></c:set>
            </c:if>
            <div id="entitlement_transferContract_div" style="${styleDiv}">
                <component:file labelCode="application.entitlement.field.transferContract.file"
                                pathFilename="transferContractFiles" fileWrapperPath="transferContractFiles"
                                fileWrapper="${flowBean.mainForm.entitlement.transferContractFiles}"/>
            </div>
            <component:checkbox path="otherGrounds" checkRender="true"
                                labelTextCode="application.entitlement.field.otherGrounds.${flowModeId}" id="entitlement_otherGrounds"/>
            <c:set var="styleDiv" value="display:none;" scope="page"></c:set>
            <c:if test="${flowBean.mainForm.entitlement.otherGrounds}">
                <c:set var="styleDiv" value="" scope="page"></c:set>
            </c:if>
            <div style="${styleDiv}" id="entitlement_otherGrounds_div">
                <component:textarea path="otherGroundsDescription" checkRender="true"
                                    labelTextCode="entitlement.form.field.otherGroundsDescription" id="otherGroundsDescription" formTagClass="entitlement-textarea"/>
            </div>
        </div>
    </form:form>


</section>

