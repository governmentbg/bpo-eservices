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
    <#assign ptApplication = args[0] />
    <#assign patent = ptApplication.patent />
    <#assign spcPatents = ptApplication.spcPatents![] />
    <#assign technicalQuestionnaire = ptApplication.technicalQuestionnaire />
    <#assign applicationId = patent.applicationNumber!""/>
    <#assign applicationDateStr = (patent.applicationDate??)?then(patent.applicationDate?string("dd.MM.yyyy HH:mm"), "")/>
    <#assign applicationNumber = ptApplication.applicationFilingNumber!""/>
    <#assign applicationReferenceNumber = ptApplication.applicationReferenceNumber!""/>
    <#assign applicationReferenceDateStr = (ptApplication.applicationReferenceDate??)?then(ptApplication.applicationReferenceDate?string("dd.MM.yyyy HH:mm"), "")/>
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
                <#if ptApplication.applicationType != "EP_EFILING">
                    <@label "ptefiling.applicationType." + ptApplication.applicationType />
                <#elseif ptApplication.applicationKind??>
                    <br/><@label "ptefiling.applicationKind." + ptApplication.applicationKind?replace(" ", "") />
                </#if>
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
                    <#if ptApplication.applicationType != "EP_EFILING">
                        <#if applicationDateStr?? && applicationDateStr != "">
                            <@label "accepted.application.message" applicationNumber applicationId applicationDateStr/>
                        <#elseif isDraft == false>
                            <@label "filed.application.message" applicationNumber />
                        </#if>
                    <#else>
                        <#if applicationReferenceDateStr?? && applicationReferenceDateStr != "">
                            <@label "accepted.application.message" applicationNumber applicationReferenceNumber applicationReferenceDateStr/>
                        <#elseif isDraft == false>
                            <@label "filed.application.message" applicationNumber />
                        </#if>
                    </#if>
                    <#if isDraft>
                        <div><@label "application.draft.message"/></div>
                    </#if>
                </#if>
            </td>
        </tr>
        <tr>
            <td><@applicants patent.applicants "applicants.title.label"></@applicants></td>
        </tr>
        <tr>
            <td><@representatives patent.representatives "representatives.title.label" ptApplication.applicationType == "EP_EFILING"></@representatives></td>
        </tr>
        <tr>
            <td><@applicationCA patent.contactDetails "applicationCA.title.label" ptApplication.user!"" ptApplication.userEmail!""></@applicationCA></td>
        </tr>

        <#if ptApplication.applicationType == "SPC_EFILING">
            <#include "fragments/patent.ftl"/>
        </#if>

        <tr>
            <td>
                <h3><@label "patent.section.title.label."+ptApplication.applicationType /></h3>
                <#if patent.svKind??>
                    <div>
                        <b><@label "patent.svkind.label"/>: </b> <@label patent.svKind.code/>
                    </div>
                </#if>
                <#if patent.regKind??>
                    <div>
                        <b><@label "patent.regkind.label"/>: </b> <@label patent.regKind.code/>
                    </div>
                </#if>

                <#if patent.firstPermissionBGNumber?? && patent.firstPermissionBGDate??>
                    <div>
                        <b><@label "patent.firstPermissionBG.label"/>: </b> ${patent.firstPermissionBGNumber} / ${patent.firstPermissionBGDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if patent.firstPermissionBGNotificationDate??>
                    <div>
                        <b><@label "patent.firstPermissionBGNotificationDate.label"/>: </b> ${patent.firstPermissionBGNotificationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if patent.firstPermissionEUNumber?? && patent.firstPermissionEUDate??>
                    <div>
                        <b><@label "patent.firstPermissionEU.label"/>: </b> ${patent.firstPermissionEUNumber} / ${patent.firstPermissionEUDate?string("dd.MM.yyyy")}
                    </div>
                </#if>

                <#if patent.applicationNumber??>
                    <div>
                        <b><@label "patent.applicationNumber.label"/>: </b> ${patent.applicationNumber}
                    </div>
                </#if>
                <#if patent.registrationNumber??>
                    <div>
                        <b><@label "patent.registrationNumber.label"/>: </b> ${patent.registrationNumber}
                    </div>
                </#if>
                <#if patent.applicationDate??>
                    <div>
                        <b><@label "patent.applicationDate.label"/>: </b> ${patent.applicationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if patent.registrationDate??>
                    <div>
                        <b><@label "patent.registrationDate.label"/>: </b> ${patent.registrationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if patent.registrationPublicationDate??>
                    <div>
                        <b><@label "patent.registrationPublicationDate.label"/>: </b> ${patent.registrationPublicationDate?string("dd.MM.yyyy")}
                    </div>
                </#if>
                <#if patent.patentTitle??>
                    <div>
                        <#if ptApplication.applicationType != "SPC_EFILING">
                            <b><@label "patent.title.label"/>: </b> <span class="titleText">${patent.patentTitle}</span>
                        <#else>
                            <b><@label "patent.title.label.spc"/>: </b> <span class="titleText">${patent.patentTitle}</span>
                        </#if>
                    </div>
                </#if>
                <#if patent.titleTransliteration??>
                    <div>
                        <b><@label "patent.titleTransliteration.label"/>: </b> <span class="titleText">${patent.titleTransliteration}</span>
                    </div>
                </#if>
                <#if patent.latinClassification??>
                    <div>
                        <b><@label "patent.latinClassification.label"/>: </b> <span class="titleText">${patent.latinClassification}</span>
                    </div>
                </#if>
                <#if patent.patentTitleSecondLang??>
                    <div>
                        <#if ptApplication.applicationType != "SPC_EFILING">
                            <b><@label "patent.patentTitleSecondLang.label"/>: </b> ${patent.patentTitleSecondLang}
                        <#else>
                            <b><@label "patent.patentTitleSecondLang.label.spc"/>: </b> ${patent.patentTitleSecondLang}
                        </#if>
                    </div>
                </#if>
                <#if patent.patentAbstract??>
                    <div>
                        <b><@label "patent.patentAbstract.label"/>: </b> ${patent.patentAbstract}
                    </div>
                </#if>
                <#if patent.patentAbstractSecondLang??>
                    <div>
                        <b><@label "patent.patentAbstractSecondLang.label"/>: </b> ${patent.patentAbstractSecondLang}
                    </div>
                </#if>
                <#if patent.patentDescriptions?? && patent.patentDescriptions?size &gt; 0>
                    <div>
                        <b><@label "patent.patentDescriptions.label"/>: </b>
                        <#assign patentDescriptions =  patent.patentDescriptions?map(d -> d.document.fileName)/>
                        ${patentDescriptions?join("; ")}
                    </div>
                </#if>
                <#if patent.patentClaims?? && patent.patentClaims?size &gt; 0>
                    <div>
                        <b><@label "patent.patentClaims.label"/>: </b>
                        <#assign patentClaims =  patent.patentClaims?map(d -> d.document.fileName)/>
                        ${patentClaims?join("; ")}
                    </div>
                </#if>
                <#if patent.patentDrawings?? && patent.patentDrawings?size &gt; 0>
                    <div>
                        <b><@label "patent.patentDrawings.label"/>: </b>
                        <#assign patentDrawings =  patent.patentDrawings?map(d -> d.document.fileName)/>
                        ${patentDrawings?join("; ")}
                    </div>
                </#if>
                <#if patent.sequencesListing?? && patent.sequencesListing?size &gt; 0>
                    <div>
                        <b><@label "patent.sequencesListing.label"/>: </b>
                        <#assign sequencesListing =  patent.sequencesListing?map(d -> d.document.fileName)/>
                        ${sequencesListing?join("; ")}
                    </div>
                </#if>
                <#if patent.pagesCount??>
                    <div>
                        <b>
                            <#if ptApplication.applicationType == "EP_EFILING"><@label "patent.pagesCount.label.EP"/>
                            <#else><@label "patent.pagesCount.label"/>
                            </#if>:
                        </b> ${patent.pagesCount}
                    </div>
                </#if>
                <#if patent.claimsCount??>
                    <div>
                        <b><@label "patent.claimsCount.label"/>: </b> ${patent.claimsCount}
                    </div>
                </#if>
                <#if patent.independentClaimsCount??>
                    <div>
                        <b><@label "patent.independentClaimsCount.label"/>: </b> ${patent.independentClaimsCount}
                    </div>
                </#if>
                <#if patent.drawingsCount??>
                    <div>
                        <b><@label "patent.drawingsCount.label"/>: </b> ${patent.drawingsCount}
                    </div>
                </#if>
                <#if patent.drawingNumber??>
                    <div>
                        <b><@label "patent.drawingNumber.label"/>: </b> ${patent.drawingNumber}
                    </div>
                </#if>
            </td>
        </tr>

        <#assign picSize = "small"/>
        <#if picSize == "small">
            <#assign picChunk =4 />
            <#assign picWidth =150 />
        <#elseif picSize == "medium">
            <#assign picChunk =2 />
            <#assign picWidth =305 />
        <#else>
            <#assign picChunk =1/>
            <#assign picWidth =610/>
        </#if>
        <#if patent.patentViews?? && patent.patentViews?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "images.title.label"/></h3>
                    <table class="views-table">
                        <tbody>
                            <#if patent.patentViews?? && patent.patentViews?size &gt; 0>
                                <div>
                                    <#list patent.patentViews?chunk(picChunk) as viewChunkList>
                                        <tr>
                                            <#list viewChunkList as view>
                                                <td width="${picWidth}">
                                                    <img data-src="documentService"
                                                         src="${view.view.document.documentId}" width="${picWidth}"/>
                                                </td>
                                            </#list>
                                        </tr>
                                    </#list>
                                </div>
                            </#if>
                        </tbody>
                    </table>
                </td>
            </tr>
        </#if>
        <#if technicalQuestionnaire?? && ptApplication.applicationType == "SV_EFILING">
            <tr>
                <td>
                    <h3><@label "patent.section.technical.questionnaire.label" /></h3>
                    <#if technicalQuestionnaire.originSupportReproductionAbstract??>
                        <div style="margin-bottom: 10px">
                            <b><@label "technical.questionnaire.originSupportReproductionAbstract.label"/>: </b> ${technicalQuestionnaire.originSupportReproductionAbstract}
                        </div>
                    </#if>
                    <#if technicalQuestionnaire.testConditionsAbstract??>
                        <div style="margin-bottom: 10px">
                            <b><@label "technical.questionnaire.testConditionsAbstract.label"/>: </b> ${technicalQuestionnaire.testConditionsAbstract}
                        </div>
                    </#if>
                    <#if technicalQuestionnaire.characteristicsAbstract??>
                        <div style="margin-bottom: 10px">
                            <b><@label "technical.questionnaire.characteristicsAbstract.label"/>: </b> ${technicalQuestionnaire.characteristicsAbstract}
                        </div>
                    </#if>
                    <#if technicalQuestionnaire.resistanceAbstract??>
                        <div>
                            <b><@label "technical.questionnaire.resistanceAbstract.label"/>: </b> ${technicalQuestionnaire.resistanceAbstract}
                        </div>
                    </#if>
                </td>
            </tr>
        </#if>

        <#if patent.priorities?? && patent.priorities?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "priorities.title.label"/></h3>
                    <#list patent.priorities as priority>
                        <div>
                            <b><@label "priority.number.label"/>: </b> ${priority.filingNumber}
                        </div>
                        <div>
                            <b><@label "priority.date.label"/>: </b> ${priority.filingDate?string("dd.MM.yyyy")}
                        </div>
                        <div>
                            <b><@label "priority.country.label"/>: </b> ${priority.filingOffice}
                        </div>
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
                </td>
            </tr>
        </#if>
        <#if patent.exhibitions?? && patent.exhibitions?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "patent.exhibitions.title.label"/></h3>

                    <#list patent.exhibitions as exhibition>
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
                    </#list>
                </td>
            </tr>
        </#if>
        <#if patent.pcts?? && patent.pcts?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "pct.title.label"/></h3>

                    <#list patent.pcts as pct>
                        <div>
                            <b><@label "pct.applicationNumber.label"/>: </b> ${pct.applicationNumber}
                        </div>
                        <div>
                            <b><@label "pct.applicationDate.label"/>: </b> ${pct.applicationDate?string("dd.MM.yyyy")}
                        </div>
                        <div>
                            <b><@label "pct.publicationNumber.label"/>: </b> ${pct.publicationNumber}
                        </div>
                        <div>
                            <b><@label "pct.publicationDate.label"/>: </b> ${pct.publicationDate?string("dd.MM.yyyy")}
                        </div>
                        <#if pct.payedFees?? && pct.payedFees == true>
                            <div>
                                <@label "pct.payedFees.label"/>
                            </div>
                        </#if>
                    </#list>
                </td>
            </tr>
        </#if>
        <#if patent.divisionalApplicationDetails?? && patent.divisionalApplicationDetails?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "divisionalApplication.title.label."+ptApplication.applicationType/></h3>

                    <#list patent.divisionalApplicationDetails as divisional>
                        <div>
                            <b><@label "divisionalApplication.number.label"/>: </b>
                            ${divisional.initialApplicationNumber}
                        </div>
                        <div>
                            <b><@label "divisionalApplication.date.label"/>: </b>
                            ${divisional.initialApplicationDate?string("dd.MM.yyyy")}
                        </div>
                    </#list>
                </td>
            </tr>
        </#if>
        <#if patent.transformationPriorities?? && patent.transformationPriorities?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "transformation.title.label"/></h3>
                    <#list patent.transformationPriorities as transformation>
                        <div>
                            <b><@label "patent.transformation.type."+transformation.countryCode /></b>
                        </div>
                        <div>
                            <b><@label "patent.transformation.applicationNumber.label."+transformation.countryCode/>: </b>
                            ${transformation.applicationNumber}
                        </div>
                        <div>
                            <b><@label "patent.transformation.applicationDate.label."+transformation.countryCode/>: </b>
                            ${transformation.applicationDate?string("dd.MM.yyyy")}
                        </div>
                        <div>
                            <b><@label "patent.transformation.publicationNumber.label."+transformation.countryCode/>: </b>
                            ${transformation.publicationNumber}
                        </div>
                        <div>
                            <b><@label "patent.transformation.publicationDate.label."+transformation.countryCode/>: </b>
                            ${transformation.publicationDate?string("dd.MM.yyyy")}
                        </div>
                        <#if transformation.payedFees?? && transformation.payedFees == true>
                            <div>
                                <@label "patent.transformation.payedFees.label"/>
                            </div>
                        </#if>
                    </#list>
                </td>
            </tr>
        </#if>
        <#if patent.parallelApplications?? && patent.parallelApplications?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "parallelApplication.title.label"/></h3>
                    <#list patent.parallelApplications as parallel>
                        <div>
                            <b><@label "parallelApplication.type."+parallel.countryCode /></b>
                        </div>
                        <div>
                            <b><@label "parallelApplication.applicationNumber.label."+parallel.countryCode/>: </b>
                            ${parallel.applicationNumber}
                        </div>
                        <div>
                            <b><@label "parallelApplication.applicationDate.label."+parallel.countryCode/>: </b>
                            ${parallel.applicationDate?string("dd.MM.yyyy")}
                        </div>
                        <div>
                            <b><@label "parallelApplication.publicationNumber.label."+parallel.countryCode/>: </b>
                            ${parallel.publicationNumber}
                        </div>
                        <div>
                            <b><@label "parallelApplication.publicationDate.label."+parallel.countryCode/>: </b>
                            ${parallel.publicationDate?string("dd.MM.yyyy")}
                        </div>
                    </#list>
                </td>
            </tr>
        </#if>

        <#if ptApplication.applicationType != "SPC_EFILING" && ptApplication.applicationType != "IS_EFILING">
            <tr>
                <td>
                    <#if ptApplication.applicationType != "SV_EFILING">
                        <h3><@label "inventors.title.label"></@label></h3>
                    <#else>
                        <h3><@label "authors.title.label"></@label></h3>
                    </#if>
                    <#if ptApplication.inventorsAreReal?? && ptApplication.inventorsAreReal>
                        <#if ptApplication.applicationType != "SV_EFILING">
                            <div><b><@label "inventors.inventorsAreReal.label"/></b></div>
                        <#else>
                            <div><b><@label "inventors.authorsAreReal.label"/></b></div>
                        </#if>
                    </#if>
                    <#if ptApplication.inventorsAreRealDocuments?? && ptApplication.inventorsAreRealDocuments?size &gt; 0>
                        <div style="margin-bottom: 10px">
                            <#if ptApplication.applicationType != "SV_EFILING">
                                <b><@label "inventors.inventorsAreRealDocuments.label"/>: </b>
                            <#else>
                                <b><@label "inventors.authorsAreRealDocuments.label"/>: </b>
                            </#if>
                            <#assign  realDocs = ptApplication.inventorsAreRealDocuments?map(d -> d.document.fileName)/>
                            ${realDocs?join("; ")}
                        </div>
                    </#if>
                    <#if patent.inventors?? && patent.inventors?size &gt; 0 >
                        <#list patent.inventors as author>
                            <div>
                                <b><@label "name.label"></@label>:</b>
                                <#if author.name??>
                                    ${author.name.firstName!""}
                                    <#if author.name.middleName??>${" "+author.name.middleName}</#if>
                                    <#if author.name.lastName??>${" "+author.name.lastName}</#if>
                                </#if>
                            </div>
                            <#if author.nationality??>
                                <div>
                                    <b><@label "nationality.label"/>: </b>
                                    ${author.nationality}
                                </div>
                            </#if>
                            <div style="margin-bottom: 10px">
                                <#assign emptyArray =[]/>
                                <@address author.addresses![] emptyArray emptyArray emptyArray></@address>
                            </div>
                        </#list>
                    </#if>
                </td>
            </tr>
        </#if>

        <#if ptApplication.applicationType != "SPC_EFILING" && ptApplication.applicationType != "IS_EFILING">
            <tr>
                <td>
                    <h3><@label "additional.info.title.label" /></h3>
                    <#if ptApplication.smallCompany?? && ptApplication.smallCompany == true>
                        <div><b><@label "smallCompany.label"></@label></b></div>
                        <div>
                            <b><@label "smallCompanyDocuments.label"/>: </b>
                            <#assign smallCompDocs = ptApplication.smallCompanyDocuments?map(d -> d.document.fileName)/>
                            ${smallCompDocs?join("; ")}
                        </div>
                    </#if>
                    <#if ptApplication.defermentOfPublication?? && ptApplication.defermentOfPublication == true>
                        <div><b><@label "defermentOfPublication.label"></@label></b></div>
                        <div>
                            <b><@label "defermentOfPublicationDocuments.label"/>: </b>
                            <#assign deferDocs = ptApplication.defermentOfPublicationDocuments?map(d -> d.document.fileName)/>
                            ${deferDocs?join("; ")}
                        </div>
                    </#if>
                    <#if ptApplication.anticipationOfPublication?? && ptApplication.anticipationOfPublication == true>
                        <div><b><@label "anticipationOfPublication.label"></@label></b></div>
                    </#if>
                    <#if ptApplication.licenceAvailability?? && ptApplication.licenceAvailability == true>
                        <div><b><@label "licenceAvailability.label"></@label></b></div>
                    </#if>
                    <#if ptApplication.classifiedForNationalSecurity?? && ptApplication.classifiedForNationalSecurity == true>
                        <div><b><@label "classifiedForNationalSecurity.label"></@label></b></div>
                    </#if>
                    <#if ptApplication.classifiedForDefense?? && ptApplication.classifiedForDefense == true>
                        <div><b><@label "classifiedForDefense.label"></@label></b></div>
                    </#if>
                    <#if ptApplication.epoDecisionCopy?? && ptApplication.epoDecisionCopy == true>
                        <div><b><@label "epoDecisionCopy.label"></@label></b></div>
                    </#if>
                    <#if ptApplication.epoTransferChangeForm?? && ptApplication.epoTransferChangeForm == true>
                        <div><b><@label "epoTransferChangeForm.label"></@label></b></div>
                    </#if>
                </td>
            </tr>
        </#if>

        <#if ptApplication.entitlement?? && ptApplication.entitlement.kind??>
            <tr>
                <td>
                    <h3><@label "entitlementTitle.label"/></h3>
                    <#if ptApplication.applicationType != "SV_EFILING">
                        <div><b><@label "entitlementKind."+ ptApplication.entitlement.kind/></b></div>
                    <#else>
                        <div><b><@label "sv.entitlementKind."+ ptApplication.entitlement.kind/></b></div>
                    </#if>
                    <div>${ptApplication.entitlement.description!""}</div>
                </td>
            </tr>
        </#if>

        <#if ptApplication.certificateRequestedIndicator??>
            <tr>
                <td>
                    <h3><@label "certificate.title.label"/></h3>
                    <div>
                        <@label "certificate.request.label"/>
                        : <@label "certificate.request."+(ptApplication.certificateRequestedIndicator?string)/>
                    </div>
                </td>
            </tr>
        </#if>

        <tr>
            <td>
                <@documents ptApplication.documents "documents.title.label"/>
                <#if ptApplication.comment??>
                    <div>
                        <b><@label "notes.label"/>: </b> ${ptApplication.comment}
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <#assign ptMatchedFees = (ptApplication.payments?? && ptApplication.payments?size &gt;0)?then(ptApplication.payments[0].fees![], [])/>
            <td><@matchedFees ptMatchedFees "ptefiling"/></td>
        </tr>
        <tr>
            <td><@signatories ptApplication.signatures![]/></td>
        </tr>
        <#if esign??>
            <tr>
                <td>
                    <@esignature esign dateSigned/>
                </td>
            </tr>
        </#if>
        <#if ptApplication.esignDocDeclaration?? && ptApplication.esignDocDeclaration == true>
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