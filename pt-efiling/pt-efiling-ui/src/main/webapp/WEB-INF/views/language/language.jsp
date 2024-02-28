<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
	<c:set var="sectionId" value="languages" scope="request"/>
	
    <section class="language" id="languages">
        <header>
            <a href="#languages" class="anchorLink"></a>
            <h2><spring:message code="design.language.title"/></h2>
        </header>

		<div id="firstSecondLang">
		</div>	
	
		<component:generic path="secLang" checkRender="true">
			<c:if test="${not empty flowBean.firstLang && not empty configurationServiceDelegator.resolveFirstLanguageCode(flowBean.firstLang)}">
				<p>
	               	<span>
	            		<span><spring:message code="design.language.selected.first"/></span>
	            		<c:set var="language" value="${configurationServiceDelegator.resolveFirstLanguageCode(flowBean.firstLang)}"/>
	                   	<span id="firstLanguageValue" class="first-language"><spring:message code="${language.value}"/></span>
	                </span>
	                <spring:message code="design.language.selected.second"/>
                </p>
			</c:if>
		</component:generic>

        <component:generic path="firstLang" checkRender="true">
            <div class="language-fields">
                <ul class="languages">
                    <li>
                    	<component:select items="${configurationServiceDelegator['firstLanguages']}" 
								labelTextCode="design.language.first" path="firstLang"
								itemLabel="value" itemValue="code" checkRender="true"/>
                    </li>
                </ul>
            </div>
        </component:generic>
        
        <component:generic path="secLang" checkRender="true">
			<div class="language-fields">
            	<ul class="languages">
                    <li>
                        <component:select items="${configurationServiceDelegator['secondLanguages']}"
                        		labelTextCode="design.language.second" path="secLang"
                                itemLabel="value" itemValue="code" checkRender="true"/>
                    </li>
                </ul>
            </div>
            <div class="language-info">
                <ul>
                    <li><component:helpMessage textCode="second.language.help.wizard"/></li>
                </ul>
            </div>
        </component:generic>

        <!-- SECOND LANGUAGE CORRESPONDANCE -->
        <component:generic path="mainForm.correspondenceLanguageCheckBox" checkRender="true">
        	<div class="language-fields">
            	<component:checkbox path="mainForm.correspondenceLanguageCheckBox"
                               checkRender="true" id="sendsecondlng" labelClass="language-option"
                               labelTextCode="design.language.second.correspondence" formTagClass=""/>
        	</div>
        </component:generic>	

        <!-- SECOND LANGUAGE TRANSLATION -->
        <component:generic path="mainForm.secondLanguageTranslation" checkRender="true">
        	<div class="language-fields">
            	<component:checkbox path="mainForm.secondLanguageTranslation" checkRender="true" labelClass="language-option"
                                id="secondLangChkbox" labelTextCode="design.language.second.flagged" formTagClass=""/>
        	</div>
        </component:generic>	

    </section>
</form:form>