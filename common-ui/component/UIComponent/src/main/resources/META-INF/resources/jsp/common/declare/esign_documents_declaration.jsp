<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="application_declarations" scope="request"/>

<div id="application_declarations" class="esignDocumentsDeclaration">
    <form:form class="mainForm formEf" modelAttribute="flowBean">
        <component:checkbox path="esignDocDeclaration" checkRender="true"
                            labelTextCode="esign.docs.declaration" id="esignDocDeclaration"/>

    </form:form>
</div>