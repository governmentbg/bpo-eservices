
package eu.ohim.sp.common.ui.adapter.opposition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.opposition.OppositionRelativeGrounds;
import eu.ohim.sp.core.domain.opposition.OppositionRevocationGrounds;

@Component
public class OppositionGroundFactory implements UIFactory<OppositionGround, OppositionBasisForm> {

	@Autowired
	private OppositionAbsoluteGroundsFactory oppositionAbsoluteGroundsFactory;

	@Autowired
	private OppositionRelativeGroundsFactory oppositionRelativeGroundsFactory;

	@Autowired
	private OppositionRevocationGroundsFactory oppositionRevocationGroundsFactory;

	@Override
	public OppositionGround convertTo(OppositionBasisForm form) {
		if(form == null)
        {
            return null;
        }
		
		switch (form.getGroundCategory()) {
			case ABSOLUTE_GROUNDS:
				return oppositionAbsoluteGroundsFactory.convertTo(form);
			case RELATIVE_GROUNDS:
				return oppositionRelativeGroundsFactory.convertTo(form);	
			case REVOCATION_GROUNDS:
				return oppositionRevocationGroundsFactory.convertTo(form);
		}
		return null;
	}

	@Override
	public OppositionBasisForm convertFrom(OppositionGround oppositionGround) {
		if (oppositionGround == null) {
			return null;
		} 
		if (oppositionGround instanceof OppositionAbsoluteGrounds) {
			return oppositionAbsoluteGroundsFactory.convertFrom((OppositionAbsoluteGrounds) oppositionGround);
		}
		else
			if (oppositionGround instanceof OppositionRelativeGrounds) {
				return oppositionRelativeGroundsFactory.convertFrom((OppositionRelativeGrounds) oppositionGround);
			}
			else
				if (oppositionGround instanceof OppositionRevocationGrounds) {
					return oppositionRevocationGroundsFactory.convertFrom((OppositionRevocationGrounds) oppositionGround);
				}
				
		return null;		
				
	}
}

