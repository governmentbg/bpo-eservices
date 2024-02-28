<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<jsp:directive.attribute name="code" required="true" type="java.lang.String" description="Code for the spring:message"/>


<div class="tooltip-simple-text">
    <ul>
        <li>
            <component:helpMessage textCode="${code}"/>
        </li>
    </ul>
</div>