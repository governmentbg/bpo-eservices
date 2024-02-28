<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/component/sp-tags.tld" prefix="tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div class="trade-mark-details">
    <header>
    	<spring:message code="gshelper.section.title.${flowModeId}"></spring:message>
    	<c:if test="${!flowBean.readOnly}">
    	<a class="edit navigateBtn" data-val="Update_Mark"><spring:message code="review.edit"/></a>
    	</c:if>
    </header>
	<div class="gshelper">

		<c:if test="${not empty flowBean.gsHelpers}">
			<table id="addedGshelpers" class="table">

				<tr>
					<th><spring:message code="gshelper.table.header.number" /></th>
					<th><spring:message
							code="gshelper.table.header.tmApplicationNumber" /></th>
					<th><spring:message code="gshelper.table.header.extent" /></th>
					<th style="width: 3%"><spring:message
							code="tm.details.table.header.GS" /></th>
				</tr>
				<c:set var="i" value="${gshelpersSize}" />
				<c:forEach var="gshelper" items="${flowBean.gsHelpers}"
						   varStatus="gshelperId">
					<c:set var="i" value="${i+1}" />
					<tr id="gshelper_id_${gshelper.id}">
						<jsp:include
								page="/WEB-INF/views/review/review_gshelper_row.jsp">
							<jsp:param name="rowId" value="${i}" />
							<jsp:param name="gshelperId" value="${gshelper.id}" />
							<jsp:param name="tmApplicationNumber"
									   value="${gshelper.tmApplicationNumber}" />
							<jsp:param name="extent" value="${gshelper.extent}" />
						</jsp:include>
					</tr>
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
								<c:if test="${!(empty gshelper.goodsAndServices)}">
									<c:forEach items="${gshelper.goodsAndServices}"
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
								<c:set var="removedGS" value="${flowBean.getRemovedGS(gshelper) }"/>
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
				</c:forEach>
			</table>
		</c:if>
	</div>
</div>