/*
 *  FspDomain:: TradeMarkApplicationTransactionConverter 13/11/13 10:21 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.tm;

import java.util.*;
import java.util.stream.Collectors;

import eu.ohim.sp.core.domain.trademark.*;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.converter.GoodsServicesDetailsConverter;
import eu.ohim.sp.core.domain.opposition.EarlierEntitlementRightKind;
import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;
import eu.ohim.sp.core.domain.opposition.LawArticle;
import eu.ohim.sp.core.domain.opposition.LegalActVersion;
import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.opposition.OppositionRelativeGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionRevocationGrounds;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.filing.domain.tm.Document;
import eu.ohim.sp.filing.domain.tm.DocumentKind;
import eu.ohim.sp.filing.domain.tm.GoodsServicesDetails;
import eu.ohim.sp.filing.domain.tm.LoginInformationType;
import eu.ohim.sp.filing.domain.tm.MarkRecord;
import eu.ohim.sp.filing.domain.tm.OppositionBasisType;
import eu.ohim.sp.filing.domain.tm.OppositionDetailsType;
import eu.ohim.sp.filing.domain.tm.OppositionGroundArticleType;
import eu.ohim.sp.filing.domain.tm.RequestSoftwareType;
import eu.ohim.sp.filing.domain.tm.SenderDetailsType;
import eu.ohim.sp.filing.domain.tm.Transaction;
import eu.ohim.sp.filing.domain.tm.TransactionBody;
import eu.ohim.sp.filing.domain.tm.TransactionContentDetails;
import eu.ohim.sp.filing.domain.tm.TransactionData;
import eu.ohim.sp.filing.domain.tm.TransactionHeaderType;

/**
 * @author SS & JD
 */
public class TradeMarkApplicationTransactionConverter extends DozerConverter<TMeServiceApplication, Transaction> implements MapperAware
{
    private Mapper mapper;

    public TradeMarkApplicationTransactionConverter()
    {
        super(TMeServiceApplication.class, Transaction.class);
    }
    @Override
    public TMeServiceApplication convertFrom(Transaction ext, TMeServiceApplication core)
    {
		TMeServiceApplication tMeServiceApplication = mapper.map(ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication(),TMeServiceApplication.class);
		tMeServiceApplication.setApplicationType(ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails()
				.getTransactionData().getTradeMarkServicesApplication().getApplicationFormName());

		GoodsServicesDetailsConverter classDescriptionsConverter = new GoodsServicesDetailsConverter();
		if (tMeServiceApplication.getTradeMarks()!=null){
			for (int i=0; i<tMeServiceApplication.getTradeMarks().size(); i++)
			{
				LimitedTradeMark trademark = tMeServiceApplication.getTradeMarks().get(i);
				for (MarkRecord markRecord: ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getMarkRecordDetails().getMarkRecord()) {
					String pozNumber = trademark.getApplicationNumber();
					if (StringUtils.isEmpty(pozNumber)) {
						pozNumber = trademark.getRegistrationNumber();
					}
					if (markRecord.getBasicRecord().getTradeMarkKeyDetails()!= null && markRecord.getBasicRecord().getTradeMarkKeyDetails().getTradeMarkKey().get(0).getIdentifier().get(0).getValue().equalsIgnoreCase(pozNumber)){
						if (ApplicationExtent.ALL_GOODS_AND_SERVICES.value().equalsIgnoreCase(markRecord.getBasicRecord().getRecordExtent())) {
							trademark.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
						} else if (ApplicationExtent.PARTIAL_GOODS_AND_SERVICES.value().equalsIgnoreCase(markRecord.getBasicRecord().getRecordExtent())) {
							trademark.setApplicationExtent(ApplicationExtent.PARTIAL_GOODS_AND_SERVICES);
						} else if (ApplicationExtent.OTHER.value().equalsIgnoreCase(markRecord.getBasicRecord().getRecordExtent())) {
							trademark.setApplicationExtent(ApplicationExtent.OTHER);
						}
						List <eu.ohim.sp.filing.domain.tm.ClassDescription> extLimitedGS = new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>();
						String gsComment = null;
						if (markRecord.getBasicRecord().getGoodsServicesLimitationDetails()!=null
								&& markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation()!=null
								&& markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().size()>0
								) {
							if(markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getLimitationClassDescriptionDetails() != null){
								extLimitedGS.addAll(markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getLimitationClassDescriptionDetails().getClassDescription());
							}
							if( markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getComment() != null){
								gsComment =markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getComment().getValue();
							}
						}
						ArrayList<ClassDescription> coreLimitedGS = new ArrayList<ClassDescription>();
						for(eu.ohim.sp.filing.domain.tm.ClassDescription extCS : extLimitedGS){
							ClassDescription coreCS = classDescriptionsConverter.fspClassDescriptionToCoreGoodsServices(extCS);
							coreLimitedGS.add(coreCS);
						}

						trademark.setLimitedClassDescriptions(coreLimitedGS);
						trademark.setGoodsServicesComment(gsComment);
						tMeServiceApplication.getTradeMarks().set(i, trademark);
					}

				}

			}
		}

		if(tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_SURRENDER") || tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_WITHDRAWAL")
				|| tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_RENEWAL")|| tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_TRANSFER")
				|| tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_OPPOSITION")|| tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_OBJECTION")
				|| tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_REVOCATION")|| tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_INVALIDITY")
		){
			if(tMeServiceApplication.getTradeMarks() != null && tMeServiceApplication.getTradeMarks().size()>0){
				tMeServiceApplication.setGsHelpers(new ArrayList<GSHelperDetails>());
				for(LimitedTradeMark limTm: tMeServiceApplication.getTradeMarks()){
					if(!limTm.getApplicationExtent().equals(ApplicationExtent.OTHER)){
						GSHelperDetails hd = new GSHelperDetails();
						hd.setTmApplicationNumber(limTm.getApplicationNumber());
						hd.setApplicationExtent(limTm.getApplicationExtent());
						hd.setClassDescriptions(limTm.getLimitedClassDescriptions());
						hd.setGoodsServicesComment(limTm.getGoodsServicesComment());
						tMeServiceApplication.getGsHelpers().add(hd);
					}
					limTm.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
					limTm.setLimitedClassDescriptions(limTm.getClassDescriptions());
					limTm.setGoodsServicesComment(null);
				}
			}
		} else if(tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_LICENCE")){
			if(tMeServiceApplication.getTradeMarks() != null && tMeServiceApplication.getTradeMarks().size()>0){
				if(!tMeServiceApplication.getTradeMarks().get(0).getApplicationExtent().equals(ApplicationExtent.OTHER)){
					GSHelperDetails hd = new GSHelperDetails();
					hd.setTmApplicationNumber(tMeServiceApplication.getTradeMarks().get(0).getApplicationNumber());
					hd.setApplicationExtent(tMeServiceApplication.getTradeMarks().get(0).getApplicationExtent());
					hd.setClassDescriptions(tMeServiceApplication.getTradeMarks().get(0).getLimitedClassDescriptions());
					hd.setGoodsServicesComment(tMeServiceApplication.getTradeMarks().get(0).getGoodsServicesComment());
					tMeServiceApplication.getLicences().get(0).setGsHelper(hd);
				}
				tMeServiceApplication.getTradeMarks().get(0).setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
				tMeServiceApplication.getTradeMarks().get(0).setLimitedClassDescriptions(tMeServiceApplication.getTradeMarks().get(0).getClassDescriptions());
				tMeServiceApplication.getTradeMarks().get(0).setGoodsServicesComment(null);
			}
		} else if(tMeServiceApplication.getApplicationType().equalsIgnoreCase("TM_APPEAL")){
			if(tMeServiceApplication.getTradeMarks() != null && tMeServiceApplication.getTradeMarks().size()>0){
				if(!tMeServiceApplication.getTradeMarks().get(0).getApplicationExtent().equals(ApplicationExtent.OTHER)){
					GSHelperDetails hd = new GSHelperDetails();
					hd.setTmApplicationNumber(tMeServiceApplication.getTradeMarks().get(0).getApplicationNumber());
					hd.setApplicationExtent(tMeServiceApplication.getTradeMarks().get(0).getApplicationExtent());
					hd.setClassDescriptions(tMeServiceApplication.getTradeMarks().get(0).getLimitedClassDescriptions());
					hd.setGoodsServicesComment(tMeServiceApplication.getTradeMarks().get(0).getGoodsServicesComment());
					tMeServiceApplication.getAppeals().get(0).setGsHelper(hd);
				}
				tMeServiceApplication.getTradeMarks().get(0).setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
				tMeServiceApplication.getTradeMarks().get(0).setLimitedClassDescriptions(tMeServiceApplication.getTradeMarks().get(0).getClassDescriptions());
				tMeServiceApplication.getTradeMarks().get(0).setGoodsServicesComment(null);
			}
		}

		OppositionDetailsType oppositionDetailsType = ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails();
		if (oppositionDetailsType != null
				&& oppositionDetailsType.getOpposition()!=null
				&& oppositionDetailsType.getOpposition().size()>0
				&& oppositionDetailsType.getOpposition().get(0)!=null
				&& oppositionDetailsType.getOpposition().get(0).getOppositionBasisDetails()!=null
				&& oppositionDetailsType.getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis()!=null
				&& oppositionDetailsType.getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().size()>0){
			List <OppositionGround> opGroundList = new ArrayList<OppositionGround>();

			for (OppositionBasisType opBasisType : oppositionDetailsType.getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis()){
				List <AttachedDocument> evidenceDocuments = new ArrayList<AttachedDocument>();
				List <AttachedDocument> explanationsDocuments = new ArrayList<AttachedDocument>();
				List <AttachedDocument> proposalDecideDocuments = new ArrayList<AttachedDocument>();
				List <AttachedDocument> reputationClaimDocuments = new ArrayList<AttachedDocument>();
				if (opBasisType.getOppositionBasisDocumentDetails()!=null){
					for (Document document: opBasisType.getOppositionBasisDocumentDetails().getOppositionDocument()){


						AttachedDocument attachedDocument = new AttachedDocument();
						attachedDocument = mapper.map(document, AttachedDocument.class);
						if (!StringUtils.isEmpty(attachedDocument.getDocumentKind())){
							if (attachedDocument.getDocumentKind().equalsIgnoreCase(DocumentKind.GROUNDS_EVIDENCE.value())) {
								evidenceDocuments.add(attachedDocument);
							} else if (attachedDocument.getDocumentKind().equalsIgnoreCase(DocumentKind.EXPLANATION_OF_GROUNDS.value())) {
								explanationsDocuments.add(attachedDocument);
							} else if (attachedDocument.getDocumentKind().equalsIgnoreCase(DocumentKind.PROPOSAL_AS_TO_DECIDE.value())) {
								proposalDecideDocuments.add(attachedDocument);
							} else if (attachedDocument.getDocumentKind().equalsIgnoreCase(DocumentKind.REPUTATION_CLAIM.value())) {
								reputationClaimDocuments.add(attachedDocument);
							}
						}
					}
				}
				LegalActVersion legalActVersion = new LegalActVersion();
				List<LawArticle> articles = new ArrayList<LawArticle>();
				String nameVersion = "";
				String codeVersion = "";
				for (OppositionGroundArticleType opGroundArticle : opBasisType.getOppositionGroundArticle()){
					LawArticle article = new LawArticle();
					if(opGroundArticle.getOppositionGroundArticleReference()!= null){
						article.setReference(opGroundArticle.getOppositionGroundArticleReference().getValue());
					}
					if(opGroundArticle.getOppositionGroundArticleText() != null){
						article.setText(opGroundArticle.getOppositionGroundArticleText().getValue());
						article.setLanguage(opGroundArticle.getOppositionGroundArticleText().getLanguage());
					}
					if (StringUtils.isEmpty(nameVersion)) {
						nameVersion = opGroundArticle.getOppositionGroundLegalAct().getValue();
					}
					if (StringUtils.isEmpty(codeVersion)) {
						codeVersion = opGroundArticle.getOppositionGroundLegalAct().getLanguage();
					}
					articles.add(article);
				}
				legalActVersion.setNameVersion(nameVersion);
				legalActVersion.setCodeVersion(codeVersion);
				legalActVersion.setArticles(articles);



				if (opBasisType.getOppositionBasisText().equalsIgnoreCase(GroundCategoryKind.ABSOLUTE_GROUNDS.toString())){
					OppositionAbsoluteGrounds opAbsoluteGround = new OppositionAbsoluteGrounds();
					opAbsoluteGround.setEvidenceDocuments(evidenceDocuments);
					opAbsoluteGround.setExplanationsDocuments(explanationsDocuments);
					opAbsoluteGround.setProposalDecideDocuments(proposalDecideDocuments);
					if (opBasisType.getOppositionBasisComment()!=null) {
						opAbsoluteGround.setExplanationText(opBasisType.getOppositionBasisComment().getValue());
					}
					opAbsoluteGround.setGlobalDocuments(null);
					opAbsoluteGround.setGroundCategory(GroundCategoryKind.ABSOLUTE_GROUNDS);
					opAbsoluteGround.setLegalActVersion(legalActVersion);
					opGroundList.add(opAbsoluteGround);
				}
				if (opBasisType.getOppositionBasisText().equalsIgnoreCase(GroundCategoryKind.REVOCATION_GROUNDS.toString())){
					OppositionRevocationGrounds opRevocationGround = new OppositionRevocationGrounds();
					opRevocationGround.setEvidenceDocuments(evidenceDocuments);
					opRevocationGround.setExplanationsDocuments(explanationsDocuments);
					opRevocationGround.setProposalDecideDocuments(proposalDecideDocuments);
					if (opBasisType.getOppositionBasisComment()!=null){
						opRevocationGround.setExplanationText(opBasisType.getOppositionBasisComment().getValue());
					}
					opRevocationGround.setGlobalDocuments(null);
					opRevocationGround.setGroundCategory(GroundCategoryKind.REVOCATION_GROUNDS);
					opRevocationGround.setLegalActVersion(legalActVersion);
					opGroundList.add(opRevocationGround);
				}
				if (opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.BUSINESS_NAME.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.CONTESTED_APPLICATION.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.EARLIER_TRADE_MARK.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.FOREIGN_TRADEMARK.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.INDUSTRIAL_PROPERTY_RIGHT.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.NON_REGISTERED.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.OTHER_RIGHTS.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.PRIVACY_RIGHT.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.PROPRIETARY_MEDICINAL_PRODUCT.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.RIGHTS_AUTOR.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.WELL_KNOW.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.AGENT_APPLIED.toString())||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.BAD_FAITH.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.REPUTATION.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.RIGHTS_PORTRAIT_NAME.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.RIGHTS_PLANT_VARIATION.toString()) ||
						opBasisType.getOppositionBasisText().equalsIgnoreCase(EarlierEntitlementRightKind.EARLIER_GI.toString())){
					OppositionRelativeGrounds opRelativeGround = new OppositionRelativeGrounds();
					opRelativeGround = mapper.map(opBasisType, OppositionRelativeGrounds.class);
					LimitedTradeMark earlier = opRelativeGround.getEarlierTradeMarkDetails();
					if(earlier != null){
						final String pozNumber = StringUtils.isNotEmpty(earlier.getApplicationNumber()) ? earlier.getApplicationNumber() : earlier.getRegistrationNumber();

						List<MarkRecord> numberMatchingRecords = ext.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getMarkRecordDetails().getMarkRecord()
							.stream().filter(rec -> rec.getBasicRecord().getTradeMarkKeyDetails()!= null && rec.getBasicRecord().getTradeMarkKeyDetails().getTradeMarkKey().get(0).getIdentifier().get(0).getValue().equalsIgnoreCase(pozNumber)).collect(Collectors.toList());

						Optional<MarkRecord> optionalMarkRecord = Optional.empty();
						if(numberMatchingRecords != null && numberMatchingRecords.size() == 1){
							optionalMarkRecord = Optional.of(numberMatchingRecords.get(0));
						} else if(numberMatchingRecords.size() > 1){
							optionalMarkRecord = numberMatchingRecords.stream().filter(rec -> rec.getBasicRecord().getRecordReference()!= null && rec.getBasicRecord().getRecordReference().equals(opBasisType.getOppositionBasisText())).findFirst();
						}

						if(optionalMarkRecord.isPresent()){
							MarkRecord markRecord = optionalMarkRecord.get();
							if (ApplicationExtent.ALL_GOODS_AND_SERVICES.value().equalsIgnoreCase(markRecord.getBasicRecord().getRecordExtent())) {
								earlier.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
							} else if (ApplicationExtent.PARTIAL_GOODS_AND_SERVICES.value().equalsIgnoreCase(markRecord.getBasicRecord().getRecordExtent())) {
								earlier.setApplicationExtent(ApplicationExtent.PARTIAL_GOODS_AND_SERVICES);
							}
							List <eu.ohim.sp.filing.domain.tm.ClassDescription> extLimitedGS = new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>();
							String gsComment = null;
							if (markRecord.getBasicRecord().getGoodsServicesLimitationDetails()!=null
								&& markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation()!=null
								&& markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().size()>0) {
								extLimitedGS.addAll(markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getLimitationClassDescriptionDetails().getClassDescription());
								if( markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getComment() != null){
									gsComment =markRecord.getBasicRecord().getGoodsServicesLimitationDetails().getGoodsServicesLimitation().get(0).getComment().getValue();
								}
							}
							ArrayList<ClassDescription> coreLimitedGS = new ArrayList<ClassDescription>();
							for(eu.ohim.sp.filing.domain.tm.ClassDescription extCS : extLimitedGS){
								ClassDescription coreCS = classDescriptionsConverter.fspClassDescriptionToCoreGoodsServices(extCS);
								coreLimitedGS.add(coreCS);
							}

							earlier.setLimitedClassDescriptions(coreLimitedGS);
							earlier.setGoodsServicesComment(gsComment);
						}
					}


					opRelativeGround.setEvidenceDocuments(evidenceDocuments);
					opRelativeGround.setExplanationsDocuments(explanationsDocuments);
					opRelativeGround.setProposalDecideDocuments(proposalDecideDocuments);

					opRelativeGround.setGlobalDocuments(null);
					opRelativeGround.setGroundCategory(GroundCategoryKind.RELATIVE_GROUNDS);
					opRelativeGround.setLegalActVersion(legalActVersion);
					if(opBasisType.getEarlierRight() != null && opBasisType.getEarlierRight().size() > 0){
						opRelativeGround.setReputationClaimed(opBasisType.getEarlierRight().get(0).isReputationClaimIndicator());
					}
					opRelativeGround.setReputationClaimDocuments(reputationClaimDocuments);
					opGroundList.add(opRelativeGround);
				}

			}
			tMeServiceApplication.setOppositionGrounds(opGroundList);

		}

		return tMeServiceApplication;
    }

    @Override
    public void setMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }
	/* (non-Javadoc)
	 * @see org.dozer.DozerConverter#convertTo(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Transaction convertTo(TMeServiceApplication core, Transaction ext) {

		Transaction transaction = new Transaction();
		transaction.setTransactionHeader(new TransactionHeaderType());
		transaction.getTransactionHeader().setSenderDetails(new SenderDetailsType());
		transaction.getTransactionHeader().getSenderDetails().setRequestXSDVersion(TransactionInformation.TM_XSD_VERSION);
		transaction.getTransactionHeader().getSenderDetails().setLoginInformation(new LoginInformationType(core.getUser(), null, null));
		transaction.setTradeMarkTransactionBody(new ArrayList<TransactionBody>());
		TransactionBody transactionBody = new TransactionBody(null, new TransactionContentDetails());
		transactionBody.getTransactionContentDetails().setTransactionCode(ApplicationBasicKind.TRADEMARK_SERVICES_EFILING.value());
		transactionBody.getTransactionContentDetails().setTransactionData(new TransactionData());

		if(core.getOppositionGrounds() != null){

			int i = 0;
			for(OppositionGround ground: core.getOppositionGrounds()){
				List<AttachedDocument> attDocs = new ArrayList<AttachedDocument>();
				if(ground.getEvidenceDocuments() != null){
					attDocs.addAll(ground.getEvidenceDocuments());
				}
				if(ground.getExplanationsDocuments() != null){
					attDocs.addAll(ground.getExplanationsDocuments());
				}
				if(ground.getProposalDecideDocuments() != null){
					attDocs.addAll(ground.getProposalDecideDocuments());
				}
				if(attDocs.size() >0){
					ground.setGlobalDocuments(attDocs);
				}

				//Adding a service url for the tmdetails where no appid or regid is provided
				if(ground instanceof OppositionRelativeGrounds){
					OppositionRelativeGrounds rel = (OppositionRelativeGrounds)ground;
					if(rel.getEarlierTradeMarkDetails() != null && ((rel.getEarlierTradeMarkDetails().getApplicationNumber() == null && rel.getEarlierTradeMarkDetails().getRegistrationNumber() == null)
							|| (rel.getEarlierTradeMarkDetails().getApplicationNumber() != null &&  rel.getEarlierTradeMarkDetails().getApplicationNumber().contains("EfilingRelGroundsServiceId")))){
						rel.getEarlierTradeMarkDetails().setApplicationNumber("EfilingRelGroundsServiceId-"+i);
						i++;
					}

				}
			}

		}

		if(core.getApplicationType().equalsIgnoreCase("TM_SURRENDER") || core.getApplicationType().equalsIgnoreCase("TM_WITHDRAWAL")||
				core.getApplicationType().equalsIgnoreCase("TM_RENEWAL")|| core.getApplicationType().equalsIgnoreCase("TM_TRANSFER")
				|| core.getApplicationType().equalsIgnoreCase("TM_OPPOSITION")|| core.getApplicationType().equalsIgnoreCase("TM_TOBJECTION")
				|| core.getApplicationType().equalsIgnoreCase("TM_REVOCATION")|| core.getApplicationType().equalsIgnoreCase("TM_INVALIDITY")
		){
			if(core.getGsHelpers() != null && core.getGsHelpers().size() > 0 && core.getTradeMarks() != null && core.getTradeMarks().size() >0 ){
				List<LimitedTradeMark> tmsProcessed = new ArrayList<>();
				for(GSHelperDetails hDetail: core.getGsHelpers()){
					for(LimitedTradeMark limited: core.getTradeMarks()){
						if(limited.getApplicationNumber() != null && hDetail.getTmApplicationNumber() != null &&
								limited.getApplicationNumber().equals(hDetail.getTmApplicationNumber())){
							limited.setApplicationExtent(hDetail.getApplicationExtent());
							limited.setLimitedClassDescriptions(hDetail.getClassDescriptions());
							limited.setGoodsServicesComment(hDetail.getGoodsServicesComment());
							tmsProcessed.add(limited);
						} else if(!tmsProcessed.contains(limited)){
							limited.setApplicationExtent(ApplicationExtent.OTHER);
						}
					}
				}
				tmsProcessed = null;
			} else if(core.getTradeMarks() != null && core.getTradeMarks().size()>0){
				for(LimitedTradeMark limited: core.getTradeMarks()){
					limited.setApplicationExtent(ApplicationExtent.OTHER);
				}
			}
		} else if(core.getApplicationType().equalsIgnoreCase("TM_LICENCE")){
			if(core.getLicences() != null && core.getLicences().size()>0 && core.getTradeMarks() != null && core.getTradeMarks().size()>0){
				for(LimitedTradeMark limited: core.getTradeMarks()){
					if(limited.getApplicationNumber() != null && core.getLicences().get(0).getGsHelper().getTmApplicationNumber() != null &&
							limited.getApplicationNumber().equals(core.getLicences().get(0).getGsHelper().getTmApplicationNumber())){
						limited.setApplicationExtent(core.getLicences().get(0).getGsHelper().getApplicationExtent());
						limited.setLimitedClassDescriptions(core.getLicences().get(0).getGsHelper().getClassDescriptions());
						limited.setGoodsServicesComment(core.getLicences().get(0).getGsHelper().getGoodsServicesComment());
					}
				}

			}
			else if(core.getTradeMarks() != null && core.getTradeMarks().size()>0){
				for(LimitedTradeMark limited: core.getTradeMarks()){
					limited.setApplicationExtent(ApplicationExtent.OTHER);
				}
			}
		} else if(core.getApplicationType().equalsIgnoreCase("TM_APPEAL")){
			if(core.getAppeals() != null && core.getAppeals().size()>0 && core.getTradeMarks() != null && core.getTradeMarks().size()>0){
				for(LimitedTradeMark limited: core.getTradeMarks()){
					if(limited.getApplicationNumber() != null && core.getAppeals().get(0).getGsHelper().getTmApplicationNumber() != null &&
						limited.getApplicationNumber().equals(core.getAppeals().get(0).getGsHelper().getTmApplicationNumber())){
						limited.setApplicationExtent(core.getAppeals().get(0).getGsHelper().getApplicationExtent());
						limited.setLimitedClassDescriptions(core.getAppeals().get(0).getGsHelper().getClassDescriptions());
						limited.setGoodsServicesComment(core.getAppeals().get(0).getGsHelper().getGoodsServicesComment());
					}
				}

			}
			else if(core.getTradeMarks() != null && core.getTradeMarks().size()>0){
				for(LimitedTradeMark limited: core.getTradeMarks()){
					limited.setApplicationExtent(ApplicationExtent.OTHER);
				}
			}
		}

		eu.ohim.sp.filing.domain.tm.TradeMarkServicesApplication extApp = mapper.map(core, eu.ohim.sp.filing.domain.tm.TradeMarkServicesApplication.class);

		//Cleaning duplicates that are created with dozer for some reason TODO: fix the dozer mapping not to create duplicates
		if(extApp.getMarkRecordDetails() != null && extApp.getMarkRecordDetails().getMarkRecord() != null && extApp.getMarkRecordDetails().getMarkRecord().size()>0) {
			Set<MarkRecord> setRecords = new HashSet<MarkRecord>(extApp.getMarkRecordDetails().getMarkRecord());
			Set<MarkRecord> toRemove = new HashSet<MarkRecord>();
			for(MarkRecord record: setRecords){
				if(record.getBasicRecord() == null && record.getRecordLicence() == null && record.getRecordAppeal() == null
						&& record.getRecordSecurityMeasure() == null){
					toRemove.add(record);
				}
			}
			setRecords.removeAll(toRemove);
			extApp.getMarkRecordDetails().getMarkRecord().clear();
			extApp.getMarkRecordDetails().getMarkRecord().addAll(setRecords);


		}
		extApp.setApplicationFormName(core.getApplicationType());
		extApp.setRequestSoftware(
				new RequestSoftwareType(TransactionInformation.REQUEST_SOFTWARE_NAME, TransactionInformation.REQUEST_SOFTWARE_VERSION));

		if (extApp.getTradeMarkDetails()!=null && extApp.getTradeMarkDetails().getTradeMark()!=null && extApp.getTradeMarkDetails().getTradeMark().size()>0){
			for(int i=0; i<extApp.getTradeMarkDetails().getTradeMark().size(); i++){
				if (extApp.getTradeMarkDetails().getTradeMark().get(i).getGoodsServicesDetails()==null){
					extApp.getTradeMarkDetails().getTradeMark().get(i).setGoodsServicesDetails(new GoodsServicesDetails());
				}
			}
		}

		transactionBody.getTransactionContentDetails().getTransactionData().setTradeMarkServicesApplication(extApp);
		transaction.getTradeMarkTransactionBody().add(transactionBody);
		return transaction;
	}

   
}
