<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="doubleItem">
    <input id="personChangeMatchesExist" type="hidden" value="true">
    <h4>
        ${param.name}
        <%--<a class="btn btn-mini">more info</a>--%>
        <a id="importPersonChangeFromMatches" data-val="${param.id}" class="btn right">
            <spring:message code="person.matches.action.import"/>
        </a>
    </h4>
    <div class="row-fluid">
        <div class="span4">
            <dl class="dl-horizontal">
                <dt><spring:message code="person.matches.info.id"/></dt>
                <dd>${param.id}</dd>
                <dt><spring:message code="person.matches.info.type"/></dt>
                <dd>${param.type}</dd>
                <dt><spring:message code="person.matches.info.country"/></dt>
                <dd>${param.addressCountry}</dd>
            </dl>
        </div>
        <div class="span4">
            <dl class="dl-horizontal">
                <dt><spring:message code="person.matches.info.province"/></dt>
                <dd>${param.addressProvince}</dd>
                <dt><spring:message code="person.matches.info.city"/></dt>
                <dd>${param.addressCity}</dd>
                <dt><spring:message code="person.matches.info.postalcode"/></dt>
                <dd>${param.addressPostal}</dd>
                <dt><spring:message code="person.matches.info.street"/></dt>
                <dd>${param.addressStreet}</dd>
            </dl>
        </div>
        <div class="span4">
            <dl>
                <dt><spring:message code="person.matches.info.correspondenceaddress"/></dt>
                <dd>Hogan Lovells</dd>
                <dd>Tobias Dolde</dd>
                <dd>Avenida Maisonnave,22</dd>
                <dd>E-03008 Alicante Spain</dd>
            </dl>
        </div>
    </div><!--row-fluid-->
</div><!--onedouble-->