<#include "../../common/macros/applicants.ftl">
<#escape x as x?html>
    <#if esApplication.holders?? && esApplication.holders?size &gt; 0>
        <tr>
            <td>
                <@applicants esApplication.holders "holders.eservices.title.label"></@applicants>
                <#if esApplication.holderIsInventor?? && esApplication.holderIsInventor == true>
                    <div><b class="titleText"><@label "holder.holderIsInventor.label."+applicationType/></b></div>
                </#if>
            </td>
        </tr>
    </#if>
</#escape>