<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="service_similarTM_details"
       value="${configurationServiceDelegator.isServiceEnabled('SimilarTM_FindMoreDetails_Service')}"/>
<c:if test="${service_similarTM_details}">
    <span class="hidden similarTMDetailsUrlTemplate">
        <c:out value="${configurationServiceDelegator.getValueFromGeneralComponent('service.similarTm.details.url')}"
               escapeXml="false"/>
    </span>
</c:if>

<span id="similarMarksMoreInfoText" class="hidden"><spring:message code="similarMarks.table.moreInfo"/></span>
<span id="similarMarksPageSize" class="hidden">5</span>

<div id="similarTrademarks">

    <div id="similarTrademarksError" class="hidden">
        <p>
            <div class="alert alert-info">
                <spring:message code="similar.marks.over.450"/>
            </div>
        </p>
    </div>

    <ul class="results-header">
        <li class="results-count">
            <span id="numberOfSimilar" class="results-value"></span>
            <spring:message code="similarMarks.ofSimilarTrademarks"/>
        </li>
        <li class="results-leyend">

            <span class="results-leyend"><spring:message code="similarMarks.searchPoweredBy"/></span>
        </li>
    </ul>

    <div id="similarTrademarksTable">
        <table>

            <tr>
                <%--
                    The data-val attribute represents
                    the name of the json property to sort by (see similarmark_common.js, sort section)
                --%>
                <th><spring:message code="similarMarks.table.header.mark.number"/><a class="sorter" data-val="applicationNumber"></a>
                </th>
                <th><spring:message code="similarMarks.table.header.mark.description"/><a class="sorter" data-val="name"></a></th>
                <th><spring:message code="similarMarks.table.header.mark.type"/><a class="sorter" data-val="type"></a></th>
                <th><spring:message code="similarMarks.table.header.office"/><a class="sorter" data-val="office"></a></th>
                <th><spring:message code="similarMarks.table.header.ownerName"/><a class="sorter" data-val="ownerName"></a></th>
                <th><spring:message code="similarMarks.table.header.class"/></th>
                <c:if test="${service_similarTM_details}">
                    <th id="similarMarkUrlColumn"><spring:message code="similarMarks.table.header.details"/></th>
                </c:if>
            </tr>
        </table>
    </div>
    <div id="similarTrademarksPagination" class="pagination">
        <ul id=similarTrademarksPageContainer>
        </ul>
    </div>
    <div class="buttons">
        <a href="similarTMReport.htm?flowKey=${empty param.execution?param.flowKey:param.execution}"
           onclick="downloadFinalReceipt(this); return false;">
            <button type="button">
                <i class="icon-pdf"></i>
                <spring:message code="similarMarks.savepdf"/>
            </button>
        </a>

        <a href="similarTMReportXLSX.htm?flowKey=${empty param.execution?param.flowKey:param.execution}"
           onclick="downloadFinalReceipt(this); return false;">
            <button type="button">
                <i class="icon-xlsx"></i>
                <spring:message code="similarMarks.savexlsx"/>
            </button>
        </a>
    </div>
</div>