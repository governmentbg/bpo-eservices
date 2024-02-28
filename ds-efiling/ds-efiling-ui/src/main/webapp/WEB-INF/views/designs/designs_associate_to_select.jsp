<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="functions" uri="/WEB-INF/tags/functions/functions.tld" %>

<tiles:importAttribute name="containsDesignsLinkForm" />
<tiles:importAttribute name="partialId" />
<c:set var="designsFromLabel">
	<c:choose>
		<c:when test="${partialId eq 'DivisionalApplication'}">
			designer.linkDesign.designsFrom.divisional
		</c:when>
		<c:when test="${partialId eq 'Priority'}">
			designer.linkDesign.designsFrom.priority
		</c:when>
		<c:when test="${partialId eq 'ExhPriority'}">
			designer.linkDesign.designsFrom.exhibition
		</c:when>
		<c:when test="${partialId eq 'Designer'}">
			designer.linkDesign.designsFrom.designer
		</c:when>
		<c:otherwise>
			designer.linkDesign.designsFrom
		</c:otherwise>
	</c:choose>
</c:set>

<c:choose>
	<c:when test="${not functions:isVisibleDesignSelects(flowBean, containsDesignsLinkForm)}">
		<input name="designsLinked" type="hidden" value="${containsDesignsLinkForm.designsLinked[0].id}" />
	</c:when>
	<c:otherwise>
		<div id="linkDesignsSection${partialId}" class="link-designs">
		
			<label>
				<spring:message code="designer.linkDesign.linkedToDesigns" />
			</label>
			
			<div id="linkDesignsFromSection${partialId}" class="link-designs-from">
				<component:generic path="designsNotLinked" checkRender="true">
					
					<c:set var="errorsDesignsNotLinked"><form:errors path="designsNotLinked"></form:errors></c:set>
					<%--<!-- Set the error class if it is necessary -->--%>
    				<c:if test="${!empty errorsDesignsNotLinked}">
        				<c:set var="formTagClass" value="error"/>
    				</c:if>
					
					<label for="linkDesignsFrom${partialId}"><spring:message code="${designsFromLabel}" /></label>
					<form:select path="designsNotLinked" id="linkDesignsFrom${partialId}" multiple="true" cssClass="${formTagClass}">
						<c:forEach items="${containsDesignsLinkForm.designsNotLinked}" var="design">
							<form:option value="${design.id}">
								<spring:message code="designer.linkDesign.designX" arguments="${design.number}" />
							</form:option>	
						</c:forEach>
		       		</form:select>
		       		
		       		<c:if test="${!empty errorsDesignsNotLinked}">
        				<c:forEach items="${errorsDesignsNotLinked}" var="message">
            				<p class="flMessageError" id="linkDesignsFrom${partialId}ErrorMessageServer">${message}</p>
        				</c:forEach>
    				</c:if>
    				
		       	</component:generic>
			</div>
			
			<div id="linkDesignsButtonsSection${partialId}" class="link-designs-buttons">
				<button id="linkAllDesigns${partialId}" type="button">
					<i class="addAllDesigns-icon">&gt;&gt;</i>
				</button>
				<br />
				<button id="linkDesign${partialId}" type="button">
					<i class="addDesign-icon">&nbsp;&gt;&nbsp;</i>
				</button>
				<br />
				<button id="unlinkDesign${partialId}" type="button">
					<i class="removeDesign-icon">&nbsp;&lt;&nbsp;</i>
				</button>
				<br />
				<button id="unlinkAllDesigns${partialId}" type="button">
					<i class="removeAllDesigns-icon">&lt;&lt;</i>
				</button>
			</div>
			
			<div id="linkDesignsToSection${partialId}" class="link-designs-to">
				<component:generic path="designsLinked" checkRender="true">
					
					<c:set var="errorsDesignsLinked"><form:errors path="designsLinked"></form:errors></c:set>
					<%--<!-- Set the error class if it is necessary -->--%>
    				<c:if test="${!empty errorsDesignsLinked}">
        				<c:set var="formTagClass" value="error"/>
    				</c:if>
					
					<label for="linkDesignsTo${partialId}">
						<spring:message code="designer.linkDesign.designsTo" />
						<span class="requiredTag">*</span>
					</label>
					<form:select path="designsLinked" id="linkDesignsTo${partialId}" multiple="true" cssClass="mandatory ${formTagClass}">
						<c:forEach items="${containsDesignsLinkForm.designsLinked}" var="design">
							<form:option value="${design.id}">
								<spring:message code="designer.linkDesign.designX" arguments="${design.number}" />
							</form:option>	
						</c:forEach>
		       		</form:select>
		       		
		       		<c:if test="${!empty errorsDesignsLinked}">
        				<c:forEach items="${errorsDesignsLinked}" var="message">
            				<p class="flMessageError" id="linkDesignsTo${partialId}ErrorMessageServer">${message}</p>
        				</c:forEach>
    				</c:if>
		       	</component:generic>
			</div>
			
			<div class="link-designs-clear"></div>
		</div>
	</c:otherwise>
</c:choose>
