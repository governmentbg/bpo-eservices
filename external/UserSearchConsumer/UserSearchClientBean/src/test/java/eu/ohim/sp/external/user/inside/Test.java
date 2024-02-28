package eu.ohim.sp.external.user.inside;

import eu.ohim.sp.external.user.inside.userSearch.bpo.LiferayRestClient;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.ExpandoResult;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.GetCompanyResult;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.GetUserResult;
import org.apache.log4j.BasicConfigurator;

import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        System.setProperty("liferay.remote.user.name", "test");
        System.setProperty("liferay.remote.user.password", "p0r7aLbp0bg");
        String url = "https://ohimcas/api/jsonws/company/get-company-by-virtual-host/virtual-host/portal.bpo.bg";
        GetCompanyResult res = new LiferayRestClient<>(GetCompanyResult.class, url).callService();
        GetUserResult usr = new LiferayRestClient<>(GetUserResult.class, "https://ohimcas/api/jsonws/user/get-user-by-screen-name/company-id/{company_id}/screen-name/{screen_name}", res.getCompanyId() + "", "test").callService();
        String expandourl = "https://ohimcas/api/jsonws/expandovalue/get-json-data/company-id/{company_id}/class-name/com.liferay.portal.model.User/table-name/CUSTOM_FIELDS/column-name/{column_name}/class-pk/{user_id}";
        ExpandoResult expando = new LiferayRestClient<>(ExpandoResult.class, expandourl, res.getCompanyId().toString(), "aid", usr.getUserId().toString()).callService();
        System.out.println(expando.getData());


        System.out.println(res.getCompanyId());
        System.out.println(usr.getFirstName());
        System.out.println(usr.getUserId());
//        System.out.println(res.getCompanyId());
    }
    private static String urlEncode(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
