<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="similar">
    <span>
    <c:if test="${not empty similarMarks}">
        <header>
            <h2>
                <spring:message code="similarMarks.risks"/>
            </h2>
        </header>

        <span id="similarMarksBody">

        <p class="note">
            <spring:message code="similarMarks.searchPoweredBy"/>
        </p>

        <jsp:include page="similarmark_table.jsp"/>
        </span>
    </c:if>
    </span>
</section>