package eu.ohim.sp.external.user.inside.userSearch.bpo;

import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.external.domain.user.User;
import eu.ohim.sp.external.domain.user.UserGroup;
import eu.ohim.sp.external.domain.user.UserRole;
import eu.ohim.sp.external.services.user.GetUser;
import eu.ohim.sp.external.services.user.GetUserResponse;
import eu.ohim.sp.external.services.user.SearchUser;
import eu.ohim.sp.external.services.user.SearchUserResponse;
import eu.ohim.sp.external.user.inside.userSearch_mock.builders.PersonBuilderFactory;
import org.apache.commons.lang.NotImplementedException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;

public class BpoUserSearchService {

    public SearchUserResponse searchUser(SearchUser parameters) {
        throw new NotImplementedException();
    }

    public GetUserResponse getUser(GetUser parameters) {
        GetUserResponse response = new GetUserResponse();
        User user = PersonSearcherFactory.getInstance().getSearcher(parameters.getModule()).search(parameters.getModule(), parameters.getOffice(), parameters.getUsername());

        response.setUser(new JAXBElement(new QName("http://ohim.eu/sp/services/user-search/v3", "foUser"), User.class, user));

        return response;
    }

    public List<UserRole> getAllRoles(String module, String office) {
        throw new NotImplementedException();
    }

    public List<UserGroup> getAllGroups(String module, String office) {
        throw new NotImplementedException();
    }
}

/**
 * Created by marcoantonioalberoalbero on 14/10/16.
 */
/*public class BpoUserSearchService {



    public SearchUserResponse searchUser(SearchUser parameters) {
        throw new NotImplementedException();
    }

    public GetUserResponse getUser(GetUser parameters) {
        GetUserResponse response = new GetUserResponse();
        User user = PersonSearcherFactory.getInstance().getSearcher(parameters.getModule()).search(parameters.getModule(), parameters.getOffice(), parameters.getUsername());

        response.setUser(new JAXBElement(new QName("http://ohim.eu/sp/services/user-search/v3", "foUser"), User.class, user));

        return response;
    }

    public List<UserRole> getAllRoles(String module, String office) {
        throw new NotImplementedException();
    }

    public List<UserGroup> getAllGroups(String module, String office) {
        throw new NotImplementedException();
    }

}*/
