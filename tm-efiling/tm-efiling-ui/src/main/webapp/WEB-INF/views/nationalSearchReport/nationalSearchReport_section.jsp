<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="national-report" id="nationalSearchReport">
    <header>
        <a id="nationalSearchReport" class="anchorLink"></a>
        <h2><spring:message code="nationalSearchReport.title"></spring:message></h2>
    </header>

    <form:form modelAttribute="flowBean.mainForm" cssClass="mainForm">
        <form:hidden path="nationalSearchReport"/>


        <div class="national-report-buttons">
            <p><spring:message code="nationalSearchReport.doYouWantToRequest"/></p>
            <ul>
                <li id="nationalSearchReport_Yes">
                    <a  class="yesNoBtn" data-toggle="tab"><spring:message code="nationalSearchReport.yes"/></a>
                </li>
                <li id="nationalSearchReport_No">
                    <a  class="yesNoBtn" data-toggle="tab"><spring:message code="nationalSearchReport.no"/></a>
                </li>
            </ul>
        </div>
        <div class="national-report-info">
            <ul>
                <li>
                    <component:helpMessage textCode="national.search.report.help"
                           useFlowModeId="true"/>
                </li>
            </ul>
        </div>
    </form:form>
</section>
