<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="designers" id="designers">

    <header>
        <a id="_designers" class="anchorLink">
        </a>

        <h2><spring:message code="${flowModeId}.designer.section.title"></spring:message></h2>
    </header>

    <div id="designerCardList">
        <jsp:include page="/WEB-INF/views/designer/designer_card_list.jsp"/>
    </div>

    <sec:authorize access="hasRole('Designer_Add')" var="security_designer_add" />
    
    <c:if test="${security_designer_add || !configurationServiceDelegator.securityEnabled}">

        <button id="designerTrigger" class="add-button" data-toggle="button" type="button">
            <i class="add-icon"></i>
            <spring:message code="${flowModeId}.person.button.designer"/>
        </button>


        <div id="tabDesigner" class="addbox" style="display: none">
            <jsp:include page="/WEB-INF/views/designer/designer_import.jsp"/>
            <div id="designerSection">
            </div>
        </div>

        <div id="designerMatches">
            <jsp:include page="/WEB-INF/views/designer/designerMatches.jsp"/>
        </div>
    </c:if>
    <div class="hidden" id="saveDesignerMsg"><spring:message code="designer.form.action.save"/></div>
    
    <c:set var="maxApp" value="designer.add.maxNumber"/>
    
	<input type="hidden" id="maxDesigners"
	value="${configurationServiceDelegator.getValue(maxApp, 'eu.ohim.sp.core.person')}">    
        <script type="text/javascript">
        	checkMaxDesigners();
        </script>
   
</section>
