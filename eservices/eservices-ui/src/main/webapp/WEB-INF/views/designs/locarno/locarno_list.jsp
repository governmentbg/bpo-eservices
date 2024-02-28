<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<c:if test="${not empty flowBean.locarno}">

	<table id="tableAddedLocarnoClassifications" class="table table-striped">
		<tr>
    		<th><spring:message code="design.form.classes.table.header.class"/><a class="sorter" data-val="classification"></a></th>
        	<th><spring:message code="design.form.classes.table.header.product.indication"/><a class="sorter" data-val="indication"></a></th>
        	<c:if test="${empty param.viewMode or not param.viewMode}">
				<th><spring:message code="design.form.classes.table.header.options"/></th>
			</c:if>	
		</tr>
       
		<c:forEach var="locarnoClass" items="${flowBean.locarno}" varStatus="l">
    		<tr id="locarno_id_${locarnoClass.id}">
				<td data-val="classification">${locarnoClass.locarnoClassFormatted}</td>
				<td data-val="indication">
						${locarnoClass.locarnoIndication}
				</td>
				<c:if test="${empty param.viewMode or not param.viewMode}">
					<td data-val="options">
						<c:if test="${empty locarnoClass.locarnoId}">
							<a class="edit-icon" id="iconEditNewProductAdded${l.count}" data-val="${locarnoClass.id}" data-rownum="${l.count}"></a>
						</c:if>	
						<a class="remove-icon" id="iconRemoveLocarno${l.count}" data-val="${locarnoClass.id}"></a>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	
</c:if>
        
