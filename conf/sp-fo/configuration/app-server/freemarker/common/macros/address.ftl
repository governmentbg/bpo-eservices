<#macro address personAddresses personPhones personEmails personUrls>
    <#escape x as x?html>
        <#if personAddresses?? && personAddresses?size &gt; 0 >
            <#assign addr = personAddresses[0]>

            <div>
                <b><@label "address.country.label"></@label>:</b> ${addr.country}
            </div>
            <#if addr.state?? && addr.state != "">
                <div>
                    <#if addr.state?replace("GB.", "")?length ==2>
                        <b><@label "address.state.label"></@label>:</b> <@label "state."+addr.state></@label>
                    <#else>
                        <b><@label "address.state.label"></@label>:</b> ${addr.state}
                    </#if>
                </div>
            </#if>
            <div>
                <b><@label "address.address.label"></@label>:</b>
                <#if addr.street??>${addr.street},</#if>
                <#if addr.city??>${" "+addr.city},</#if>
                <#if addr.postCode??>${" "+addr.postCode}</#if>
            </div>
        </#if>
        <#if personPhones?? && personPhones?size &gt; 0>
            <#list personPhones as phone>
                <div>
                    <#if phone.kind?? && phone.kind == "Fax">
                        <b><@label "fax.label"></@label>:</b> ${phone.number}
                    <#else>
                        <b><@label "phone.label"></@label>:</b> ${phone.number}
                    </#if>
                </div>
            </#list>
        </#if>
        <#if personEmails?? && personEmails?size &gt; 0>
            <div>
                <b><@label "email.label"></@label>:</b> ${personEmails[0].emailAddress}
            </div>
        </#if>
        <#if personUrls?? && personUrls?size &gt; 0>
            <div>
                <b><@label "website.label"></@label>: </b> ${personUrls[0]}
            </div>
        </#if>
    </#escape>
</#macro>