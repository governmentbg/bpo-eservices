<#ftl encoding="UTF-8" />
<#escape x as x?html>
    <html>
    <head>
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
        <#include "../common/css/styles.ftl"/>
        <style type="text/css">
            table.receiptinfo {
                max-width: 21cm;
                font-size: 9pt;
                page-break-inside: avoid;
                page-break-after: auto;
                -fs-table-paginate: paginate;
            }
            table.receiptinfo td, th {
                padding: 2px;
            }
            thead tr.receipthead {
                font-weight: bold;
            }
            table.receiptinfo .receiptcell {
                word-wrap: break-word;
                max-width: 8cm
            }
            @page {
                margin: 1cm 1cm 1cm;
                width: 21cm;
                height: 29.7cm;
                font-family:  'Arial Unicode MS';
                @bottom-right {
                    font-size: 9pt;
                    counter-increment: page;
                    content: counter(page) "/" counter(pages);
                }
                @bottom-left {
                    font-size: 9pt;
                    counter-increment: page;
                    content: "${.now?string("dd.MM.yyyy HH:mm:ss")}";
                }
            }
        </style>
        <#include "../common/macros/label.ftl"/>
    </head>

    <#assign similarMarks = args[0]![] />
    <div class="receiptcontent">
        <div>
            <img src="../common/img/Logo_BG_new.png"/>
        </div>
        <h3><@label "similarity.report.title.label"/></h3>

        <table class="bordered receiptinfo">
            <thead style="display: table-header-group">
            <tr class="receipthead">
                <th><@label "similarity.report.markNumber.label"/></th>
                <th class="receiptcell"><@label "similarity.report.markDescription.label"/></th>
                <th><@label "similarity.report.markType.label"/></th>
                <th><@label "similarity.report.office.label"/></th>
                <th class="receiptcell"><@label "similarity.report.ownerName.label"/></th>
                <th class="receiptcell"><@label "similarity.report.inputTerm.label"/></th>
            </tr>
            </thead>
            <tbody>
            <#if similarMarks?? && similarMarks?size &gt; 0>
                <#list similarMarks as mark>
                    <tr>
                        <td>${mark.applicationNumber}</td>
                        <td class="receiptcell">
                            <#if mark.wordSpecifications?? && mark.wordSpecifications?size &gt; 0>
                                ${mark.wordSpecifications[0].wordElements}
                            </#if>
                        </td>
                        <td>
                            <#if mark.markKind??>
                                <@label "mark.type."+mark.markKind.value()/>
                            </#if>
                        </td>
                        <td>${mark.registrationOffice}</td>
                        <td class="receiptcell">
                            <#if mark.applicants?? && mark.applicants?size &gt; 0>
                                <#list mark.applicants as applicant>
                                    <#if applicant.name??>
                                        <div>${applicant.name.firstName!""+" "+applicant.name.lastName!""}</div>
                                    </#if>
                                </#list>
                            </#if>
                        </td>
                        <td class="receiptcell">
                            <#if mark.classDescriptions?? && mark.classDescriptions?size &gt; 0>
                                <#list mark.classDescriptions as cls>
                                    ${cls.classNumber}<#sep>;
                                </#list>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <div>
            <h3><@label "similarity.report.warn.label"/></h3>
        </div>
    </div>
    </html>
</#escape>