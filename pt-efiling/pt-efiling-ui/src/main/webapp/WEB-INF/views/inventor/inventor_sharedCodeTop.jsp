<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<span class="importErrorPlaceholder"></span>


<input type="hidden" id="maxCorrespondenceAddresses" value="${configurationServiceDelegator.getValue('person.inventor.correspondenceAddresses.max', 'eu.ohim.sp.core.person')}">
<input type="hidden" id="service_applicantMatches_enabled" value="${configurationServiceDelegator.isServiceEnabled('Inventor_Match')}"/>
       
<c:set var="imported" value="${inventorForm.imported}" scope="request" />
	
<form:hidden path="imported" id="importedInventor" />

<c:set var="sectionId" value="inventor" scope="request"/>
<component:validateImport path="validateImported" />

<input type="hidden" id="formReturned" value="true" />
<form:hidden id="hiddenFormId" path="id" />

<form:hidden path="mayWaiver" />

