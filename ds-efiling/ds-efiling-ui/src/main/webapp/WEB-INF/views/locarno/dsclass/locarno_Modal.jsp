<%@ page import="eu.ohim.sp.common.ui.form.design.LocarnoComplexKindForm" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="locarnoModal modal-responsive">
<div>
	<div class="term big hide fade" id="locarnoModalBrowser">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">x</button>
			<h3>
				<spring:message code="design.form.classes.locarno.modal.title" />
			</h3>
			<div id="cart-info" class="disabled">
				<ul>
					<li><strong id="terms-amount">0</strong> <spring:message
							code="design.form.classes.locarno.modal.terms" /></li>
					<li><strong id="classes-amount">0</strong> <spring:message
							code="design.form.classes.locarno.modal.classes" /></li>
					<li class="cart-btn"><a id="view-terms" class="btn"><strong><spring:message
									code="design.form.classes.locarno.modal.viewSelection" /></strong></a></li>
					<li class="cart-btn hide"><a id="hide-terms"
						class="btn active"><spring:message
								code="design.form.classes.locarno.modal.goBack" /></a></li>
				</ul>
			</div>
		</div>
		<img src="<%=request.getContextPath()%>/resources/img/preloader.gif" id="preloader" />
        <jsp:include page="/WEB-INF/views/locarno/dsclass/locarno_ModalSearch.jsp" />
		<div class="modal-body">
			<div id="breadcrumb-container"></div>
			<div id="locarnoResultsTable">
				<p id="wait-overlay"></p>
				<div></div>
			</div>
			<div id="locarnoSelected"></div>
		</div>
		<!--modal-body-->
		<div class="modal-footer">
			<div id="footer-options"></div>

			<nav class="page-options">
				<ul class="unstyled pull-right">
					<li class="cart-btn"><a id="btn-done"
						class="btn btn-big btn-primary disabled"><spring:message
								code="design.form.classes.locarno.modal.add" /></a></li>
					<li class="cart-btn hide"><a id="btn-end"
						class="btn btn-big btn-success"
						data-dismiss="modal"><i class="icon-white icon-ok" style="margin-right:10px"></i><spring:message
								code="design.form.classes.locarno.modal.done" /></a></li>
				</ul>
			</nav>
		</div>
	</div>
	<form id="formNewLocarno" cssClass="formEF" modelAttribute="locarnoForm">
	
		<div class="term hide fade" id="locarnoModalEnterMyTerms">
			<div class="term-header">
				<button type="button" class="close" data-dismiss="modal">x</button>
				<h3>
					<spring:message code="design.form.classes.locarno.myTerms.title" />
				</h3>
			</div>
			<div class="term-body">
				<div class="row-fluid">
					<div class="span6">
						<label for="locarnoClasses"><spring:message code="design.form.classes.locarno.form.action.locarnoClassesLabel" /></label>
						<select name="locarnoClassification.locarnoClass.clazz" id="locarnoClasses" class="span6">
							<option value="-1"><spring:message code="design.form.classes.locarno.modal.selectAllClasses" /></option>
						</select>
                        <select name="locarnoClassification.locarnoClass.subclass" id="locarnoSubclasses" class="span6">
							<option value="-1"><spring:message code="design.form.classes.locarno.modal.selectAllSubclasses" /></option>
						</select>
					</div>
					<div class="span6">
                        <label for="enterMyProductIndications"><spring:message code="design.form.classes.locarno.form.action.enterMyTermsLabel" />
                            <em class="requiredTag">*</em>
                            <a class="tooltip-icon" data-toggle="tooltip" title=""
                               data-original-title="<spring:message code="design.form.classes.locarno.form.action.enterMyTerms.toltip"/>" ></a>
                        </label>
						<textarea class="enterMyProductIndications" name="locarnoClassification.indication"
							id="enterMyProductIndications" maxlength="100"></textarea>
					</div>
				</div>
				<div class="row-fluid myTermsErrorTermsExceed"
					style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message
								code="design.form.classes.locarno.myTerms.termsExceed"
								arguments="${configurationServiceDelegator.getValue('locarno.add.maxNumber', 'design')}" />
						</div>
					</div>
				</div>
				<div class="row-fluid myTermsErrorNotAllowedClass"
					style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message code="design.form.classes.locarno.myTerms.classNotAllowed" />
						</div>
					</div>
				</div>
				<div class="row-fluid myTermsErrorClassesNotChoosen"
					style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message code="design.form.classes.locarno.myTerms.classesNotChoosen" />
						</div>
					</div>
				</div>
				<div class="row-fluid myTermsErrorTermsEmpty" style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message code="design.form.classes.locarno.myTerms.termsEmpty" />
						</div>
					</div>
				</div>
				<div class="genericErrorMyTerms"></div>
			</div>
			<div class="term-footer">
				<ul class="controls unstyled">
					<li><a class="cancelButton" data-dismiss="modal"><spring:message
								code="design.form.classes.locarno.myTerms.action.close" /></a></li>
					<li><a class="acceptMyOwnTerms modalbtn" type="button"><spring:message
								code="design.form.classes.locarno.myTerms.action.ok" /> </a></li>
				</ul>
			</div>

		</div>
	</form>
	<form id="formNewComplexLocarno" class="formEF locarno" modelAttribute="locarnoComplexForm">

		<div class="term big hide fade" id="locarnoModalEnterMyComplexTerms">
			<div class="term-header">
				<button type="button" class="close" data-dismiss="modal">x</button>
				<h3>
					<spring:message code="design.form.classes.create" />
				</h3>
			</div>
			<div class="term-body">
				<div class="alert alert-info locarno-warning-message">
					<spring:message code="design.form.classes.modal.warning" />
				</div>
				<div class="class-search">
					<label for="complexLocarnoClass"><spring:message code="design.form.classes.locarno.form.action.locarnoClassesLabel" /></label>
					<select id="complexLocarnoClass" class="span6">
						<option value="-1"><spring:message code="design.form.classes.locarno.modal.selectAllClasses" /></option>
					</select>
					<select id="complexLocarnoSubClass" class="span6">
						<option value="-1"><spring:message code="design.form.classes.locarno.modal.selectAllSubclasses" /></option>
					</select>
				</div>
				<div class="class-search-button">
					<button id="addComplexLocarnoClass" class="btn" type="button">
						<spring:message code="design.form.classes.model.button.add" />
					</button>

				</div>
				<div>
					<label>
						<spring:message code="design.form.classes.modal.label.product.myClasses" /><span class="requiredTag">*</span>
					</label>

					<div class="addedClasses">
						<ul id="listComplexLocarnoClasses">

						</ul>
					</div>

				</div>
				<div class="typeClasses">
					<div class="typeClasses">
						<c:set var="singleProductValue" value="<%= LocarnoComplexKindForm.SINGLE_PRODUCT.getValue() %>" />
						<c:set var="setCompositionValue" value="<%= LocarnoComplexKindForm.SET_COMPOSITION.getValue() %>" />
						<ul>
							<li>
								<label for="singleProduct">
									<input type="radio" name="type"  value="${singleProductValue}" id="singleProduct" />
									<spring:message code="design.form.classes.modal.label.product.type.product" />
								</label>
							</li>
							<li>
								<label for="setComposition">
									<input type="radio" name="type" value="${setCompositionValue}" id="setComposition" />
									<spring:message code="design.form.classes.modal.label.product.type.setComposition" />
								</label>
							</li>
						</ul>
					</div>

				</div>
				<div>
					<label for="enterMyComplexProductIndications"><spring:message code="design.form.classes.locarno.form.action.enterMyTermsLabel" />
						<em class="requiredTag">*</em>
					</label>
					<input name="indication"
						   id="enterMyComplexProductIndications" maxlength="200"></input>
					<br/>
				</div>
				<div class="row-fluid myTermsErrorTypeNotChosen"
					 style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message code="design.form.classes.locarno.myTerms.choseType" />
						</div>
					</div>
				</div>
				<div class="row-fluid myTermsErrorClassesNotChosen"
					 style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message code="design.form.classes.locarno.myTerms.classesNotChoosen" />
						</div>
					</div>
				</div>
				<div class="row-fluid myTermsErrorTermsEmpty" style="display: none;">
					<div class="span12">
						<div class="alert alert-ohim alert-error">
							<spring:message code="design.form.classes.locarno.myTerms.termsEmpty" />
						</div>
					</div>
				</div>
				<div class="genericErrorMyTerms"></div>
			</div>
			<div class="term-footer">
				<ul class="controls unstyled">
					<li><a class="cancelButton" data-dismiss="modal"><spring:message
							code="design.form.classes.locarno.myTerms.action.close" /></a></li>
					<li><a class="acceptMyOwnComplexTerms modalbtn" type="button"><spring:message
							code="design.form.classes.locarno.myTerms.action.ok" /> </a></li>
				</ul>
			</div>

		</div>
	</form>
	<jsp:include page="/WEB-INF/views/locarno/dsclass/locarno_ModalTemplates.jsp" />
</div>

<div id="termModal" class="modal hide fade modal-locarno-review">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <ul class="navModal">
			<li><a class="close-icon" data-dismiss="modal"></a></li>
            <li id="termsAmount"><span></span> <spring:message code="terms.modal.termsToReview"/></li>
        </ul>
        <h1><spring:message code="terms.modal.rejectionTerm"/></h1>
    </header>
    <!--modal-header-->
    <section>

        <h4><spring:message code="terms.modal.termToBeReviewed"/></h4>
        <table class="table table-striped">
            <tr>
                <th class="short"><spring:message code="terms.modal.tableHeader.class"/></th>
                <th><spring:message code="terms.modal.tableHeader.inputTerm"/></th>
            </tr>
            <tr>
                <td id="termClass" class="classId short"></td>

                <td id="termReviewed"></td>
            </tr>
        </table>
        <div id="termsEditing">
            <h4><spring:message code="terms.modal.foundTerms"/></h4>
            <table class="table table-striped" id="otherTerms">
                <thead>
                <tr>
                    <th class="short"><spring:message code="terms.modal.tableHeader.class"/></th>
                    <th><spring:message code="terms.modal.tableHeader.inputTerm"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <!--editOptions-->
        <a  class="link" id="replaceTerm"><spring:message code="terms.modal.viewandreplace"/><span class="replace"></span></a>
        <a class="link" id="searchTerm"></a>
        <span id="searchTermText" class="hidden"><spring:message code="terms.modal.searchforterm"/></span>
        
        <p class="disclaimer"><spring:message code="terms.modal.disclaimer"/></p>
    </section>
    <!--modal-body-->
    <footer>
        <ul id="termNavigation">
            <li><a href="#productIndicationTable" class="btn" id="prevTerm"><spring:message code="terms.modal.navigation.previousTerm"/></a></li>
            <li><a href="#productIndicationTable" class="btn" id="nextTerm"><spring:message code="terms.modal.navigation.nextTerm"/></a></li>
        </ul>

        <ul class="modalTermOptions">
            <li><a href="#productIndicationTable" class="btn btn-success" id="modalKeepTerm"><spring:message code="terms.modal.termAction.keep"/></a></li>
            <li><a href="#productIndicationTable" class="btn btn-danger" id="modalRemoveTerm"><spring:message code="terms.modal.termAction.remove"/></a></li>
            <li><a href="#productIndicationTable" class="btn btn-warning disabled" id="modalChangeTerm"><spring:message code="terms.modal.termAction.change"/></a></li>
        </ul>
    </footer>
</div>
</div>
</div>
<!--termModal-->
</div>