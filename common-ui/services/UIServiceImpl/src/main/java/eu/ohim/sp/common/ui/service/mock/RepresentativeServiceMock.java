package eu.ohim.sp.common.ui.service.mock;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;

@Deprecated
public class RepresentativeServiceMock {
	public static String getRepresentativeAutocomplete(String text, int numberOfRows){
		
		
		return "[{\"rnc\":\"CH\",\"rnm\":\"Davidoff & Cie SA\",\"rid\":\"100298\"},{\"rnc\":\"GB\",\"rnm\":\"David Brain\",\"rid\":\"100336\"},{\"rnc\":\"GB\",\"rnm\":\"DAVINA FASHIONS LIMITED\",\"rid\":\"100751\"},{\"rnc\":\"ES\",\"rnm\":\"David Murciano\",\"rid\":\"101228\"},{\"rnc\":\"ES\",\"rnm\":\"David Manent Muoz\",\"rid\":\"101720\"},{\"rnc\":\"DE\",\"rnm\":\"Dirk David Goldbeck\",\"rid\":\"102062\"},{\"rnc\":\"AU\",\"rnm\":\"David William Ettridge\",\"rid\":\"103172\"},{\"rnc\":\"GB\",\"rnm\":\"Hogarth Davies Lloyd Limited\",\"rid\":\"104117\"},{\"rnc\":\"ES\",\"rnm\":\"David Alistair Marshall Paterson\",\"rid\":\"104252\"},{\"rnc\":\"GB\",\"rnm\":\"Craig David\",\"rid\":\"105807\"},{\"rnc\":\"GB\",\"rnm\":\"David Stewart Willey\",\"rid\":\"105898\"},{\"rnc\":\"ES\",\"rnm\":\"Davide Pizzorni\",\"rid\":\"106392\"},{\"rnc\":\"GB\",\"rnm\":\"David Morris International Limited\",\"rid\":\"106799\"},{\"rnc\":\"GB\",\"rnm\":\"David W Hodgkinson\",\"rid\":\"107085\"},{\"rnc\":\"GB\",\"rnm\":\"David Norman Crisp\",\"rid\":\"107110\"},{\"rnc\":\"FR\",\"rnm\":\"Ccile David Louvard\",\"rid\":\"107148\"},{\"rnc\":\"SE\",\"rnm\":\"David Griffin\",\"rid\":\"10725\"},{\"rnc\":\"GB\",\"rnm\":\"Kevin David Nicholas Kearney\",\"rid\":\"10798\"},{\"rnc\":\"GB\",\"rnm\":\"Raymond Davidson\",\"rid\":\"108876\"},{\"rnc\":\"GB\",\"rnm\":\"David Garnham\",\"rid\":\"109585\"},{\"rnc\":\"NZ\",\"rnm\":\"Wilfred Frrncis David Bisset\",\"rid\":\"109864\"},{\"rnc\":\"GB\",\"rnm\":\"Travers David McCullough\",\"rid\":\"110208\"},{\"rnc\":\"US\",\"rnm\":\"David Rowland\",\"rid\":\"110691\"},{\"rnc\":\"FR\",\"rnm\":\"David Lafere\",\"rid\":\"112677\"},{\"rnc\":\"GB\",\"rnm\":\"Howard David Webb\",\"rid\":\"112792\"},{\"rnc\":\"IT\",\"rnm\":\"Davide Alcide Setten\",\"rid\":\"112932\"},{\"rnc\":\"GB\",\"rnm\":\"Parke, DavisCo. Limited\",\"rid\":\"113744\"},{\"rnc\":\"ES\",\"rnm\":\"Marcos David Mayordomo Aznar\",\"rid\":\"114159\"},{\"rnc\":\"IT\",\"rnm\":\"Davide Romagnoli\",\"rid\":\"114266\"},{\"rnc\":\"GB\",\"rnm\":\"David Brrnch\",\"rid\":\"11468\"},{\"rnc\":\"GB\",\"rnm\":\"David Coleman\",\"rid\":\"114766\"},{\"rnc\":\"GB\",\"rnm\":\"David Brrnch\",\"rid\":\"115031\"},{\"rnc\":\"US\",\"rnm\":\"Famous Daves of America, Inc.\",\"rid\":\"115108\"},{\"rnc\":\"ZA\",\"rnm\":\"David Whitehead Sons (SA) (Pty) Limited\",\"rid\":\"115610\"},{\"rnc\":\"GB\",\"rnm\":\"David Bremner\",\"rid\":\"116676\"}]";
		
		//return "[{\"rnc\":\"CH\",\"rnm\":\"Davidoff & Cie SA\",\"rid\":\"100298\"},{\"rnc\":\"GB\",\"rnm\":\"David Brain\",\"rid\":\"100336\"},{\"rnc\":\"GB\",\"rnm\":\"DAVINA FASHIONS LIMITED\",\"rid\":\"100751\"},{\"rnc\":\"ES\",\"rnm\":\"David Murciano\",\"rid\":\"101228\"},{\"rnc\":\"ES\",\"rnm\":\"David Manent Muoz\",\"rid\":\"101720\"},{\"rnc\":\"DE\",\"rnm\":\"Dirk David Goldbeck\",\"rid\":\"102062\"},{\"rnc\":\"AU\",\"rnm\":\"David William Ettridge\",\"rid\":\"103172\"},{\"rnc\":\"GB\",\"rnm\":\"Hogarth Davies Lloyd Limited\",\"rid\":\"104117\"},{\"rnc\":\"ES\",\"rnm\":\"David Alistair Marshall Paterson\",\"rid\":\"104252\"},{\"rnc\":\"GB\",\"rnm\":\"Craig David\",\"rid\":\"105807\"},{\"rnc\":\"GB\",\"rnm\":\"David Stewart Willey\",\"rid\":\"105898\"},{\"rnc\":\"ES\",\"rnm\":\"Davide Pizzorni\",\"rid\":\"106392\"},{\"rnc\":\"GB\",\"rnm\":\"David Morris International Limited\",\"rid\":\"106799\"},{\"rnc\":\"GB\",\"rnm\":\"David W Hodgkinson\",\"rid\":\"107085\"},{\"rnc\":\"GB\",\"rnm\":\"David Norman Crisp\",\"rid\":\"107110\"},{\"rnc\":\"FR\",\"rnm\":\"Ccile David Louvard\",\"rid\":\"107148\"},{\"rnc\":\"SE\",\"rnm\":\"David Griffin\",\"rid\":\"10725\"},{\"rnc\":\"GB\",\"rnm\":\"Kevin David Nicholas Kearney\",\"rid\":\"10798\"},{\"rnc\":\"GB\",\"rnm\":\"Raymond Davidson\",\"rid\":\"108876\"},{\"rnc\":\"GB\",\"rnm\":\"David Garnham\",\"rid\":\"109585\"},{\"rnc\":\"NZ\",\"rnm\":\"Wilfred Frrncis David Bisset\",\"rid\":\"109864\"},{\"rnc\":\"GB\",\"rnm\":\"Travers David McCullough\",\"rid\":\"110208\"},{\"rnc\":\"US\",\"rnm\":\"David Rowland\",\"rid\":\"110691\"},{\"rnc\":\"FR\",\"rnm\":\"David Lafere\",\"rid\":\"112677\"},{\"rnc\":\"GB\",\"rnm\":\"Howard David Webb\",\"rid\":\"112792\"},{\"rnc\":\"IT\",\"rnm\":\"Davide Alcide Setten\",\"rid\":\"112932\"},{\"rnc\":\"GB\",\"rnm\":\"Parke, DavisCo. Limited\",\"rid\":\"113744\"},{\"rnc\":\"ES\",\"rnm\":\"Marcos David Mayordomo Aznar\",\"rid\":\"114159\"},{\"rnc\":\"IT\",\"rnm\":\"Davide Romagnoli\",\"rid\":\"114266\"},{\"rnc\":\"GB\",\"rnm\":\"David Brrnch\",\"rid\":\"11468\"},{\"rnc\":\"GB\",\"rnm\":\"David Coleman\",\"rid\":\"114766\"},{\"rnc\":\"GB\",\"rnm\":\"David Brrnch\",\"rid\":\"115031\"},{\"rnc\":\"US\",\"rnm\":\"Famous Daves of America, Inc.\",\"rid\":\"115108\"},{\"rnc\":\"ZA\",\"rnm\":\"David Whitehead Sons (SA) (Pty) Limited\",\"rid\":\"115610\"},{\"rnc\":\"GB\",\"rnm\":\"David Bremner\",\"rid\":\"116676\"}]";
	}
	
	public static RepresentativeForm getRepresentative(String id,String lang){
		RepresentativeNaturalPersonForm form=new RepresentativeNaturalPersonForm();
			AddressForm address=new AddressForm();
			address.setCity("Alicante");
			address.setCountry("es");
			address.setHouseNumber("2");
			address.setPostalCode("432432");
			address.setStateprovince("Alicante");
			address.setStreet("test street");
			form.setAddress(address);
			form.setConsentForPublishingPersonalInfo(true);
			
			form.setCurrentUserIndicator(true);
			form.setEmail("test@mail.com");
			form.setFax("956456565");
			form.setDomicile("Madrid");

			form.setId(id);
			form.setImported(true);
			form.setNationalOfficialBusinessRegister("es");

			form.setPartOfEEA(true);
			form.setWebsite("www.test.com");
			form.setName("Pedro Martinez");
			form.setReceiveCorrespondenceViaEmail(true);
			form.setPhone("9856565656");			
			List<CorrespondenceAddressForm> correspondenceAddresses=new ArrayList<CorrespondenceAddressForm>();
			
			
			CorrespondenceAddressForm caf1=new CorrespondenceAddressForm();
			AddressForm address1=new AddressForm();
			address1.setCity("Madrid");
			address1.setCountry("es");
			address1.setHouseNumber("2");
			address1.setPostalCode("432432");
			address1.setStateprovince("Madrid");
			address1.setStreet("test street");
			caf1.setAddressForm(address1);
			caf1.setCorrespondenceEmail("test@test.com");
			caf1.setCorrespondenceFax("9999999");
			caf1.setCorrespondenceName("Frrncisco Perez Garcia");
			caf1.setCorrespondencePhone("+34565555454");
			correspondenceAddresses.add(caf1);
			
			
			CorrespondenceAddressForm caf2=new CorrespondenceAddressForm();
			AddressForm address2=new AddressForm();
			address2.setCity("Madrid");
			address2.setCountry("es");
			address2.setHouseNumber("2");
			address2.setPostalCode("432432");
			address2.setStateprovince("Madrid");
			address2.setStreet("test street");
			caf2.setAddressForm(address2);
			caf2.setCorrespondenceEmail("test@test.com");
			caf2.setCorrespondenceFax("9999999");
			caf2.setCorrespondenceName("Frrncisco Perez Garcia");
			caf2.setCorrespondencePhone("+34565555454");
			correspondenceAddresses.add(caf2);
			form.setCorrespondenceAddresses(correspondenceAddresses);
			
			form.setCountryOfDomicile("fdsafd");
			form.setFeeByRepresentativeInfo(true);
			form.setReference("reference");
			form.setTaxIdNumber("432432");
			
			return form;
	}
}
