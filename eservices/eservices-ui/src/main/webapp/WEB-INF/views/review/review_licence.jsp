<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
<div class="licence-details">
	<header>
		<spring:message code="review.licence"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Licence"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
	<div class="licence">
		<c:set var="licenceSize" value="0"/>
    	<c:if test="${not empty flowBean.licences}">
    		<table id="licenceList">
    			<tr>
	                <th><spring:message code="licence.table.header.number"/></th>
	                <th><spring:message code="licence.table.header.kind"/></th>                
	                <th><spring:message code="licence.table.header.territory"/></th>
	                <th><spring:message code="licence.table.header.endDate"/></th>
	                <c:if test="${flowModeId == 'tm-licence' }">
		                <th><spring:message code="licence.table.header.extent"/></th>
		                <th style="width: 3%"><spring:message
								code="tm.details.table.header.GS" /></th>
					</c:if>
	            </tr>
    		
    			<c:set var="i" value="0"/>
    			<c:forEach var="licence" items="${flowBean.licences}">
    				<c:set var="i" value="${i+1}"/>
    				<c:set var="endDate">
                		<fmt:formatDate type="date" pattern="${dateFormat}" value="${licence.periodLimitationEndDate}"/>
               		 </c:set>
    				<tr id="licence_id_${licence.id}">                
	                    <jsp:include page="/WEB-INF/views/review/review_licence_row.jsp">
	                    	<jsp:param name="rowId" value="${i}" />
	                    	<jsp:param name="licenceId" value="${licence.id}"/> 
	                    	<jsp:param name="sublicence" value="${licence.subLicenceIndicator}" />
	                    	<jsp:param name="kind" value="${licence.licenceKind != null ? licence.licenceKind.toString() : 'Unknown'}"/> 
	                        <jsp:param name="territory" value="${licence.territoryLimitationIndicator}" />
	                        <jsp:param name="endDate" value="${endDate}" />
	                        <jsp:param name="extent" value="${licence.gsHelper.extent}" />
	                        <jsp:param name="upToExpiration" value="${licence.periodLimitationIndicator}" />
	                    </jsp:include>
                	</tr>
                	<c:if test="${flowModeId == 'tm-licence' }">
                	<tr id="tableremain_gshelper${i}"
						style="display: none; background: #F9F9F9" valign="right">
						<td></td>
						<td colspan="4">
						<table>
							<thead>
						        <tr>
						            <th style="width:5%"><spring:message code="gs.class"/></th>
						            <th style="width:95%"><spring:message code="${flowModeId}.remain.gshelper.table.title"/></th>
						            
						        </tr>
						    </thead>
							<tbody>
							<c:if test="${!(empty licence.gsHelper.goodsAndServices)}">
								<c:forEach items="${licence.gsHelper.goodsAndServices}"
									var="good">
									<c:set var="classRow" value="class-review" />
									<c:set var="classTerm" value="term-review-remain" />

									<tr>
										<td class="class-review"
											style="padding-bottom: 0px; padding-top: 0px">${good.classId}</td>
										<td class="class-review"
											style="padding-bottom: 0px; padding-top: 0px">

											<ul class="country-list notRemovableClass">
												<c:forEach items="${good.termForms}" var="term">
													<li class="term-review"><span class="name">${term.description}</span>
													</li>
												</c:forEach>
											</ul>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							</tbody>
							</table>
						</td>
					</tr>
					<tr id="tablegshelper${i}"
						style="display: none; background: #F9F9F9" valign="right">
						<td></td>
						<td colspan="4">
						<table>
							<thead>
						        <tr>
						            <th style="width:5%"><spring:message code="gs.class"/></th>
						            <th style="width:95%"><spring:message code="${flowModeId}.removed.table.title"/></th>
						            
						        </tr>
						    </thead>
							<tbody>
							<c:set var="removedGS" value="${flowBean.getRemovedGS(licence.gsHelper) }"/>
							<c:if test="${!(empty removedGS)}">
								<c:forEach items="${removedGS}"
									var="good">
									<c:set var="classRow" value="class-review" />
									<c:set var="classTerm" value="term-review-removed" />

									<tr>
										<td class="${classRow}"
											style="padding-bottom: 0px; padding-top: 0px">${good.classId}</td>
										<td class="${classRow}"
											style="padding-bottom: 0px; padding-top: 0px">

											<ul class="country-list notRemovableClass">
												<c:forEach items="${good.termForms}" var="term">
													<li class="${classTerm}"><span class="name">${term.description}</span>
													</li>
												</c:forEach>
											</ul>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							</tbody>
							</table>
						</td>
					</tr>
					</c:if>
    			</c:forEach>
    			<c:set var="licenceSize" value="${i}"/>
    		</table>    		
    	</c:if>
	</div>
</div>