package eu.ohim.sp.core.domain.dossier;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.dossier.annotations.Annotation;
import eu.ohim.sp.core.domain.dossier.letters.Letter;
import eu.ohim.sp.core.domain.dossier.publications.Publication;
import eu.ohim.sp.core.domain.dossier.tasks.Task;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.payment.Payment;
import eu.ohim.sp.core.domain.payment.PaymentFee;

/**
 * The Class Dossier. It represents a Dossier definition stored in the TM
 * BackOffice system. Depending of the Dossier kind it will exist a subclass of
 * this one with additional properties related to the kind of Dossier. For
 * example, TradeMarkDossier
 */
public abstract class Dossier extends Id implements Serializable {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -6921889525510764948L;

	/** The kind of dossier **/
	private DossierKind kind;

	/** The annotations. */
	private List<Annotation> annotations;

	/** The fees. */
	private List<PaymentFee> fees;

	/** The payments. */
	private List<Payment> payments;

	/** The letters. */
	private List<Letter> letters;

	/** The tasks. */
	private List<Task> tasks;

	/** The publications **/
	private List<Publication> publications;

	/**
	 * @return the kind
	 */
	public DossierKind getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(DossierKind kind) {
		this.kind = kind;
	}

	/**
	 * Gets the annotations.
	 * 
	 * @return the annotations
	 */
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * Sets the annotations.
	 * 
	 * @param annotations
	 *            the new annotations
	 */
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	/**
	 * Gets the fees.
	 * 
	 * @return the fees
	 */
	public List<PaymentFee> getFees() {
		return fees;
	}

	/**
	 * Sets the fees.
	 * 
	 * @param fees
	 *            the new fees
	 */
	public void setFees(List<PaymentFee> fees) {
		this.fees = fees;
	}

	/**
	 * Gets the payments.
	 * 
	 * @return the payments
	 */
	public List<Payment> getPayments() {
		return payments;
	}

	/**
	 * Sets the payments.
	 * 
	 * @param payments
	 *            the new payments
	 */
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * Gets the letters.
	 * 
	 * @return the letters
	 */
	public List<Letter> getLetters() {
		return letters;
	}

	/**
	 * Sets the letters.
	 * 
	 * @param letters
	 *            the new letters
	 */
	public void setLetters(List<Letter> letters) {
		this.letters = letters;
	}

	/**
	 * Gets the tasks.
	 * 
	 * @return the tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * Sets the tasks.
	 * 
	 * @param tasks
	 *            the new tasks
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the publications
	 */
	public List<Publication> getPublications() {
		return publications;
	}

	/**
	 * @param publications
	 *            the publications to set
	 */
	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

}