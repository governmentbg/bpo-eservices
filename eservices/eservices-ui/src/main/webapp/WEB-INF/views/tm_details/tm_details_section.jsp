<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>


<section class="trademark">
        
	<form:form id="tmDetailsForm" cssClass="formEF" modelAttribute="tmDetailsForm">
	    <c:set var="sectionId" value="tm_details_section" scope="request"/>
		<c:set var="imported" value="${tmDetailsForm.imported}" scope="request"/>

		
	    <input type="hidden" id="formReturned" value="true"/>
	
	    <form:hidden id="hiddenFormId" path="id"/>
	    <form:hidden id="hiddenUnpublished" path="unpublished"/>
		<form:hidden id="hiddenRegistrationOffice" path="registrationOffice" />
		<form:hidden id="hiddenApplicationLanguage" path="applicationLanguage"/>
	    
	    <form:hidden id="importedId" path="imported"/>
	    
	    <c:set var="hiddenTMStatus" value="true"/>
	    <component:generic path="tradeMarkStatus">
	    	<c:set var="hiddenTMStatus" value="false"/>
	    </component:generic>
	    <c:if test="${hiddenTMStatus eq 'true'}">
	    	<form:hidden id="tradeMarkStatusId" path="tradeMarkStatus"/>
	    </c:if>
	    
	    <c:if test="${empty imported or !imported}">
	    	<input type="hidden" name="imageRepresentationURI" /> 
	    </c:if>
	    <c:if test="${imported}">
	    	<form:hidden id="imageRepresentationURIFormId" path="imageRepresentationURI"/> 
	    </c:if>
	    
	    <component:generic path="formWarnings" checkRender="false">
	    	<c:set var="errors">
	        	<form:errors path="formWarnings"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<div id="customWarningMessage" class="hidden">${message}</div>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>	    
	    
	    <component:generic path="formMessages" checkRender="true">
	    	<c:set var="errors">
	        	<form:errors path="formMessages"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<p class="flMessageError permanentMessage" id="${path}ErrorMessageServer">${message}</p>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>
	    
	    <c:if test="${imported}">
	    	<label class="mark-name">
				<c:if test="${not empty tmDetailsForm.applicationRepresentationAlternative}">
					<c:out value="${tmDetailsForm.applicationRepresentationAlternative.originalValue}"/>
				</c:if>
			</label>
	    	<form:hidden id="applicationRepresentationId" path="applicationRepresentationAlternative"/>
	    </c:if>
	    
	    <c:if test="${!imported}">
	    	<component:input path="applicationRepresentation" checkRender="true" labelTextCode="tm.details.field.applicationRepresentation" formTagClass="onerow-fields"/>
	    </c:if>
	    
	    <div id="representationAttachment" class="fileuploadInfo collectiveSelectors">            
        	<component:file pathFilename="fileWrapperImage" fileWrapperPath="representationAttachment"
                        fileWrapper="${tmDetailsForm.representationAttachment}"/>
		</div>
		
	    <div class="inline-fields half_pct">
	    	<component:input path="applicationNumber" checkRender="true" labelTextCode="tm.details.field.applicationNumber" formTagClass="onerow-fields"/>
	    </div>
	    <div class="right-fields">
	    	<component:select items="${configurationServiceDelegator['tradeMarkKinds']}" path="tradeMarkKind" itemLabel="value"
                 itemValue="code" itemFilter="${param.itemFilterValue}" checkRender="true"
                 labelTextCode="tm.details.field.tmKind" formTagClass="onerow-fields"/>
	    </div>
	    <br  />
	    <div class="inline-fields half_pct">
	    	<component:input path="applicationDate" checkRender="true" labelTextCode="tm.details.field.applicationDate" formTagClass="field-date"/>
	    </div>
	    
	    <div class="right-fields">
			<component:select items="${configurationServiceDelegator['tradeMarkTypes']}" path="tradeMarkType" itemLabel="value"
							  itemValue="code" checkRender="true"
							  labelTextCode="tm.details.field.tmType" formTagClass="onerow-fields"/>
	    </div>
	    <br  />
	    
    	<component:input path="registrationNumber" checkRender="true" labelTextCode="tm.details.field.registrationNumber" formTagClass="onerow-fields"/>

    	<component:input path="registrationDate" checkRender="true" labelTextCode="tm.details.field.registrationDate" formTagClass="field-date"/>
  
    	<component:select items="${configurationServiceDelegator['tradeMarkStatusList']}" path="tradeMarkStatus" itemLabel="value"
                 itemValue="code" checkRender="true"
                 labelTextCode="tm.details.field.tradeMarkStatus" formTagClass="onerow-fields"/>
                 
    	<component:input path="expiryDate" checkRender="true" labelTextCode="tm.details.field.expiryDate" formTagClass="field-date"/>
 
    	<component:input path="publicationDate" checkRender="true" labelTextCode="tm.details.field.publicationDate" formTagClass="field-date"/>
    	
    	<component:input path="markDisclaimer" checkRender="true" labelTextCode="tm.details.field.markDisclaimer" formTagClass="onerow-fields"/>
    	
    	<hr>
    	<h3><spring:message code="tm.details.owners.title"></spring:message></h3>

    	<c:if test="${imported}">
    		<c:set var="i" value="0" />
	    	<c:forEach var="owner" items="${tmDetailsForm.applicants}" varStatus="app">
	    		<label>${owner.name} (${owner.id})</label>
		    	<component:textarea path="applicants[${i}].domicile" formTagClass="owner-fields-long"/>
				<form:hidden id="applicants[${i}].imported" path="applicants[${i}].imported" />
		    	<form:hidden id="applicants[${i}].id" path="applicants[${i}].id" />
		    	<form:hidden id="applicants[${i}].name" path="applicants[${i}].name" />
		    	<form:hidden id="applicants[${i}].domicileCountry" path="applicants[${i}].domicileCountry" />
		    	<form:hidden id="applicants[${i}].contactPerson" path="applicants[${i}].contactPerson" />
		    	<form:hidden id="applicants[${i}].internalApplicantId" path="applicants[${i}].internalApplicantId" />
		    	
		    	<form:hidden id="applicants[${i}].address.street" path="applicants[${i}].address.street" />
		    	<form:hidden id="applicants[${i}].address.houseNumber" path="applicants[${i}].address.houseNumber" />
		    	<form:hidden id="applicants[${i}].address.city" path="applicants[${i}].address.city" />
		    	<form:hidden id="applicants[${i}].address.stateprovince" path="applicants[${i}].address.stateprovince" />
		    	<form:hidden id="applicants[${i}].address.postalCode" path="applicants[${i}].address.postalCode" />
		    	<form:hidden id="applicants[${i}].address.country" path="applicants[${i}].address.country" />
		    	
		    	<form:hidden id="applicants[${i}].phone" path="applicants[${i}].phone" />
		    	<form:hidden id="applicants[${i}].fax" path="applicants[${i}].fax" />
		    	<form:hidden id="applicants[${i}].email" path="applicants[${i}].email" />
		    	<form:hidden id="applicants[${i}].website" path="applicants[${i}].website" />
		    	<form:hidden id="applicants[${i}].nationalOfficialBusinessRegister" path="applicants[${i}].nationalOfficialBusinessRegister" />
		    	<form:hidden id="applicants[${i}].receiveCorrespondenceViaEmail" path="applicants[${i}].receiveCorrespondenceViaEmail" />
		    	<form:hidden id="applicants[${i}].taxIdNumber" path="applicants[${i}].taxIdNumber" />
		    	<form:hidden id="applicants[${i}].consentForPublishingPersonalInfo" path="applicants[${i}].consentForPublishingPersonalInfo" />
		    	<form:hidden id="applicants[${i}].partOfEEA" path="applicants[${i}].partOfEEA" />
		    	<form:hidden id="applicants[${i}].type" path="applicants[${i}].type" />
		    	<form:hidden id="applicants[${i}].currentUserIndicator" path="applicants[${i}].currentUserIndicator" />

		    	<c:set var="i" value="${i+1}" />
	    	</c:forEach>

			<hr>
			<h3><spring:message code="form.representatives.title"></spring:message></h3>

			<c:set var="i" value="0" />
			<c:forEach var="repr" items="${tmDetailsForm.representatives}" varStatus="r">
				<label>${repr.name} (${repr.id})</label>
				<form:hidden id="representatives[${i}].imported" path="representatives[${i}].imported" />
				<form:hidden id="representatives[${i}].id" path="representatives[${i}].id" />
				<form:hidden id="representatives[${i}].name" path="representatives[${i}].name" />
				<c:set var="i" value="${i+1}" />
			</c:forEach>
    	</c:if>
    	
    	<c:if test="${!imported}">
    		<div class="applicantsContainer">
    		<div class="inline-fields">
				<label><spring:message code="tm.details.field.applicant" /></label>
			</div>
			<div class="right-fields">
				<label><spring:message code="tm.details.field.domicile" /></label>
			</div>
    		<c:set var="ownersList" value="${tmDetailsForm.applicants}" ></c:set>
	    	<c:if test="${!empty ownersList}">
		    	<c:set var="i" value="0" />
		    	<c:forEach items="${ownersList}" var="owner">
		  	    	<div class="ownerAdded" style="display:block">	
					   	<div class="inline-fields">
					   		<input type="text" maxlength="100" size="100" value="${owner.name}" disabled="disabled" class="onerow-fields" name="applicant" id="applicantOwner">
					   	</div>
					   	<div class="owners-fields">
					   		<input type="text" maxlength="100" size="100" value="${owner.domicile}" disabled="disabled" class="onerow-fields" name="domicile" id="domicile">
					   		<a class="removeApplicant" ><i class="minus-icon"></i></a>
					   	</div>
					</div>
			    	<c:set var="i" value="${i+1}" />
	    		</c:forEach>
	    	</c:if>
		    	<div class="ownerAdded" style="display:block">	
				   	<div class="inline-fields">
				   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="applicant" id="applicantOwner">
				   	</div>
				   	<div class="owners-fields">
				   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="domicile" id="domicile">
				   		<a class="addOwner" ><i class="add-icon"></i></a>
				   	</div>
				</div>
	    	</div>
    	</c:if>
    	
    	<hr>
    	    	
    	<c:if test="${imported}">
	    	<component:generic path="extent" checkRender="true">
				<div id="extentChooseMessage" style="display: none"  class="alert alert-info">
					<spring:message code="${flowModeId}.extent.choose.message" />
				</div>

				<div id="extent">
					<label><spring:message code="tm.details.field.extent"/></label>
					<component:radiobutton path="extent" checkRender="true" labelTextCode="${flowModeId}.tm.details.field.completeExtent" formTagClass="allGS" value="true"/>
				<component:radiobutton path="extent" checkRender="true" labelTextCode="${flowModeId}.tm.details.field.partialExtent" formTagClass="someGS" value="false" />
				</div>
			</component:generic>
			<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="extent.warning" checkRender="true">
				<div id="markExtentPartialWarning" style="display: none"  class="alert alert-danger">
					<spring:message code="${flowModeId}.gshelper.form.declaration.partial" />
				</div>
			</tags:render>
		</c:if>
		<div id="tmGSCommentDiv">
			<component:textarea path="goodsServicesComment" formTagClass="textarea-fields" checkRender="true" labelTextCode="${flowModeId}.tm.details.field.gs.comment"
			tipCode="gs.comments.tip"/>
		</div>
	</form:form>
</section>

<div id="gsSection">
	<jsp:include page="/WEB-INF/views/goods_services/goodsservices.jsp" />
</div>