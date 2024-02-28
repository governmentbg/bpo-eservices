<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<section class="representative" id="personChange">
    <header>
        <a id="personChange" class="anchorLink">
        </a>

        <h2><spring:message code="personChange.section.title"></spring:message></h2>
    </header>

    <jsp:include page="/WEB-INF/views/personChange/personChange_card_list.jsp"/>
    
    <sec:authorize access="hasRole('PersonChange_Add')" var="security_personChange_add" />
    
    <c:if test="${security_personChange_add || !configurationServiceDelegator.securityEnabled}">

        <button type="button" id="personChangeTrigger" class="add-button" data-toggle="button">
            <i class="add-icon"></i>
            <spring:message code="person.button.personChange"/>
        </button>

        <div id="tabPersonChange" class="addbox" style="display:none">

            <header>
                <span id="personChangeCurrentNumber" class="number"></span>

                <h3><spring:message code="personChange.changeKind.requestChangeForm"/></h3>
            </header>
            <div id="requestNewChangeOptionsLayer">
                <div style="float:left; min-width: 300px;">
                    <b><spring:message code="personChange.changeKind.title.representative"/></b><br>
                    <input type="radio" name="changeKind" id="ADD_NEW_REPRESENTATIVE" value="ADD_NEW_REPRESENTATIVE">
                    <spring:message code="personChange.changeType.ADD_NEW_REPRESENTATIVE"/><br>
                    <input type="radio" name="changeKind" id="REPLACE_REPRESENTATIVE" value="REPLACE_REPRESENTATIVE">
                    <spring:message code="personChange.changeType.REPLACE_REPRESENTATIVE"/><br>
                    <input type="radio" name="changeKind" id="REMOVE_REPRESENTATIVE" value="REMOVE_REPRESENTATIVE">
                    <spring:message code="personChange.changeType.REMOVE_REPRESENTATIVE"/><br>
                    <input type="radio" name="changeKind" id="CHANGE_REPRESENTATIVE_ADDRESS" value="CHANGE_REPRESENTATIVE_ADDRESS">
                    <spring:message code="personChange.changeType.CHANGE_REPRESENTATIVE_ADDRESS"/><br>
                    <input type="radio" name="changeKind" id="CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS" value="CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS">
                    <spring:message code="personChange.changeType.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS"/><br>
                    <br>
                </div>
                <div>
                    <b><spring:message code="personChange.changeKind.title.correspondent"/></b><br>
                    <input type="radio" name="changeKind" id="ADD_NEW_CORRESPONDENT" value="ADD_NEW_CORRESPONDENT">
                    <spring:message code="personChange.changeType.ADD_NEW_CORRESPONDENT"/><br>
                    <input type="radio" name="changeKind" id="REPLACE_CORRESPONDENT" value="REPLACE_CORRESPONDENT">
                    <spring:message code="personChange.changeType.REPLACE_CORRESPONDENT"/><br>
                    <input type="radio" name="changeKind" id="REMOVE_CORRESPONDENT" value="REMOVE_CORRESPONDENT">
                    <spring:message code="personChange.changeType.REMOVE_CORRESPONDENT"/><br>
                    <input type="radio" name="changeKind" id="CHANGE_CORRESPONDENT_ADDRESS" value="CHANGE_CORRESPONDENT_ADDRESS">
                    <spring:message code="personChange.changeType.CHANGE_CORRESPONDENT_ADDRESS"/><br>
                    <br>
                    <br>
                </div>
            </div>

            <div id="changeRequestContent">
                <div id="personChangeTopSection">
                </div>
                <div id="personChangeSection">
                </div>
            </div>

        </div>

        <div id="personChangeMatches">
            <jsp:include page="/WEB-INF/views/personChange/personChangeMatches.jsp"/>
        </div>
    </c:if>
    
    <c:set var="maxPersonChangeKey" value="personChange.add.maxNumber.${flowModeId}"/>
    
	<input type="hidden" id="maxPersonChanges"
       value="${configurationServiceDelegator.getValue(maxPersonChangeKey, 'eu.ohim.sp.core.person')}">
    <script type="text/javascript">
        checkMaxPersonChanges();
    </script>
</section>
