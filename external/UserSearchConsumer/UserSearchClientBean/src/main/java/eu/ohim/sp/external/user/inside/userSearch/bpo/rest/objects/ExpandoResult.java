package eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExpandoResult extends BaseResult {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
