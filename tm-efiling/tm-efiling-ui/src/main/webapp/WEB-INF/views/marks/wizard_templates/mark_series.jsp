<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<component:generic path="mainForm.seriesPresent" checkRender="true">
    <fieldset>
        <div class="dependant">
            <component:checkbox path="mainForm.seriesPresent" checkRender="true"
                                id="seriesPresent" labelTextCode="mark.series.present"
                                formTagClass="checkbox" />
            <c:set var="displayNoneSeriesPresent" value="display: none;" />
            <c:if test="${flowBean.mainForm.seriesPresent}">
                <c:set var="displayNoneSeriesPresent" value="" />
            </c:if>
            <div style="${displayNoneSeriesPresent}">
                <component:input path="mainForm.seriesNumber" checkRender="true"
                                 id="seriesCheck1" labelTextCode="mark.series.number"
                                 formTagClass="" />
            </div>
        </div>
    </fieldset>
</component:generic>