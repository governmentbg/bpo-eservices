<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="sp-tags.tld" prefix="tags" %>

<jsp:directive.attribute name="path" required="true" type="java.lang.String" description="Path for the form:tag"/>
<jsp:directive.attribute name="id" required="false" type="java.lang.String" description="Id for the form:tag"/>
<jsp:directive.attribute name="checkRender" required="false" type="java.lang.Boolean"
                         description="Indicates whether the component has to be rendered"/>
<jsp:directive.attribute name="labelTextCode" required="false" type="java.lang.String"
                         description="Code for the spring:message"/>
<jsp:directive.attribute name="formTagClass" required="false" type="java.lang.String"
                         description="Class for the form:tag"/>
<jsp:directive.attribute name="divClass" required="false" type="java.lang.String"
                         description="Class for the wrapping div tag"/>

<c:if test="${empty checkRender}">
    <c:set var="checkRender" value="false"/>
</c:if>
<%-- Render the component if it has to be rendered (reads property field:visible in the CMS) --%>
<tags:render flowModeId="${flowModeId}" sectionId="${sectionId}" field="${path}" checkRender="${checkRender}">
    <div class="${divClass}">
        <fieldset>
            <div class="dependant">
                <component:checkbox path="${path}" id="${id}" checkRender="${checkRender}"
                                    formTagClass="${formTagClass}"
                                    labelTextCode="${labelTextCode}"/>
            </div>
        </fieldset>
    </div>
</tags:render>