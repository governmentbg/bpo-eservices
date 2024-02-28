<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="sectionId" value="ds_details_section" scope="request"/>

<div class="holderDSAdded" style="display:block">	
   	<div class="inline-fields">
   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="applicant" id="applicant">
   	</div>
   	<div class="owners-fields">
   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="domicile" id="domicile">
   		<a class="addHolderDS" ><i class="add-icon"></i></a>
   	</div>
</div>