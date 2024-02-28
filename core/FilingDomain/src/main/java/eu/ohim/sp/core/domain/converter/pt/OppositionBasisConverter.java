/*
 *  FspDomain:: OppositionBasisConverter 24/04/17 11:37 albemar $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.pt;

import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.opposition.OppositionRelativeGrounds;
import eu.ohim.sp.filing.domain.pt.OppositionBasisType;
import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;
import java.util.List;

public class OppositionBasisConverter implements CustomConverter, Converter, MapperAware {

	/**
	 * It gives access to the dozer mapper
	 */
	private Mapper mapper;

	/**
	 * It is used to set the mapper used on the context
	 * @param mapper
	 */
	@Override
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Object convert(Class type, Object value) {
		/** filing to core (LOAD) **/
		if(value instanceof List && ((ArrayList)value).size() > 0 && (((ArrayList) value).get(0) instanceof OppositionBasisType)) {
			List<OppositionAbsoluteGrounds> a = new ArrayList<>();
			List<OppositionRelativeGrounds> b = new ArrayList<>();
			((ArrayList)value).stream().forEach(
					i -> {
						if("absolute".equals(((OppositionBasisType) i).getCategory())) {
							OppositionAbsoluteGrounds g = mapper.map(i, OppositionAbsoluteGrounds.class);
							a.add(g);
						}

						if("relative".equals(((OppositionBasisType) i).getCategory()) ) {
							OppositionRelativeGrounds g = mapper.map(i, OppositionRelativeGrounds.class);
							b.add(g);
						}
					}
			);
			if(a.size() > 0) {
				return a;
			}
			if(b.size() > 0) {
				return b;
			}
		}
		/** end filing to core **/

		/** core to filing (SAVE) **/
		if(value instanceof List && ((ArrayList)value).size() > 0 && (((ArrayList) value).get(0) instanceof OppositionGround)) {
			List<OppositionBasisType> o = new ArrayList<>();
			((ArrayList) value).stream().forEach(
					i -> {
						OppositionBasisType ob = mapper.map(i, OppositionBasisType.class);
						o.add(ob);
					}
			);
			return o;
		}
		/** end core to filing **/

		return null;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}

}
