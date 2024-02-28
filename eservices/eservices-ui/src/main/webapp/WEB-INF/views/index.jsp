<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sp.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>
</head>
<body>

<div class="two-column">
	<div class="left">
		<h3><spring:message code="index.tm.title"/></h3>
		<div class="btn_action">
			<c:forEach var="flow" items="${flows}">
				<c:if test="${fn:contains(flow, 'tm-')}">
					<button type="button" rel="next" onclick="window.open('<c:url value="${flow}.htm"/>');" data-val="Next" class="text-big navigateBtn btn-primary"><spring:message code="${flow}.layout.mainTitle"/></button>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<div class="right">
		<h3><spring:message code="index.ds.title"/></h3>
		<div class="btn_action">
			<c:forEach var="flow" items="${flows}">
				<c:if test="${fn:contains(flow, 'ds-')}">
					<button type="button" rel="next" onclick="window.open('<c:url value="${flow}.htm"/>');" data-val="Next" class="text-big navigateBtn btn-primary"><spring:message code="${flow}.layout.mainTitle"/></button>
				</c:if>
			</c:forEach>
		</div>
	</div>
</div>

</body>
</html>

<script type="text/javascript">
	$(document).ready(
	function () {
		var lsorted = $(".left > .btn_action").children().sort(function(a, b) { return ((a.innerText < b.innerText) ? -1 : ((a.innerText > b.innerText) ? 1 : 0))});
		$(".left > .btn_action").children().each(function() { this.remove() });
		$(".left > .btn_action").append(lsorted);
		var rsorted = $(".right > .btn_action").children().sort(function(a, b) { return ((a.innerText < b.innerText) ? -1 : ((a.innerText > b.innerText) ? 1 : 0))});
		$(".right > .btn_action").children().each(function() { this.remove() });
		$(".right > .btn_action").append(rsorted);
	});
</script>