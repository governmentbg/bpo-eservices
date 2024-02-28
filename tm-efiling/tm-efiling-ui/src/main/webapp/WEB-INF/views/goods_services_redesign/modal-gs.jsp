<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="gs-redesign">
    <div class="modal big hide fade modal-content" id="modal-gs" data-backdrop="static">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">x</button>
            <h3><spring:message code="gs.newModal.title"/></h3>
            <div id="cart-info" class="disabled">
                <ul>
                    <li><strong id="terms-amount">0</strong> <spring:message code="gs.newModal.terms"/></li>
                    <li><strong id="classes-amount">0</strong> <spring:message code="gs.newModal.classes"/></li>
                    <li class="cart-btn"><a id="view-terms" class="btn "><strong><spring:message code="gs.newModal.viewSelection"/></strong></a></li>
                    <li class="cart-btn hide"><a id="hide-terms" class="btn btn-primary active"><spring:message code="gs.newModal.goBack"/></a></li>
                </ul>
            </div>
        </div>
		<img src="<%=request.getContextPath()%>/resources/img/preloader.gif" id="preloader"/>
        <div class="modal-body">
            <jsp:include page="/WEB-INF/views/goods_services_redesign/gs-search.jsp"/>
            <div id="breadcrumb-container"></div>
            <div id="gs-results-table">
                <p id="wait-overlay"></p>
                <div></div>
            </div>
            <div id="gs-selected"></div>
        </div><!--modal-body-->
        <div class="modal-footer">
            <div id="footer-options">
            </div>
<%--            <div class="pull-left span7 text-left"><i class="disclaimer-icon"></i><b><spring:message code="general.messages.disclaimer"/></b></div>--%>
<%--            <span class="pull-left span7 text-left"><spring:message code="gs.newModal.disclaimer"/></span>--%>
            <nav class="page-options">
                <ul class="unstyled pull-right">
                    <li class="cart-btn"><a id="btn-done"  class="btn btn-big btn-primary disabled" style="max-width:165px"><spring:message code="gs.newModal.viewSelection"/></a></li>
                    <li class="cart-btn hide"><a id="btn-end" class="btn btn-big btn-success" data-dismiss="modal">
                        <i class="icon-white icon-ok" style="margin-right:10px"></i><spring:message code="gs.newModal.done"/></a></li>
                </ul>
            </nav>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/goods_services_redesign/gs-templates.jsp"/>

    <div id="confirm-close-modal-gands" class="modal fade messagePopup modal-standard">
    <div class="modal-dialog">
    <div class="modal-content">
        <header>
            <h1>
                <spring:message code="general.confirmModal.header.title"/>
            </h1>
            <a class="close-icon" data-dismiss="modal"></a>
        </header>
        <div class="modal-body">
            <span>
                <spring:message code="gs.warning.closeSearch"/>
            </span>
        </div>
        <footer>
            <ul>
                <li>
                    <button type="button" data-dismiss="modal">
                        <spring:message code="general.confirmModal.footer.buttonCancel"/>
                    </button>
                </li>
                <li>
                    <button class="confirm-action" type="button" data-dismiss="modal">
                        <spring:message code="general.confirmModal.footer.closeAnyway"/>
                    </button>
                </li>
            </ul>
        </footer>
    </div>
    </div>
    </div>
</div>