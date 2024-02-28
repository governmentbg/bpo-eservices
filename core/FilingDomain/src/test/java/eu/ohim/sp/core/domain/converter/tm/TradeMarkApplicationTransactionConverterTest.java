package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.filing.domain.tm.*;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;
import eu.ohim.sp.core.domain.trademark.ApplicationExtent;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;

public class TradeMarkApplicationTransactionConverterTest {

	private static final String APPLICATION_FILING_NUMBER = "00123452";
	private static final String VALUE = "value";
	private static final String LANGUAGE = "en";
	private static final String IDENTIFIER_KIND_CODE = "identifierKindCode";
	private static final String USER = "user";
	private static final String APPLICATION_TYPE = "applicationType";

	@Test
	public void testConvertFrom() {
		TradeMarkApplicationTransactionConverter converter = initializeConverter();

		Transaction ext = new Transaction();
		ext.getTradeMarkTransactionBody().add(new TransactionBody());
		ext.getTradeMarkTransactionBody().get(0)
				.setTransactionContentDetails(new TransactionContentDetails());
		ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails()
				.setTransactionData(new TransactionData());

		// TradeMarkServiceApplication
		TradeMarkServicesApplication application = getTradeMarkServicesApplication();

		// MarkRecordDetails
		MarkRecordDetails markRecordDetails = getMarkRecordDetails();

		// OppositionDetailsType
		OppositionDetailsType oppositionDetailsType = getOppositionDetailsType();

		ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails()
				.getTransactionData()
				.setTradeMarkServicesApplication(application);

		ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails()
				.getTransactionData().getTradeMarkServicesApplication()
				.setMarkRecordDetails(markRecordDetails);

		ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails()
				.getTransactionData().getTradeMarkServicesApplication()
				.setOppositionDetails(oppositionDetailsType);

		TMeServiceApplication tMeServiceApplication = converter.convertFrom(
				ext, new TMeServiceApplication());
		assertEquals(tMeServiceApplication.getApplicationFilingNumber(),
				APPLICATION_FILING_NUMBER);

	}

	@Test
	public void testConvertTo() {
		TradeMarkApplicationTransactionConverter converter = initializeConverter();
		TMeServiceApplication core = new TMeServiceApplication();
		core.setUser(USER);
		core.setApplicationType(APPLICATION_TYPE);

		Transaction transaction = converter.convertTo(core, new Transaction());
		assertEquals(transaction.getTradeMarkTransactionBody().get(0)
				.getTransactionContentDetails().getTransactionData()
				.getTradeMarkServicesApplication().getApplicationFormName(),
				APPLICATION_TYPE);
	}

	private TradeMarkApplicationTransactionConverter initializeConverter() {
		TradeMarkApplicationTransactionConverter converter = new TradeMarkApplicationTransactionConverter();
		List<String> myMappingFiles = new ArrayList<String>();
		myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
		myMappingFiles.add("dozer/tm/dozerMapping.xml");

		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		dozerBeanMapper.setMappingFiles(myMappingFiles);
		converter.setMapper(dozerBeanMapper);

		return converter;
	}

	private TradeMarkServicesApplication getTradeMarkServicesApplication() {
		TradeMarkServicesApplication application = new TradeMarkServicesApplication();
		application.setApplicationFilingNumber(APPLICATION_FILING_NUMBER);
		application.setApplicationFilingDate(new Date());
		application.setApplicationReference("some reference");
		application.setComment("some comment");
		application.setApplicationFormName("formName");

		application.setTradeMarkDetails(new TradeMarkDetails());
		application.getTradeMarkDetails().setTradeMark(
				new ArrayList<TradeMark>());
		application.getTradeMarkDetails().getTradeMark().add(new TradeMark());
		application.getTradeMarkDetails().getTradeMark().get(0)
				.setApplicationNumber(APPLICATION_FILING_NUMBER);
		application.getTradeMarkDetails().getTradeMark().get(0)
				.setRegistrationNumber("registrationNumber");
		return application;
	}

	private MarkRecordDetails getMarkRecordDetails() {
		MarkRecordDetails markRecordDetails = new MarkRecordDetails();
		markRecordDetails.setMarkRecord(new ArrayList<MarkRecord>());
		markRecordDetails.getMarkRecord()
				.add(new MarkRecord(new BasicRecord(), null, null, null));
		markRecordDetails.getMarkRecord().get(0).getBasicRecord()
				.setBasicRecordKind("some");
		markRecordDetails.getMarkRecord().get(0).getBasicRecord()
				.setTradeMarkKeyDetails(new TradeMarkKeyDetailsType());
		markRecordDetails.getMarkRecord().get(0).getBasicRecord()
				.getTradeMarkKeyDetails().setTradeMarkKey(new ArrayList<Key>());
		markRecordDetails.getMarkRecord().get(0).getBasicRecord()
				.getTradeMarkKeyDetails().getTradeMarkKey()
				.add(new Key(new ArrayList<Identifier>(), new URI("some uri")));
		markRecordDetails
				.getMarkRecord()
				.get(0)
				.getBasicRecord()
				.getTradeMarkKeyDetails()
				.getTradeMarkKey()
				.get(0)
				.getIdentifier()
				.add(new Identifier(APPLICATION_FILING_NUMBER,
						IDENTIFIER_KIND_CODE));
		markRecordDetails
				.getMarkRecord()
				.get(0)
				.getBasicRecord()
				.setRecordExtent(
						ApplicationExtent.ALL_GOODS_AND_SERVICES.value());
		markRecordDetails
				.getMarkRecord()
				.get(0)
				.getBasicRecord()
				.setGoodsServicesLimitationDetails(
						new GoodsServicesLimitationDetailsType());
		markRecordDetails
				.getMarkRecord()
				.get(0)
				.getBasicRecord()
				.getGoodsServicesLimitationDetails()
				.setGoodsServicesLimitation(
						new ArrayList<GoodsServicesLimitationType>());

		markRecordDetails.getMarkRecord().get(0).getBasicRecord()
				.getGoodsServicesLimitationDetails()
				.getGoodsServicesLimitation()
				.add(new GoodsServicesLimitationType());

		markRecordDetails.getMarkRecord().get(0).getBasicRecord()
				.getGoodsServicesLimitationDetails()
				.getGoodsServicesLimitation().get(0)
				.setGoodsServicesLimitationCode(VALUE);
		markRecordDetails
				.getMarkRecord()
				.get(0)
				.getBasicRecord()
				.getGoodsServicesLimitationDetails()
				.getGoodsServicesLimitation()
				.get(0)
				.setLimitationClassDescriptionDetails(
						new LimitationClassDescriptionDetailsType());
		markRecordDetails
				.getMarkRecord()
				.get(0)
				.getBasicRecord()
				.getGoodsServicesLimitationDetails()
				.getGoodsServicesLimitation()
				.get(0)
				.setLimitationCountryDetails(new LimitationCountryDetailsType());
		return markRecordDetails;
	}

	private OppositionDetailsType getOppositionDetailsType() {
		OppositionDetailsType oppositionDetailsType = new OppositionDetailsType();
		oppositionDetailsType.setOpposition(new ArrayList<OppositionType>());
		oppositionDetailsType.getOpposition().add(new OppositionType());
		oppositionDetailsType.getOpposition().get(0)
				.setOppositionBasisDetails(new OppositionBasisDetailsType());
		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails()
				.setOppositionBasis(new ArrayList<OppositionBasisType>());
		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis()
				.add(new OppositionBasisType());
		oppositionDetailsType
				.getOpposition()
				.get(0)
				.getOppositionBasisDetails()
				.getOppositionBasis()
				.get(0)
				.setOppositionBasisDocumentDetails(
						new OppositionDocumentDetailsType());
		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionBasisDocumentDetails()
				.setOppositionDocument(new ArrayList<Document>());

		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionBasisDocumentDetails().getOppositionDocument()
				.add(new Document());

		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionBasisDocumentDetails().getOppositionDocument()
				.get(0).setKind(DocumentKind.GROUNDS_EVIDENCE);

		oppositionDetailsType
				.getOpposition()
				.get(0)
				.getOppositionBasisDetails()
				.getOppositionBasis()
				.get(0)
				.setOppositionGroundArticle(
						new ArrayList<OppositionGroundArticleType>());

		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionGroundArticle()
				.add(new OppositionGroundArticleType());

		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionGroundArticle().get(0)
				.setOppositionGroundArticleReference(new Text(VALUE, LANGUAGE));

		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionGroundArticle().get(0)
				.setOppositionGroundArticleText(new Text(VALUE, LANGUAGE));

		oppositionDetailsType.getOpposition().get(0)
				.getOppositionBasisDetails().getOppositionBasis().get(0)
				.getOppositionGroundArticle().get(0)
				.setOppositionGroundLegalAct(new Text(VALUE, LANGUAGE));

		oppositionDetailsType
				.getOpposition()
				.get(0)
				.getOppositionBasisDetails()
				.getOppositionBasis()
				.get(0)
				.setOppositionBasisText(
						GroundCategoryKind.ABSOLUTE_GROUNDS.toString());
		return oppositionDetailsType;
	}
}
