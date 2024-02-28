<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component"%>

<jsp:directive.attribute name="node" required="true" type="eu.ohim.sp.common.ui.form.TaxonomyConceptNodeTreeView" description="The node to be represented in the node" />
<jsp:directive.attribute name="rootItem" required="true" type="java.lang.Boolean" description="Defines if it is the root element of the taxonomy view" />

<li>
	<span title="<spring:message code="gs.treeOptions.node"/>" id="${node.id}" class="btn"><c:out 
		value="${node.text}" 
		/> - <c:out value="${node.numTermsSatisfyingCriteria}" 
	/></span><c:if test="${!node.leaf}"
		><ul><c:forEach 
			items="${node.children}" var="nodeChild"
				><component:node node="${nodeChild}" rootItem="false" 
			/></c:forEach
		></ul></c:if
></li>