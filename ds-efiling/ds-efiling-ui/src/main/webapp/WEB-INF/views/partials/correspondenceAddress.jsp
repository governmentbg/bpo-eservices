<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<input type="hidden" id="correspondenceAddressPath" value="${param.path}[0]"/>

<input type="hidden" id="numberOfCA" value="${param.size}"/>

<div class="correspondenceAddressList">
	<span id="correspondenceAddressList">
	<c:if test="${param.size > 0}">
	    <% for (int i = 0; i < Integer.parseInt(request.getParameter("size")); i++)
	    {%>
	    <c:set var="index" value="<%=i%>"/>
	        <span id="CAwrapper_${param.path}[${index}]">
	            <label class="personFormSection"> <spring:message code="person.field.correspondenceAddress"/> </label>
	            <jsp:include page="/WEB-INF/views/common/correspondent/correspondenceAddressItem.jsp">
	                <jsp:param name="path" value="${param.path}[${index}]"/>
	                <jsp:param name="itemFilterValue" value="${param.itemFilterValue}"/>
	            </jsp:include>
	            <ul id="correspondenceAddress_Options" class="controls">
	                <li>
	                    <a class="btn removeCA"><spring:message code="person.field.correspondenceAddress.remove"/></a>
	                </li>
	                <li>
	                    <a class="btn bgAdd addAnotherCA"><spring:message code="person.field.correspondenceAddress.add"/></a>
	                </li>
	            </ul>
	            <br>
	        </span>
	    <%}%>
	</c:if>
	</span>
	
	
	<span id="correspondenceAddressTemplate" class="hidden">
	    <c:if test="${param.size eq 0}">
	        <label class="personFormSection"> <spring:message code="person.field.correspondenceAddress"/> </label>
	        <jsp:include page="/WEB-INF/views/common/correspondent/correspondenceAddressItem.jsp">
	            <jsp:param name="path" value="${param.path}[0]"/>
	        </jsp:include>
	        <ul id="correspondenceAddress_Options" class="controls">
	            <li>
	                <a class="btn removeCA"><spring:message code="person.field.correspondenceAddress.remove"/></a>
	            </li>
	            <li>
	                <a class="btn bgAdd addAnotherCA"><spring:message code="person.field.correspondenceAddress.add"/></a>
	            </li>
	        </ul>
	        <br>
	    </c:if>
	</span>
</div>