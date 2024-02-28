<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="your-details">
    <header>
        <spring:message code="review.technical.questionnaire"/>
        <a class="edit navigateBtn" data-val="Update_Patent"><spring:message code="review.update"/></a>
    </header>

    <div>
        <table>
            <tbody>
                <c:if test="${not empty flowBean.technicalQuestionnaireForm.originSupportReproductionAbstract}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.originSupportReproductionAbstract"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.technicalQuestionnaireForm.originSupportReproductionAbstract}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.technicalQuestionnaireForm.testConditionsAbstract}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.testConditionsAbstract"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.technicalQuestionnaireForm.testConditionsAbstract}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.technicalQuestionnaireForm.characteristicsAbstract}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.characteristicsAbstract"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.technicalQuestionnaireForm.characteristicsAbstract}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.technicalQuestionnaireForm.resistanceAbstract}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.resistanceAbstract"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.technicalQuestionnaireForm.resistanceAbstract}"/></p>
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

</div>