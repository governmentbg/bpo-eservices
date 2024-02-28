<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<section class="attachments" id="otherAttachments">
    <header>
        <a href="#otherAttachments" class="anchorLink"></a>

        <h2><spring:message code="otherAttachments.title"/><component:fastTrackMark sectionId="${sectionId}" helpMessageKey="fasttrack.help.attachPaymentDocument.tip"/></h2>
    </header>
    <c:set var="sectionId" value="otherAttachments" scope="request"/>
    <c:set var="service_fasttrack"
           value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>
    
	<form:form class="mainForm formEf" modelAttribute="flowBean">	
		<component:textarea path="comment" checkRender="true" id="comment"
	                        labelTextCode="otherAttachments.comment" formTagClass="comments"/>

        <component:generic path="willPayOnline" checkRender="true">
            <component:checkbox path="willPayOnline" checkRender="true" id="willPayOnline"
                                labelTextCode="onlinePaymentDeclaration.field.willPayOnline"/>
            <div class="tip">
                <spring:message code="onlinePaymentDeclaration.field.willPayOnline.tip"/>
            </div>
        </component:generic>
    </form:form>                
    <div id="otherAttachments" class="fileuploadInfo flFormRow w80 alignCenter">        
        <component:file pathFilename="otherAttachments" fileWrapperPath="otherAttachments"
                        fileWrapper="${flowBean.otherAttachments}" labelClass="${service_fasttrack ? 'fasttrack-icon-after': ''}"/>
    </div>
</section>