package eu.ohim.sp.core.domain.opposition;




public class RevocationGroundLawArticle extends LawArticle {

	private static final long serialVersionUID = 1L;
	private Boolean nonUse;
	private int nonUsePeriod;
	/**
	 * @return the nonUse
	 */
	public Boolean getNonUse() {
		return nonUse;
	}
	/**
	 * @param nonUse the nonUse to set
	 */
	public void setNonUse(Boolean nonUse) {
		this.nonUse = nonUse;
	}
	/**
	 * @return the nonUsePeriod
	 */
	public int getNonUsePeriod() {
		return nonUsePeriod;
	}
	/**
	 * @param nonUsePeriod the nonUsePeriod to set
	 */
	public void setNonUsePeriod(int nonUsePeriod) {
		this.nonUsePeriod = nonUsePeriod;
	}
	
	
	
}
