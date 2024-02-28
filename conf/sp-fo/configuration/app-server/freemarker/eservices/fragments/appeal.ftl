<#include "../../common/macros/gshelper.ftl">
<#escape x as x?html>
    <#if esApplication.appeals?? && esApplication.appeals?size &gt; 0>
        <#assign appeal = esApplication.appeals[0]/>
        <tr>
            <td>
                <h3><@label "appeal.title.label"/></h3>
                <#if appeal.appealKind??>
                    <div>
                        <b><@label "appeal.appealKind.label"/>: </b> <@label "appeal.appealKind."+applicationType+"."+appeal.appealKind/>
                    </div>
                </#if>
                <#if appeal.decisionNumber??>
                    <div>
                        <b><@label "appeal.decisionNumber.label"/>: </b> ${appeal.decisionNumber}
                    </div>
                </#if>
                <#if appeal.oppositionFilingDate??>
                    <div>
                        <b><@label "appeal.oppositionFilingDate.label"/>: </b> ${appeal.oppositionFilingDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if appeal.decisionDate??>
                    <div>
                        <b><@label "appeal.decisionDate.label"/>: </b> ${appeal.decisionDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if appeal.explanationText??>
                    <div>
                        <b><@label "appeal.explanationText.label"/>: </b> ${appeal.explanationText!""}
                    </div>
                </#if>
                <#if appeal.explanationsDocuments?? && appeal.explanationsDocuments?size &gt; 0>
                    <div>
                        <b><@label "appeal.explanationsDocuments.label"/>: </b>
                        <#assign explanationDocs = appeal.explanationsDocuments?map(d-> d.document.fileName)/>
                        ${explanationDocs?join("; ")}
                    </div>
                </#if>

                <#if appeal.gsHelper?? && appeal.gsHelper.tmApplicationNumber?? >
                    <@gshelper appeal.gsHelper esApplication.getRemovedGS(appeal.gsHelper)![] applicationType false false/>
                </#if>
            </td>
        </tr>
    </#if>
</#escape>