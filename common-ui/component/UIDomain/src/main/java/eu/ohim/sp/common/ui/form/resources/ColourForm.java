/*******************************************************************************
 * * $Id:: ErrorTerm.java 14333 2012-10-29 13:23:34Z karalch                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.resources;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Objects;

public class ColourForm extends AbstractForm {

	private static final long serialVersionUID = 5760939525665500303L;

	private String value;

	private String format;

	public ColourForm() {

	}

	public ColourForm(String value, String format) {
		super();
		this.value = value;
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ColourForm that = (ColourForm) o;
		return Objects.equals(value, that.value) &&
			Objects.equals(format, that.format);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), value, format);
	}
}
