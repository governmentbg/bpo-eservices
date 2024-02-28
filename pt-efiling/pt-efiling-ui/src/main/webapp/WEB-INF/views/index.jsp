<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 30.1.2019 г.
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sp.css">
</head>
<body>


<div class="main">
    <div class="index-column">
        <div class="center">
        <div class="btn_action">
            <button type="button" rel="next" onclick="window.open('pt-efiling.htm');" data-val="Next" class="text-big navigateBtn">
                <spring:message code="layout.mainTitle.pt-efiling"/>
            </button>

            <br>

            <button type="button" rel="next" onclick="window.open('um-efiling.htm');" data-val="Next" class="text-big navigateBtn">
                <spring:message code="layout.mainTitle.um-efiling"/>
            </button>

            <br>

            <button type="button" rel="next" onclick="window.open('ep-efiling.htm');" data-val="Next" class="text-big navigateBtn">
                <spring:message code="layout.mainTitle.ep-efiling"/>
            </button>

            <br>

            <button type="button" rel="next" onclick="window.open('sv-efiling.htm');" data-val="Next" class="text-big navigateBtn">
                <spring:message code="layout.mainTitle.sv-efiling"/>
            </button>

            <br>

            <button type="button" rel="next" onclick="window.open('spc-efiling.htm');" data-val="Next" class="text-big navigateBtn">
                <spring:message code="layout.mainTitle.spc-efiling"/>
            </button>

            <br>

            <button type="button" rel="next" onclick="window.open('is-efiling.htm');" data-val="Next" class="text-big navigateBtn">
                <spring:message code="layout.mainTitle.is-efiling"/>
            </button>
        </div>
    </div>
</div>
</div>

</body>
</html>