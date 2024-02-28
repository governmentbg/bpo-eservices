<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<section class="type" id="typemark">
    <a id="typemark" class="anchorLink"></a>
        <h3><spring:message code="mark.type"/></h3>
        <tiles:useAttribute id="sections_list" name="typeMark_content" classname="java.util.List"/>
        
        <input type="hidden" value="${flowBean.mainForm.markType}" id="markType"/>
        <ul class="types-of-marks" data-tabs="tabs"
		            rel="markTypesSection">
            <sec:authorize access="hasRole('Word_Mark')" var="security_word_mark" />
            <sec:authorize access="hasRole('Figurative_Mark')" var="security_figurative_mark" />
            <sec:authorize access="hasRole('ThreeD_Mark')" var="security_3d_mark" />
            <sec:authorize access="hasRole('Colour_Mark')" var="security_colour_mark" />
            <sec:authorize access="hasRole('Sound_Mark')" var="security_sound_mark" />
            
            <c:if test="${not empty sections_list}">
				<c:forEach var="section_item" items="${sections_list}">
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/wordmark.jsp'}">
			            <c:if test="${security_word_mark || !configurationServiceDelegator.securityEnabled}">
			                <li id="wordmark" class="markMenu ${flowBean.mainForm.markType=='wordmark'?'active':''}"><a class="word-mark">
			                    <i></i><span><spring:message code="mark.names.word"/></span>
			                </a></li>
			            </c:if>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/figurative.jsp'}">
			            <c:if test="${security_figurative_mark || !configurationServiceDelegator.securityEnabled}">
			                <li class="markMenu ${flowBean.mainForm.markType=='figurative'?'active':''}" id="figurative"><a  class="figurative-mark">
			                    <i></i><span><spring:message code="mark.names.figurative"/></span>
			                </a></li>
			            </c:if>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/figwordmark.jsp'}">
			            <c:if test="${security_figurative_mark || !configurationServiceDelegator.securityEnabled}">
			                <li class="markMenu ${flowBean.mainForm.markType=='figwordmark'?'active':''}" id="figwordmark"><a class="figurative-with-words-mark">
			                    <i></i><span><spring:message code="mark.names.figurativeContainingWords"/></span>
			                </a></li>
			            </c:if>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/3dmark.jsp'}">
			            <c:if test="${security_3d_mark || !configurationServiceDelegator.securityEnabled}">
			                <li class="markMenu ${flowBean.mainForm.markType=='3dmark'?'active':''}" id="3dmark"><a class="threed-mark">
			                   <i></i><span><spring:message code="mark.names.threeD"/></span>
			                </a></li>
			            </c:if>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/colourmark.jsp'}">
			            <c:if test="${security_colour_mark || !configurationServiceDelegator.securityEnabled}">
			                <li class="markMenu ${flowBean.mainForm.markType=='colourmark'?'active':''}" id="colourmark"><a class="colour-mark">
			                    <i></i><span><spring:message code="mark.names.colour"/></span>	
			                </a></li>
			            </c:if>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/soundmark.jsp'}">
			            <c:if test="${security_sound_mark || !configurationServiceDelegator.securityEnabled}">
			                <li class="markMenu ${flowBean.mainForm.markType=='soundmark'?'active':''}" id="soundmark"><a class="sound-mark">        
			                    <i></i><span><spring:message code="mark.names.sound"/></span>
			                </a></li>
			            </c:if>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/other.jsp'}">
			            <li class="markMenu ${flowBean.mainForm.markType=='other'?'active':''}" id="other"><a class="other-mark">
			            	<i></i><span><spring:message code="mark.names.other"/></span>
			           </a></li>
		            </c:if>
		            <c:if test="${section_item eq '/WEB-INF/views/marks/forms/3dwordmark.jsp'}">
			            <c:if test="${security_3d_mark || !configurationServiceDelegator.securityEnabled}">
			                <li class="markMenu ${flowBean.mainForm.markType=='3dwordmark'?'active':''}" id="3dwordmark"><a class="threed-with-words-mark">
			                    <i></i><span><spring:message code="mark.names.threeDword"/></span>
			               </a></li>
			            </c:if>
		            </c:if>
					<c:if test="${section_item eq '/WEB-INF/views/marks/forms/shapemark.jsp'}">
						<li class="markMenu ${flowBean.mainForm.markType=='shapemark'?'active':''}" id="shapemark"><a class="threed-with-words-mark">
							<i></i><span><spring:message code="mark.names.shape"/></span>
						</a></li>
					</c:if>
					<c:if test="${section_item eq '/WEB-INF/views/marks/forms/hologrammark.jsp'}">
						<li class="markMenu ${flowBean.mainForm.markType=='hologrammark'?'active':''}" id="hologrammark"><a class="hologram-mark">
							<i></i><span><spring:message code="mark.names.hologram"/></span>
						</a></li>
					</c:if>
					<c:if test="${section_item eq '/WEB-INF/views/marks/forms/positionmark.jsp'}">
						<li class="markMenu ${flowBean.mainForm.markType=='positionmark'?'active':''}" id="positionmark"><a class="position-mark">
							<i></i><span><spring:message code="mark.names.position"/></span>
						</a></li>
					</c:if>
					<c:if test="${section_item eq '/WEB-INF/views/marks/forms/patternmark.jsp'}">
						<li class="markMenu ${flowBean.mainForm.markType=='patternmark'?'active':''}" id="patternmark"><a class="pattern-mark">
							<i></i><span><spring:message code="mark.names.pattern"/></span>
						</a></li>
					</c:if>
					<c:if test="${section_item eq '/WEB-INF/views/marks/forms/motionmark.jsp'}">
						<li class="markMenu ${flowBean.mainForm.markType=='motionmark'?'active':''}" id="motionmark"><a class="motion-mark">
							<i></i><span><spring:message code="mark.names.motion"/></span>
						</a></li>
					</c:if>
					<c:if test="${section_item eq '/WEB-INF/views/marks/forms/multimediamark.jsp'}">
						<li class="markMenu ${flowBean.mainForm.markType=='multimediamark'?'active':''}" id="multimediamark"><a class="multimedia-mark">
							<i></i><span><spring:message code="mark.names.multimedia"/></span>
						</a></li>
					</c:if>
	            </c:forEach>
            </c:if>
        </ul>
   <div class="add-edit-mark" id="markType_content">
    	<c:if test="${flowBean.mainForm.markType!='0'}" >
			<c:set var="mode" value="forms" />
			<jsp:include page="marks/${mode}/${flowBean.mainForm.markType}.jsp" />
    	</c:if>
    </div>

</section>
