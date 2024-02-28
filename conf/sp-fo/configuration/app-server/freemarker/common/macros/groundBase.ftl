<#macro groundBase ground>
    <#escape x as x?html>
        <#if ground.legalActVersion?? && ground.legalActVersion.articles?? && ground.legalActVersion.articles?size &gt; 0>
            <div>
                <b><@label "ground.articles.label"/>: </b>
                <#list ground.legalActVersion.articles as article>
                    <div>
                        <@label article.reference/>
                    </div>
                </#list>
            </div>
        </#if>
        <#if ground.explanationText?? && ground.explanationText != "">
            <div>
                <b><@label "ground.explanationText.label"/>: </b> ${ground.explanationText!""}
            </div>
        </#if>
        <#if ground.globalDocuments?? && ground.globalDocuments?size &gt; 0>
            <div>
                <#list ground.globalDocuments as doc>
                    <div>
                        <b><@label "document."+doc.documentKind?replace(" ", "")/>: </b>
                        <i>${doc.document.fileName}</i>
                        <#if doc.document.comment?? && doc.document.comment != "">
                            (${doc.document.comment})
                        </#if>
                    </div>
                </#list>
            </div>
        </#if>
    </#escape>
</#macro>