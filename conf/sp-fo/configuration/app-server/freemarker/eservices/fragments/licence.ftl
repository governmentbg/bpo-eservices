<#include "../../common/macros/gshelper.ftl">
<#escape x as x?html>
    <#if esApplication.licences?? && esApplication.licences?size &gt; 0>
        <#assign licence = esApplication.licences[0]/>
        <tr>
            <td>
                <h3><@label "licence.title.label"/></h3>
                <#if licence.licenceKind??>
                    <div>
                        <b><@label "licence.kind.label"/>: </b> <@label "licence.kind."+licence.licenceKind?string/>
                    </div>
                </#if>
                <#if licence.subLicenceIndicator??>
                    <div>
                        <b><@label "licence.subLicenceIndicator.label"/>: </b> <@label "licence.subLicenceIndicator."+licence.subLicenceIndicator?string/>
                    </div>
                </#if>
                <#if licence.territoryLimitationIndicator??>
                    <div>
                        <b><@label "licence.territoryLimitationIndicator.label"/>: </b> <@label "licence.territoryLimitationIndicator."+licence.territoryLimitationIndicator?c/>
                    </div>

                    <#if licence.territoryLimitationIndicator == true>
                        <div>
                            <b><@label "licence.territoryLimitationText.label"/>: </b> ${licence.territoryLimitationText}
                        </div>
                    </#if>
                </#if>
                <div>
                    <b><@label "licence.periodLimitationEndDate.label"/>: </b>
                    <#if licence.periodLimitationIndicator?? && licence.periodLimitationIndicator == true>
                        <@label "licence.periodLimitationEndDate.toExpiry."+applicationType/>
                    <#elseif licence.periodLimitationEndDate??>
                        ${licence.periodLimitationEndDate?string("dd.MM.yyyy")}
                    </#if>
                </div>

                <#if licence.gsHelper?? && licence.gsHelper.tmApplicationNumber??>
                    <@gshelper licence.gsHelper esApplication.getRemovedGS(licence.gsHelper)![] applicationType false false/>
                </#if>
            </td>
        </tr>
    </#if>
</#escape>