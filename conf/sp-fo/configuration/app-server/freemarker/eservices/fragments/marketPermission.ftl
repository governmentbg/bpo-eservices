<#include "../../common/macros/label.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <h3>
                <@label "patent.eservices.firstPermission.label."+applicationType/>
            </h3>
            <#if esApplication.marketPermission??>
                <#assign mp = esApplication.marketPermission/>
                <div>
                    <b><@label "market.permission.firstPermissionBGLabel"/></b>
                </div>
                <#if mp.firstPermissionBGNumber??>
                    <div>
                        <@label "market.permission.number"/>: ${mp.firstPermissionBGNumber}
                    </div>
                </#if>
                <#if mp.firstPermissionBGDate??>
                    <div>
                        <@label "market.permission.date"/>: ${mp.firstPermissionBGDate?string("dd.MM.yyyy")}
                    </div>
                </#if>

                <div>
                    <b><@label "market.permission.firstPermissionEULabel"/></b>
                </div>
                <#if mp.firstPermissionEUNumber??>
                    <div>
                        <@label "market.permission.number"/>: ${mp.firstPermissionEUNumber}
                    </div>
                </#if>
                <#if mp.firstPermissionEUDate??>
                    <div>
                        <@label "market.permission.date"/>: ${mp.firstPermissionEUDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
            </#if>
        </td>
    </tr>
</#escape>