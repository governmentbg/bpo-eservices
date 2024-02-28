<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 4.1.2019 г.
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="your-details">
    <header>
        <spring:message code="review.patentDetails.${flowModeId}"/>
        <a class="edit navigateBtn" data-val="Update_Patent"><spring:message code="review.update"/></a>
    </header>
    <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
    <div>
        <table>
            <tbody>
                <c:if test="${not empty flowBean.patent.applicationNumber}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.applicationNumber"/></label>
                        </td>
                        <td width="75%">
                            <c:out value="${flowBean.patent.applicationNumber}"/>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.applicationDate}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.applicationDate"/></label>
                        </td>
                        <td width="75%">
                            <fmt:formatDate value="${flowBean.patent.applicationDate}" var="ptAppDate" pattern="dd.MM.yyyy"/>
                            <c:out value="${ptAppDate}"/>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${not empty flowBean.patent.svKind}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="svKind.option"/></label>
                        </td>
                        <td width="75%">
                            <p><spring:message code="${flowBean.patent.svKind.code}"/></p>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${not empty flowBean.patent.regKind}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="regKind.option"/></label>
                        </td>
                        <td width="75%">
                            <p><spring:message code="${flowBean.patent.regKind.code}"/></p>
                        </td>
                    </tr>
                </c:if>

                <tr>
                    <td width="15%">
                        <label><spring:message code="patent.form.field.patentTitle.${flowModeId}"/></label>
                    </td>
                    <td width="75%">
                        <p><c:out value="${flowBean.patent.patentTitle}"/></p>
                        <p>---------------------------------------------------------</p>
                        <p><c:out value="${flowBean.patent.patentTitleSecondLang}"/></p>
                    </td>
                </tr>

                <c:if test="${not empty flowBean.patent.titleTransliteration}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.titleTransliteration"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.patent.titleTransliteration}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.latinClassification}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.latinClassification"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.patent.latinClassification}"/></p>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${not empty flowBean.patent.patentAbstract || not empty flowBean.patent.patentAbstractSecondLang}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.patentAbstract"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.patent.patentAbstract}"/></p>
                            <p>---------------------------------------------------------</p>
                            <p><c:out value="${flowBean.patent.patentAbstractSecondLang}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.firstPermissionBGNumber || not empty flowBean.patent.firstPermissionBGDate}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.firstPermissionBGLabel"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.patent.firstPermissionBGNumber}"/> / <fmt:formatDate type="date" pattern="${dateFormat}" value="${flowBean.patent.firstPermissionBGDate}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.firstPermissionEUNumber || not empty flowBean.patent.firstPermissionEUDate}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.firstPermissionEULabel"/></label>
                        </td>
                        <td width="75%">
                            <p><c:out value="${flowBean.patent.firstPermissionEUNumber}"/> / <fmt:formatDate type="date" pattern="${dateFormat}" value="${flowBean.patent.firstPermissionEUDate}"/></p>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.patentViews}">
                    <tr>

                        <td width="15%">
                            <label><spring:message code="patent.form.field.patentViews"/></label>
                        </td>
                        <td width="75%">
                            <div>
                                <table>
                                    <thead>
                                    <tr>
                                        <th><spring:message code="patentView.table.number" /></th>
                                        <th><spring:message code="patentView.table.view" /></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="patentViewForm" items="${flowBean.patent.patentViews}" varStatus="statusPatentViews">
                                        <tr>
                                            <td width="10%">${statusPatentViews.count}</td>
                                            <td width="50%">
                                                <c:set var="storedFile" value="${patentViewForm.view.storedFiles.size() > 0 ? patentViewForm.view.storedFiles[0] : null}" />
                                                <div class="view-image">
                                                    <c:if test="${storedFile ne null}">
                                                        <c:choose>
                                                            <c:when test="${storedFile.thumbnail ne null}">
                                                                <c:if test="${storedFile.thumbnail.documentId ne null && not empty storedFile.thumbnail.documentId}">
                                                                    <img class="thumb" src="getDocument.htm?documentId=${storedFile.thumbnail.documentId}" id="viewImage${statusPatentViews.count}" data-file-documentId="${storedFile.documentId}" data-file-name="${storedFile.originalFilename}">
                                                                </c:if>
                                                                <c:if test="${storedFile.thumbnail.documentId eq null || empty storedFile.thumbnail.documentId}">
                                                                    <img class="thumb" src="resources/img/thumbnailNotAvailable.png" id="viewImage${statusPatentViews.count}" data-file-documentId="${storedFile.documentId}" data-file-name="${storedFile.originalFilename}">
                                                                </c:if>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img class="thumb" src="getDocument.htm?documentId=${storedFile.documentId}" id="viewImage${statusPatentViews.count}" data-file-documentId="${storedFile.documentId}" data-file-name="${storedFile.originalFilename}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </td>

                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.patentDescriptions and not empty flowBean.patent.patentDescriptions.storedFiles}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.patentDescriptions.${flowModeId}"/></label>
                        </td>
                        <td width="75%">

                            <c:forEach var="stored" items="${flowBean.patent.patentDescriptions.storedFiles}">
                                <div>
                                    <c:out value="${stored.originalFilename}"/>
                                </div>
                            </c:forEach>

                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.patentClaims and not empty flowBean.patent.patentClaims.storedFiles}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.patentClaims.${flowModeId}"/></label>
                        </td>
                        <td width="75%">

                            <c:forEach var="stored" items="${flowBean.patent.patentClaims.storedFiles}">
                                <div>
                                    <c:out value="${stored.originalFilename}"/>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.patentDrawings and not empty flowBean.patent.patentDrawings.storedFiles}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.patentDrawings.${flowModeId}"/></label>
                        </td>
                        <td width="75%">
                            <c:forEach var="stored" items="${flowBean.patent.patentDrawings.storedFiles}">
                                <div>
                                    <c:out value="${stored.originalFilename}"/>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.sequencesListing and not empty flowBean.patent.sequencesListing.storedFiles}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.sequencesListing.${flowModeId}"/></label>
                        </td>
                        <td width="75%">
                            <c:forEach var="stored" items="${flowBean.patent.sequencesListing.storedFiles}">
                                <div>
                                    <c:out value="${stored.originalFilename}"/>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.pagesCount}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.pagesCount.${flowModeId}"/></label>
                        </td>
                        <td width="75%">
                            <c:out value="${flowBean.patent.pagesCount}"/>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.claimsCount}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.claimsCount.${flowModeId}"/></label>
                        </td>
                        <td width="75%">
                            <c:out value="${flowBean.patent.claimsCount}"/>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.drawingsCount}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.drawingsCount"/></label>
                        </td>
                        <td width="75%">
                            <c:out value="${flowBean.patent.drawingsCount}"/>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty flowBean.patent.drawingNumber}">
                    <tr>
                        <td width="15%">
                            <label><spring:message code="patent.form.field.drawingNumber"/></label>
                        </td>
                        <td width="75%">
                            <c:out value="${flowBean.patent.drawingNumber}"/>
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

</div>