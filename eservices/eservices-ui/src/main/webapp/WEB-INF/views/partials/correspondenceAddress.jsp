<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<input type="hidden" id="correspondenceAddressPath" value="${param.path}[0]"/>

<input type="hidden" id="numberOfCA" value="${param.size}"/>

<span id="correspondenceAddressList">
<c:if test="${param.size > 0}">
    <% for (int i = 0; i < Integer.parseInt(request.getParameter("size")); i++)
    {%>
    <c:set var="index" value="<%=i%>"/>
        <span id="CAwrapper_${param.path}[${index}]">
            <label class="personFormSection"> <spring:message code="person.field.correspondenceAddress"/> </label>
            <jsp:include page="/WEB-INF/views/partials/correspondenceAddressItem.jsp">
                <jsp:param name="path" value="${param.path}[${index}]"/>
                <jsp:param name="itemFilterValue" value="${param.itemFilterValue}"/>
            </jsp:include>
            <br>
        </span>
    <%}%>
</c:if>
</span>


<span id="correspondenceAddressTemplate" class="hidden">
        <label class="personFormSection"> <spring:message code="person.field.correspondenceAddress"/> </label>
        <jsp:include page="/WEB-INF/views/partials/correspondenceAddressItem.jsp">
            <jsp:param name="path" value="${param.path}[0]"/>
        </jsp:include>
        <br>
</span>