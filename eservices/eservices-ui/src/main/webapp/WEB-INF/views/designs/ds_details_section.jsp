<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="trademark">
        
	<form:form id="dsDetailsForm" cssClass="formEF" modelAttribute="eSDesignDetailsForm">
	    <c:set var="sectionId" value="ds_details_section" scope="request"/>
		<c:set var="imported" value="${eSDesignDetailsForm.imported}" scope="request"/>
		
		<c:set var="jcrUrl" value="${configurationServiceDelegator.getValueFromGeneralComponent('repository.url')}" />
		<c:set var="stringafter" value="${fn:substringAfter(jcrUrl, 'http://')}" />
		<c:set var="stringbefore" value="${fn:substringBefore(stringafter, '/')}" />
		
	    <input type="hidden" id="formReturned" value="true"/>	    
	
	    <form:hidden id="hiddenFormId" path="id"/>
	    
	    <form:hidden id="numberOfRenewalsId" path="numberOfRenewals"/>
	    
	    <form:hidden id="importedId" path="imported"/>
		<form:hidden id="unpublishedHidden" path="unpublished"/>
	    
	    <c:set var="hiddenDSStatus" value="true"/>
	    <component:generic path="dsStatus">
	    	<c:set var="hiddenDSStatus" value="false"/>
	    </component:generic>
	    <c:if test="${hiddenDSStatus eq 'true'}">
	    	<form:hidden id="designStatusId" path="dsStatus"/>
	    </c:if>
	    
	     <c:if test="${empty imported or !imported}">
	    	<input type="hidden" name="imageRepresentationURI" value="${stringbefore}<%=request.getContextPath()%>"/> 
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
	    

	    

	    <component:input path="designIdentifier" checkRender="true" labelTextCode="ds.details.field.designIdentifier" formTagClass="onerow-fields"/>
		
		<div id="representationAttachment" class="fileuploadInfo collectiveSelectors">            
        	<component:file pathFilename="fileWrapperImage" fileWrapperPath="representationAttachment" labelCode="ds.details.field.view"
                        fileWrapper="${eSDesignDetailsForm.representationAttachment}"/>
		</div>
		
	    <div class="inline-fields">	
	    	<component:input path="eSDesignApplicationData.applicationNumber" checkRender="true" labelTextCode="ds.details.field.applicationNumber" formTagClass="onerow-fields"/>
	    </div>
	    
	    <div class="inline-fields">
	    	<component:input path="eSDesignApplicationData.applicationDate" checkRender="true" labelTextCode="ds.details.field.applicationDate" formTagClass="field-date"/>
	    </div>
	    <br>
	    <div class="inline-fields">	
    		<component:input path="registrationNumber" checkRender="true" labelTextCode="ds.details.field.registrationNumber" formTagClass="onerow-fields"/>
		</div>
		<div class="inline-fields">	
    		<component:input path="registrationDate" checkRender="true" labelTextCode="ds.details.field.registrationDate" formTagClass="field-date"/>
  		</div>
    	<component:select items="${configurationServiceDelegator['designStatusList']}" path="dsStatus" itemLabel="value"
                 itemValue="code" checkRender="true"
                 labelTextCode="ds.details.field.dsStatus" formTagClass="long-fields"/>
                 
    	<component:input path="expiryDate" checkRender="true" labelTextCode="ds.details.field.expiryDate" formTagClass="field-date"/>
 
    	<component:input path="eSDesignApplicationData.publicationDate" checkRender="true" labelTextCode="ds.details.field.publicationDate" formTagClass="field-date"/>
    	
    	<component:checkbox path="renewalIndicator" checkRender="true" labelTextCode="ds.details.field.renewalIndicator"></component:checkbox>
    	
    	<hr>
    	<h3><spring:message code="ds.details.holders.title"></spring:message></h3>

    	<c:if test="${imported}">
    		<c:set var="i" value="0" />
	    	<c:forEach var="holder" items="${eSDesignDetailsForm.eSDesignApplicationData.holders}" varStatus="hold">
	    		<label>${holder.name}</label>
	    				    	
		    	<component:textarea path="eSDesignApplicationData.holders[${i}].domicile" formTagClass="owner-fields-long"/>
		    	<form:hidden id="applicants[${i}].id" path="eSDesignApplicationData.holders[${i}].id" />
				<form:hidden id="applicants[${i}].imported" path="eSDesignApplicationData.holders[${i}].imported" />
		    	<form:hidden id="applicants[${i}].name" path="eSDesignApplicationData.holders[${i}].name" />
		    	<form:hidden id="applicants[${i}].domicileCountry" path="eSDesignApplicationData.holders[${i}].domicileCountry" />
		    	<form:hidden id="applicants[${i}].contactPerson" path="eSDesignApplicationData.holders[${i}].contactPerson" />
		    	<form:hidden id="applicants[${i}].internalApplicantId" path="eSDesignApplicationData.holders[${i}].internalApplicantId" />
		    	
		    	<form:hidden id="applicants[${i}].address.street" path="eSDesignApplicationData.holders[${i}].address.street" />
		    	<form:hidden id="applicants[${i}].address.houseNumber" path="eSDesignApplicationData.holders[${i}].address.houseNumber" />
		    	<form:hidden id="applicants[${i}].address.city" path="eSDesignApplicationData.holders[${i}].address.city" />
		    	<form:hidden id="applicants[${i}].address.stateprovince" path="eSDesignApplicationData.holders[${i}].address.stateprovince" />
		    	<form:hidden id="applicants[${i}].address.postalCode" path="eSDesignApplicationData.holders[${i}].address.postalCode" />
		    	<form:hidden id="applicants[${i}].address.country" path="eSDesignApplicationData.holders[${i}].address.country" />
		    	
		    	<form:hidden id="applicants[${i}].phone" path="eSDesignApplicationData.holders[${i}].phone" />
		    	<form:hidden id="applicants[${i}].fax" path="eSDesignApplicationData.holders[${i}].fax" />
		    	<form:hidden id="applicants[${i}].email" path="eSDesignApplicationData.holders[${i}].email" />
		    	<form:hidden id="applicants[${i}].website" path="eSDesignApplicationData.holders[${i}].website" />
		    	<form:hidden id="applicants[${i}].nationalOfficialBusinessRegister" path="eSDesignApplicationData.holders[${i}].nationalOfficialBusinessRegister" />
		    	<form:hidden id="applicants[${i}].receiveCorrespondenceViaEmail" path="eSDesignApplicationData.holders[${i}].receiveCorrespondenceViaEmail" />
		    	<form:hidden id="applicants[${i}].taxIdNumber" path="eSDesignApplicationData.holders[${i}].taxIdNumber" />
		    	<form:hidden id="applicants[${i}].consentForPublishingPersonalInfo" path="eSDesignApplicationData.holders[${i}].consentForPublishingPersonalInfo" />
		    	<form:hidden id="applicants[${i}].partOfEEA" path="eSDesignApplicationData.holders[${i}].partOfEEA" />
		    	<form:hidden id="applicants[${i}].type" path="eSDesignApplicationData.holders[${i}].type" />
		    	<form:hidden id="applicants[${i}].currentUserIndicator" path="eSDesignApplicationData.holders[${i}].currentUserIndicator" />

		    	<c:set var="i" value="${i+1}" />
	    	</c:forEach>

			<hr>
			<h3><spring:message code="form.representatives.title"></spring:message></h3>

			<c:set var="i" value="0" />
			<c:forEach var="repr" items="${eSDesignDetailsForm.eSDesignApplicationData.representatives}" varStatus="r">
				<label>${repr.name} (${repr.id})</label>
				<form:hidden id="representatives[${i}].imported" path="eSDesignApplicationData.representatives[${i}].imported" />
				<form:hidden id="representatives[${i}].id" path="eSDesignApplicationData.representatives[${i}].id" />
				<form:hidden id="representatives[${i}].name" path="eSDesignApplicationData.representatives[${i}].name" />
				<c:set var="i" value="${i+1}" />
			</c:forEach>
    	</c:if>
    	
    	<c:if test="${!imported}">
    		<div class="holdersDSContainer">
    		<div class="inline-fields">
				<label><spring:message code="ds.details.field.applicant" /></label>
			</div>
			<div class="right-fields">
				<label><spring:message code="ds.details.field.domicile" /></label>
			</div>
    		<c:set var="holdersList" value="${eSDesignDetailsForm.eSDesignApplicationData.holders}" ></c:set>
	    	<c:if test="${!empty holdersList}">
		    	<c:set var="i" value="0" />
		    	<c:forEach items="${holdersList}" var="holder">
		  	    	<div class="ownerAdded" style="display:block">	
					   	<div class="inline-fields">
					   		<input type="text" maxlength="100" size="100" value="${holder.name}" disabled="disabled" class="onerow-fields" name="applicant" id="applicant">
					   	</div>
					   	<div class="owners-fields">
					   		<input type="text" maxlength="100" size="100" value="${holder.domicile}" disabled="disabled" class="onerow-fields" name="domicile" id="domicile">
					   		<a class="removeHolderDS" ><i class="minus-icon"></i></a>
					   	</div>
					</div>
			    	<c:set var="i" value="${i+1}" />
	    		</c:forEach>
	    	</c:if>
		    	<div class="holderDSAdded" style="display:block">	
				   	<div class="inline-fields">
				   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="applicant" id="applicant">
				   	</div>
				   	<div class="owners-fields">
				   		<input type="text" maxlength="100" size="100" value="" class="onerow-fields" name="domicile" id="domicile">
				   		<a class="addHolderDS" ><i class="add-icon"></i></a>
				   	</div>
				</div>
	    	</div>
    	</c:if>
    	
    	<hr>
    	
    	
	</form:form>
	
	<c:set var="sectionId" value="ds_details_section" scope="request" />
		<component:generic path="locarnoEnabled" checkRender="true">
    	<h3><spring:message code="ds.details.lorcano.title"></spring:message></h3>
    		<%-- PRODUCT INDICATION --%>    		
			<c:if test="${imported}">
				<jsp:include page="locarno/locarno_section_list.jsp">
					<jsp:param name="viewMode" value="true" />
				</jsp:include>
			</c:if>
			<c:if test="${!imported}">
				<jsp:include page="locarno/locarno_section_list.jsp"/>			
		   		<jsp:include page="locarno/locarno_buttons.jsp"/>
		   	</c:if>
			</component:generic>
			<%-- END PRODUCT INDICATION --%>
			<c:if test="${!imported}">
    			<jsp:include page="locarno/locarno_section.jsp" />
    		</c:if>
    	<hr>
	
</section>