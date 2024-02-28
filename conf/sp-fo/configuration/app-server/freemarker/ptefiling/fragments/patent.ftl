<#include "../../common/macros/label.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <h3>
                <@label "patent.efiling.title.label.SPC"/>
            </h3>
            <#if spcPatents?? && spcPatents?size &gt; 0>
                <#assign pt = spcPatents[0]/>
                <div>
                    <b><@label "patent.applicationNumber.label"/>: </b> ${pt.applicationNumber}
                </div>
                <#if pt.applicationDate??>
                    <div>
                        <b><@label "patent.applicationDate.label"/>: </b> ${pt.applicationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if pt.registrationNumber??>
                    <div>
                        <b><@label "patent.registrationNumber.label"/>: </b> ${pt.registrationNumber}
                    </div>
                </#if>
                <#if pt.registrationDate??>
                    <div>
                        <b><@label "patent.registrationDate.label"/>: </b> ${pt.registrationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if pt.currentStatus??>
                    <div>
                        <b><@label "patent.currentStatus.label"/>: </b> ${pt.currentStatus}
                    </div>
                </#if>
                <#if pt.currentStatusDate??>
                    <div>
                        <b><@label "patent.currentStatusDate.label"/>: </b> ${pt.currentStatusDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <div>
                    <b><@label "patent.eservices.title.label"/>: </b> ${pt.patentTitle}
                </div>
                <#if pt.patentTitleSecondLang??>
                    <div>
                        <b><@label "patent.patentTitleSecondLang.label"/>: </b> ${pt.patentTitleSecondLang}
                    </div>
                </#if>
                <#if pt.patentAbstract??>
                    <div>
                        <b><@label "patent.patentAbstract.label"/>: </b> ${pt.patentAbstract}
                    </div>
                </#if>
                <#if pt.patentAbstractSecondLang??>
                    <div>
                        <b><@label "patent.patentAbstractSecondLang.label"/>: </b> ${pt.patentAbstractSecondLang}
                    </div>
                </#if>

                <#if pt.applicants?? && pt.applicants?size &gt; 0>
                    <div><b class="titleText"><@label "patent.applicants.label.SPC"/></b></div>
                    <#list pt.applicants as applicant>
                        <div style="margin-top:5px; margin-left:20px">
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

                <#if pt.inventors?? && pt.inventors?size &gt; 0>
                    <div><b class="titleText"><@label "patent.inventors.label.SPC"/></b></div>
                    <#list pt.inventors as inventor>
                        <div style="margin-top:5px; margin-left:20px">
                            <div>
                                <b><@label "name.label"/>: </b>
                                <#if inventor.name??>
                                    <#if inventor.name.firstName??>${inventor.name.firstName + " "}</#if>
                                    <#if inventor.name.middleName??>${inventor.name.middleName + " "}</#if>
                                    <#if inventor.name.lastName??>${inventor.name.lastName}</#if>
                                </#if>
                                <#if inventor.personNumber??>
                                    (${inventor.personNumber})
                                <#elseif inventor.identifiers?? && inventor.identifiers?size &gt;0
                                && inventor.identifiers[0].value??>
                                    (${inventor.identifiers[0].value})
                                </#if>
                            </div>
                            <div>
                                <b><@label "address.label"/>: </b>
                                <#if inventor.addresses?? && inventor.addresses?size &gt; 0>
                                    <#if inventor.addresses[0].street??>${inventor.addresses[0].street + ". "}</#if>
                                    <#if inventor.addresses[0].city??>${inventor.addresses[0].city + " - "}</#if>
                                    <#if inventor.addresses[0].country??>${inventor.addresses[0].country + "."}</#if>
                                </#if>
                            </div>
                        </div>
                    </#list>
                </#if>

                <#if pt.ipcClasses?? && pt.ipcClasses?size &gt; 0>
                    <div><b class="titleText"><@label "patent.ipc.label.SPC"/></b></div>
                    <#list pt.ipcClasses as ipc>
                        <div style="margin-top:5px; margin-left:20px">
                            <#if ipc??>
                                ${ipc}
                            </#if>
                        </div>
                    </#list>
                </#if>

            </#if>
        </td>
    </tr>
</#escape>