<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<td colspan="4">
    <div class="row-fluid no-space">
        <c:forEach var="translation" items="${translationList}">
            <div class="span2">${translation.key}:</div>
            <div class="span4">${translation.text}</div>
        </c:forEach>
    </div>
</td>