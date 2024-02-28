<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="comment" uri="http://www.springframework.org/tags/form" %>


<section id="technical_questionnaire" class="patent">
    <header>
        <a id="technical_questionnaire_section" class="anchorLink">
        </a>

        <h2><spring:message code="patent.technical.questionnaire.${flowModeId}"></spring:message></h2>
    </header>

    <form:form id="technicalQuestionnaireForm" class="mainForm formEF addbox" modelAttribute="flowBean" method="POST">
        <c:set var="sectionId" value="technical_questionnaire" scope="request"/>

        <component:textarea path="technicalQuestionnaireForm.originSupportReproductionAbstract" labelTextCode="patent.form.field.originSupportReproductionAbstract" formTagClass="description-textarea" checkRender="true"></component:textarea>
        <component:textarea path="technicalQuestionnaireForm.testConditionsAbstract" labelTextCode="patent.form.field.testConditionsAbstract" formTagClass="description-textarea" checkRender="true"></component:textarea>
        <component:textarea path="technicalQuestionnaireForm.characteristicsAbstract" labelTextCode="patent.form.field.characteristicsAbstract" formTagClass="description-textarea" checkRender="true"></component:textarea>
        <component:textarea path="technicalQuestionnaireForm.resistanceAbstract" labelTextCode="patent.form.field.resistanceAbstract" formTagClass="description-textarea" checkRender="true"></component:textarea>
    </form:form>
</section>

<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /patent/register-patent/patent
</div>