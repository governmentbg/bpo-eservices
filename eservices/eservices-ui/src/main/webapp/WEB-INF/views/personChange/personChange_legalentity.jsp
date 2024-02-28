<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${representativeLegalEntityForm.id}" name="id"/>
    <jsp:param value="${representativeLegalEntityForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${representativeLegalEntityForm.changeType}" name="changeType"/>
    <jsp:param value="${representativeLegalEntityForm.personRol}" name="personRol"/>
    <jsp:param value="representativeLegalEntityForm" name="representativeFormName"/>
    <jsp:param value="representative/representative_legalentity" name="representativeJspName"/>
</jsp:include>