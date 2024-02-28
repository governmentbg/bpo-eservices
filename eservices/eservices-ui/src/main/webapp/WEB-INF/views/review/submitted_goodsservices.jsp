<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:if test="${not empty submittedForm.flowBean.tmsList[param.indexTM]}">
	<c:set var="printTable" value="false"/>
	<c:choose>
		<c:when test="${param.gsType eq 'remain'}">
			<c:set var="printTable" value="true"/>
		</c:when>
		<c:when test="${param.gsType eq 'removed'}">
			<c:forEach items="${submittedForm.flowBean.tmsList[param.indexTM].originalGS}" var="goodPT">
				<c:if test="${!(submittedForm.flowBean.isExistsNiceClass(goodPT.classId,submittedForm.flowBean.tmsList[param.indexTM].goodsAndServices))}">
					<c:set var="printTable" value="true"/>
				</c:if>
				<c:if test="${printTable eq 'false'}">
					<c:if test="${submittedForm.flowBean.isContainsRemovedTerm(goodPT.classId,submittedForm.flowBean.tmsList[param.indexTM].originalGS,submittedForm.flowBean.tmsList[param.indexTM].goodsAndServices)}">
						<c:set var="printTable" value="true"/>
					</c:if>						
				</c:if>
			</c:forEach>	
		</c:when>
	</c:choose>	
	<c:if test="${printTable eq 'true'}">		
	<table>
		<thead>
	        <tr>
	            <th style="width:5%"><spring:message code="gs.class"/></th>
	            <th style="width:95%"><spring:message code="${submittedForm.flowModeId}.${param.gsType}.table.title"/></th>
	            
	        </tr>
	    </thead>
		<tbody>
			<c:if test="${param.gsType eq 'remain'}">
				<c:forEach items="${submittedForm.flowBean.tmsList[param.indexTM].goodsAndServices}" var="good">
					<tr>
						<td class="class-review" style="padding-bottom:0px; padding-top: 0px">${good.classId}</td>
						<td class="class-review" style="padding-bottom:0px; padding-top: 0px">
							<ul class="country-list notRemovableClass">
								<c:forEach items="${good.termForms}" var="term">
									<li class="term-review">
										<span class="name">${term.description}</span>
									</li>
								</c:forEach>	
							</ul>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${param.gsType eq 'removed'}">
				<c:forEach items="${submittedForm.flowBean.tmsList[param.indexTM].originalGS}" var="good">
				 	<c:set var="classRow" value="class-review"/>
				 	<c:set var="classTerm" value="term-review-removed"/>
				 	<c:choose>
				 		<c:when test="${submittedForm.flowBean.isExistsNiceClass(good.classId,submittedForm.flowBean.tmsList[param.indexTM].goodsAndServices)}">
				 			<c:if test="${submittedForm.flowBean.isContainsRemovedTerm(good.classId,submittedForm.flowBean.tmsList[param.indexTM].originalGS,submittedForm.flowBean.tmsList[param.indexTM].goodsAndServices)}">
				 				<tr>
				 					<td class="${classRow}" style="padding-bottom:0px; padding-top: 0px">${good.classId}</td>
				 					<td class="${classRow}" style="padding-bottom:0px; padding-top: 0px">
				 						
				 						<ul class="country-list notRemovableClass">
				 						<c:forEach items="${good.termForms}" var="term">
				 							<c:if test="${!(submittedForm.flowBean.isExistsTerm(good.getClassId(),term.getDescription(),submittedForm.flowBean.tmsList[param.indexTM].goodsAndServices))}">
				 								<li class="${classTerm}">
													<span class="name">${term.description}</span>
												</li>	
				 							</c:if>	
				 						</c:forEach>
				 						</ul>
				 					</td>		
				 				</tr>	
				 			</c:if>
				 		</c:when>
				 		<c:otherwise>
				 			<tr>
				 				<td class="${classRow}" style="padding-bottom:0px; padding-top: 0px">${good.classId}</td>
								<td class="${classRow}" style="padding-bottom:0px; padding-top: 0px">
									<ul class="country-list notRemovableClass">
										<c:forEach items="${good.termForms}" var="term">
											<li class="${classTerm}">
												<span class="name">${term.description}</span>
											</li>
										</c:forEach>
									</ul>
								</td>		
				 			</tr>	
				 		</c:otherwise>
				 	</c:choose>
			 </c:forEach>				
			</c:if> 	
		</tbody>
	</table>	
	</c:if>	
</c:if>

