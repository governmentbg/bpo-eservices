<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="eu.ohim.sp.dsefiling.ui.util.LocarnoAutocompletProvider" %>
<spring:eval var="classificationProvider" expression="@propertyConfigurer.getProperty('classification.provider')" />
<c:set var="isUsingTmDsView" value="${classificationProvider == 'TMDSView'}"/>
<input type="hidden" id="isUsingTmDsView" value="${isUsingTmDsView}"/>
<script id="locarnoResults" type="text/template">
    <table class="ohim-table table-collapsable locarnoResultTable" id="locarnoResultTable">
        <thead>
        <tr>
            <th class="locarnoSelect"></th>
            <th class="productIndicationClassSubclass"><a class="sorting
                <@ if (sortBy == 'classification'){ @>
                    sorting_asc
                <@ } @>
                " data-sort="classification"><spring:message code="designInformation.locarno.modal.classSubclassTitle"/></a></th>
            <th class="locarnoResult"><a class="sorting
                <@ if (sortBy == 'alphabetical'){ @>
                    sorting_asc
                <@ } @>
                " data-sort="alphabetical"><spring:message code="designInformation.locarno.modal.indicationProductTitle"/></a></th>
            <@ if (${!isUsingTmDsView}){ @>
            <th><spring:message code="designInformation.locarno.modal.translation"/></th>
            <@ } @>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < data.length; i++){ @>
                <@
                var selectedClass = data[i].group ? 'locarnoTaxonomy' : 'termRow';
				var checked = false;
                for (var s = 0; s < alreadySelected.length; s++){
                    if(data[i].text == alreadySelected[s].text){
                        selectedClass += ' active';
						checked = "checked=\"checked\"";
						break;
                    }
                } @>
                <tr class="<@= selectedClass @>" data-product-indication-identifier="<@=data[i].identifier@>">
                    <td class="locarnoSelect">
						<@ if (!data[i].group && data[i].identifier) { @>
                        <label class="checkbox">
                                    <input type="checkbox" data-term="<@=data[i].text@>" data-rel="<@=i@>" <@=checked@>/>
                            <span>&nbsp;</span>
                        </label>
						<@ } @>
                    </td>
                    <td class="productIndicationClassSubclass classSubclass" data-classCode="<@=data[i].classCode@>"><@=data[i].classCode@>-<@=data[i].subclassCode@></td>
                    <td class="locarnoResult">
                        <div>
                            <p><@=data[i].text@></p>
                        </div>
                    </td>
					<@ if (${!isUsingTmDsView}){ @>
						<@ if (data[i].group){ @>
    	       		<td class="browseGroup">
						<a class="browse-icon browse-term" data-group-identifier="<@=data[i].identifier@>" data-class="<@=data[i].classCode@>" data-subclass="<@=data[i].subclassCode@>"></a>
	           		</td>
						<@ } else { @>	
							<@ if (!data[i].group && data[i].identifier){ @>
					<td>
						<div class="icon-chevron-down translateProductIndication" data-product-indication-identifier="<@=data[i].identifier@>">&nbsp;</div>
					<td>
							<@ }@>
						<@ }@>
					<@ }@>
            </tr>
            <@ if (${!isUsingTmDsView}){ @>
                <tr class="translationRow" data-product-indication-identifier="<@=data[i].identifier@>" style="display:none;">
                </tr>
            <@ } @>
        <@ } @>
        </tbody>
    </table>
	<@= pagination @>
    <a class="ico-pdf" target="_blank" href="<spring:message code="designInformation.locarno.modal.link.url"/>"><spring:message code="designInformation.locarno.modal.link.text"/></a>
    <span style="font-style:italic;"><spring:message code="designInformation.locarno.modal.link.date"/></span>
</script>
<script id="locarnoPagination" type="text/template">
    <@ if(total >0) { @>
    <div class="table-bottom">
        <div class="dataTables_info">

            <spring:message code="designInformation.locarno.modal.pagination.showingFrom"/>
            <@= (pageSize*currentPage) - (pageSize  - 1)@>
            <spring:message code="designInformation.locarno.modal.pagination.to"/>
            <@
            if(pageSize*currentPage > total) {
            print(total);
            } else {
            print(pageSize*currentPage);
            }
            @>
            (<@=total@> <spring:message code="designInformation.locarno.modal.pagination.inTotal"/>)

        </div>
        <div class="pagination">
            <ul>
                <li class="first
        <@ if (currentPage == 1){ @>
         disabled
        <@ } @>
         "><a class="first-page search-page" data-pag="1" href="#"><spring:message code="designInformation.locarno.modal.pagination.first"/></a></li>
                <li class="prev
        <@ if (currentPage == 1){ @>
         disabled
        <@ } @>
        "><a class="prev-page search-page" data-pag="<@=currentPage -1@>" href="#"><spring:message code="designInformation.locarno.modal.pagination.previous"/></a></li>
                <@ for (var n = 0; n < total/pageSize; n++){ @>
                <li class="
            <@ if ( n == currentPage -1) { @>
                active
            <@ } @>
            "><a href="#" class="search-page
            <@ if (n > topLimit) { @>
                 hide
            <@ } @>
            <@ if (n < bottomLimit) { @>
                 hide
            <@ } @>
            " data-pag="<@=n +1@>"><@=n +1@></a></li>

                <@ } @>
                <li class="next
            <@ if (currentPage == Math.ceil(total/pageSize)){ @>
             disabled
            <@ } @>

        "><a class="next-page search-page" data-pag="<@=currentPage +1@>" href="#"><spring:message code="designInformation.locarno.modal.pagination.next"/></a></li>
                <li class="last
            <@ if (currentPage == Math.ceil(total/pageSize)){ @>
             disabled
            <@ } @>

        "><a class="last-page search-page" data-pag="<@=Math.ceil(total/pageSize)@>" href="#"><spring:message code="designInformation.locarno.modal.pagination.last"/></a></li>
            </ul>
        </div>
    </div>
    <@ } @>
</script>
<script id="locarnoResultsInfo" type="text/template">
    <strong>
        <span class="locarnoResultAmount"><@=locarnoResultAmount@></span>
        <spring:message code="designInformation.locarno.modal.productIndicationAmount.small"/>
    </strong>
</script>

<script id="replicatedProductIndication" type="text/template">
    <div id="wrap-generated">
        <table class="ohim-table table-collapsable generated locarnoResultTable" id="locarno-generated">
            <tr class="active">
                <@= data@>
            </tr>
        </table>
    </div>
</script>

<script id="locarnoSelectedTable" type="text/template">
    <table class="ohim-table table-collapsable" id="gs-selected">
        <thead>
        <tr>
            <th class="locarnoSelect"></th>
            <th class="productIndicationClassSubclass"><spring:message code="designInformation.locarno.modal.classSubclassTitle"/></th>
            <th class="locarnoResult"><spring:message code="designInformation.locarno.modal.indicationProductTitle"/></th>
            <@ if (${!isUsingTmDsView}){ @>
                <th><spring:message code="designInformation.locarno.modal.translation"/></th>
            <@ } @>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < data.length; i++){ @>
        <tr data-product-indication-identifier="<@=data[i].identifier@>">
            <td class="locarnoSelect">
                <label class="checkbox">
                    <input type="checkbox" data-rel="<@=i@>" data-pos="<@=data[i].pos@>" data-term="<@=data[i].term@>" checked/>
                    <span>&nbsp;</span>
                </label>
            </td>
            <td class="productIndicationClassSubclass classSubclass" data-classCode="<@=data[i].classCode@>"><@=data[i].classCode@>-<@=data[i].subclassCode@></td>
            <td class="locarnoResult">
                <p><@=data[i].text@></p>
            </td>
            <@ if (${!isUsingTmDsView}){ @>
                <td>
					<@ if (data[i].identifier){ @>
                    <div  class="icon-chevron-down translateProductIndication" data-product-indication-identifier="<@=data[i].identifier@>">&nbsp;</div>
					<@ } @>
                </td>
            <@ } @>
        </tr>
        <@ if (${!isUsingTmDsView}){ @>
            <tr class="translationRow" data-product-indication-identifier="<@=data[i].identifier@>" style="display:none;">
            <tr>
        <@ } @>
        <@ } @>
        </tbody>
    </table>
</script>

<script id="locarnoNavTaxonomy" type="text/template">
    <table class="ohim-table table-collapsable" id="locarnoTaxonomy">
        <thead>
        <tr>
            <th class="locarnoSelect"></th>
            <th class="locarnoResult"><spring:message code="designInformation.locarno.modal.indicationProductTitle"/></th>
            <th class="productIndicationClassSubclass"><spring:message code="designInformation.locarno.modal.classSubclassTitle"/></th>
			<th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < data.length; i++){ @>
        <tr class="locarnoTaxonomy">
            <td>
            </td>
            <td>
                <p><@=data[i].text@></p>
            </td>
            <td class="productIndicationClassSubclass classSubclass"><@=data[i].classCode@><@=data[i].subclassCode ? "-"+data[i].subclassCode : "" @></td>
            <td class="browseGroup">
				<a class="browse-icon browse-term" data-class="<@=data[i].classCode@>" data-subclass="<@=data[i].subclassCode@>"></a>
            </td>
        </tr>
        <@ } @>
        </tbody>
    </table>
	<@= pagination @>
</script>
<script id="locarnoSuggestion" type="text/template">
<div id="locarnoPopover" class="popover popover-gs searchablelinks editable">
	<div class="popover-inner">
		<h2 class="popover-title"></h2>
        <section class="popover-section class-description">
            <spring:message code="designInformation.locarno.popover.notCompliant"/>
        </section>
        <section class="popover-section class-browse row-space-s">
            <p>
                <span class="terms-suggestion"><spring:message code="designInformation.locarno.popover.checkSuggestion"/></span>
            </p>
            <div id="termsEditing">
                <div class="otherTerms">
				<@ if(_.isArray(data.relatedTerms)) { 
					for (var i = 0; i < data.relatedTerms.length; i++){ 
					var related = data.relatedTerms[i];
				@>
					<label class="checkbox" for="replaceTerm<@=i@>">
						<input type="checkbox" name="replaceTerm" id="replaceTerm<@=i@>" value="<@=related.text@>" data-index="<@=i@>">
						<span><@=related.text@>&nbsp;(<spring:message code="designInformation.locarno.modal.class"/> <@=related.classCode@>-<@=related.subclassCode@>)</span>
					</label>
				<@ 	
						}
					} 
				@>
				</div>
            </div>
            <div class="page-options">
                <ul class="modalTermOptions unstyled pull-right">
                    <li>
                        <a class="aside-link mini" id="popoverCancel">
                            <spring:message code="designInformation.locarno.modal.cancel"/>
                        </a>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-mini-xs" id="popoverChangeTerm">
                            <spring:message code="designInformation.locarno.popover.change"/>
                        </a>
                    </li>
                </ul>
            </div>
        </section>
        <section class="popover-section">
            <spring:message code="designInformation.locarno.popover.footer"/>
        </section>
    </div>
</div>
</script>

<div id="errorTermAlreadyAdded" class="hidden">
    <p><spring:message code="designInformation.locarno.modal.termAlreadyAdded" /></p>
    <nav class="popover-menu row-fluid">
        <div class="span6">
            <a class="link cancelAdd"><spring:message code="designInformation.locarno.modal.cancel"/></a>
        </div>
        <div class="span6">
            <a class="btn btn-primary btn-mini" disabled="disabled"><spring:message code="designInformation.locarno.modal.ok"/></a>
        </div>
    </nav>
</div>

<div id="errorNoMoreTermsToBeAdded" class="hidden">
    <p><spring:message code="designInformation.locarno.modal.termsExceed" arguments="${configurationServiceDelegator.getValue('locarno.add.maxNumber', 'design')}"/></p>
    <nav class="popover-menu row-fluid">
        <div class="span6">
            <a class="link cancelAdd"><spring:message code="designInformation.locarno.modal.cancel"/></a>
        </div>
        <div class="span6">
            <a class="btn btn-primary btn-mini" disabled="disabled"><spring:message code="designInformation.locarno.modal.ok"/></a>
        </div>
    </nav>
</div>

<div id="errorClassNotAllowed" class="hidden">
    <p><spring:message code="designInformation.locarno.modal.classNotAllowed"/></p>
    <nav class="popover-menu row-fluid">
        <div class="span6">
            <a class="link cancelAdd"><spring:message code="designInformation.locarno.modal.cancel" arguments="${configurationServiceDelegator.getValue('locarno.add.maxNumber', 'design')}"/></a>
        </div>
        <div class="span6">
            <a class="btn btn-primary btn-mini" disabled="disabled"><spring:message code="designInformation.locarno.modal.ok"/></a>
        </div>
    </nav>
</div>
