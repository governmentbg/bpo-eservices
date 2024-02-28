package eu.ohim.sp.core.domain.dossier.userpref;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

/**
 * User preferences Inbox column configuration.
 * 
 */
public class InboxConfigColumn extends Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private int inboxConfigId;
	private String txtname;
	private String defaultInbox;
	private boolean defaultColumn;
	private int nrorder;
	private boolean selected;
	private boolean ordered;

	/**
	 * @return the inboxConfigId
	 */
	public int getInboxConfigId() {
		return inboxConfigId;
	}

	/**
	 * @param inboxConfigId
	 *            the inboxConfigId to set
	 */
	public void setInboxConfigId(int inboxConfigId) {
		this.inboxConfigId = inboxConfigId;
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
	 * @return the defaultInbox
	 */
	public String getDefaultInbox() {
		return defaultInbox;
	}

	/**
	 * @param defaultInbox
	 *            the defaultInbox to set
	 */
	public void setDefaultInbox(String defaultInbox) {
		this.defaultInbox = defaultInbox;
	}

	/**
	 * @return the defaultColumn
	 */
	public boolean getDefaultColumn() {
		return defaultColumn;
	}

	/**
	 * @param defaultColumn
	 *            the defaultColumn to set
	 */
	public void setDefaultColumn(boolean defaultColumn) {
		this.defaultColumn = defaultColumn;
	}

	/**
	 * @return the nrorder
	 */
	public int getNrorder() {
		return nrorder;
	}

	/**
	 * @param nrorder
	 *            the nrorder to set
	 */
	public void setNrorder(int nrorder) {
		this.nrorder = nrorder;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the ordered
	 */
	public boolean isOrdered() {
		return ordered;
	}

	/**
	 * @param ordered
	 *            the ordered to set
	 */
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	public InboxConfigColumn() {
	}

}
