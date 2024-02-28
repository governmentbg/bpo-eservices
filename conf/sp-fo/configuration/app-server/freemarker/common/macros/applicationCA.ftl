<#macro applicationCA correspondenceAddresses labelTitle currentUser currentUserEmail>
    <#include "address.ftl"/>

    <#escape x as x?html>
        <h3><@label labelTitle></@label></h3>
        <#if correspondenceAddresses?? && correspondenceAddresses?size &gt;0>
            <#if correspondenceAddresses[0].address?? && correspondenceAddresses[0].address?size &gt; 0>
                <div>
                    <b><@label "name.label"/>:</b> ${correspondenceAddresses[0].address[0].postalName}
                </div>
                <#assign emptyArray=[] />
                <@address correspondenceAddresses[0].address emptyArray emptyArray emptyArray />
            </#if>
            <#if currentUser?? && currentUser != "">
                <div>
                    <b><@label "currentUser.label"/>:</b>
                    ${currentUser}${(currentUserEmail?? && currentUserEmail != "")?then(", "+currentUserEmail, "")}
                </div>
                <div>
                    <@label "ecorrespondence.agree"/>
                </div>
            <#else>
                <#if correspondenceAddresses[0].email?? && correspondenceAddresses[0].email?size &gt; 0  && correspondenceAddresses[0].email[0] != "">
                    <div>
                        <b><@label "email.label"></@label>:</b> ${correspondenceAddresses[0].email[0]}
                    </div>
                </#if>
            </#if>
        </#if>
    </#escape>
</#macro>