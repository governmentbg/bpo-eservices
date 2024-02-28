/*
 *  FspDomain:: OppositionRevocationGroundsTest 15/11/13 15:58 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter2;

import eu.ohim.sp.core.domain.converter.tm.GroundCategoryKindConverter;
import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;
import eu.ohim.sp.core.domain.opposition.OppositionRevocationGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.filing.domain.utils.MapperFactory;
import eu.ohim.sp.filing.domain.utils.FOTransactionUtil;
import eu.ohim.sp.filing.domain.tm.OppositionBasisType;
import eu.ohim.sp.filing.domain.tm.TradeMark;
import eu.ohim.sp.filing.domain.tm.TradeMarkDetails;
import eu.ohim.sp.filing.domain.tm.Transaction;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: KARALCH
 * Date: 30/10/13
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
//**** TO BE FIXED problems found on the languages, LegalActVersion works one way not the other way round
public class OppositionRevocationGroundsTest {

    public static final Logger LOGGER = Logger.getLogger(OppositionRevocationGrounds.class);

    public static OppositionRevocationGrounds fillOppositionRevocationGrounds() {
        PodamFactory factory = new PodamFactoryImpl(RandomDataProviderStrategy.getInstance(1));
        OppositionRevocationGrounds oppositionRevocationGrounds = factory.manufacturePojo(OppositionRevocationGrounds.class);
        oppositionRevocationGrounds.setGroundCategory(GroundCategoryKind.REVOCATION_GROUNDS);
        
        return oppositionRevocationGrounds;
    }
    
    @Test
    public void testConvertOppositionRevocationGrounds() {
        OppositionRevocationGrounds oppositionAbsoluteGrounds = fillOppositionRevocationGrounds();
    
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();


        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionAbsoluteGrounds);

        Transaction transaction = (Transaction) ((FOTransactionUtil) MapperFactory.getTMEservice()).createTransactionInformation(tMeServiceApplication, "", true);


        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);


        Assert.assertNotNull(oppositionBasisType);

        // **** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getLanguage()
        //    , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getLanguage()
                , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        //**** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getLanguage()
        //        , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getValue()
                , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getReference());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getValue()
                , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getText());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getValue()
                , oppositionAbsoluteGrounds.getLegalActVersion().getNameVersion());
        Assert.assertEquals(oppositionBasisType.getOppositionBasisComment().getValue()
                , oppositionAbsoluteGrounds.getExplanationText());
        GroundCategoryKindConverter groundCategoryKindConverter = new GroundCategoryKindConverter();
        Assert.assertEquals(oppositionBasisType.getOppositionBasisCode()
                , groundCategoryKindConverter.convert(String.class, oppositionAbsoluteGrounds.getGroundCategory()));
        Assert.assertEquals(oppositionBasisType.getOppositionBasisText()
                , oppositionAbsoluteGrounds.getGroundCategory().toString());

        try {
            LOGGER.error(new String(MapperFactory.getTMEservice().generateByte(tMeServiceApplication, "", false), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testConvertOppositionRevocationGroundsNull() {
        OppositionRevocationGrounds oppositionRevocationGrounds = fillOppositionRevocationGrounds();

        oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).setText(null);

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();

        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionRevocationGrounds);

        Transaction transaction = (Transaction) ((FOTransactionUtil) MapperFactory.getTMEservice()).createTransactionInformation(tMeServiceApplication, "", true);


        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);


        Assert.assertNotNull(oppositionBasisType);

        // **** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getLanguage()
        //    , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getLanguage()
                , oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        //**** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getLanguage()
        //        , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getValue()
                , oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).getReference());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getValue()
                , oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).getText());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getValue()
                , oppositionRevocationGrounds.getLegalActVersion().getNameVersion());
        Assert.assertEquals(oppositionBasisType.getOppositionBasisComment().getValue()
                , oppositionRevocationGrounds.getExplanationText());
        GroundCategoryKindConverter groundCategoryKindConverter = new GroundCategoryKindConverter();
        Assert.assertEquals(oppositionBasisType.getOppositionBasisCode()
                , groundCategoryKindConverter.convert(String.class, oppositionRevocationGrounds.getGroundCategory()));
        Assert.assertEquals(oppositionBasisType.getOppositionBasisText()
                , oppositionRevocationGrounds.getGroundCategory().toString());

        try {
            LOGGER.error(new String(MapperFactory.getTMEservice().generateByte(tMeServiceApplication, "", false), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testConvertOppositionRevocationGroundsReverse() {
        OppositionRevocationGrounds oppositionRevocationGrounds = fillOppositionRevocationGrounds();

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();


        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionRevocationGrounds);

        Transaction transaction = (Transaction) ((FOTransactionUtil) MapperFactory.getTMEservice()).createTransactionInformation(tMeServiceApplication, "", true);
        transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().setTradeMarkDetails(new TradeMarkDetails());
        transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getTradeMarkDetails().setTradeMark(new ArrayList<TradeMark>());

        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);

        Assert.assertNotNull(oppositionBasisType);

        TMeServiceApplication tMeServiceApplication1 = (TMeServiceApplication) ((FOTransactionUtil) MapperFactory.getTMEservice()).retrieveIPApplication(transaction, true);

        Assert.assertTrue(tMeServiceApplication1.getOppositionGrounds().get(0) instanceof OppositionRevocationGrounds);
        oppositionRevocationGrounds = (OppositionRevocationGrounds) tMeServiceApplication1.getOppositionGrounds().get(0);

        // **** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getLanguage()
        //    , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getLanguage()
                , oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        //**** Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getLanguage()
        //        , oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).getLanguage());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleReference().getValue()
                , oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).getReference());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundArticleText().getValue()
                , oppositionRevocationGrounds.getLegalActVersion().getArticles().get(0).getText());
        Assert.assertEquals(oppositionBasisType.getOppositionGroundArticle().get(0).getOppositionGroundLegalAct().getValue()
                , oppositionRevocationGrounds.getLegalActVersion().getNameVersion());
        Assert.assertEquals(oppositionBasisType.getOppositionBasisComment().getValue()
                , oppositionRevocationGrounds.getExplanationText());
        GroundCategoryKindConverter groundCategoryKindConverter = new GroundCategoryKindConverter();
        Assert.assertEquals(oppositionBasisType.getOppositionBasisCode()
                , groundCategoryKindConverter.convert(String.class, oppositionRevocationGrounds.getGroundCategory()));
        Assert.assertEquals(oppositionBasisType.getOppositionBasisText()
                , oppositionRevocationGrounds.getGroundCategory().toString());



        try {
            LOGGER.error(new String(MapperFactory.getTMEservice().generateByte(tMeServiceApplication, "", false), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
