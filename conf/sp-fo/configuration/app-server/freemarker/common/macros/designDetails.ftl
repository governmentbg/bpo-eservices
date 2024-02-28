<#macro designDetails selectedDesigns>
    <#escape x as x?html>
        <div style="margin-bottom: 10px">
            <h3><@label "design.application"/></h3>
            <#if selectedDesigns?? && selectedDesigns?size &gt; 0>
                <#assign designApplication = selectedDesigns[0]/>
                <#if designApplication.designDetails?? && designApplication.designDetails?size &gt; 0>
                    <#assign design = designApplication.designDetails[0]/>
                    <div style="margin-top:10px">
                        <b><@label "design.applicationNumber.label"/>: </b> ${designApplication.applicationNumber!""}
                    </div>
                    <#if designApplication.applicationDate??>
                        <div>
                            <b><@label "design.applicationDate.label"/>: </b> ${designApplication.applicationDate?string("dd.MM.yyyy")}
                        </div>
                    </#if>
                    <div>
                        <b><@label "design.registrationNumber.label"/>: </b> ${design.registrationNumber!""}
                    </div>
                    <#if design.registrationDate??>
                        <div>
                            <b><@label "design.registrationDate.label"/>: </b> ${design.registrationDate?string("dd.MM.yyyy")}
                        </div>
                    </#if>
                    <#if design.expiryDate??>
                        <div>
                            <b><@label "design.expiryDate.label"/>: </b> ${design.expiryDate?string("dd.MM.yyyy")}
                        </div>
                    </#if>
                    <#if design.currentStatus??>
                        <div>
                            <b><@label "design.currentStatus.label"/>: </b> <@label "design.currentStatus."+design.currentStatus/>
                        </div>
                    </#if>
                    <#if designApplication.applicants?? && designApplication.applicants?size &gt;0>
                        <div style="margin-top:10px"><b class="titleText"><@label "design.applicants.label"/>: </b></div>
                        <#list designApplication.applicants as applicant>
                            <div style="margin-top:5px; margin-left: 20px">
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
                                    ${applicant.domicileLocality}
                                </div>
                            </div>
                        </#list>
                    </#if>
                </#if>
            </#if>

            <#if selectedDesigns?? && selectedDesigns?size &gt; 0>
                <#list selectedDesigns as designApplication>
                    <#if designApplication.designDetails?? && designApplication.designDetails?size &gt; 0>
                        <#assign design = designApplication.designDetails[0]/>
                        <div style="margin-top: 20px">
                            <u class="titleText"><@label "design.number.label"/>: ${design.designIdentifier}</u>
                        </div>

                        <#if design.views?? && design.views?size &gt; 0 && design.views[0].view?? && design.views[0].view.document??>
                            <div style="margin-left: 250px;">
                                <#if design.views[0].view.document.documentId??>
                                    <img data-src="documentService" src="${design.views[0].view.document.documentId}" width="150" />
                                <#elseif design.views[0].view.document.uri??>
                                    <img src="${design.views[0].view.document.uri}" width="150" />
                                </#if>
                            </div>
                        </#if>

                        <#if design.productIndications?? && design.productIndications?size &gt;0>
                            <table class="bordered" style="margin-top:10px">
                                <tbody>
                                <tr>
                                    <td><b><@label "productsList.header.class"/></b></td>
                                    <td><b><@label "productsList.header.name"/></b></td>
                                </tr>
                                <#list design.productIndications as pi>
                                    <#if pi.classes?? && pi.classes?size &gt; 0>
                                        <#assign piDescription = pi.classes[0].description/>
                                        <#assign piClasses = pi.classes?map(cl -> cl.mainClass+"-"+cl.subClass)/>
                                        <tr>
                                            <td>
                                                ${piClasses?join("; ")}
                                            </td>
                                            <td>
                                                ${piDescription}
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                                </tbody>
                            </table>
                        </#if>
                    </#if>
                </#list>
            </#if>
        </div>
    </#escape>
</#macro>