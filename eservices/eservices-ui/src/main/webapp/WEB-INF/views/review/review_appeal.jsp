<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
<div class="appeal-details">
	<header>
		<spring:message code="appeal.section.title"/>
		<c:if test="${!flowBean.readOnly}">
			<a class="edit navigateBtn" data-val="Update_Appeal"><spring:message code="review.edit"/></a>
		</c:if>
	</header>
    
    <c:if test="${not empty flowBean.appeals}">
    <div>
        <table id="addedAppeals" class="table">

            <tr>
                <th><spring:message code="appeal.table.header.number"/></th>
                <th><spring:message code="appeal.table.header.kind"/></th> 
                <th><spring:message code="appeal.table.header.decisionDate"/></th>
                <c:if test="${flowModeId == 'tm-appeal' }">
                	<th><spring:message code="appeal.table.header.decisionNumber"/></th>                             	
                	<th><spring:message code="appeal.table.header.opposition.filing.date"/></th>
                    <th><spring:message code="appeal.table.header.extent"/></th>
                    <th style="width: 3%"><spring:message
                            code="tm.details.table.header.GS" /></th>
                </c:if>
            </tr>
            <c:set var="i" value="${appealsSize}"/>
            <c:forEach var="appeal" items="${flowBean.appeals}" varStatus="appealId">
                <c:set var="i" value="${i+1}"/>
                <c:set var="appealDate">
                	<fmt:formatDate type="date" pattern="${dateFormat}" value="${appeal.decisionDate}"/>
                </c:set>
                <c:set var="oppoDate">
                	<fmt:formatDate type="date" pattern="${dateFormat}" value="${appeal.oppositionFilingDate}"/>
                </c:set>
                <tr id="appeal_id_${appeal.id}">                
                    <td data-val='number'>${i}</td>
					<td data-val='kind'><spring:message code="${flowModeId }.${appeal.appealKind.labelCode }"/></td>
					
					<td data-val="appeal_date">${empty appealDate ? "-" : appealDate}</td>
					<c:if test="${flowModeId == 'tm-appeal'}">
						<td data-val="appeal_num">${empty appeal.decisionNumber ? "-" : appeal.decisionNumber}</td>
						<td data-val="appeal_oppo_filing">${empty oppoDate ? "-" : oppoDate}</td>
                        <td data-val="gs"><spring:message code="appeal.field.extent.${appeal.gsHelper.extent }"/></td>
                        <td data-val="GS">
                            <div id="expandGshelperReview${i}" class="icon-arrow-down-gshelper" style="cursor:pointer;font-weight: normal; " data-val="${i}" data-rownum="${i}"></div>
                            <div id="collapseGshelperReview${i}" class="icon-arrow-up-gshelper" style="display:none; cursor:pointer;font-weight: normal;" data-val="${i}" data-rownum="${i}"></div>
                        </td>
					</c:if>
                </tr>
                <c:if test="${flowModeId == 'tm-appeal' }">
                    <tr id="tableremain_gshelper${i}"
                        style="display: none; background: #F9F9F9" valign="right">
                        <td></td>
                        <td colspan="6">
                            <table>
                                <thead>
                                <tr>
                                    <th style="width:5%"><spring:message code="gs.class"/></th>
                                    <th style="width:95%"><spring:message code="${flowModeId}.remain.gshelper.table.title"/></th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!(empty appeal.gsHelper.goodsAndServices)}">
                                    <c:forEach items="${appeal.gsHelper.goodsAndServices}"
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
                        <td colspan="6">
                            <table>
                                <thead>
                                <tr>
                                    <th style="width:5%"><spring:message code="gs.class"/></th>
                                    <th style="width:95%"><spring:message code="${flowModeId}.removed.table.title"/></th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="removedGS" value="${flowBean.getRemovedGS(appeal.gsHelper) }"/>
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
        </table>
    </div>
    </c:if>
</div>