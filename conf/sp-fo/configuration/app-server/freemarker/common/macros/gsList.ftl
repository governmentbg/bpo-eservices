<#macro gsList gsList>
    <#escape x as x?html>
        <#if gsList?? && gsList?size &gt;0>
            <#list gsList as gs>
                <p>
                    <b><@label "goodsservices.class.label"/> ${gs.classNumber}</b>
                </p>
                <#if gs.classificationTerms?? && gs.classificationTerms?size &gt; 0>
                    <#assign gsTerms = gs.classificationTerms?map(g -> g.termText)/>
                    <p>
                        ${gsTerms?join("; ")}
                    </p>
                </#if>
            </#list>
        </#if>
    </#escape>
</#macro>