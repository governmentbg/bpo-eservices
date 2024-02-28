<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<input type="hidden" id="designerBelongsHidden" value="${designerForm.belongsToAGroup}"/>

<c:set var="sectionId" value="designers" scope="request"/>
<component:checkbox id="checkBelongs" path="belongsToAGroup" checkRender="true" labelTextCode="designer.field.belongsToAGroupOfDesigners" />
