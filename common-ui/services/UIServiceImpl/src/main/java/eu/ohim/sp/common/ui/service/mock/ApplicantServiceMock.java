package eu.ohim.sp.common.ui.service.mock;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;

@Deprecated
public class ApplicantServiceMock {
	
	public static String getApplicantAutocomplete(String text, int numberOfRows){
		
		
		return "[{\"anc\":\"CH\",\"anm\":\"Davidoff & Cie SA\",\"aid\":\"100298\"},{\"anc\":\"GB\",\"anm\":\"David Brain\",\"aid\":\"100336\"},{\"anc\":\"GB\",\"anm\":\"DAVINA FASHIONS LIMITED\",\"aid\":\"100751\"},{\"anc\":\"ES\",\"anm\":\"David Murciano\",\"aid\":\"101228\"},{\"anc\":\"ES\",\"anm\":\"David Manent Muoz\",\"aid\":\"101720\"},{\"anc\":\"DE\",\"anm\":\"Dirk David Goldbeck\",\"aid\":\"102062\"},{\"anc\":\"AU\",\"anm\":\"David William Ettridge\",\"aid\":\"103172\"},{\"anc\":\"GB\",\"anm\":\"Hogarth Davies Lloyd Limited\",\"aid\":\"104117\"},{\"anc\":\"ES\",\"anm\":\"David Alistair Marshall Paterson\",\"aid\":\"104252\"},{\"anc\":\"GB\",\"anm\":\"Craig David\",\"aid\":\"105807\"},{\"anc\":\"GB\",\"anm\":\"David Stewart Willey\",\"aid\":\"105898\"},{\"anc\":\"ES\",\"anm\":\"Davide Pizzorni\",\"aid\":\"106392\"},{\"anc\":\"GB\",\"anm\":\"David Morris International Limited\",\"aid\":\"106799\"},{\"anc\":\"GB\",\"anm\":\"David W Hodgkinson\",\"aid\":\"107085\"},{\"anc\":\"GB\",\"anm\":\"David Norman Crisp\",\"aid\":\"107110\"},{\"anc\":\"FR\",\"anm\":\"Ccile David Louvard\",\"aid\":\"107148\"},{\"anc\":\"SE\",\"anm\":\"David Griffin\",\"aid\":\"10725\"},{\"anc\":\"GB\",\"anm\":\"Kevin David Nicholas Kearney\",\"aid\":\"10798\"},{\"anc\":\"GB\",\"anm\":\"Raymond Davidson\",\"aid\":\"108876\"},{\"anc\":\"GB\",\"anm\":\"David Garnham\",\"aid\":\"109585\"},{\"anc\":\"NZ\",\"anm\":\"Wilfred Francis David Bisset\",\"aid\":\"109864\"},{\"anc\":\"GB\",\"anm\":\"Travers David McCullough\",\"aid\":\"110208\"},{\"anc\":\"US\",\"anm\":\"David Rowland\",\"aid\":\"110691\"},{\"anc\":\"FR\",\"anm\":\"David Lafere\",\"aid\":\"112677\"},{\"anc\":\"GB\",\"anm\":\"Howard David Webb\",\"aid\":\"112792\"},{\"anc\":\"IT\",\"anm\":\"Davide Alcide Setten\",\"aid\":\"112932\"},{\"anc\":\"GB\",\"anm\":\"Parke, DavisCo. Limited\",\"aid\":\"113744\"},{\"anc\":\"ES\",\"anm\":\"Marcos David Mayordomo Aznar\",\"aid\":\"114159\"},{\"anc\":\"IT\",\"anm\":\"Davide Romagnoli\",\"aid\":\"114266\"},{\"anc\":\"GB\",\"anm\":\"David Branch\",\"aid\":\"11468\"},{\"anc\":\"GB\",\"anm\":\"David Coleman\",\"aid\":\"114766\"},{\"anc\":\"GB\",\"anm\":\"David Branch\",\"aid\":\"115031\"},{\"anc\":\"US\",\"anm\":\"Famous Daves of America, Inc.\",\"aid\":\"115108\"},{\"anc\":\"ZA\",\"anm\":\"David Whitehead Sons (SA) (Pty) Limited\",\"aid\":\"115610\"},{\"anc\":\"GB\",\"anm\":\"David Bremner\",\"aid\":\"116676\"}]";
	}
	
	public static ApplicantForm getApplicant(String id,String lang){
		LegalEntityForm form=new LegalEntityForm();
			AddressForm address=new AddressForm();
			address.setCity("Alicante");
			address.setCountry("es");
			address.setHouseNumber("2");
			address.setPostalCode("432432");
			address.setStateprovince("Alicante");
			address.setStreet("test street");
			form.setAddress(address);
			form.setConsentForPublishingPersonalInfo(true);
			form.setContactPerson("Pedro Martinez Garcia");
			form.setCurrentUserIndicator(true);
			form.setEmail("test@mail.com");
			form.setFax("956456565");
			form.setDomicile("Madrid");
			form.setDomicileCountry("es");
			form.setId(id);
			form.setImported(true);
			form.setNationalOfficialBusinessRegister("es");
			form.setType(ApplicantKindForm.LEGAL_ENTITY);
			form.setPartOfEEA(true);
			form.setWebsite("www.test.com");
			form.setName("Pedro Martinez");
			form.setReceiveCorrespondenceViaEmail(true);
			form.setPhone("9856565656");
			return form;
	}
}
