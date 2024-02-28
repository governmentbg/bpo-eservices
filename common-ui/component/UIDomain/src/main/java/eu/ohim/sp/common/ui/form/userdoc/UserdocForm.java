package eu.ohim.sp.common.ui.form.userdoc;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 26.04.2022
 * Time: 14:20
 */
public class UserdocForm extends AbstractImportableForm {

    private String userdocType;
    private String userdocTypeName;
    private String externalSystemId;
    private Date filingDate;
    private String docOrigin;
    private String docLog;
    private Integer docSeries;
    private Integer docNbr;
    private Integer docSeqSeries;
    private Integer docSeqNbr;
    private Integer parentProcessNbr;
    private String parentProcessType;
    private Integer processNbr;
    private String processType;

    private List<UserdocForm> children;

    public UserdocForm() {
        this.children = new ArrayList<>();
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        UserdocForm form = new UserdocForm();
        form.setId(this.getId());
        form.setImported(this.getImported());
        form.setUserdocType(this.userdocType);
        form.setUserdocTypeName(this.userdocTypeName);
        form.setExternalSystemId(this.externalSystemId);
        form.setFilingDate(this.filingDate);
        form.setDocLog(this.docLog);
        form.setDocOrigin(this.docOrigin);
        form.setDocSeries(this.docSeries);
        form.setDocNbr(this.docNbr);
        form.setDocSeqSeries(this.docSeqSeries);
        form.setDocSeqNbr(this.docSeqNbr);
        form.setParentProcessNbr(this.parentProcessNbr);
        form.setParentProcessType(this.parentProcessType);
        form.setProcessNbr(this.processNbr);
        form.setProcessType(this.processType);
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.USERDOC;
    }

    public String getUserdocType() {
        return userdocType;
    }

    public void setUserdocType(String userdocType) {
        this.userdocType = userdocType;
    }

    public String getUserdocTypeName() {
        return userdocTypeName;
    }

    public void setUserdocTypeName(String userdocTypeName) {
        this.userdocTypeName = userdocTypeName;
    }

    public String getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(String externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public String getDocOrigin() {
        return docOrigin;
    }

    public void setDocOrigin(String docOrigin) {
        this.docOrigin = docOrigin;
    }

    public String getDocLog() {
        return docLog;
    }

    public void setDocLog(String docLog) {
        this.docLog = docLog;
    }

    public Integer getDocSeries() {
        return docSeries;
    }

    public void setDocSeries(Integer docSeries) {
        this.docSeries = docSeries;
    }

    public Integer getDocNbr() {
        return docNbr;
    }

    public void setDocNbr(Integer docNbr) {
        this.docNbr = docNbr;
    }

    public Integer getDocSeqSeries() {
        return docSeqSeries;
    }

    public void setDocSeqSeries(Integer docSeqSeries) {
        this.docSeqSeries = docSeqSeries;
    }

    public Integer getDocSeqNbr() {
        return docSeqNbr;
    }

    public void setDocSeqNbr(Integer docSeqNbr) {
        this.docSeqNbr = docSeqNbr;
    }

    public Integer getParentProcessNbr() {
        return parentProcessNbr;
    }

    public void setParentProcessNbr(Integer parentProcessNbr) {
        this.parentProcessNbr = parentProcessNbr;
    }

    public String getParentProcessType() {
        return parentProcessType;
    }

    public void setParentProcessType(String parentProcessType) {
        this.parentProcessType = parentProcessType;
    }

    public Integer getProcessNbr() {
        return processNbr;
    }

    public void setProcessNbr(Integer processNbr) {
        this.processNbr = processNbr;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public List<UserdocForm> getChildren() {
        return children;
    }

    public void setChildren(List<UserdocForm> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserdocForm that = (UserdocForm) o;
        return docOrigin.equals(that.docOrigin) && docLog.equals(that.docLog) && docSeries.equals(that.docSeries) && docNbr.equals(that.docNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), docOrigin, docLog, docSeries, docNbr);
    }
}
