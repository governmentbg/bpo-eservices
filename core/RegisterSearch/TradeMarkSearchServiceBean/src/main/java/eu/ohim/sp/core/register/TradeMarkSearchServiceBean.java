/*
 *  TradeMarkService:: TradeMarkService 07/10/13 21:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.register;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.ForeignRegistration;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.register.TradeMarkSearchClientInside;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class TradeMarkSearchServiceBean implements TradeMarkSearchServiceRemote, TradeMarkSearchServiceLocal {

	private Logger logger = Logger.getLogger(TradeMarkSearchServiceBean.class);

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService rulesService;

	@Inject @TradeMarkSearchClientInside
	private TradeMarkSearchService adapter;

	/**
	 * The Constant TRADEMARKLIST.
	 */
	private static final String TRADEMARK_SET = "trademark_set";
	private static final String OPPOSITION_SET = "opposition_set";
	private static final String LICENCE_SET = "licence_set";
	private static final String CORRESPONDENCE_ADDRESS_SET = "correspondenceAddress_set";
	private static final String APPEAL_SET = "appeal_set";
	private static final String MARKVIEW_SET = "markview_set";
	private static final String FOREIGNREGISTRATION_SET = "foreignregistration_set";

	@Override
	@Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
	public String getForeignTradeMarkAutocomplete(String office, String text, Integer numberOfResults) {
		return adapter.getTradeMarkAutocomplete(office, text, numberOfResults);
	}

	@Override
	@Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
	public String getTradeMarkAutocomplete(String office, String text, Integer numberOfResults) {
		return adapter.getTradeMarkAutocomplete(office, text, numberOfResults);
	}

	@Override
	@Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
	public TradeMark getTradeMark(String office, String tradeMarkId) {
		return adapter.getTradeMark(office, tradeMarkId);
	}

	@Override
	@Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
	public TradeMark getForeignTradeMark(String office, String tradeMarkId) {
        return adapter.getForeignTradeMark(office, tradeMarkId);
	}

	@Override
	@Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
	public TradeMark getInternationalTradeMark(String office, String tradeMarkId, Boolean extraImport) {
		return adapter.getInternationalTradeMark(office, tradeMarkId, extraImport);
	}

	@Override
	@Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
	public List<TradeMark> getPreclearanceReport(String office, TradeMark tradeMark) {
		return adapter.getPreclearanceReport(office, tradeMark);
	}

	@Override
	public ErrorList validateTradeMark(String module, TradeMark tradeMark, RulesInformation rulesInformation) {
		if (tradeMark != null) {
			logger.debug(">>> Validate TradeMark: " + tradeMark.getMarkKind());
		}

		// Variable declaration
		List<Object> objects = new ArrayList<Object>();
		Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

		// Prepares the objects to insert in the session
		objects.add(section);
		objects.add(tradeMark);
		objects.add(rulesInformation);

		// Starts the check
        ErrorList errors = rulesService.validate(module, TRADEMARK_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate TradeMark END");
		}

		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrorList validateOpposition(String module, OppositionGround opposition, RulesInformation rulesInformation) {
		if (opposition != null) {
			logger.debug(">>> Validate Opposition: " + opposition.getLegalActVersion());
		}

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();

        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        objects.add(section);
		objects.add(opposition);
		objects.add(rulesInformation);
        objects.add(rulesInformation.getCustomObjects().get("sections"));

		// Starts the check
		ErrorList errors = rulesService.validate(module, OPPOSITION_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate Opposition END");
		}

		return errors;
	}

	@Override
	public ErrorList validateLicence(String module, Licence licence, RulesInformation rulesInformation) {
		if (licence != null) {
			logger.debug(">>> Validate licence: " + licence);
		}

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();

		Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

		objects.add(section);
		objects.add(licence);
		objects.add(rulesInformation);
		objects.add(rulesInformation.getCustomObjects().get("sections"));

		// Starts the check
		ErrorList errors = rulesService.validate(module, LICENCE_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate Licence END");
		}

		return errors;
	}

	@Override
	public ErrorList validateAppeal(String module, Appeal appeal, RulesInformation rulesInformation) {
		if (appeal != null) {
			logger.debug(">>> Validate appeal: " + appeal);
		}

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();

		Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

		objects.add(section);
		objects.add(appeal);
		objects.add(rulesInformation);
		objects.add(rulesInformation.getCustomObjects().get("sections"));

		// Starts the check
		ErrorList errors = rulesService.validate(module, APPEAL_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate Appeal END");
		}

		return errors;
	}

	@Override
	public ErrorList validateApplicationCA(String module, ContactDetails contactDetails, RulesInformation rulesInformation){
		if (contactDetails != null) {
			logger.debug(">>> Validate contactDetails: " + contactDetails);
		}

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();

		Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

		objects.add(section);
		objects.add(contactDetails);
		objects.add(rulesInformation);
		objects.add(rulesInformation.getCustomObjects().get("sections"));

		// Starts the check
		ErrorList errors = rulesService.validate(module, CORRESPONDENCE_ADDRESS_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate contactDetails END");
		}

		return errors;
	}

	@Override
	public ErrorList validateMarkView(String module, ImageSpecification markView, RulesInformation rulesInformation) {
		if (markView != null) {
			logger.debug(">>> Validate markView (ImageSpecification): " + markView);
		}

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();

		Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

		objects.add(section);
		objects.add(markView);
		objects.add(rulesInformation);
		objects.add(rulesInformation.getCustomObjects().get("sections"));

		// Starts the check
		ErrorList errors = rulesService.validate(module, MARKVIEW_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate markView (ImageSpecification) END");
		}

		return errors;
	}

	public ErrorList validateForeignRegistration(String module, ForeignRegistration foreignRegistration, RulesInformation rulesInformation){
		if (foreignRegistration != null) {
			logger.debug(">>> Validate foreign registration : " + foreignRegistration);
		}
		//Variable declaration
		List<Object> objects = new ArrayList<Object>();

		Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

		objects.add(section);
		objects.add(foreignRegistration);
		objects.add(rulesInformation);
		objects.add(rulesInformation.getCustomObjects().get("sections"));
		// Starts the check
		ErrorList errors = rulesService.validate(module, FOREIGNREGISTRATION_SET, objects);

		if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate foreign registration END");
		}

		return errors;
	}
}
