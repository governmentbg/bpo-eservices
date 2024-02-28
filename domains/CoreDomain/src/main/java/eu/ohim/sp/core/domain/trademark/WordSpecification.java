package eu.ohim.sp.core.domain.trademark;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

public class WordSpecification extends Id implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4212682466677707601L;

	/** The character set. */
	private String characterSet;
	/** The transcription details. */
	private String transcriptionDetails;
	/** The transliteration details. */
	private String transliterationDetails;
	/** The word elements. */
	private String wordElements;

    private short seriesIdentifier;

    public String getCharacterSet() {
		return characterSet;
	}
	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}
	public String getTranscriptionDetails() {
		return transcriptionDetails;
	}
	public void setTranscriptionDetails(String transcriptionDetails) {
		this.transcriptionDetails = transcriptionDetails;
	}
	public String getTransliterationDetails() {
		return transliterationDetails;
	}
	public void setTransliterationDetails(String transliterationDetails) {
		this.transliterationDetails = transliterationDetails;
	}
	public String getWordElements() {
		return wordElements;
	}
	public void setWordElements(String wordElements) {
		this.wordElements = wordElements;
	}
    public short getSeriesIdentifier() {
        return seriesIdentifier;
    }
    public void setSeriesIdentifier(short seriesIdentifier) {
        this.seriesIdentifier = seriesIdentifier;
    }
}
