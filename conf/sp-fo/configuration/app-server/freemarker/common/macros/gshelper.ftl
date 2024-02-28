<#macro gshelper gshelper removedGS applicationType showDeclaration showTitle>
    <#include "gsList.ftl"/>

    <#escape x as x?html>
        <#if showTitle>
            <h3><@label "gshelper.title.label."+applicationType/></h3>
        </#if>
        <div>
            <b><@label "gshelper.type.label."+applicationType />: </b> <@label "gshelper.type."+applicationType+"."+ (gshelper.applicationExtent??)?then(gshelper.applicationExtent.name(), "")/>
        </div>
        <#if showDeclaration>
            <div>
                <b style="color: red"><@label "gshelper.declaration."+applicationType+"."+ (gshelper.applicationExtent??)?then(gshelper.applicationExtent.name(), "")/></b>
            </div>
        </#if>
        <#if gshelper.applicationExtent?? && gshelper.applicationExtent.name() == "PARTIAL_GOODS_AND_SERVICES">
            <div>
                <b><@label "gshelper.remainGS.label."+applicationType/></b>
                <div style="color: #016e01">
                    <@gsList gshelper.classDescriptions/>
                </div>
            </div>
            <div>
                <b><@label "gshelper.removedGS.label."+applicationType/></b>
                <div style="color: #790b01">
                    <@gsList removedGS/>
                </div>
            </div>
        </#if>
        <#if gshelper.goodsServicesComment?? && gshelper.goodsServicesComment !="">
            <div>
                <b><@label "gshelper.goodsServicesComment.label"/>: </b> ${gshelper.goodsServicesComment}
            </div>
        </#if>
    </#escape>
</#macro>