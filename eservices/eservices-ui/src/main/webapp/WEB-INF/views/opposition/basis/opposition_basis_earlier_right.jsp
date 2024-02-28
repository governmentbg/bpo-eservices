<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>

<section  class="trademark">
	<c:set var="sectionId" value="sec_${earlierEntitleMent}" scope="request"/>
	<c:set var="myNestedPath" value="oppositionBasisForm"/>
	<c:if test="${not empty formErrors}">
		<c:set var="myNestedPath" value=""/>
	</c:if>
	<spring:nestedPath path="${myNestedPath}">

		<div class="row" id="relativeGroundsDivTable">

			<c:if test="${flowModeId != 'ds-invalidity' }">
				<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="hideEarlierTM" checkRender="true" var="hideEarlierTM" />
				<c:if test="${hideEarlierTM == false }">
					<div>
						<jsp:include page="/WEB-INF/views/opposition/basis/earlier_entitlement_right_details.jsp">
							<jsp:param value="${earlierEntitleMent}" name="earlierEntitleMent"/>
						</jsp:include>
					</div>
				</c:if>


				<c:set var="sectionId" value="sec_${earlierEntitleMent}" scope="request"/>
				<c:if test="${hideEarlierTM == true }">
					<br/>
				</c:if>
			</c:if>
			<c:if test="${flowModeId == 'ds-invalidity' }">
				<br/>
			</c:if>

			<div>
				<table>
					<tr>
						<th style="font-size:14px; width:55%"> <spring:message code="relativeGrounds.table.header.ground.${flowModeId }"/><em class="requiredTag">(*)</em> </th>
						<th id="expandRelativeGrounds"  style="cursor:pointer;font-weight: normal; "><div class="add-icon" ></div> <spring:message code="earlierRight.header.expand"/> </th>
						<th id="collapseRelativeGrounds" style="display:none; cursor:pointer;font-weight: normal; "><div class="collapse-icon" ></div>  <spring:message code="earlierRight.header.collapse"/> </th>
					</tr>
				</table>

			</div>

			<div id="divRelativeGroundsRows" style="display:none">
				<jsp:include page="/WEB-INF/views/opposition/basis/grounds_relative_subsection.jsp">
					<jsp:param value="${legalActVersionParam}" name="legalActVersionParam"/>
					<jsp:param value="${earlierEntitleMent}" name="earlierEntitleMent"/>
				</jsp:include>
			</div>
			<br>

			<c:if test="${flowModeId == 'ds-invalidity'}">
				<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="hideEarlierTM" checkRender="true" var="hideEarlierTM" />
				<c:if test="${hideEarlierTM == false }">
					<div id="tmEarlierSubsection" style="display:none">
						<jsp:include page="/WEB-INF/views/opposition/basis/earlier_entitlement_right_details.jsp">
							<jsp:param value="${earlierEntitleMent}" name="earlierEntitleMent"/>
						</jsp:include>
					</div>
				</c:if>

				<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="hideEarlierDS" checkRender="true" var="hideEarlierDS" />
				<c:if test="${hideEarlierDS == false }">
					<div id="dsEarlierSubsection" style="display:none">
						<jsp:include page="/WEB-INF/views/opposition/basis/ds_earlier_entitlement_right_details.jsp">
							<jsp:param value="${earlierEntitleMent}" name="earlierEntitleMent"/>
						</jsp:include>
					</div>
				</c:if>



			</c:if>


		</div>

	</spring:nestedPath>


</section>