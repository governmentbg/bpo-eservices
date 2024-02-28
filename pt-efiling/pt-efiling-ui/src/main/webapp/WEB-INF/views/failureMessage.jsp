<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="flMessageError" style="margin:20px;">
    <div style="font-size: 16pt">
        <div><spring:message code="msg.irrepearable.error"/></div>

        <div>
            <%
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            %>
            <%= sdf.format(d) %>
        </div>
    </div>

</div>