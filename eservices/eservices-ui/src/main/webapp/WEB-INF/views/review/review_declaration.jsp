<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="comments">
    <header>
    	<spring:message code="review.declaration.title"/>
    	<c:if test="${!flowBean.readOnly}">
    		<a class="edit navigateBtn" data-val="Update_Declaration"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
    <c:if test="${flowBean.mainForm.smallCompany || flowBean.mainForm.licenceAvailability}">
			<table>
				<tbody>
				<c:if test="${flowBean.mainForm.smallCompany}">
					 <tr>						
						<td><spring:message code="review.small.company"/></td>
					</tr>
				</c:if>
				<c:if test="${flowBean.mainForm.licenceAvailability}">
					<tr>
						<td><spring:message code="review.licence.availability"/></td>
					</tr>
				</c:if>
				</tbody>
			</table>		
		</c:if>
</div>