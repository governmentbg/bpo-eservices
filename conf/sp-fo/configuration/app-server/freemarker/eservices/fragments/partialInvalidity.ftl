<#escape x as x?html>
    <#if (applicationType =="PT_INVALIDITY" || applicationType =="SPC_INVALIDITY" || applicationType =="UM_INVALIDITY") && esApplication.partialInvalidity??>
        <tr>
            <td>
                <h3 style="color: red"><@label "partial.invalidity."+esApplication.partialInvalidity?c+"."+applicationType/></h3>
            </td>
        </tr>
    </#if>
</#escape>