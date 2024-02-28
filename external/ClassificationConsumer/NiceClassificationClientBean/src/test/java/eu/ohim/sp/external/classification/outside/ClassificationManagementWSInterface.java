package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.external.domain.classification.wrapper.*;
import eu.ohim.sp.external.ws.exception.NiceClassificationFaultException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@WebService
public interface ClassificationManagementWSInterface {

    /**
     *
     * @param arg0
     * @return
     *         returns java.util.List<eu.ohim.sp.external.classification.ws.client.Term>
     */
    public List<TermsValidated> verifyListOfTerms(List<TermsToBeValidated> arg0)throws NiceClassificationFaultException;

    /**
     *
     * @param arg0
     * @return
     *         returns eu.ohim.sp.external.classification.ws.client.SearchResults
     */
    public SearchResults searchTerm(SearchTermCriteria arg0)throws NiceClassificationFaultException;

    /**
     *
     * @param arg0
     * @return
     *         returns eu.ohim.sp.external.classification.ws.client.DistributionResults
     */
    public DistributionResults getDistribution(DistributionCriteria arg0)throws NiceClassificationFaultException;

    /**
     *
     * @param arg0
     * @return
     *         returns java.util.List<eu.ohim.sp.external.classification.ws.client.Term>
     */
    public List<Term> getNiceClassHeading(NiceHeadingCriteria arg0)throws NiceClassificationFaultException;

    /**
     *
     * @param arg0
     * @return
     *         returns java.util.List<eu.ohim.sp.external.classification.ws.client.TaxonomyConceptNode>
     */
    @WebMethod
    public List<TaxonomyConceptNode> getTaxonomy(TaxonomyCriteria arg0)throws NiceClassificationFaultException;


    /**
     *
     * @param arg0
     * @return
     *     returns java.util.List<eu.ohim.sp.external.classification.ws.client.Term>
     */
    @WebMethod
    public List<ClassScope> getClassScopes(
            ClassScopesCriteria arg0)throws NiceClassificationFaultException;

    /**
     *
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    public List<String> getDidYouMean(
            DidYouMeanCriteria arg0)throws NiceClassificationFaultException;

}
