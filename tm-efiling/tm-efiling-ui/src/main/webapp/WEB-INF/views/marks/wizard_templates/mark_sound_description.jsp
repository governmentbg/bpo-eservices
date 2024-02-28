<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:textarea path="mainForm.markDescription" checkRender="true"
                   id="sounddescription" labelTextCode="mark.sound.description"
                   formTagClass="mark-description-textarea" rightLabel="mark.description.first.note"/>
