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
    </head>

    <body>
    <#assign tmApplication = args[0] />
    <#assign mark = tmApplication.tradeMark />
    <#assign acceptedApplicationNumber = mark.applicationNumber!""/>
    <#assign acceptedApplicationDateStr = (mark.applicationDate??)?then(mark.applicationDate?string("dd.MM.yyyy HH:mm"), "")/>
    <#if args?size &gt; 4>
        <#assign forcedApplicationDateStr = (args[4]??)?then(args[4]?string("dd.MM.yyyy HH:mm:ss"), "")/>
    </#if>

    <table class="bordered receiptTable">
        <tbody>
        <tr>
            <td><img src="../common/img/Logo_BG_new.png"/></td>
        </tr>
        <tr>
            <td style="color: #0069d6" class="titleText">
                <@label "tmefiling.title.label" />
                <#if tmApplication.fastTrack?? && tmApplication.fastTrack == true>
                    (<@label "fasttrack.title.label"/>)
                <#else>
                    (<@label "regular.title.label"/>)
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <#if isDraft>
                    <@label "application.draft.message"/>
                <#elseif acceptedApplicationDateStr?? && acceptedApplicationDateStr != "">
                    <@label "accepted.application.message" tmApplication.applicationFilingNumber acceptedApplicationNumber acceptedApplicationDateStr/>
                <#elseif acceptedApplicationDateStr??  == false || acceptedApplicationDateStr == "">
                    <@label "filed.application.message" tmApplication.applicationFilingNumber/>
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
                <h3><@label "mark.title.label"/></h3>
                <div>
                    <b><@label "mark.type.label"/>:</b>
                    <#if mark.markKind?? && mark.markKind.value() != "Undefined">
                        <@label "mark.type."+mark.markKind.value()/>
                        <#if mark.markRightKind?? && (mark.markRightKind.value() == "Collective" || mark.markRightKind.value() == "Certificate")>
                            /<@label "mark.right."+mark.markRightKind.value()/>
                        </#if>
                    </#if>
                </div>
                <#if mark.soundRepresentations?? && mark.soundRepresentations?size &gt; 0 && mark.soundRepresentations[0].document??>
                    <div>
                        <b><@label "mark.sound.label"/>: </b>
                        ${mark.soundRepresentations[0].document.fileName}
                    </div>
                </#if>
                <#if mark.mediaRepresentations?? && mark.mediaRepresentations?size &gt; 0 && mark.mediaRepresentations[0].representation??>
                    <div>
                        <b><@label "mark.video.label"/>:</b>
                        ${mark.mediaRepresentations[0].representation.fileName}
                    </div>
                </#if>
                <#if mark.tradeMarkDocuments?? && mark.tradeMarkDocuments?size &gt; 0>
                    <div>
                        <b><@label "mark.rules.label"/>:</b>
                        <#assign docNames =  mark.tradeMarkDocuments?map(d -> d.document.fileName)/>
                        ${docNames?join(", ")}
                    </div>
                </#if>
                <#if mark.markKind?? && (mark.markKind.value() == "Other" || mark.markKind.value() == "Word")
                && mark.wordSpecifications?? && mark.wordSpecifications?size &gt; 0>
                    <div>
                        <b><@label "mark.name.label"/>:</b>
                        <span style="font-size: 14pt">${mark.wordSpecifications[0].wordElements}</span>
                    </div>
                </#if>
                <#if mark.markDescriptions?? && mark.markDescriptions?size &gt; 0>
                    <div>
                        <b><@label "mark.description.label"/>:</b>
                        ${mark.markDescriptions[0].value}
                    </div>
                </#if>
                <#if mark.imageSpecifications?? && mark.imageSpecifications?size &gt; 0 && mark.imageSpecifications[0].representation??>
                    <#if mark.imageSpecifications[0].colours?? && mark.imageSpecifications[0].colours?size &gt; 0>
                        <div>
                            <b><@label "mark.colours.label"/>:</b>
                            <#assign colours =  mark.imageSpecifications[0].colours?map(c -> c.value)/>
                            ${colours?join(", ")}
                        </div>
                    </#if>
                    <div>
                        <b><@label "mark.image.label"/>:</b>
                    </div>
                    <table>
                        <#list mark.imageSpecifications?chunk(4) as rowImgs>
                            <tr>
                                <#list rowImgs as markImg>
                                    <#if markImg.representation?? && markImg.representation.documentId?? >
                                        <img data-src="documentService" src="${markImg.representation.documentId}" width="150"/>
                                    </#if>
                                    <#if markImg.title??>
                                        <br/>
                                        ${markImg.title}
                                    </#if>
                                    <#if markImg.imageNumber??>
                                        <br/>
                                        <@label "image.number.label"/>: ${markImg.imageNumber}
                                    </#if>
                                </#list>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </td>
        </tr>

        <tr>
            <td>
                <h3><@label "priorities.title.label"/></h3>
                <#if mark.priorities?? && mark.priorities?size &gt; 0>
                    <#list mark.priorities as priority>
                        <div>
                            <b><@label "priority.number.label"/>: </b> ${priority.filingNumber}
                        </div>
                        <div>
                            <b><@label "priority.date.label"/>: </b> ${priority.filingDate?string("dd.MM.yyyy")}
                        </div>
                        <div>
                            <b><@label "priority.country.label"/>: </b> ${priority.filingOffice}
                        </div>
                        <#if priority.partialIndicator && priority.partialGoodsServices?? && priority.partialGoodsServices?size &gt; 0>
                            <div>
                                <b><@label "priority.partial.gs.label"/>: </b> <br/>
                                <#list priority.partialGoodsServices as gsPrio>
                                    <#if gsPrio.classNumber?? && gsPrio.classificationTerms?? && gsPrio.classificationTerms?size &gt; 0>
                                        ${gsPrio.classNumber}: ${gsPrio.classificationTerms[0].termText} <br/>
                                    </#if>
                                </#list>
                            </div>
                        </#if>
                        <div>
                            <b><@label "priority.documents.label"/>: </b> <br/>
                            <#if priority.attachedDocuments?? && priority.attachedDocuments?size &gt; 0>
                                <#assign docsNames = priority.attachedDocuments?map(d -> d.document.fileName)/>
                                ${docsNames?join(", ")}
                            <#else>
                                <@label "priority.documents.later.label"/>
                            </#if>
                        </div>
                    </#list>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "exhibitions.title.label"/></h3>
                <#if mark.exhibitionPriorities?? && mark.exhibitionPriorities?size &gt; 0>
                    <#list mark.exhibitionPriorities as exhibition>
                        <div>
                            <b><@label "exhibitions.name.label"/>: </b>
                            ${exhibition.exhibition.name}
                        </div>
                        <div>
                            <#if exhibition.firstDisplayDate??>
                                <b><@label "exhibitions.date.label"/>: </b>
                                ${exhibition.firstDisplayDate?string("dd.MM.yyyy")}
                            </#if>
                        </div>
                        <div>
                            <b><@label "exhibitions.documents.label"/>: </b>
                            <#if exhibition.attachedDocuments?? && exhibition.attachedDocuments?size &gt; 0>
                                <#assign docsNames = exhibition.attachedDocuments?map(d -> d.document.fileName)/>
                                ${docsNames?join(", ")}
                            <#else>
                                <@label "exhibitions.documents.later.label"/>
                            </#if>
                        </div>
                    </#list>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "transformation.title.label"/></h3>
                <#if mark.transformationPriorities?? && mark.transformationPriorities?size &gt; 0>
                    <#list mark.transformationPriorities as transformation>
                        <div>
                            <b><@label "transformation.type.label"/>: </b>
                            <@label "transformation.type."+transformation.transformationCountryCode />
                        </div>
                        <div>
                            <b><@label "transformation.registrationNumber.label"/>: </b>
                            ${transformation.registrationNumber}
                        </div>
                        <div>
                            <#if transformation.registrationDate??>
                                <b><@label "transformation.registrationDate.label"/>: </b>
                                ${transformation.registrationDate?string("dd.MM.yyyy")}
                            </#if>
                        </div>
                        <div>
                            <#if transformation.priorityDate??>
                                <b><@label "transformation.priorityDate.label"/>: </b>
                                ${transformation.priorityDate?string("dd.MM.yyyy")}
                            </#if>
                        </div>
                        <div>
                            <#if transformation.cancellationDate??>
                                <b><@label "transformation.cancellationDate.label"/>: </b>
                                ${transformation.cancellationDate?string("dd.MM.yyyy")}
                            </#if>
                        </div>
                    </#list>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "divisionalApplication.title.label"/></h3>
                <#if mark.divisionalApplicationDetails??>
                    <div>
                        <b><@label "divisionalApplication.number.label"/>: </b>
                        ${mark.divisionalApplicationDetails.initialApplicationNumber}
                    </div>
                    <div>
                        <b><@label "divisionalApplication.date.label"/>: </b>
                        ${mark.divisionalApplicationDetails.initialApplicationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "goodsservices.title.label"/></h3>
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
                            </#list><br/>
                            <#if cls.isFullClassCoverageIndicator()?? && cls.isFullClassCoverageIndicator() ==true>
                                <u><b><i><@label "goodsservices.fullCoverage.label"/></i></b></u>
                            </#if>
                        </div>
                    </#list>
                </#if>
            </td>
        </tr>
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
                <#if tmApplication.willPayOnline?? && tmApplication.willPayOnline>
                    <div>
                        <@label "willPayOnline.indicator.label"/>
                    </div>
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
            <td><@matchedFees tmMatchedFees "tmefiling"/></td>
        </tr>
        <tr>
            <td><@signatories mark.signatures![]/></td>
        </tr>
        <#if forcedApplicationDateStr?? && forcedApplicationDateStr != "">
            <tr>
                <td>
                    <b><@label "application.date.time"/>: ${forcedApplicationDateStr} </b>
                </td>
            </tr>
        <#elseif acceptedApplicationDateStr?? == false ||  acceptedApplicationDateStr =="">
            <tr>
                <td>
                    <b><@label "application.date.time"/>: ${.now?string("dd.MM.yyyy HH:mm:ss")} </b>
                </td>
            </tr>
        </#if>
        </tbody>
    </table>
    </body>
    </html>
</#escape>