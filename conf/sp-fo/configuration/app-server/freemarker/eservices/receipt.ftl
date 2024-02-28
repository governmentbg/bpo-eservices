<#ftl encoding="UTF-8" />
<#escape x as x?html>
    <html>
    <head>
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
        <#include "../common/css/styles.ftl">
        <#include "../common/macros/label.ftl">
        <#include "../common/macros/applicants.ftl">
        <#include "../common/macros/representatives.ftl">
        <#include "../common/macros/applicationCA.ftl">
        <#include "../common/macros/documents.ftl">
        <#include "../common/macros/matchedFees.ftl">
        <#include "../common/macros/signatories.ftl">
        <#include "../common/macros/esignature.ftl">
        <#include "../common/macros/designDetails.ftl">
    </head>

    <body>
    <#assign esApplication = args[0] />
    <#assign mainApplicationType = (esApplication.applicationType!"xx")?substring(0,2) />
    <#assign applicationType = esApplication.applicationType!"" />
    <#assign applicationId = esApplication.applicationNumber!""/>
    <#assign applicationDateStr = (esApplication.applicationDate??)?then(esApplication.applicationDate?string("dd.MM.yyyy HH:mm"), "")/>
    <#assign applicationNumber = esApplication.applicationFilingNumber!""/>
    <#if args?size &gt; 5>
        <#assign esign = args[5]/>
    </#if>
    <#if args?size &gt; 6>
        <#assign dateSigned = args[6]/>
    </#if>


    <table class="bordered receiptTable">
        <tbody>
        <tr>
            <td><img src="../common/img/Logo_BG_new.png"/></td>
        </tr>

        <#include "fragments/heading.ftl"/>

        <#include "fragments/applicants.ftl"/>

        <#if !applicationType?ends_with("PROVIDEPOW") && !applicationType?ends_with("PAIDTAXES") >
            <#include "fragments/representatives.ftl"/>
            <#include "fragments/applicationCA.ftl"/>
        </#if>

<#--        <#if applicationType == "SPC_EXTENDTERM" >-->
<#--            <#include "fragments/marketPermission.ftl"/>-->
<#--        </#if>-->

        <#if mainApplicationType == "TM">
            <#include "fragments/mark.ftl"/>
            <#include "fragments/gshelper.ftl"/>
        <#elseif mainApplicationType == "DS">
            <#include "fragments/designs.ftl"/>
        <#elseif mainApplicationType == "PT" || mainApplicationType == "UM" || mainApplicationType == "EP" || mainApplicationType == "SV" || mainApplicationType == "SP">
            <#include "fragments/patent.ftl"/>
        </#if>

        <#include "fragments/oppositionGrounds.ftl"/>

        <#include "fragments/partialInvalidity.ftl"/>

        <!-- TODO oppo/invalidity stuff - margins and stuff, overall look improve, declarations - red and so on-->

        <#include "fragments/appeal.ftl"/>

        <#include "fragments/declarations.ftl"/>

        <#include "fragments/licence.ftl"/>

        <#include "fragments/securityMeasure.ftl"/>

        <#include "fragments/assignees.ftl"/>

        <#include "fragments/holders.ftl"/>

        <tr>
            <td>
                <@documents esApplication.documents![] "documents.eservices.title.label"/>
                <#if esApplication.processInitiatedBeforePublication?? && esApplication.processInitiatedBeforePublication == true>
                    <div>
                        <b><@label "application.processInitiatedBeforePublication.label"/></b>
                    </div>
                </#if>

            </td>
        </tr>
        <#if esApplication.pagesCountAttachment?? && esApplication.pagesCountAttachment != 0>
        <tr>
            <td>
                <div>
                    <h3><@label "attachments.eservices.pagesCount"/></h3>
                    <div>${esApplication.pagesCountAttachment}</div>
                </div>
            </td>
        </tr>
        </#if>
        <tr>
            <td>
                <div>
                    <h3><@label "notes.eservices.label"/></h3>
                    <#if esApplication.comment??>
                        <div>${esApplication.comment}</div>
                    </#if>
                </div>
            </td>
        </tr>
        <tr>
            <#assign esMatchedFees = (esApplication.payments?? && esApplication.payments?size &gt;0)?then(esApplication.payments[0].fees![], [])/>
            <td><@matchedFees esMatchedFees "eservices"/></td>
        </tr>
        <tr>
            <td><@signatories esApplication.signatures![]/></td>
        </tr>

        <#if esign??>
            <tr>
                <td>
                    <@esignature esign dateSigned/>
                </td>
            </tr>
        </#if>
        <#if esApplication.esignDocDeclaration?? && esApplication.esignDocDeclaration == true>
            <tr>
                <td>
                    <h3><i><u><@label "esignDocDeclaration.label"/></u></i></h3>
                </td>
            </tr>
        </#if>
        <#if dateSigned??>
            <tr>
                <td class="titleText">
                    <b><@label "application.date.time"/>: ${dateSigned?string("dd.MM.yyyy HH:mm:ss")} </b>
                </td>
            </tr>
        </#if>

        </tbody>
    </table>

    </body>
    </html>
</#escape>