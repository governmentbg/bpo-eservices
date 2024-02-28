<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<script id="editable-termPopover" type="text/template">
    <div class="template">
        <section class=" popover-section class-description">
            <spring:message code="terms.popover.editable.term.description" />
        </section>
        <section class="popover-section class-browse row-space-s">
            <p>
                <span class="terms-suggestion"><spring:message code="terms.popover.terms.suggestions" /></span>
                <span class="terms-wrongClass hide"><spring:message code="terms.popover.editable.term.wrong.class.description" /></span>
            </p>
            <div id="termsEditing">
                <div class="otherTerms">
                    <div class="loading"></div>
                </div>
            </div>
            <div class="page-options">
                <ul class="modalTermOptions unstyled pull-right">
                    <li>
                        <a class='aside-link mini' action="closePopOver"  href="" >
                            <spring:message code="Cancel" />
                        </a>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-mini-xs disabled  " id="modalChangeTerm">
                            <spring:message code="terms.modal.termAction.change" />
                        </a>
                    </li>
                </ul>
            </div>

        </section>
        <section class="popover-section">
            <spring:message code="terms.popover.search.similar.terms" />
            <div class="searchLinks">
            </div>
        </section>
        <section class="popover-section">
            <spring:message code="terms.popover.common.issue" />
        </section>
    </div>
</script>
<script id="invalid-termPopover" type="text/template">
    <div class="template">
        <section class=" popover-section class-description">
            <spring:message code="terms.popover.invalid.term.description" />
        </section>
        <section class="popover-section class-browse row-space-s">
            <p>
                <span class="terms-suggestion"><spring:message code="terms.modal.term.search" /></span>
            </p>
            <div id="termsEditing">
                <div class="otherTerms">
                </div>
                <div class="suggestedSearches folder-list ">
                    <div class="loading"></div>
                </div>
            </div>
            <div class="page-options">
                <ul class="modalTermOptions unstyled pull-right">
                    <li>
                        <a class='aside-link mini' action="closePopOver" href="" >
                            <spring:message code="Cancel" />
                        </a>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-mini-xs disabled hide " id="modalChangeTerm">
                            <spring:message code="terms.modal.termAction.change" />
                        </a>
                    </li>
                </ul>
            </div>
        </section>
        <section class="popover-section">
            <spring:message code="terms.popover.search.similar.terms" />
            <div class="searchLinks">
            </div>
        </section>
        <section class="popover-section">
            <spring:message code="terms.popover.full.nonaccepted.list" />
        </section>
    </div>
</script>
<script id="notfound-termPopover" type="text/template">
    <div class="template">
        <section class=" popover-section class-description">
            <spring:message code="terms.popover.notfoundterm.description" />
        </section>
        <section class="popover-section class-browse row-space-s">
            <p>
                <span class="terms-suggestion"><spring:message code="terms.modal.term.search" /></span>
            </p>
            <div id="termsEditing">
                <div class="otherTerms">
                </div>
                <div class="suggestedSearches folder-list ">
                </div>
            </div>
            <div class="page-options">
                <ul class="modalTermOptions unstyled pull-right">
                    <li>
                        <a class='aside-link mini' action="closePopOver" href="" >
                            <spring:message code="Cancel" />
                        </a>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-mini-xs disabled hide" id="modalChangeTerm">
                            <spring:message code="terms.modal.termAction.change" />
                        </a>
                    </li>
                </ul>
            </div>
        </section>
        <section class="popover-section">
            <spring:message code="terms.popover.search.similar.terms" />
            <div class="searchLinks">
            </div>
        </section>
        <section class="popover-section">
            <spring:message code="terms.popover.common.issue" />
        </section>
    </div>
</script>

<script id="HDBEquivalentTerm-alert" type="text/template">
        <tr class="hdbAlert-column" >
            <td class="" colspan="2">
                <div class="alert-arrow"></div>
                <div class="hdbEquivalenteAlert" >
                    <div class="alert-description">
                        <spring:message code="gs.alert.hdbequivalenttermfound" />
                    </div>
                    <div class="actions">
                        <a class="btn btn-mini-xs " id="hdbConfirm" rel="hdb-confirm">
                            <spring:message code="gs.button.label.consult" />
                        </a>
                        <a rel="hdb-tooltip" class="hdb-information-close icon-remove pull-right" data-original-title="Remove alert"></a>
                    </div>
                </div>
            </td>
        </tr>
</script>

<script id="literalsContainer" type="text/template">
    <div class="template">
        <span class="literal-selectAll"><spring:message code="selectAll" /></span>
        <span class="literal-gs_class"><spring:message code="gs.class" /></span>
        <span class="literal-terms.popover.class.contains.goods"><spring:message code="terms.popover.class.contains.goods" /></span>
        <span class="literal-terms.edit.popover.class.notfound"><spring:message code="terms.edit.popover.class.notfound" /></span>
    </div>
</script>
