package eu.ohim.sp.core.domain.claim;

import java.util.List;

import eu.ohim.sp.core.domain.trademark.ClassDescription;

/**
 * The Class PartialClaim.
 */
public class PartialClaim extends Claim {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7086358695234298504L;

	/** The partial indicator. */
	private Boolean partialIndicator;

	/** The partial goods services. */
	private List<ClassDescription> partialGoodsServices;

	/** The partial goods services comment. */
	private String partialGoodsServicesComment;

	/**
	 * Gets the partial indicator.
	 * 
	 * @return the partial indicator
	 */
	public Boolean getPartialIndicator() {
		return partialIndicator;
	}

	/**
	 * Sets the partial indicator.
	 * 
	 * @param partialIndicator
	 *            the new partial indicator
	 */
	public void setPartialIndicator(Boolean partialIndicator) {
		this.partialIndicator = partialIndicator;
	}

	/**
	 * Gets the partial goods services.
	 * 
	 * @return the partial goods services
	 */
	public List<ClassDescription> getPartialGoodsServices() {
		return partialGoodsServices;
	}

	/**
	 * Sets the partial goods services.
	 * 
	 * @param partialGoodsServices
	 *            the new partial goods services
	 */
	public void setPartialGoodsServices(
			List<ClassDescription> partialGoodsServices) {
		this.partialGoodsServices = partialGoodsServices;
	}

	/**
	 * Gets the partial goods services comment.
	 * 
	 * @return the partial goods services comment
	 */
	public String getPartialGoodsServicesComment() {
		return partialGoodsServicesComment;
	}

	/**
	 * Sets the partial goods services comment.
	 * 
	 * @param partialGoodsServicesComment
	 *            the new partial goods services comment
	 */
	public void setPartialGoodsServicesComment(
			String partialGoodsServicesComment) {
		this.partialGoodsServicesComment = partialGoodsServicesComment;
	}

}
