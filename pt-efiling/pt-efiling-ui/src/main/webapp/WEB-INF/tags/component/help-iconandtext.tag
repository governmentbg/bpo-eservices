<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<jsp:directive.attribute name="code" required="true" type="java.lang.String" description="Code for the spring:message"/>

<div class="tip"><i class="icon-info-sign"></i><component:helpMessage textCode="${code}"/></div>