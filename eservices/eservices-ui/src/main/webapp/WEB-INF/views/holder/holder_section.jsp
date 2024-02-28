<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="holder" id="holder">
	<c:set var="sectionId" value="holder" scope="request"/>
    <header>
        <a id="holder" class="anchorLink">
        </a>

        <c:choose>
            <c:when test="${flowModeId eq 'um-change'  || flowModeId eq 'ep-change'}">
                <h2><spring:message code="holder.section.title.um_ep_change"></spring:message></h2>
            </c:when>
            <c:otherwise>
                <h2><spring:message code="holder.section.title"></spring:message></h2>
            </c:otherwise>
        </c:choose>

    </header>

    <c:if test="${flowModeId eq 'tm-change'  || flowModeId eq 'ds-change'  || flowModeId eq 'pt-change'  || flowModeId eq 'um-change'  || flowModeId eq 'ep-change' || flowModeId eq 'sv-change' || flowModeId eq 'spc-change'}">
        <div class="alert alert-info">
            <spring:message code="holder.canbe.${flowModeId }"/>
        </div>
    </c:if>

    <div id="holderCardList">
        <jsp:include page="/WEB-INF/views/holder/holder_card_list.jsp"/>
    </div>
    <jsp:include page="/WEB-INF/views/holder_is_inventor.jsp"/>
    <sec:authorize access="hasRole('Holder_Add')" var="security_holder_add" />
    
    <c:if test="${security_holder_add || !configurationServiceDelegator.securityEnabled}">
        <c:if test="${ configurationServiceDelegator.isServiceEnabled('Holder_Manual') }">
            <button id="holderTrigger" class="add-button" data-toggle="button" type="button">
                <i class="add-icon"></i>
                <spring:message code="person.button.holder"/>
            </button>
        </c:if>


        <div id="tabHolder" class="addbox" style="display: none">
            <jsp:include page="/WEB-INF/views/holder/holder_import.jsp"/>
            <div id="holderSection">
            
	            <ul class="controls">
				    <li>
				        <a class="cancelButton holder"><spring:message
				                code="holder.form.action.cancelAdd.top"/></a>
				    </li>
				    
				    <li>
				        <button id="addHolderTopButton" type="button" disabled="disabled">
				          	<span class="add-icon"></span>
				            <spring:message code="holder.form.action.add.top"/>
				        </button>
				    </li>
				     
				</ul>
				
             	<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="reuseApplicantData" 
 						checkRender="true" var="hasReuseApplicantData" ></tags:render> 


				<%-- <div id="selectHolderType" style="${hasReuseApplicantData && not empty flowBean.addedApplicants ? 'display:none':'display:block'}"> --%>
				
				<div id="selectHolderType" >
				</div>
				<div id="errorMessageHolder">
				</div>
            </div>
        </div>

        <div id="holderMatches">
            <%--<jsp:include page="/WEB-INF/views/holder/holderMatches.jsp"/>--%>
        </div>
    </c:if>
    <div class="hidden" id="saveHolderMsg"><spring:message code="holder.form.action.save"/></div>
    
      <c:set var="maxHold" value="holder.add.maxNumber"/>
    
    <input type="hidden" id="maxHolders"
       value="${configurationServiceDelegator.getValue(maxHold, 'eu.ohim.sp.core.person')}">    
        <script type="text/javascript">
        checkMaxHolders();
        </script>
    
    
</section>
