<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--http://f5dmzprodf03.oami.europa.eu/jira/browse/DEVSPHARMO-430-->

<div class="goods-and-services">
	<header>
		<spring:message code="review.goodsServices.${flowModeId}"/>
		<a class="edit navigateBtn" data-val="Update_GoodsServices"><spring:message code="review.update"/></a>
	</header>
	<div>
		<c:if test="${not empty flowBean.goodsAndServices}">		
			<table>
				<tbody>
					 <c:forEach items="${flowBean.goodsAndServices}" var="good">
						 <tr>
							<td class="class-number">${good.classId}</td>
							<td>
								<c:forEach items="${good.termForms}" var="term" varStatus="status">
									<span>
										<spring:eval var="storenumber" expression="term.description"/>
										<c:choose>
											<c:when test="${fn:length(good.termForms)-1 > status.index}">
												<c:set var="terminator" value=";"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="terminator" value="."></c:set>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${storenumber.length()>60}">
												${storenumber.substring(0,60)}...${terminator}
											</c:when>
											<c:otherwise>
												${storenumber}${terminator}
											</c:otherwise>
										</c:choose>
									</span>
								</c:forEach>
							</td>
						 </tr>
					 </c:forEach>					
				</tbody>
			</table>		
		</c:if>
	</div>
</div>