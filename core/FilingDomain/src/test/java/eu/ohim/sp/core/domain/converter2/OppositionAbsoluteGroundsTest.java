/*
 *  FspDomain:: OppositionAbsoluteGroundsTest 15/11/13 15:58 karalch $
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
import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;

import eu.ohim.sp.filing.domain.tm.OppositionBasisType;
import eu.ohim.sp.filing.domain.tm.TradeMark;
import eu.ohim.sp.filing.domain.tm.TradeMarkDetails;
import eu.ohim.sp.filing.domain.tm.Transaction;
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
public class OppositionAbsoluteGroundsTest {

    public static final Logger LOGGER = Logger.getLogger(OppositionAbsoluteGrounds.class);

    public static OppositionAbsoluteGrounds fillOppositionAbsoluteGrounds() {
        PodamFactory factory = new PodamFactoryImpl(RandomDataProviderStrategy.getInstance(1));
        OppositionAbsoluteGrounds oppositionAbsoluteGrounds = factory.manufacturePojo(OppositionAbsoluteGrounds.class);
        oppositionAbsoluteGrounds.setGroundCategory(GroundCategoryKind.ABSOLUTE_GROUNDS);
        
        return oppositionAbsoluteGrounds;
    }
    
    @Test
    public void testConvertOppositionAbsoluteGrounds() {
        OppositionAbsoluteGrounds oppositionAbsoluteGrounds = fillOppositionAbsoluteGrounds();
    
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();


        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionAbsoluteGrounds);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);

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

    }

    @Test
    public void testConvertOppositionAbsoluteGroundsNull() {
        OppositionAbsoluteGrounds oppositionAbsoluteGrounds = fillOppositionAbsoluteGrounds();

        oppositionAbsoluteGrounds.getLegalActVersion().getArticles().get(0).setText(null);

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();

        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionAbsoluteGrounds);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);

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

    }

    @Test
    public void testConvertOppositionAbsoluteGroundsReverse() {
        OppositionAbsoluteGrounds oppositionAbsoluteGrounds = fillOppositionAbsoluteGrounds();

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();


        tMeServiceApplication.setOppositionGrounds(new ArrayList<OppositionGround>());
        tMeServiceApplication.getOppositionGrounds().add(oppositionAbsoluteGrounds);

        Transaction transaction = CommonSetup.getMapper().map(tMeServiceApplication, Transaction.class);

        transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().setTradeMarkDetails(new TradeMarkDetails());
        transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getTradeMarkDetails().setTradeMark(new ArrayList<TradeMark>());

        OppositionBasisType oppositionBasisType = transaction.getTradeMarkTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getTradeMarkServicesApplication().getOppositionDetails().getOpposition().get(0).getOppositionBasisDetails().getOppositionBasis().get(0);

        Assert.assertNotNull(oppositionBasisType);

        TMeServiceApplication tMeServiceApplication1 = CommonSetup.getMapper().map(transaction, TMeServiceApplication.class);

        Assert.assertTrue(tMeServiceApplication1.getOppositionGrounds().get(0) instanceof OppositionAbsoluteGrounds);
        oppositionAbsoluteGrounds = (OppositionAbsoluteGrounds) tMeServiceApplication1.getOppositionGrounds().get(0);

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



    }
}
