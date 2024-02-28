<#include "../../common/macros/label.ftl">
<#escape x as x?html>
    <#if esApplication.securityMeasure?? >
        <tr>
            <td>
                <h3><@label "security.measure.title.label"/></h3>
                <#if esApplication.securityMeasure.securityMeasureForbidRightsUse?? && esApplication.securityMeasure.securityMeasureForbidRightsUse == true>
                    <div><b><@label "security.measure.forbid.use."+applicationType/></b></div>
                </#if>
                <#if esApplication.securityMeasure.securityMeasureForbidRightsManage?? && esApplication.securityMeasure.securityMeasureForbidRightsManage == true>
                    <div><b><@label "security.measure.forbid.manage."+applicationType/></b></div>
                </#if>
            </td>
        </tr>
    </#if>
</#escape>