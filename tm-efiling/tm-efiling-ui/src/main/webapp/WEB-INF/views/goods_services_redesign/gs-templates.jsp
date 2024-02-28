<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script id="gsResults" type="text/template">
    <table class="ohim-table table-collapsable gs-table" id="gs-table">
        <thead>
        <tr>
            <th class="gs-select"></th>
            <th class="span10"><a class="sorting
                <@ if (orderBy){ @>
                    <@ if (orderBy == 'ASC' && sortBy == 'text'){ @>
                        sorting_asc
                    <@ } @>
                    <@ if (orderBy == 'DESC' && sortBy == 'text'){ @>
                        sorting_desc
                    <@ } @>
                <@ } @>
                " data-sort="text"><spring:message code="gs.newModal.term"/></a></th>
            <th class="span1"><a class="sorting
                <@ if (orderBy){ @>
                    <@ if (orderBy == 'ASC' && sortBy == 'niceClass'){ @>
                        sorting_asc
                    <@ } @>
                    <@ if (orderBy == 'DESC' && sortBy == 'niceClass'){ @>
                        sorting_desc
                    <@ } @>
                <@ } @>
                " data-sort="niceClass"><spring:message code="gs.newModal.class"/></a></th>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < data.length; i++){ @>
                <@
                var selectedClass = '';
                for (var s = 0; s < alreadySelected.length; s++){
                    if(data[i].term == alreadySelected[s].term){
                        selectedClass = 'active'
                    }
                } @>
            <tr class="<@= selectedClass @>">
            <td class="gs-select">
                <@ if (data[i].scopeAvailability){ @>
                <label class="checkbox">
                            <input type="checkbox" data-term="<@=data[i].term@>" data-rel="<@=i@>"
                                <@ if(selectedClass == 'active') { @>
                                    checked="checked"
                                <@ } @>
                            />
                    <span>&nbsp;</span>
                </label>
                <@ } @>
                <@ if (!data[i].scopeAvailability){ @>
                <label class="checkbox disabled" data-toggle='popover' data-container="body" data-placement='top' data-content='<spring:message code="gs.newModal.tooVague"/>'>
                    <input type="checkbox" data-rel="<@=i@>" disabled="disabled"/>
                    <span>&nbsp;</span>
                </label>
                <@ } @>
            </td>
            <td class="gs-result">
                <div>
                    <p><@=data[i].term@></p>
                    <@ if (pro){ @>


  						<@  
							var newul = 'false'; 
						@>

                 
                        <@ for (var s = 0; s < data[i].subcategories.length; s++){ @>
							
							 <@ if((data[i].subcategories[s].level)==1 && newul=='true'){  newul='false'	  @>

							   </ul>
						  	 <@ } @>


							 <@ if((data[i].subcategories[s].level)==1 ){  	 newul='true'; @>

							   <ul class="pro-path">
						  	 <@ } @>

                                <li>
                                  <a class="browse-term" data-id="<@=data[i].subcategories[s].id@>" data-class="<@=data[i].cat@>" data-term="<@=data[i].subcategories[s].description@>">
                                  <span><@=data[i].subcategories[s].description@></span>
                                </a>
                       			</li>
                        <@ } @>
                   


                    <@ } @>
                    <@ if (!pro){ @>
                    <ul>
					<@ if (data[i].subcategories[1]){ @>
                        <li>
                            <a class="similar-generic browse-term" data-id="<@=data[i].subcategories[1].id@>" data-scope="<@=data[i].scopeAvailability@>" data-lang="<@=data[i].lang@>" data-class="<@=data[i].cat@>" data-term="<@=data[i].subcategories[1].description@>">
                                <span><spring:message code="gs.newModal.viewSimilar"/></span>
                            </a>
                        </li>
					<@ } @>
					<@ if (data[i].subcategories[0]){ @>
                        <li>
                            <a class="similar-generic browse-term" data-id="<@=data[i].subcategories[0].id@>" data-scope="<@=data[i].scopeAvailability@>" data-lang="<@=data[i].lang@>" data-class="<@=data[i].cat@>" data-term="<@=data[i].subcategories[0].description@>">
                                <span><spring:message code="gs.newModal.viewGeneric"/></span>
                            </a>
                        </li>
					<@ } @>

                    </ul>
                    <@ } @>
                </div>
            </td>
            <td class="gs-class">
                <@=data[i].cat@>
            </td>
        </tr>
        <@ } @>
        </tbody>
    </table>
   <@ if(amountPages > 1) { @>        
    <div class="table-bottom">
        <div class="dataTables_info">
            <spring:message code="gs.newModal.pagination.showingFrom"/>
            <@= (pageSize*currentPage) - (pageSize  - 1)@>
            <spring:message code="gs.newModal.pagination.to"/>
            <@
            if(pageSize*currentPage > total) {
                print(total);
            } else {
                print(pageSize*currentPage);
            }
        @>           
            (<@=total@> <spring:message code="gs.newModal.pagination.inTotal"/>)
        </div>
        <div class="pagination">
            <ul>
                <li class="first
                <@ if (currentPage == 1){ @>
                 disabled
                <@ } @>
                 "><a class="first-page search-page" data-pag="1" href="#"><spring:message code="gs.newModal.pagination.first"/></a></li>
                <li class="prev
                <@ if (currentPage == 1){ @>
                 disabled
                <@ } @>
                "><a class="prev-page search-page" data-pag="<@=currentPage -1@>" href="#"><spring:message code="gs.newModal.pagination.previous"/></a></li>
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

                "><a class="next-page search-page" data-pag="<@=currentPage +1@>" href="#"><spring:message code="gs.newModal.pagination.next"/></a></li>
                <li class="last
                    <@ if (currentPage == Math.ceil(total/pageSize)){ @>
                     disabled
                    <@ } @>

                "><a class="last-page search-page" data-pag="<@=Math.ceil(total/pageSize)@>" href="#"><spring:message code="gs.newModal.pagination.last"/></a></li>
            </ul>
        </div>
    </div>
    <@ } @>    
</script>

<script id="gsTerms" type="text/template">
    <table class="ohim-table table-collapsable gs-table" id="gs-terms">
        <thead>
        <tr>
            <th class="gs-select"></th>
            <th class="span10"><spring:message code="gs.newModal.term"/></th>
            <th class="span1"></th>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < data.length; i++){ @>
                <@
                var selectedClass = '';
                for (var s = 0; s < alreadySelected.length; s++){
                    if(data[i].term == alreadySelected[s].term){
                        selectedClass = 'active'
                    }
                } @>
                <tr class="<@= selectedClass @>">
            <td class="gs-select">
                <@ if (data[i].scopeAvailability){ @>
                <label class="checkbox">
                            <input type="checkbox" data-term="<@=data[i].term@>" data-rel="<@=i@>"
                            <@ if(selectedClass == 'active') { @>
                                checked="checked"
                            <@ } @>
                            />
                    <span>&nbsp;</span>
                </label>
                <@ } @>
                <@ if (!data[i].scopeAvailability){ @>
                <label class="checkbox disabled" data-toggle='popover' data-container="body" data-placement='top' data-content='<spring:message code="gs.newModal.tooVague"/>'>
                    <input type="checkbox" data-rel="<@=i@>" disabled="disabled"/>
                    <span>&nbsp;</span>
                </label>
                <@ } @>
            </td>
            <td class="gs-result">
                <p><@=data[i].term@></p>
            </td>
            <@ if (data[i].id == null){ @>
            <td class="no-browse">
                <span></span>
            </td>
            <@ } @>
            <@ if (data[i].id != null){ @>
            <td class="gs-browse">
                <a class="browse-icon browse-term" data-class="<@=data[i].cat@>"  data-id="<@=data[i].id@>" data-term="<@=data[i].term@>"></a>
            </td>
            <@ } @>
        </tr>
        <@ } @>
        </tbody>
    </table>
  <@ if(amountPages > 1) { @>
    <div class="table-bottom">
        <div class="dataTables_info">
        <spring:message code="gs.newModal.pagination.showingFrom"/>
        <@= (pageSize*currentPage) - (pageSize  - 1)@>
        <spring:message code="gs.newModal.pagination.to"/> 
        <@
        if(pageSize*currentPage > total) {
            print(total);
        } else {
            print(pageSize*currentPage);
        }
        @>
        (<@=total@>  <spring:message code="gs.newModal.pagination.inTotal"/>)
		
		
		
       </div>
            <div class="pagination">
                <ul>
                    <li class="first
                    <@ if (currentPage == 1){ @>
                     disabled   
                    <@ } @>
                     "><a class="first-page browse-page" data-class="<@=dataClass@>" data-id="<@=currentId@>" data-pag="1" href="#">First</a></li>
                    <li class="prev
                    <@ if (currentPage == 1){ @>
                     disabled   
                    <@ } @>
                    "><a class="prev-page browse-page" data-class="<@=dataClass@>" data-id="<@=currentId@>" data-pag="<@=currentPage -1@>" href="#">Previous</a></li>
                     <@ for (var n = 0; n < total/pageSize; n++){ @>
                        <li class="
                        <@ if ( n == currentPage -1) { @>
                            active
                        <@ } @>
                        "><a  href="#" data-id="<@=currentId@>" data-class="<@=dataClass@>" class="browse-page
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

                    "><a class="next-page browse-page" data-class="<@=dataClass@>" data-id="<@=currentId@>" data-pag="<@=currentPage +1@>" href="#">Next</a></li>
                    <li class="last
                        <@ if (currentPage == Math.ceil(total/pageSize)){ @>
                         disabled   
                        <@ } @>

                    "><a class="last-page browse-page" data-class="<@=dataClass@>" data-id="<@=currentId@>" data-pag="<@=Math.ceil(total/pageSize)@>" href="#">Last</a></li>
                </ul>
            </div>    
            </div>
        <@ } @>   	
</script>

<script id="gsSelected" type="text/template">
    <table class="ohim-table table-collapsable" id="gs-selected">
        <thead>
        <tr>
            <th class="gs-select"></th>
            <th class="span10"><spring:message code="gs.newModal.term"/></th>
            <th class="span1"><spring:message code="gs.newModal.class"/></th>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < data.length; i++){ @>
        <tr>
            <td class="gs-select">
                <label class="checkbox">
                    <input type="checkbox" data-container="body" data-rel="<@=i@>" data-pos="<@=data[i].pos@>" data-term="<@=data[i].term@>" checked/>
                    <span>&nbsp;</span>
                </label>
            </td>
            <td class="gs-result">
                <p><@=data[i].term@></p>
            </td>
            <td class="gs-class">
                <@=data[i].cat@>
            </td>
        </tr>
        <@ } @>
        </tbody>
    </table>
</script>

<script id="gsClasses" type="text/template">
    <table class="ohim-table table-collapsable terms-table gs-table" id="classes-table">
        <thead>
        <tr>
            <th class="span10"><spring:message code="gs.newModal.term"/></th>
            <th class="span1"><spring:message code="gs.newModal.class"/></th>
            <th class="span1">&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <@ for (var i = 0; i < goods.length; i++){ @>
        <tr data-class="<@=goods[i].cat@>">
            <td class="gs-result">
                <div>
                    <@  if(goods[i].term.length > 102) { @>
                    <div class="full-term"><@=goods[i].term@></div>
                    <div class="short-term"><@=goods[i].term.replace(/^(.{101}[^\s]*).*/, "$1") @>...</div>
                    <a class="read-more"></a>
                    <@ } @>
                    <@  if(goods[i].term.length <= 102) { @>
                        <div><@=goods[i].term@></div>
                    <@ } @>
                </div>
            </td>
            <td class="gs-class">
                <@=goods[i].cat@>
            </td>
            <td class="gs-browse">
                <a class="browse-icon browse-term" data-id="<@=goods[i].taxoConceptNodeId@>" data-class="<@=goods[i].cat@>" data-class="<@=goods[i].cat@>" data-term="<@=goods[i].term@>"  data-mainterm="<@=goods[i].term@>"></a>
            </td>
        </tr>
        <@ } @>
        </tbody>
    </table>
</script>

<script id="gsSearchInfo" type="text/template">
    <strong>
        <span class="terms-amount"><@=termsAmount@></span>
        <spring:message code="gs.newModal.terms.small"/>
    </strong>
    <@  if(searchTerm.length > 0) { @>
        <spring:message code="gs.newModal.whenSearching"/> <strong><@=searchTerm@></strong>
    <@ } @>
    <@  if(sources.length > 0 && sources == "Nice") { @>
        <spring:message code="gs.newModal.searchingIn.nice" htmlEscape="false"/></strong>
    <@ } @>
</script>

<script id="gsAddClass" type="text/template">
    <nav class="popover-menu row-fluid">
        <div class='span6'>
            <a class='link cancelAdd'><spring:message code="gs.newModal.cancel"/></a>
        </div>
        <div class='span6'>
            <a class='btn btn-primary btn-mini addItem'><spring:message code="gs.newModal.ok"/></a>
        </div>
    </nav>
</script>

<script id="gsRemoveClass" type="text/template">
    <nav class="popover-menu row-fluid">
        <div class='span6'>
            <a class='link cancelRemoveClassButton'><spring:message code="gs.newModal.cancel"/></a>
        </div>
        <div class='span6'>
            <a class='btn btn-primary btn-mini removeClassButton'><spring:message code="gs.newModal.ok"/></a>
        </div>
    </nav>
</script>

<script id="gsReplicated" type="text/template">
    <div id="wrap-generated">
        <table class="ohim-table table-collapsable generated gs-table" id="gs-generated">
            <tr class="active">
                <@= data@>
            </tr>
        </table>
    </div>
</script>

<script id="gsNav" type="text/template">
    <div id="nav-gs">
        <@ for (var i = 0; i < goods.length; i++){ @>

        <@ if (i == 0){ @>
        <div id="nav-goods">
            <p><a data-id="goods"><spring:message code="gs.newModal.goods"/></a></p>
            <ul>
                <@ } @>
                <@ if (i == 34){ @>
            </ul>
        </div>
        <div id="nav-services">
            <p><a data-id="services"><spring:message code="gs.newModal.services"/></a></p>
            <ul>
                <@ } @>
                <li>
                    <a data-class="goods-popover" data-toggle='popover' data-container='body' data-id="<@=goods[i].taxoConceptNodeId@>" data-placement='top' data-content="<@=goods[i].term@>" data-type='goods' data-class='<@=goods[i].cat@>'><@=i +1@></a>
                </li>
                <@ } @>
            </ul>
        </div>

        </ul>
    </div>
    </div>
</script>

<script id="gsBreadcrumb" type="text/template">
    <@  if(classDescription.length > 140) { @>
    <div class="gs-title full-term"><@= classDescription@></div>
    <div class="gs-title short-term"><@=classDescription.replace(/^(.{140}[^\s]*).*/, "$1") @>...</div>
    <a class="read-more"></a>
    <@ } @>
    <@  if(classDescription.length <= 140) { @>
        <div class="gs-title short-term"><@=classDescription@></div>
    <@ } @>

    <ul id="gs-breadcrumb">
        <@ for (var i = 0; i < parents.length; i++){ @>
        <li
        <@ if (i == parents.length -1 && i > 0 ) {@>
        class="final-step"
        <@ } @>

        ><a class="browse-term" data-class="<@=dataClass@>"  data-id="<@=parents[i].id@>"><@=parents[i].description@></a></li>
        <@ if (parents.length == 1 ) {@>
        <li class="final-step"><a></a></li>
        <@ } @>

        <@ } @>
    </ul>
</script>