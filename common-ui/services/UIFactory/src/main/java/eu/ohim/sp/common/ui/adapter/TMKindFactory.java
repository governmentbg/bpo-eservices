package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.trademark.TradeMarkKind;
import eu.ohim.sp.core.domain.trademark.MarkKind;

@Component
public class TMKindFactory implements UIFactory<MarkKind, TradeMarkKind> {

	@Override
	public MarkKind convertTo(TradeMarkKind uiKind) {
		
		if(uiKind == null){
			return MarkKind.INDIVIDUAL;
		}
		switch(uiKind){
		
		case INDIVIDUAL :
			return MarkKind.INDIVIDUAL;
		case COLLECTIVE : 
			return MarkKind.COLLECTIVE;
		case CERTIFICATE : 
			return MarkKind.CERTIFICATE;
		case CERTIFICATION :
			return MarkKind.CERTIFICATION;
		case GUARANTEE : 
			return MarkKind.GUARANTEE;
		case DEFENSIVE :
			return MarkKind.DEFENSIVE;
		case OTHER :
			return MarkKind.OTHER;
		default :
			return MarkKind.INDIVIDUAL;
		}
	}

	@Override
	public TradeMarkKind convertFrom(MarkKind coreKind) {
		if(coreKind == null){
			return TradeMarkKind.INDIVIDUAL;
		}
		switch(coreKind){
		case INDIVIDUAL :
			return TradeMarkKind.INDIVIDUAL;
		case COLLECTIVE :
			return TradeMarkKind.COLLECTIVE;
		case CERTIFICATE :
			return TradeMarkKind.CERTIFICATE;
		case CERTIFICATION :
			return TradeMarkKind.CERTIFICATION;
		case GUARANTEE :
			return TradeMarkKind.GUARANTEE;
		case DEFENSIVE :
			return TradeMarkKind.DEFENSIVE;
		case OTHER :
			return TradeMarkKind.OTHER;
		default :
			return TradeMarkKind.INDIVIDUAL;
		}
	}
}
