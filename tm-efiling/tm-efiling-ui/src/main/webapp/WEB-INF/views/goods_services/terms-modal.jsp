<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:choose>
    <c:when test="${flowModeId == 'gi-efiling'}">
        <spring:eval var="numberOfClasses" expression="@propertyConfigurer.getProperty('gs.number.classes.gi').trim()" />
    </c:when>
    <c:otherwise>
        <spring:eval var="numberOfClasses" expression="@propertyConfigurer.getProperty('gs.number.classes').trim()" />
    </c:otherwise>
</c:choose>
<div id="modal-gands-importHeading" class="modal fade modal-gands-mylist">
<div class="modal-dialog">
<div class="modal-content">
	<header>
	   <h1><spring:message code="gs.import.header"/></h1>
	   <a class="close-icon" data-dismiss="modal"></a>
	</header>
	<section class="modal-gands-body">
        <h2><spring:message code="gs.importClassHeading.chooseClass"/></h2>
        <div class="nice-classes" data-toggle="buttons-radio">
		<c:forEach begin="1" end="${numberOfClasses}" var="classHeading">
            <button type="button" class="btn">${classHeading}</button>
        </c:forEach>
        </div>
        <div class="nice-classes-headings">
			<h4><spring:message code="gs.terms.imported"/> : <span class="classHeading"></span></h4>
			<ul class="term-list-template" style="display:none;">
				<li class="term-valid">
					<span class="name"></span>
					<span class="fullname" style="display: none;" ></span>
				</li>
				<li class="term-invalid">
					<i class="term-invalid-icon"></i>
					<span class="name"></span>
					<span class="fullname" style="display: none;" ></span>
				</li>
				<li class="term-modifiable">
					<i class="term-modifiable-icon"></i>
					<span class="name"></span>
					<span class="fullname" style="display: none;" ></span>
				</li>
				<li class="term-not-found">
					<i class="term-not-found-icon"></i>
					<span class="name"></span>
					<span class="fullname" style="display: none;" ></span>
				</li>
			</ul>

		</div>
		<span class="flMessageError"></span>
    </section>
    <footer>
        <ul>
            <li><a href="#goodsandservices" class="Save"><spring:message code="" /> <spring:message code="gs.import.button.save" /></a></li>
            <li><a href="#goodsandservices" style="color: white" class="SaveAndClose btn btn-primary"><spring:message code="" /> <spring:message code="gs.import.button.done" /></a></li>
        </ul>
    </footer>
</div>
</div>
</div>

<div id="termModal" class="modal fade modal-gands-review">
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
            <li><a href="#goodsandservices" class="btn" id="prevTerm"><spring:message code="terms.modal.navigation.previousTerm"/></a></li>
            <li><a href="#goodsandservices" class="btn" id="nextTerm"><spring:message code="terms.modal.navigation.nextTerm"/></a></li>
        </ul>

        <ul class="modalTermOptions">
            <li><a href="#goodsandservices" class="btn btn-success" id="modalKeepTerm"><spring:message code="terms.modal.termAction.keep"/></a></li>
            <li><a href="#goodsandservices" class="btn btn-danger" id="modalRemoveTerm"><spring:message code="terms.modal.termAction.remove"/></a></li>
            <li><a href="#goodsandservices" class="btn btn-warning disabled" id="modalChangeTerm"><spring:message code="terms.modal.termAction.change"/></a></li>
        </ul>
    </footer>
</div>
</div>
</div>
<!--termModal-->

<div id="modal-gands-mylist" class="modal fade modal-gands-mylist">
<div class="modal-dialog">
<div class="modal-content">
	<header>
	   <h1><spring:message code="gs.providelist.header"/></h1>
	   <a class="close-icon" data-dismiss="modal"></a>
	</header>
	<section class="modal-gands-body">
        <div class="delay-warning"><component:helpMessage textCode="goods.services.take.longer.help.${flowModeId}"/></div>
        <h2><spring:message code="gs.providelist.label.choose" />:</h2>
        <div class="nice-classes" data-toggle="buttons-radio">
			<c:forEach begin="1" end="${numberOfClasses}" var="classHeading">
	            <button type="button" class="btn">${classHeading}</button>
	        </c:forEach>
        </div>
		<h3 class="hideOnClear hide"><spring:message code="gs.providelist.label.terms.${flowModeId}" /> <span class="class-number"></span></h3>
        <h6 class="hideOnClear hide classDescHolder"></h6>
		<textarea class="hideOnClear hide" rows="5"></textarea>
		<span class="flMessageError"></span>
    </section>
    <br/>
    <footer>
        <ul>
            <li><a href="#goodsandservices" class="Save"><spring:message code="gs.providelist.button.save" /></a></li>
            <li><button class="SaveAndClose"><spring:message code="gs.providelist.button.done" /></button></li>
        </ul>
    </footer>
</div>
</div>
</div>

<div id="modal-gands-editAsText" class="modal fade modal-gands-editAsText">
    <div class="modal-dialog">
        <div class="modal-content">
            <header>
                <h1><spring:message code="gs.editAsText.header"/>&nbsp;<span class="class-number"></span></h1>
                <a class="close-icon" data-dismiss="modal"></a>
            </header>
            <section class="modal-gands-body">
                <div class="delay-warning"><component:helpMessage textCode="goods.services.take.longer.help.${flowModeId}"/></div>
                <h2><spring:message code="terms.modal.editAsText"/></h2>

                <textarea rows="10" class="termsEditText"></textarea>
                <input type="hidden"class="termsEditClass"/>
                <span class="flMessageError"></span>
            </section>
            <br/>
            <footer>
                <ul>
                    <li><a href="#" data-dismiss="modal"><spring:message code="gs.translation.label.cancel" /></a></li>
                    <li><button class="SaveAndClose"><spring:message code="gs.providelist.button.done" /></button></li>
                </ul>
            </footer>
        </div>
    </div>
</div>

<div id="modal-translation" class="modal fade modal-translation">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1><spring:message code="gs.translation.header"/></h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
    <section class="modal-taxonomy-body">
		<h2><spring:message code="gs.translation.info.class" /> <span class="class-number"></span> <spring:message code="gs.translation.info.terms"/> <strong></strong></h2>
    	<textarea rows="5" cols="50"></textarea>
		<spring:eval var="termsSeperator" expression="@propertyConfigurer.getProperty('gs.terms.seperator')" />
 		<h3><spring:message arguments="${termsSeperator}" code="gs.provideList.info"/></h3>
		<span class="flMessageError"></span>
    </section>
    <footer>
        <ul>
            <li><a href="#" data-dismiss="modal"><spring:message code="gs.translation.label.cancel" /></a></li>
            <li><button><spring:message code="gs.translation.label.add" /></button></li>
        </ul>
    </footer>
</div> 
</div>
</div>

<c:set var="service_search_taxonomy" value="${configurationServiceDelegator.isServiceEnabled('Search_Taxonomy')}" />
<div id="modal-taxonomy" taxonomy-enabled="${!service_search_taxonomy}" class="modal fade modal-taxonomy">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1><spring:message code="gs.search.header" /></h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	
	<c:choose>
		<c:when  test="${!service_search_taxonomy}">
			<section class="modal-no-taxonomy-body">
				<div class="taxonomy-list">
					<div class="taxonomy-search-container">
						<input type="text" class="taxonomy-search-input" />
						<button class="taxonomy-search-button">
							<spring:message code="gs.search.label.search" />
						</button>
					</div>
					<div class="last-level">
					</div>
				</div>
			</section>
		</c:when>
		<c:otherwise>
			<section class="modal-taxonomy-body">
				<div class="taxonomy-tree">
					<div class="taxonomy-search-container">
						<input type="text" class="taxonomy-search-input" />
						<button class="taxonomy-search-button">
							<spring:message code="gs.search.label.search" />
						</button>
					</div>
					<ul class="first-level">
					</ul>
				</div>
				<div class="taxonomy-list">
					<ul class="last-level">
					</ul>
				</div>
			</section>
		</c:otherwise>
	</c:choose>
    <footer>
        <ul>
            <li><a data-dismiss="modal"><spring:message code="gs.search.label.cancel"/></a></li>
            <li>
                <span class="taxonomy-count-totals">
                    <span><strong class="taxonomy-count-terms"></strong> <spring:message code="gs.search.label.tems" /></span>
                    <span><strong class="taxonomy-count-classes"></strong> <spring:message code="gs.search.label.nice" /></span>
                </span>
                <button class="add-terms-button"><i class="add-terms-icon"></i><spring:message code="gs.search.label.add"/></button>
            </li>
        </ul>
    </footer>
</div>
</div>
</div>
