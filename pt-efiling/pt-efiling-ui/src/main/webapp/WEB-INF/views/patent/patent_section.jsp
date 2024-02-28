<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib prefix="comment" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<section id="patent" class="patent">
    <header>
        <a id="patent_section" class="anchorLink">
        </a>

        <h2><spring:message code="patent.details.title.${flowModeId}"></spring:message></h2>
    </header>

    <form:form id="patentForm" class="mainForm formEF addbox" modelAttribute="flowBean" method="POST">
        <c:set var="sectionId" value="patent" scope="request"/>

        <component:generic path="patent.imported" checkRender="true">
            <form:hidden path="patent.imported"/>
            <form:hidden path="patent.id"/>
            <form:hidden path="patent.patentValidated"/>
            <form:hidden path="patent.externalReferenceNumber"/>

            <c:set var="imported" value="${flowBean.patent.imported}" scope="request"/>

            <component:generic path="formWarnings" checkRender="true">
                <c:set var="errors">
                    <form:errors path="formWarnings"></form:errors>
                </c:set>
                <c:if test="${!empty errors}">
                    <p class="flMessageError permanentMessage" id="patentErrorMessageServer">${errors}</p>
                </c:if>
                <c:if test="${!empty flowBean.formWarnings}">
                    <c:forEach var="err" items="${flowBean.formWarnings}">
                        <p class="flMessageError permanentMessage" id="patentErrorMessageServer"><spring:message code="${err}"/></p>
                    </c:forEach>
                </c:if>
            </component:generic>

            <div style="display: block">
                <div class="inline-fields half_pct">
                    <component:input path="patent.applicationNumber" labelTextCode="patent.form.field.applicationNumber" formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
                </div>
                <div class="right-fields">
                    <component:input path="patent.applicationDate" labelTextCode="patent.form.field.applicationDate" formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
                </div>
            </div>
            <div style="display: block">
                <div class="inline-fields half_pct">
                    <component:input path="patent.registrationNumber" labelTextCode="patent.form.field.registrationNumber" formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
                </div>
                <div class="right-fields">
                    <component:input path="patent.registrationDate" labelTextCode="patent.form.field.registrationDate" formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
                </div>
            </div>
            <div style="display: block">
                <div class="inline-fields half_pct">
                    <component:input path="patent.registrationPublicationDate" labelTextCode="patent.form.field.registrationPublicationDate" formTagClass="onerow-fields" readOnly="true" checkRender="true"/>
                </div>
            </div>
        </component:generic>

        <c:if test="${flowModeId == 'sv-efiling'}">
            <div style="margin-bottom: 20px">
                <component:label path="patent.svKind.label" labelTextCode="svKind.option" />
                <c:forEach var="kind" items="${configurationServiceDelegator.getSVKinds()}">
                    <component:radiobutton checkRender="false" path="patent.svKind" value="${kind}"
                                           labelTextCode="${kind.code}" displayInline="true"/>
                </c:forEach>
            </div>
        </c:if>

        <component:input path="patent.patentTitle" labelTextCode="patent.form.field.patentTitle.${flowModeId}" formTagClass="title-input" maxlength="850" checkRender="true"></component:input>
        <component:generic path="patent.patentTitle" checkRender="true">
            <div class="tip"><spring:message code="patent.form.field.patentTitle.hint.${flowModeId}"></spring:message></div>
        </component:generic>

        <component:input path="patent.titleTransliteration" labelTextCode="patent.form.field.titleTransliteration.${flowModeId}" formTagClass="title-input" maxlength="850" checkRender="true"></component:input>

        <c:if test="${flowModeId == 'sv-efiling'}">
            <component:input id="taxonCode" path="patent.taxon" formTagClass="hidden" checkRender="true"></component:input>
            <sec:authorize access="hasRole('Latin_Search')" var="security_latin_search"/>
            <component:autocomplete
                    id="latinImportTextfield"
                    checkRender="true"
                    labelTextCode="patent.form.field.latinClassification.${flowModeId}"
                    component="eu.ohim.sp.core.classification"
                    autocompleteUrl="autocompleteLatin.htm"
                    textCodeWhenEmpty="latin.importBox.whenEmpty"
                    width="80"
                    path="patent.latinClassification"/>
        </c:if>

        <component:input path="patent.patentTitleSecondLang" labelTextCode="patent.form.field.patentTitleSecondLang" formTagClass="title-input" maxlength="850" checkRender="true"
            readOnly="${flowModeId == 'ep-efiling'}"></component:input>

        <c:if test="${flowModeId == 'spc-efiling'}">
            <div style="margin-bottom: 20px; margin-top: 20px">
                <component:label path="patent.regKind.label" labelTextCode="regKind.option" />
                <c:forEach var="kind" items="${configurationServiceDelegator.getRegKinds()}">
                    <div>
                        <component:radiobutton checkRender="false" path="patent.regKind" value="${kind}"
                                               labelTextCode="${kind.code}" displayInline="true"/>
                    </div>
                </c:forEach>
            </div>

            <div style="display: block">
                <label><spring:message code="patent.firstPermissionBGLabel"/></label>
                <div>
                    <div class="inline-fields half_pct">
                        <component:input path="patent.firstPermissionBGNumber" labelTextCode="patent.firstPermissionBGNumber" formTagClass="onerow-fields" checkRender="true"/>
                    </div>
                    <div class="right-fields">
                        <component:input path="patent.firstPermissionBGDate" checkRender="true" id="firstPermissionBGDate"
                                         labelTextCode="patent.firstPermissionBGDate"
                                         formTagClass="filing-date-input onerow-fields"/>
                    </div>
                </div>
                <div>
                    <div class="inline-fields half_pct">
                        <component:input path="patent.firstPermissionBGNotificationDate" checkRender="true" id="firstPermissionBGNotificationDate"
                                         labelTextCode="patent.firstPermissionBGNotificationDate"
                                         formTagClass="filing-date-input onerow-fields"/>
                    </div>
                </div>
                <div id="first-permission-bg-info" class="alert alert-info" style="margin-top: 10px">
                    <spring:message code="firstPermission.bg.info.spc-efiling"/>
                </div>
            </div>

            <div style="display: block; margin-top: 20px">
                <label><spring:message code="patent.firstPermissionEULabel"/></label>
                <div class="inline-fields half_pct">
                    <component:input path="patent.firstPermissionEUNumber" labelTextCode="patent.firstPermissionEUNumber" formTagClass="onerow-fields" checkRender="true"/>
                </div>
                <div class="right-fields">
                    <component:input path="patent.firstPermissionEUDate" checkRender="true" id="firstPermissionEUDate"
                                     labelTextCode="patent.firstPermissionEUDate"
                                     formTagClass="filing-date-input onerow-fields"/>
                        <%--                <component:input path="patent.firstPermissionEUDate" labelTextCode="patent.firstPermissionEUDate" formTagClass="onerow-fields" checkRender="true"/>--%>
                </div>
                <div id="first-permission-eu-info" class="alert alert-info" style="margin-top: 10px">
                    <spring:message code="firstPermission.eu.info.spc-efiling"/>
                </div>
            </div>
        </c:if>

        <component:textarea path="patent.patentAbstract" labelTextCode="patent.form.field.patentAbstract" formTagClass="description-textarea" checkRender="true"></component:textarea>
        <component:generic path="patent.patentAbstract" checkRender="true">
            <div class="tip"><spring:message code="patent.form.field.patentAbstract.hint.${flowModeId}"></spring:message></div>
        </component:generic>

        <component:textarea path="patent.patentAbstractSecondLang" labelTextCode="patent.form.field.patentAbstractSecondLang" formTagClass="description-textarea" checkRender="true"></component:textarea>

        <component:textarea path="patent.patentPublicationsInfo" labelTextCode="patent.form.field.patentPublicationsInfo" formTagClass="description-textarea" checkRender="true"
            readOnly="true"></component:textarea>

        <c:set var="errors">
            <form:errors path="patent.patentViews"></form:errors>
        </c:set>
        <c:if test="${!empty errors}">
            <p class="flMessageError" id="patent.patentViewsErrorMessageServer">${errors}</p>
        </c:if>

        <component:label path="patent.patentViews" labelTextCode="patent.form.field.patentViews" />

        <component:generic path="patent.patentViews" checkRender="true">
            <div class="alert alert-info">
                <spring:message code="patent.form.message.patentViews"/>
            </div>

            <jsp:include page="view_list.jsp"></jsp:include>
        </component:generic>

        <c:set var="sectionId" value="patent" scope="request"/>

        <c:if test="${flowModeId != 'ep-efiling' || (not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind != 'EP_TEMPORARY_PROTECTION')}">
            <div class="fileuploadInfo">
                <component:file fileWrapper="${flowBean.patent.patentDescriptions}" fileWrapperPath="patent.patentDescriptions" pathFilename="patentDescriptions"
                                labelCode="patent.form.field.patentDescriptions.${flowModeId}" showPreLabelInputFileButton="false" helpMessageKey="patent.form.field.patentDescriptions.fileUpload.info"/>
            </div>
            <component:generic path="patent.patentDescriptions" checkRender="true">
                <div class="alert alert-info">
                    <spring:message code="patent.form.message.patentDescriptions.${flowModeId}"/>
                </div>
            </component:generic>
        </c:if>



        <div class="fileuploadInfo">
            <component:file fileWrapper="${flowBean.patent.patentClaims}" fileWrapperPath="patent.patentClaims" pathFilename="patentClaims"
                            labelCode="patent.form.field.patentClaims.${flowModeId}" showPreLabelInputFileButton="false" helpMessageKey="patent.form.field.patentClaims.fileUpload.info"/>
        </div>
        <component:generic path="patent.patentClaims" checkRender="true">
            <div class="alert alert-info">
                <spring:message code="patent.form.message.patentClaims.${flowModeId}"/>
            </div>
        </component:generic>

        <c:if test="${flowModeId != 'ep-efiling' || (not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind != 'EP_TEMPORARY_PROTECTION')}">
            <div class="fileuploadInfo">
                <component:file fileWrapper="${flowBean.patent.patentDrawings}" fileWrapperPath="patent.patentDrawings" pathFilename="patentDrawings"
                                labelCode="patent.form.field.patentDrawings.${flowModeId}" showPreLabelInputFileButton="false" helpMessageKey="patent.form.field.patentDrawings.fileUpload.info"/>
            </div>
            <component:generic path="patent.patentDrawings" checkRender="true">
                <div class="alert alert-info">
                    <spring:message code="patent.form.message.patentDrawings.${flowModeId}"/>
                </div>
            </component:generic>
        </c:if>

        <div class="fileuploadInfo">
            <component:file fileWrapper="${flowBean.patent.sequencesListing}" fileWrapperPath="patent.sequencesListing" pathFilename="sequencesListing"
                            labelCode="patent.form.field.sequencesListing.${flowModeId}" showPreLabelInputFileButton="false" helpMessageKey="patent.form.field.sequencesListing.fileUpload.info"/>
        </div>
        <component:generic path="patent.sequencesListing" checkRender="true">
            <div class="alert alert-info">
                <spring:message code="patent.form.message.sequencesListing.${flowModeId}" htmlEscape="false"/>
            </div>
        </component:generic>


        <c:if test="${flowModeId != 'ep-efiling' || (not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind != 'EP_TEMPORARY_PROTECTION')}">
            <component:input path="patent.pagesCount" id="patentPagesCount" labelTextCode="patent.form.field.pagesCount.${flowModeId}" formTagClass="count-input ajaxSubmitPatentField" checkRender="true"></component:input>
        </c:if>
        <c:if test="${flowModeId != 'ep-efiling' || (not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind != 'EP_TEMPORARY_PROTECTION')}">
            <component:input path="patent.claimsCount" id="patentClaimsCount" labelTextCode="patent.form.field.claimsCount.${flowModeId}" formTagClass="count-input ajaxSubmitPatentField" checkRender="true"></component:input>
            <component:generic path="patent.claimsCount" checkRender="true">
                <c:if test="${flowModeId != 'um-efiling' && flowModeId != 'ep-efiling'}">
                    <div class="tip"><spring:message code="patent.form.field.0.hint"></spring:message></div>
                </c:if>
            </component:generic>
        </c:if>
        <component:input path="patent.independentClaimsCount" id="patentIndependentClaimsCount" labelTextCode="patent.form.field.independentClaimsCount" formTagClass="count-input ajaxSubmitPatentField" checkRender="true"></component:input>
        <c:if test="${flowModeId != 'ep-efiling' || (not empty flowBean.mainForm.applicationKind && flowBean.mainForm.applicationKind != 'EP_TEMPORARY_PROTECTION')}">
            <component:input path="patent.drawingsCount" labelTextCode="patent.form.field.drawingsCount" formTagClass="count-input" checkRender="true"></component:input>
            <component:generic path="patent.drawingsCount" checkRender="true">
                <c:if test="${flowModeId != 'ep-efiling'}">
                    <div class="tip"><spring:message code="patent.form.field.0.hint"></spring:message></div>
                </c:if>
            </component:generic>
        </c:if>
        <component:input path="patent.drawingNumber" labelTextCode="patent.form.field.drawingNumber" formTagClass="count-input" checkRender="true"></component:input>
        <component:generic path="patent.drawingNumber" checkRender="true">
            <c:if test="${flowModeId != 'ep-efiling'}">
                <div class="tip"><spring:message code="patent.form.field.0.hint"></spring:message></div>
            </c:if>
        </component:generic>

    </form:form>
</section>

<jsp:include page="view_section.jsp" />

<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /patent/register-patent/patent
</div>