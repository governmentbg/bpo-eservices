<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 10.5.2022 г.
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="signature" id="goodsAndServicesDescriptionsSection">

    <header>
        <a id="goodsAndServicesDescriptions" class="anchorLink"></a>

        <h2>
            <spring:message code="goodsandservices.descriptions.title"/>
        </h2>
    </header>
    <c:set var="sectionId" value="goodsandservices_descriptions" scope="request"/>

    <form:form modelAttribute="flowBean" id="formEF" cssClass="mainForm" method="POST">
        <component:textarea path="mainForm.goodsCharacteristicsDescription" checkRender="true" id="goodsCharacteristicsDescription"
                            labelTextCode="goods.characteristics.description" formTagClass="goods-description-textarea"/>

        <component:textarea path="mainForm.goodsProductionDescription" checkRender="true" id="goodsProductionDescription"
                            labelTextCode="goods.production.description" formTagClass="goods-description-textarea"/>

        <component:textarea path="mainForm.goodsGeographyDescription" checkRender="true" id="goodsGeographyDescription"
                            labelTextCode="goods.geography.description" formTagClass="goods-description-textarea"/>

        <c:if test="${flowBean.mainForm.markType == 'oriname'}">
            <component:textarea path="mainForm.goodsFactorsDescription" checkRender="true" id="goodsFactorsDescription"
                                labelTextCode="goods.factors.description" formTagClass="goods-description-textarea"/>
        </c:if>
    </form:form>

</section>
