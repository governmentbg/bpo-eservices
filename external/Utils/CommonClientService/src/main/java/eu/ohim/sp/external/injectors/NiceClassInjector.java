package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.common.*;
import eu.ohim.sp.external.domain.classification.wrapper.*;
import eu.ohim.sp.integration.domain.smooks.FreeFormatString;
import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
/**DS Class Integration changes start*/
import eu.ohim.sp.external.domain.classification.wrapper.Term;
/**DS Class Integration changes end*/

public class NiceClassInjector extends AbstractConsumerService implements Serializable{

	private static final long serialVersionUID = -2078092999276280610L;

	/** urls **/
	private final static String CLASS = System.getProperty("url.euroclass.class.scopes");
	private final static String AUTOCOMPLETE = System.getProperty("url.euroclass.autocomplete");
	private final static String DISTRIBUTION = System.getProperty("url.euroclass.getDistribution");
	private final static String HEADINGS = System.getProperty("url.euroclass.niceHeading.terms");
	private final static String TERMS = System.getProperty("url.euroclass.search.terms");
	private final static String TAXONOMY = System.getProperty("url.euroclass.taxonomy");
	private final static String VERIFYLIST = System.getProperty("url.euroclass.verify.terms");

	/** smooks transform file **/
	private final static String TRANSFORM_FILE_CLASS = "META-INF/smooks-NiceClassificationAdapter-classScopes.xml";
	private final static String TRANSFORM_FILE_AUTOCOMPLETE = "META-INF/smooks-NiceClassificationAdapter-Autocomplete.xml";
	private final static String TRANSFORM_FILE_DISTRIBUTION = "META-INF/smooks-NiceClassificationAdapter-getDistribution.xml";
	private final static String TRANSFORM_FILE_HEADINGS = "META-INF/smooks-NiceClassificationAdapter-niceHeadings.xml";
	private final static String TRANSFORM_FILE_TERMS = "META-INF/smooks-NiceClassificationAdapter-searchTerm.xml";
	private final static String TRANSFORM_FILE_TAXONOMY = "META-INF/smooks-NiceClassificationAdapter-taxonomy.xml";
	private final static String TRANSFORM_FILE_VERIFYLIST = "META-INF/smooks-NiceClassificationAdapter-verifyList.xml";

	/** tranformer **/
	private final static String TRANSFORMER_CLASS = "class";
	private final static String TRANSFORMER_AUTOCOMPLETE = "autocomplete";
	private final static String TRANSFORMER_DISTRIBUTION = "distribution";
	private final static String TRANSFORMER_HEADINGS = "headings";
	private final static String TRANSFORMER_TERMS = "terms";
	private final static String TRANSFORMER_TAXONOMY = "taxonomy";
	private final static String TRANSFORMER_VERIFYLIST = "verify";

	/** url selectors **/
	private final static String URL_CLASS = "class";
	private final static String URL_AUTOCOMPLETE = "autocomplete";
	private final static String URL_DISTRIBUTION = "distribution";
	private final static String URL_HEADINGS = "headings";
	private final static String URL_TERMS = "terms";
	private final static String URL_TAXONOMY = "taxonomy";
	private final static String URL_VERIFYLIST = "verify";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(NiceClassInjector.class);

	private String _getUrl(final String selector, final Object... arguments) {
		StringBuilder url = new StringBuilder();
		try {
			switch (selector) {
				case URL_CLASS:
					url.append(this.CLASS)
							.append("?lc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"));
							if(arguments[1] != null)
								url.append("&nc=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_AUTOCOMPLETE:
					url.append(this.AUTOCOMPLETE).append("?language=").append(URLEncoder.encode((String) arguments[0], "UTF-8")).append("&text=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_HEADINGS:
					url.append(this.HEADINGS)
							.append("?lc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&nc=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_DISTRIBUTION:
					url.append(this.DISTRIBUTION)
							.append("?office=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&lc=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&tt=").append(URLEncoder.encode((String) arguments[2], "UTF-8"))
							.append("&niceClassList=").append(URLEncoder.encode((String) arguments[3], "UTF-8"));
					break;
				case URL_TAXONOMY:
					url.append(this.TAXONOMY)
							.append("?office=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&lc=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&tt=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
							if(arguments[3] != null)
								url.append("&levelLimit=").append(URLEncoder.encode((String) arguments[3], "UTF-8"));
							if(arguments[4] != null)
								url.append("&taxoConceptNodeId=").append(URLEncoder.encode((String) arguments[4], "UTF-8"));
					break;
				case URL_VERIFYLIST:
					url.append(this.VERIFYLIST)
							.append("?office=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&language=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&termList=").append(URLEncoder.encode((String) arguments[2], "UTF-8"))
							.append("&niceClass=").append(URLEncoder.encode((String) arguments[3], "UTF-8"))
							.append("&terminologySourceList=Nice");
					break;
				case URL_TERMS:
					url.append(this.TERMS)
							.append("?office=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("&language=").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
							if(arguments[2] != null)
								url.append("&term=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
							if(arguments[3] != null)
								url.append("&taxoConceptNodeId=").append(URLEncoder.encode((String) arguments[3], "UTF-8"));
							if(arguments[4] != null)
								url.append("&page=").append(URLEncoder.encode((String) arguments[4], "UTF-8"));
							if(arguments[5] != null)
								url.append("&size=").append(URLEncoder.encode((String) arguments[5], "UTF-8"));
							if(arguments[6] != null)
								url.append("&sortBy=").append(URLEncoder.encode((String) arguments[6], "UTF-8"));
							if(arguments[7] != null)
								url.append("&orderBy=").append(URLEncoder.encode((String) arguments[7], "UTF-8"));
							if(arguments[8] != null)
								url.append("&sources=").append(URLEncoder.encode((String) arguments[8], "UTF-8"));
					break;
			}
		} catch (UnsupportedEncodingException e) {
			throw new SPException(e);
		}
		return url.toString();
	}

	private ConsumerResponseTransformer<? extends Object, String> getNiceTransformer(String selector) {
		String TRANSFORMER = null;
		String TRANSFORMER_FILE;
		ConsumerResponseTransformer<? extends Object, String> transformer = null;
		switch (selector) {
			case TRANSFORMER_CLASS:
				TRANSFORMER = TRANSFORMER_CLASS;
				TRANSFORMER_FILE = TRANSFORM_FILE_CLASS;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<List<ClassScope>>(
								(Class<List<ClassScope>>)(Class<?>)List.class,
								TRANSFORMER_FILE
						);
				break;
			case TRANSFORMER_AUTOCOMPLETE:
				TRANSFORMER = TRANSFORMER_AUTOCOMPLETE;
				TRANSFORMER_FILE = TRANSFORM_FILE_AUTOCOMPLETE;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<List<String>>(
								(Class<List<String>>)(Class<?>)List.class,
								TRANSFORMER_FILE
						);
				break;
			case TRANSFORMER_DISTRIBUTION:
				TRANSFORMER = TRANSFORMER_DISTRIBUTION;
				TRANSFORMER_FILE = TRANSFORM_FILE_DISTRIBUTION;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<DistributionResults>(
								DistributionResults.class,
								TRANSFORMER_FILE
						);
				break;
			case TRANSFORMER_HEADINGS:
				TRANSFORMER = TRANSFORMER_HEADINGS;
				TRANSFORMER_FILE = TRANSFORM_FILE_HEADINGS;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<List<Term>>(
								(Class<List<Term>>)(Class<?>)List.class,
								TRANSFORMER_FILE
						);
				break;
			case TRANSFORMER_TERMS:
				TRANSFORMER = TRANSFORMER_TERMS;
				TRANSFORMER_FILE = TRANSFORM_FILE_TERMS;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<SearchResults>(
								SearchResults.class,
								TRANSFORMER_FILE
						);
				break;
			case TRANSFORMER_TAXONOMY:
				TRANSFORMER = TRANSFORMER_TAXONOMY;
				TRANSFORMER_FILE = TRANSFORM_FILE_TAXONOMY;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<List<TaxonomyConceptNode>>(
								(Class<List<TaxonomyConceptNode>>)(Class<?>)List.class,
								TRANSFORMER_FILE
						);
				break;
			case TRANSFORMER_VERIFYLIST:
				TRANSFORMER = TRANSFORMER_VERIFYLIST;
				TRANSFORMER_FILE = TRANSFORM_FILE_VERIFYLIST;
				if (hasTransformer(TRANSFORMER)) return getTransformer(TRANSFORMER);
				transformer =
						new XMLConsumerResponseTransformer<List<TermsValidated>>(
								(Class<List<TermsValidated>>)(Class<?>)List.class,
								TRANSFORMER_FILE
						);
				break;
		}
		addTransformer(TRANSFORMER, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<List<TaxonomyConceptNode>, String> getTaxonomyTransformer() {
		if (hasTransformer(TRANSFORMER_TAXONOMY)) return getTransformer(TRANSFORMER_TAXONOMY);
		XMLConsumerResponseTransformer<List<TaxonomyConceptNode>> transformer =
				new XMLConsumerResponseTransformer<List<TaxonomyConceptNode>>(
						(Class<List<TaxonomyConceptNode>>)(Class<?>)List.class,
						TRANSFORM_FILE_TAXONOMY
				);
		addTransformer(TRANSFORMER_TAXONOMY, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<List<Term>, String> getValidationTransformer() {
		if (hasTransformer(TRANSFORMER_VERIFYLIST)) return getTransformer(TRANSFORMER_VERIFYLIST);
		XMLConsumerResponseTransformer<List<Term>> transformer =
				new XMLConsumerResponseTransformer<List<Term>>(
						(Class<List<Term>>)(Class<?>)List.class,
						TRANSFORM_FILE_VERIFYLIST
				);
		addTransformer(TRANSFORMER_VERIFYLIST, transformer);
		return transformer;
	}

	/**
	 * NiceClass orchestration process
	 * @param lang class language
	 * @param classId class id
	 * @return External Class Scope
	 */
	public List<ClassScope> getClassScopes(final String lang, final String classId) {
		final String url = _getUrl(URL_CLASS, lang, classId);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got Class, Transforming it <<<");
		final ConsumerResponseTransformer<List<ClassScope>, String> transformer = (ConsumerResponseTransformer<List<ClassScope>, String>)getNiceTransformer(TRANSFORMER_CLASS);
		return doTransform(transformer, response);
	}

	public List<FreeFormatString> getAutocomplete(final String lang, final String phrase) {
		final String url = _getUrl(URL_AUTOCOMPLETE, lang, phrase);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got suggestions, Transforming it <<<");
		final ConsumerResponseTransformer<List<String>, String> transformer = (ConsumerResponseTransformer<List<String>, String>)getNiceTransformer(TRANSFORMER_AUTOCOMPLETE);
		return doTransform(transformer, response);
	}

	public DistributionResults getDistribution(final String office, final String language, final String term, final String niceClassList) {
		final String url = _getUrl(URL_DISTRIBUTION, office, language, term, niceClassList);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got suggestions, Transforming it <<<");
		final ConsumerResponseTransformer<DistributionResults, String> transformer = (ConsumerResponseTransformer<DistributionResults, String>)getNiceTransformer(TRANSFORMER_DISTRIBUTION);
		return doTransform(transformer, response);
	}

	public List<Term> getHeadings(final String language, final String heading) {
		final String url = _getUrl(URL_HEADINGS, language, heading);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got headings, Transforming it <<<");
		final ConsumerResponseTransformer<List<Term>, String> transformer = (ConsumerResponseTransformer<List<Term>, String>)getNiceTransformer(TRANSFORMER_HEADINGS);
		return doTransform(transformer, response);
	}

	public List<TaxonomyConceptNode> getTaxonomy(final String office,
												 final String language,
												 final String term,
												 final String levelLimit,
												 final String conceptId) {
		final String url = _getUrl(URL_TAXONOMY, office, language, term, levelLimit, conceptId);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response;
		try {
			response = doRequest(request);
			LOGGER.info(">>> Done, we've got taxonomy, Transforming it <<<");
		} catch(Exception e){
			LOGGER.error(">>> Exception returned in Taxonomy! 404 not found <<<");
			return Collections.emptyList();
		}
		final ConsumerResponseTransformer<List<TaxonomyConceptNode>, String> transformer = getTaxonomyTransformer();
		return doTransform(transformer, response);
	}

	public List<Term> getVerifyTerms(final String office,
											   final String language,
											   final String termList,
											   final String niceClass) {
		final String url = _getUrl(URL_VERIFYLIST, office, language, termList, niceClass);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got validated terms, Transforming it <<<");
		final ConsumerResponseTransformer<List<Term>, String> transformer = getValidationTransformer();
		return doTransform(transformer, response);
	}

	public SearchResults getSearchTerms(final String office,
										 final String language,
										 final String term,
										 final String conceptId,
										 final String page,
										 final String size,
										 final String sortBy,
										 final String orderBy,
										 final String sources) {
		final String url = _getUrl(URL_TERMS, office, language, term, conceptId, page, size, sortBy, orderBy, sources);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		final ConsumerResponse<String> response = doRequest(request);
		LOGGER.info(">>> Done, we've got terms, Transforming it <<<");
		final ConsumerResponseTransformer<SearchResults, String> transformer = (ConsumerResponseTransformer<SearchResults, String>)getNiceTransformer(TRANSFORMER_TERMS);
		return doTransform(transformer, response);
	}
}
