package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;



public class EarlierRightRepresentation implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	protected String representation;
	protected FileWrapper attachment;
	
	/**
	 * Method that returns the representation
	 *
	 * @return the representation
	 */
	public String getRepresentation() {
		return representation;
	}
	/**
     * Method that sets the representation
     *
     * @param representation the representation to set
     */
	public void setRepresentation(String representation) {
		this.representation = representation;
	}
	/**
	 * Method that returns the attachment
	 *
	 * @return the attachment
	 */
	public FileWrapper getAttachment() {
		return attachment;
	}
	/**
     * Method that sets the attachment
     *
     * @param attachment the attachment to set
     */
	public void setAttachment(FileWrapper attachment) {
		this.attachment = attachment;
	}
	
	
	 /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   EarlierRightRepresentation earlierRightRepresentationForm = new EarlierRightRepresentation();
	   earlierRightRepresentationForm.setRepresentation(representation);
	   earlierRightRepresentationForm.setAttachment(attachment);
       return earlierRightRepresentationForm;
   }
	
  public boolean isEmpty()
   {
       return (StringUtils.isBlank(representation) && (attachment==null) || (attachment.getStoredFiles()==null));
   }
 //TODO preguntar si debe tener el FileWrapper el método isEmpty.
   
   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof EarlierRightRepresentation))
       {
           return false;
       }

       EarlierRightRepresentation that = (EarlierRightRepresentation) o;

       if (representation != null ? !representation.equals(that.representation) : that.representation != null)
       {
           return false;
       }
       if (attachment != null ? !attachment.equals(that.attachment) : that.attachment != null)
       {
           return false;
       }
       return true;
   }

   @Override
   public int hashCode()
   {
       int result = representation != null ? representation.hashCode() : 0;
       result = value31 * result + (attachment != null ? attachment.hashCode() : 0);
       return result;
   }
		
	
	
}
