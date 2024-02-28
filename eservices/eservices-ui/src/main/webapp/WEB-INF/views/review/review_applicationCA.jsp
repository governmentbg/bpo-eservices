<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div class="applicationCA">
    <header>
    	<spring:message code="correspondenceAddress.title.${flowModeId}"/>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_Person"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
    
    <c:if test="${not empty flowBean.correspondanceAddresses }">
   		<table>
			<thead>
				<tr>
                <th><spring:message code="review.person.aplicant.table.column.number.title"/></th>
                <th><spring:message code="person.table.header.name"/></th>
                <th><spring:message code="person.table.header.country"/></th>
            </tr>
			</thead>
			<tbody>	
			<c:set var="i" value="0"/>
	    	<c:forEach var="appCA" items="${flowBean.correspondanceAddresses }">	    		
	    		 <c:set var="i" value="${i+1}"/>
	    		 <td data-val='number'>${i}</td>
				 <td data-val='name'>${appCA.correspondenceAddressForm.correspondenceName}</td>
				 <td data-val='country'>${appCA.correspondenceAddressForm.addressForm.country}</td>	    		 
	    	</c:forEach>
	    	
	    	</tbody>
    	</table>
    </c:if>		
    
    
</div>