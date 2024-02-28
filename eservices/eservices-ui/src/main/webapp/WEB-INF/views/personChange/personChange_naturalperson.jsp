<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${representativeNaturalPersonForm.id}" name="id"/>
    <jsp:param value="${representativeNaturalPersonForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${representativeNaturalPersonForm.changeType}" name="changeType"/>
    <jsp:param value="${representativeNaturalPersonForm.personRol}" name="personRol"/>
    <jsp:param value="representativeNaturalPersonForm" name="representativeFormName"/>
    <jsp:param value="representative/representative_naturalperson" name="representativeJspName"/>
</jsp:include>
