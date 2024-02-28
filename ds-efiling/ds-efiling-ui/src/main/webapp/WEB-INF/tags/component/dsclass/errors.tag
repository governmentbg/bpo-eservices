<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:directive.attribute name="path" required="false" type="java.lang.String" description="the path of the validation errors"/>

<%--
// <spring:hasBindErrors> checks only the nested path so we need to get it and remove the final "." if any.
String pathToUse = (String)jspContext.getAttribute("nestedPath", PageContext.REQUEST_SCOPE);
if (pathToUse != null && pathToUse.endsWith(".")) {
	pathToUse = pathToUse.substring(0, pathToUse.length() - 1);
}
jspContext.setAttribute("pathToUse", pathToUse);
--%>
<%-- 
<spring:hasBindErrors name="${pathToUse}"> 
	<spring:bind path="${path}">
	  <c:if test="${status.error}">
	  	<c:forEach items="${status.errorMessages}" var="message" >
	          	${message}
	    </c:forEach>
	  </c:if>
	</spring:bind>
</spring:hasBindErrors>
--%>
<form:errors path="${path}" >
  <c:forEach items="${messages}" var="message">${message}</c:forEach>         
</form:errors>