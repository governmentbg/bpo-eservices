<#include "../../common/macros/label.ftl">
<#include "../../common/macros/markDetails.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <#switch applicationType>
                <#case "TM_OPPOSITION"><#case "TM_OBJECTION"><#case "TM_INVALIDITY"><#case "TM_REVOCATION">
                <#assign markMsgCode = "mark.eservices.title.label."+applicationType/>
                <#break>
                <#default>
                    <#assign markMsgCode = "mark.eservices.title.label"/>
                    <#break>
            </#switch>

            <#include "selectedUserdoc.ftl"/>

            <h3><@label markMsgCode/></h3>
            <#if esApplication.tradeMarks?? && esApplication.tradeMarks?size &gt; 0>
                <#assign tm = esApplication.tradeMarks[0]/>
                <@markDetails tm false false ""/>
            </#if>
        </td>
    </tr>
</#escape>