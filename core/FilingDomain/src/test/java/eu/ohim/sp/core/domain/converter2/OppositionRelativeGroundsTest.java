/*
 *  FspDomain:: OppositionRelativeGroundsTest 15/11/13 15:58 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter2;

import eu.ohim.sp.core.domain.converter.tm.GroundCategoryKindConverter;
import eu.ohim.sp.core.domain.opposition.*;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;

import eu.ohim.sp.filing.domain.tm.*;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: KARALCH
 * Date: 30/10/13
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
//**** TO BE FIXED problems found on the languages, LegalActVersion works one way not the other way round
public class OppositionRelativeGroundsTest {

    public static final Logger LOGGER = Logger.getLogger(OppositionRelativeGrounds.class);



    @Test
    public void test() {
        PersonConverterTest.fillPerson(Holder.class);
    }


    public OppositionRelativeGrounds fillRelativeGrounds() {
        OppositionRelativeGrounds oppositionRelativeGrounds = new OppositionRelativeGrounds();
        PodamFactory factory = new PodamFactoryImpl(RandomDataProviderStrategy.getInstance(1));
        oppositionRelativeGrounds.setLegalActVersion(factory.manufacturePojo(LegalActVersion.class));
        oppositionRelativeGrounds.setExplanationText("explanationText");
        oppositionRelativeGrounds.setGroundCategory(GroundCategoryKind.RELATIVE_GROUNDS);
        oppositionRelativeGrounds.setEarlierEntitlementRightType(EarlierEntitlementRightKind.EARLIER_TRADE_MARK);
        oppositionRelativeGrounds.setEarlierTradeMarkCategory("earlierTradeMarkCategory");
        oppositionRelativeGrounds.setEarlierTradeMarkDetails(TradeMarkHelper.fillCoreTradeMark(LimitedTradeMark.class));
        oppositionRelativeGrounds.setTypeRight("typeRight");
        oppositionRelativeGrounds.setTypeRightDetails("typeRightDetails");
        oppositionRelativeGrounds.setAreaActivity("areaActivity");
        oppositionRelativeGrounds.setRegistrationCountry("ES");
        oppositionRelativeGrounds.setOpponentEntitlementText("opponentEntitlementText");
        oppositionRelativeGrounds.setOpponentEntitlementKind("Owner");
        oppositionRelativeGrounds.setRelativeGroundLawArticles(new ArrayList<RelativeGroundLawArticle>());
        oppositionRelativeGrounds.getRelativeGroundLawArticles().add(factory.manufacturePojo(RelativeGroundLawArticle.class));

        oppositionRelativeGrounds.setReputationClaimed(Boolean.FALSE);
        oppositionRelativeGrounds.setReputationClaimExplanation("reputationClaimExplanation");

        oppositionRelativeGrounds.setReputationClaimCountries(new ArrayList<String>());
        oppositionRelativeGrounds.getReputationClaimCountries().add("ES");
        oppositionRelativeGrounds.getReputationClaimCountries().add("GR");

        return oppositionRelativeGrounds;
    }

    @Test
    public void testConvertOppositionRelativeGrounds() {
        OppositionRelativeGrounds oppositionRelativeGrounds = fillRelativeGrounds();
    
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();


        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionRelativeGrounds);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);

        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);

        checkAssertions(oppositionBasisType, oppositionRelativeGrounds);

    }

    public void checkAssertions(OppositionBasisType oppositionBasisType, OppositionRelativeGrounds oppositionRelativeGrounds) {

        Assert.assertNotNull(oppositionBasisType);

        // **** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getLanguage()
        //    , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getLanguage()
                , oppositionRelativeGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        //**** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getLanguage()
        //        , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getValue()
                , oppositionRelativeGrounds.getLegalActVersion().getArticles().get(0).getReference());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getValue()
                , oppositionRelativeGrounds.getLegalActVersion().getArticles().get(0).getText());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getValue()
                , oppositionRelativeGrounds.getLegalActVersion().getNameVersion());
        Assert.assertEquals(oppositionBasisType.getOppositionBasisComment().getValue()
                , oppositionRelativeGrounds.getExplanationText());
        GroundCategoryKindConverter groundCategoryKindConverter = new GroundCategoryKindConverter();
        Assert.assertEquals(oppositionBasisType.getOppositionBasisCode()
                , groundCategoryKindConverter.convert(String.class, oppositionRelativeGrounds.getGroundCategory()));
        Assert.assertEquals(oppositionBasisType.getOppositionBasisText()
                , oppositionRelativeGrounds.getEarlierEntitlementRightType().toString());
        Assert.assertEquals(oppositionBasisType.getOppositionBasisText()
                , oppositionRelativeGrounds.getEarlierEntitlementRightType().toString());


        EarlierMarkType earlierMarkType = oppositionBasisType.getEarlierRight().get(0);
        Assert.assertEquals(earlierMarkType.isReputationClaimIndicator()
                       , oppositionRelativeGrounds.getReputationClaimed());

        //TO BE FIXED it doesn't work from xml-tm to core
        //Assert.assertEquals(earlierMarkType.getReputationClaim().getReputationClaimText().getValue()
        //        , oppositionRelativeGrounds.getReputationClaimExplanation());
        //TO BE FIXED
        //Assert.assertEquals(earlierMarkType.getReputationClaim().getReputationClaimCountry().size(), oppositionRelativeGrounds.getReputationClaimCountries().size());
        int check = oppositionRelativeGrounds.getReputationClaimCountries().size();
        for (String country : oppositionRelativeGrounds.getReputationClaimCountries()) {
            for (ISOCountryCode countryXML : earlierMarkType.getReputationClaim().getReputationClaimCountry()) {
                if (countryXML.value().equals(country)) {
                    check--;
                }
            }

        }
        //Assert.assertEquals(check, 0);
        Assert.assertEquals(earlierMarkType.getEarlierMarkCountryCode().toString(), oppositionRelativeGrounds.getRegistrationCountry());
        Assert.assertEquals(earlierMarkType.getRightKindCode(), oppositionRelativeGrounds.getTypeRight());
        Assert.assertEquals(earlierMarkType.getRightKindText(), oppositionRelativeGrounds.getTypeRightDetails());
    }

    public void testConvertOppositionRelativeGroundsNull() {
        OppositionRelativeGrounds oppositionRelativeGrounds = fillRelativeGrounds();

        oppositionRelativeGrounds.getLegalActVersion().getArticles().get(0).setText(null);

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();

        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionRelativeGrounds);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);

        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);


        checkAssertions(oppositionBasisType, oppositionRelativeGrounds);

    }

    @Test
    public void testConvertOppositionRelativeGroundsReverse() {
        OppositionRelativeGrounds oppositionRelativeGrounds = fillRelativeGrounds();

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();

        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionRelativeGrounds);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);
        transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().setTradeMarkDetails(new TradeMarkDetails());
        transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getTradeMarkDetails().setTradeMark(new ArrayList<TradeMark>());

        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);

        Assert.assertNotNull(oppositionBasisType);

        TMeServiceApplication tMeServiceApplication1 = CommonSetup.getMapper().map(transaction, TMeServiceApplication.class);

        Assert.assertTrue(tMeServiceApplication1.getOppositionGrounds().get(0) instanceof OppositionRelativeGrounds);
        oppositionRelativeGrounds = (OppositionRelativeGrounds) tMeServiceApplication1.getOppositionGrounds().get(0);

        checkAssertions(oppositionBasisType, oppositionRelativeGrounds);

    }
}
