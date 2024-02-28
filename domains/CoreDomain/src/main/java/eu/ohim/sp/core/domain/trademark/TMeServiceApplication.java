/*
 *  CoreDomain:: TMeServiceApplication 03/09/13 09:33 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

/**
 * 
 */
package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import eu.ohim.sp.core.domain.application.EServiceApplication;

/**
 * The Class TradeMarkApplication.
 */
public class TMeServiceApplication extends EServiceApplication implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4145649964683884663L;


	/** The trade mark. */
	private List<LimitedTradeMark> tradeMarks;

	/**
	 * @return the tradeMarks
	 */
	public List<LimitedTradeMark> getTradeMarks() {
		return tradeMarks;
	}

	/**
	 * @param tradeMarks the tradeMarks to set
	 */
	public void setTradeMarks(List<LimitedTradeMark> tradeMarks) {
		this.tradeMarks = tradeMarks;
	}

	/**
	 * Return the removed good and services
	 * @param gshelper the original good and services
	 * @return
     */
	public List<ClassDescription> getRemovedGS(GSHelperDetails gshelper ){
		List<ClassDescription> result = new ArrayList<>();
		if(gshelper == null || gshelper.getTmApplicationNumber().isEmpty() || getTradeMarks() == null || getTradeMarks().size() == 0){
			return result;
		}
		LimitedTradeMark theTM = null;
		for(LimitedTradeMark tmDetails: getTradeMarks()){
			if(!tmDetails.getApplicationNumber().isEmpty() && tmDetails.getApplicationNumber().equals(gshelper.getTmApplicationNumber())){
				theTM = tmDetails;
				break;
			}
		}

		if(theTM == null){
			return result;
		}

		List<ClassDescription> keptGS = gshelper.getClassDescriptions();
		List<ClassDescription> oriGS = theTM.getClassDescriptions();

		for(ClassDescription oClass: oriGS){
            Optional<ClassDescription> keptClass = keptGS.stream().filter(k -> k.getClassNumber()
                                .equals(oClass.getClassNumber())).findFirst();
            if(keptClass.isPresent()) {
                // The class has been kept. Look for deleted terms
                List<ClassificationTerm> delTerms = oClass.getClassificationTerms().stream()
                        .filter(ct -> !keptClass.get().getClassificationTerms().stream()
                                .anyMatch(kct -> kct.getTermText().trim().equals(ct.getTermText().trim())))
                        .collect(Collectors.toList());
                if(!delTerms.isEmpty()){
                    ClassDescription toAdd = new ClassDescription();
                    toAdd.setClassNumber(oClass.getClassNumber());
                    toAdd.setLanguage(oClass.getLanguage());
                    toAdd.setClassificationTerms(delTerms);
                    result.add(toAdd);
                }
            } else {
                // Class has been deleted
                result.add(oClass);
			}
		}

		return result;
	}

}
