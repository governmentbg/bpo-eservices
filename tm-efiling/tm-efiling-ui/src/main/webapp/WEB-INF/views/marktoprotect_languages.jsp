<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="type" id="languages">
    <form:form modelAttribute="flowBean" cssClass="formObject mainForm formEF"
               method="POST">
        <c:set var="sectionId" value="languages" scope="request"/>
        <a id="languages" class="anchorLink"></a>

        <h3><spring:message code="mark.language.title"/></h3>
        <ul class="languages">
            <li>
                <component:select items="${configurationServiceDelegator['firstLanguages']}" labelTextCode="mark.language.first"
                                 path="firstLang"
                                 itemLabel="value" itemValue="code" checkRender="true"/>
            </li>
            <li>
                <component:select items="${configurationServiceDelegator['secondLanguages']}" labelTextCode="mark.language.second"
                                 path="secLang"
                                 itemLabel="value" itemValue="code" checkRender="true"/>
            </li>
        </ul>

        <label for="send-correspondence" class="language-option">
    <span><component:checkbox path="mainForm.correspondenceLanguageCheckBox" checkRender="true"
                             id="sendsecondlng1" labelTextCode="mark.language.second.correspondence" formTagClass=""/></span>
        </label>
        <label for="provide-translations" class="language-option">
    <span>		<component:checkbox path="mainForm.secondLanguageTranslation" checkRender="true"
                                     id="secondLangChkbox" labelTextCode="mark.disclaimer.translationToSecond" formTagClass=""/></span>
        </label>

    </form:form>
</section>
