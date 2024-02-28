<#include "../../common/macros/label.ftl">
<#include "../../common/macros/markDetails.ftl">
<#escape x as x?html>
    <#if esApplication.selectedUserdoc??>
        <h3><@label "userdoc.title.label"/>: ${esApplication.selectedUserdoc.userdocTypeName}</h3>
        <div>
            <b><@label "userdoc.number.label"/>: </b>
            <#if esApplication.selectedUserdoc.externalSystemId??>
                ${esApplication.selectedUserdoc.externalSystemId}
            <#else>
                ${esApplication.selectedUserdoc.docOrigin+"/"+esApplication.selectedUserdoc.docLog+"/"+(esApplication.selectedUserdoc.docSeqSeries?string["0"])+"/"+(esApplication.selectedUserdoc.docSeqNbr?string["0"])}
            </#if>
            <#if esApplication.selectedUserdoc.filingDate??>
                ${"/ "+esApplication.selectedUserdoc.filingDate?string("dd.MM.yyyy")}
            </#if>
        </div>
    </#if>
</#escape>