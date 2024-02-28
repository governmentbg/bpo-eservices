<#macro fees feesList feeLabelPrefix>
    <#escape x as x?html>
        <h3><@label "fees.title.label"/></h3>
        <#assign total=0.0/>
        <#if feesList?? && feesList?size &gt; 0>
            <#assign count=feesList?size/>
            <#list feesList as fee>
                <#assign total = total + fee.amount/>
                <#if count &gt; 1>
                    <@label feeLabelPrefix+".layout.cart."+fee.feeType.nameKey/>: ${fee.amount?string("##0.00")} <br/>
                </#if>
            </#list>
        </#if>
        <div style="font-size: 12pt">
            <b><@label "fees.total.label"/>: ${total?string("##0.00")}</b>
        </div>
    </#escape>
</#macro>