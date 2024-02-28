<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>

<section class="attachments">
	
	<c:choose>
		<c:when test="${flowModeId eq 'tm-revocation'}">
			<div class="row" id="divGExplanationGroundsMarksAttachment">
	            <div class="span3">	

		 			<div id="fileDocumentAttachmentAA" class="fileuploadInfo collectiveSelectors">
		       		<component:file pathFilename="gAExplanationGroundsMarksAttachment" fileWrapperPath="revocationGrounds.gExplanationGroundsMarks" labelCode="revocationGrounds.details.field.explanations"
		                       fileWrapper="${oppositionBasisForm.revocationGrounds.gExplanationGroundsMarks}"/>
		       		</div>
					<tags:render checkRender="true" flowModeId="${flowModeId}" sectionId="${sectionId}" field="revocationGrounds.gExplanationGroundsMarks.explanation" var="explRadio"/>
					<c:set var="displayExpl" value="${ not empty explRadio && explRadio eq true? 'none':'block'}"/>
					<div id="explanationCommentDiv" style="display:${displayExpl}">
						<component:textarea  path="revocationGrounds.explanations" checkRender="true" labelTextCode="revocationGrounds.details.field.explanations" formTagClass="comments"
						tipCode="explanations.textarea.tip"/>
					</div>
		       		<div id="fileDocumentAttachmentAB" class="fileuploadInfo collectiveSelectors">
		       		<component:file pathFilename="gAProposalDecideAttachment" fileWrapperPath="revocationGrounds.gProposalDecide" labelCode="revocationGrounds.details.field.gProposalDecide"
		                       fileWrapper="${oppositionBasisForm.revocationGrounds.gProposalDecide}"/>
		            </div>
		        	<div id="fileDocumentAttachmentAC" class="fileuploadInfo collectiveSelectors">
		       		<component:file pathFilename="gAEvidenceAttachment" fileWrapperPath="revocationGrounds.gEvidence" labelCode="revocationGrounds.details.field.gEvidence"
		                       fileWrapper="${oppositionBasisForm.revocationGrounds.gEvidence}"/>
		            <%--<p class="eServiceMessageWarning"><spring:message code="warning.evidence.later"></spring:message> </p>--%>
	            	</div>
				</div>    	
					
			</div>
		</c:when>
		<c:otherwise>
			<div class="row" id="divGExplanationGroundsMarksAttachment">
	            <div class="span3">
		
		 			<div id="fileDocumentAttachmentAA" class="fileuploadInfo collectiveSelectors">
		       		<component:file pathFilename="gAExplanationGroundsMarksAttachment" fileWrapperPath="absoluteGrounds.gExplanationGroundsMarks" labelCode="absoluteGrounds.details.field.explanations"
		                       fileWrapper="${oppositionBasisForm.absoluteGrounds.gExplanationGroundsMarks}"/>
		       		</div>
					<tags:render checkRender="true" flowModeId="${flowModeId}" sectionId="${sectionId}" field="absoluteGrounds.gExplanationGroundsMarks.explanation" var="explRadio"/>
					<c:set var="displayExpl" value="${ not empty explRadio && explRadio eq true? 'none':'block'}"/>
					<div id="explanationCommentDiv" style="display:${displayExpl}">
						<component:textarea  path="absoluteGrounds.explanations" checkRender="true" labelTextCode="absoluteGrounds.details.field.explanations" formTagClass="comments"
						tipCode="explanations.textarea.tip"/>
					</div>
		       		<div id="fileDocumentAttachmentAB" class="fileuploadInfo collectiveSelectors">
		       		<component:file pathFilename="gAProposalDecideAttachment" fileWrapperPath="absoluteGrounds.gProposalDecide" labelCode="absoluteGrounds.details.field.gProposalDecide"
		                       fileWrapper="${oppositionBasisForm.absoluteGrounds.gProposalDecide}"/>
		            </div>
		        	<div id="fileDocumentAttachmentAC" class="fileuploadInfo collectiveSelectors">
		       		<component:file pathFilename="gAEvidenceAttachment" fileWrapperPath="absoluteGrounds.gEvidence" labelCode="absoluteGrounds.details.field.gEvidence"
		                       fileWrapper="${oppositionBasisForm.absoluteGrounds.gEvidence}"/>
	            	</div>
				</div>    	
					
			</div>
		</c:otherwise>
	
	</c:choose>	
	 
</section>