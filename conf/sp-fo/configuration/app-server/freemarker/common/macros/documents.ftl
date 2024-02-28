<#macro documents documentList titleLabel>
    <#escape x as x?html>
        <h3><@label titleLabel/></h3>
        <#if documentList?? && documentList?size &gt; 0>
            <#list documentList as doc>
                <div style="margin-bottom: 10px">
                    ${doc.document.fileName}
                    <#if doc.documentKind??>
                        <i>(<@label "document."+doc.documentKind?replace(" ", "_")?upper_case/>)</i>
                    </#if>
                    <#if doc.document.comment??>
                        <br/>
                        ${doc.document.comment}
                    </#if>
                </div>
            </#list>
        <#else>
            <@label "documents.not.attached.label"/>
        </#if>
    </#escape>
</#macro>