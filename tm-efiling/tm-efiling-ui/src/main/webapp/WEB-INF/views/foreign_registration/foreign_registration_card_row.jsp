<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 9.5.2022 г.
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td>${param.registrationNumber}</td>
<td>${param.registrationDate}</td>
<td>${param.registrationCountry}</td>
<td>
    <a class="edit-icon editForeignRegistrationButton" data-val="${param.foreignRegId}"></a>
    <a class="remove-icon deleteForeignRegistrationButton" data-val="${param.foreignRegId}"></a>
</td>