<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${flowBean.mainForm.secondLanguageTranslation == true}">
    <div id="secondLanguageField" class="row">
        <component:textarea path="mainForm.markDescriptionSecond"
                           checkRender="true" id="markdescriptionsecond"
                           labelTextCode="mark.sound.descriptionSecond" formTagClass="mark-description-textarea"/>
    </div>
</c:if>
<c:if test="${flowBean.mainForm.secondLanguageTranslation != true}">
    <div id="secondLanguageField" class="row secondLanguage">

        <component:textarea path="mainForm.markDescriptionSecond"
                           checkRender="true" id="markdescriptionsecond"
                           labelTextCode="mark.sound.descriptionSecond" formTagClass="mark-description-textarea"/>
    </div>
</c:if>
