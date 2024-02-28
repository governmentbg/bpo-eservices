<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${representativeEmployeeRepresentativeForm.id}" name="id"/>
    <jsp:param value="${representativeEmployeeRepresentativeForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${representativeEmployeeRepresentativeForm.changeType}" name="changeType"/>
    <jsp:param value="${representativeEmployeeRepresentativeForm.personRol}" name="personRol"/>
    <jsp:param value="representativeEmployeeRepresentativeForm" name="representativeFormName"/>
    <jsp:param value="representative/representative_employeerepresentative" name="representativeJspName"/>
</jsp:include>