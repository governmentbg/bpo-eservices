<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty flowBean.addedPersonChanges}">
    <div class="request-change-details">
        <header>
            <spring:message code="review.changerequest.details"/>
            <c:if test="${!flowBean.readOnly}">
                <a class="edit navigateBtn" data-val="Update_ChangeRequest"><spring:message code="review.edit"/></a>
            </c:if>
        </header>
        <div class="changerequest">

            <table class="table" style="padding: 0;">

                <thead>
                    <tr style="width:100%">
                        <th><spring:message code="person.table.header.number"/></th>
                        <th><spring:message code="person.table.header.type"/></th>
                        <th><spring:message code="person.table.header.currrentName"/></th>
                        <th><spring:message code="person.table.header.updatedName"/></th>
                        <th><spring:message code="person.table.header.updatedAddress"/></th>
                    </tr>
                </thead>

                <c:forEach var="personChange" items="${flowBean.addedPersonChanges}" varStatus="personChangeStatus">
                    <c:choose>
                        <c:when test="${personChange.changeType eq 'CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS'}">
                            <c:if test="${not empty personChange.correspondenceAddresses}">
                                <c:set var="personChangeNewAddress" value="${personChange.correspondenceAddresses[0].addressForm.fullAddress}" />
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="personChangeNewAddress" value="${personChange.address.fullAddress}" />
                        </c:otherwise>
                    </c:choose>
                    <tr style="width:100%">
                        <td>${personChangeStatus.index+1}</td>
                        <td><spring:message code="personChange.changeType.${personChange.changeType}"/></td>
                        <td>${personChange.previousPersonName}</td>
                        <td>${personChange.name}</td>
                        <td>${personChangeNewAddress}</td>
                    </tr>
                </c:forEach>

            </table>

        </div>
    </div>
</c:if>