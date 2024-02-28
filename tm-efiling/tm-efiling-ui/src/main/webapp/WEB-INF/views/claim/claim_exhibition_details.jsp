<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="addbox claimFields">
       <form:form modelAttribute="exhPriorityForm" id="exhibitionForm" cssClass="fileUploadForm formEF">
           <c:set var="sectionId" value="exhibition" scope="request"/>
           <c:set var="imported" value="${exhPriorityForm.imported}" scope="request"/>

		<header>
			<span class="number"><fmt:formatNumber
					value="${entityPosition}" minIntegerDigits="2" /></span>
			<h3>
				<spring:message code="claim.title.exhibition" />
			</h3>
			<ul class="controls">
				<li><a class="cancelButton"><spring:message
							code="claim.action.cancel" /></a></li>
				<li><button type="button" class="addExhibition">
						<i class="claim-button-add"></i>
						<c:choose>
							<c:when test="${empty exhPriorityForm.id}">
								<spring:message code="claim.action.add" />
							</c:when>
							<c:otherwise>
								<spring:message code="claim.action.save" />
							</c:otherwise>
						</c:choose>
					</button></li>
			</ul>
		</header>
           
           <fieldset>
               <form:hidden path="id"/>
                                                   
            	<component:select items="${configurationServiceDelegator.getExhibitionsByModule('tmefiling')}" labelTextCode="claim.exhibition.field.name" path="exhibitionName"
                             itemLabel="value"
                             itemValue="code" checkRender="true" textSelectedOption="true"/>	
               <component:input path="firstDate" checkRender="true" id="fDisc"
                             labelTextCode="claim.exhibition.field.firstDisclsure"
                             formTagClass="filing-date-input"/>
               
               <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
                   <component:file labelCode="claim.exhibition.document"  pathFilename="fileDocumentAttachment" fileWrapperPath="fileWrapper" fileWrapper="${exhPriorityForm.fileWrapper}"/>
               </div>

			<footer>
				<ul class="controls">
					<li><a class="cancelButton"><spring:message
								code="claim.action.cancel" /></a></li>
						<li><button class="addExhibition" type="button">
						<i class="claim-button-add"></i>
						<c:choose>
							<c:when test="${empty exhPriorityForm.id}">
								<spring:message code="claim.action.add" />
							</c:when>
							<c:otherwise>
								<spring:message code="claim.action.save" />
							</c:otherwise>
						</c:choose>
					</button></li>
				</ul>
			</footer>
           </fieldset>            
       </form:form>
</div>
