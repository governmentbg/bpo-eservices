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
	    		<th style="font-size:14px ; width:55%"> <spring:message code="earlierDSRight.header.text"/> </th>
	    		<th id="expandEarlierDSRight"  style="cursor:pointer;font-weight: normal; "><div class="add-icon" ></div> <spring:message code="earlierDSRight.header.expand"/> </th>
	    		<th id="collapseEarlierDSRight" style="display:none; cursor:pointer;font-weight: normal; "><div class="collapse-icon" ></div> <spring:message code="earlierDSRight.header.collapse"/> </th>
	    	</tr>
	    </table>
	    
	    </div>
	    <br>
		<div id="divEarlierDSRightInfoRow" style="display:none">
		
		<div class="alert alert-info" >
			<spring:message code="ds.invalidity.earlier.design.save.warning"/>
		</div>
		
		<div id="importDSEarlier" class="addbox" style="display: none">
			<jsp:include page="/WEB-INF/views/opposition/basis/ds_earlier_import.jsp"/>
		</div>
		<c:set var="importErrors" value="true"></c:set>
			 	
	 	
	 	<component:generic path="relativeGrounds.earlierEntitlementRightInf.entitlementOpponent.entitlement" checkRender="true">
	 	
	 	<div id="divEntitlementRows" style="display:${entitlementHidden ? 'none':'block' }">
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
	 	
	 	
	 	<div id="tabDSImported">
		 	<jsp:include page="/WEB-INF/views/opposition/basis/ds_earlier_card_list.jsp"/>
	 	</div>
	 	
	 	<div id="tabDSEarlierDetails" class="addbox" style="display: none">
		 	
	 	</div>
	 	
	 	</div>
	 	
</section>	 	