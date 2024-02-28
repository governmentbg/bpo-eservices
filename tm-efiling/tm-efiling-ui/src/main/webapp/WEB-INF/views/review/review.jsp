<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="review" id="review">
	<header>
        <h2><spring:message code="review.title"/></h2>
    </header>
    <p>
    	<spring:message code="review.text.initial.${flowModeId}"/>
    </p>
    <c:if test="${flowModeId == 'wizard'}">
        <jsp:include page="/WEB-INF/views/review/review_mark.jsp" />
    </c:if>
    <c:if test="${flowModeId == 'gi-efiling'}">
        <jsp:include page="/WEB-INF/views/review/review_gi.jsp" />
    </c:if>
    
    <jsp:include page="/WEB-INF/views/review/review_goodsservices.jsp"/>

    <jsp:include page="/WEB-INF/views/review/review_person.jsp" />

    <!--
        <jsp:include page="/WEB-INF/views/review/review_nationalsearch.jsp"/>
    -->

    <c:if test="${flowModeId == 'wizard'}">
        <jsp:include page="/WEB-INF/views/review/review_claim.jsp"/>
    </c:if>
    <div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
        /trademark/register-trademark/review
    </div>
</section>