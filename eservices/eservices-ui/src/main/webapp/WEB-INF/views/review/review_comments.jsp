<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="comments">
    <header>
    	<spring:message code="review.comments.title"/>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_Comments"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
    <c:if test="${not empty flowBean.comment}">		
			<table>
				<tbody>					
					 <tr>						
						<td>${flowBean.comment}</td>
					</tr>
				</tbody>
			</table>		
		</c:if>
</div>