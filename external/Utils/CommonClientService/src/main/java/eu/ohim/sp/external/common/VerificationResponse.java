//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.external.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * #DS Class Integration changes.
 * The Class VerificationResponse.
 * @author Ramittal
 *
 */
public class VerificationResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4794241346656847125L;

	/** The verification result. */
	@JsonProperty("VerificationResult")
	private VerificationResult verificationResult;

	/** The message. */
	@JsonProperty("Message")
	private String message;

	/** The matching results. */
	@JsonProperty("MatchingResults")
	private List<MatchingResult> matchingResults;

	/**
	 * Gets the verification result.
	 *
	 * @return the verificationResult
	 */
	public VerificationResult getVerificationResult() {
		return verificationResult;
	}

	/**
	 * Sets the verification result.
	 *
	 * @param verificationResult the verificationResult to set
	 */
	public void setVerificationResult(VerificationResult verificationResult) {
		this.verificationResult = verificationResult;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the matching results.
	 *
	 * @return the matchingResults
	 */
	public List<MatchingResult> getMatchingResults() {
		return matchingResults;
	}

	/**
	 * Sets the matching results.
	 *
	 * @param matchingResults the matchingResults to set
	 */
	public void setMatchingResults(List<MatchingResult> matchingResults) {
		this.matchingResults = matchingResults;
	}

    @Override
    public String toString() {
        return "VerificationResponse{" +
                "verificationResult=" + verificationResult +
                ", message='" + message + '\'' +
                ", matchingResults=" + matchingResults +
                '}';
    }
}
