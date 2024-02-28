<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 16.4.2019 г.
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<section class="review" id="review">
    <header>
        <h2>
            <spring:message code="review.title"/>
        </h2>
    </header>

    <c:if test="${flowModeId eq 'spc-efiling'}">
        <jsp:include page="review_spc_patent.jsp"/>
    </c:if>

    <jsp:include page="review_patent.jsp"/>

    <c:if test="${flowModeId eq 'sv-efiling'}">
        <jsp:include page="review_technical_questionnaire.jsp"/>
    </c:if>

    <c:if test="${!(flowModeId eq 'spc-efiling') && !(flowModeId eq 'is-efiling')}">
        <jsp:include page="review_claims.jsp"/>
    </c:if>

    <jsp:include page="review_persons.jsp"/>

    <c:if test="${!(flowModeId eq 'spc-efiling') && !(flowModeId eq 'is-efiling')}">
        <jsp:include page="review_inventors.jsp"/>
    </c:if>

</section>
<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /patent/register-patent/review
</div>