<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/signature.js"></script>

<tiles:useAttribute id="sections_list" name="signature_content" classname="java.util.List"/>

<section class="signature" id="signature">

    <header>
        <a href="#signature" class="anchorLink"></a>

        <h2>
            <spring:message code="signature.title"/>
        </h2>
    </header>

    <p class="declaration"><spring:message code="signature.declaration"/></p>


    <div id="firstSignatureDetails">
        <jsp:include page="/WEB-INF/views/signature/signature.jsp">
            <jsp:param name="nestedPathName" value="signatoryForm"/>
            <jsp:param name="sectionId" value="signature_first"/>
        </jsp:include>
    </div>
    <input id="hasSecondSignature" type="hidden" value="${flowBean.mainForm.addSecondSign}">

    <%--<c:set var="addSig" value=""/>--%>
    <%--<c:set var="secondSig" value="display: none"/>--%>

    <c:if test="${flowBean.mainForm.addSecondSign}">
        <c:set var="addSig" value="display: none"/>
        <c:set var="secondSig" value=""/>
    </c:if>
    <button type="button" id="addSignatureButton" class="btn add-button" data-toggle="button">
        <i class="add-icon"></i>
        <spring:message code="signature.action.add"/>
    </button>

    <button type="button" id="removeSignatureButton" class="btn add-button" data-toggle="button">
        <i class="remove-icon"></i>
        <spring:message code="signature.action.remove"/>
    </button>

    <form:form class="mainForm formEf" modelAttribute="flowBean.mainForm">
        <form:hidden path="addSecondSign" id="addSecondSign"/>
    </form:form>


    <div id="secondSignatureDetails">
        <jsp:include page="/WEB-INF/views/signature/signature.jsp">
            <jsp:param name="nestedPathName" value="secondSignatoryForm"/>
            <jsp:param name="sectionId" value="signature_second"/>
        </jsp:include>
    </div>

</section>