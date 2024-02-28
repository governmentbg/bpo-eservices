package eu.ohim.sp.core.domain.design;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.List;

/**
 * The type Product indication.
 */
public class ProductIndication extends Id implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5677968908561088631L;

	private String description;

	private String version;

	private String languageCode;

	private ProductIndicationKind kind;
	
	private List<ProductIndicationClass> classes;

	private ValidationCode validationCode;

	/**
	 * Gets description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 *
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets version.
	 *
	 * @param version the version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets language code.
	 *
	 * @return the language code
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Sets language code.
	 *
	 * @param languageCode the language code
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Gets classes.
	 *
	 * @return the classes
	 */
	public List<ProductIndicationClass> getClasses() {
		return classes;
	}

	/**
	 * Sets classes.
	 *
	 * @param classes the classes
	 */
	public void setClasses(List<ProductIndicationClass> classes) {
		this.classes = classes;
	}


	/**
	 * Gets kind.
	 *
	 * @return the kind
	 */
	public ProductIndicationKind getKind() {
		return kind;
	}

	/**
	 * Sets kind.
	 *
	 * @param kind the kind
	 */
	public void setKind(ProductIndicationKind kind) {
		this.kind = kind;
	}

	public ValidationCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ValidationCode validationCode) {
		this.validationCode = validationCode;
	}
}
