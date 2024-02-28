package eu.ohim.sp.core.domain.dossier.letters;

import java.io.Serializable;

import eu.ohim.sp.core.domain.dossier.DossierKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;

/**
 * Available search criteria for TMBO Letter Templates searching
 * 
 * @author masjose
 * 
 */
public class LetterTemplateSearchCriteria implements Serializable {

	private static final long serialVersionUID = 8357843829238525418L;

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
}
