<%@ page import="eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerType" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<jsp:include page="/WEB-INF/views/personChange/parts/personChange_form_part.jsp">
    <jsp:param value="${representativeProfessionalPractitionerForm.id}" name="id"/>
    <jsp:param value="${representativeProfessionalPractitionerForm.previousPersonId}" name="previousPersonId"/>
    <jsp:param value="${representativeProfessionalPractitionerForm.changeType}" name="changeType"/>
    <jsp:param value="${representativeProfessionalPractitionerForm.personRol}" name="personRol"/>
    <jsp:param value="representativeProfessionalPractitionerForm" name="representativeFormName"/>
    <jsp:param value="representative/representative_professionalpractitioner" name="representativeJspName"/>
</jsp:include>