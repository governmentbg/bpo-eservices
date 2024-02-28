<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="attachments" id="otherAttachments">
    <header>
        <a href="#otherAttachments" class="anchorLink"></a>

        <c:set var="headerLabel" value="otherAttachments.title"/>
        <c:if test="${flowModeId == 'ep-efiling'}">
            <c:set var="headerLabel" value="attachments.title"/>
        </c:if>
        <h2><spring:message code="${headerLabel}"/></h2>
    </header>
    <c:set var="sectionId" value="otherAttachments" scope="request"/>
    
	<form:form class="mainForm formEf" modelAttribute="flowBean">
        <component:checkbox path="mainForm.epoDecisionCopy" checkRender="true"
                            labelTextCode="application.declaration.field.epoDecisionCopy" id="declaration_epoDecisionCopy"
                            disabled="${not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind == 'EP_TEMPORARY_PROTECTION'}"/>
        <component:generic path="mainForm.epoDecisionCopy" checkRender="true">
            <div class="tip"><spring:message code="attachDocs.bellow.hint"></spring:message></div>
        </component:generic>

        <component:checkbox path="mainForm.epoTransferChangeForm" checkRender="true"
                            labelTextCode="application.declaration.field.epoTransferChangeForm" id="declaration_epoTransferChangeForm"
                            disabled="${not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind == 'EP_TEMPORARY_PROTECTION'}"/>
        <component:generic path="mainForm.epoTransferChangeForm" checkRender="true">
            <div class="tip"><spring:message code="application.declaration.epoTransferChangeForm.hint"></spring:message></div>
            <div class="tip"><spring:message code="attachDocs.bellow.hint"></spring:message></div>
        </component:generic>


		<component:textarea path="comment" checkRender="true" id="comment"
	                        labelTextCode="otherAttachments.comment" formTagClass="comments"/>
    </form:form>

    <c:if test="${flowModeId eq 'spc-efiling'}">
        <div id="otherAttachments-info" class="alert alert-info">
            <spring:message code="otherAttachments.info.spc-efiling"/>
        </div>
    </c:if>

    <div id="otherAttachments" class="fileuploadInfo flFormRow w80 alignCenter">        
        <component:file pathFilename="otherAttachments" fileWrapperPath="otherAttachments"
                        fileWrapper="${flowBean.otherAttachments}"/>
    </div>
</section>