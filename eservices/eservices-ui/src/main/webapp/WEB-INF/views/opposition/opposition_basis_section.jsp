<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<section class="trademark">

 	<header>
        <a id="applicant" class="anchorLink">
        </a>

        <h2><spring:message code="${flowModeId}.details.title"></spring:message></h2>
    </header>  
    
     <jsp:include page="/WEB-INF/views/opposition/opposition_basis_card_list.jsp"/>
    
    <button id="oppositionBasisTrigger" class="add-button" data-toggle="button" type="button">
       <i class="add-icon"></i>
       <spring:message code="${flowModeId}.section.button.basis"/>
	</button>

    <div id="tabOppositionBasis" class="addbox" style="display: none">
    	<div id="oppositionBasisSection"></div>
    	
	</div>

    <c:choose>
        <c:when test="${flowModeId eq 'tm-objection'}">
            <input type="hidden" id="maxOBs"
                    value="${configurationServiceDelegator.getValue('objectionBasics.maxNumber', 'general')}">
        </c:when>
        <c:when test="${flowModeId eq 'tm-revocation'}">
            <input type="hidden" id="maxOBs"
                   value="${configurationServiceDelegator.getValue('revocationBasics.maxNumber', 'general')}">
        </c:when>
        <c:otherwise>
            <input type="hidden" id="maxOBs"
                    value="${configurationServiceDelegator.getValue('oppositionBasics.maxNumber', 'general')}">
        </c:otherwise>
    </c:choose>


        <script type="text/javascript">
        	checkMaxOBs();
        </script>

    <jsp:include page="/WEB-INF/views/goods_services/terms-modal.jsp"/>
        
</section>