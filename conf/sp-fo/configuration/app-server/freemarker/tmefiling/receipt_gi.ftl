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
    </head>

    <body>
    <#assign tmApplication = args[0] />
    <#assign mark = tmApplication.tradeMark />
    <#assign acceptedApplicationNumber = mark.applicationNumber!""/>
    <#assign acceptedApplicationDateStr = (mark.applicationDate??)?then(mark.applicationDate?string("dd.MM.yyyy HH:mm"), "")/>
    <#if args?size &gt; 5>
        <#assign esign = args[5] />
    </#if>
    <#if args?size &gt; 6>
        <#assign dateSigned = args[6]/>
    </#if>

    <table class="bordered receiptTable">
        <tbody>
        <tr>
            <td><img src="../common/img/Logo_BG_new.png"/></td>
        </tr>
        <tr>
            <td style="color: #0069d6" class="titleText">
                <@label "giefiling.title.label" />
            </td>
        </tr>
        <tr>
            <td>
                <#if esign?? == false>
                    <div><@label "application.not.signed.message"/></div>
                    <#if isDraft>
                        <div><@label "application.not.signed.draft.message"/></div>
                    </#if>
                <#else>
                    <#if acceptedApplicationDateStr?? && acceptedApplicationDateStr != "">
                        <@label "accepted.application.message" tmApplication.applicationFilingNumber acceptedApplicationNumber acceptedApplicationDateStr/>
                    <#elseif  isDraft == false>
                        <@label "filed.application.message" tmApplication.applicationFilingNumber/>
                    </#if>
                    <#if isDraft>
                        <div><@label "application.draft.message"/></div>
                    </#if>
                </#if>
            </td>
        </tr>
        <tr>
            <td><@applicants mark.applicants "applicants.title.label"></@applicants></td>
        </tr>
        <tr>
            <td><@representatives mark.representatives "representatives.title.label" false></@representatives></td>
        </tr>
        <tr>
            <td><@applicationCA mark.contactDetails "applicationCA.title.label" tmApplication.user!"" tmApplication.userEmail!""></@applicationCA></td>
        </tr>
        <tr>
            <td>
                <h3><@label "gi.title.label"/></h3>
                <div>
                    <b><@label "gi.type.label"/>:</b>
                    <#if mark.markKind?? && mark.markKind.value() != "Undefined">
                        <@label "mark.type."+mark.markKind.value()?replace(" ", "")/>
                    </#if>
                </div>
                <#if mark.markKind?? && mark.wordSpecifications?? && mark.wordSpecifications?size &gt; 0 && mark.wordSpecifications[0]?? && mark.wordSpecifications[0].wordElements??>
                    <div>
                        <b><@label "gi.name.label"/>:</b>
                        <span style="font-size: 14pt">${mark.wordSpecifications[0].wordElements}</span>
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "gi.goodsservices.title.label"/></h3>
                <#if mark.classDescriptions?? && mark.classDescriptions?size &gt; 0>
                    <#list mark.classDescriptions as cls>
                        <div>
                            <b><@label "goodsservices.class.label"/> ${cls.classNumber}</b> <br/>
                            <#list cls.classificationTerms as term>
                            <#if term.termAssessment ?? && term.termAssessment.verificationAssessment??>
                                <u><b>${term.termText}</b></u>
                            <#else>
                                ${term.termText}
                            </#if>
                            <#sep>;
                            </#list>
                        </div>
                    </#list>
                </#if>
            </td>
        </tr>
        <#if mark.markDescriptions?? && mark.markDescriptions?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "gi.description.label"/></h3>
                    <div>
                        <b><@label "gi.description.label.short"/>:</b>
                        ${mark.markDescriptions[0].value}
                    </div>
                </td>
            </tr>
        </#if>
        <tr>
            <td>
                <h3><@label "gi.goodsservices.description.title.label"/></h3>
                <#if mark.goodsCharacteristicsDescription?? >
                    <div>
                        <b><@label "gi.goodsservices.description.goodsCharacteristicsDescription.label"/>:</b>
                        ${mark.goodsCharacteristicsDescription}
                    </div>
                </#if>
                <#if mark.goodsProductionDescription?? >
                    <div>
                        <b><@label "gi.goodsservices.description.goodsProductionDescription.label"/>:</b>
                        ${mark.goodsProductionDescription}
                    </div>
                </#if>
                <#if mark.goodsGeographyDescription?? >
                    <div>
                        <b><@label "gi.goodsservices.description.goodsGeographyDescription.label"/>:</b>
                        ${mark.goodsGeographyDescription}
                    </div>
                </#if>
                <#if mark.goodsFactorsDescription?? >
                    <div>
                        <b><@label "gi.goodsservices.description.goodsFactorsDescription.label"/>:</b>
                        ${mark.goodsFactorsDescription}
                    </div>
                </#if>
            </td>
        </tr>
        <#if mark.foreignRegistrations?? && mark.foreignRegistrations?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "foreign.registration.title.label"/></h3>

                    <div>
                        <b><@label "foreign.registration.registrationNumber.label"/>:</b>
                        ${mark.foreignRegistrations[0].registrationNumber}
                    </div>
                    <div>
                        <b><@label "foreign.registration.registrationDate.label"/>:</b>
                        ${mark.foreignRegistrations[0].registrationDate?string("dd.MM.yyyy")}
                    </div>
                    <div>
                        <b><@label "foreign.registration.registrationCountry.label"/>:</b>
                        ${mark.foreignRegistrations[0].registrationCountry}
                    </div>
                </td>
            </tr>
        </#if>
        <tr>
            <td>
                <@documents tmApplication.documents "documents.title.label"/>
                <#if tmApplication.documents?? && tmApplication.documents?size &gt; 0>
                    <#if tmApplication.trueDocumentsIndicator?? && tmApplication.trueDocumentsIndicator>
                        <div>
                            <@label "documents.true.indicator.label"/>
                        </div>
                    </#if>
                </#if>
                <#if mark.comment??>
                    <div>
                        <b><@label "notes.label"/>: </b> ${mark.comment}
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "certificate.title.label"/></h3>
                <#if tmApplication.certificateRequestedIndicator??>
                    <div>
                        <@label "certificate.request.label"/>: <@label "certificate.request."+(tmApplication.certificateRequestedIndicator?string)/>
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <#assign tmMatchedFees = ( tmApplication.payments?? &&  tmApplication.payments?size &gt;0)?then( tmApplication.payments[0].fees![], [])/>
            <td><@matchedFees tmMatchedFees "giefiling"/></td>
        </tr>
        <tr>
            <td><@signatories mark.signatures![]/></td>
        </tr>
        <#if esign??>
            <tr>
                <td>
                    <@esignature esign dateSigned/>
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