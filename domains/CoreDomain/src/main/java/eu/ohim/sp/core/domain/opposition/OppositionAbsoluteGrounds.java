package eu.ohim.sp.core.domain.opposition;

import java.util.List;

/**
 * Stores all the necessary information for the Absolute Grounds.
 *
 * @author ssegura
 */
public class OppositionAbsoluteGrounds extends OppositionGround {
	
	private static final long serialVersionUID = 1L;
	
	private List <AbsoluteGroundLawArticle> absoluteGroundLawArticles;
	private String category = "absolute";

	/**
	 * @return the absoluteGroundLawArticles
	 */
	public List<AbsoluteGroundLawArticle> getAbsoluteGroundLawArticles() {
		return absoluteGroundLawArticles;
	}

	/**
	 * @param absoluteGroundLawArticles the absoluteGroundLawArticles to set
	 */
	public void setAbsoluteGroundLawArticles(
			List<AbsoluteGroundLawArticle> absoluteGroundLawArticles) {
		this.absoluteGroundLawArticles = absoluteGroundLawArticles;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
