<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 4.12.2023 г.
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<div>
    <label><spring:message code="representative.field.powDetails"/><span class="requiredTag">*</span></label>
    <component:checkbox path="powValidityIndefiniteIndicator" checkRender="true"
                        labelTextCode="representative.field.powValidityIndefiniteIndicator"/>

    <component:input path="powValidityEndDate" checkRender="true"
                     labelTextCode="representative.field.powValidityEndDate" formTagClass="standard-date-input"/>

    <component:generic path="powReauthorizationIndicator" checkRender="true">
        <label><spring:message code="representative.field.powReauthorizationIndicator"/><span class="requiredTag">*</span></label>
        <component:radiobutton path="powReauthorizationIndicator" value="true" labelTextCode="representative.field.powReauthorizationIndicator.true"/>
        <component:radiobutton path="powReauthorizationIndicator" value="false" labelTextCode="representative.field.powReauthorizationIndicator.false"/>
    </component:generic>

    <component:checkbox path="powRevokesPreviousIndicator" checkRender="true"
                        labelTextCode="representative.field.powRevokesPreviousIndicator"/>

    <component:textarea path="powNote" checkRender="true" labelTextCode="representative.field.powNote" formTagClass="textarea-fields" />
</div>

