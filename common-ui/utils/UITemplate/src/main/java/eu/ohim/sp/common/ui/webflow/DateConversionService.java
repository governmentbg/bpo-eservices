/*******************************************************************************
 * * $Id:: DateConversionService.java 113489 2013-04-22 14:59:26Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.ParseException;

import javax.annotation.PostConstruct;

public class DateConversionService extends
		DefaultConversionService {

	@Value("${iu.template.dateFormat}")
	private String dateFormat;

	@PostConstruct
	public void installFormatters() {
		addDefaultConverters();
	}

	protected void addDefaultConverters() {
		addConverter(validateString());
		addConverter(StringToDate());
		addConverter(DateToString());
	}

	public Converter<String, String> validateString() {
		return new Converter<String, String>() {
			@Override
			public String convert(String source) {
		    	if (!StringUtils.containsNone(source, "<>")) {
		    		throw new IllegalArgumentException();
		    	}
				return source;
			}
			
		};

	}

	public Converter<String, Date> StringToDate() {
		return new Converter<String, Date>() {

			@Override
			public Date convert(String source) {
				SimpleDateFormat sdf = new SimpleDateFormat(getDatePattern());
				try {
					return sdf.parse(source);
				} catch (ParseException e) {
					return null;
				} catch (java.text.ParseException e) {
					return null;
				}
			}
		};
	}

	protected String getDatePattern() {
		if (dateFormat == null)
			return "dd.MM.yyyy";
		else
			return dateFormat;
	}

	public Converter<Date, String> DateToString() {
		return new Converter<Date, String>() {

			@Override
			public String convert(Date source) {
				SimpleDateFormat sdf = new SimpleDateFormat(getDatePattern());

				try {
					return sdf.format(source);
				} catch (ParseException e) {
					return null;
				}
			}
		};
	}


}
