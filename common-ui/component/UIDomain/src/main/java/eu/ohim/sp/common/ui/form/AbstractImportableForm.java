/*******************************************************************************
 * * $Id:: AbstractImportableForm.java 50771 2012-11-14 15:10:27Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form;

import eu.ohim.sp.common.ui.interfaces.Importable;

/**
 * @author ionitdi
 */
public abstract class AbstractImportableForm extends AbstractForm implements Importable {

	private boolean imported;
	private boolean checkbdblocking;
	private boolean validateImported;
	
	/**
	 * @return the imported
	 */
	@Override
	public boolean getImported() {
		return imported;
	}

	/**
	 * @param imported the imported to set
	 */
	@Override
	public void setImported(boolean imported) {
		this.imported = imported;
	}

	/**
	 * @return if form blocking messages must be checked from bd param config
	 */
	public boolean getCheckBdBlocking() {
		return checkbdblocking;
	}

	/**
	 * @param checkbdblocking
	 */
	public void setCheckBdBlocking(boolean checkbdblocking) {
		this.checkbdblocking = checkbdblocking;
	}
	
	/**
	 * @return the validateImported
	 */
	public boolean getValidateImported() {
		return validateImported;
	}

	/**
	 * @param validateImported the validateImported to set
	 */
	public void setValidateImported(boolean validateImported) {
		this.validateImported = validateImported;
	}
}
