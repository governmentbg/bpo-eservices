<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:set var="sectionId" value="goodsandservices" scope="request"/>
<jsp:include page="/WEB-INF/views/goods_services/terms-modal.jsp"/>

<sec:authorize access="hasRole('Goods_Services_Add')" var="security_goods_add" />

<c:set var="service_search_taxonomy" value="${configurationServiceDelegator.isServiceEnabled('Search_Taxonomy')}" /> 
<c:set var="service_search_term" value="${configurationServiceDelegator.isServiceEnabled('Search_Term')}" /> 
<c:set var="service_nice_class_heading" value="${configurationServiceDelegator.isServiceEnabled('Import_Nice_Class_Heading')}" />
<c:set var="service_fasttrack"
	   value="${configurationServiceDelegator.isServiceEnabledByFlowMode('FastTrack', flowModeId)}"/>

<section class="classes" id="goodsandservices">
	<header>
		<h2><spring:message code="gs.title.${flowModeId}"/><span class="requiredTag">*</span><span class="tip"></span></h2>
	</header>
	<c:if test="${security_goods_add || !configurationServiceDelegator.securityEnabled}">
		<div><spring:message code="gs.intro.${flowModeId}" /><component:fastTrackMark sectionId="${sectionId}" helpMessageKey="fasttrack.help.goodsandservices.intro"/>
			<a rel="classes-help-tooltip" class="classes-disclaimer-icon"></a>
	        <div data-tooltip="help" class="hidden">
	            <a class="close-popover"></a>
	            <component:helpMessage textCode="goods.services.protect.help" useFlowModeId="true"/>
	        </div>
	    </div>
		<p>
			<c:if test="${service_search_term}" >
                <a href="#modal-gs" id="oneform" class="choose-terms-button"><spring:message code="gs.newModal.triggerText"/></a>
            </c:if>
			<component:generic path="provideMyOwnList" checkRender="true">
				<a class="provide-list-anchor" data-target="#modal-gands-mylist" data-toggle="modal"><spring:message code="gs.action.provideList.${flowModeId}"/></a>
			</component:generic>
			<c:if test="${service_nice_class_heading}">
				<component:generic path="importClassHeading" checkRender="true">
					<a class="provide-list-anchor" data-target="#modal-gands-importHeading" data-toggle="modal" id="getClassHeading"><spring:message code="gs.action.importNiceClass"/></a>
				</component:generic>
			</c:if>
		</p>
	</c:if>
	<div class="alert alert-info">
		<spring:message code="gs.message.alpha.list.warn.${flowModeId}" htmlEscape="false"/>
	</div>
	<ul class="language-tabs">
	    <li class="active"><a href="en" data-toggle="tab"></a></li>
	    <li><a href="de" data-toggle="tab"></a></li>
	</ul>
	<span class="provide-translation-info">
		<i class="provide-translation-icon"></i>
		<i class="provide-translation-arrow"></i>
		<span class="provide-translation"><spring:message code="gs.message.provideTranslation" /></span>
	</span>
	<table id="tableGoodsAndServices" class="${service_fasttrack? 'fastTrackEnabled' : ''}">
	    <thead>
	        <tr>
	            <th><spring:message code="gs.class"/><a class="sorter asc"></a></th>
				<th>
					<spring:message code="gs.inputTerm"/>
				</th>
				<th class="borderless">
					<span class="show-taxonomy">
						<span>
							<img src="/sp-ui-tmefiling/resources/img/preloader.gif" id="preloaderTaxonomy" style="display: none; float:left">
							<spring:message code="gs.message.complete.taxonomy" />
						</span>
						<spring:message code="gs.message.provideTranslation" var="translation"/>
						<a data-content="${translation}" id="gsbuilderShowPathButton"
						class="btn filter ico-path-icon-button span2" href=""></a>
					</span>
				</th>
				<th class="borderless">
				</th>
	        </tr>
	    </thead>
	    <tbody>
			<tr class="template">
				<td class="class-number"></td>
				<td colspan="2">
					<span class="firstLanguageTermsInfo"><strong></strong><br>
					</span>
					<ul class="term-list">
						<li class="term-valid">
							<span class="name editable" rel="term-tooltip" title="<spring:message code="gs.button.label.edit" />"></span>
							<span class="fullname" style="display: none;" ></span>
							<a rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a>
						</li>
						<li class="term-modifiable">
							<i class="term-modifiable-icon"></i>
							<span class="name editable" rel="term-tooltip" title="<spring:message code="gs.button.label.edit" />"></span>
							<span class="fullname" style="display: none;" ></span>
							<a rel="term-tooltip" title="<spring:message code="gs.button.label.view" />" class="term-search"></a>
							<a  rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a>
						</li>
						<li class="term-not-found">
							<i class="term-not-found-icon"></i>
							<span class="name editable" rel="term-tooltip" title="<spring:message code="gs.button.label.edit" />"></span>
							<span class="fullname" style="display: none;" ></span>
							<a rel="term-tooltip" title="<spring:message code="gs.button.label.view" />" class="term-search"></a>
							<a  rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a>
						</li>
						<li class="term-invalid">
							<span class="name editable" rel="term-tooltip" title="<spring:message code="gs.button.label.edit" />"></span>
							<span class="fullname" style="display: none;" ></span>
							<a rel="term-tooltip" title="<spring:message code="gs.button.label.view" />" class="term-search"></a>
							<a rel="term-tooltip" title="<spring:message code="gs.button.label.remove" />" class="term-close"></a>
						</li>
					</ul>	
					<ul class="term-translation"><li><a href="#modal-translation" data-toggle="modal"><spring:message code="gs.action.provideTranslation" /></a></li></ul>
<%--					<c:if test="${service_search_term}" >--%>
<%--						<div class="disableRemoval">--%>
<%--							<input class="importedNiceClassHeading" data-language="" data-classId="" type="checkbox" name="checkboxtype" />--%>
<%--							<spring:message code="gs.classHeading.coverage"/>--%>
<%--						</div>--%>
<%--					</c:if>--%>
				</td>
				<td class="class-options">
					<a rel="term-tooltip" class="term-close" title="<spring:message code='gs.button.label.removeClass'/>"></a>
					<a rel="term-tooltip" class="term-edit" title="<spring:message code='gs.button.label.editAsText'/>"></a>
				</td>
			</tr>
	    </tbody>
	</table>
	<component:generic path="termsWarning" checkRender="true">
		<ul class="termsDefinition">
			<li class="termValid">
				<strong><spring:message code="gs.terms.valid"/>: </strong>"<spring:message code="gs.terms.valid.description"/>"
			</li>
			<li class="termInvalid">
				<strong><spring:message code="gs.terms.invalid"/>: </strong>"<spring:message code="gs.terms.invalid.description.${flowModeId}"/>"
			</li>
			<li class="termEditable">
				<strong><spring:message code="gs.terms.editable"/>: </strong>"<spring:message code="gs.terms.editable.description.${flowModeId}"/>"
			</li>
			<li class="termNotFound">
				<strong><spring:message code="gs.terms.notFound"/>: </strong>"<spring:message code="gs.terms.notFound.description.${flowModeId}"/>"
			</li>
		</ul>
	</component:generic>
</section>
<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
	/trademark/register-trademark/goodsservices
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#tableGoodsAndServices").refreshGS({langId: getLanguageInfo().first, languageInfo : getLanguageInfo()});
});
</script>
