<#include "../../common/macros/label.ftl">
<#include "../../common/macros/designDetails.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <#switch applicationType>
                <#case "DS_RENEWAL"><#case "DS_TRANSFER"><#case "DS_CHANGE"><#case "DS_LICENCE">
                <#case "DS_SECURITY"><#case "DS_REM"><#case "DS_INVALIDITY"><#case "DS_BANKRUPTCY">
                <#case "DS_SURRENDER"><#case "DS_WITHDRAWAL">
                <#assign designMsgCode = "design.eservices.title.label."+applicationType/>
                <#break>
                <#default>
                    <#assign designMsgCode = "design.eservices.title.label"/>
                    <#break>
            </#switch>

            <#if applicationType?ends_with("SURRENDER")>
                <h3 style="color: red"><@label "design.surrender.declaration"/></h3>
            </#if>

            <#include "selectedUserdoc.ftl"/>

            <h3><@label designMsgCode/></h3>
            <#if esApplication.selectedDesigns?? && esApplication.selectedDesigns?size &gt; 0>
                <#assign selectedDesigns = esApplication.selectedDesigns/>
                <@designDetails selectedDesigns/>
            </#if>
        </td>
    </tr>
</#escape>
