<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="addbox claimFields">
    <form:form modelAttribute="exhPriorityForm" id="exhibitionForm" cssClass="fileUploadForm formEF">
        <c:set var="sectionId" value="exhibition" scope="request"/>

        <header>
        	<span id="exhibitionCurrentNumber" class="number"></span>
            <h3><spring:message code="claim.title.exhibition"/></h3>
            <ul class="controls">
                <li>
                    <a class="cancelExhibitionButton">
                        <spring:message code="claim.action.cancel"/>
                    </a>
                </li>

                <li>
                    <button type="button" class="addExhibitionWizard">
                    	<spring:message code="claim.action.save"/>
                    	<%--
                        <c:choose>
                            <c:when test="${empty exhPriorityForm.id}">
                                <spring:message code="claim.action.add"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="claim.action.save"/>
                            </c:otherwise>
                        </c:choose>
                        --%>
                    </button>
                </li>
            </ul>
        </header>


        <form:hidden path="id"/>

        <component:select items="${configurationServiceDelegator.getExhibitionsByModule('dsefiling')}" labelTextCode="claim.exhibition.field.name"
                         path="exhibitionName"
                         itemLabel="value"
                         itemValue="code" checkRender="true"/>

        <component:input path="exhibitionDescription" checkRender="true" 
                        labelTextCode="claim.exhibition.field.description" formTagClass=""/>

        <component:input path="firstDate" checkRender="true" id="fDisc"
                        labelTextCode="claim.exhibition.field.firstDisclsure" formTagClass="filing-date-input" showDateHelp="true" />


        <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
            <component:file labelCode="claim.exhibition.document" pathFilename="fileDocumentAttachment" fileWrapperPath="fileWrapper"
                           fileWrapper="${exhPriorityForm.fileWrapper}"/>
        </div>

        <tiles:insertDefinition name="designs_associate_to">
			<tiles:putAttribute name="containsDesignsLinkForm" value="${exhPriorityForm}"/>
			<tiles:putAttribute name="partialId" value="ExhPriority" />		
		</tiles:insertDefinition>
        
        <br />
        
        <ul class="controls">
            <li>
                <a class="cancelExhibitionButton">
                    <spring:message code="claim.action.cancel"/></a>
            </li>
            <li>
                <button type="button" class="addExhibitionWizard">
                	<spring:message code="claim.action.save"/>
                	<%--
                    <c:choose>
                        <c:when test="${empty exhPriorityForm.id}">
                            <spring:message code="claim.action.add"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="claim.action.save"/>
                        </c:otherwise>
                    </c:choose>
                    --%> 
                </button>
            </li>
        </ul>
        
        <br />
        
    </form:form>
    <%--<div class="row">--%>
        <%--<p class="flBox data-url" data-rel="tab1_exhibition">--%>
            <%--<a  class="addCtn addAnotherExhibitionWizard"><span>--%>
	            <%--<spring:message code="claim.exhibition.action.addAnother"/>--%>
	        <%--</span></a>--%>
        <%--</p>--%>
    <%--</div>--%>
</div>