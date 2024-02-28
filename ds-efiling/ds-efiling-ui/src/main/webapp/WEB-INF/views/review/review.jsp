<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="review" id="reviewPage">
	<header>
		<a href="#reviewPage" class="anchorLink"></a>
        <h2><spring:message code="review.title"/></h2>
    </header>
    <p>
    	<spring:message code="review.text.initial"/>
    </p>
    
    <jsp:include page="/WEB-INF/views/review/review_design.jsp" />
    
    <jsp:include page="/WEB-INF/views/review/review_person.jsp" />
    
    <jsp:include page="/WEB-INF/views/review/review_designer.jsp" />
    
    <jsp:include page="/WEB-INF/views/review/review_claim.jsp"/>
    
</section>

<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /design/register-design/review
</div>