<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>

<section class="attachments">
	
	<div class="row" id="divGRExplanationGroundsMarksAttachment">
		<div class="span3">
			
				<div class="row" >
					<div class="span3">
						<div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">   
				      		<component:file pathFilename="gRExplanationGroundsMarksAttachment" fileWrapperPath="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gExplanationGroundsMarks" labelCode="relativeGrounds.details.field.explanations"
				                      fileWrapper="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gExplanationGroundsMarks}"/>
				     	 </div>
				    </div>
				</div>


			<tags:render checkRender="true" flowModeId="${flowModeId}" sectionId="${sectionId}" field="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gExplanationGroundsMarks.explanation" var="explRadio"/>
			<c:set var="displayExpl" value="${ not empty explRadio && explRadio eq true? 'none':'block'}"/>
			<div id="explanationCommentDiv" style="display:${displayExpl}">
				<component:textarea  path="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.explanations" checkRender="true" labelTextCode="relativeGrounds.details.field.explanations" formTagClass="comments"
									 tipCode="explanations.textarea.tip"/>
			</div>
				
				
				<div class="row" >
					<div class="span3">
				     	 <div id="fileDocumentAttachmentB" class="fileuploadInfo collectiveSelectors">
				      		<component:file pathFilename="gRProposalDecideAttachment" fileWrapperPath="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gProposalDecide" labelCode="relativeGrounds.details.field.gProposalDecide"
				                      fileWrapper="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gProposalDecide}"/>
				       	</div>
		       		</div>
				</div>
				
				
		       	<div class="row" >
					<div class="span3">
				       	<div id="fileDocumentAttachmentC" class="fileuploadInfo collectiveSelectors">
				      		<component:file pathFilename="gREvidenceAttachment"  fileWrapperPath="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gEvidence" labelCode="relativeGrounds.details.field.gEvidence"
				                      fileWrapper="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gEvidence}"/>
						</div>    	
					</div>
				</div>
				
			<div class="row" >
				<div class="span3">
					<div id="divReputationClaimed" style="display:none">

						
						<label><spring:message code="relativeGrounds.details.field.reputationClaimed"/></label>
						<component:radiobutton id ="reputationClaimedYes" path="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gReputationClaimed.reputationClaimed" checkRender="true" value="true" labelTextCode="relativeGrounds.details.field.reputationClaimed.YES" />
						<component:radiobutton id ="reputationClaimedNot" path="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gReputationClaimed.reputationClaimed" checkRender="true" value="false" labelTextCode="relativeGrounds.details.field.reputationClaimed.NOT"/>

						<div id="divExplanationsClaim" style="display:none">
							<component:textarea path="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gReputationClaimDetails.explanationClaim" checkRender="true" labelTextCode="relativeGrounds.details.field.explanationsClaim" formTagClass="comments"/>
						</div>
						<div id="fileDocumentAttachmentReputation" class="fileuploadInfo collectiveSelectors" style="display:none">		
						     <component:file pathFilename="gRReputationClaimAttachment"  fileWrapperPath="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gReputationClaimDetails.attachments" labelCode=""
						            	fileWrapper="${oppositionBasisForm.relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gReputationClaimDetails.attachments}"/>
						</div>
						<component:generic path="relativeGrounds.earlierEntitlementRightInf.groundsRelativeForOpposition.gROCountryReputationClaimed" checkRender="true">
							<div id= "divReputationCountriesSection" style="display:none">
								<br>
								<div id="divTitleCountry" style="display:none">
									<spring:message code="relativeGrounds.details.field.reputationCountriesTitle"/>
								</div>
								<div id="divReputationCountries">
									
									<div id="divCountries">
											
									</div>
									
								</div>
								
								<div id="divTemplate" class="templateRep">
									<ul class="country-list notRemovableClass">
										<li class="country-valid"><span class="name"></span><a rel="country-tooltip" title="<spring:message code="rc.button.label.remove" />" class="country-close"></a></li>
									</ul>
								</div>
								
								<div id="divTemplateNotRemovable" class="templateRep">
									<ul class="country-list notRemovableClass">
										<li class="country-valid"><span class="name"></span></li>
									</ul>
								</div>
								
								<div style="display:none">
									<c:choose>
										<c:when test="${formEdit && not empty flowBean.getReputationCountries()}">
											<c:set var="editCountries" value=""/>
											<c:forEach items="${flowBean.getReputationCountries()}" var="repCountryEdit" >
												<c:choose>
													<c:when test="${editCountries eq ''}">
														<c:set var="editCountries" value="${repCountryEdit}"/>
													</c:when>
													<c:otherwise>
														<c:set var="editCountries" value="${editCountries};${repCountryEdit}"/>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<input id="inputCountries" value="${editCountries}" class="something" />
										</c:when>
										<c:otherwise>
										<input id="inputCountries" value="" class="nothing"/>
										</c:otherwise>
									</c:choose>
									
								</div>
								
								
								
								<div id="divAllCountries" style="display:none">
									<select name="repcountries" id="repcountries">
										<c:forEach items="${configurationServiceDelegator['countries']}" var="countryRep" >
											<option value="${countryRep.code}"><spring:message code="${countryRep.value}"/></option>
										</c:forEach>
									</select>
								</div>
								<div id="divEUCountries" style="display:none">
									<select name="repcountriesEU" id="repcountriesEU">
										<c:forEach items="${configurationServiceDelegator['countries']}" var="countryRep" >
											<c:if test="${countryRep.isEU eq 'true'}">
												<option value="${countryRep.code}"><spring:message code="${countryRep.value}"/></option>
											</c:if>
										</c:forEach>
									</select>
								</div>
								<div id="divUniqueCountry" style="display:none">
									
								</div>
								<div id="divManualCountries" style="display:none">

								</div>
								
								
								
								<span><strong class="count-countries"></strong></span>
								<div id="divButtonCountries" style="display:none">
									<button id="buttonCountries" class="add-countries-button" type="button"><i class="add-countries-icon"></i><spring:message code="relativeGrounds.details.field.reputationButton"/></button>
								</div>
							</div>
						</component:generic>
					</div>
				</div>
			</div>	
		</div>
	</div>
 
</section>