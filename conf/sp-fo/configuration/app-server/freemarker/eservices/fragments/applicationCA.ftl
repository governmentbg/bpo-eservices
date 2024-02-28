<#include "../../common/macros/applicationCA.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <#if applicationType?ends_with("CHANGECA") || applicationType?ends_with("LICENCE") ||
            applicationType?ends_with("REM") || applicationType?ends_with("TRANSFER") || applicationType?ends_with("CHANGE")>
                <#assign appCAMsgCode ="applicationCA.eservices.title.label."+applicationType/>

               <#else>
                   <#assign appCAMsgCode ="applicationCA.eservices.title.label"/>
            </#if>

            <@applicationCA esApplication.contactDetails appCAMsgCode "" ""></@applicationCA>
        </td>
    </tr>
</#escape>