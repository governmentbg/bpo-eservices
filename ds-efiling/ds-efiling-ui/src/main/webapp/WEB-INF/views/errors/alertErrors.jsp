<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
%><c:if test="${not empty alertMessage}"
	><spring:message code="${alertMessage}" 
/></c:if>