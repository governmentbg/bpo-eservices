<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 5.5.2022 г.
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="trade-mark-details">
    <header>
        <spring:message code="review.gi.details"/>
        <a class="edit navigateBtn" data-val="Update_Mark"><spring:message code="review.update"/></a>
    </header>
    <div>
        <div class="details-list">
            <c:choose>
                <c:when test="${flowBean.mainForm.markType=='geoindication'}">
                    <i class="word-mark"></i><span><spring:message code="mark.names.geoindication"/></span>
                </c:when>
                <c:when test="${flowBean.mainForm.markType=='oriname'}">
                    <i class="word-mark"></i><span><spring:message code="mark.names.oriname"/></span>
                </c:when>
            </c:choose>
            <dl>
                <c:if test="${not empty flowBean.mainForm.wordRepresentation}">
                    <dt><spring:message code="review.gi.wordElements"/></dt>
                    <dd>
                            <spring:eval var="storewordRepresentation" expression="flowBean.mainForm.wordRepresentation"/>
                        <c:choose>
                        <c:when test="${storewordRepresentation.length()>50}">
                            ${storewordRepresentation.substring(0,50)}...
                        </c:when>
                        <c:otherwise>
                            ${storewordRepresentation}
                        </c:otherwise>
                        </c:choose>
                    <dd>
                </c:if>
                <c:if test="${not empty flowBean.mainForm.markDescription}">
                    <dt><spring:message code="review.mark.description"/></dt>
                    <dd>
                            <spring:eval var="storewordRepresentationDescr" expression="flowBean.mainForm.markDescription"/>
                        <c:choose>
                        <c:when test="${storewordRepresentationDescr.length()>100}">
                            ${storewordRepresentationDescr.substring(0,100)}...
                        </c:when>
                        <c:otherwise>
                            ${storewordRepresentationDescr}
                        </c:otherwise>
                        </c:choose>
                    <dd>
                </c:if>
            </dl>
        </div>
    </div>
</div>