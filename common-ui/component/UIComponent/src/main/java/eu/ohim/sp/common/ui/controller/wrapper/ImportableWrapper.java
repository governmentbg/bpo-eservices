package eu.ohim.sp.common.ui.controller.wrapper;

import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.interfaces.Importable;

/**
 * @author ionitdi
 */
public class ImportableWrapper {
	private Importable importable;

	private ImportParameters importParameters;

	/**
	 * @return
	 */
	public Importable getImportable() {
		return importable;
	}

	/**
	 * @param importable
	 */
	public void setImportable(Importable importable) {
		this.importable = importable;
	}

	/**
	 * @return
	 */
	public ImportParameters getImportParameters() {
		return importParameters;
	}

	/**
	 * @param importParameters
	 */
	public void setImportParameters(ImportParameters importParameters) {
		this.importParameters = importParameters;
	}
}
