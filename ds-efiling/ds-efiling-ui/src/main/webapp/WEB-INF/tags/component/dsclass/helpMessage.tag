<%@ taglib uri="/tags/fsp-tags" prefix="tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.attribute name="textCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="useFlowModeId" required="false"
                         type="java.lang.Boolean"
                         description="Use the flowMmodeId at the end of the  message label" />

<c:if test="${empty useFlowModeId}">
    <c:set var="useFlowModeId" value="false"/>
</c:if>
<c:if test="${useFlowModeId}">
        <c:set var="textCode" value="${textCode}.${flowModeId}"/>
</c:if>
<tags:helpMessage textKey="${textCode}">
   <spring:message code="${textCode}"/>
</tags:helpMessage>
