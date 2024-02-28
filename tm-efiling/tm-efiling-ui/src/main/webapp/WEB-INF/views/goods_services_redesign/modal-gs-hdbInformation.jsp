<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

    <div class="modal big hide fade" id="gs-hdbInformation" data-backdrop="static">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">x</button>
            <h3><spring:message code="terms.modal.classheading.hdbequivalents.title"/></h3>
        </div>
        <div class="modal-body">
            <div class="row-fluid">
                <div class="span11">
                    <div class="alert alert-ohim alert-info">
                        <spring:message code="terms.modal.classheading.hdbequivalents.information"/>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span6 initialTerms">
                    <h5><spring:message code="terms.modal.classheading.hdbequivalents.initial.terms"/></h5>
                    <div class="termlist">
                        <h6><spring:message code="gs.class" /> <span></span></h6><span><i class="term-modifiable-icon"></i></span>
                        <div class="terms"></div>
                    </div>
                </div>
                <div class="span6 hdbTerms">
                    <h5><spring:message code="terms.modal.classheading.hdbequivalents.hdb.terms"/></h5>
                    <div class="termlist">
                        <h6 class="checkbox"><input class="replaceTermAll" type="checkbox" name="replaceTermAll"/><span><spring:message code="gs.class" /> <span></span></span></h6><span><i class="term-validharm-icon"></i></span>
                        <div class="terms"></div>
                    </div>
                </div>
            </div>
        </div><!--modal-body-->
        <div class="modal-footer">
            <nav class="page-options">
                <ul class="unstyled pull-right">
                     <li class="cart-btn cancel-button"><a href="#" class="cancel" data-dismiss="modal"><spring:message code="layout.button.cancel" /></a></li>
                     <li class="cart-btn"><a id="btn-done" class="btn btn-big btn-primary "><spring:message code="terms.modal.termAction.change"/></a></li>
                </ul>
            </nav>
        </div>
        <div class="term-templates hide" >
        </div>
    </div>
