package eu.ohim.sp.common.ui.form.design;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * POJO that contains information about a Locarno class.
 * TODO Revise with Locarno integration
 */
public class LocarnoClass implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -319646361024224057L;
	
	private String clazz;
	private String subclass;
	private List<String> indications;
	
	public LocarnoClass() {
		
	}
	
	public LocarnoClass(String clazz, String subclass) {
		this.clazz = clazz;
		this.subclass = subclass;
	}

	public LocarnoClass(String clazz, String subclass, List<String> indications) {
		this.clazz = clazz;
		this.subclass = subclass;
		this.indications = indications;
	}
	
	/**
	 * Method that returns the Locarno class.
	 *
	 * @return the Locarno class.
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * Method that sets the Locarno class.
	 *
	 * @param clazz the Locarno class to set.
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * Method that returns the Locarno subclass.
	 *
	 * @return the Locarno subclass.
	 */
	public String getSubclass() {
		return subclass;
	}

	/**
	 * Method that sets the Locarno subclass.
	 *
	 * @param subclass the Locarno subclass to set.
	 */
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public List<String> getIndications() {
		return indications;
	}

	public void setIndications(List<String> indications) {
		this.indications = indications;
	}

	/**
	 * Returns the class and subclasses formatted with pattern CC.SS
	 * @return class and subclasses formatted with pattern CC.SS
	 */
	public String getClassFormatted() {
		String classFormated = "";
		if (StringUtils.isNotEmpty(clazz)) {
			classFormated += clazz;
			if (StringUtils.isNotEmpty(subclass)) {
				classFormated += "-" + subclass;
			}
		}
		return classFormated;
	}
	
	/**
    * (non-Javadoc)
    *
    * @see java.lang.Object#clone()
    */
	@Override
	public LocarnoClass clone() {
		LocarnoClass locarnoClass = new LocarnoClass(clazz, subclass, indications);
		return locarnoClass;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((subclass == null) ? 0 : subclass.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocarnoClass other = (LocarnoClass) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (subclass == null) {
			if (other.subclass != null)
				return false;
		} else if (!subclass.equals(other.subclass))
			return false;
		return true;
	}
   
}
