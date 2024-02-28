<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<div id="productIndicationTable">
	<c:choose>
		<c:when test="${param.viewMode eq true}">
			<c:set var="viewMode" value="true"/>
		</c:when>	
		<c:otherwise>
			<c:set var="viewMode" value="false"/>
		</c:otherwise>
	</c:choose>
								
    <c:if test="${not empty flowBean.locarno}">
        <c:set var="validMessage" value="design.form.classes.locarno.terms.harm"/>
        <c:set var="validMessageDescription" value="design.form.classes.locarno.terms.harm.description"/>
        <c:set var="invalidMessage" value="design.form.classes.locarno.terms.notFound"/>
        <c:set var="invalidMessageDescription" value="design.form.classes.locarno.terms.notFound.description"/>
        <c:set var="wrenchIcon" value="icon-wrench"/>
        
        <table id="addedProductIndications" class="table table-striped data-table" style="margin-bottom: 5px">
            <thead>
                <tr>
                    <th><spring:message code="design.form.classes.locarno.table.locarnoClassification"/><a data-val="number"/></th>
                    <th><spring:message code="design.form.classes.locarno.table.indicationOfProduct"/><a data-val="text"/></th>
                    <th><spring:message code="design.form.classes.locarno.table.type"/><a data-val="text"/></th>
                    <%-- <th class="options"><spring:message code="design.form.classes.locarno.table.options"/></th> --%>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="locarnoClass" items="${flowBean.locarno}" varStatus="productIndicationIndex">
                    <tr id="locarnoClass_id_${locarnoClass.id}">
                        <jsp:include page="/WEB-INF/views/locarno/dsclass/locarno_Row.jsp">
                            <jsp:param value="${productIndicationIndex.count}" name="rowId"/>
                            <jsp:param value="${locarnoClass.id}" name="locarnoId"/>
                            <jsp:param value="${locarnoClass.locarnoClassFormatted}" name="locarnoClassFormatted"/>
                            <jsp:param value="${locarnoClass.locarnoIndication}" name="locarnoIndication"/>
                            <jsp:param value="${locarnoClass.validationCode}" name="locarnoClassValid"/>
                            <jsp:param value="${empty locarnoClass.suggestions ? '' : wrenchIcon}" name="wrenchIcon"/>
                            <jsp:param name="type" value="${locarnoClass.type.value}"/>
                            <jsp:param value="${viewMode}" name="viewMode"/>
                        </jsp:include>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:set var="sectionId" value="locarnoNewProduct" scope="request" />
        <div>
            <ul class="termsDefinition">
                <li class="termValidharm">
                    <strong><spring:message code="${validMessage}"/>: </strong>"<spring:message code="${validMessageDescription}"/>"
                    <component:fastTrackMark sectionId="designsDetails" fieldId="locarnoEnabled" helpMessageKey="fasttrack.help.locarno.terms"/></li>
                </li>
                <li class="termInvalid notVisibleInWizard" style="${hideLegend}">
                    <strong><spring:message code="design.form.classes.locarno.terms.invalid"/>: </strong>"<spring:message code="design.form.classes.locarno.terms.invalid.description"/>"
                </li>
                <li class="termEditable notVisibleInWizard" style="${hideLegend}">
                    <strong><spring:message code="design.form.classes.locarno.terms.editable"/>: </strong>"<spring:message code="design.form.classes.locarno.terms.editable.description"/>"
                </li>
                <li class="termNotFound notVisibleInWizard">
                    <strong><spring:message code="${invalidMessage}"/>: </strong>"<spring:message code="${invalidMessageDescription}"/>"
                </li>
            </ul>
        </div>
    </c:if>
</div>