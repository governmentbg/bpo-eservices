<#include "../../common/macros/label.ftl">
<#escape x as x?html>
    <tr>
        <td>
            <#if applicationType?ends_with("SURRENDER")>
                <h3 style="color: red"><@label "patent.surrender.declaration."+applicationType/></h3>
            <#elseif applicationType?ends_with("WITHDRAWAL")>
                <h3 style="color: red"><@label "patent.withdrawal.declaration."+applicationType/></h3>
            </#if>

            <#include "selectedUserdoc.ftl"/>

            <h3>
                <#if applicationType?ends_with("LICENCE")
                || applicationType?ends_with("EXTENDTERM")
                || applicationType == "SPC_REM"
                || applicationType == "SPC_BANKRUPTCY"
                || applicationType == "SPC_SECURITY"
                || applicationType == "SPC_GENERIC">
                    <@label "patent.eservices.title.label."+applicationType/>
                <#else>
                    <@label "patent.eservices.title.label."+mainApplicationType/>
                </#if>
            </h3>
            <#if esApplication.patentList?? && esApplication.patentList?size &gt; 0>
                <#assign pt = esApplication.patentList[0]/>
                <div>
                    <#if mainApplicationType == "SPC" || mainApplicationType == "SP">
                        <b><@label "patent.applicationNumber.label"/>: </b> ${'BG/S/' + pt.applicationNumber?substring(0,4) + '/' + pt.applicationNumber?substring(4,10)?remove_beginning('0')?remove_beginning('0')?remove_beginning('0')?remove_beginning('0')?remove_beginning('0')}
                    <#else>
                        <b><@label "patent.applicationNumber.label"/>: </b> ${pt.applicationNumber}
                    </#if>
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
<#--                <#if pt.currentStatusDate??>-->
<#--                    <div>-->
<#--                        <b><@label "patent.currentStatusDate.label"/>: </b> ${pt.currentStatusDate?string("dd.MM.yyyy")}-->
<#--                    </div>-->
<#--                </#if>-->
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
                    <div><b class="titleText"><@label "patent.applicants.label."+mainApplicationType/>: </b></div>
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
            </#if>
        </td>
    </tr>
</#escape>