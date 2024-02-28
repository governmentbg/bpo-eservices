<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="sectionId" value="tm_details_section" scope="request"/>

<div class="ownerAdded" style="display:block">	
   	<div class="inline-fields">
   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="applicant" id="applicantOwner">
   	</div>
   	<div class="owners-fields">
   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="domicile" id="domicile">
   		<a class="addOwner" ><i class="add-icon"></i></a>
   	</div>
</div>