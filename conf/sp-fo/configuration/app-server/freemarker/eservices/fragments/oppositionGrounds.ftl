<#include "../../common/macros/designDetails.ftl">
<#include "../../common/macros/markDetails.ftl">
<#include "../../common/macros/groundBase.ftl">
<#escape x as x?html>
    <#if esApplication.oppositionGrounds?? && esApplication.oppositionGrounds?size &gt; 0>
        <#assign absoluteGrounds = esApplication.oppositionGrounds?filter(g -> g.groundCategory?string == "Absolute Grounds")/>
        <#assign relativeGrounds = esApplication.oppositionGrounds?filter(g -> g.groundCategory?string == "Relative Grounds")/>
        <#assign revocationGrounds = esApplication.oppositionGrounds?filter(g -> g.groundCategory?string == "Revocation Grounds")/>

        <#if relativeGrounds?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "relativeGrounds.title.label."+applicationType/></h3>
                    <#list relativeGrounds as groundRel>
                        <div>
                            <u class="titleText"><@label "earlier.entitlement.label"/>: <@label "earlier.entitlement."+ applicationType+"."+groundRel.earlierEntitlementRightType?string/></u>
                        </div>
                        <div style="margin-left: 20px">
                            <#if groundRel.earlierDesignDetails?? && groundRel.earlierDesignDetails?size &gt; 0>
                                <div>
                                    <div style="font-size: 12pt; font-weight: bold; margin-bottom: 10px">
                                        <@label "earlier.designs.label"/>
                                    </div>

                                    <#assign selectedDesignApps = groundRel.earlierDesignDetails?filter(d -> d.designDetails?? && d.designDetails?size &gt;0 && d.designDetails[0]?? && d.designDetails[0].selected)/>
                                    <#if selectedDesignApps?? && selectedDesignApps?size &gt; 0>
                                        <@designDetails selectedDesignApps/>
                                    </#if>
                                </div>
                            </#if>
                            <#if groundRel.earlierTradeMarkDetails??>
                                <#assign isEarlierGI=groundRel.earlierEntitlementRightType?string == "earlierGI"/>
                                <div style="font-size: 12pt; font-weight: bold">
                                    <#if isEarlierGI>
                                        <@label "earlier.gi.label"/>
                                    <#else>
                                        <@label "earlier.mark.label"/>
                                    </#if>
                                </div>
                                <div>
                                    <#if groundRel.earlierTradeMarkCategory?? && groundRel.earlierTradeMarkCategory != "">
                                        <div>
                                            <b><@label "relativeGrounds.earlierTradeMarkCategory.label"/>:</b> <@label "relativeGrounds.earlierTradeMarkCategory."+groundRel.earlierTradeMarkCategory/>
                                        </div>
                                    </#if>

                                    <#assign showExtent = (isEarlierGI == false && applicationType != "DS_INVALIDITY")/>
                                    <#assign showGS = applicationType != "DS_INVALIDITY"/>
                                    <@markDetails groundRel.earlierTradeMarkDetails showExtent showGS
                                        "mark.extent.label."+groundRel.earlierEntitlementRightType?string/>
                                    <#if groundRel.registrationCountry??>
                                        <div>
                                            <b><@label "relativeGrounds.registrationCountry.label"/>:</b> ${groundRel.registrationCountry}
                                        </div>
                                    </#if>
                                    <#if groundRel.typeRightDetails?? && groundRel.typeRightDetails != "">
                                        <div>
                                            <b><@label "relativeGrounds.typeRightDetails.label"/>:</b> ${groundRel.typeRightDetails}
                                        </div>
                                    </#if>
                                </div>
                            </#if>
                            <#if groundRel.dateOfFame??>
                                <div>
                                    <b><@label "relativeGrounds.dateOfFame.label."+groundRel.earlierEntitlementRightType?string/>: </b>
                                    ${groundRel.dateOfFame?string("dd.MM.yyyy")}
                                </div>
                            </#if>
                            <#if groundRel.relatedApplicationsNumbers??>
                                <div>
                                    <b><@label "relativeGrounds.relatedApplicationsNumbers.label"/>: </b> ${groundRel.relatedApplicationsNumbers}
                                </div>
                            </#if>
                            <#if groundRel.opponentEntitlementKind??>
                                <div>
                                    <b>
                                        <#if applicationType?ends_with("INVALIDITY")>
                                            <@label "relativeGrounds.opponentEntitlementKind.label.invalidity"/>
                                        <#else>
                                            <@label "relativeGrounds.opponentEntitlementKind.label"/>
                                        </#if> :
                                    </b> <@label "relativeGrounds.opponentEntitlementKind."+groundRel.opponentEntitlementKind/>
                                </div>
                            </#if>
                            <#if groundRel.opponentEntitlementText??>
                                <div>
                                    <b><@label "relativeGrounds.opponentEntitlementText.label"/>: </b> ${groundRel.opponentEntitlementText}
                                </div>
                            </#if>
                            <#if groundRel.earlierRightDescription??>
                                <div>
                                    <b><@label "relativeGrounds.earlierRightDescription.label."+groundRel.earlierEntitlementRightType?string/>: </b>
                                    ${groundRel.earlierRightDescription}
                                </div>
                            </#if>
                            <@groundBase groundRel/>
                        </div>
                    </#list>
                </td>
            </tr>
        </#if>
        <#if absoluteGrounds?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "absoluteGrounds.title.label."+applicationType/></h3>
                    <#list absoluteGrounds as groundAbs>
                        <div>
                            <@groundBase groundAbs/>
                        </div>
                    </#list>
                </td>
            </tr>
        </#if>
        <#if revocationGrounds?size &gt; 0>
            <tr>
                <td>
                    <h3><@label "revocationGrounds.title.label"/></h3>
                    <#list revocationGrounds as groundRev>
                        <div>
                            <@groundBase groundRev/>
                        </div>
                    </#list>
                </td>
            </tr>
        </#if>
    </#if>
</#escape>