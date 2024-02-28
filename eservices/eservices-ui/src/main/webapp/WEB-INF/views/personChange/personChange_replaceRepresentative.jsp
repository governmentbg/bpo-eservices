<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


    <div id="personChangeTopSection">
        <header>


            <h3><spring:message code="personChange.form.top.title.${personChangeForm.personRol}"/></h3>
        </header>

        <jsp:include page="/WEB-INF/views/personChange/parts/personChange_import_from_tmds_part.jsp">
            <jsp:param value="notUpdateBottom" name="importPersonFromTmDsClasses"/>
            <jsp:param value="${personChangeForm.personRol}" name="personRol"/>
            <jsp:param value="${personChangeForm.previousPersonId}" name="previousPersonId"/>
        </jsp:include>
        <form:form cssClass="formEF" modelAttribute="personChangeForm">
            <jsp:include page="/WEB-INF/views/personChange/parts/personChange_previous_part.jsp"/>
        </form:form>
    </div>

    <jsp:include page="/WEB-INF/views/personChange/personChange_import.jsp"/>
    <div id="personChangeSection">
    </div>
