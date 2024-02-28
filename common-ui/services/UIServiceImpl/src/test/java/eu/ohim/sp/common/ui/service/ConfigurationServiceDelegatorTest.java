package eu.ohim.sp.common.ui.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.resources.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;

import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.classification.NiceClassificationServiceRemote;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;
import eu.ohim.sp.core.configuration.domain.claims.xsd.SeniorityNatures;
import eu.ohim.sp.core.configuration.domain.claims.xsd.SeniorityNatures.SeniorityNature;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries.Country;
import eu.ohim.sp.core.configuration.domain.country.xsd.Nationalities;
import eu.ohim.sp.core.configuration.domain.country.xsd.Nationalities.Nationality;
import eu.ohim.sp.core.configuration.domain.country.xsd.States;
import eu.ohim.sp.core.configuration.domain.country.xsd.States.Country.State;
import eu.ohim.sp.core.configuration.domain.language.xsd.Languages;
import eu.ohim.sp.core.configuration.domain.language.xsd.Languages.Language;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PaymentMethods;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PaymentMethods.PaymentMethod;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.document.DocumentServiceRemote;
import eu.ohim.sp.core.resource.ResourceServiceRemote;
import eu.ohim.sp.core.rules.RulesServiceRemote;

public class ConfigurationServiceDelegatorTest {

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private SectionViewConfiguration viewConfiguration;

    @Mock
    private NiceClassificationServiceRemote classificationService;

    @Mock
    private DocumentServiceRemote documentService;

    @Mock
    private RulesServiceRemote businessRulesService;

    @Mock
    private ResourceServiceRemote resourceService;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private ConfigurationServiceDelegator configurationServiceDelegator;

    private static final String FIRST_LANG_PARAM = "first-lang-conf";
    private static final String SECOND_LANG_PARAM = "second-lang-conf";
    private static final String COUNTRIES_PARAM = "country-config";
    private static final String NATIONALITIES_PARAM = "nationality-config";
    private static final String STATES_PARAM = "states-config";
    private static final String SENIORITY_NATURE_PARAM = "seniority-natures";
    private static final String EXHIBITION_PARAM = "exhibitions";
    private static final String PAYMENT_METHOD_PARAM = "payment-methods";
    private static final String PAYER_TYPE_PARAM = "payer-types";

    private static final String CODE = "code";
    private static final String VALUE = "value";
    private static final String FLOW_MODE_ID = "flowModeId";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFirstLanguages() {
        List<Language> languageList = new ArrayList<Languages.Language>();
        languageList.add(new Language(CODE, VALUE));
        Languages languagesMock = new Languages(languageList);

        when(
                configurationService.getObject(FIRST_LANG_PARAM, null,
                        Languages.class)).thenReturn(languagesMock);

        List<Language> languages = configurationServiceDelegator
                .getFirstLanguages();
        assertEquals(languages.size(), 1);
    }

    @Test
    public void testResolveFirstLanguageCode() {
        List<Language> languageList = new ArrayList<Languages.Language>();
        languageList.add(new Language(CODE, VALUE));
        Languages languagesMock = new Languages(languageList);

        when(
                configurationService.getObject(FIRST_LANG_PARAM, null,
                        Languages.class)).thenReturn(languagesMock);

        Language language = configurationServiceDelegator
                .resolveFirstLanguageCode(CODE);
        assertEquals(language.getCode(), CODE);
    }

    @Test
    public void testResolveFirstLanguageCodeNull() {
        List<Language> languageList = new ArrayList<Languages.Language>();
        languageList.add(new Language(CODE, VALUE));
        Languages languagesMock = new Languages(languageList);

        when(
                configurationService.getObject(FIRST_LANG_PARAM, null,
                        Languages.class)).thenReturn(languagesMock);

        Language language = configurationServiceDelegator
                .resolveFirstLanguageCode(null);
        assertEquals(language, null);
    }

    @Test
    public void testGetSecondLanguages() {
        List<Language> languageList = new ArrayList<Languages.Language>();
        languageList.add(new Language(CODE, VALUE));
        Languages languagesMock = new Languages(languageList);

        when(
                configurationService.getObject(SECOND_LANG_PARAM, null,
                        Languages.class)).thenReturn(languagesMock);

        List<Language> languages = configurationServiceDelegator
                .getSecondLanguages();
        assertEquals(languages.size(), 1);
    }

    @Test
    public void testResolveSecondLanguageCode() {
        List<Language> languageList = new ArrayList<Languages.Language>();
        languageList.add(new Language(CODE, VALUE));
        Languages languagesMock = new Languages(languageList);

        when(
                configurationService.getObject(SECOND_LANG_PARAM, null,
                        Languages.class)).thenReturn(languagesMock);

        Language language = configurationServiceDelegator
                .resolveSecondLanguageCode(CODE);
        assertEquals(language.getCode(), CODE);
    }

    @Test
    public void testResolveSecondLanguageCodeNull() {
        List<Language> languageList = new ArrayList<Languages.Language>();
        languageList.add(new Language(CODE, VALUE));
        Languages languagesMock = new Languages(languageList);

        when(
                configurationService.getObject(SECOND_LANG_PARAM, null,
                        Languages.class)).thenReturn(languagesMock);

        Language language = configurationServiceDelegator
                .resolveSecondLanguageCode(null);
        assertEquals(language, null);
    }

    @Test
    public void testGetDocumentSize() {
        // given
        final String documentId = "documentId";
        final Long zero = 1L;

        final Document document = mock(Document.class);
        when(document.getFileSize()).thenReturn(zero);

        when(documentService.getDocument(eq(documentId))).thenReturn(document);

        // when
        Double fileSize = configurationServiceDelegator.getDocumentSize(documentId, "fileName");

        // then
        assertEquals(fileSize.intValue(), zero.intValue());
    }

    @Test
    public void testGetCountries() {
        Countries countriesMock = new Countries(
                new ArrayList<Countries.Country>());
        countriesMock.getCountry().add(new Country());
        countriesMock.getCountry().get(0).setCode(CODE);
        when(
                configurationService.getObject(COUNTRIES_PARAM, null,
                        Countries.class)).thenReturn(countriesMock);

        List<Country> countries = configurationServiceDelegator.getCountries();
        assertEquals(countries.size(), 1);
    }

    @Test
    public void testGetNationalities() {
        Nationalities nationalitiesMock = new Nationalities(
                new ArrayList<Nationalities.Nationality>());
        nationalitiesMock.getNationality().add(new Nationality());
        nationalitiesMock.getNationality().get(0).setCode(CODE);
        when(
                configurationService.getObject(NATIONALITIES_PARAM, null,
                        Nationalities.class)).thenReturn(nationalitiesMock);

        List<Nationality> nationalities = configurationServiceDelegator
                .getNationalities();
        assertEquals(nationalities.size(), 1);
    }

    @Test
    public void testGetStates() {
        States statesMock = new States(new ArrayList<States.Country>());
        statesMock.getCountry().add(new States.Country());
        statesMock.getCountry().get(0).setCountryCode(CODE);
        statesMock.getCountry().add(new States.Country());
        statesMock.getCountry().get(1).setCountryCode(VALUE);
        when(
                configurationService
                        .getObject(STATES_PARAM, null, States.class))
                .thenReturn(statesMock);

        List<State> states = configurationServiceDelegator.getStates(CODE);
        assertEquals(states.size(), 0);
    }

    @Test
    public void testGetMessage() {
        when(resourceService.getMessage(Mockito.anyString()))
                .thenReturn(VALUE);
        assertEquals(configurationServiceDelegator.getMessage(CODE), VALUE);
    }

    @Test
    public void testGetSeniorityNatures() {
        SeniorityNatures seniorityNaturesMock = new SeniorityNatures(
                new ArrayList<SeniorityNatures.SeniorityNature>());
        seniorityNaturesMock.getSeniorityNature().add(
                new SeniorityNature(CODE, VALUE));
        when(
                configurationService.getObject(SENIORITY_NATURE_PARAM, null,
                        SeniorityNatures.class)).thenReturn(
                seniorityNaturesMock);
        List<SeniorityNature> seniorityNatures = configurationServiceDelegator
                .getSeniorityNatures();
        assertEquals(seniorityNatures.size(), 1);
    }

    @Test
    public void testGetExhibitions() {
        Exhibitions exhibitionsMock = new Exhibitions(
                new ArrayList<Exhibitions.Exhibition>());
        exhibitionsMock.getExhibition().add(new Exhibition());
        when(
                configurationService.getObject(EXHIBITION_PARAM, null,
                        Exhibitions.class)).thenReturn(exhibitionsMock);

        List<Exhibition> exhibitions = configurationServiceDelegator
                .getExhibitions();
        assertEquals(exhibitions.size(), 1);
    }

    @Test
    public void testGetActivePaymentMethods() {
        PaymentMethods paymentMethodsMock = new PaymentMethods(
                new ArrayList<PaymentMethods.PaymentMethod>());
        paymentMethodsMock.getPaymentMethod().add(
                new PaymentMethod(CODE, VALUE, true, true));
        paymentMethodsMock.getPaymentMethod().add(
                new PaymentMethod(CODE, VALUE, false, false));

        when(
                configurationService.getObject(PAYMENT_METHOD_PARAM, null,
                        PaymentMethods.class)).thenReturn(paymentMethodsMock);

        List<PaymentMethod> paymentMethods = configurationServiceDelegator
                .getActivePaymentMethods();
        assertEquals(paymentMethods.size(), 1);
    }

    @Test
    public void testGetActivePayerTypes() {
        PayerTypes payerTypesMock = new PayerTypes(
                new ArrayList<PayerTypes.PayerType>());
        payerTypesMock.getPayerType().add(new PayerType(CODE, VALUE, true));
        payerTypesMock.getPayerType().add(new PayerType(CODE, VALUE, false));
        when(
                configurationService.getObject(PAYER_TYPE_PARAM, null,
                        PayerTypes.class)).thenReturn(payerTypesMock);

        List<PayerType> activePayerTypes = configurationServiceDelegator
                .getActivePayerTypes();
        assertEquals(activePayerTypes.size(), 1);
    }

    @Test
    public void testGetValueFromGeneralComponent() {
        when(
                configurationService.getValue(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(VALUE);
        assertEquals(
                configurationServiceDelegator
                        .getValueFromGeneralComponent("key"),
                VALUE);
    }

    @Test
    public void testGetValue() {
        when(
                configurationService.getValue(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(VALUE);
        assertEquals(
                configurationServiceDelegator.getValue("key", "component"),
                VALUE);
    }

    @Test
    public void testGetXMLFromGeneralComponent() {
        when(
                configurationService.getXml(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(VALUE);
        assertEquals(
                configurationServiceDelegator.getXMLFromGeneralComponent(CODE),
                VALUE);
    }

    @Test
    public void testGetObjectFromGeneralComponent() {
        when(configurationService.getObject(CODE, null, String.class))
                .thenReturn(VALUE);
        assertEquals(
                configurationServiceDelegator.getObjectFromGeneralComponent(
                        CODE, String.class), VALUE);
    }

    @Test
    public void testGetApplicantTypes() {
        List<Section> sectionsMock = new ArrayList<Section>();
        sectionsMock.add(new Section());
        sectionsMock.get(0).setPath(VALUE);
        when(
                viewConfiguration.getSortedSubsections(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(AvailableSection.class))).thenReturn(
                sectionsMock);

        List<String> types = configurationServiceDelegator
                .getApplicantTypes(FLOW_MODE_ID);
        assertEquals(types.size(), 1);
    }

    @Test
    public void testGetRepresentativeTypes() {
        List<Section> sectionsMock = new ArrayList<Section>();
        sectionsMock.add(new Section());
        sectionsMock.get(0).setPath(VALUE);
        when(
                viewConfiguration.getSortedSubsections(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(AvailableSection.class))).thenReturn(
                sectionsMock);

        List<String> types = configurationServiceDelegator
                .getRepresentativeTypes(FLOW_MODE_ID);
        assertEquals(types.size(), 1);
    }

    @Test
    public void testGetPersonTypes() {
        List<Section> sectionsMock = new ArrayList<Section>();
        sectionsMock.add(new Section());
        sectionsMock.get(0).setPath(VALUE);
        when(
                viewConfiguration.getSortedSubsections(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(AvailableSection.class))).thenReturn(
                sectionsMock);

        List<String> types = configurationServiceDelegator
                .getPersonTypes(FLOW_MODE_ID);
        assertEquals(types.size(), 1);
    }

    @Test
    public void testIsServiceEnabled() {
        String serviceEnabled = "enabled";
        String serviceNotEnabled = "notEnabled";
        String componentEnabled = "componentEnabled";
        String componentNotEnabled = "componentNotEnabled";
        String generalComponent = "general";

        when(
                configurationService.isServiceEnabled(
                        eq(serviceEnabled),eq(componentEnabled))).thenReturn(Boolean.TRUE);
        when(
                configurationService.isServiceEnabled(
                        eq(serviceEnabled),eq(generalComponent))).thenReturn(Boolean.TRUE);
        when(
                configurationService.isServiceEnabled(
                        eq(serviceNotEnabled),eq(componentNotEnabled))).thenReturn(Boolean.FALSE);
        when(
                configurationService.isServiceEnabled(
                        eq(serviceNotEnabled),eq(generalComponent))).thenReturn(Boolean.FALSE);

        assertEquals(configurationServiceDelegator.isServiceEnabled(serviceEnabled,componentEnabled), Boolean.TRUE);
        assertEquals(configurationServiceDelegator.isServiceEnabled(serviceEnabled,generalComponent), Boolean.TRUE);
        assertEquals(configurationServiceDelegator.isServiceEnabled(serviceNotEnabled,componentNotEnabled), Boolean.FALSE);
        assertEquals(configurationServiceDelegator.isServiceEnabled(serviceNotEnabled,generalComponent), Boolean.FALSE);
    }

    @Test
    public void testIsSecurityEnabled() {
        assertEquals(configurationServiceDelegator.isSecurityEnabled(), false);
    }

    @Test
    public void testEmptyCache() {
        configurationServiceDelegator.emptyCache();
    }

    @Test
    public void testEmptyCacheByCacheName() {
        configurationServiceDelegator.emptyCache("cacheName");
    }
}
