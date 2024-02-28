<#include "../../common/macros/gshelper.ftl">
<#escape x as x?html>
    <#if esApplication.gsHelpers?? && esApplication.gsHelpers?size &gt; 0>
        <#if esApplication.applicationType == "TM_SURRENDER" || esApplication.applicationType == "TM_WITHDRAWAL">
            <#assign showDeclaration = true/>
        <#else>
            <#assign showDeclaration = false/>

        </#if>

        <tr>
            <td>
                <@gshelper esApplication.gsHelpers[0] esApplication.getRemovedGS(esApplication.gsHelpers[0]) applicationType showDeclaration true/>
            </td>
        </tr>
    </#if>
</#escape>