package eu.ohim.sp.core.domain.dossier;

import eu.ohim.sp.core.domain.trademark.TradeMark;

/**
 * Dossier of type Trademark representation.
 * 
 * @author masjose
 * 
 */
public class TradeMarkDossier extends Dossier {

	/** Serial id **/
	private static final long serialVersionUID = 6654825580363175689L;

	/**
	 * The trademark data associated with the dossier
	 */
	private TradeMark tradeMark;

	/**
	 * @return the tradeMark
	 */
	public TradeMark getTradeMark() {
		return tradeMark;
	}

	/**
	 * @param tradeMark
	 *            the tradeMark to set
	 */
	public void setTradeMark(TradeMark tradeMark) {
		this.tradeMark = tradeMark;
	}

}