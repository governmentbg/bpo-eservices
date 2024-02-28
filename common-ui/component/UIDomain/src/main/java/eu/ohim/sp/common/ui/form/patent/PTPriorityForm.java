package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

/**
 * Created by Raya
 * Copied from Priority
 * 09.04.2019
 */
public class PTPriorityForm extends AbstractImportableForm {

    private Date date;
    private String number;
    private String country;
    private Boolean warning;

    private FileWrapper fileWrapperCopy;
    private FileWrapper fileWrapperTranslation;
    private FileWrapper filePriorityCertificate;

    /**
     * Method that returns the date
     *
     * @return the date
     */
    public Date getDate() {
        if (date != null) {
            return (Date) date.clone();
        } else {
            return null;
        }
    }

    /**
     * Method that sets the date
     *
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
        if (date != null) {
            this.date = (Date) date.clone();
        } else {
            this.date = null;
        }
    }

    /**
     * Method that returns the number
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Method that sets the number
     *
     * @param number
     *            the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Method that returns the country
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method that sets the country
     *
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method that returns the warning
     *
     * @return the warning
     */
    public Boolean getWarning() {
        return warning;
    }

    /**
     * Method that sets the warning
     *
     * @param warning
     *            the warning to set
     */
    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

    public FileWrapper getFileWrapperCopy() {
        if (fileWrapperCopy == null) {
            fileWrapperCopy = new FileWrapper();
        }
        return fileWrapperCopy;
    }

    public FileWrapper getFileWrapperTranslation() {
        if (fileWrapperTranslation == null) {
            fileWrapperTranslation = new FileWrapper();
        }
        return fileWrapperTranslation;
    }

    public void setFileWrapperCopy(FileWrapper fileWrapperCopy) {
        this.fileWrapperCopy = fileWrapperCopy;
    }

    public void setFileWrapperTranslation(FileWrapper fileWrapperTranslation) {
        this.fileWrapperTranslation = fileWrapperTranslation;
    }

    public FileWrapper getFilePriorityCertificate() {
        if (filePriorityCertificate == null) {
            setFilePriorityCertificate(new FileWrapper());
        }
        return filePriorityCertificate;
    }

    public void setFilePriorityCertificate(FileWrapper filePriorityCertificate) {
        this.filePriorityCertificate = filePriorityCertificate;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public PTPriorityForm clone() throws CloneNotSupportedException {
        PTPriorityForm priorityForm = new PTPriorityForm();
        priorityForm.setCountry(country);
        priorityForm.setDate(date);
        priorityForm.setId(id);
        priorityForm.setNumber(number);
        priorityForm.setImported(getImported());
        priorityForm.setFileWrapperCopy(fileWrapperCopy);
        priorityForm.setFileWrapperTranslation(fileWrapperTranslation);
        priorityForm.setFilePriorityCertificate(filePriorityCertificate);

        return priorityForm;
    }

    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PRIORITY;
    }

}
