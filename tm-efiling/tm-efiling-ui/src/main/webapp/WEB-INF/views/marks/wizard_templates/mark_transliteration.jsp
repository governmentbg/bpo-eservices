<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:textarea path="mainForm.transcriptionDetails" checkRender="true"
                   id="transliterationdetails" labelTextCode="mark.transliteration"
                   formTagClass="mark-description-textarea" rightLabel="mark.transliteration.note"/>