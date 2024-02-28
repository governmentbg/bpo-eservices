package eu.ohim.sp.core.domain.dossier.userpref;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;

/**
 * User preferences Inbox configuration.
 * 
 */
public class InboxConfig extends Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private int bouserId;
	private String inboxtype;
	private String txtname;
	private int maxNumber;
	private String query;
	private boolean fixed;

	private List<InboxConfigColumn> columns;

	/**
	 * @return the bouser
	 */
	public int getBouser() {
		return bouserId;
	}

	/**
	 * @param bouser
	 *            the bouser to set
	 */
	public void setBouser(int bouser) {
		this.bouserId = bouser;
	}

	/**
	 * @return the inboxtype
	 */
	public String getInboxtype() {
		return inboxtype;
	}

	/**
	 * @param inboxtype
	 *            the inboxtype to set
	 */
	public void setInboxtype(String inboxtype) {
		this.inboxtype = inboxtype;
	}

	/**
	 * @return the txtname
	 */
	public String getTxtname() {
		return txtname;
	}

	/**
	 * @param txtname
	 *            the txtname to set
	 */
	public void setTxtname(String txtname) {
		this.txtname = txtname;
	}

	/**
	 * @return the maxNumber
	 */
	public int getMaxNumber() {
		return maxNumber;
	}

	/**
	 * @param maxNumber
	 *            the maxNumber to set
	 */
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the fixed
	 */
	public boolean isFixed() {
		return fixed;
	}

	/**
	 * @param fixed
	 *            the fixed to set
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	/**
	 * @return the columns
	 */
	public List<InboxConfigColumn> getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(List<InboxConfigColumn> columns) {
		this.columns = columns;
	}

}
