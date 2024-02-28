package bg.duosoft.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: ggeorgiev
 * Date: 19.12.2022
 * Time: 13:07
 */
@Entity
public class CountEntity {
    @Id
    @Column(name = "base_efiling_type")
    private String baseEfilingType;
    @Column(name = "applications_count")
    private long applicationsCount;

    public String getBaseEfilingType() {
        return baseEfilingType;
    }

    public void setBaseEfilingType(String baseEfilingType) {
        this.baseEfilingType = baseEfilingType;
    }

    public long getApplicationsCount() {
        return applicationsCount;
    }

    public void setApplicationsCount(long applicationsCount) {
        this.applicationsCount = applicationsCount;
    }
}
