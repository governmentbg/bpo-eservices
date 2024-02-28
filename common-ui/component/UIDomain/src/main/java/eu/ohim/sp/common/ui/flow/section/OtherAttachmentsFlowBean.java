package eu.ohim.sp.common.ui.flow.section;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

public interface OtherAttachmentsFlowBean extends FlowBean{
	 /**
     * Gets other attachments
     * 
     * @return FileWrapper other attachments
     */
    FileWrapper getOtherAttachments();

    /**
     * Sets other attachments
     * 
     * @param otherAttachments
     */
    void setOtherAttachments(FileWrapper otherAttachments);

}
