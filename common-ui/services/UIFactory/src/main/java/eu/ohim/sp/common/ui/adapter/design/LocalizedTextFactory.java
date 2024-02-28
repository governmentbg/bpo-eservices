//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.common.ui.adapter.design;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.core.domain.design.EUFirstLanguageCode;
import eu.ohim.sp.core.domain.design.LocalizedText;
import org.springframework.stereotype.Component;

/**
 * #DS Class Integration changes.
 * A factory for creating LocalizedText objects.
 * @author Ramittal
 *
 */
@Component
public class LocalizedTextFactory implements UIFactory<LocalizedText, String> {

	@Override
	public LocalizedText convertTo(String form) {
		LocalizedText core = new LocalizedText();
		core.setValue(form);
		return core;
	}

	/**
	 * convert to
	 * 
	 * @param form to convert
	 * @param languageCode to set the specific language
	 * @return LocalizedText for text
	 * 
	 */
	public LocalizedText convertTo(String form, String languageCode) {
		LocalizedText core = convertTo(form);
		core.setLanguageCode(EUFirstLanguageCode.fromValue(languageCode));
		return core;
	}

	@Override
	public String convertFrom(LocalizedText core) {
		return core.getValue();
	}
}
