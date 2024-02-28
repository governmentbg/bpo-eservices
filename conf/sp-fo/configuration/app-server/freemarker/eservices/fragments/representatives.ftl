<#include "../../common/macros/representatives.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <#if applicationType?ends_with("CHANGEREP")>
                <#assign representMsgCode = "newrepresentatives.eservices.title.label"/>
            <#else >
                <#assign representMsgCode = "representatives.eservices.title.label"/>
            </#if>

            <@representatives esApplication.representatives representMsgCode true></@representatives>
        </td>
    </tr>
</#escape>