<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="formEF" class="flForm">
    <a id="payment" class="anchorLink"></a>

    <h3>
        <spring:message code="review.payment"/>
    </h3>

    <div class="flFormRow alignCenter">
        <ul class="giantButtonsList">
            <li><a rel="" class="active"><spring:message code="review.credicard"/></a></li>
            <li><a rel=""><spring:message code="review.paypal"/></a></li>
        </ul>
    </div>
</div>