<#include "../../common/macros/label.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <h3 class="eserviceTitle ${mainApplicationType}">
                <#if esApplication.changeType?? == false || esApplication.changeType == "">
                    <@label "eservices.applicationType."+ applicationType />
                <#else>
                    <#switch mainApplicationType>
                        <#case "PT">
                            <@label "eservices.applicationType.GENERIC"/>: <@label "eservices.applicationType.GENERIC.pt."+ esApplication.changeType />
                            <#break>
                        <#case "UM">
                            <@label "eservices.applicationType.GENERIC"/>: <@label "eservices.applicationType.GENERIC.um."+ esApplication.changeType />
                            <#break>
                        <#case "EP">
                            <@label "eservices.applicationType.GENERIC"/>: <@label "eservices.applicationType.GENERIC.ep."+ esApplication.changeType />
                            <#break>
                        <#case "SV">
                            <@label "eservices.applicationType.GENERIC"/>: <@label "eservices.applicationType.GENERIC.sv."+ esApplication.changeType />
                            <#break>
                        <#case "SP"><#case "SPC">
                            <@label "eservices.applicationType.GENERIC"/>: <@label "eservices.applicationType.GENERIC.spc."+ esApplication.changeType />
                            <#break>
                        <#default>
                            <@label "eservices.applicationType.GENERIC"/>: <@label "eservices.applicationType.GENERIC."+ esApplication.changeType />
                            <#break>
                    </#switch>
                </#if>
            </h3>
        </td>
    </tr>
    <tr>
        <td>
            <#if esign?? == false>
                <div><@label "application.not.signed.message"/></div>
                <#if isDraft>
                    <div><@label "application.not.signed.draft.message"/></div>
                </#if>
            <#else>
                <#switch applicationType>
                    <#case "TM_OPPOSITION"> <#case "TM_OBEJCTION">
                    <#case "TM_APPEAL">
                        <#assign filedMsgCode = "filed.eservice.message."+applicationType/>
                        <#break>
                    <#default>
                        <#assign filedMsgCode = "filed.eservice.message"/>
                        <#break>
                </#switch>

                <#if applicationDateStr?? && applicationDateStr != "">
                    <@label "accepted.eservice.message" applicationNumber applicationId applicationDateStr/>
                <#elseif isDraft == false>
                    <@label filedMsgCode applicationNumber />
                    <#if applicationType == "TM_OPPOSITION" || applicationType == "TM_OBJECTION" || applicationType == "TM_REVOCATION" || applicationType == "TM_INVALIDITY">
                        <br/>
                        <@label "eservices.evidence.warning.msg"/>
                    </#if>
                </#if>

                <#if isDraft>
                    <div><@label "application.draft.message"/></div>
                </#if>
            </#if>
        </td>
    </tr>
</#escape>