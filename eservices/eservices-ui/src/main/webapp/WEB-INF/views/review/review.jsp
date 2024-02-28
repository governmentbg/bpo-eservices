<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="review" id="review">
	<header>
        <h2><spring:message code="review.title"/></h2>
    </header>
	<p>
		<c:choose>
			<c:when test="${flowModeId eq 'tm-opposition' || flowModeId eq 'tm-objection'}">
				<spring:message code="review.text.initial.${flowModeId}"/>
			</c:when>
			<c:otherwise>
				<spring:message code="review.text.initial"/>
			</c:otherwise>
		</c:choose>
	</p>

    <jsp:include page="/WEB-INF/views/review/review_reference.jsp" />

	<c:if test="${formUtil.getMainType() == 'PT' || formUtil.getMainType() == 'UM' || formUtil.getMainType() == 'EP' || formUtil.getMainType() == 'SV' || formUtil.getMainType() == 'SPC'}">
		<jsp:include page="/WEB-INF/views/review/review_patent.jsp" />
	</c:if>

    <c:if test="${formUtil.getMainType() == 'TM'}"> 
   		<jsp:include page="/WEB-INF/views/review/review_mark.jsp" />
   	</c:if>
   	
   	<c:if test="${formUtil.getMainType() == 'DS'}"> 
   		<jsp:include page="/WEB-INF/views/review/review_design.jsp" />
   	</c:if>

	<jsp:include page="/WEB-INF/views/review/review_requesttype.jsp"/>

	<c:if test="${flowModeId eq 'tm-surrender' || flowModeId eq 'tm-withdrawal' || flowModeId eq 'tm-transfer' || flowModeId == 'tm-renewal'
	|| flowModeId eq 'tm-opposition' || flowModeId eq 'tm-objection' || flowModeId eq 'tm-revocation' || flowModeId eq 'tm-invalidity'}">
		<jsp:include page="/WEB-INF/views/review/review_gshelper.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-appeal')}">
		<jsp:include page="/WEB-INF/views/review/review_appeal.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-security')}">
		<jsp:include page="/WEB-INF/views/review/review_securitymeasure.jsp" />
	</c:if>

<%--	<c:if test="${fn:endsWith(flowModeId, '-extendterm')}">--%>
<%--		<jsp:include page="/WEB-INF/views/review/review_marketpermission.jsp" />--%>
<%--	</c:if>--%>

	<c:if test="${flowModeId eq 'tm-opposition' || flowModeId eq 'tm-revocation' || fn:endsWith(flowModeId, '-invalidity') || flowModeId eq 'tm-objection'}">
		<jsp:include page="/WEB-INF/views/review/review_oppositionBasis.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-licence') || fn:endsWith(flowModeId, '-compulsorylicence')}">
		<jsp:include page="/WEB-INF/views/review/review_licence.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-transfer') || fn:endsWith(flowModeId, '-licence') || fn:endsWith(flowModeId, '-compulsorylicence') || fn:endsWith(flowModeId, '-rem') || fn:endsWith(flowModeId, '-security') || fn:endsWith(flowModeId, '-invdenial')}">
		<jsp:include page="/WEB-INF/views/review/review_assignees.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-change')}">
		<jsp:include page="/WEB-INF/views/review/review_holders.jsp" />
	</c:if>

	<c:if test="${flowModeId eq 'ds-change' || flowModeId eq 'pt-change' || flowModeId eq 'um-change' || flowModeId eq 'ep-change' || flowModeId eq 'sv-change' || flowModeId eq 'spc-change'}">
		<jsp:include page="/WEB-INF/views/review/review_holderisinventor.jsp" />
	</c:if>

	<c:if test="${flowModeId eq 'pt-invalidity' || flowModeId eq 'spc-invalidity' || flowModeId eq 'um-invalidity'}">
		<jsp:include page="/WEB-INF/views/review/review_partialinvalidity.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-renewal') == false && fn:endsWith(flowModeId, '-surrender')  == false && fn:endsWith(flowModeId, '-bankruptcy')  == false
		&& fn:endsWith(flowModeId, '-changerep') == false && fn:endsWith(flowModeId, '-providepow') == false && fn:endsWith(flowModeId, '-paidtaxes') == false }">
		<jsp:include page="/WEB-INF/views/review/review_applicationCA.jsp" />
	</c:if>

	<jsp:include page="/WEB-INF/views/review/review_applicants.jsp" />

	<c:if test="${flowModeId eq 'ds-authorreg'}">
		<jsp:include page="/WEB-INF/views/review/review_designers.jsp" />
	</c:if>

	<c:if test="${fn:endsWith(flowModeId, '-changeca') == false && fn:endsWith(flowModeId, '-providepow') == false && fn:endsWith(flowModeId, '-paidtaxes') == false}">
		<jsp:include page="/WEB-INF/views/review/review_representatives.jsp" />
	</c:if>

	<c:if test="${flowModeId eq 'pt-appeal'}">
		<jsp:include page="/WEB-INF/views/review/review_declaration.jsp" />
	</c:if>

   	<jsp:include page="/WEB-INF/views/review/review_changerequest.jsp"/>

	<c:if test="${!(flowModeId eq 'spc-invalidity') && !(flowModeId eq 'um-invalidity')}">
		<jsp:include page="/WEB-INF/views/review/review_attachments.jsp" />
	</c:if>

	<c:if test="${!flowModeId eq 'spc-invalidity' && !flowModeId eq 'um-invalidity'}">
		<jsp:include page="/WEB-INF/views/review/review_signature.jsp" />
	</c:if>
   	
</section>