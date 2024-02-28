package eu.ohim.sp.external.user.inside.userSearch_mock.builders;

import eu.ohim.sp.external.domain.contact.Address;
import eu.ohim.sp.external.domain.contact.Email;
import eu.ohim.sp.external.domain.person.*;
import eu.ohim.sp.external.domain.user.FOUser;
import eu.ohim.sp.external.domain.user.User;
import eu.ohim.sp.external.domain.user.UserPersonDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcoantonioalberoalbero on 5/4/17.
 */
public class Builder {
    private final String USERNAME = "carlos";

    public User build() {
        FOUser out= new FOUser();

        UserPersonDetails userPersonDetails= new UserPersonDetails();
        List<Applicant> applicants = new ArrayList<Applicant>();
        List<Representative> representatives = new ArrayList<Representative>();
        List<Designer> designers =  new ArrayList<Designer>();


        Applicant applicant = buildMockApplicant("AP1");
        Representative representative = buildMockRepresentative("RP1");
        Designer designer = buildMockDesigner("DS1");

        applicants.add(applicant);
        representatives.add(representative);
        designers.add(designer);

        userPersonDetails.setApplicants(applicants);
        userPersonDetails.setRepresentatives(representatives);
        userPersonDetails.setDesigners(designers);


        out.setUserPersonDetails(userPersonDetails);
        out.setUserName(USERNAME);
        out.setFullName(USERNAME);
        return out;
    }

    private Applicant buildMockApplicant(String suffix) {
        Applicant out= new Applicant();
        out.setPersonNumber("mockId1");
        out.setKind(PersonKind.NATURAL_PERSON);
        out.setName(buildMockPersonName(suffix));
        out.setNationality("ES");
        List<Email> emails = new ArrayList<Email>();
        emails.add(new Email("dsd", "mock@somewhere.com"));
        out.setEmails(emails);
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address("sd", "dsd", "StreetMock", "2", "London", "45000", "GB.EX", "GB", null));
        out.setAddresses(addresses);
        return out;
    }

    private Representative buildMockRepresentative(String suffix) {
        Representative out= new Representative();
        out.setPersonNumber("mockId2");
        out.setKind(PersonKind.LEGAL_ENTITY);
        out.setLegalForm(PersonKind.LEGAL_ENTITY.value());
        out.setRepresentativeKind(RepresentativeKind.PROFESSIONAL_REPRESENTATIVE);
        out.setName(buildMockPersonName(suffix));
        out.setNationality("ES");
        List<Email> emails = new ArrayList<Email>();
        emails.add(new Email("dsd", "mock2@somewhere.com"));
        out.setEmails(emails);
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address("sd", "dsd", "StreetMock2", "2", "London", "45000", "GB.EX", "GB", null));
        out.setAddresses(addresses);
        return out;
    }

    private Designer buildMockDesigner(String suffix) {
        Designer out= new Designer();
        out.setPersonNumber("mockId3");
        out.setName(buildMockPersonName(suffix));
        out.setNationality("ES");
        List<Email> emails = new ArrayList<Email>();
        emails.add(new Email("dsd", "mock2@somewhere.com"));
        out.setEmails(emails);
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address("sd", "dsd", "StreetMock2", "2", "London", "45000", "GB.EX", "GB", null));
        out.setAddresses(addresses);
        return out;
    }

    private PersonName buildMockPersonName(String suffix) {
        PersonName out= new PersonName();

        out.setFirstName("mockFirstName"+suffix);
        out.setMiddleName("mockMiddleName"+suffix);
        out.setLastName("mockLastname"+suffix);
        out.setOrganizationName("mockOrgname"+suffix);
        out.setSecondLastName("mocksecondlastname"+suffix);

        return out;
    }
}
