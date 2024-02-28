<#include "../../common/macros/label.ftl">
<#escape x as x?html>
    <#if applicationType == "PT_APPEAL" || applicationType == "SPC_APPEAL" >
        <tr>
            <td>
                <h3><@label "declarations.title.label"/></h3>
                <#if esApplication.smallCompany?? && esApplication.smallCompany==true>
                    <div style="color: red">
                        <b><@label "smallCompany.eservices.label"/></b>
                    </div>
                    <div>
                        <b><@label "smallCompanyDocuments.label"/>: </b>
                        <#assign smallCompDocs = esApplication.smallCompanyFiles?map(d -> d.document.fileName)/>
                        ${smallCompDocs?join("; ")}
                    </div>
                </#if>
                <#if esApplication.licenceAvailability?? && esApplication.licenceAvailability==true>
                    <div style="color: red">
                        <b><@label "licenceAvailability.eservices.label"/></b>
                    </div>
                </#if>
            </td>
        </tr>
    </#if>
</#escape>