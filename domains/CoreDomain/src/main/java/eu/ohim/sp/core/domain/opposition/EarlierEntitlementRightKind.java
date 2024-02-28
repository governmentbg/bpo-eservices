/**
 * 
 */
package eu.ohim.sp.core.domain.opposition;

/**
 * @author segurse
 *
 */
public enum EarlierEntitlementRightKind {
    EARLIER_TRADE_MARK("earlierTradeMark"),
	WELL_KNOW("wellKnow"),
    NON_REGISTERED("nonRegistered"),
    CONTESTED_APPLICATION("contestedApplication"),
    INDUSTRIAL_PROPERTY_RIGHT("industrialPropertyRight"),
    EARLIER_GI("earlierGI"),
    RIGHTS_PLANT_VARIATION("rightsPlantVariation"),
    RIGHTS_PORTRAIT_NAME("rightsPortraitName"),
    PRIVACY_RIGHT("privacyRight"),
    RIGHTS_AUTOR("rightsAuthor"),
    FOREIGN_TRADEMARK("foreignTradeMark"),
    BUSINESS_NAME("businessName"),
    PROPRIETARY_MEDICINAL_PRODUCT("medicinalProduct"),
    OTHER_RIGHTS("otherRights"),
	AGENT_APPLIED("agentApplied"),
	REPUTATION("reputation"),
	BAD_FAITH("badFaith"),
	EVERY_PERSON("everyPerson"),
	PERSON_LEGAL_INTEREST("personLegalInterest"),
	COURT_ADMITTED_OWNER("courtAdmittedOwner");




	private EarlierEntitlementRightKind(final String text)
	{
		this.text = text;
	}
	
	private final String text;
	
	@Override
	public String toString()
	{
		return text;
	}

}