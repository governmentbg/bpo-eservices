package eu.ohim.sp.core.domain.classification.wrapper;
import java.io.Serializable;

/**
 * User: jaraful
 * Date: 27/02/14
 * Time: 15:42
 */
public class TermId implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
