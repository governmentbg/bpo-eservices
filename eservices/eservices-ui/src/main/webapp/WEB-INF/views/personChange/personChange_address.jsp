<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${personChangeForm.id}" name="id"/>
    <jsp:param value="${personChangeForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${personChangeForm.changeType}" name="changeType"/>
    <jsp:param value="${personChangeForm.personRol}" name="personRol"/>
    <jsp:param value="personChangeForm" name="representativeFormName"/>
    <jsp:param value="personChange/parts/personChange_address_part" name="representativeJspName"/>
</jsp:include>

