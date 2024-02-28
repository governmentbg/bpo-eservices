package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.external.domain.classification.*;
import eu.ohim.sp.external.domain.classification.wrapper.*;
import eu.ohim.sp.external.domain.classification.wrapper.ObjectFactory;
import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.services.classification.nice.NiceClassificationFault;
import eu.ohim.sp.external.ws.exception.NiceClassificationFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.SOAPBinding;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@WebService(serviceName = "NiceClassificationService", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", portName = "NiceClassificationServicePort", wsdlLocation = "wsdl/NiceClassificationService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class ClassificationManagementWS implements ClassificationManagementWSInterface {

    /**
     *
     * @param termsList
     * @return
     *         returns java.util.List<eu.ohim.sp.external.services.classification.nice.Term>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "verifyListOfTerms", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.VerifyListOfTerms")
    @ResponseWrapper(localName = "verifyListOfTermsResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.VerifyListOfTermsResponse")
    public List<TermsValidated> verifyListOfTerms(
            @WebParam(name = "termsList", targetNamespace = "")  List<TermsToBeValidated> termsList)
            throws NiceClassificationFaultException {
        if (termsList==null || termsList.isEmpty()) {
            try {
                termsList.get(0);
            } catch (RuntimeException e) {
                NiceClassificationFault classificationFault = new NiceClassificationFault();
                classificationFault.setReturnedObject(new Fault());
                classificationFault.getReturnedObject().setCode("error.code");
                classificationFault.getReturnedObject().setMessage("system error");
                throw new NiceClassificationFaultException("system error", classificationFault, e);
            }
        }

        List<TermsValidated> termsValidated = new ArrayList<TermsValidated>();
        TermsValidated termValidated = new TermsValidated();
        for (TermsToBeValidated term : termsList) {
            eu.ohim.sp.external.domain.classification.ObjectFactory objectFactory = new eu.ohim.sp.external.domain.classification.ObjectFactory();
            ObjectFactory wrapperObjectFactory = new ObjectFactory();
            termValidated = wrapperObjectFactory.createTermsValidated();
            termValidated.setLanguage(term.getLanguage());
            termValidated.setNiceClass(term.getNiceClass());
            termValidated.setTerms(new ArrayList<Term>());

            termValidated.getTerms().add(wrapperObjectFactory.createTerm());
            termValidated.getTerms().get(0).setMatchedTerms(new ArrayList<MatchedTerm>());

            termValidated.getTerms().get(0).getMatchedTerms().add(objectFactory.createMatchedTerm());

            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedClassNumber(BigInteger.ONE);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setTermSourceType("source");
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedTermText("term");
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchClassType(MatchClassType.SAME_CLASS);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setIdentifier("identifier");
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setLegacyTermFirstUseDate(new Date());
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setLegacyTermFrequency(BigInteger.TEN);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedTermPositionInInputTerm(BigInteger.ZERO);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchRank(BigInteger.TEN);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setPartial(true);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchedTermPositionInInputTermList(BigInteger.valueOf(6));
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setMatchProximityType(MatchProximityType.MATCH_TO_VERIFY);
            termValidated.getTerms().get(0).getMatchedTerms().get(0).setLegacyTermLastUseDate(new Date());

            termValidated.setDescription(term.getTerms());

            termsValidated.add(termValidated);
        }


        return termsValidated;
    }

    /**
     *
     * @param criteria
     * @return
     *         returns eu.ohim.sp.external.services.classification.nice.SearchResults
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "searchTerm", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.SearchTerm")
    @ResponseWrapper(localName = "searchTermResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.SearchTermResponse")
    public SearchResults searchTerm(@WebParam(name = "criteria", targetNamespace = "") SearchTermCriteria criteria)throws NiceClassificationFaultException {
        if (StringUtils.isBlank(criteria.getLanguage())) {
            try {
                StringUtils.defaultIfBlank(criteria.getLanguage(), null).equals("error");
            } catch (NullPointerException e) {
                NiceClassificationFault classificationFault = new NiceClassificationFault();
                classificationFault.setReturnedObject(new Fault());
                classificationFault.getReturnedObject().setCode("error.code");
                classificationFault.getReturnedObject().setMessage("system error");
                throw new NiceClassificationFaultException("system error", classificationFault, e);
            }
        }

        SearchResults searchResults = new SearchResults();
        searchResults.setTerms(new ArrayList<Term>());
        searchResults.setTotalResults(2);
        Term term = new Term();
        term.setLang(criteria.getLanguage());
        term.setText(criteria.getTerm() + "sth");

        searchResults.getTerms().add(term);

        term = new Term();
        term.setLang(criteria.getLanguage());
        term.setText(criteria.getTerm() + "sth else");


        searchResults.getTerms().add(term);

        return searchResults;
    }

    /**
     *
     * @param criteria
     * @return
     *         returns eu.ohim.sp.external.services.classification.nice.DistributionResults
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDistribution", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetDistribution")
    @ResponseWrapper(localName = "getDistributionResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetDistributionResponse")
    public DistributionResults getDistribution(@WebParam(name = "criteria", targetNamespace = "") DistributionCriteria criteria)throws NiceClassificationFaultException {

        if (StringUtils.isBlank(criteria.getLanguage())) {
            NiceClassificationFault classificationFault = new NiceClassificationFault();
            classificationFault.setReturnedObject(new Fault());
            classificationFault.getReturnedObject().setCode("error.code");
            classificationFault.getReturnedObject().setMessage("system error");
            throw new NiceClassificationFaultException("system error", classificationFault);
        }

        DistributionResults distributionResults = new DistributionResults();
        distributionResults.setDistributionResult(new ArrayList<DistributionResult>());

        for (BigInteger bigInteger : criteria.getNiceClassList()) {
            DistributionResult distributionResult = new DistributionResult();
            distributionResult.setClassNum(bigInteger);
            distributionResult.setTotalMatches(new BigInteger("54"));
            distributionResult.setTotalNumber(new BigInteger("555"));
            distributionResults.getDistributionResult().add(distributionResult);
        }


        return distributionResults;
    }

    /**
     *
     * @param criteria
     * @return
     *         returns java.util.List<eu.ohim.sp.external.services.classification.nice.Term>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNiceClassHeading", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetNiceClassHeading")
    @ResponseWrapper(localName = "getNiceClassHeadingResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetNiceClassHeadingResponse")
    public List<Term> getNiceClassHeading(@WebParam(name = "criteria", targetNamespace = "") NiceHeadingCriteria criteria)throws NiceClassificationFaultException {
        if (StringUtils.isBlank(criteria.getLanguage())) {
            NiceClassificationFault classificationFault = new NiceClassificationFault();
            classificationFault.setReturnedObject(new Fault());
            classificationFault.getReturnedObject().setCode("error.code");
            classificationFault.getReturnedObject().setMessage("system error");
            throw new NiceClassificationFaultException("system error", classificationFault);
        }

        List<Term> terms = new ArrayList<Term>();

        Term term  = new Term();
        term.setLang(criteria.getLanguage());

        terms.add(term);

        return  terms;

    }

    /**
     *
     * @param criteria
     * @return
     *         returns java.util.List<eu.ohim.sp.external.services.classification.nice.TaxonomyConceptNode>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTaxonomy", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetTaxonomy")
    @ResponseWrapper(localName = "getTaxonomyResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetTaxonomyResponse")
    public List<TaxonomyConceptNode> getTaxonomy(@WebParam(name = "criteria", targetNamespace = "") TaxonomyCriteria criteria)throws NiceClassificationFaultException {
        if (StringUtils.isBlank(criteria.getLanguage())) {
            NiceClassificationFault classificationFault = new NiceClassificationFault();
            classificationFault.setReturnedObject(new Fault());
            classificationFault.getReturnedObject().setCode("error.code");
            classificationFault.getReturnedObject().setMessage("system error");
            throw new NiceClassificationFaultException("system error", classificationFault);
        }

        if (!StringUtils.equals(criteria.getLanguage(), "en")) {
            NiceClassificationFault classificationFault = new NiceClassificationFault();
            classificationFault.setReturnedObject(new Fault());
            classificationFault.getReturnedObject().setCode("error.code");
            classificationFault.getReturnedObject().setMessage("system error");
            throw new NiceClassificationFaultException("Test Error", classificationFault);
        }
        List<TaxonomyConceptNode> taxonomyConceptNodes = new ArrayList<TaxonomyConceptNode>();
        taxonomyConceptNodes.add(new TaxonomyConceptNode());
        taxonomyConceptNodes.get(0).setParentId(criteria.getTaxoConceptNodeId());

        return taxonomyConceptNodes;
    }

    /**
     *
     * @param criteria
     * @return
     *     returns java.util.List<eu.ohim.sp.external.services.classification.nice.Term>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getClassScopes", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetClassScopes")
    @ResponseWrapper(localName = "getClassScopesResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetClassScopesResponse")
    public List<ClassScope> getClassScopes(
            @WebParam(name = "criteria", targetNamespace = "")
            ClassScopesCriteria criteria)throws NiceClassificationFaultException {
        if (StringUtils.isBlank(criteria.getLanguage())) {
            NiceClassificationFault classificationFault = new NiceClassificationFault();
            classificationFault.setReturnedObject(new Fault());
            classificationFault.getReturnedObject().setCode("error.code");
            classificationFault.getReturnedObject().setMessage("system error");
            throw new NiceClassificationFaultException("system error", classificationFault);
        }

        List<ClassScope> classScopes = new ArrayList<ClassScope>();
        classScopes.add(new ClassScope());
        classScopes.get(0).setLanguage(criteria.getLanguage());

        return classScopes;
    }

    /**
     *
     * @param criteria
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDidYouMean", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetDidYouMean")
    @ResponseWrapper(localName = "getDidYouMeanResponse", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3", className = "eu.ohim.sp.external.services.classification.nice.GetDidYouMeanResponse")
    public List<String> getDidYouMean(
            @WebParam(name = "criteria", targetNamespace = "")
            DidYouMeanCriteria criteria)throws NiceClassificationFaultException {
        if (StringUtils.isBlank(criteria.getLanguage())) {
            NiceClassificationFault classificationFault = new NiceClassificationFault();
            classificationFault.setReturnedObject(new Fault("system.error.01", "Unexpected value"));

            throw new NiceClassificationFaultException("system error", classificationFault);
        }

        List<String> terms = new ArrayList<String>();
        terms.add(criteria.getSearchPhrase() + "sth");

        return terms;
    }

}