package eu.ohim.sp.core.domain.userdoc;
import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 26.04.2022
 * Time: 14:09
 */
public class Userdoc extends Id implements Serializable {
    private String userdocType;
    private String userdocTypeName;
    private String externalSystemId;
    private Date filingDate;
    private String docOrigin;
    private String docLog;
    private Integer docSeries;
    private Integer docNbr;
    private Integer docSeqNbr;
    private Integer docSeqSeries;
    private Integer parentProcessNbr;
    private String parentProcessType;
    private Integer processNbr;
    private String processType;

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

    public Integer getDocSeqNbr() {
        return docSeqNbr;
    }

    public void setDocSeqNbr(Integer docSeqNbr) {
        this.docSeqNbr = docSeqNbr;
    }

    public Integer getDocSeqSeries() {
        return docSeqSeries;
    }

    public void setDocSeqSeries(Integer docSeqSeries) {
        this.docSeqSeries = docSeqSeries;
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
}
