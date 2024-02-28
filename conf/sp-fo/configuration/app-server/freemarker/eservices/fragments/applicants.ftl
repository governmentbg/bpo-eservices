<#include "../../common/macros/applicants.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <#switch applicationType>
                <#case "TM_OPPOSITION"> <#case "TM_OBEJCTION"> <#case "TM_REVOCATION"> <#case "TM_INVALIDITY">
                <#case "TM_APPEAL"><#case "DS_INVALIDITY"><#case "PT_APPEAL"><#case "SPC_APPEAL"><#case "PT_INVALIDITY"><#case "SPC_INVALIDITY"><#case "UM_INVALIDITY">
                    <#assign applicantsMsgCode = "applicants.eservices.title.label."+applicationType/>
                    <#break>
                <#default>
                    <#assign applicantsMsgCode = "applicants.eservices.title.label"/>
                    <#break>
            </#switch>

            <@applicants esApplication.applicants applicantsMsgCode></@applicants>
        </td>
    </tr>
</#escape>