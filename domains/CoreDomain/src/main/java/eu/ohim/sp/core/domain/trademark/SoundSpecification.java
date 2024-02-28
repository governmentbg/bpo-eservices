package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.Document;

/**
 * The Class SoundSpecification.
 */
public class SoundSpecification extends Id implements Serializable {

	private static final long serialVersionUID = -8155313791881832249L;

	/** The common document. */
	private Document document;
	
	/** The series identifier. */
	private short seriesIdentifier;

	/**
	 * Gets the common document.
	 *
	 * @return the common document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Sets the common document.
	 *
	 * @param document the new common document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * Gets the series identifier.
	 *
	 * @return the series identifier
	 */
	public short getSeriesIdentifier() {
		return seriesIdentifier;
	}

	/**
	 * Sets the series identifier.
	 *
	 * @param seriesIdentifier the new series identifier
	 */
	public void setSeriesIdentifier(short seriesIdentifier) {
		this.seriesIdentifier = seriesIdentifier;
	}

}
