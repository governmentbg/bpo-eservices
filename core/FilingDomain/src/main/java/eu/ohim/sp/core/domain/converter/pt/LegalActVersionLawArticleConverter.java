/*
 *  FspDomain:: LegalActVersionLawArticleConverter 31/10/13 17:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.pt;

import eu.ohim.sp.core.domain.converter.TextConverter;
import eu.ohim.sp.core.domain.opposition.LawArticle;
import eu.ohim.sp.core.domain.opposition.LegalActVersion;
import eu.ohim.sp.filing.domain.pt.OppositionGroundArticleType;
import eu.ohim.sp.filing.domain.pt.Text;
import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

import java.util.ArrayList;
import java.util.List;


public class LegalActVersionLawArticleConverter implements CustomConverter, Converter {
	private TextConverter textConverter;
	
	@Override
	public Object convert(Class type, Object value) {
		 if (value instanceof ArrayList) {
			 ArrayList<OppositionGroundArticleType> opGroundArticles = (ArrayList<OppositionGroundArticleType>)value;
			 LegalActVersion lav = new LegalActVersion();
			 ArrayList<LawArticle> lal = new ArrayList<>();
			 for(OppositionGroundArticleType ground : opGroundArticles) {
				 LawArticle la = new LawArticle();
				 if(ground.getOppositionGroundLegalAct().getValue() != null) {
					 lav.setNameVersion(ground.getOppositionGroundLegalAct().getValue());
				 }
				 if(ground.getOppositionGroundLegalAct().getLanguage() != null) {
					 lav.setCodeVersion(ground.getOppositionGroundLegalAct().getLanguage());
				 }
				 if(ground.getOppositionGroundArticleReference().getValue() != null) {
					 la.setReference(ground.getOppositionGroundArticleReference().getValue().trim());
				 }
				 lal.add(la);
			 }
			 lav.setArticles(lal);
			 return lav;
		 }
		 if (value instanceof LegalActVersion) {
			LegalActVersion legalAct = (LegalActVersion)value;
			List <OppositionGroundArticleType> opGroundArticles = new ArrayList<OppositionGroundArticleType>();
			textConverter = new TextConverter();
			for (LawArticle la:legalAct.getArticles()){
				if (la!=null){
					OppositionGroundArticleType opGroundArticle = new OppositionGroundArticleType();
					Text articleReference = (Text)textConverter.convert(null, la.getReference(), Text.class, null);
					if (articleReference == null) {
						articleReference = new Text();
                    }
					opGroundArticle.setOppositionGroundArticleReference(articleReference);
					Text articleText = (Text)textConverter.convert(null, la.getText(), Text.class, null);
					if (articleText == null) {
						articleText = new Text();
                    }
					articleText.setLanguage(la.getLanguage());
					opGroundArticle.setOppositionGroundArticleText(articleText);
					Text legalActText = (Text)textConverter.convert(null, legalAct.getNameVersion(), Text.class, null);
					if (legalActText == null) {
						legalActText = new Text();
                    }
					legalActText.setLanguage(legalAct.getCodeVersion());
					opGroundArticle.setOppositionGroundLegalAct(legalActText);
					opGroundArticles.add(opGroundArticle);
				}
			}
			return opGroundArticles;
		}
		return null;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}

}
