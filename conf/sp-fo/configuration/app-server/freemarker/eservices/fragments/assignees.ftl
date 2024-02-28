<#include "../../common/macros/applicants.ftl">
<#escape x as x?html>
    <#if esApplication.assignees?? && esApplication.assignees?size &gt; 0>
        <tr>
            <td>
                <#if applicationType?ends_with("TRANSFER")>
                    <#assign assigneeMsgCode = "assignees.eservices.title.label.transfer"/>
                <#elseif applicationType?ends_with("LICENCE")>
                    <#assign assigneeMsgCode = "assignees.eservices.title.label.licence"/>
                <#elseif applicationType?ends_with("REM")>
                    <#assign assigneeMsgCode = "assignees.eservices.title.label.rem"/>
                <#elseif applicationType?ends_with("SECURITY")>
                    <#assign assigneeMsgCode = "assignees.eservices.title.label.security"/>
                <#elseif applicationType?ends_with("INVDENIAL")>
                    <#assign assigneeMsgCode = "assignees.eservices.title.label.invdenial"/>
                </#if>

                <@applicants esApplication.assignees assigneeMsgCode></@applicants>
            </td>
        </tr>
    </#if>
</#escape>