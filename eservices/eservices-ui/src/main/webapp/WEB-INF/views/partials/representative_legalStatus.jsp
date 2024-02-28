<%@ page import="eu.ohim.sp.common.ui.form.person.LegalStatusType" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<component:generic path="legalForm" checkRender="true">
    <c:set var="enumValues" value="<%=Arrays.asList(LegalStatusType.values())%>"/>
    <component:select path="legalForm"
                      items="${enumValues}"
                      checkRender="false"
                      labelTextCode="representative.legalStatus.field.type"
                      itemValue="code" itemLabel="description"/>
</component:generic>
