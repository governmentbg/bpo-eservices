package eu.ohim.sp.core.domain.opposition;

import java.util.List;

/**
 * Stores all the necessary information for the Revocation Grounds.
 *
 * @author ssegura
 */
public class OppositionRevocationGrounds extends OppositionGround {
	
	private static final long serialVersionUID = 1L;

	private String category = "revocation";
	private List <RevocationGroundLawArticle> revocationGroundLawArticles;

	/**
	 * @return the revocationGroundLawArticles
	 */
	public List<RevocationGroundLawArticle> getRevocationGroundLawArticles() {
		return revocationGroundLawArticles;
	}

	/**
	 * @param revocationGroundLawArticles the revocationGroundLawArticles to set
	 */
	public void setRevocationGroundLawArticles(
			List<RevocationGroundLawArticle> revocationGroundLawArticles) {
		this.revocationGroundLawArticles = revocationGroundLawArticles;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
