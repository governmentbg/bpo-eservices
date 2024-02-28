<#macro esignature esign dateSigned>
    <#escape x as x?html>
        <h3><@label "esignature.title.label"/></h3>
        <div>
            <b><@label "dateSigned.label"/>: </b> <#if dateSigned??>${dateSigned?string("dd.MM.yyyy HH:mm:ss")}</#if>
        </div>
        <div>
            <b><@label "signatoryName.label"/>: </b> ${esign.name!""}
        </div>
        <div>
            <b><@label "signatureEmail.label"/>: </b> ${esign.email!""}
        </div>
        <div>
            <b><@label "signatureIssuser.label"/>: </b> ${esign.issuer!""}
        </div>
        <div>
            <b><@label "signatureValidTo.label"/>: </b> <#if esign.validTo??>${esign.validTo?string("dd.MM.yyyy HH:mm:ss")}</#if>
        </div>
    </#escape>
</#macro>