package bg.duosoft.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("properties")
public class PropertyAccess {

    @Value("${trademarks.url}")
    private String trademarksUrl;

    @Value("${designs.url}")
    private String designsUrl;

    @Value("${eservices.url}")
    private String eservicesUrl;

    @Value("${patents.url}")
    private String patentsUrl;

    @Value("${spc.url}")
    private String spcUrl;

    @Value("${sort.breeds.url}")
    private String sortBreedsUrl;

    @Value("${utility.models.url}")
    private String utilityModelsUrl;

    @Value("${eu.patents.url}")
    private String euPatentsUrl;

    @Value("${geo.indications.url}")
    private String geoIndicationsUrl;

    @Value("${integral.schemas.url}")
    private String integralSchemasUrl;

    @Value("${recently.signed.url}")
    private String recentlySignedUrl;

    public String getTrademarksUrl() {
        return trademarksUrl;
    }

    public void setTrademarksUrl(String trademarksUrl) {
        this.trademarksUrl = trademarksUrl;
    }

    public String getDesignsUrl() {
        return designsUrl;
    }

    public void setDesignsUrl(String designsUrl) {
        this.designsUrl = designsUrl;
    }

    public String getEservicesUrl() {
        return eservicesUrl;
    }

    public void setEservicesUrl(String eservicesUrl) {
        this.eservicesUrl = eservicesUrl;
    }

    public String getPatentsUrl() {
        return patentsUrl;
    }

    public void setPatentsUrl(String patentsUrl) {
        this.patentsUrl = patentsUrl;
    }

    public String getUtilityModelsUrl() {
        return utilityModelsUrl;
    }

    public void setUtilityModelsUrl(String utilityModelsUrl) {
        this.utilityModelsUrl = utilityModelsUrl;
    }

    public String getRecentlySignedUrl() {
        return recentlySignedUrl;
    }

    public void setRecentlySignedUrl(String recentlySignedUrl) {
        this.recentlySignedUrl = recentlySignedUrl;
    }

    public String getEuPatentsUrl() {
        return euPatentsUrl;
    }

    public void setEuPatentsUrl(String euPatentsUrl) {
        this.euPatentsUrl = euPatentsUrl;
    }

    public String getSpcUrl() {
        return spcUrl;
    }

    public void setSpcUrl(String spcUrl) {
        this.spcUrl = spcUrl;
    }

    public String getSortBreedsUrl() {
        return sortBreedsUrl;
    }

    public void setSortBreedsUrl(String sortBreedsUrl) {
        this.sortBreedsUrl = sortBreedsUrl;
    }

    public String getGeoIndicationsUrl() {
        return geoIndicationsUrl;
    }

    public void setGeoIndicationsUrl(String geoIndicationsUrl) {
        this.geoIndicationsUrl = geoIndicationsUrl;
    }

    public String getIntegralSchemasUrl() {
        return integralSchemasUrl;
    }

    public void setIntegralSchemasUrl(String integralSchemasUrl) {
        this.integralSchemasUrl = integralSchemasUrl;
    }
}
