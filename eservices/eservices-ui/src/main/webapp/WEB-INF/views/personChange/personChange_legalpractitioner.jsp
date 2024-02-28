<%@ page import="eu.ohim.sp.common.ui.form.person.LegalPractitionerType" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${representativeLegalPractitionerForm.id}" name="id"/>
    <jsp:param value="${representativeLegalPractitionerForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${representativeLegalPractitionerForm.changeType}" name="changeType"/>
    <jsp:param value="${representativeLegalPractitionerForm.personRol}" name="personRol"/>
    <jsp:param value="representativeLegalPractitionerForm" name="representativeFormName"/>
    <jsp:param value="representative/representative_legalpractitioner" name="representativeJspName"/>
</jsp:include>