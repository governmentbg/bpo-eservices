package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.common.*;
import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.domain.person.*;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import eu.ohim.sp.external.transformers.PersonMatchRequestConverter;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonInjector extends AbstractConsumerService implements Serializable{

	private static final long serialVersionUID = -2078092999276280610L;

	/** smooks transform file **/
	private final static String TRANSFORM_FILE_APPLICANT = "META-INF/smooks-PersonSearchAdapter-getApplicant.xml";
	private final static String TRANSFORM_FILE_MATCH_APPLICANT = "META-INF/smooks-PersonSearchAdapter-getApplicantMatch.xml";
	private final static String TRANSFORM_FILE_REPRESENTATIVE = "META-INF/smooks-PersonSearchAdapter-getRepresentative.xml";
	private final static String TRANSFORM_FILE_MATCH_REPRESENTATIVE = "META-INF/smooks-PersonSearchAdapter-getRepresentativeMatch.xml";
	private final static String TRANSFORM_FILE_DESIGNER = "META-INF/smooks-PersonSearchAdapter-getDesigner.xml";
	private final static String TRANSFORM_FILE_MATCH_DESIGNER = "META-INF/smooks-PersonSearchAdapter-getDesignerMatch.xml";
	private final static String TRANSFORM_FILE_INVENTOR = "META-INF/smooks-PersonSearchAdapter-getInventor.xml";
	private final static String TRANSFORM_FILE_MATCH_INVENTOR = "META-INF/smooks-PersonSearchAdapter-getInventorMatch.xml";

	/** tranformer **/
	private final static String TRANSFORMER_APPLICANT = "applicant";
	private final static String TRANSFORMER_MATCH_APPLICANT = "applicant_match";
	private final static String TRANSFORMER_REPRESENTATIVE = "representative";
	private final static String TRANSFORMER_MATCH_REPRESENTATIVE = "representative_match";
	private final static String TRANSFORMER_DESIGNER = "designer";
	private final static String TRANSFORMER_MATCH_DESIGNER = "designer_match";
	private final static String TRANSFORMER_INVENTOR = "inventor";
	private final static String TRANSFORMER_MATCH_INVENTOR= "inventor_match";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(PersonInjector.class);

	public static final String APPLICANT = "applicant";
	public static final String REPRESENTATIVE = "representative";
	public static final String DESIGNER = "designer";
	public static final String INVENTOR = "inventor";

	/** urls **/
	private final static String DS_GET_APPLICANT = System.getProperty("url.dsview.get.applicant");
	private final static String TM_GET_APPLICANT = System.getProperty("url.tmview.get.applicant");
	private final static String PT_GET_APPLICANT = System.getProperty("url.pt.get.applicant");
	private final static String MATCH_APPLICANT = System.getProperty("url.match.applicant");
	private final static String DS_GET_REPRESENTATIVE = System.getProperty("url.dsview.get.representative");
	private final static String TM_GET_REPRESENTATIVE = System.getProperty("url.tmview.get.representative");
	private final static String PT_GET_REPRESENTATIVE = System.getProperty("url.pt.get.representative");
	private final static String INTLP_GET_REPRESENTATIVE = System.getProperty("url.intlp.get.representative");
	private final static String DS_DESIGNER_GET = System.getProperty("url.dsview.get.designer");
	private final static String DS_DESIGNER_AUTOCOMPLETE = System.getProperty("url.dsview.designer.search.autocomplete");
	private final static String MATCH_DESIGNER = System.getProperty("url.match.designer");
	private final static String PT_INVENTOR_GET = System.getProperty("url.pt.get.inventor");
	private final static String PT_INVENTOR_AUTOCOMPLETE = System.getProperty("url.pt.inventor.search.autocomplete");
	private final static String MATCH_INVENTOR = System.getProperty("url.match.inventor");
	private final static String DS_APPLICANT_AUTOCOMPLETE = System.getProperty("url.dsview.applicant.search.autocomplete");
	private final static String DS_REPRESENTATIVE_AUTOCOMPLETE = System.getProperty("url.dsview.representative.search.autocomplete");
	private final static String TM_REPRESENTATIVE_AUTOCOMPLETE = System.getProperty("url.tmview.representative.search.autocomplete");
	private final static String PT_REPRESENTATIVE_AUTOCOMPLETE = System.getProperty("url.pt.representative.search.autocomplete");
	private final static String INTLP_REPRESENTATIVE_LIST = System.getProperty("url.intlp.representative.search.list");
	private final static String MATCH_REPRESENTATIVE = System.getProperty("url.match.representative");
	private final static String TM_APPLICANT_AUTOCOMPLETE = System.getProperty("url.tmview.applicant.search.autocomplete");
	private final static String PT_APPLICANT_AUTOCOMPLETE = System.getProperty("url.pt.applicant.search.autocomplete");

	private final static String WO_APPLICANT_BASE_URI = System.getProperty("url.wo.applicant.base.uri");
	private final static String WO_REPRESENTATIVE_BASE_URI = System.getProperty("url.wo.representative.base.uri");

	private final static String URL_DS_GET_APPLICANT = "ds_get_applicant";
	private final static String URL_TM_GET_APPLICANT = "tm_get_applicant";
	private final static String URL_PT_GET_APPLICANT = "pt_get_applicant";
	private final static String URL_MATCH_APPLICANT = "match_applicant";
	private final static String URL_DS_GET_REPRESENTATIVE = "ds_get_representative";
	private final static String URL_TM_GET_REPRESENTATIVE = "tm_get_representative";
	private final static String URL_PT_GET_REPRESENTATIVE = "pt_get_representative";
	private final static String URL_INTLP_GET_REPRESENTATIVE = "intlp_get_representative";
	private final static String URL_MATCH_REPRESENTATIVE = "match_representative";
	private final static String URL_DS_GET_DESIGNER = "ds_get_designer";
	private final static String URL_DESIGNER_AUTOCOMPLETE = "designer_autocomplete";
	private final static String URL_MATCH_DESIGNER = "match_designer";
	private final static String URL_PT_GET_INVENTOR = "pt_get_inventor";
	private final static String URL_INVENTOR_AUTOCOMPLETE = "inventor_autocomplete";
	private final static String URL_MATCH_INVENTOR = "match_inventor";
	private final static String URL_DS_APPLICANT_AUTOCOMPLETE = "ds.applicant_autocomplete";
	private final static String URL_DS_REPRESENTATIVE_AUTOCOMPLETE = "ds.representative_autocomplete";
	private final static String URL_TM_APPLICANT_AUTOCOMPLETE = "tm.applicant_autocomplete";
	private final static String URL_TM_REPRESENTATIVE_AUTOCOMPLETE = "tm.representative_autocomplete";
	private final static String URL_PT_APPLICANT_AUTOCOMPLETE = "pt.applicant_autocomplete";
	private final static String URL_PT_REPRESENTATIVE_AUTOCOMPLETE = "pt.representative_autocomplete";
	private final static String URL_INTLP_REPRESENTATIVE_LIST = "intlp.representative_list";

	/**
	 * URLs composer for person search
	 * @param selector
	 * @param arguments
	 * @return
	 */
	private String _getUrl(final String selector, final Object... arguments) {
		StringBuilder url = new StringBuilder();
		try {
			switch (selector) {
				case URL_DS_APPLICANT_AUTOCOMPLETE:
					url.append(this.DS_APPLICANT_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_TM_APPLICANT_AUTOCOMPLETE:
					url.append(this.TM_APPLICANT_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_PT_APPLICANT_AUTOCOMPLETE:
					url.append(this.PT_APPLICANT_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
						.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_DS_GET_APPLICANT:
					url.append(this.DS_GET_APPLICANT)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_TM_GET_APPLICANT:
					url.append(this.TM_GET_APPLICANT)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_PT_GET_APPLICANT:
					url.append(this.PT_GET_APPLICANT)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_MATCH_APPLICANT:
					url.append(this.MATCH_APPLICANT);
					break;
				case URL_DS_REPRESENTATIVE_AUTOCOMPLETE:
					url.append(this.DS_REPRESENTATIVE_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_TM_REPRESENTATIVE_AUTOCOMPLETE:
					url.append(this.TM_REPRESENTATIVE_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_PT_REPRESENTATIVE_AUTOCOMPLETE:
					url.append(this.PT_REPRESENTATIVE_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
						.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_DS_GET_REPRESENTATIVE:
					url.append(this.DS_GET_REPRESENTATIVE)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_TM_GET_REPRESENTATIVE:
					url.append(this.TM_GET_REPRESENTATIVE)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_PT_GET_REPRESENTATIVE:
					url.append(this.PT_GET_REPRESENTATIVE)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_MATCH_REPRESENTATIVE:
					url.append(this.MATCH_REPRESENTATIVE);
					break;
				case URL_DS_GET_DESIGNER:
					url.append(this.DS_DESIGNER_GET)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_DESIGNER_AUTOCOMPLETE:
					url.append(this.DS_DESIGNER_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
							.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
							.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_PT_GET_INVENTOR:
					url.append(this.PT_INVENTOR_GET)
//							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("/").append(URLEncoder.encode((String) arguments[1], "UTF-8"));
					break;
				case URL_INVENTOR_AUTOCOMPLETE:
					url.append(this.PT_INVENTOR_AUTOCOMPLETE)
//							.append("?oc=").append(URLEncoder.encode((String) arguments[0], "UTF-8"))
						.append("?word=").append(URLEncoder.encode((String) arguments[1], "UTF-8"))
						.append("&rows=").append(URLEncoder.encode((String) arguments[2], "UTF-8"));
					break;
				case URL_MATCH_DESIGNER:
					url.append(this.MATCH_DESIGNER);
					break;
				case URL_MATCH_INVENTOR:
					url.append(this.MATCH_INVENTOR);
					break;
				case URL_INTLP_REPRESENTATIVE_LIST:
					url.append(this.INTLP_REPRESENTATIVE_LIST);
					break;
				case URL_INTLP_GET_REPRESENTATIVE:
					url.append(this.INTLP_GET_REPRESENTATIVE)
							.append("/").append(URLEncoder.encode((String) arguments[0], "UTF-8"));
					break;
			}
		} catch (UnsupportedEncodingException e) {
			throw new SPException(e);
		}
		return url.toString();
	}

	/**
	 * Get the concrete transformer for the applicant transformation with smooks
	 * @return Applicant transformer
	 */
	private ConsumerResponseTransformer<Applicant, String> getApplicantTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_APPLICANT)) return getTransformer(TRANSFORMER_APPLICANT);
		final ConsumerResponseTransformer<Applicant, String> transformer =
				new XMLConsumerResponseTransformer<Applicant>(
						Applicant.class,
						TRANSFORM_FILE_APPLICANT
				);
		addTransformer(TRANSFORMER_APPLICANT, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<List, String> getApplicantMatchTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_MATCH_APPLICANT)) return getTransformer(TRANSFORMER_MATCH_APPLICANT);
		final ConsumerResponseTransformer<List, String> transformer =
			new XMLConsumerResponseTransformer<List>(
				List.class,
				TRANSFORM_FILE_MATCH_APPLICANT
			);
		addTransformer(TRANSFORMER_MATCH_APPLICANT, transformer);
		return transformer;
	}

	/**
	 * Get the concrete transformer for the representative transformation with smooks
	 * @return Representative transformer
	 */
	private ConsumerResponseTransformer<Representative, String> getRepresentativeTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_REPRESENTATIVE)) return getTransformer(TRANSFORMER_REPRESENTATIVE);
		final ConsumerResponseTransformer<Representative, String> transformer =
				new XMLConsumerResponseTransformer<Representative>(
						Representative.class,
						TRANSFORM_FILE_REPRESENTATIVE
				);
		addTransformer(TRANSFORMER_REPRESENTATIVE, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<List, String> getRepresentativeMatchTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_MATCH_REPRESENTATIVE)) return getTransformer(TRANSFORMER_MATCH_REPRESENTATIVE);
		final ConsumerResponseTransformer<List, String> transformer =
			new XMLConsumerResponseTransformer<List>(
				List.class,
				TRANSFORM_FILE_MATCH_REPRESENTATIVE
			);
		addTransformer(TRANSFORMER_MATCH_REPRESENTATIVE, transformer);
		return transformer;
	}
	/**
	 * Get the concrete transformer for the designer transformation with smooks
	 * @return Designer transformer
	 */
	private ConsumerResponseTransformer<Designer, String> getDesignerTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_DESIGNER)) return getTransformer(TRANSFORMER_DESIGNER);
		final ConsumerResponseTransformer<Designer, String> transformer =
				new XMLConsumerResponseTransformer<Designer>(
						Designer.class,
						TRANSFORM_FILE_DESIGNER
				);
		addTransformer(TRANSFORMER_DESIGNER, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<List, String> getDesignerMatchTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_MATCH_DESIGNER)) return getTransformer(TRANSFORMER_MATCH_DESIGNER);
		final ConsumerResponseTransformer<List, String> transformer =
			new XMLConsumerResponseTransformer<List>(
				List.class,
				TRANSFORM_FILE_MATCH_DESIGNER
			);
		addTransformer(TRANSFORMER_MATCH_DESIGNER, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<Inventor, String> getInventorTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_INVENTOR)) return getTransformer(TRANSFORMER_INVENTOR);
		final ConsumerResponseTransformer<Inventor, String> transformer =
			new XMLConsumerResponseTransformer<Inventor>(
				Inventor.class,
				TRANSFORM_FILE_INVENTOR
			);
		addTransformer(TRANSFORMER_INVENTOR, transformer);
		return transformer;
	}

	private ConsumerResponseTransformer<List, String> getInventorMatchTransformer() {
		// lazy load the transformer instance and cache it for future access
		if (hasTransformer(TRANSFORMER_MATCH_INVENTOR)) return getTransformer(TRANSFORMER_MATCH_INVENTOR);
		final ConsumerResponseTransformer<List, String> transformer =
			new XMLConsumerResponseTransformer<List>(
				List.class,
				TRANSFORM_FILE_MATCH_INVENTOR
			);
		addTransformer(TRANSFORMER_MATCH_INVENTOR, transformer);
		return transformer;
	}

	/**
	 * Connect to a url and get a person (Applicant/Representative), transforming it
	 * @param url Url to connect
	 * @param type Applicant or Representative
	 * @param <T> Object extending PersonRole
	 * @return Applicant or Representative
	 */
	private <T extends PersonRole> T getPerson(String url, String type) {
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		try {
			final ConsumerResponse<String> response = doRequest(request);
			ConsumerResponseTransformer<T, String> transformer = null;
			switch (type) {
				case PersonInjector.APPLICANT:
					LOGGER.info(">>> Done, we've got Applicant, Transforming it <<<");
					transformer = (ConsumerResponseTransformer<T, String>) getApplicantTransformer(); break;
				case PersonInjector.REPRESENTATIVE:
					LOGGER.info(">>> Done, we've got Representative, Transforming it <<<");
					transformer = (ConsumerResponseTransformer<T, String>) getRepresentativeTransformer(); break;
				case PersonInjector.DESIGNER:
					LOGGER.info(">>> Done, we've got Designer, Transforming it <<<");
					transformer = (ConsumerResponseTransformer<T, String>) getDesignerTransformer(); break;
				case PersonInjector.INVENTOR:
					LOGGER.info(">>> Done, we've got Inventor, Transforming it <<<");
					transformer = (ConsumerResponseTransformer<T, String>) getInventorTransformer(); break;
			}
			T person = doTransform(transformer, response);
			if (OperationCodeType.DELETE.equals(person.getOperationCode())) {
				return null;
			} else {
				return person;
			}
		} catch (RuntimeException re) {
			LOGGER.error("Error getting person of type " + type +" from URL " + url, re);
			return null;
		}
	}

	public void injectApplicants(Object object) {
		if(object instanceof TradeMark) {
			List<Applicant> applicants = ((TradeMark)object).getApplicants()
					.stream()
					.map(a -> (Applicant)getPerson(correctApplicantUri(a.getPersonNumber(), ((TradeMark) object).getRegistrationOffice()), APPLICANT))
					.filter(a -> a!=null && !applicantIsUnpublished(a, (TradeMark) object))
					.collect(Collectors.toList());
			((TradeMark) object).setApplicants(applicants);
		}
		if(object instanceof DesignApplication) {
			List<Applicant> applicants = ((DesignApplication)object).getApplicants()
					.stream()
					.map(a -> (Applicant)getPerson(a.getPersonNumber(), APPLICANT))
					.filter(a -> a!=null)
					.collect(Collectors.toList());
			((DesignApplication) object).setApplicants(applicants);
		}
	}

	private boolean applicantIsUnpublished(Applicant applicant, TradeMark tm){
		if(applicant.getIdentifiers() != null && applicant.getIdentifiers().size()>0){
			Optional<PersonIdentifier> optionalId = applicant.getIdentifiers().stream().filter(id ->
					id != null && id.getValue() != null && id.getValue().equals("389541001")).findFirst();

			if(optionalId.isPresent()&& tm.getRegistrationOffice() != null && tm.getRegistrationOffice().equals("BG")){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void injectRepresentatives(Object object) {
		if(object instanceof TradeMark) {
			List<Representative> representatives = ((TradeMark)object).getRepresentatives()
					.stream()
					.map(r -> (Representative)getPerson(correctRepresentativeUri(r.getPersonNumber(), ((TradeMark) object).getRegistrationOffice()), REPRESENTATIVE))
					.filter(r -> r!=null)
					.collect(Collectors.toList());
			((TradeMark) object).setRepresentatives(representatives);
		}
		if(object instanceof DesignApplication) {
			List<Representative> representatives = ((DesignApplication)object).getRepresentatives()
					.stream()
					.map(r -> (Representative)getPerson(r.getPersonNumber(), REPRESENTATIVE))
					.filter(r -> r!=null)
					.collect(Collectors.toList());
			((DesignApplication) object).setRepresentatives(representatives);
		}
	}

	/**
	 * Applicant autocomplete
	 * @param module DS or TM (only DS autocomplete is implemented).
	 * @param office office
	 * @param text text to search for
	 * @param numberOfRows number of returned rows
	 * @return
	 */
	public String getApplicantAutocomplete(final String module,
										   final String office,
										   final String text,
										   final String numberOfRows) {
		String result = "";
		if("dsefiling".equals(module) || module.startsWith("ds-")) {
			String url = _getUrl(URL_DS_APPLICANT_AUTOCOMPLETE, office, text, numberOfRows);
			result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		}
		if("tmefiling".equals(module) || module.startsWith("tm-") || module.startsWith("ol-")) {
			String url = _getUrl(URL_TM_APPLICANT_AUTOCOMPLETE, office, text, numberOfRows);
			result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		}
		if("ptefiling".equals(module) || module.startsWith("pt-") || module.startsWith("um-") || module.startsWith("ep-") || module.startsWith("sv-") || module.startsWith("spc-") || module.startsWith("is-")) {
			String url = _getUrl(URL_PT_APPLICANT_AUTOCOMPLETE, office, text, numberOfRows);
			result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		}
		return result;
	}

	/**
	 * Get applicant based on module
	 * @param module
	 * @param office
	 * @param applicantId
	 * @return
	 */
	public Applicant getApplicant(final String module,
										   final String office,
										   final String applicantId) {
		String url;
		// DSVIEW
		if("dsefiling".equals(module) || module.startsWith("ds-")) {
			url = _getUrl(URL_DS_GET_APPLICANT, office, applicantId);
		} else if("ptefiling".equals(module) || module.startsWith("pt-") || module.startsWith("um-") || module.startsWith("ep-") || module.startsWith("sv-") || module.startsWith("spc-") || module.startsWith("is-")){
			url = _getUrl(URL_PT_GET_APPLICANT, office, applicantId);
		} else {
			//TMVIEW otherwise
			url = _getUrl(URL_TM_GET_APPLICANT, office, applicantId);
		}
		return this.getPerson(url, PersonInjector.APPLICANT);
	}

	/**
	 * Search applicant
	 * @param module
	 * @param office
	 * @param applicantId
	 * @param applicantName
	 * @param applicantNationality
	 * @param numberOfResults
	 * @return
	 */
	public List<Applicant> searchApplicant(final String module,
										final String office,
										final String applicantId,
										final String applicantName,
										final String applicantNationality,
										final String numberOfResults) {
		throw new NotImplementedException();
	}

	/**
	 * Match applicant
	 * @param module
	 * @param office
	 * @param extApplicant
	 * @param numberOfResults
	 * @return
	 */
	public List<Applicant> matchApplicant(final String module,
										  final String office,
										  final Applicant extApplicant,
										  final String numberOfResults) {
		PersonMatchRequest personMatchRequest = PersonMatchRequestConverter.convertPersonToMatchRequest(extApplicant);
		personMatchRequest.setNumResults(Integer.parseInt(numberOfResults));
		String url = _getUrl(URL_MATCH_APPLICANT);
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		try {
			final ConsumerResponse<String> response = request.doPostRequest(personMatchRequest);
			ConsumerResponseTransformer<List, String> transformer = getApplicantMatchTransformer();
			return doTransform(transformer, response);
		} catch (Exception e) {
			LOGGER.error("Error matching applicant from URL " + url, e);
			return null;
		}
	}

	/**
	 * Save applicant
	 * @param module
	 * @param office
	 * @param user
	 * @param externalObject
	 * @return
	 */
	public Result saveApplicant(final String module,
								final String office,
								final String user,
								final Applicant externalObject) {
		throw new NotImplementedException();
	}

	/**
	 * Representative autocomplete
	 * @param module
	 * @param office
	 * @param text
	 * @param numberOfRows
	 * @return
	 */
	public String getRepresentativeAutocomplete(final String module,
										   final String office,
										   final String text,
										   final String numberOfRows) {
		String result = "";
		if("dsefiling".equals(module) || module.startsWith("ds-")) {
			String url = _getUrl(URL_DS_REPRESENTATIVE_AUTOCOMPLETE, office, text, numberOfRows);
			result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		}
		if("tmefiling".equals(module) || module.startsWith("tm-") || module.startsWith("ol-")) {
			String url = _getUrl(URL_TM_REPRESENTATIVE_AUTOCOMPLETE, office, text, numberOfRows);
			result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		}
		if("ptefiling".equals(module) || module.startsWith("pt-") || module.startsWith("um-") || module.startsWith("ep-") || module.startsWith("sv-") || module.startsWith("spc-") || module.startsWith("is-")) {
			String url = _getUrl(URL_PT_REPRESENTATIVE_AUTOCOMPLETE, office, text, numberOfRows);
			result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		}
		return result;
	}

	public String getIntlPRepresentativeList() {
		String url = _getUrl(URL_INTLP_REPRESENTATIVE_LIST);
		String result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		return result;
	}

	/**
	 * Representative get
	 * @param module
	 * @param office
	 * @param applicantId
	 * @return
	 */
	public Representative getRepresentative(final String module,
								  final String office,
								  final String applicantId) {
		String url;
		// DSVIEW
		if("dsefiling".equals(module) || module.startsWith("ds-")) {
			url = _getUrl(URL_DS_GET_REPRESENTATIVE, office, applicantId);
		} else if("ptefiling".equals(module) || module.startsWith("pt-") || module.startsWith("um-") || module.startsWith("ep-") || module.startsWith("sv-") || module.startsWith("spc-") || module.startsWith("is-")) {
			url = _getUrl(URL_PT_GET_REPRESENTATIVE, office, applicantId);
		} else {
			//TMVIEW otherwise
			url = _getUrl(URL_TM_GET_REPRESENTATIVE, office, applicantId);
		}
		return this.getPerson(url, PersonInjector.REPRESENTATIVE);
	}

	public Representative getIntlPRepresentative(final String representativeId) {
		String url = _getUrl(URL_INTLP_GET_REPRESENTATIVE,representativeId);
		return this.getPerson(url, PersonInjector.REPRESENTATIVE);
	}

	/**
	 * Search representative
	 * @param module
	 * @param office
	 * @param representativeId
	 * @param representativeName
	 * @param representativeNationality
	 * @param numberOfResults
	 * @return
	 */
	public List<Representative> searchRepresentative(final String module,
										   final String office,
										   final String representativeId,
										   final String representativeName,
										   final String representativeNationality,
										   final String numberOfResults) {
		throw new NotImplementedException();
	}

	/**
	 * Match representative
	 * @param module
	 * @param office
	 * @param extRepresentative
	 * @param numberOfResults
	 * @return
	 */
	public List<Representative> matchRepresentative(final String module,
										  final String office,
										  final Representative extRepresentative,
										  final String numberOfResults) {
		PersonMatchRequest personMatchRequest = PersonMatchRequestConverter.convertPersonToMatchRequest(extRepresentative);
		personMatchRequest.setNumResults(Integer.parseInt(numberOfResults));
		String url = _getUrl(URL_MATCH_REPRESENTATIVE);
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		try {
			final ConsumerResponse<String> response = request.doPostRequest(personMatchRequest);
			ConsumerResponseTransformer<List, String> transformer = getRepresentativeMatchTransformer();
			return doTransform(transformer, response);
		} catch (Exception e) {
			LOGGER.error("Error matching representative from URL " + url, e);
			return null;
		}
	}

	/**
	 * Save representative
	 * @param module
	 * @param office
	 * @param user
	 * @param externalObject
	 * @return
	 */
	public Result saveRepresentative(final String module,
								final String office,
								final String user,
								final Representative externalObject) {
		throw new NotImplementedException();
	}

	/**
	 * Designer autocomplete
	 * @param module
	 * @param office
	 * @param text
	 * @param numberOfRows
	 * @return
	 */
	public String getDesignerAutocomplete(final String module,
												final String office,
												final String text,
												final String numberOfRows) {
		String result;
		String url = _getUrl(URL_DESIGNER_AUTOCOMPLETE, office, text, numberOfRows);
		result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		return result;
	}

	/**
	 * Get designer
	 * @param module
	 * @param office
	 * @param designerId
	 * @return
	 */
	public Designer getDesigner(final String module,
											final String office,
											final String designerId) {
		String url = _getUrl(URL_DS_GET_DESIGNER, office, designerId);

		return this.getPerson(url, PersonInjector.DESIGNER);
	}

	public String getInventorAutocomplete(final String module,
										  final String office,
										  final String text,
										  final String numberOfRows) {
		String result;
		String url = _getUrl(URL_INVENTOR_AUTOCOMPLETE, office, text, numberOfRows);
		result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
		return result;
	}


	public Inventor getInventor(final String module,
								final String office,
								final String designerId) {
		String url = _getUrl(URL_PT_GET_INVENTOR, office, designerId);

		return this.getPerson(url, PersonInjector.INVENTOR);
	}

	public List<Inventor> matchInventor(final String module,
										final String office,
										final Inventor extInventor,
										final String numberOfResults) {
		PersonMatchRequest personMatchRequest = PersonMatchRequestConverter.convertPersonToMatchRequest(extInventor);
		personMatchRequest.setNumResults(Integer.parseInt(numberOfResults));
		String url = _getUrl(URL_MATCH_INVENTOR);
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		try {
			final ConsumerResponse<String> response = request.doPostRequest(personMatchRequest);
			ConsumerResponseTransformer<List, String> transformer = getInventorMatchTransformer();
			return doTransform(transformer, response);
		} catch (Exception e) {
			LOGGER.error("Error matching applicant from URL " + url, e);
			return null;
		}
	}

	/**
	 * Search designer
	 * @param module
	 * @param office
	 * @param designerId
	 * @param designerName
	 * @param nationality
	 * @param numberOfResults
	 * @return
	 */
	public List<Designer> searchDesigner(final String module,
													 final String office,
													 final String designerId,
													 final String designerName,
													 final String nationality,
													 final String numberOfResults) {
		throw new NotImplementedException();
	}

	/**
	 * Match designer
	 * @param module
	 * @param office
	 * @param extDesigner
	 * @param numberOfResults
	 * @return
	 */
	public List<Designer> matchDesigner(final String module,
													final String office,
													final Designer extDesigner,
													final String numberOfResults) {
		PersonMatchRequest personMatchRequest = PersonMatchRequestConverter.convertPersonToMatchRequest(extDesigner);
		personMatchRequest.setNumResults(Integer.parseInt(numberOfResults));
		String url = _getUrl(URL_MATCH_DESIGNER);
		final ConsumerRequest request = new RestXmlConsumerRequest(url);
		try {
			final ConsumerResponse<String> response = request.doPostRequest(personMatchRequest);
			ConsumerResponseTransformer<List, String> transformer = getDesignerMatchTransformer();
			return doTransform(transformer, response);
		} catch (Exception e) {
			LOGGER.error("Error matching applicant from URL " + url, e);
			return null;
		}
	}

	/**
	 * Save designer
	 * @param module
	 * @param office
	 * @param user
	 * @param externalObject
	 * @return
	 */
	public Result saveDesigner(final String module,
									 final String office,
									 final String user,
									 final Designer externalObject) {
		throw new NotImplementedException();
	}

	private String correctApplicantUri(String originalUri, String registrationOffice){
		if(uriEligibleForChange(originalUri, registrationOffice)){
			String uriNumber = originalUri.substring(originalUri.lastIndexOf("/")+1);
			return WO_APPLICANT_BASE_URI + uriNumber;
		}
		return originalUri;
	}

	private String correctRepresentativeUri(String originalUri, String registrationOffice){
		if(uriEligibleForChange(originalUri, registrationOffice)){
			String uriNumber = originalUri.substring(originalUri.lastIndexOf("/")+1);
			return WO_REPRESENTATIVE_BASE_URI + uriNumber;
		}
		return originalUri;
	}

	private boolean uriEligibleForChange(String originalUri, String registrationOffice){
		if(registrationOffice != null && registrationOffice.equalsIgnoreCase("WO")
				&& originalUri != null && originalUri.indexOf('/') > -1 && originalUri.length() > originalUri.indexOf('/')+1){
			return true;
		}
		return false;
	}
}
