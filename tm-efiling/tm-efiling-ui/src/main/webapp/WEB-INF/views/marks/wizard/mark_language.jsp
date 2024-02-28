<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
    <section class="language">

        <c:set var="sectionId" value="languages" scope="request"/>
        <header>
            <a id="languages" class="anchorLink"></a>

            <h2><spring:message code="mark.language.title"/></h2>
        </header>

        <p>
            <span><spring:message code="mark.language.selected.first"/></span>
            <span>
                <c:if test="${not empty flowBean.firstLang && not empty configurationServiceDelegator.resolveFirstLanguageCode(flowBean.firstLang)}">
                    <c:set var="language" value="${configurationServiceDelegator.resolveFirstLanguageCode(flowBean.firstLang)}"/>
                    <span id="firstLanguageValue" class="first-language"><spring:message code="${language.value}"/></span>
                </c:if>
            </span>
            <spring:message code="mark.language.selected.second"/>
        </p>

        <component:generic path="firstLang" checkRender="true">
            <div class="language-fields">
                <ul class="languages">
                    <li><component:select items="${configurationServiceDelegator['firstLanguages']}" labelTextCode="mark.language.first"
                                         path="firstLang"
                                         itemLabel="value" itemValue="code" checkRender="true"/>
                    </li>
                </ul>
            </div>
        </component:generic>
        <component:generic path="secLang" checkRender="true">
            <div class="language-fields">
                <ul class="languages">
                    <li>
                        <component:select items="${configurationServiceDelegator['secondLanguages']}"
                                         labelTextCode="mark.language.second"
                                         path="secLang"
                                         itemLabel="value" itemValue="code" checkRender="true"/>
                    </li>
                </ul>
                    <%--<div class="span3">--%>
                    <%--<h4 class="note"><spring:message code="mark.language.second"/></h4>--%>

                    <%--</div>--%>
            </div>
            <div class="language-info">
                <ul>
                    <li><component:helpMessage textCode="second.language.help.wizard"/></li>
                </ul>
            </div>
        </component:generic>

        <!-- SECOND LANGUAGE CORRESPONDANCE -->
        <div class="language-fields">
            <component:checkbox path="mainForm.correspondenceLanguageCheckBox"
                               checkRender="true" id="sendsecondlng1" labelClass="language-option"
                               labelTextCode="mark.language.second.correspondence" formTagClass=""/>
        </div>
            <%--<component:generic path="mainForm.correspondenceLanguageCheckBox" checkRender="true">--%>
            <%--<div id="referenceId" class="span3">--%>
            <%--<h4 class="note">--%>
            <%--<spring:message code="mark.correspondence"/>--%>
            <%--</h4>--%>

            <%--<p class="note">--%>
            <%--<spring:message code="mark.correspondence.note"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</component:generic>--%>
        <!-- SECOND LANGUAGE TRANSLATION -->
        <div class="language-fields">
            <component:checkbox path="mainForm.secondLanguageTranslation" checkRender="true" labelClass="language-option"
                                id="secondLangChkbox" labelTextCode="mark.language.second.flagged" formTagClass=""/>
        </div>
        <%--<component:generic path="mainForm.secondLanguageTranslation" checkRender="true">--%>
            <%--<div id="secondLangChkboxNote" class="span3">--%>
                <%--<h4 class="note">--%>
                    <%--<spring:message code="mark.language.second.flagged.note.title"/>--%>
                <%--</h4>--%>

                <%--<p class="note">--%>
                    <%--<spring:message code="mark.language.second.flagged.note"/>--%>
                <%--</p>--%>
            <%--</div>--%>
        <%--</component:generic>--%>

    </section>
</form:form>