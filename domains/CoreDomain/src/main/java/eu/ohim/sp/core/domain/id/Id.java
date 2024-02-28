package eu.ohim.sp.core.domain.id;

import java.io.Serializable;

/**
 * User: jaraful
 * Date: 11/07/13
 * Time: 15:18
 * TO be checked if it is better as string
 */
public class Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
