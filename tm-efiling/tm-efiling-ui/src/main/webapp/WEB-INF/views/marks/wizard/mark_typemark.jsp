<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


<section class="type">
    <header>
        <a id="typemark" class="anchorLink"></a>

        <h2>
            <spring:message code="mark.type"/><span class="requiredTag">*</span>
            <input type="hidden" value="${flowBean.mainForm.markType}" id="markType"/>
        </h2>
    </header>

    <p>
        <spring:message code="mark.type.which"/>
    </p>
    <tiles:useAttribute id="sections_list" name="typeMark_content" classname="java.util.List"/>
    <ul id="wizardTypes" class="mark-types-wizard">
        <c:if test="${not empty sections_list}">
            <c:forEach var="section_item" items="${sections_list}">
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/wordmark.jsp'}">
                    <component:markItem listItemClass="word-mark" wrapperDivId="wordmark" moreInfoRef="#moreword"
                                        titleCode="mark.names.word" descriptionCode="mark.description.word"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/figurative.jsp'}">
                    <component:markItem listItemClass="figurative-mark" wrapperDivId="figurative" moreInfoRef="#morefigurative"
                                        titleCode="mark.names.figurative" descriptionCode="mark.description.figurative"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/figwordmark.jsp'}">
                    <component:markItem listItemClass="figurative-with-words-mark" wrapperDivId="figwordmark"
                                        titleCode="mark.names.figurativeContainingWords" moreInfoRef="#morefigword"
                                        descriptionCode="mark.description.figurativeContainingWords"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/3dmark.jsp'}">
                    <component:markItem listItemClass="threed-mark" wrapperDivId="3dmark"
                                        titleCode="mark.names.threeD" moreInfoRef="#more3d"
                                        descriptionCode="mark.description.threeD"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/soundmark.jsp'}">
                    <component:markItem listItemClass="sound-mark minorType" wrapperDivId="soundmark"
                                        titleCode="mark.names.sound" moreInfoRef="#moresound"
                                        descriptionCode="mark.description.sound"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/colourmark.jsp'}">
                    <component:markItem listItemClass="colour-mark minorType" wrapperDivId="colourmark"
                                        titleCode="mark.names.colour" moreInfoRef="#morecolour"
                                        descriptionCode="mark.description.colour" />
                </c:if>
            </c:forEach>
        </c:if>
        <button class="moreMarks see-more-button" type="button">
            <spring:message code="mark.type.protectAnother"/>
        </button>
        <c:if test="${not empty sections_list}">
            <c:forEach var="section_item" items="${sections_list}">

                <c:if test="${section_item eq '/WEB-INF/views/marks/forms/other.jsp'}">
                    <component:markItem listItemClass="other-mark minorType" wrapperDivId="other"
                                        titleCode="mark.names.other" moreInfoRef="#moreothers"
                                        descriptionCode="mark.description.other" hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/3dwordmark.jsp'}">
                    <component:markItem listItemClass="threed-with-words-mark minorType" wrapperDivId="3dwordmark"
                                        titleCode="mark.names.threeDword" moreInfoRef="#more3dword"
                                        descriptionCode="mark.description.threeDword"
                                        hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/shapemark.jsp'}">
                    <component:markItem listItemClass="shape-mark minorType" wrapperDivId="shapemark"
                                        titleCode="mark.names.shape" moreInfoRef="#moreShape"
                                        descriptionCode="mark.description.shape"
                                        hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/hologrammark.jsp'}">
                    <component:markItem listItemClass="hologram-mark minorType" wrapperDivId="hologrammark"
                                        titleCode="mark.names.hologram" moreInfoRef="#moreHologram"
                                        descriptionCode="mark.description.hologram"
                                        hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/positionmark.jsp'}">
                    <component:markItem listItemClass="position-mark minorType" wrapperDivId="positionmark"
                                        titleCode="mark.names.position" moreInfoRef="#morePosition"
                                        descriptionCode="mark.description.position"
                                        hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/patternmark.jsp'}">
                    <component:markItem listItemClass="pattern-mark minorType" wrapperDivId="patternmark"
                                        titleCode="mark.names.pattern" moreInfoRef="#morePattern"
                                        descriptionCode="mark.description.pattern"
                                        hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/motionmark.jsp'}">
                    <component:markItem listItemClass="motion-mark minorType" wrapperDivId="motionmark"
                                        titleCode="mark.names.motion" moreInfoRef="#moreMotion"
                                        descriptionCode="mark.description.motion"
                                        hidden="true"/>
                </c:if>
                <c:if test="${section_item eq '/WEB-INF/views/marks/wizard/multimediamark.jsp'}">
                    <component:markItem listItemClass="multimedia-mark minorType" wrapperDivId="multimediamark"
                                        titleCode="mark.names.multimedia" moreInfoRef="#moreMultimedia"
                                        descriptionCode="mark.description.multimedia"
                                        hidden="true"/>
                </c:if>
            </c:forEach>
        </c:if>
    </ul>
    <jsp:include page="additional_info.jsp"/>
    <div id="markType_content" class="add-edit-mark">
        <c:if test="${flowBean.mainForm.markType!='0'}">
            <c:set var="mode" value="wizard"/>
            <jsp:include page="${flowBean.mainForm.markType}.jsp"/>
        </c:if>
    </div>
</section>
<div class="wizard-steps-analytics" style="display:none;" data-ignore-parent="true">
    /trademark/register-trademark/select_mark_type
</div>
