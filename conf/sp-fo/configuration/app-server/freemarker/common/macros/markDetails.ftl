<#macro markDetails mark showExtent showGS gsLabelCode>
    <#include "gsList.ftl"/>
    <#escape x as x?html>
        <#if mark.markRightKind??>
            <div>
                <b><@label "mark.right.label"></@label>: </b>
                <@label "mark.right."+mark.markRightKind.value()/>
            </div>
        </#if>
        <#if mark.markKind??>
            <div>
                <b><@label "mark.type.short.label"></@label>: </b>
                <@label "mark.type."+mark.markKind.value()?replace(" ", "")/>
            </div>
        </#if>
        <#if mark.wordSpecifications?? && mark.wordSpecifications?size &gt; 0 && mark.wordSpecifications[0].wordElements??>
            <div>
                <b><@label "mark.name.label"></@label>: </b>
                <span class="titleText">${mark.wordSpecifications[0].wordElements}</span>
            </div>
        </#if>
        <#if mark.imageSpecifications?? && mark.imageSpecifications?size &gt; 0 && mark.imageSpecifications[0].representation??>
            <b><@label "mark.image.label"></@label>: </b>
            <#if mark.imageSpecifications[0].representation.documentId??>
                <img data-src="documentService" src="${mark.imageSpecifications[0].representation.documentId}" width="150" />
            <#elseif mark.imageSpecifications[0].representation.uri??>
                <img src="${mark.imageSpecifications[0].representation.uri}" width="150" />
            </#if>
        </#if>
        <#if mark.applicationNumber??>
            <div>
                <b><@label "mark.applicationNumber.label"/>: </b> <span class="titleText">${mark.applicationNumber}</span>
            </div>
        </#if>
        <#if mark.registrationNumber??>
            <div>
                <b><@label "mark.registrationNumber.label"/>: </b> <span class="titleText">${mark.registrationNumber}</span>
            </div>
        </#if>
        <#if mark.applicationDate??>
            <div>
                <b><@label "mark.applicationDate.label"/>: </b>  ${mark.applicationDate?string("dd.MM.yyyy")}
            </div>
        </#if>
        <#if mark.registrationDate??>
            <div>
                <b><@label "mark.registrationDate.label"/>: </b> ${mark.registrationDate?string("dd.MM.yyyy")}
            </div>
        </#if>
        <#if mark.priorities?? && mark.priorities?size &gt;0 && mark.priorities[0].filingDate??>
            <div>
                <b><@label "mark.priority.filingDate.label"/>: </b> ${mark.priorities[0].filingDate?string("dd.MM.yyyy")}
            </div>
        </#if>
        <#if mark.expirationDate??>
            <div>
                <b><@label "mark.expirationDate.label"/>: </b> ${mark.expirationDate?string("dd.MM.yyyy")}
            </div>
        </#if>
        <#if mark.currentStatus??>
            <div>
                <b><@label "mark.currentStatus.label"/>: </b> <@label "mark.currentStatus."+mark.currentStatus?replace(" ", "")/>
            </div>
        </#if>
        <#if mark.applicants?? && mark.applicants?size &gt; 0>
            <#if mark.applicants[0].name?? && (mark.applicants[0].name.firstName?? ||
            mark.applicants[0].name.middleName?? || mark.applicants[0].name.lastName?? || mark.applicants[0].name.organizationName??) >
                <div><b class="titleText"><@label "mark.applicants.label"/>: </b></div>
                <#list mark.applicants as applicant>
                    <div style="margin-top:10px; margin-left: 20px">
                        <div>
                            <b><@label "name.label"/>: </b>
                            <#if applicant.name??>
                                <#if applicant.kind?? && applicant.kind.name() == "NATURAL_PERSON">
                                    ${applicant.name.firstName}
                                    <#if applicant.name.middleName??>${" "+applicant.name.middleName}</#if>
                                    <#if applicant.name.lastName??>${" "+applicant.name.lastName}</#if>
                                <#else>
                                    ${applicant.name.organizationName}
                                </#if>
                            </#if>
                            <#if applicant.personNumber??>
                                (${applicant.personNumber})
                            <#elseif applicant.identifiers?? && applicant.identifiers?size &gt;0
                            && applicant.identifiers[0].value??>
                                (${applicant.identifiers[0].value})
                            </#if>
                        </div>
                        <div>
                            <b><@label "address.label"/>: </b>
                            <#if applicant.domicileLocality?? && applicant.domicileLocality != "">
                                ${applicant.domicileLocality!""}
                            <#elseif applicant.addresses?? && applicant.addresses?size &gt;0>
                                <#assign addr = applicant.addresses[0]/>
                                ${(addr.street??)?then(addr.street+", ", "")}
                                ${(addr.city??)?then(addr.city+", ", "")}
                                ${addr.country!""}
                            </#if>
                        </div>
                    </div>
                </#list>
            </#if>
        </#if>
        <#if showExtent>
            <div style="margin-top: 10px">
                <b><@label "mark.extent.label"/>: </b> <@label "mark.extent."+mark.applicationExtent.name()/>
            </div>
        </#if>
        <#if showGS>
            <#if showExtent == false || mark.applicationExtent?? == false || mark.applicationExtent.name() !="ALL_GOODS_AND_SERVICES">
                <#if gsLabelCode?? && gsLabelCode != "">
                    <div>
                        <@label gsLabelCode/>
                    </div>
                </#if>
            </#if>
            <#if mark.applicationExtent?? == false || mark.applicationExtent.name() !="ALL_GOODS_AND_SERVICES">
                <#if mark.limitedClassDescriptions?? && mark.limitedClassDescriptions?size &gt; 0>
                    <@gsList mark.limitedClassDescriptions />
                </#if>
                <#if mark.goodsServicesComment?? && mark.goodsServicesComment != "">
                    <b><@label "mark.goodsServicesComment.label"/>: </b> ${mark.goodsServicesComment}
                </#if>
            </#if>
        </#if>
    </#escape>
</#macro>