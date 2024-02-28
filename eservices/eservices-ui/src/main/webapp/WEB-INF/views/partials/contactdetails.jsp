<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:input path="internationalPrefix" checkRender="true"
                labelTextCode="person.contactDetails.field.internationalPrefix"/>
<div class="phone-numbers">
    <component:input path="phone" checkRender="true" formTagClass="phone-input"
                    labelTextCode="person.contactDetails.field.phoneNumber"/>
</div>
<div class="phone-numbers">
    <component:input path="fax" checkRender="true" formTagClass="phone-input"
                    labelTextCode="person.contactDetails.field.faxNumber"/>
</div>

<component:input path="email" checkRender="true"
                labelTextCode="person.contactDetails.field.email"/>
<component:input path="website" checkRender="true"
                labelTextCode="person.contactDetails.field.website"/>
