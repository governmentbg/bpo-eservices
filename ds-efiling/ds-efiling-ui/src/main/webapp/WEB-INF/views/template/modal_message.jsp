<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<span id="messages" class="hidden">
    <span id="openFormsMessage"></span>
    <div id="openApplicantFormMessage">
        <spring:message code="general.messages.openForms.applicant"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll="#tabApplicant">
            <spring:message code="general.messages.openForms.applicant.scroll"/>
        </a>
        <br>
    </div>
    <div id="openRepresentativeFormMessage">
        <spring:message code="general.messages.openForms.representative"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll="#tabRepresentative">
            <spring:message code="general.messages.openForms.representative.scroll"/>
        </a>
        <br>
    </div>
    <div id="openClaimsFormMessage">
        <spring:message code="general.messages.openForms.claims"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll=".claimSections">
            <spring:message code="general.messages.openForms.claims.scroll"/>
        </a>
        <br>
    </div>
    <div id="openApplicationCAFormMessage">
        <spring:message code="general.messages.openForms.applicationCA"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll="#tabApplicationCA">
            <spring:message code="general.messages.openForms.applicationCA.scroll"/>
        </a>
        <br>
    </div>
    <div id="openDesignerFormMessage">
        <spring:message code="general.messages.openForms.designer"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll="#tabDesigner">
            <spring:message code="general.messages.openForms.designer.scroll"/>
        </a>
        <br>
    </div>    
	<div id="openDesignsFormMessage">
        <spring:message code="general.messages.openForms.designs"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll="#addDesignForm">
            <spring:message code="general.messages.openForms.designs.scroll"/>
        </a>
        <br>
    </div>  
    
    <div id="openDivisionalApplicationFormMessage">
    	<spring:message code="general.messages.openForms.divisionalApplication"/>
        <a class="messageScroll" data-dismiss="modal" data-scroll="#divisionalApplicationTab">
            <spring:message code="general.messages.openForms.divisionalApplication.scroll"/>
        </a>
        <br />
    </div>
          
    <div id="applicantCreationInfo">
        <spring:message code="general.messages.applicant.create"/>
    </div>
    <div id="representativeCreationInfo">
        <spring:message code="general.messages.representative.create"/>
    </div>
    
    <div id="selectLanguageFirst">
        <spring:message code="general.messages.selectLanguageFirst"/>
    </div>
    <div id="locarno_searchCriteriaEmpty">
        <spring:message code="general.messages.search.criteria.empty"/>
    </div>

    <%--
    <div id="similarMarksNotSeen">
        <spring:message code="general.messages.similarMarks.notSeen"/>
    </div>
    <div id="goodsServices_selectLanguageFirst">
        <spring:message code="general.messages.goodsServices.selectLanguageFirst"/>
    </div>
    <div id="goodsServices_writeTerm">
        <spring:message code="general.messages.goodsServices.writeTerm"/>
    </div>
    <div id="goodsServices_provideTermChooseClass">
        <spring:message code="general.messages.goodsServices.provideTermChooseClass"/>
    </div>
    <div id="goodsServices_selectClass">
        <spring:message code="general.messages.goodsServices.selectClass"/>
    </div>
     --%>
    <div id="languages_prevent_same">
        <spring:message code="design.language.preventSame"/>
    </div>
    <%--
	<div id="seniority_remove">
        <spring:message code="general.messages.claim.seniority.confirmRemoveSeniority"/>
    </div>
    --%>
    <div id="exhibition_remove">
    	<spring:message code="general.messages.claim.exhibition.confirmRemoveExhibition"/>
    </div>
    <%--
    <div id="transformation_remove">
    	<spring:message code="general.messages.claim.transformation.confirmRemoveTransformation"/>
    </div>
    --%> 
	<div id="priority_remove">
        <spring:message code="general.messages.claim.priority.confirmRemovePriority"/>
    </div>

    <div id="delete_confirmation_signature">
        <spring:message code="general.messages.signature.confirmRemoveSignature"/>
    </div>
    <%--
    <div id="openSeniority_yesMessage">
        <spring:message code="claim.seniority.wizard.yes"/>
    </div>
    <div id="openSeniority_addAnotherMessage">
        <spring:message code="claim.seniority.wizard.addAnother"/>
    </div>
    --%>
    <div id="openPriority_yesMessage">
        <spring:message code="claim.priority.wizard.yes"/>
    </div>
	<div id="openPriority_addAnotherMessage">
        <spring:message code="claim.priority.wizard.addAnother"/>
    </div>
    <div id="openExhibition_yesMessage">
        <spring:message code="claim.exhibition.wizard.yes"/>
    </div>
    <div id="openExhibition_addAnotherMessage">
        <spring:message code="claim.exhibition.wizard.addAnother"/>
    </div>
    <%--
    <div id="openTransformation_yesMessage">
        <spring:message code="claim.transformation.wizard.yes"/>
    </div>
    <div id="openTransformation_addAnotherMessage">
        <spring:message code="claim.transformation.wizard.addAnother"/>
    </div>
    <div id="partialPriority_removeGoodsServices">
        <spring:message code="general.messages.claim.priority.confirmRemoveGoodsServices"/>
    </div>
    <div id="partialPriority_removeClass">
        <spring:message code="general.messages.claim.priority.confirmRemoveClass"/>
    </div>
    <div id="partialPriority_addClassWithSameName">
        <spring:message code="general.messages.claim.priority.confirmReplaceTerms"/>
    </div>
    <div id="partialPriority_missingInputText">
        <spring:message code="general.messages.claim.priority.missingInputText"/>
    </div>
    <div id="partialPriority_emptyTable">
        <spring:message code="general.messages.claim.priority.emptyTable"/>
    </div>
    <div id="marks_changeType">
        <spring:message code="general.messages.marks.changeType"/>
    </div>
    --%>
    <div id="showDesignWarning">
        <spring:message code="NonBlockingMessage.Split.Application.message"/>
    </div>
    <div id="sessionExpired">
        <spring:message code="general.messages.session.expired"/>
    </div>
    <div id="languageMessage_changeClearsData">
        <spring:message code="general.messages.language.changeClearsData"/>
    </div>
    <div id="fileMessage_removeFile">
        <spring:message code="general.messages.files.remove"/>
    </div>
    <div id="timeout_message">
        <spring:message code="general.messages.error.timeout"/>
    </div>
    <%--
    <div id="similarMarks_noWordRepresentation">
        <spring:message code="general.messages.similarMarks.noWordRepresentation"/>
    </div>
    <div id="similarMarks_noResultsFound">
        <spring:message code="general.messages.similarMarks.noResultsFound"/>
    </div>
    --%>
    <div id="representative_OHIM_reviewInfo">
        <spring:message code="general.messages.representative.ohimProfessionalPractitioner.underReview"/>
    </div>
    <div id="payment_closePopup">
        <spring:message code="general.messages.payment.confirmClosePopup"/>
    </div>
    <%--
    <div id="previousCtm_missingInputText">
        <spring:message code="general.messages.previousCtm.emptyInput"/>
    </div>
     --%>
    <div id="previousRegisteredDesign_missingInputText">
        <spring:message code="general.messages.previousRegisteredDesign.emptyInput"/>
    </div>
    <div id="applicationca_deleteConfirmation">
        <spring:message code="general.messages.applicationca.deleteConfirmation"/>
    </div>
    <div id="representative_deleteConfirmation">
        <spring:message code="general.messages.representative.deleteConfirmation"/>
    </div>
    <div id="applicant_deleteConfirmation">
        <spring:message code="general.messages.applicant.deleteConfirmation"/>
    </div>
	<div id="applicant_missingInputText">
        <spring:message code="general.messages.applicant.emptyInput"/>
    </div>
	<div id="representative_missingInputText">
        <spring:message code="general.messages.representative.emptyInput"/>
    </div>
    <div id="designer_missingInputText">
        <spring:message code="general.messages.designer.emptyInput"/>
    </div>
    <div id="designer_select_first">
        <spring:message code="general.messages.designer.cannotImport"/>
    </div>
    <div id="designer_deleteConfirmation">
        <spring:message code="general.messages.designer.deleteConfirmation"/>
    </div>    
    <div id="applicant_addDesigner">
        <spring:message code="general.messages.applicant.addDesigner"/>
    </div>
    <div id="applicant_cannotImport">
        <spring:message code="general.messages.applicant.cannotImport"/>
    </div>
    <div id="representative_cannotImport">
        <spring:message code="general.messages.representative.cannotImport"/>
    </div>
    <div id="designer_cannotImport">
        <spring:message code="general.messages.designer.cannotImport"/>
    </div>
    <div id="design_cannotImport">
        <spring:message code="general.messages.design.cannotImport"/>
    </div>
	<div id="session_time_out">
        <spring:message code="general.messages.session.timeout"/>
    </div>
	<div id="error_common_restCall_204">
        <spring:message code="error.common.restCall.204"/>
    </div>
    <%--
	<div id="gs_classHeading_coverage_unselected">
        <spring:message code="gs.classHeading.coverage.unselected"/>
    </div>
	<div id="gs_classHeading_coverage_review_unselected">
        <spring:message code="gs.classHeading.coverage.review.unselected"/>
    </div>
	<div id="error_tmview_search_results_multiple">
        <spring:message code="error.tmview.search.results.multiple"/>
    </div>
    --%>
    <p id="error_jquery_validation_required">
    	<spring:message code="error.jquery.validation.required" />
    </p>
    <p id="error_jquery_validation_remote">
    	<spring:message code="error.jquery.validation.remote" />
    </p>
    <p id="error_jquery_validation_email">
    	<spring:message code="error.jquery.validation.email" />
    </p>
    <p id="error_jquery_validation_url">
    	<spring:message code="error.jquery.validation.url" />
    </p>
    <p id="error_jquery_validation_date">
    	<spring:message code="error.jquery.validation.date" />
    </p>
    <p id="error_jquery_validation_dateISO">
    	<spring:message code="error.jquery.validation.dateISO" />
    </p>
    <p id="error_jquery_validation_number">
    	<spring:message code="error.jquery.validation.number" />
    </p>
    <p id="error_jquery_validation_digits">
    	<spring:message code="error.jquery.validation.digits" />
    </p>
    <p id="error_jquery_validation_creditcard">
    	<spring:message code="error.jquery.validation.creditcard" />
    </p>
    <p id="error_jquery_validation_equalTo">
    	<spring:message code="error.jquery.validation.equalTo" />
    </p>
    <p id="error_jquery_validation_accept">
    	<spring:message code="error.jquery.validation.accept" />
    </p>
    <p id="error_jquery_validation_matchPattern">
    	<spring:message code="error.jquery.validation.matchPattern" />
    </p>
    <%--
	<div id="error_tmview_search_results_zero">
        <spring:message code="error.tmview.search.results.zero"/>
    </div>
    
	<div id="error_tmview_search_results_invalid_uri">
        <spring:message code="error.tmview.search.results.invalid.uri"/>
    </div>
    --%>
    <div id="error_illegalArguments">
        <spring:message code="error.illegalArguments"/>
    </div>
	<div id="error_common_restCall_404">
        <spring:message code="error.common.restCall.404"/>
    </div>
	<div id="generic_errors_service_fail">
        <spring:message code="generic.errors.service.fail"/>
    </div>
    <div id="resetApplicationConfirmation">
        <spring:message code="general.messages.application.reset"/>
    </div>
    <div id="logoutApplicationConfirmation">
        <spring:message code="general.messages.application.logout"/>
    </div>
    <div id="representative_newAssociation">
        <spring:message code="general.messages.representative.newAssociation"/>
    </div>
	<div id="errors_generic_load_unknown">
        <spring:message code="errors.generic.load.unknown"/>
    </div>
	<div id="errors_generic_load_parsing">
        <spring:message code="errors.generic.load.parsing"/>
    </div>
	<div id="errors_generic_load_not_authorized">
        <spring:message code="errors.generic.load.not.authorized"/>
    </div>
	<div id="errors_language_ajax_update">
        <spring:message code="errors.language.ajax.update"/>
    </div>
	<div id="importPriority_extraDetails">
        <spring:message code="general.messages.priority.importExtraDetails"/>
    </div>
    <%--
    <div id="importSeniority_extraDetails">
        <spring:message code="general.messages.seniority.importExtraDetails"/>
    </div>
    <div id="colour_missingDescription">
        <spring:message code="mark.color.missingDescription"/>
    </div>
    <div id="colour_removeAllColours">
        <spring:message code="mark.color.removeAllColours"/>
    </div>
    <div id="importTransformation_extraDetails">
        <spring:message code="general.messages.transformation.importExtraDetails"/>
    </div>
    --%>
    <%--
	<div id="previousCTM_importConfirmation">
        <spring:message code="general.messages.previousCTM.importConfirmation"/>
    </div>
    
    
     --%>
    <div id="previousRegisteredDesign_importConfirmation">
        <spring:message code="general.messages.previousRegisteredDesign.importConfirmation"/>
    </div>
    
    <div id="personEditText"><spring:message code="person.form.action.save"/></div>

    <div id="design_deleteConfirmation">
        <spring:message code="design.table.deleteConfirmation"/>
    </div>
    
    <%--
    <div id="design_duplicateConfirmation">
        <spring:message code="design.table.duplicateConfirmation"/>
    </div>
     --%>
     
    <div id="design_view_deleteConfirmation">
    	<spring:message code="design.view.grid.deleteConfirmation"/>
    </div>
	<div id="locarno_deleteConfirmation">
        <spring:message code="design.form.classes.table.deleteConfirmation"/>
    </div>
    <!-- DS Class Integration Changes Start -->

    <div id="locarno_selectClass">
        <spring:message code="general.messages.locarno.selectClass"/>
    </div>

    <!-- DS Class Integration Changes End -->
    <div id="divisionalApplication_deleteConfirmation">
    	<spring:message code="general.messages.divisionalApplication.confirmRemove"/>
    </div>
    
    
    <div id="design_cannotDelete">
		<spring:message code="general.messages.oneform.designs.cannotDelete" />    	
    </div>
    
    <div id="design_youNeedAtLeastOneDesign">
    	<spring:message code="general.messages.oneform.designs.youNeedAtLeastOneDesign" />
    </div>
 
    <div id="design_pubDistinctFeatFee">
    	<spring:message code="general.messages.oneform.designs.pubDistinctFeatWarning" />
    </div>    
    
    <div id="importApplicationXml_contentNotValid">
    	<spring:message code="import.application.xml.contentNotValid" />
    </div>
    <div id="importApplicationXml_willRedirectToHome">
        <spring:message code="import.application.xml.willRedirectToHome" />
    </div>
    
    <div id="autocomplete_noResultsFound">
    	<spring:message code="general.messages.autocomplete.noResultsFound" />
    </div>

     <div id="applicantCreateNewOnImportedChange"><spring:message code="applicant.create.new.on.imported.change"/></div>

    <div id="designerCreateNewOnImportedChange"><spring:message code="designer.create.new.on.imported.change"/></div>

    <div id="applicationSubmitConfirmation"><spring:message code="general.messages.application.submit.confirmation" htmlEscape="false"/></div>
</span>

<div id="messageModal" class="modal fade messagePopup modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="general.messageModal.header.title"/>
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
    <div class="modal-body">
        <span id="messagePlaceholder"></span>
    </div>

    <footer>
        <ul>
            <li>
                <button type="button" id="messageModalOk" data-dismiss="modal">
                    <spring:message code="general.messageModal.footer.buttonOk"/>
                </button>
            </li>
        </ul>
    </footer>
</div>
</div>
</div>

<div id="warningModal" class="modal fade messagePopup modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="general.warningModal.header.title"/>
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>

    <div class="modal-body">
        <span id="warningPlaceholder"></span>
    </div>

    <footer>
        <ul>
            <li>
                <button type="button" id="warningModalOk" data-dismiss="modal">
                    <spring:message code="general.warningModal.footer.buttonOk"/>
                </button>
            </li>
        </ul>
    </footer>
</div>
</div>
</div>

<div id="confirmModal" class="modal fade messagePopup modal-standard">
<div class="modal-dialog">
<div class="modal-content">

    <header>
        <h1>
            <spring:message code="general.confirmModal.header.title"/>
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
    <div class="modal-body">
        <span id="confirmPlaceholder"></span>
    </div>
    <footer>
        <ul>
            <li>
                <button type="button" id="confirmModalCancel" data-dismiss="modal">
                    <spring:message code="general.confirmModal.footer.buttonCancel"/>
                </button>
            </li>
            <li>
                <button type="button" id="confirmModalOk" data-dismiss="modal">
                    <spring:message code="general.confirmModal.footer.buttonOk"/>
                </button>
            </li>
        </ul>
    </footer>

</div>
</div>
</div>

<div id="confirmModalYesNo" class="modal fade messagePopup modal-standard" data-backdrop="static">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="general.confirmModal.header.title"/>
        </h1>

    </header>
    <div class="modal-body">
        <span id="confirmYesNoPlaceholder"></span>
    </div>
    <footer>
        <ul>
            <li>
                <button type="button" id="confirmModalNo" data-dismiss="modal">
                    <spring:message code="general.confirmModal.footer.buttonNo"/>
                </button>
            </li>
            <li>
                <button type="button" id="confirmModalYes" data-dismiss="modal">
                    <spring:message code="general.confirmModal.footer.buttonYes"/>
                </button>
            </li>
        </ul>
    </footer>
</div>
</div>
</div>