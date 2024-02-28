package eu.ohim.sp.core.domain.dossier.letters;

import java.io.Serializable;

import eu.ohim.sp.core.domain.dossier.DossierKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.domain.id.Id;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Letter HTML Template for letter body generation
 * 
 * @author masjose
 * 
 */
public class LetterTemplate extends Id implements Serializable {

	private static final long serialVersionUID = 8705846919445733324L;

	private DossierKind typeDossier;
	private String nameShort;
	private LetterTypeSendMethod typeMethod;
	private String pathTemplate;

	public DossierKind getTypeDossier() {
		return typeDossier;
	}

	public void setTypeDossier(DossierKind typeDossier) {
		this.typeDossier = typeDossier;
	}

	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	public LetterTypeSendMethod getTypeMethod() {
		return typeMethod;
	}

	public void setTypeMethod(LetterTypeSendMethod typeMethod) {
		this.typeMethod = typeMethod;
	}

	public String getPathTemplate() {
		return pathTemplate;
	}

	public void setPathTemplate(String pathTemplate) {
		this.pathTemplate = pathTemplate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return super.equals(obj);
		}

		LetterTemplate actual = (LetterTemplate) obj;

		return EqualsBuilder.reflectionEquals(this, actual);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
