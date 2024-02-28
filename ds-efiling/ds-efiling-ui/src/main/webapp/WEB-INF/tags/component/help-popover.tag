<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<jsp:directive.attribute name="code" required="true" type="java.lang.String" description="Code for the spring:message"/>

<a rel="classes-help-tooltip" class="tooltip-icon"></a>
<div data-tooltip="help" class="hidden">
    <a class="close-popover"></a>
    <component:helpMessage textCode="${code}" />
</div>