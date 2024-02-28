/*******************************************************************************
 * * $Id:: OpposedTrademarkFactory.java 50771 2012-11-14 15:10:27Z         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.opposition.OpposedTradeMarkForm;
import eu.ohim.sp.common.ui.form.trademark.TradeMarkKind;
import eu.ohim.sp.core.domain.trademark.TradeMark;

@Component
public class OpposedTrademarkFactory implements UIFactory<TradeMark, OpposedTradeMarkForm> {

	@Override
	public TradeMark convertTo(OpposedTradeMarkForm form) {

		if(form == null){
			return new TradeMark();
		}
	
		TradeMark coreObject = new TradeMark();
		coreObject.setApplicationNumber(form.getApplicationNumber());
		coreObject.setApplicationDate(form.getApplicationDate());
		coreObject.setCurrentStatus(form.getApplicationStatus());
		coreObject.setPublicationDate(form.getPublicationDate());
		// coreObject.setApplicationRepresentation();
		//coreObject.setTradeMarkName(form.getTradeMarkName());
		//coreObject.setMarkDisclaimers(new List(form.getMarkDisclaimer()));
		//coreObject.setApplicants(applicants);
		//coreObject.setRepresentatives(representatives);
		//coreObject.setClassDescriptions(classDescriptions);
	/*	switch(form.getTradeMarkType()){
	
			case INDIVIDUAL :
				coreObject.setMarkRightKind(MarkKind.INDIVIDUAL);
			case COLLECTIVE : 
				coreObject.setMarkRightKind(MarkKind.COLLECTIVE);
			case CERTIFICATE : 
				coreObject.setMarkRightKind(MarkKind.CERTIFICATE);
			case CERTIFICATION :
				coreObject.setMarkRightKind(MarkKind.CERTIFICATION);
			case GUARANTEE : 
				coreObject.setMarkRightKind(MarkKind.GUARANTEE);
			case DEFENSIVE :
				coreObject.setMarkRightKind(MarkKind.DEFENSIVE);
			case OTHER :
				coreObject.setMarkRightKind(MarkKind.OTHER);
			default :
				coreObject.setMarkRightKind(null);
		}*/
				
		return coreObject;
	}

	@Override
	public OpposedTradeMarkForm convertFrom(TradeMark core) {
		OpposedTradeMarkForm uiObject = new OpposedTradeMarkForm();
		uiObject.setApplicationDate(core.getApplicationDate());
		uiObject.setApplicationNumber(core.getApplicationNumber());
		uiObject.setApplicationRepresentation(null); // We need to find out what to put in here
		uiObject.setApplicationStatus(core.getCurrentStatus()); //Its not applicable for renewal, we need to find out how to fill it for revocation/invalidity/etc.
		uiObject.setPublicationDate(core.getPublicationDate());
		uiObject.setMarkDisclaimer(null); // core.getMarkDisclaimers()); //TODO comprobar si markDisclaimer debe de ser una lista en el formulario
		uiObject.setOpposedTradeMarkApplicant(null);// debe heredar de applicants
		uiObject.setOpposedTradeMarkRepresentative(null);// debe heredarde representative
		
		uiObject.setTradeMarkName(null);//
		
		
		
		switch(core.getMarkRightKind()){

			case INDIVIDUAL :
				uiObject.setTradeMarkKind(TradeMarkKind.INDIVIDUAL);
				break;
			case COLLECTIVE :
				uiObject.setTradeMarkKind(TradeMarkKind.COLLECTIVE);
				break;
			case CERTIFICATE :
				uiObject.setTradeMarkKind(TradeMarkKind.CERTIFICATE);
				break;
			case CERTIFICATION :
				uiObject.setTradeMarkKind(TradeMarkKind.CERTIFICATION);
				break;
			case GUARANTEE :
				uiObject.setTradeMarkKind(TradeMarkKind.GUARANTEE);
				break;
			case DEFENSIVE :
				uiObject.setTradeMarkKind(TradeMarkKind.DEFENSIVE);
				break;
			case OTHER :
				uiObject.setTradeMarkKind(TradeMarkKind.OTHER);
				break;
			default :
				uiObject.setTradeMarkType(null);
				break;
		}



		return uiObject;
	}

}
