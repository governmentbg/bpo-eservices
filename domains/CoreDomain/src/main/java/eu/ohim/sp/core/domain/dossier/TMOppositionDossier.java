package eu.ohim.sp.core.domain.dossier;

import eu.ohim.sp.core.domain.opposition.TMOpposition;


/**
 * Dossier of type Trademark representation.
 * 
 * @author masjose
 * 
 */
public class TMOppositionDossier extends Dossier {

	/** Serial id **/
	private static final long serialVersionUID = 6654825580363175689L;

	/**
	 * The trademark data associated with the dossier
	 */
	private TMOpposition tmOpposition;

	/**
	 * @return the tmOpposition
	 */
	public TMOpposition getTmOpposition() {
		return tmOpposition;
	}

	/**
	 * @param tmOpposition the tmOpposition to set
	 */
	public void setTmOpposition(TMOpposition tmOpposition) {
		this.tmOpposition = tmOpposition;
	}

	
}