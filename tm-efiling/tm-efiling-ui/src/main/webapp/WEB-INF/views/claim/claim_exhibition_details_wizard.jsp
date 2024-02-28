<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript">
    <!--
    $('.cancelExhibitionButton').live("click", function ()
    {
        cancelExhibition();
    });

    function cancelExhibition()
    {
        $('#tabExhibition').hide();
        $('.exhpriority').removeClass('active');
        $('#openExhibition').parent().removeClass('active');
        markClaimNoButtonActive(".exhpriority", "#noExhibition");
    }
    //-->
</script>

<div class="addbox claimFields">
    <form:form modelAttribute="exhPriorityForm" id="exhibitionForm" cssClass="fileUploadForm formEF">
        <c:set var="sectionId" value="exhibition" scope="request"/>

        <header>
            <h3><spring:message code="claim.title.exhibition"/></h3>
            <ul class="controls">
                <li>
                    <a class="cancelExhibitionButton">
                        <spring:message code="claim.action.cancel"/>
                    </a>
                </li>

                <li>
                    <button type="button" class="addExhibitionWizard">
                        <c:choose>
                            <c:when test="${empty exhPriorityForm.id}">
                                <spring:message code="claim.action.add"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="claim.action.save"/>
                            </c:otherwise>
                        </c:choose>
                    </button>
                </li>
            </ul>
        </header>


        <form:hidden path="id"/>

        <component:input labelTextCode="claim.exhibition.field.name"
                         path="exhibitionName"
                         checkRender="true"/>

        <component:input path="firstDate" checkRender="true" id="fDisc"
                        labelTextCode="claim.exhibition.field.firstDisclsure" formTagClass="filing-date-input"/>


        <div id="fileDocumentAttachment" class="fileuploadInfo collectiveSelectors">
            <component:file labelCode="claim.exhibition.document" pathFilename="fileDocumentAttachment" fileWrapperPath="fileWrapper"
                           fileWrapper="${exhPriorityForm.fileWrapper}"/>
        </div>

        <ul class="controls">
            <li>
                <a class="cancelExhibitionButton">
                    <spring:message code="claim.action.cancel"/></a>
            </li>
            <li>
                <button type="button" class="addExhibitionWizard">
                    <c:choose>
                        <c:when test="${empty exhPriorityForm.id}">
                            <spring:message code="claim.action.add"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="claim.action.save"/>
                        </c:otherwise>
                    </c:choose>
                </button>
            </li>

        </ul>
        <br>
    </form:form>
    <%--<div class="row">--%>
        <%--<p class="flBox data-url" data-rel="tab1_exhibition">--%>
            <%--<a class="addCtn addAnotherExhibitionWizard"><span>--%>
	            <%--<spring:message code="claim.exhibition.action.addAnother"/>--%>
	        <%--</span></a>--%>
        <%--</p>--%>
    <%--</div>--%>
</div>