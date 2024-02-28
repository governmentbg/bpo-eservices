<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
                
<c:set var="websiteCode" value="person.contactDetails.field.website"/>

<c:if test="${not empty param.websiteFieldMessageCode}">
    <c:set var="websiteCode" value="${param.websiteFieldMessageCode}"/>
</c:if>

<div>
<component:input path="internationalPrefix" checkRender="true"
                labelTextCode="person.contactDetails.field.internationalPrefix"/>
                
<component:generic path="fixed" checkRender="true">                
	<div class="phone-numbers">
	    <component:input path="fixed" checkRender="true" formTagClass="phone-input"
	                    labelTextCode="person.contactDetails.field.fixedNumber"/>
	</div>
</component:generic>

<component:generic path="phone" checkRender="true">                
	<div class="phone-numbers">
	    <component:input path="phone" checkRender="true" formTagClass="phone-input"
	                    labelTextCode="person.contactDetails.field.phoneNumber"/>
	</div>
</component:generic>
	
<component:generic path="fax" checkRender="true">	
	<div class="phone-numbers">
	    <component:input path="fax" checkRender="true" formTagClass="phone-input"
	                    labelTextCode="person.contactDetails.field.faxNumber"/>
	</div>
</component:generic>	

<component:input path="email" checkRender="true"
                labelTextCode="person.contactDetails.field.email"/>

<component:input path="website" checkRender="true"
                labelTextCode="${websiteCode}"/>          
                
</div>