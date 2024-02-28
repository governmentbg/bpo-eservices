<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="termsAndConditionsModal" class="modal modal-standard fade hide">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="termsAndConditions.modal.title"/>
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
    <div class="modal-body">
        <span id="confirmPlaceholder">
			<spring:message code="termsAndConditions.modal.message"/>        
        </span>
    </div>
    <footer>
        <ul>
            <li>
                <button type="button" id="confirmModalOk" data-dismiss="modal">
                    <spring:message code="termsAndConditions.modal.footer.buttonClose"/>
                </button>
            </li>
        </ul>
    </footer>
</div>
</div>
</div>