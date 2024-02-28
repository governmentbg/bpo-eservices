<#macro representatives representativesList labelTitle powApplies>
    <#include "address.ftl" />

    <#escape x as x?html>
        <h3><@label labelTitle></@label></h3>
        <#if representativesList?? && representativesList?size &gt; 0 >
            <#list representativesList as repr>
                <div style="margin-bottom:10px">
                    <#if repr.representativeKind??>
                        <div>
                            <b><@label "representative.kind.label"></@label>:</b>
                            <#if repr.representativeKind?string != "Other">
                                <@label "representative.kind."+repr.representativeKind.name() ></@label>
                            <#elseif repr.kind.enumName??>
                                <@label "representative.kind."+repr.kind.enumName ></@label>
                            </#if>
                        </div>
                    </#if>
                    <div>
                        <b><@label "name.label"></@label>:</b>
                        <#if repr.name?? && (repr.name.firstName??)>
                            ${repr.name.firstName}
                            <#if repr.name.middleName??>${" "+repr.name.middleName}</#if>
                            <#if repr.name.lastName??>${" "+repr.name.lastName}</#if>
                        <#else>
                            ${repr.name.organizationName}
                        </#if>
                    </div>
                    <#if repr.nationality??>
                        <div>
                            <b><@label "nationality.label"/>: </b>
                            ${repr.nationality}
                        </div>
                    </#if>
                    <#if repr.identifiers?? && repr.identifiers?size &gt; 0>
                        <#list repr.identifiers as id>
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
                        <@address repr.addresses![] repr.phones![] repr.emails![] repr.urls![]></@address>
                    </div>
                    <#if powApplies = true && repr.personRoleRelationships?? && repr.personRoleRelationships?size &gt; 0>
                        <#list repr.personRoleRelationships as pow>
                            <div>
                                <b><@label "pow.label"/>: </b>
                                <div style="margin-left: 20px">
                                    <#if pow.documents?? && pow.documents?size &gt;0 && pow.documents[0].document?? && pow.documents[0].document.status??>
                                        <#if pow.documents[0].document.status = "Provided">
                                            <@label "pow.provided.label"/>
                                        <#elseif pow.documents[0].document.status = "To Follow">
                                            <@label "pow.toFollow.label"/>
                                        <#else>
                                            <#if pow.documents[0].document.fileName??>
                                                ${pow.documents[0].document.fileName}
                                            </#if>
                                            <#if pow.documents[0].document.comment??>
                                                <i>${" ("+pow.documents[0].document.comment+") "}</i>
                                            </#if>
                                        </#if>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    <#if repr.powValidityIndefiniteIndicator??>
                        <div>
                            <b><@label "pow.powValidityIndefiniteIndicator.label"></@label>:</b>
                            <#if repr.powValidityIndefiniteIndicator>
                                <@label "pow.powValidityIndefiniteIndicator.true" />
                            <#else>
                                <@label "pow.powValidityIndefiniteIndicator.false" />
                            </#if>
                        </div>
                    </#if>
                    <#if repr.powValidityEndDate??>
                        <div>
                            <b><@label "pow.validityEndDate.label"></@label>:</b>
                            ${repr.powValidityEndDate?string("dd.MM.yyyy")}
                        </div>
                    </#if>
                    <#if repr.powReauthorizationIndicator??>
                        <div>
                            <b><@label "pow.powReauthorizationIndicator.label"></@label>:</b>
                            <#if repr.powReauthorizationIndicator>
                                <@label "pow.powReauthorizationIndicator.true" />
                            <#else>
                                <@label "pow.powReauthorizationIndicator.false" />
                            </#if>
                        </div>
                    </#if>
                    <#if repr.powRevokesPreviousIndicator??>
                        <div>
                            <b><@label "pow.powRevokesPreviousIndicator.label"></@label>:</b>
                            <#if repr.powRevokesPreviousIndicator>
                                <@label "pow.powRevokesPreviousIndicator.true" />
                            <#else>
                                <@label "pow.powRevokesPreviousIndicator.false" />
                            </#if>
                        </div>
                    </#if>
                    <#if repr.powNote??>
                        <div>
                            <b><@label "pow.powNote.label"></@label>:</b>
                            ${repr.powNote}
                        </div>
                    </#if>
                </div>
            </#list>
        </#if>
    </#escape>
</#macro>