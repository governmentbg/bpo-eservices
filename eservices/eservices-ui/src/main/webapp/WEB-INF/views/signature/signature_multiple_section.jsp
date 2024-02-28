<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/scripts/developers/signature.js">
</script>

<%-- <tiles:useAttribute id="sections_list" name="signature_content" classname="java.util.List"/> --%>


<section class="trademark" id="signature">

    <header>
        <a id="signature" class="anchorLink"></a>

        <h2>
            <spring:message code="signature.title"/>
        </h2>
    </header>
	<component:generic path="formWarnings" checkRender="false">
	    	<c:set var="errors">
	        	<form:errors path="formWarnings"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<div id="customWarningMessage" class="hidden">${message}</div>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>	    
	    
	    <component:generic path="formMessages" checkRender="true">
	    	<c:set var="errors">
	        	<form:errors path="formMessages"></form:errors>
	        </c:set>	    
			<c:if test="${!empty errors}">
		    <c:forEach items="${errors}" var="message">
		       	<p class="flMessageError permanentMessage" id="${path}ErrorMessageServer">${message}</p>
		    </c:forEach>
		    </c:if>		    
	    </component:generic>
    <%-- <p class="declaration"><spring:message code="signature.declaration"/></p> --%>

  <jsp:include page="/WEB-INF/views/signature/signature_card_list.jsp"/>
    

	    <button id="signatureTrigger" class="add-button" data-toggle="button" type="button">
	       <i class="add-icon"></i>
	       <spring:message code="signature.section.button"/>
		</button>

	
    <div id="tabSignature" class="addbox" style="display: none">
    	<div id="signatureSection"></div>
    	
	</div>
	


	 	
 	 <input type="hidden" id="maxSignatures"
       value="${configurationServiceDelegator.getValue('signature.add.maxNumber', 'general')}"> 
        <script type="text/javascript">
        	checkMaxSignatures();
        </script>

</section>
