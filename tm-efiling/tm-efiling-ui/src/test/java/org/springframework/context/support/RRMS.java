package org.springframework.context.support;

import java.text.MessageFormat;
import java.util.Locale;

public class RRMS extends ReloadableResourceBundleMessageSource {

	@Override
	public MessageFormat resolveCode(String code, Locale locale) {
		return new MessageFormat(getParentMessageSource().getMessage(code, null, locale), locale);
	}
}
