<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<a id="marktitle" class="anchorLink"></a>
<h3><spring:message code="marktoprotect" /></h3>

<div id="firstSecondLang">
	<tiles:insertAttribute name="marktoprotect_languages"/>
</div>

<tiles:insertAttribute name="marktoprotect_reference"/>

<tiles:insertAttribute name="marktoprotect_typemark"/>