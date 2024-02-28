<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="attachments" id="otherAttachments">
    <header>
        <a id="otherAttachments" class="anchorLink"></a>
        <c:choose>
            <c:when test="${fn:endsWith(flowModeId, '-generic')}">
                <h2><spring:message code="otherAttachments.title.generic"/></h2>
            </c:when>
            <c:otherwise>
                <h2><spring:message code="otherAttachments.title"/></h2>
            </c:otherwise>
        </c:choose>

    </header>
    <c:set var="sectionId" value="otherAttachments" scope="request"/>

    <form:form class="mainForm formEf" modelAttribute="flowBean">
        <component:generic path="processInitiatedBeforePublication" checkRender="true">
            <component:checkbox path="processInitiatedBeforePublication" labelTextCode="otherAttachments.processInitiatedBeforePublication" id="processInitiatedBeforePublication" />
            <div class="tip">
                <spring:message code="otherAttachments.processInitiatedBeforePublication.tip"/>
            </div>
        </component:generic>
    </form:form>
    
    <c:if test="${fn:endsWith(flowModeId, '-transfer') || fn:endsWith(flowModeId, '-licence') || fn:endsWith(flowModeId, '-security') || fn:endsWith(flowModeId, '-bankruptcy') || fn:endsWith(flowModeId, '-docchanges') || fn:endsWith(flowModeId, '-extendterm') || fn:endsWith(flowModeId, '-rem')
   		|| flowModeId == 'ds-renewal' || flowModeId == 'ds-change'}">
        <div class="alert alert-info">
            <spring:message code="otherAttachments.info.${ flowModeId}"/>
        </div>
    </c:if>

    <div id="otherAttachments" class="fileuploadInfo flFormRow w80 alignCenter">
        <component:file pathFilename="otherAttachments" fileWrapperPath="otherAttachments"
                        fileWrapper="${flowBean.otherAttachments}"/>
    </div>

    <br/>

    <c:if test="${fn:endsWith(flowModeId, '-generic')}">

        <div id="changeTypeDefaultAttachment" class="alert alert-info"
             style="${(
             flowBean.changeType eq 'translationCorrection' ||
             flowBean.changeType eq 'actionCorrection'
             ) ? 'display:none;' : ''}">
            <spring:message code="otherAttachments.info.${ flowModeId}"/>
        </div>

        <c:if test="${flowModeId eq 'ep-generic'}">
            <div id="changeTypeAdditionalAttachment-translationCorrection" class="alert alert-info changeTypeAdditional"
                 style="${flowBean.changeType eq 'translationCorrection' ? '' : 'display:none;'}">
                <spring:message code="otherAttachments.info.ep-generic.translationCorrection"/>
            </div>
            <div id="changeTypeAdditionalAttachment-actionCorrection" class="alert alert-info changeTypeAdditional"
                 style="${flowBean.changeType eq 'actionCorrection' ? '' : 'display:none;'}">
                <spring:message code="otherAttachments.info.ep-generic.actionCorrection"/>
            </div>
        </c:if>

    </c:if>
    
	<form:form class="mainForm formEf" modelAttribute="flowBean">
        <c:set var="commentKey"/>
        <c:choose>
            <c:when test="${fn:endsWith(flowModeId, '-generic')}">
                <c:set var="commentKey" value="otherAttachments.comment.generic"/>
            </c:when>
            <c:otherwise>
                <c:set var="commentKey" value="otherAttachments.comment"/>
            </c:otherwise>
        </c:choose>
		<component:textarea path="comment" checkRender="true" id="comment"
	                        labelTextCode="${commentKey}" formTagClass="comments" tipCode="general.comments.tip"/>

        <div id="changeTypeAdditional-translationCorrection" class="changeTypeAdditional" style="${flowBean.changeType eq 'translationCorrection' ? 'margin-top:20px;' : 'display:none; margin-top:20px;'}">
            <component:input path="pagesCountAttachment" id="pagesCountAttachment" labelTextCode="otherAttachments.pages.count" formTagClass="count-input" checkRender="true"></component:input>
        </div>
    </form:form>                

</section>
