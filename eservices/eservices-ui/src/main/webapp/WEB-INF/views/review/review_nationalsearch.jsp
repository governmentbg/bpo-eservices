<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="national-search">
	<header>
		<spring:message code="review.nationalSearch.details"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_NationalSearch"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div><p>
		<c:choose>
		    <c:when test="${(not empty flowBean.mainForm.nationalSearchReport) and flowBean.mainForm.nationalSearchReport}">
		        <spring:message code="review.nationalSearch.yes"/>
		    </c:when>
		    <c:otherwise>
		        <spring:message code="review.nationalSearch.no"/>
			</c:otherwise>
		</c:choose>		
	</p></div>
</div>