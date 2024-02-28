package eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects;

import javax.xml.bind.annotation.XmlRootElement;

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class GetCompanyResult extends BaseResult {
//    @JsonProperty
    private Integer companyId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
