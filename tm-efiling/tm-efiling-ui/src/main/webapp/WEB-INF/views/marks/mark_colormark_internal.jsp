<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="format-inputs">
    <li style="display: none">
        <label>
            <spring:message code="mark.color.formatText"/>
        </label>
        <select id="format">
            <option id="DESCRIPTION" selected="selected"><spring:message code="mark.color.description"/></option>
            <!-- Removed by DEVIMPFO-710
                <option id="PANTONE">PANTONE</option>
                <option id="HEX">HEX</option>
                <option id="RAL">RAL</option>
                <option id="RGB">RGB</option>
            -->
        </select>
    </li>
    <li>
        <label><spring:message code="mark.color.value"/>
            <em class="requiredTag">(*)</em>
        </label>
        <input type="text" id="value" name="value"
            class="autocompleted ui-autocomplete-input"
            data-minchars="1"
            data-url="autocompleteColor.htm?flowKey=${empty param.execution ? param.flowKey : param.execution}"/>
        <c:if test="${not empty colourForm}">
            <form:errors cssClass="flMessageError" path="colourForm.value"/>
        </c:if>
    </li>
    <li>
        <button type="button" id="saveColourButton" class="saveColourButton">
            <i class="add-format-icon"></i>
            <spring:message code="mark.color.add"/>
        </button>
    </li>
</ul>

<c:if test="${emptyColour}">
    <p class="flMessageError"><spring:message code="mark.color.emptyColor"/></p>
</c:if>
<c:if test="${colorNotValid}">
    <p class="flMessageError"><spring:message code="mark.color.colorNotValid"/></p>
</c:if>

<%--<div id="colourMark">--%>
<%--<div>--%>
<%--<div>--%>
<%--<input id="colour" name="colour" labelTextCode="mark.color.describe" class="autocompleted ui-autocomplete-input"--%>
<%--data-minchars="1" data-url="autocompleteColor.htm"/>--%>
<%--<c:if test="${not empty colourForm}">--%>
<%--<form:errors cssClass="flMessageError" path="colourForm.colour"/>--%>
<%--</c:if>--%>
<%--<em class="requiredTag">(*)</em>--%>
<%--</div>--%>
<%--<div>--%>
<%--<input id="description" name="description" labelTextCode="mark.color.describe"/>--%>
<%--<c:if test="${not empty colourForm}">--%>
<%--<form:errors cssClass="flMessageError" path="colourForm.description"/>--%>
<%--</c:if>--%>
<%--<em class="requiredTag">(*)</em>--%>
<%--</div>--%>
<%--<div>--%>
<%--<select id="format" labelTextCode="mark.color.format" formTagClass="span3">--%>
<%--<option value="Pantone">Pantone</option>--%>
<%--<option value="HEX">HEX</option>--%>
<%--<option value="RAL">RAL</option>--%>
<%--<option value="RGB">RGB</option>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>

<%--<input type="button" id="addColourButton" class="addColourButton" value="<spring:message code="mark.color.add"/>"/>--%>
<%--<input type="button" id="saveColourButton" class="saveColourButton" value="<spring:message code="mark.color.save"/>"--%>
<%--style="display:none"/>--%>
<%--<input type="button" id="cancelColourButton" class="cancelColourButton" value="<spring:message code="mark.color.cancel"/>"/>--%>
<%--</div>--%>

<div style="display:none" class="editvalue"></div>
<div style="display:none" class="editformat"></div>

<div id="colourCardList">
    <c:if test="${not empty flowBean.mainForm.colourList}">
        <table class="format-table">
            <tr>
                <%--<th><spring:message code="mark.color.formatText"/></th>--%>
                <th><spring:message code="mark.color.value"/></th>
                <th><spring:message code="mark.color.options"/></th>
            </tr>
            <c:forEach items="${flowBean.mainForm.colourList}" var="colour">
                <tr>
                    <%--<td class="format"><c:choose><c:when test="${colour.format == 'DESCRIPTION'}"><spring:message code="mark.color.description"/></c:when><c:otherwise>${colour.format}</c:otherwise></c:choose></td>--%>
                    <td class="value">${colour.value}</td>
                    <td class="options">
                        <%--<a id="editColourButton" class="editColourButton edit-format-icon" data-id="${colour.format}"></a>--%>
                        <a id="removeColourButton" class="removeColourButton remove-format-icon" data-id="${colour.format}"></a>
                    </td>
                        <%--<td><input type="button" id="removeColourButton" class="removeColourButton" value="X"/></td>--%>
                        <%--<td><input type="button" id="editColourButton" class="editColourButton" value="e"/></td>--%>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
