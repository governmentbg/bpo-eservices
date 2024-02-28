package eu.ohim.sp.external.injectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.design.*;
import eu.ohim.sp.external.common.*;
import eu.ohim.sp.external.domain.design.ProductIndication;
import eu.ohim.sp.external.transformers.LocarnoTranformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocarnoInjector extends AbstractConsumerService implements Serializable{

	private static final long serialVersionUID = -2078092999276280610L;

	/** urls **/
	private final static String GET = System.getProperty("url.dsview.get.locarno");
	private final static String SEARCH = System.getProperty("url.dsview.search.locarno");

	//DS Class Integration Changes Start

	private final static String GET_AUTOCOMPLETE = System.getProperty("url.dsclass.get.locarno.autocomplete");
	private final static String GET_TERMS = System.getProperty("url.dsclass.get.terms");
	private final static String GET_TAXONOMY_BROWSE = System.getProperty("url.dsclass.get.taxonomy.browse");
	private final static String GET_TAXONOMY_TREE = System.getProperty("url.dsclass.get.taxonomy.tree");
	private final static String GET_LOCARNO_CLASSES = System.getProperty("url.dsclass.get.classes");
	private final static String GET_TRANSLATION = System.getProperty("url.dsclass.get.translation");
	public static final String DESIGN_CLASS_OFFICE_CODE = System.getProperty("design.class.office.code");
	private static final String VERIFICATION_SERVICE_URL = System.getProperty("url.dsclass.locarno.verification");
	private static final String UTF_ENCODING = "UTF-8";

	//DS Class Integration Changes End

	/** url selectors **/
	private final static String URL_GET = "get";
	private final static String URL_SEARCH = "search";

	//DS Class Integration Changes Start

	private final static String URL_GET_AUTOCOMPLETE = "getAutoComplete";
	private final static String URL_GET_TERM = "getTerms";
	private final static String URL_TAXONOMY_BROWSE = "taxonomyBrowse";
	private final static String URL_TAXONOMY_TREE = "taxonomyTree";

	private final static String URL_GET_LOCARNO_CLASSES = "getLocarnoClasses";
	private final static String URL_GET_TRANSLATION = "getTranslation";

	/** The Constant UNKNOWN_CLASS_CODE. */
	public static final Integer UNKNOWN_CLASS_CODE = Integer.valueOf(-1);

	/** The Constant UNKNOWN_CLASS_CODE_STR. */
	public static final String UNKNOWN_CLASS_CODE_STR = String.valueOf(UNKNOWN_CLASS_CODE);
	//DS Class Integration Changes End

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LocarnoInjector.class);

	private String _getUrl(final String selector, final Object... arguments) {
		StringBuilder url = new StringBuilder();
		try {
			switch (selector) {
				case URL_GET:
					url.append(this.GET).append("?lang=").append(URLEncoder.encode((String) arguments[0], UTF_ENCODING));
					break;
				case URL_SEARCH:
					url.append(this.SEARCH).append("?idclass=").append(URLEncoder.encode((String) arguments[0], UTF_ENCODING))
						.append("&idsubclass=").append(URLEncoder.encode((String) arguments[1], UTF_ENCODING))
						.append("&cdlanguage=").append(URLEncoder.encode((String) arguments[2], UTF_ENCODING));
					if (arguments[3] != null) {
						url.append("&indprod=").append(URLEncoder.encode((String) arguments[3], UTF_ENCODING));
					}
					break;
				//DS Class Integration Changes Start
				case URL_GET_AUTOCOMPLETE:
					url.append(this.GET_AUTOCOMPLETE).append("?language=")
						.append(URLEncoder.encode((String) arguments[0], UTF_ENCODING)).append("&term=")
						.append(URLEncoder.encode((String) arguments[1], UTF_ENCODING)).append("&maxSuggestions=")
						.append(URLEncoder.encode((String) arguments[2], UTF_ENCODING)).append("&sortBy=alphabetical");
					break;
				case URL_GET_TERM:
					url.append(this.GET_TERMS)
						.append("?officeList=").append(URLEncoder.encode((String) arguments[0], UTF_ENCODING))
						.append("&language=").append(URLEncoder.encode((String) arguments[1], UTF_ENCODING))
						.append("&numPage=").append(URLEncoder.encode((String) arguments[2], UTF_ENCODING))
						.append("&numTerms=").append(URLEncoder.encode((String) arguments[3], UTF_ENCODING));
					if(arguments[4] != null) {
						url.append("&term=").append(URLEncoder.encode((String) arguments[4], UTF_ENCODING));
					}
					if((String)arguments[5] != null){
						String clsss=(((String)arguments[5]).length() == 1) ? "0"+(String) arguments[5] : (String) arguments[5];
						url.append("&class=").append(URLEncoder.encode(clsss, UTF_ENCODING));
					}
					if((String)arguments[6] != null){
						String subclsss=(((String)arguments[6]).length() == 1) ? "0"+(String) arguments[6] : (String) arguments[6];
						url.append("&subclass=").append(URLEncoder.encode(subclsss, UTF_ENCODING));
					}
					if((String)arguments[7] != null){
						String groupId=(String)arguments[7];
						url.append("&groupId=").append(URLEncoder.encode(groupId, UTF_ENCODING));
					}
					if((String)arguments[8] != null) {
						String sortBy=(String)arguments[8];
						url.append("&sortBy=").append(URLEncoder.encode(sortBy, UTF_ENCODING));
					} else {
						url.append("&sortBy=alphabetical");
					}
					break;
				case URL_GET_LOCARNO_CLASSES:
					url.append(this.GET_LOCARNO_CLASSES).append("?eurolocarno=false");
					if (arguments[0] != null) {
						url.append("&language=").append(URLEncoder.encode((String) arguments[0], UTF_ENCODING));
					}
					if (arguments[1] != null) {
						url.append("&version=").append(URLEncoder.encode((String) arguments[1], UTF_ENCODING));
					}
					break;
				case URL_GET_TRANSLATION:
					url.append(this.GET_TRANSLATION).append("?languageFrom=")
						.append(URLEncoder.encode((String) arguments[0], UTF_ENCODING));
					if ((String) arguments[1] != null) {
						url.append("&languageTo=").append(URLEncoder.encode((String) arguments[1], UTF_ENCODING));
					}
					if ((String) arguments[2] != null) {
						url.append("&termTranslate=").append(URLEncoder.encode((String) arguments[2], UTF_ENCODING));
					}

					url.append("&termId=").append(URLEncoder.encode((String) arguments[3], UTF_ENCODING));
					if ((String) arguments[4] != null) {
						url.append("&class=").append(URLEncoder.encode((String) arguments[4], UTF_ENCODING));
					}
					if ((String) arguments[5] != null) {
						url.append("&subclass=").append(URLEncoder.encode((String) arguments[5], UTF_ENCODING));
					}
					url.append("&sortBy=alphabetical");
					break;
				case URL_TAXONOMY_BROWSE:
					url.append(this.GET_TAXONOMY_BROWSE)
						.append("?language=").append(URLEncoder.encode((String) arguments[1], UTF_ENCODING))
						.append("&numPage=").append(URLEncoder.encode((String) arguments[2], UTF_ENCODING))
						.append("&numResults=").append(URLEncoder.encode((String) arguments[3], UTF_ENCODING));
					if((String)arguments[5] != null){
						url.append("&class=").append(URLEncoder.encode((String) arguments[5], UTF_ENCODING));
					}
					if((String)arguments[6] != null){
						url.append("&subclass=").append(URLEncoder.encode((String) arguments[6], UTF_ENCODING));
					}

					break;
				case URL_TAXONOMY_TREE:
					url.append(this.GET_TAXONOMY_TREE)
						.append("?officeList=").append(URLEncoder.encode((String) arguments[0], UTF_ENCODING))
						.append("&language=").append(URLEncoder.encode((String) arguments[1], UTF_ENCODING))
						.append("&numPage=").append(URLEncoder.encode((String) arguments[2], UTF_ENCODING))
						.append("&numResults=").append(URLEncoder.encode((String) arguments[3], UTF_ENCODING));
					if((String)arguments[5] != null){
						String clsss=(((String)arguments[5]).length() == 1) ? "0"+(String) arguments[5] : (String) arguments[5];
						url.append("&class=").append(URLEncoder.encode(clsss, UTF_ENCODING));
					}
					if((String)arguments[6] != null){
						url.append("&subclass=").append(URLEncoder.encode((String) arguments[6], UTF_ENCODING));
					}
					break;
				//DS Class Integration Changes End
			}
		} catch (UnsupportedEncodingException e) {
			throw new SPException(e);
		}
		return url.toString();
	}

	public List<ProductIndication> getFullClassification(final String lang) {
		final String url = _getUrl(URL_GET, lang);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);
		LOGGER.info(">>> Done, we've got Product Indication, Transforming it <<<");
		return LocarnoTranformer.productIndications(response.getResponse());
	}

	public List<eu.ohim.sp.external.domain.design.ProductIndication> getProductIndications(final String clazz,
																						   final String subclass,
																						   final String lang) {
		final String url = _getUrl(URL_SEARCH, clazz, subclass, lang);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);
		LOGGER.info(">>> Done, we've got Product Indication, Transforming it <<<");
		ArrayList<ProductIndication> pis = new ArrayList<>();
		ProductIndication pi = new ProductIndication();
		pi.setClasses(LocarnoTranformer.productIndicationsClasses(response.getResponse()));
		pis.add(pi);
		return pis;
	}

	public List<eu.ohim.sp.external.domain.design.ProductIndication> searchProductIndication(
			final String indicationProduct,
			final String clazz,
			final String subclass,
			final String lang
	) {
		final String url = _getUrl(URL_SEARCH, clazz, subclass, lang, indicationProduct);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);
		LOGGER.info(">>> Done, we've got Product Indication, Transforming it <<<");
		ArrayList<ProductIndication> pis = new ArrayList<>();
		ProductIndication pi = new ProductIndication();
		pi.setClasses(LocarnoTranformer.productIndicationsClasses(response.getResponse()));
		pis.add(pi);
		return pis;
	}

	//DS Class Integration Changes Start

	public String getLocarnoAutocomplete(String language, String text, Integer numberOfResults) {
		final String url = _getUrl(URL_GET_AUTOCOMPLETE, language, text, numberOfResults.toString());
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);
		String autoCompleteResponse = LocarnoTranformer.translateDesignClassResponse(response.getResponse());
		LOGGER.info(">>> Done, we've got Autocomplete Locarno Response, Translating it <<<" + autoCompleteResponse);
		return autoCompleteResponse;
	}

	public ClassificationHeading getLocarnoClasses(String language, String version) throws Exception {
		final String url = _getUrl(URL_GET_LOCARNO_CLASSES, language, version);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);
		LOGGER.info(">>> Got the Locarno classes response: " + response + "<<<");
		return LocarnoTranformer.locarnoClasses(response.getResponse());
	}

	public List<ClassDescription> validateClassDescriptions(List<ClassDescription> classDescriptions) {
		if (CollectionUtils.isEmpty(classDescriptions)) {
			return Collections.emptyList();
		}
		List<VerificationResponse> verificationResponses = processProductDescriptionsByDesignClass(classDescriptions);
		List<ClassDescription> classDescriptionsToReturn = new ArrayList<>(classDescriptions.size());

		for (int i = 0; i < classDescriptions.size() && i < verificationResponses.size(); i++) {
			ClassDescription oldClassDescription = classDescriptions.get(i);
			VerificationResponse verificationResponse = verificationResponses.get(i);
			ClassDescription newClassDescription = validateClassDescription(oldClassDescription, verificationResponse);
			classDescriptionsToReturn.add(newClassDescription);
		}
		return classDescriptionsToReturn;
	}

	/**
	 * Validate class description.
	 *
	 * @param classDescription
	 *            the class description
	 * @param verificationResponse
	 *            the verification response
	 * @return the class description
	 */
	private ClassDescription validateClassDescription(ClassDescription classDescription,
													  VerificationResponse verificationResponse) {
		ClassDescription newClassDescription = new ClassDescription();
		newClassDescription.setClassId(classDescription.getClassId());
		newClassDescription.setSubClassId(classDescription.getSubClassId());
		newClassDescription.setValid(ValidationCode.fromValue(verificationResponse.getVerificationResult().name()));
		newClassDescription.getProductDescriptions().addAll(classDescription.getProductDescriptions());
		String termId = null;
		if (Arrays.asList(ValidationCode.HINT, ValidationCode.NOT_OK).contains(newClassDescription.getValid())
			&& verificationResponse.getMatchingResults() != null) {
			newClassDescription.setSuggestions(getSuggestions(verificationResponse));
		} else if (ValidationCode.OK == newClassDescription.getValid()
			&& verificationResponse.getMatchingResults() != null
			&& verificationResponse.getMatchingResults().size() == 1) {
			termId = verificationResponse.getMatchingResults().get(0).getTermId();
		}
		newClassDescription.setClassificationTermIdentifier(termId);
		return newClassDescription;
	}

	/**
	 * Gets suggestions.
	 *
	 * @param verificationResponse
	 *            the verification response
	 * @return the suggestions
	 */
	private List<ClassDescription> getSuggestions(VerificationResponse verificationResponse) {
		List<ClassDescription> suggestions = new ArrayList<>();
		for (MatchingResult matchingResult : verificationResponse.getMatchingResults()) {
			ClassDescription hintResult = new ClassDescription();
			hintResult.setClassificationTermIdentifier(matchingResult.getTermId());
			hintResult.setLocarnoClassNumber(matchingResult.getTermClass());
			LocalizedText text = new LocalizedText();
			text.setValue(matchingResult.getTermText());
			hintResult.getProductDescriptions().add(text);
			suggestions.add(hintResult);
		}
		return suggestions;
	}

	/**
	 * Process all classifications by DesignClass
	 *
	 * @param classifications
	 *            classification list
	 * @return The verification response from DesignClass
	 */
	private List<VerificationResponse> processProductDescriptionsByDesignClass(List<ClassDescription> classifications) {
		ArrayList<VerificationResponse> results = new ArrayList<>();
		List<ClassDescription> toVerify = new ArrayList<>();
		for (ClassDescription classDescription : classifications) {
			if (isValidClassDescription(classDescription)) {
				toVerify.add(classDescription);
			} else {
				results.add(createDefaultVerificationResponse());
			}
		}
		if (CollectionUtils.isNotEmpty(toVerify)) {
			results.addAll(verification(toVerify, DESIGN_CLASS_OFFICE_CODE));
		}
		return results;
	}

	private List<VerificationResponse> verification(List<ClassDescription> toVerify, String designClassOfficeCode) {
		List<VerificationResponse> ret = new ArrayList<>();
		VerificationRequest request = new VerificationRequest();
		request.setOfficeCode(designClassOfficeCode);
		List<Term> terms = new LinkedList<>();
		List<String> classifications = new LinkedList<>();
		for (ClassDescription classDef : toVerify) {
			List<LocalizedText> descriptions = classDef.getProductDescriptions();
			if (descriptions != null && !descriptions.isEmpty() && descriptions.get(0).getValue() != null) {
				Term term = new Term();
				term.setLanguageCode(descriptions.get(0).getLanguageCode().name());
				term.setTermText(descriptions.get(0).getValue());
				terms.add(term);
			}
			classifications.add(classDef.getClassId().intValue() > 0 ? classDef.getLocarnoClassNumber() : "");
		}
		request.setTermList(terms);
		request.setClassificationList(classifications);
		LOGGER.info(">>> VERIFICATION_SERVICE_URL: " + VERIFICATION_SERVICE_URL + "<<<");

		// doPost
		final ConsumerRequest consumerRequest = new RestJsonConsumerRequest(VERIFICATION_SERVICE_URL);

		try {

			final ConsumerResponse<String> response = doPostRequestWithCookieSkip(consumerRequest, request);
			LOGGER.info( ">>> REST response for verification: " + response.getResponse() + "<<<");

			VerificationResponse[] verificationresponse = new ObjectMapper().readValue(response.getResponse(),
				VerificationResponse[].class);
			LOGGER.info(">>> Consuming REST service for verification: " + verificationresponse + "<<<");
			ret = Arrays.asList(verificationresponse);
		} catch (Exception e) {
			ret = Arrays.asList(createVerificationError());
		}
		return ret;
	}
	/**
	 * Create verification error.
	 *
	 * @return the verification response
	 */
	private VerificationResponse createVerificationError() {
		VerificationResponse response = new VerificationResponse();
		response.setMatchingResults(Collections.emptyList());
		response.setMessage("Verification error");
		response.setVerificationResult(VerificationResult.NOT_OK);
		return response;
	}

	/**
	 * True if classDescription has all required data to be validated by Design
	 * Class
	 *
	 * @param classDescription
	 * @return True if is a valid class description
	 */
	private boolean isValidClassDescription(ClassDescription classDescription) {
		Integer classId = classDescription.getClassId();
		Integer subClassId = classDescription.getSubClassId();
		return classId != null && classId.intValue() >= 1 && subClassId != null && subClassId.intValue() >= 0
			&& CollectionUtils.isNotEmpty(classDescription.getProductDescriptions());
	}

	private VerificationResponse createDefaultVerificationResponse() {
		VerificationResponse verificationResponse = new VerificationResponse();
		verificationResponse.setVerificationResult(VerificationResult.NOT_FOUND);
		return verificationResponse;
	}

	public TermsSearch getTerms(String offices, String language, Integer page, Integer pageSize, String searchInput,
								Integer selectedClass, Integer selectedSubclass, final Object... arguments) {
		String normalizedClass=normalizeClassSubClassCode(selectedClass.toString());
		String normalizedSubClass=normalizeClassSubClassCode(selectedSubclass.toString());
		String searchMode=(String)arguments[0];
		String nodeId=(String)arguments[1];
		String sortBy = (String)arguments[2];

		String urlType="";
		if(normalizedClass==null && normalizedSubClass==null && !Boolean.parseBoolean(searchMode)){
			urlType=URL_TAXONOMY_BROWSE;
			pageSize=50;
		}
		else if (normalizedClass!=null && normalizedSubClass==null && !Boolean.parseBoolean(searchMode)){
			urlType=URL_TAXONOMY_TREE;
		}
		else{
			urlType=URL_GET_TERM;
		}
		final String url = _getUrl(urlType, offices, language, page.toString(), pageSize.toString(), searchInput,
			normalizedClass, normalizedSubClass,nodeId, sortBy);

		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);

		String resp = response.getResponse();
		TermsSearch termSearch = new TermsSearch();
		if(normalizedClass==null && normalizedSubClass==null && !Boolean.parseBoolean(searchMode)){
			termSearch=LocarnoTranformer.parseBrowseTerms(resp);
		}
		else if (normalizedClass!=null && normalizedSubClass==null && !Boolean.parseBoolean(searchMode)){
			termSearch=LocarnoTranformer.parseTaxonomyTree(selectedClass, resp);
		}
		else{
			termSearch=LocarnoTranformer.parseSearchTerms(selectedClass, resp, searchInput);
		}

		termSearch.setPageSize(pageSize);
		return termSearch;
	}

	public List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate,
												String productIndicationIdentifier, String selectedClass, String subclass) {
		final String url = _getUrl(URL_GET_TRANSLATION, languageFrom, languageTo, termTranslate,
			productIndicationIdentifier, selectedClass, subclass);
		LOGGER.info(">>> Consuming REST service: " + url + "<<<");
		final ConsumerRequest request = new RestJsonConsumerRequest(url);
		final ConsumerResponse<String> response = doRequestWithCookieSkip(request);
		String resp = response.getResponse();
		List<KeyTextUIClass> list = LocarnoTranformer.parseTranslation(resp);
		return list;
	}

	private String normalizeClassSubClassCode(String code) {
		return (StringUtils.isEmpty(code) || StringUtils.equals(code, this.UNKNOWN_CLASS_CODE_STR)) ? null : code;
	}
	//DS Class Integration Changes End
}
