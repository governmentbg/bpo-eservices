package eu.ohim.sp.filing.domain.tm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Date: 20/1/2014
 * Time: 10:01 μμ
 * To change this template use File | Settings | File Templates.
 */
public class ObjectFactoryTest {

    ObjectFactory objectFactory;

    @Before
    public void setUp() {
        objectFactory = new ObjectFactory();
    }

    @Test
    public void testCreateTransaction() {
        Assert.assertNotNull(objectFactory.createTransaction());
    }

    @Test
    public void testCreateTransactionBody() {
        Assert.assertNotNull(objectFactory.createTransactionBody());
    }

    @Test
    public void testCreateFormattedNameAddress() {
        Assert.assertNotNull(objectFactory.createFormattedNameAddress());
    }

    @Test
    public void testCreateSeniorityDetails() {
        Assert.assertNotNull(objectFactory.createSeniorityDetails());
    }

    @Test
    public void testCreateEntitlementEstablishmentType() {
        Assert.assertNotNull(objectFactory.createEntitlementEstablishmentType());
    }

    @Test
    public void testCreateTradeMarkApplication() {
        Assert.assertNotNull(objectFactory.createTradeMarkApplication());
    }

    @Test
    public void testCreateAssociatedMarkDetailsType() {
        Assert.assertNotNull(objectFactory.createAssociatedMarkDetailsType());
    }

    @Test
    public void testCreateTradeMark() {
        Assert.assertNotNull(objectFactory.createTradeMark());
    }

    @Test
    public void testCreateExhibitionPriorityDetails() {
        Assert.assertNotNull(objectFactory.createExhibitionPriorityDetails());
    }

    @Test
    public void testCreateRepresentative() {
        Assert.assertNotNull(objectFactory.createRepresentative());
    }

    @Test
    public void testCreateClassificationTermStatusType() {
        Assert.assertNotNull(objectFactory.createClassificationTermStatusType());
    }

    @Test
    public void testCreateFormattedName() {
        Assert.assertNotNull(objectFactory.createFormattedName());
    }

    @Test
    public void testCreateMarkImageRenditionType() {
        Assert.assertNotNull(objectFactory.createMarkImageRenditionType());
    }

    @Test
    public void testCreatePreviousRegistrationType() {
        Assert.assertNotNull(objectFactory.createPreviousRegistrationType());
    }

    @Test
    public void testCreateRequestSearch() {
        Assert.assertNotNull(objectFactory.createRequestSearch());
    }

    @Test
    public void testCreateMarkDescriptionDetails() {
        Assert.assertNotNull(objectFactory.createMarkDescriptionDetails());
    }

    @Test
    public void testCreateExtendedWIPOBasicNumberType() {
        Assert.assertNotNull(objectFactory.createExtendedWIPOBasicNumberType());
    }

    @Test
    public void testCreateMarkEventDetailsType() {
        Assert.assertNotNull(objectFactory.createMarkEventDetailsType());
    }

    @Test
    public void testCreateRecordPublicationDetailsType() {
        Assert.assertNotNull(objectFactory.createRecordPublicationDetailsType());
    }

    @Test
    public void testCreateBasicRegistrationType() {
        Assert.assertNotNull(objectFactory.createBasicRegistrationType());
    }

    @Test
    public void testCreateExtendedWIPOST3Code() {
        Assert.assertNotNull(objectFactory.createExtendedWIPOST3Code());
    }

    @Test
    public void testCreatePreviousRegistrationDetailsType() {
        Assert.assertNotNull(objectFactory.createPreviousRegistrationDetailsType());
    }

    @Test
    public void testCreateTradeMarkDetails() {
        Assert.assertNotNull(objectFactory.createTradeMarkDetails());
    }

    @Test
    public void testCreateMarkImageRenditionDetailsType() {
        Assert.assertNotNull(objectFactory.createMarkImageRenditionDetailsType());
    }

    @Test
    public void testCreateEntitlementDomiciledType() {
        Assert.assertNotNull(objectFactory.createEntitlementDomiciledType());
    }

    @Test
    public void testCreateGoodsServicesDetails() {
        Assert.assertNotNull(objectFactory.createGoodsServicesDetails());
    }

    @Test
    public void testCreateClassificationTermType() {
        Assert.assertNotNull(objectFactory.createClassificationTermType());
    }

    @Test
    public void testCreateTradeMarkServicesApplication() {
        Assert.assertNotNull(objectFactory.createTradeMarkServicesApplication());
    }

    @Test
    public void testCreateReimbursementFeeType() {
        Assert.assertNotNull(objectFactory.createReimbursementFeeType());
    }

    @Test
    public void testCreatePaymentFeeDetails() {
        Assert.assertNotNull(objectFactory.createPaymentFeeDetails());
    }

    @Test
    public void testCreateOpponentDetailsType() {
        Assert.assertNotNull(objectFactory.createOpponentDetailsType());
    }

    @Test
    public void testCreateChequeType() {
        Assert.assertNotNull(objectFactory.createChequeType());
    }

    @Test
    public void testCreateTransactionError() {
        Assert.assertNotNull(objectFactory.createTransactionError());
    }

    @Test
    public void testCreateDivisionalApplicationDetailsType() {
        Assert.assertNotNull(objectFactory.createDivisionalApplicationDetailsType());
    }

    @Test
    public void testCreateOppositionDetailsType() {
        Assert.assertNotNull(objectFactory.createOppositionDetailsType());
    }

    @Test
    public void testCreateDesignatedCountryType() {
        Assert.assertNotNull(objectFactory.createDesignatedCountryType());
    }

    @Test
    public void testCreateRecordEventDetailsType() {
        Assert.assertNotNull(objectFactory.createRecordEventDetailsType());
    }

    @Test
    public void testCreatePaymentFee() {
        Assert.assertNotNull(objectFactory.createPaymentFee());
    }

    @Test
    public void testCreateBasicRecord() {
        Assert.assertNotNull(objectFactory.createBasicRecord());
    }

    @Test
    public void testCreatePriorityDetails() {
        Assert.assertNotNull(objectFactory.createPriorityDetails());
    }

    @Test
    public void testCreateFormattedAddress() {
        Assert.assertNotNull(objectFactory.createFormattedAddress());
    }

    @Test
    public void testCreateBankTransfer() {
        Assert.assertNotNull(objectFactory.createBankTransfer());
    }

    @Test
    public void testCreateIdentifier() {
        Assert.assertNotNull(objectFactory.createIdentifier());
    }

    @Test
    public void testCreateVersionType() {
        Assert.assertNotNull(objectFactory.createVersionType());
    }

    @Test
    public void testCreateMarkRecord() {
        Assert.assertNotNull(objectFactory.createMarkRecord());
    }

    @Test
    public void testCreateExtendedISOLanguageCode() {
        Assert.assertNotNull(objectFactory.createExtendedISOLanguageCode());
    }

    @Test
    public void testCreateRecordUseIntentDetailsType() {
        Assert.assertNotNull(objectFactory.createRecordUseIntentDetailsType());
    }

    @Test
    public void testCreateRecordEventType() {
        Assert.assertNotNull(objectFactory.createRecordEventType());
    }

    @Test
    public void testCreateMarkEventType() {
        Assert.assertNotNull(objectFactory.createMarkEventType());
    }

    @Test
    public void testCreateRequestSoftwareType() {
        Assert.assertNotNull(objectFactory.createRequestSoftwareType());
    }

    @Test
    public void testCreateClassDescriptionDetails() {
        Assert.assertNotNull(objectFactory.createClassDescriptionDetails());
    }

    @Test
    public void testCreateOppositionEventDetailsType() {
        Assert.assertNotNull(objectFactory.createOppositionEventDetailsType());
    }

    @Test
    public void testCreateRecordDesignatedCountryDetailsType() {
        Assert.assertNotNull(objectFactory.createRecordDesignatedCountryDetailsType());
    }

    @Test
    public void testCreateReimbursementType() {
        Assert.assertNotNull(objectFactory.createReimbursementType());
    }

    @Test
    public void testCreatePublicationDetailsType() {
        Assert.assertNotNull(objectFactory.createPublicationDetailsType());
    }

    @Test
    public void testCreateSignatory() {
        Assert.assertNotNull(objectFactory.createSignatory());
    }

    @Test
    public void testCreatePayment() {
        Assert.assertNotNull(objectFactory.createPayment());
    }

    @Test
    public void testCreateTransformationPriorityDetails() {
        Assert.assertNotNull(objectFactory.createTransformationPriorityDetails());
    }

    @Test
    public void testCreatePriority() {
        Assert.assertNotNull(objectFactory.createPriority());
    }

    @Test
    public void testCreateDesignatedCountryDetailsType() {
        Assert.assertNotNull(objectFactory.createDesignatedCountryDetailsType());
    }

    @Test
    public void testCreateAuthorization() {
        Assert.assertNotNull(objectFactory.createAuthorization());
    }

    @Test
    public void testCreatePhone() {
        Assert.assertNotNull(objectFactory.createPhone());
    }

    @Test
    public void testCreateAmount() {
        Assert.assertNotNull(objectFactory.createAmount());
    }

    @Test
    public void testCreateKey() {
        Assert.assertNotNull(objectFactory.createKey());
    }

    @Test
    public void testCreateFreeFormatAddressType() {
        Assert.assertNotNull(objectFactory.createFreeFormatAddressType());
    }

    @Test
    public void testCreateApplicantDetails() {
        Assert.assertNotNull(objectFactory.createApplicantDetails());
    }

    @Test
    public void testCreateTransactionHeaderType() {
        Assert.assertNotNull(objectFactory.createTransactionHeaderType());
    }

    @Test
    public void testCreateBasicApplicationDetailsType() {
        Assert.assertNotNull(objectFactory.createBasicApplicationDetailsType());
    }

    @Test
    public void testCreateDocument() {
        Assert.assertNotNull(objectFactory.createDocument());
    }

    @Test
    public void testCreateClassNumber() {
        Assert.assertNotNull(objectFactory.createClassNumber());
    }

    @Test
    public void testCreateEarlierMarkType() {
        Assert.assertNotNull(objectFactory.createEarlierMarkType());
    }

    @Test
    public void testCreateFreeFormatNameDetailsType() {
        Assert.assertNotNull(objectFactory.createFreeFormatNameDetailsType());
    }

    @Test
    public void testCreateRepresentativeRelationshipDetails() {
        Assert.assertNotNull(objectFactory.createRepresentativeRelationshipDetails());
    }

    @Test
    public void testCreateClassificationTermDetails() {
        Assert.assertNotNull(objectFactory.createClassificationTermDetails());
    }

    @Test
    public void testCreateOpponentType() {
        Assert.assertNotNull(objectFactory.createOpponentType());
    }

    @Test
    public void testCreateNiceClassNumber() {
        Assert.assertNotNull(objectFactory.createNiceClassNumber());
    }

    @Test
    public void testCreateStaffType() {
        Assert.assertNotNull(objectFactory.createStaffType());
    }

    @Test
    public void testCreateAddress() {
        Assert.assertNotNull(objectFactory.createAddress());
    }

    @Test
    public void testCreateOtherDateType() {
        Assert.assertNotNull(objectFactory.createOtherDateType());
    }

    @Test
    public void testCreateUseRightType() {
        Assert.assertNotNull(objectFactory.createUseRightType());
    }

    @Test
    public void testCreateConversionPriorityDetailsType() {
        Assert.assertNotNull(objectFactory.createConversionPriorityDetailsType());
    }

    @Test
    public void testCreateEntitlementType() {
        Assert.assertNotNull(objectFactory.createEntitlementType());
    }

    @Test
    public void testCreateBasicRegistrationDetailsType() {
        Assert.assertNotNull(objectFactory.createBasicRegistrationDetailsType());
    }

    @Test
    public void testCreateSignatoryDetails() {
        Assert.assertNotNull(objectFactory.createSignatoryDetails());
    }

    @Test
    public void testCreateFreeFormatNameType() {
        Assert.assertNotNull(objectFactory.createFreeFormatNameType());
    }

    @Test
    public void testCreateHolderDetailsType() {
        Assert.assertNotNull(objectFactory.createHolderDetailsType());
    }

    @Test
    public void testCreateOppositionType() {
        Assert.assertNotNull(objectFactory.createOppositionType());
    }

    @Test
    public void testCreatePartyIdentifier() {
        Assert.assertNotNull(objectFactory.createPartyIdentifier());
    }

    @Test
    public void testCreateClaimantType() {
        Assert.assertNotNull(objectFactory.createClaimantType());
    }

    @Test
    public void testCreateLimitationCountryDetailsType() {
        Assert.assertNotNull(objectFactory.createLimitationCountryDetailsType());
    }

    @Test
    public void testCreateRepresentativeDetails() {
        Assert.assertNotNull(objectFactory.createRepresentativeDetails());
    }

    @Test
    public void testCreateColourRGBType() {
        Assert.assertNotNull(objectFactory.createColourRGBType());
    }

    @Test
    public void testCreateMarkImageDetails() {
        Assert.assertNotNull(objectFactory.createMarkImageDetails());
    }

    @Test
    public void testCreateExhibitionPriority() {
        Assert.assertNotNull(objectFactory.createExhibitionPriority());
    }

    @Test
    public void testCreateTransformationPriority() {
        Assert.assertNotNull(objectFactory.createTransformationPriority());
    }

    @Test
    public void testCreateOppositionBasisType() {
        Assert.assertNotNull(objectFactory.createOppositionBasisType());
    }

    @Test
    public void testCreateURI() {
        Assert.assertNotNull(objectFactory.createURI());
    }

    @Test
    public void testCreateOppositionEventType() {
        Assert.assertNotNull(objectFactory.createOppositionEventType());
    }

    @Test
    public void testCreatePaymentDetails() {
        Assert.assertNotNull(objectFactory.createPaymentDetails());
    }

    @Test
    public void testCreateApplicant() {
        Assert.assertNotNull(objectFactory.createApplicant());
    }

    @Test
    public void testCreateSenderDetailsType() {
        Assert.assertNotNull(objectFactory.createSenderDetailsType());
    }

    @Test
    public void testCreateReimbursementFeeDetailsType() {
        Assert.assertNotNull(objectFactory.createReimbursementFeeDetailsType());
    }

    @Test
    public void testCreateOppositionBasisDetailsType() {
        Assert.assertNotNull(objectFactory.createOppositionBasisDetailsType());
    }

    @Test
    public void testCreateTransactionContentDetails() {
        Assert.assertNotNull(objectFactory.createTransactionContentDetails());
    }

    @Test
    public void testCreateSeniorityKind() {
        Assert.assertNotNull(objectFactory.createSeniorityKind());
    }

    @Test
    public void testCreateMarkImage() {
        Assert.assertNotNull(objectFactory.createMarkImage());
    }

    @Test
    public void testCreateBaseGoodsServicesDetailsType() {
        Assert.assertNotNull(objectFactory.createBaseGoodsServicesDetailsType());
    }

    @Test
    public void testCreateOppositionDocumentDetailsType() {
        Assert.assertNotNull(objectFactory.createOppositionDocumentDetailsType());
    }

    @Test
    public void testCreateGoodsServicesLimitationType() {
        Assert.assertNotNull(objectFactory.createGoodsServicesLimitationType());
    }

    @Test
    public void testCreateLoginInformationType() {
        Assert.assertNotNull(objectFactory.createLoginInformationType());
    }

    @Test
    public void testCreateColourCodeType() {
        Assert.assertNotNull(objectFactory.createColourCodeType());
    }

    @Test
    public void testCreateTradeMarkKeyDetailsType() {
        Assert.assertNotNull(objectFactory.createTradeMarkKeyDetailsType());
    }

    @Test
    public void testCreateTransactionData() {
        Assert.assertNotNull(objectFactory.createTransactionData());
    }

    @Test
    public void testCreateLimitationClassDescriptionDetailsType() {
        Assert.assertNotNull(objectFactory.createLimitationClassDescriptionDetailsType());
    }

    @Test
    public void testCreateText() {
        Assert.assertNotNull(objectFactory.createText());
    }

    @Test
    public void testCreateOppositionGroundArticleType() {
        Assert.assertNotNull(objectFactory.createOppositionGroundArticleType());
    }

    @Test
    public void testCreateRequestExaminationType() {
        Assert.assertNotNull(objectFactory.createRequestExaminationType());
    }

    @Test
    public void testCreateGoodsServicesLimitationDetailsType() {
        Assert.assertNotNull(objectFactory.createGoodsServicesLimitationDetailsType());
    }

    @Test
    public void testCreateCardAccount() {
        Assert.assertNotNull(objectFactory.createCardAccount());
    }

    @Test
    public void testCreateWordMarkSpecification() {
        Assert.assertNotNull(objectFactory.createWordMarkSpecification());
    }

    @Test
    public void testCreateRepresentationSizeType() {
        Assert.assertNotNull(objectFactory.createRepresentationSizeType());
    }

    @Test
    public void testCreateMarkTransliterationType() {
        Assert.assertNotNull(objectFactory.createMarkTransliterationType());
    }

    @Test
    public void testCreateTransactionErrorDetails() {
        Assert.assertNotNull(objectFactory.createTransactionErrorDetails());
    }

    @Test
    public void testCreateMarkSound() {
        Assert.assertNotNull(objectFactory.createMarkSound());
    }

    @Test
    public void testCreateClassDescription() {
        Assert.assertNotNull(objectFactory.createClassDescription());
    }

    @Test
    public void testCreateAccount() {
        Assert.assertNotNull(objectFactory.createAccount());
    }

    @Test
    public void testCreateCorrespondenceAddress() {
        Assert.assertNotNull(objectFactory.createCorrespondenceAddress());
    }

    @Test
    public void testCreateMarkImageCategoryType() {
        Assert.assertNotNull(objectFactory.createMarkImageCategoryType());
    }

    @Test
    public void testCreateCategoryCodeDetailsType() {
        Assert.assertNotNull(objectFactory.createCategoryCodeDetailsType());
    }

    @Test
    public void testCreateName() {
        Assert.assertNotNull(objectFactory.createName());
    }

    @Test
    public void testCreateColourDetailsType() {
        Assert.assertNotNull(objectFactory.createColourDetailsType());
    }

    @Test
    public void testCreateEarlierMarkDetailsType() {
        Assert.assertNotNull(objectFactory.createEarlierMarkDetailsType());
    }

    @Test
    public void testCreateBasicRegistrationApplicationDetailsType() {
        Assert.assertNotNull(objectFactory.createBasicRegistrationApplicationDetailsType());
    }

    @Test
    public void testCreateRecordDocumentDetailsType() {
        Assert.assertNotNull(objectFactory.createRecordDocumentDetailsType());
    }

    @Test
    public void testCreatePartyRelationship() {
        Assert.assertNotNull(objectFactory.createPartyRelationship());
    }

    @Test
    public void testCreateAddressBook() {
        Assert.assertNotNull(objectFactory.createAddressBook());
    }

    @Test
    public void testCreateConversionPriorityType() {
        Assert.assertNotNull(objectFactory.createConversionPriorityType());
    }

    @Test
    public void testCreateFreeFormatAddressDetailsType() {
        Assert.assertNotNull(objectFactory.createFreeFormatAddressDetailsType());
    }

    @Test
    public void testCreateCorrespondenceAddressKey() {
        Assert.assertNotNull(objectFactory.createCorrespondenceAddressKey());
    }

    @Test
    public void testCreateBasicApplicationType() {
        Assert.assertNotNull(objectFactory.createBasicApplicationType());
    }

    @Test
    public void testCreateSeniority() {
        Assert.assertNotNull(objectFactory.createSeniority());
    }

    @Test
    public void testCreateReimbursementDetailsType() {
        Assert.assertNotNull(objectFactory.createReimbursementDetailsType());
    }

    @Test
    public void testCreateTradeMarkDocumentDetails() {
        Assert.assertNotNull(objectFactory.createTradeMarkDocumentDetails());
    }

    @Test
    public void testCreateClassificationTermSourceDetailsType() {
        Assert.assertNotNull(objectFactory.createClassificationTermSourceDetailsType());
    }

    @Test
    public void testCreatePaymentMethod() {
        Assert.assertNotNull(objectFactory.createPaymentMethod());
    }

    @Test
    public void testCreatePublicationType() {
        Assert.assertNotNull(objectFactory.createPublicationType());
    }

    @Test
    public void testCreateMarkDisclaimerDetails() {
        Assert.assertNotNull(objectFactory.createMarkDisclaimerDetails());
    }

    @Test
    public void testCreateBasicRegistrationApplicationType() {
        Assert.assertNotNull(objectFactory.createBasicRegistrationApplicationType());
    }

    @Test
    public void testCreateContactInformationDetails() {
        Assert.assertNotNull(objectFactory.createContactInformationDetails());
    }

    @Test
    public void testCreateMarkSeriesDetailsType() {
        Assert.assertNotNull(objectFactory.createMarkSeriesDetailsType());
    }

    @Test
    public void testCreateReceiverDetailsType() {
        Assert.assertNotNull(objectFactory.createReceiverDetailsType());
    }

    @Test
    public void testCreateColourType() {
        Assert.assertNotNull(objectFactory.createColourType());
    }

    @Test
    public void testCreateGoodsServices() {
        Assert.assertNotNull(objectFactory.createGoodsServices());
    }

    @Test
    public void testCreateMarkRecordDetails() {
        Assert.assertNotNull(objectFactory.createMarkRecordDetails());
    }

    @Test
    public void testCreateReputationClaimType() {
        Assert.assertNotNull(objectFactory.createReputationClaimType());
    }

    @Test
    public void testCreateStaffDetailsType() {
        Assert.assertNotNull(objectFactory.createStaffDetailsType());
    }

    @Test
    public void testCreateDocumentIncludedDetails() {
        Assert.assertNotNull(objectFactory.createDocumentIncludedDetails());
    }

    @Test
    public void testCreateMarkSoundDetails() {
        Assert.assertNotNull(objectFactory.createMarkSoundDetails());
    }

    @Test
    public void testCreateColourRALType() {
        Assert.assertNotNull(objectFactory.createColourRALType());
    }

    @Test
    public void testCreateClaimantDetailsType() {
        Assert.assertNotNull(objectFactory.createClaimantDetailsType());
    }

    @Test
    public void testCreateColourHEXType() {
        Assert.assertNotNull(objectFactory.createColourHEXType());
    }

    @Test
    public void testCreateTransactionJAXB() {
        Assert.assertNotNull(objectFactory.createTransaction(new Transaction()));
    }
}
