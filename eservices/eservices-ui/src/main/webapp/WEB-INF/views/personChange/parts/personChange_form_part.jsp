<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<span>
    <c:if test="${param.changeType eq 'REMOVE_REPRESENTATIVE' or param.changeType eq 'REPLACE_REPRESENTATIVE' or param.changeType eq 'REMOVE_CORRESPONDENT' or param.changeType eq 'REPLACE_CORRESPONDENT' or param.changeType eq 'CHANGE_REPRESENTATIVE_ADDRESS' or param.changeType eq 'CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS' or param.changeType eq 'CHANGE_CORRESPONDENT_ADDRESS'}">
        <div id="personChangeTopSection">
            <header>
                <h3><spring:message code="personChange.form.top.title.${param.personRol}"/></h3>
            </header>
            <c:set var="importButtonId" value="importPersonFromTmDs" scope="request"/>
            <c:if test="${param.changeType eq 'REPLACE_REPRESENTATIVE' or param.changeType eq 'REPLACE_CORRESPONDENT'}">
                <c:set var="importPersonFromTmDsClasses" value="notUpdateBottom" scope="request"/>
            </c:if>
            <jsp:include page="/WEB-INF/views/personChange/parts/personChange_import_from_tmds_part.jsp">
                <jsp:param value="${importPersonFromTmDsClasses}" name="importPersonFromTmDsClasses"/>
                <jsp:param value="${param.personRol}" name="personRol"/>
                <jsp:param value="${param.previousPersonId}" name="previousPersonId"/>
            </jsp:include>
            <form:form cssClass="formEF" modelAttribute="${param.representativeFormName}">
                <jsp:include page="/WEB-INF/views/personChange/parts/personChange_previous_part.jsp">
                    <jsp:param value="${param.id}" name="id"/>
                </jsp:include>
            </form:form>
        </div>
    </c:if>

    <div id="personChangeSection">
        <c:choose>
            <c:when test="${param.changeType eq 'ADD_NEW_REPRESENTATIVE' or param.changeType eq 'REPLACE_REPRESENTATIVE' or param.changeType eq 'ADD_NEW_CORRESPONDENT' or param.changeType eq 'REPLACE_CORRESPONDENT'}">
                <c:if test="${not empty formEdit}">
                    <header>
                        <h3><spring:message code="personChange.form.bottom.title.${param.changeType}"/></h3>
                    </header>
                </c:if>
                <jsp:include page="/WEB-INF/views/${param.representativeJspName}.jsp"/>
            </c:when>
            <c:when test="${param.changeType eq 'CHANGE_REPRESENTATIVE_ADDRESS' or param.changeType eq 'CHANGE_CORRESPONDENT_ADDRESS' or param.changeType eq 'CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS'}">
                <header>
                    <h3><spring:message code="personChange.form.bottom.title.${param.changeType}"/></h3>
                </header>
                <form:form cssClass="formEF" modelAttribute="${param.representativeFormName}">
                    <form:hidden id="personHiddenFormId" path="id"/>
                    <c:set var="sectionId" value="personChange_address" scope="request"/>
                    <c:if test="${param.changeType eq 'CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS'}">
                        <c:set var="sectionId" value="personChange_correspondenceAddress" scope="request"/>
                    </c:if>
                    <jsp:include page="/WEB-INF/views/${param.representativeJspName}.jsp"/>
                    <jsp:include page="/WEB-INF/views/personChange/parts/personChange_controls_part.jsp">
                        <jsp:param value="addChangeAddress" name="saveButtonClass"/>
                    </jsp:include>
                </form:form>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="${param.representativeFormName}">
                    <form:hidden id="personHiddenFormId" path="id"/>
                </form:form>
                <jsp:include page="/WEB-INF/views/personChange/parts/personChange_controls_part.jsp">
                    <jsp:param value="addRepresentativeNaturalPerson" name="saveButtonClass"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>
        <form:form cssClass="formEF" modelAttribute="${param.representativeFormName}">
            <form:hidden id="changeType" path="changeType"/>
        </form:form>
    </div>
</span>