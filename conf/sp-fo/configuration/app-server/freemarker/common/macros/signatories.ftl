<#macro signatories signatoryList>

    <#escape x as x?html>
        <h3><@label "signatures.title.label"/></h3>
        <#if signatoryList?? && signatoryList?size &gt; 0>
            <#list signatoryList as signatory>
                <div style="margin-bottom: 10px">
                    <div>
                        <b><@label "signatures.name.label"/>:</b> ${signatory.name}
                    </div>
                    <div>
                        <b><@label "signatures.capacity.label"/>:</b> <@label "signatures.capacity."+signatory.capacity.name()/>
                    </div>
                    <#if signatory.associatedText??>
                        <div>
                            <b><@label "signatures.associatedText.label"/>:</b> ${signatory.associatedText}
                        </div>
                    </#if>
                </div>
            </#list>
        </#if>
    </#escape>
</#macro>