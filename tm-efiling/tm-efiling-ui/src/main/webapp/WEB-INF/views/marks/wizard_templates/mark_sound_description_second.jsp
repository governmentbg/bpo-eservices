<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${flowBean.mainForm.secondLanguageTranslation == true}">
    <div id="secondLanguageField">
        <component:textarea path="mainForm.markDescriptionSecond"
                           checkRender="true" id="sounddescriptionsecond"
                           labelTextCode="mark.sound.descriptionSecond" formTagClass="mark-description-textarea"
                           rightLabel="mark.description.second.note"/>
    </div>
</c:if>

<c:if test="${flowBean.mainForm.secondLanguageTranslation != true}">
    <div id="secondLanguageField" class="secondLanguage">
        <component:textarea path="mainForm.markDescriptionSecond"
                           checkRender="true" id="sounddescriptionsecond"
                           labelTextCode="mark.sound.descriptionSecond" formTagClass="mark-description-textarea"
                           rightLabel="mark.description.second.note"/>
    </div>
</c:if>