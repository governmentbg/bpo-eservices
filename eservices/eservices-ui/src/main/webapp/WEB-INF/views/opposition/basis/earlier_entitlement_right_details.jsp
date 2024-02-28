<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>


<section>
	    <br>
	    <div>
	    <table>
	    	<tr id="info">
	    		<th style="font-size:14px ; width:55%"> <spring:message code="earlierRight.header.text"/> </th>
	    		<th id="expandEarlierRight"  style="cursor:pointer;font-weight: normal; "><div class="add-icon" ></div> <spring:message code="earlierRight.header.expand"/> </th>
	    		<th id="collapseEarlierRight" style="display:none; cursor:pointer;font-weight: normal; "><div class="collapse-icon" ></div> <spring:message code="earlierRight.header.collapse"/> </th>
	    	</tr>
	    </table>
	    
	    </div>
	    <br>
		<div id="divEarlierRightInfoRow" style="display:none">
		<div style="display:none">
		<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.typeRight" checkRender="true" labelTextCode="earlierRight.details.field.typeRight"/>
		</div>

		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierRightDescription" checkRender="true">
			<component:textarea path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierRightDescription" checkRender="true"
								labelTextCode="earlierRight.details.field.description.${sectionId}"
								formTagClass="textareaBig"
				tipCode="earlierRight.details.field.description.tip.${sectionId}"
			/>

		</component:generic>

		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.tradeMarkStatus" checkRender="true">
			<ul class="reuse-applicant-data">
							<li><label><input type="radio" name="applicationRegistration" value="Application" id="applicationRegistrationAP"><spring:message code="earlierRight.details.field.earlierTradeMarkState.Application"/></label></li>
							<li><label><input type="radio" name="applicationRegistration" value="Registration" checked id="applicationRegistrationRE"><spring:message code="earlierRight.details.field.earlierTradeMarkState.Registration"/></label></li>
			</ul>
		</component:generic>	
         
          
	         <div id="divCategoryInsert"> 
	         
	         	<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.categoryTradeMark" checkRender="true">
	         		<c:set var="errors"><form:errors path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.categoryTradeMark"></form:errors></c:set>
	         	<label for="categoryTradeMark-select"><spring:message code="earlierRight.details.field.categoryTradeMark"></spring:message>
		         	<c:set var="checkRequired">
						<tags:required flowModeId="${flowModeId}" sectionId="sec_${earlierEntitleMent}" field="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.categoryTradeMark" />
					</c:set>
					<c:if test="${checkRequired}">
	         			<span class="requiredTag">*</span>
	         		</c:if>
	         	</label>
	         	
	         	<select id="categoryTradeMark-select">  
	         	<option value=""><spring:message code="opposition_basis.details.field.groundCategory.select"/></option>
	         	<c:forEach items="${configurationServiceDelegator['categoriesTradeMark']}" var="categoryTM">
					<c:if test="${(not empty categoryTM.code) && (categoryTM.applicationType eq flowModeId)}">
						<option value="${categoryTM.code}"><spring:message code="${categoryTM.value}"/> </option>
					</c:if>
				</c:forEach>
				</select>
				<c:if test="${!empty errors}">
			        <c:forEach items="${errors}" var="message">
			            <p class="flMessageError" id="categoryTradeMark-selectErrorMessageServer">${message}</p>
			        </c:forEach>
			    </c:if>  
				</component:generic>
				      
	         </div> 
        
         <div id="divCategoryHidden" style="display:none">
         	<component:input id = "categoryTradeMark-input" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.categoryTradeMark" checkRender="true" labelTextCode="earlierRight.details.field.categoryTradeMark"/>
         	<div style="display:none">
         		<component:input id = "categoryInputHidden" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.categoryTradeMark" checkRender="true" labelTextCode="earlierRight.details.field.categoryTradeMark"/>
         	</div>
         </div>
         		
         		
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.filterCountriesForeignTMView" checkRender="true">
		    <div id="divCountryForeign" style="display:block">
		    	<label for="countryForeign"><spring:message code="earlierRight.details.field.country"></spring:message>
        		</label>
        	
			    <a class="sorting-select">
	       			<select id="countryForeign">
	       				<c:forEach items="${configurationServiceDelegator['countries']}" var="countryForeignTM" >
	       					<c:if test="${countryForeignTM.isForeignTMView eq 'true'}">
	       						<c:set var="itemSelected" value=""/>
	       						<c:if test="${countryForeignTM.code eq ''}">
	                				<c:set var="itemSelected" value="selected"/>
	                			</c:if>
								<option value="${countryForeignTM.code}" selected="${itemSelected}"><spring:message code="${countryForeignTM.value}"/> </option>
							</c:if>	
	       				</c:forEach>
	               	</select>	
	             </a>
             </div>
		</component:generic>
         		 



		<div style="display:none">
			<select id="categoryTradeMark-aux">
				<c:forEach items="${configurationServiceDelegator['categoriesTradeMark']}" var="categoryTM">
					
					<c:if test="${(not empty categoryTM.code) && (categoryTM.applicationType eq flowModeId)}">
						<c:set var="divShow" value=""/>
						<c:set var="countriesList" value=""/>
						<c:choose>
							<c:when test="${categoryTM.filterCountries eq 'true'}">
								<c:choose>
									<c:when test="${not empty categoryTM.countries && (categoryTM.countries.size() > 1)}">
										<c:set var="divShow" value="divManualCountries"/>
										<c:set var="countriesList" value="${categoryTM.countries}"/>
									</c:when>
									<c:when test="${not empty categoryTM.countries && (categoryTM.countries.size() eq 1)}">
										<c:set var="divShow" value="divUniqueCountry"/>
										<c:set var="countriesList" value="${categoryTM.countries}"/>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${categoryTM.isEU eq 'true'}">
												<c:set var="divShow" value="divEUCountries"/>
											</c:when>
											<c:otherwise>
												<c:set var="divShow" value="divAllCountries"/>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${categoryTM.isEU eq 'true'}">
										<c:set var="divShow" value="divEUCountries"/>
									</c:when>
									<c:otherwise>
										<c:set var="divShow" value="divAllCountries"/>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<option label="${divShow}" value="${categoryTM.code}" title="${countriesList}"/>
					</c:if>
				</c:forEach>
			</select>
		</div>                     
		
		<c:if test="${flowModeId == 'ds-invalidity'}">
			<div class="alert alert-info">
				<spring:message code="ds-invalidity.earlierMark.warning"/>
			</div>
		</c:if>
		
		<br>
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.importHelper" checkRender="true">
		
		<div id="importTMEarlier" class="addbox" style="display: none">
			<jsp:include page="/WEB-INF/views/opposition/basis/tm_earlier_import.jsp"/>
		</div>
		<c:set var="importErrors" value="true"></c:set>
		</component:generic>

	 	<component:generic path="relativeGrounds.earlierEntitlementRightInf.entitlementOpponent.entitlement" checkRender="true">
	 	
	 	<div id="divEntitlementRows">
	 		<label><spring:message code="entitlement.table.header.entitlement.${flowModeId}"/></label>
	 		<component:filterTextSelect id="entitlementOpponet-select" 
	 				 items="${configurationServiceDelegator['entitlementsOpponent']}" 
	 				 path="relativeGrounds.earlierEntitlementRightInf.entitlementOpponent.entitlement"
                     labelTextCode="entitlementOpponent.details.field.entitlement.${flowModeId }"
                     itemLabel="value" 
                     itemValue="code" 
                     checkRender="true" 
                     fieldFilter="earlierEntitlementRightType"
                     fieldFilterText="${earlierEntitleMent}"
                     anotherFieldRender="showDetails"
                     anotherFieldComponent="input"
                     anotherFieldFilter="true"
                     anotherFieldId="inputHiddenEntitlement"
                     importInAnotherField="true"
                     formTagClass="long-fields"
                     /> 
		

		<div id="divEntitlementDetails" style="display:none"> 
			<component:textarea id="inputHiddenEntitlement" path="relativeGrounds.earlierEntitlementRightInf.entitlementOpponent.details" checkRender="true" labelTextCode="entitlementOpponent.details.field.details"/>
			<div class="tip"><spring:message code="entitlementOpponent.details.field.details.footer"/></div>
			
		</div>
	 	</div>
	 	<br>
	 	</component:generic>
	
		
		<div id="tabTMEarlier" class="addbox" style="display: none">
		
			<spring:eval var="gs_Languages" expression="@propertyConfigurer.getProperty('gs.languages')"/>
			<input type="hidden" id="gs_Languages" value="${gs_Languages}"/>
			 
			<c:set var="notImportEarlier" value="true"/>
			<component:generic path="relativeGrounds.earlierEntitlementRightInf.importHelperManual" checkRender="true">
				<c:set var="notImportEarlier" value="false"/>
			</component:generic>
			<input id="importable" value="${notImportEarlier}" type="hidden"/>

			<div id="divTradeMarkInfo">
				<c:if test="${(not empty formErrors && notImportEarlier eq 'true') && not empty importErrors && importErrors eq 'true'}">
			 			<jsp:include page="/WEB-INF/views/opposition/basis/earlier_trademark_details.jsp">
			 				<jsp:param value="${param.earlierEntitleMent}" name="earlierEntitleMent"/>
			 				<jsp:param value="false" name="notImportEarlier"/>
			 			</jsp:include> 	
		 		</c:if>
			</div>
			<div style="display:none" id="fame">
				<component:input formTagClass="field-date" path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.dateOfFame" checkRender="true" labelTextCode="earlierRight.details.field.dateOfFame.${earlierEntitleMent}"/>
			</div>
			
			<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.originalGS" checkRender="true">
	       		<br> 
	       		<c:if test="${(not empty formErrors && notImportEarlier eq 'true')|| not empty formEdit || notImportEarlier eq 'true' }">
				 	<div id="divEarlierGSRows" style="display: none">
				 		
				    	<div id="gsSection">
				    		<jsp:include page="/WEB-INF/views/opposition/basis/opposition_goodsservices.jsp"/>
						</div>
						
				 	</div>
				 	<c:set var="sectionId" value="sec_${earlierEntitleMent}" scope="request"/>
					<c:if test="${!empty param.earlierEntitleMent}">
						<c:set var="sectionId" value="sec_${param.earlierEntitleMent}" scope="request"/>
			        </c:if>
		        </c:if>
		 	</component:generic>
		 	
		 	<div style="display:none" id="relatedApplications">
				<component:input path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.relatedApplicationsNumbers" checkRender="true" labelTextCode="earlierRight.details.field.relatedApplicationsNumbers"/>
			</div>
		 	
		</div>
		<c:set var="notImportEarlier" value="true"/>
		<component:generic path="relativeGrounds.earlierEntitlementRightInf.importHelperManual" checkRender="true">
				<c:if test="${empty formEdit}">
		 			<jsp:include page="/WEB-INF/views/opposition/basis/earlier_trademark_details.jsp">
		 				<jsp:param value="${param.earlierEntitleMent}" name="earlierEntitleMent"/>
		 				<jsp:param value="${notImportEarlier}" name="notImportEarlier"/>
		 			</jsp:include> 	
	 			
	 			<component:generic path="relativeGrounds.earlierEntitlementRightInf.earlierEntitlementRightDetails.earlierTradeMarkDetails.originalGS" checkRender="true">
	       			<br> 
			 		<div id="divEarlierGSRows">
			 			
			    		<div id="gsSection">
			    			<jsp:include page="/WEB-INF/views/opposition/basis/opposition_goodsservices.jsp"/>
						</div>
						 
			 		</div>
		 		</component:generic>
		 		</c:if>
	 			<br>
	 			
		</component:generic>
		
		</div>
		
</section>