<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<section class="trademark">

 	<header>
        <a id="applicant" class="anchorLink">
        </a>

        <h2><spring:message code="tm_opposed.details.title"></spring:message></h2>
    </header>  
    
     <jsp:include page="/WEB-INF/views/opposition/tm_opposed_card_list.jsp"/>
    
    <button id="opposedTradeMarkTrigger" class="add-button" data-toggle="button" type="button">
       <i class="add-icon"></i>
       <spring:message code="tm_opposed.section.button.trademark"/>
	</button>

    <div id="tabOpposedTradeMark" class="addbox" style="display: none">
    	<div id="opposedTradeMarkSection"></div>
    	
	</div>
  	
  	<div id="applicantMatches">
    	<jsp:include page="/WEB-INF/views/applicant/applicantMatches.jsp"/>
 	</div>
 	
 	<input type="hidden" id="maxOTMs"
       value="${configurationServiceDelegator.getValue('opposedTradeMark.maxNumber', 'general')}">    
        <script type="text/javascript">
        	checkMaxOpposedTMs();
        </script>
  	
	<div style="display:none">
		<input id="applicationType" value="${flowModeId}"/>
	</div>

  	<label>Extent of the opposition</label>
		<ul class="reuse-applicant-data" >
			<li><label><input type="radio" name="allGoodsServices" value="true" checked id="allGoodsServicesYES">Against all goods and services in the application</label></li>
			<li><label><input type="radio" name="allGoodsServices" value="false" id="allGoodsServicesYES">Against some goods and services in the application</label></li>
		</ul>


        
</section>