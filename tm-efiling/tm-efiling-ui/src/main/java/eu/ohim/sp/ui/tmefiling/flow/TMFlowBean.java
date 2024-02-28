package eu.ohim.sp.ui.tmefiling.flow;

import eu.ohim.sp.common.ui.flow.section.TrademarkFlowBean;
import eu.ohim.sp.common.ui.form.trademark.ForeignRegistrationForm;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.ui.tmefiling.form.MainForm;

import java.util.List;

/**
 * @author karalch
 */
public interface TMFlowBean extends TrademarkFlowBean {
    /**
     * Returns the main form that is stored in the session
     *
     * @return the mainForm
     */
    MainForm getMainForm();

    Boolean getUseReference();

    /**
     * Method that sets the useReference
     *
     * @param useReference
     *            the reference to set
     */
    void setUseReference(Boolean useReference);

    /**
     * Method that returns the reference
     *
     * @return the reference
     */
    String getReference();

    /**
     * Method that sets the reference
     *
     * @param reference
     *            the reference to set
     */
    void setReference(String reference);


    String getComment();

    /**
     * @param comment the comment to set
     */
    void setComment(String comment);

    Boolean getTrueDocumentsIndicator();

    void setTrueDocumentsIndicator(Boolean trueDocumentsIndicator);

    void clearMarkViews();

    void clearForeignRegistrations();

    List<MarkViewForm> getMarkViews();

    void setMarkViews(List<MarkViewForm> markViews);

    Boolean getCertificateRequestedIndicator();

    void setCertificateRequestedIndicator(Boolean certificateRequestedIndicator);

    List<ForeignRegistrationForm> getForeignRegistrations();

    void setForeignRegistrations(List<ForeignRegistrationForm> foreignRegistrations);

}
