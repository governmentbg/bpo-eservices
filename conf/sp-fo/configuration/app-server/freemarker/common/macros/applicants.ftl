<#macro applicants applicantsList labelTitle>
    <#include "address.ftl" />

    <#escape x as x?html>
        <h3><@label labelTitle></@label></h3>
        <#if applicantsList?? && applicantsList?size &gt; 0 >
            <#list applicantsList as applicant>
                <div style="margin-bottom: 10px">
                    <div>
                        <b><@label "name.label"></@label>:</b>
                        <#if applicant.name??>
                            <#if applicant.kind?? && applicant.kind.name() == "NATURAL_PERSON">
                                ${applicant.name.firstName}
                                <#if applicant.name.middleName??>${" "+applicant.name.middleName}</#if>
                                <#if applicant.name.lastName??>${" "+applicant.name.lastName}</#if>
                            <#else>
                                ${applicant.name.organizationName}
                            </#if>
                        </#if>
                    </div>
                    <#if applicant.nationality??>
                        <div>
                            <b><@label "nationality.label"/>: </b>
                            ${applicant.nationality}
                        </div>
                    </#if>
                    <#if applicant.identifiers?? && applicant.identifiers?size &gt; 0>
                        <#list applicant.identifiers as id>
                            <#if id.identifierKindCode?? && (id.identifierKindCode =="Bulgarian National ID" ||
                                id.identifierKindCode =="Bulgarian Foreigner ID")
                                && id.value??>
                                <div>
                                    <b><@label "nationalId.label"/>: </b>
                                    ${id.value}
                                </div>
                             </#if>

                            <#if id.identifierKindCode?? && (id.identifierKindCode =="Company Number")
                                && id.value??>
                                <div>
                                    <b><@label "companyNumber.label"/>: </b>
                                    ${id.value}
                                </div>
                            </#if>
                        </#list>
                    </#if>
                    <div>
                        <@address applicant.addresses![] applicant.phones![] applicant.emails![] applicant.urls![]></@address>
                    </div>
                </div>
            </#list>
        </#if>
    </#escape>
</#macro>