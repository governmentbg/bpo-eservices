<%@ page import="eu.ohim.sp.common.ui.form.application.SignatoryKindForm" %>
<%@ page import="java.util.Arrays" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<form:form class="mainForm formEf" modelAttribute="flowBean.mainForm.${param.nestedPathName}">
    <c:set var="sectionId" value="${param.sectionId}" scope="request"/>
    <ul class="row-fields">
        <li>
            <component:input path="fullName" checkRender="true" labelTextCode="signature.signatoryName"
                             formTagClass="name-text"/>
        </li>
        <component:generic path="personCapacity" checkRender="true">
            <li>
                <c:set var="enumValues" value="<%=Arrays.asList(SignatoryKindForm.values())%>"/>
                <component:select path="personCapacity"
                                  items="${enumValues}"
                                  itemValue="code" itemLabel="description" labelTextCode="signature.capacityOfSignatory"
                                  containsEmptyValue="true"
                                  formTagClass="capacity-select"/>
            </li>
        </component:generic>
		<component:generic path="position" checkRender="true">
			<li>
				<component:input path="position" checkRender="false" labelTextCode="signature.capacityOfSignatory" formTagClass="place-text"/>
			</li>
		</component:generic>
    </ul>

    <ul class="row-fields">
    	<component:generic path="taxIdNumber" checkRender="true">
        	<li>
	        	<component:input path="taxIdNumber" checkRender="true" labelTextCode="signature.taxIdNumber" formTagClass="name-text" />
			</li>        
    	</component:generic>
    
        <component:generic path="date" checkRender="true">
            <li>
                <component:input path="date" checkRender="true" labelTextCode="signature.date"
                                 formTagClass="standard-date-input" showDateHelp="true" />
            </li>
        </component:generic>
         
        <component:generic path="signaturePlace" checkRender="true">
            <li>
                <component:input path="signaturePlace" checkRender="true" labelTextCode="signature.place"
                                 formTagClass="name-text"/>
            </li>
        </component:generic>
    </ul>
    <ul class="row-fields">
        <component:generic path="signatureAssociatedText" checkRender="true">
            <li>
                <component:input path="signatureAssociatedText" checkRender="true" labelTextCode="signature.associatedText"
                                 formTagClass="name-text"/>
            </li>
        </component:generic>
        <component:generic path="userId" checkRender="true">
            <li>
                <component:input path="userId" checkRender="true" labelTextCode="signature.userId"
                                 formTagClass="place-text"/>
            </li>
        </component:generic>
    </ul>
</form:form>