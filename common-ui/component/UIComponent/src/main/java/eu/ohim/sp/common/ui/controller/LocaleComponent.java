/*******************************************************************************
 * * $Id:: LocaleComponent.java 49264 2012-10-29 13:23:34Z karalch               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Component that is used to have visibility of the available locales of the application.
 *
 * @author karalch
 */
@Component
public class LocaleComponent {
	
	private static final Logger logger = Logger.getLogger(LocaleComponent.class);
	
	/** The locale resolver. */
	@Autowired
	private SessionLocaleResolver localeResolver;
	
	/** The reloadable resource bundle message source. */
	@Autowired
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	/** A property for the default language of the application. */
	@Value("${defaultLanguage}")
	private String defaultLanguage;

	/** A map that contains all the available locales. */
	private Map<String, SPLocale> availableLocale;
	
	private static final String LANGUAGE_LABEL = "language.label";

	/**
	 * Initializes the available locales for the application. It is better to have common default
	 * language as the Locale.getDefault(). To check if the environment user.language should be the 
	 * same as the default language of the application. It checks whether this language is resolved by 
	 * the configuration of the {@link ReloadableResourceBundleMessageSource}
	 */
	@PostConstruct
	void init() {
		availableLocale = new HashMap<String, SPLocale>();
		Locale defaultLocale = new Locale(defaultLanguage);
		Locale.setDefault(defaultLocale);
		String defaultLanguageLabel = reloadableResourceBundleMessageSource.getMessage(LANGUAGE_LABEL, null, defaultLocale);
		if(LANGUAGE_LABEL.equals(defaultLanguageLabel)){
			logger.warn("Property: " + LANGUAGE_LABEL + " not found for default locale: " + defaultLocale);
		}
		
		availableLocale.put(defaultLanguage, new SPLocale(defaultLanguage, defaultLanguageLabel));
		for (Locale locale : Locale.getAvailableLocales()) {
			String languageLabel = reloadableResourceBundleMessageSource.getMessage(LANGUAGE_LABEL, null, locale);
			if (!languageLabel.equals(defaultLanguageLabel)) {
				availableLocale.put(locale.getLanguage(), new SPLocale(locale.getLanguage(), languageLabel));
			}
		}
	}
	
	/**
	 * Returns a list of the available locales of the application that can be used for the current release.
	 *
	 * @return a list of the available locales
	 */
	public List<SPLocale> getAvailableLocale() {
		List<SPLocale> locales = new ArrayList<SPLocale>();
		for (Entry<String, SPLocale> entry : availableLocale.entrySet()) {
			locales.add(entry.getValue());
		}
		return locales;
	}
	
	/**
	 * Finds which locale is currently used by the current session.
	 *
	 * @param request the request
	 * @return the locale
	 */
	public SPLocale getLocale(HttpServletRequest request) {
		return availableLocale.get(localeResolver.resolveLocale(request).getLanguage());
	}
	
	/**
	 * POJO that contains the value of the language of the locale
	 * and label code that is presented to the UI.
	 *
	 * @author karalch
	 */
	public class SPLocale {
		
		/** The label that be later resolved by the {@link ReloadableResourceBundleMessageSource} with the use of the value as locale. */
		private String label;
		
		/** The language code of the locale. */
		private String value;
		
		/**
		 * Instantiates a new SP locale.
		 *
		 * @param value the value
		 * @param label the label
		 */
		protected SPLocale(String value, String label) {
			this.label = label;
			this.value = value;
		}
		
		/**
		 * Gets the label.
		 *
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}
		
		/**
		 * Sets the label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		
		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Sets the value.
		 *
		 * @param value the new value
		 */
		public void setValue(String value) {
			this.value = value;
		}
	}
}
