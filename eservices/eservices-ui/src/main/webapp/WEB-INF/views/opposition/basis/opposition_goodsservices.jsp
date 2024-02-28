<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="goodsandservices" scope="request"/>

<sec:authorize access="hasRole('Goods_Services_Add')" var="security_goods_add" />

<section class="classes" id="goodsandservices">
	
	<div id="manuallyGS" style="display: none">	
		<p>
			<label><spring:message code="gs.action.provideList.${earlierEntitleMent}"/><span class="requiredTag">*</span></label>
			<a class="provide-list-anchor" data-target="#modal-gands-mylist" data-toggle="modal"><spring:message code="gs.action.provideList"/></a>
		</p>
	</div>
	<br />
	<div style="display: none">	
		<ul class="language-tabs">
		    <li class="active"><a href="${configurationServiceDelegator['languageOffice']}" data-toggle="tab"></a></li>
		</ul>
	</div>
	<div class="showHideAll" style="margin: 5px">
		<a class="show-hide-gs-link showAllTerms" href="#" style="display: none"><spring:message code="gs.show.all.terms"/></a>
		<a class="show-hide-gs-link hideAllTerms" href="#" style="display: none"><spring:message code="gs.hide.all.terms"/></a>
	</div>
	<table id="tableGoodsAndServices">
	    <thead>
	        <tr>
	            <th><spring:message code="gs.class"/><a class="sorter asc"></a></th>
	            <th><spring:message code="gs.inputTerm"/></th>
	            <th class="term-options"></th>
	        </tr>
	    </thead>
	    <tbody>
			<tr class="template">
				<td class="class-number"></td>
				<td>
					<span class="firstLanguageTermsInfo"><strong></strong><br>
					</span>
					<ul class="term-list notRemovableClass">
						<li class="term-valid"><span class="name"></span><a rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a></li>
						<li class="term-modifiable"><span class="name"></span><a rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a></li>
						<li class="term-notEditable"><span class="name"></span><a rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a></li>
					</ul>
					<span>
						<a class="show-hide-gs-link showClassTerms" href="#" style="display: none"><spring:message code="gs.show.class.terms"/></a>
						<a class="show-hide-gs-link hideClassTerms" href="#" style="display: none"><spring:message code="gs.hide.class.terms"/></a>
					</span>
					<ul class="term-translation"><li><a href="#modal-translation" data-toggle="modal"><spring:message code="gs.action.provideTranslation" /></a></li></ul>
				</td>
				<td class="term-options notRemovableClass"><a rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a></td>
			</tr>
	    </tbody>
	</table>
</section>
