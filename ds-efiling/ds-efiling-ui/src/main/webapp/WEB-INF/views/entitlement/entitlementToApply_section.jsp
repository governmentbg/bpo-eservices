<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="entitlement" id="entitlement">
	<c:set var="sectionId" value="entitlement" scope="request" />
	<header>
		<a href="#entitlement" class="anchorLink">
        </a>
	
	    <h2>
	        <spring:message code="entitlement.title"/><span class="requiredTag">*</span>
	    </h2>
	</header>

	<form:form class="mainForm formEf" modelAttribute="flowBean.mainForm.entitlement">
		<div>
			<component:checkbox path="designOfficiary" checkRender="true" 
						labelTextCode="application.entitlement.field.designOfficiary" id="entitlement_designOfficiary"/>
	  		<c:set var="styleDiv" value="display:none;" scope="page"></c:set>
	  		<c:if test="${flowBean.mainForm.entitlement.designOfficiary}">
	  			<c:set var="styleDiv" value="" scope="page"></c:set>
	  		</c:if>
	  		<div id="entitlement_designOfficiary_div" class="fileuploadInfo collectiveSelectors" style="${styleDiv}">
	            <component:file labelCode="application.entitlement.field.designOfficiary.file"
	                            pathFilename="designOfficiaryFiles" fileWrapperPath="designOfficiaryFiles"
	                            fileWrapper="${flowBean.mainForm.entitlement.designOfficiaryFiles}"/>
	        </div>
	                      
			<component:checkbox path="designNotOfficiary" checkRender="true" 
						labelTextCode="application.entitlement.field.designNotOfficiary" id="entitlement_designNotOfficiary"/>
	  		
	  		<c:set var="styleDiv" value="display:none;" scope="page"></c:set>
	  		<c:if test="${flowBean.mainForm.entitlement.designNotOfficiary}">
	  			<c:set var="styleDiv" value="" scope="page"></c:set>
	  		</c:if>
	  		<div id="entitlement_designNotOfficiary_div" class="fileuploadInfo collectiveSelectors" style="${styleDiv}">
				<component:file labelCode="application.entitlement.field.designNotOfficiary.file"
	                            pathFilename="designNotOfficiaryFiles"
	                            fileWrapperPath="designNotOfficiaryFiles"
	                            fileWrapper="${flowBean.mainForm.entitlement.designNotOfficiaryFiles}"/>
	        </div>                       
			<component:checkbox path="notApplicantsWithWaived" checkRender="true"
                       labelTextCode="application.entitlement.field.notApplicantsWithWaived" id="entitlement_notApplicantsWithWaived"/>
			<component:checkbox path="dueToSucession" checkRender="true"
                       labelTextCode="application.entitlement.field.dueToSucession" id="entitlement_dueToSucession"/>
			<component:checkbox path="accordingToAssociationToCompany" checkRender="true"
                       labelTextCode="application.entitlement.field.accordingToAssociationToCompany" id="entitlement_accordingToAssociationToCompany"/>
			<component:checkbox path="transferOfRights" checkRender="true"
                       labelTextCode="application.entitlement.field.transferOfRights" id="entitlement_transferOfRights"/>    
			<c:set var="styleDiv" value="display:none;" scope="page"></c:set>
	  		<c:if test="${flowBean.mainForm.entitlement.transferOfRights}">
	  			<c:set var="styleDiv" value="" scope="page"></c:set>
	  		</c:if>
			<div id="entitlement_transferOfRights_div" style="${styleDiv}">
				<component:input path="dateOfTransfer" checkRender="true" id="wDate"
                         labelTextCode="application.entitlement.field.dateOfTransfer"
                         formTagClass="standard-date-input" showDateHelp="true" />                          
			</div>                         
			<component:checkbox path="otherGrounds" checkRender="true"
                       labelTextCode="application.entitlement.field.otherGrounds" id="entitlement_otherGrounds"/> 
			<c:set var="styleDiv" value="display:none;" scope="page"></c:set>
	  		<c:if test="${flowBean.mainForm.entitlement.otherGrounds}">
	  			<c:set var="styleDiv" value="" scope="page"></c:set>
	  		</c:if>			
			<div style="${styleDiv}" id="entitlement_otherGrounds_div">
				<component:textarea path="otherGroundsDescription" checkRender="true"
            		labelTextCode="entitlement.form.field.otherGroundsDescription" id="otherGroundsDescription" formTagClass="entitlement-textarea"/>
			</div>                                                                                                                                    
		</div>
	</form:form>

	<div class="reviewHowto">
		<span class="requiredTag">*</span> <spring:message code="review.howto"/>
	</div>
</section>

