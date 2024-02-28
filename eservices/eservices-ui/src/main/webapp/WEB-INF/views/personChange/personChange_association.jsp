<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${representativeAssociationForm.id}" name="id"/>
    <jsp:param value="${representativeAssociationForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${representativeAssociationForm.changeType}" name="changeType"/>
    <jsp:param value="${representativeAssociationForm.personRol}" name="personRol"/>
    <jsp:param value="representativeAssociationForm" name="representativeFormName"/>
    <jsp:param value="representative/representative_association" name="representativeJspName"/>
</jsp:include>