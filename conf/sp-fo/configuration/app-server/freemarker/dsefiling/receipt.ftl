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
    <#assign dsApplication = args[0] />
    <#assign acceptedApplicationNumber = dsApplication.applicationNumber!""/>
    <#assign acceptedApplicationDateStr = (dsApplication.applicationDate??)?then(dsApplication.applicationDate?string("dd.MM.yyyy HH:mm"), "")/>
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
                <@label "dsefiling.title.label" />
                <#if dsApplication.fastTrack?? && dsApplication.fastTrack == true>
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
                    <@label "accepted.application.message" dsApplication.applicationFilingNumber acceptedApplicationNumber acceptedApplicationDateStr/>
                <#elseif acceptedApplicationDateStr?? == false || acceptedApplicationDateStr == "">
                    <@label "filed.application.message" dsApplication.applicationFilingNumber/>
                </#if>
            </td>
        </tr>
        <tr>
            <td><@applicants dsApplication.applicants "applicants.title.label"></@applicants></td>
        </tr>
        <tr>
            <td><@representatives dsApplication.representatives "representatives.title.label" false></@representatives></td>
        </tr>
        <tr>
            <td><@applicationCA dsApplication.contactDetails "applicationCA.title.label" dsApplication.user!"" dsApplication.userEmail!""></@applicationCA></td>
        </tr>
        <tr>
            <td>
                <div class="titleText">
                    <@label "designCount.label"/>: ${(dsApplication.designDetails??)?then(dsApplication.designDetails?size, 0)}
                </div>
                <#if dsApplication.applicationVerbalElementsEn??>
                    <div class="titleText">
                        <@label "designApplicationName.label"/>: ${dsApplication.applicationVerbalElementsEn}
                    </div>
                </#if>
                <#if dsApplication.designDetails?? && dsApplication.designDetails?size &gt;0>
                    <#assign designsWithDescr = dsApplication.designDetails?filter(d -> d.description??  && d.description != "") />
                    <#if designsWithDescr?size &gt; 0>
                        <div>
                            <b><@label "designDescriptions.label"/></b>
                            <#list designsWithDescr as dsWithDescr>
                                <div>
                                    <b>${dsWithDescr.designIdentifier}: </b> ${dsWithDescr.description}
                                </div>
                            </#list>
                        </div>
                    </#if>
                </#if>
                <#if dsApplication.designDetails?? && dsApplication.designDetails?size &gt;0>
                    <#assign designsWithTitleEn = dsApplication.designDetails?filter(d -> d.verbalElementsEn??  && d.verbalElementsEn != "") />
                    <#if designsWithTitleEn?size &gt; 0>
                        <div>
                            <b><@label "designVerbalElementsEn.label"/></b>
                            <#list designsWithTitleEn as dsWithTitleEn>
                                <div>
                                    <b>${dsWithTitleEn.designIdentifier}: </b> ${dsWithTitleEn.verbalElementsEn}
                                </div>
                            </#list>
                        </div>
                    </#if>
                </#if>
            </td>
        </tr>
        <#if dsApplication.designDetails?? && dsApplication.designDetails?size &gt;0>
            <#assign designsDeferred = dsApplication.designDetails?filter(d -> d.publicationDefermentIndicator??  && d.publicationDefermentIndicator ==true) />
            <#if designsDeferred?size &gt; 0>
                <#assign deferred = designsDeferred?map(d -> d.designIdentifier)/>
                <tr>
                    <td>
                        <h3><@label "deferredPublications.label"/></h3>
                        <div>
                            <b><@label "deferredPublicationDesigns.label"/>:</b>
                            ${deferred?join("; ")}
                        </div>
                    </td>
                </tr>
            </#if>
        </#if>
        <tr>
            <td>
                <h3><@label "priorities.title.label"/></h3>
                <#if dsApplication.priorities?? && dsApplication.priorities?size &gt; 0>
                    <#list dsApplication.priorities as priority>
                        <div>
                            <b><@label "priority.number.label"/>: </b> ${priority.filingNumber}
                        </div>
                        <div>
                            <b><@label "priority.date.label"/>: </b> ${priority.filingDate?string("dd.MM.yyyy")}
                        </div>
                        <div>
                            <b><@label "priority.country.label"/>: </b> ${priority.filingOffice}
                        </div>
                        <#if priority.designs?? && priority.designs?size &gt; 0>
                            <div>
                                <b><@label "priority.designs.label"/>: </b>
                                <#assign prioDesigns = priority.designs?map(d -> d.designIdentifier)/>
                                ${prioDesigns?join("; ")}
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
                <#elseif dsApplication.priorityClaimLaterIndicator?? && dsApplication.priorityClaimLaterIndicator == true>
                    <b><@label "priority.claimLater.label"/></b>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "divisionalApplication.title.label"/></h3>
                <#if dsApplication.divisionalApplicationDetails??>
                    <div>
                        <b><@label "divisionalApplication.number.label"/>: </b>
                        ${dsApplication.divisionalApplicationDetails.initialApplicationNumber}
                    </div>
                    <div>
                        <b><@label "divisionalApplication.date.label"/>: </b>
                        ${dsApplication.divisionalApplicationDetails.initialApplicationDate?string("dd.MM.yyyy")}
                    </div>
                    <#if dsApplication.divisionalApplicationDetails.designs?? && dsApplication.divisionalApplicationDetails.designs?size &gt; 0>
                        <div>
                            <b><@label "divisionalApplication.designs.label"/>: </b>
                            <#assign divisionalDesigns = dsApplication.divisionalApplicationDetails.designs?map(d -> d.designIdentifier)/>
                            ${divisionalDesigns?join("; ")}
                        </div>
                    </#if>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "productsList.title.label"/></h3>
                <#if dsApplication.designDetails?? && dsApplication.designDetails?size &gt;0>
                    <div>
                        <table class="bordered">
                            <tbody>
                            <tr>
                                <td><b><@label "productsList.header.name"/></b></td>
                                <td><b><@label "productsList.header.class"/></b></td>
                                <td><b><@label "productsList.header.views"/></b></td>
                            </tr>
                            <#list dsApplication.designDetails as piDesign>
                                <#if piDesign.productIndications?? && piDesign.productIndications?size &gt;0>
                                    <#list piDesign.productIndications as pi>
                                        <#if pi.classes?? && pi.classes?size &gt; 0>
                                            <#assign piDescription = pi.classes[0].description/>
                                            <#assign piClasses = pi.classes?map(cl -> cl.mainClass+"-"+cl.subClass)/>
                                            <tr>
                                                <td>
                                                    <#if pi.validationCode?? && pi.validationCode.name() == "OK">
                                                        ${piDescription}
                                                    <#else>
                                                        <u>${piDescription}</u>
                                                    </#if>
                                                    (<@label "indicationKind."+((pi.kind??)?then(pi.kind, "UNKNOWN"))/>)
                                                </td>
                                                <td>
                                                    ${piClasses?join("; ")}
                                                </td>
                                                <td>
                                                    <#if piDesign.views?? && piDesign.views?size &gt;0>
                                                        <#assign viewNums = piDesign.views?map(v -> piDesign.designIdentifier+"."+v.sequence)/>
                                                        ${viewNums?join("; ")}
                                                    </#if>
                                                </td>
                                            </tr>
                                        </#if>
                                    </#list>
                                </#if>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <h3><@label "authors.title.label"></@label></h3>
                <#if dsApplication.designers?? && dsApplication.designers?size &gt; 0 >
                    <#list dsApplication.designers as author>
                        <div>
                            <b><@label "name.label"></@label>:</b>
                            <#if author.name??>
                                ${author.name.firstName}
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
        <#if dsApplication.entitlement?? && dsApplication.entitlement.kind??>
            <tr>
                <td>
                    <h3><@label "entitlementTitle.label"/></h3>
                    <div><b><@label "entitlementKind."+ dsApplication.entitlement.kind/></b></div>
                    <div>${dsApplication.entitlement.description!""}</div>
                </td>
            </tr>
        </#if>
        <tr>
            <td>
                <@documents dsApplication.documents "documents.title.label"/>
                <#if dsApplication.willPayOnline?? && dsApplication.willPayOnline>
                    <div>
                        <@label "willPayOnline.indicator.label"/>
                    </div>
                </#if>
                <#if dsApplication.comment??>
                    <div>
                        <b><@label "notes.label"/>: </b> ${dsApplication.comment}
                    </div>
                </#if>
            </td>
        </tr>
        <tr>
            <#assign dsMatchedFees = ( dsApplication.payments?? &&  dsApplication.payments?size &gt;0)?then( dsApplication.payments[0].fees![], [])/>
            <td><@matchedFees dsMatchedFees "dsefiling"/></td>
        </tr>
        <tr>
            <td><@signatories dsApplication.signatures![]/></td>
        </tr>
        <#if forcedApplicationDateStr?? && forcedApplicationDateStr != "">
            <tr>
                <td>
                    <b><@label "application.date.time"/>: ${forcedApplicationDateStr} </b>
                </td>
            </tr>
        <#elseif acceptedApplicationDateStr?? == false || acceptedApplicationDateStr =="">
            <tr>
                <td>
                    <b><@label "application.date.time"/>: ${.now?string("dd.MM.yyyy HH:mm:ss")} </b>
                </td>
            </tr>
        </#if>
        <#assign picSize = dsApplication.receiptNumber!"small"/>
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
        <tr>
            <td>
                <h3><@label "images.title.label"/></h3>
                <#if dsApplication.designDetails?? && dsApplication.designDetails?size &gt; 0>
                    <table class="views-table">
                        <tbody>
                        <#list dsApplication.designDetails as design>
                            <#if design.views?? && design.views?size &gt; 0>
                                <#assign designViewCount = design.views?size/>
                                <div>
                                    <#list design.views?chunk(picChunk) as viewChunkList>
                                        <tr>
                                            <#list viewChunkList as view>
                                                <td width="${picWidth}">
                                                    <img data-src="documentService" src="${view.view.document.documentId}" width="${picWidth}"/>
                                                    <div><@label "view.number.label"/>: ${design.designIdentifier}<#if designViewCount &gt; 1 >.${view.sequence}</#if></div>
                                                    <div><@label "view.type."+view.type/></div>
                                                </td>
                                            </#list>
                                        </tr>
                                    </#list>
                                </div>
                            </#if>
                        </#list>
                        </tbody>
                    </table>
                </#if>
            </td>
        </tr>
        </tbody>
    </table>
    </body>
    </html>
</#escape>