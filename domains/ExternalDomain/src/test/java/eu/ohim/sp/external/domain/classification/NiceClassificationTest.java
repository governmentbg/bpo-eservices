package eu.ohim.sp.external.domain.classification;

import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.external.domain.classification.wrapper.*;
import eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory;
import eu.ohim.sp.external.domain.classification.wrapper.OrderBy;
import eu.ohim.sp.external.domain.classification.wrapper.SearchMode;
import eu.ohim.sp.external.domain.classification.wrapper.SearchTermCriteria;
import eu.ohim.sp.external.domain.classification.wrapper.SortBy;
import eu.ohim.sp.external.domain.classification.wrapper.Term;
import eu.ohim.sp.external.domain.classification.wrapper.TermsToBeValidated;
import eu.ohim.sp.external.domain.classification.wrapper.TermsValidated;
import org.dozer.DozerBeanMapper;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 27/01/14
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class NiceClassificationTest {

    @Test
    public void testDummySearchTermCriteriaConstructor() {
        SearchTermCriteria searchTermCriteria = new SearchTermCriteria("office", "language", "term", SearchMode.FULLPHRASE,
                new ArrayList<BigInteger>(), true, BigInteger.ONE, BigInteger.ONE, SortBy.RELEVANCE, OrderBy.ASC, "taxoConceptNodeId", false);
    }

    @Test
    public void testSearchTermCriteria() {
        eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory objectFactory = new eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory();
        SearchTermCriteria searchTermCriteria = objectFactory.createSearchTermCriteria();
        searchTermCriteria.setOrderBy(OrderBy.ASC);
        searchTermCriteria.setPage(BigInteger.valueOf(1));
        searchTermCriteria.setShowMaster(false);
        searchTermCriteria.setSearchMode(SearchMode.EXACTMATCH);
        searchTermCriteria.setSize(BigInteger.valueOf(100));
        searchTermCriteria.setShowNonTaxoTermsOnly(false);
        searchTermCriteria.setTaxoConceptNodeId("node");
        searchTermCriteria.setLanguage("en");
        searchTermCriteria.setNiceClassList(new ArrayList<BigInteger>());
        searchTermCriteria.getNiceClassList().add(BigInteger.valueOf(1));
        searchTermCriteria.setOffice("EM");
        searchTermCriteria.setTerm("term");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria searchTermCriteriaCore =
                dozerBeanMapper.map(searchTermCriteria, eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria.class);


        assertEquals(searchTermCriteria.getOrderBy().value(), searchTermCriteriaCore.getOrderBy().value());
        assertEquals(searchTermCriteria.getPage().intValue(), searchTermCriteriaCore.getPage().intValue());
        assertEquals(searchTermCriteria.getSize().intValue(), searchTermCriteriaCore.getSize().intValue());
        assertEquals(searchTermCriteria.getOffice(), searchTermCriteriaCore.getOffice());
        assertEquals(searchTermCriteria.getTerm(), searchTermCriteriaCore.getTerm());
        assertEquals(searchTermCriteria.getTaxoConceptNodeId(), searchTermCriteriaCore.getTaxoConceptNodeId());
        assertEquals(searchTermCriteria.getTaxoConceptNodeId(), searchTermCriteriaCore.getTaxoConceptNodeId());
        assertEquals(searchTermCriteria.getLanguage(), searchTermCriteriaCore.getLanguage());
        assertEquals(searchTermCriteria.getNiceClassList().size(), searchTermCriteriaCore.getNiceClassList().size());
        assertEquals(searchTermCriteria.getNiceClassList().get(0).intValue(), searchTermCriteriaCore.getNiceClassList().get(0).intValue());
        assertEquals(searchTermCriteria.getSearchMode().value().toLowerCase(), searchTermCriteriaCore.getSearchMode().value().toLowerCase());
    }


    @Test
    public void testTermsValidated() {
        eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory objectFactory = new eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory();

        TermsValidated termsValidated = objectFactory.createTermsValidated();
        termsValidated.setLanguage("en");
        termsValidated.setDescription("description");
        termsValidated.setNiceClass("5");
        termsValidated.setTerms(new ArrayList<Term>());
        Term term = new Term();
        term.setLang("en");
        term.setNiceClass(5);
        term.setVerificationAssessment("assessement");
        term.setScopeAcceptability(true);
        term.setText("term text");
        term.setVerificationResult(VerifiedTermResult.HINT);
        term.setMatchedTerms(new ArrayList<MatchedTerm>());

        MatchedTerm matchedTerm = new MatchedTerm();
        matchedTerm.setMatchedTermText("matchedTermText");
        matchedTerm.setMatchClassType(MatchClassType.SAME_CLASS);
        matchedTerm.setTermSourceType("source");
        matchedTerm.setMatchedClassNumber(BigInteger.TEN);
        term.getMatchedTerms().add(matchedTerm);

        termsValidated.getTerms().add(term);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.classification.wrapper.TermsValidated termsValidatedCore =
                dozerBeanMapper.map(termsValidated, eu.ohim.sp.core.domain.classification.wrapper.TermsValidated.class);

        assertEquals(termsValidated.getLanguage(), termsValidatedCore.getLanguage());
        assertEquals(termsValidated.getDescription(), termsValidatedCore.getDescription());
        assertEquals(termsValidated.getNiceClass(), termsValidatedCore.getNiceClass());
        assertEquals(termsValidated.getTerms().size(), termsValidatedCore.getTerms().size());
        assertEquals(termsValidated.getTerms().get(0).getNiceClass(), termsValidatedCore.getTerms().get(0).getNiceClass());
        assertEquals(termsValidated.getTerms().get(0).getLang(), termsValidatedCore.getTerms().get(0).getLang());
        assertEquals(termsValidated.getTerms().get(0).getText(), termsValidatedCore.getTerms().get(0).getText());
        assertEquals(termsValidated.getTerms().get(0).getVerificationAssessment(), termsValidatedCore.getTerms().get(0).getVerificationAssessment());
        assertEquals(termsValidated.getTerms().get(0).getVerificationResult().value().toLowerCase(), termsValidatedCore.getTerms().get(0).getVerificationResult().value().toLowerCase());
        assertEquals(termsValidated.getTerms().get(0).getMatchedTerms().size(), termsValidatedCore.getTerms().get(0).getMatchedTerms().size());
        assertEquals(termsValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchedTermText(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchedTermText());
        assertEquals(termsValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchedClassNumber().intValue(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchedClassNumber().intValue());
    }

    @Test
    public void testDummyTermsValidated() {
        eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory objectFactory = new eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory();

        TermsValidated termsValidated = objectFactory.createTermsValidated();
        TermsValidated termsValidatedC = new TermsValidated("5", null, "en", "description");

        termsValidated.setLanguage("en");
        termsValidated.setDescription("description");
        termsValidated.setNiceClass("5");

        assertEquals(termsValidated.getLanguage(), termsValidatedC.getLanguage());
        assertEquals(termsValidated.getDescription(), termsValidatedC.getDescription());
        assertEquals(termsValidated.getNiceClass(), termsValidatedC.getNiceClass());
    }


    @Test
    public void testTermsToBeValidated() {
        ObjectFactory objectFactory = new ObjectFactory();
        TermsToBeValidated termsToBeValidated = objectFactory.createTermsToBeValidated();
        termsToBeValidated.setLanguage("en");
        termsToBeValidated.setNiceClass("5");
        termsToBeValidated.setOffice("EM");
        termsToBeValidated.setTerms("test; term");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated termsToBeValidatedCore =
                dozerBeanMapper.map(termsToBeValidated, eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated.class);

        assertEquals(termsToBeValidated.getLanguage(), termsToBeValidatedCore.getLanguage());
        assertEquals(termsToBeValidated.getTerms(), termsToBeValidatedCore.getTerms());
        assertEquals(termsToBeValidated.getOffice(), termsToBeValidatedCore.getOffice());
        assertEquals(termsToBeValidated.getNiceClass(), termsToBeValidatedCore.getNiceClass());

    }

    @Test
    public void testValidatedTerms() {
        ObjectFactory objectFactory = new ObjectFactory();
        TermsValidated termValidated = objectFactory.createTermsValidated();
        termValidated.setNiceClass("niceClass");
        termValidated.setLanguage("en");
        termValidated.setNiceClass("5");
        termValidated.setTerms(new ArrayList<Term>());

        termValidated.getTerms().add(objectFactory.createTerm());
        termValidated.getTerms().get(0).setText("text");
        termValidated.getTerms().get(0).setScope(new ArrayList<String>());
        termValidated.getTerms().get(0).getScope().add("sth");
        termValidated.getTerms().get(0).setVerificationResult(VerifiedTermResult.NONE);
        termValidated.getTerms().get(0).setLang("es");
        termValidated.getTerms().get(0).setNiceClass(5);
        termValidated.getTerms().get(0).setScopeAcceptability(true);
        termValidated.getTerms().get(0).setVerificationAssessment("assessement");

        termValidated.getTerms().get(0).setMatchedTerms(new ArrayList<MatchedTerm>());

        termValidated.getTerms().get(0).getMatchedTerms().add(new eu.ohim.sp.external.domain.classification.ObjectFactory().createMatchedTerm());

        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedClassNumber(BigInteger.ONE);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setTermSourceType("source");
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedTermText("term");
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchClassType(MatchClassType.NONE);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setIdentifier("identifier");
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setLegacyTermFirstUseDate(new Date());
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setLegacyTermFrequency(BigInteger.TEN);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedTermPositionInInputTerm(BigInteger.ZERO);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchRank(BigInteger.TEN);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setPartial(true);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedTermPositionInInputTermList(BigInteger.valueOf(6));
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchProximityType(MatchProximityType.MATCH_TO_VERIFY);
        termValidated.getTerms().get(0).getMatchedTerms().get(0).setLegacyTermLastUseDate(new Date());

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.classification.wrapper.TermsValidated termsValidatedCore = dozerBeanMapper.map(termValidated, eu.ohim.sp.core.domain.classification.wrapper.TermsValidated.class);

        assertEquals(termValidated.getNiceClass(), termsValidatedCore.getNiceClass());
        assertEquals(termValidated.getDescription(), termsValidatedCore.getDescription());
        assertEquals(termValidated.getLanguage(), termsValidatedCore.getLanguage());
        assertEquals(termValidated.getTerms().get(0).getVerificationAssessment(), termsValidatedCore.getTerms().get(0).getVerificationAssessment());
        assertEquals(termValidated.getTerms().get(0).getVerificationResult().value().toLowerCase(), termsValidatedCore.getTerms().get(0).getVerificationResult().value().toLowerCase());
        assertEquals(termValidated.getTerms().get(0).getText(), termsValidatedCore.getTerms().get(0).getText());
        assertEquals(termValidated.getTerms().get(0).getLang(), termsValidatedCore.getTerms().get(0).getLang());
        assertEquals(termValidated.getTerms().get(0).getScope().size(), termsValidatedCore.getTerms().get(0).getScope().size());
        assertEquals(termValidated.getTerms().get(0).getParentIds(), termsValidatedCore.getTerms().get(0).getParentIds());
        assertEquals(termValidated.getTerms().get(0).getScopeAcceptability(), termsValidatedCore.getTerms().get(0).getScopeAcceptability());
        assertEquals(termValidated.getTerms().get(0).getNiceClass(), termsValidatedCore.getTerms().get(0).getNiceClass());

        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchedClassNumber().intValue(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchedClassNumber().intValue());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getTermSourceType(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getTermSourceType());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchedTermText(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchedTermText());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchClassType().value().toLowerCase(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchClassType().value().toLowerCase());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getIdentifier(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getIdentifier());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getLegacyTermFirstUseDate(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getLegacyTermFirstUseDate());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getLegacyTermFrequency().intValue(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getLegacyTermFrequency().intValue());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchedTermPositionInInputTerm().intValue(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchedTermPositionInInputTerm().intValue());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchRank().intValue(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchRank().intValue());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).isPartial(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().isPartial());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchedTermPositionInInputTermList().intValue(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchedTermPositionInInputTermList().intValue());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getMatchProximityType().value(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getMatchProximityType().toString());
        assertEquals(termValidated.getTerms().get(0).getMatchedTerms().get(0).getLegacyTermLastUseDate(), termsValidatedCore.getTerms().get(0).getMatchedTerms().iterator().next().getLegacyTermLastUseDate());

    }

}
