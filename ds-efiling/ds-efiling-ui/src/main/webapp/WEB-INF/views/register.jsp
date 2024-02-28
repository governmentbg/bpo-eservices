<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sec:authorize var="authenticatedUser" access="isAuthenticated()" />

<c:if test="${!authenticatedUser}">
	<h4><spring:message code="registration.label"/></h4>
	<spring:eval var="cas_registration_url" expression="@propertyConfigurer.getProperty('cas.registration.url')" />
	<c:set var="cas_callback" value="${not empty cas_registration_url ? cas_registration_url.trim().replaceAll('\\\\{callback\\\\}', pageContext.request.contextPath.concat('/callback.htm')) : null}"/>

	<iframe id="frameExternal" src="${cas_callback}" style="float:left;border:0px;z-index:100;" >
	</iframe>
</c:if>


<script type="text/javascript">
	$('iframe').load(function() {
		setInterval(startApplication($(this)), 3000);
	});
	
	function startApplication(iframe) {
		$('#rightBar').hide();
		$(iframe).width('980px');
		try {
			$(iframe).height(iframe.contentWindow.document.body.offsetHeight);
		} catch (err) {
			//Set default height if applications are not on the same domain
			$(iframe).height('600px');
		}
	}
</script>