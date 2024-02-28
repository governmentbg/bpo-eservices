package bg.duosoft.taxcalculator.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApplicationTypes {

    //the module names
    public static final String TMEFILING_KEY = "tmefilingfee";
    public static final String TMOBJECTION_KEY = "tmobjection";
    public static final String TMOPPOSITION_KEY = "tmopposition";
    public static final String TMAPPEAL_KEY = "tmappeal";
    public static final String TMINVALIDITY_KEY = "tminvalidity";
    public static final String TMREVOCATION_KEY = "tmrevocation";
    public static final String TMWITHDRAWAL_KEY = "tmwithdrawal";
    public static final String TMSURRENDER_KEY = "tmsurrender";
    public static final String TMTRANSFER_KEY = "tmtransfer";
    public static final String TMLICENCE_KEY = "tmlicence";
    public static final String TMREM_KEY = "tmrem";
    public static final String TMSECURITY_KEY = "tmsecurity";
    public static final String TMBANKRUPTCY_KEY = "tmbankruptcy";
    public static final String TMRENEWAL_KEY = "tmrenewal";
    public static final String TMCHANGE_KEY = "tmchange";
    public static final String TMCHANGEREP_KEY = "tmchangerep";
    public static final String TMCHANGECA_KEY = "tmchangeca";
    public static final String TMGENERIC_KEY = "tmgeneric";
    public static final String TMPROVIDEPOW_KEY = "tmprovidepow";
    public static final String TMPAIDTAXES_KEY = "tmpaidtaxes";

    //    DESIGNS
    public static final String DSEFILING_KEY = "dsefiling";
    public static final String DSAPPEAL_KEY = "dsappeal";
    public static final String DSWITHDRAWAL_KEY = "dswithdrawal";

    public static final String DSBANKRUPTCY_KEY = "dsbankruptcy";
    public static final String DSCHANGE_KEY = "dschange";
    public static final String DSCHANGECA_KEY = "dschangeca";
    public static final String DSCHANGEREP_KEY = "dschangerep";
    public static final String DSGENERIC_KEY = "dsgeneric";
    public static final String DSINVALIDITY_KEY = "dsinvalidity";
    public static final String DSLICENCE_KEY = "dslicence";
    public static final String DSREM_KEY = "dsrem";
    public static final String DSRENEWAL_KEY = "dsrenewal";
    public static final String DSSECURITY_KEY = "dssecurity";
    public static final String DSSURRENDER_KEY = "dssurrender";
    public static final String DSTRANSFER_KEY = "dstransfer";
    public static final String DSPROVIDEPOW_KEY = "dsprovidepow";
    public static final String DSPAIDTAXES_KEY = "dspaidtaxes";

    //    PATENTS
    public static final String PTEFILING_KEY = "ptefilingfee";
    public static final String PTBANKRUPTCY_KEY = "ptbankruptcy";
    public static final String PTDOCCHANGES_KEY = "ptdocchanges";
    public static final String PTCHANGE_KEY = "ptchange";
    public static final String PTLICENCE_KEY = "ptlicence";
    public static final String PTREM_KEY = "ptrem";
    public static final String PTSECURITY_KEY = "ptsecurity";
    public static final String PTTRANSFER_KEY = "pttransfer";
    public static final String PTPIS_KEY = "ptpis";
    public static final String PTCHANGECA_KEY = "ptchangeca";
    public static final String PTAPPLERR_KEY = "ptapplerr";
    public static final String PTAPPEAL_KEY = "ptappeal";
    public static final String PTSURRENDER_KEY = "ptsurrender";
    public static final String PTWITHDRAWAL_KEY = "ptwithdrawal";
    public static final String PTCHANGEREP_KEY = "ptchangerep";
    public static final String PTINVALIDITY_KEY = "ptinvalidity";
    public static final String PTGENERIC_KEY = "ptgeneric";
    public static final String PTPROVIDEPOW_KEY = "ptprovidepow";
    public static final String PTPAIDTAXES_KEY = "ptpaidtaxes";
    public static final String PTCOMPULSORYLICENCE_KEY = "ptcompulsorylicence";
    public static final String PTINVDENIAL_KEY = "ptinvdenial";

    //    UTILITY MODELS
    public static final String UMEFILING_KEY = "umefilingfee";
    public static final String UMDOCCHANGES_KEY = "umdocchanges";
    public static final String UMBANKRUPTCY_KEY = "umbankruptcy";
    public static final String UMCHANGE_KEY = "umchange";
    public static final String UMLICENCE_KEY = "umlicence";
    public static final String UMREM_KEY = "umrem";
    public static final String UMSECURITY_KEY = "umsecurity";
    public static final String UMTRANSFER_KEY = "umtransfer";
    public static final String UMPIS_KEY = "umpis";
    public static final String UMCHANGECA_KEY = "umchangeca";
    public static final String UMAPPLERR_KEY = "umapplerr";
    public static final String UMREGDECLINE_KEY = "umregdecline";
    public static final String UMREGTERMINATION_KEY = "umregtermination";
    public static final String UMPARTIALINVALIDITY_KEY = "umpartialinvalidity";
    public static final String UMFULLINVALIDITY_KEY = "umfullinvalidity";
    public static final String UMSURRENDER_KEY = "umsurrender";
    public static final String UMWITHDRAWAL_KEY = "umwithdrawal";
    public static final String UMCHANGEREP_KEY = "umchangerep";
    public static final String UMRENEWAL_KEY = "umrenewal";
    public static final String UMGENERIC_KEY = "umgeneric";
    public static final String UMPROVIDEPOW_KEY = "umprovidepow";
    public static final String UMPAIDTAXES_KEY = "umpaidtaxes";
    public static final String UMAPPEAL_KEY = "umappeal";
    public static final String UMINVALIDITY_KEY = "uminvalidity";
    public static final String UMINVDENIAL_KEY = "uminvdenial";

    //    EUROPEAN PATENTS
    public static final String EPEFILING_KEY = "epefilingfee";
    public static final String EPBANKRUPTCY_KEY = "epbankruptcy";
    public static final String EPCHANGE_KEY = "epchange";
    public static final String EPLICENCE_KEY = "eplicence";
    public static final String EPREM_KEY = "eprem";
    public static final String EPSECURITY_KEY = "epsecurity";
    public static final String EPTRANSFER_KEY = "eptransfer";
    public static final String EPPIS_KEY = "eppis";
    public static final String EPCHANGECA_KEY = "epchangeca";
    public static final String EPPARTIALINVALIDITY_KEY = "eppartialinvalidity";
    public static final String EPFULLINVALIDITY_KEY = "epfullinvalidity";
//    public static final String EPTRANSLATIONEDIT_KEY = "eptranslationedit";
    public static final String EPSURRENDER_KEY = "epsurrender";
    public static final String EPWITHDRAWAL_KEY = "epwithdrawal";
    public static final String EPCHANGEREP_KEY = "epchangerep";
    public static final String EPGENERIC_KEY = "epgeneric";
    public static final String EPPROVIDEPOW_KEY = "epprovidepow";
    public static final String EPPAIDTAXES_KEY = "eppaidtaxes";

    //    PROTECTION_CERTIFICATES
    public static final String PCISSUANCECERTIFICATE_KEY = "pcissuancecertificate";
    public static final String PCMEDTERMEXTENSION_KEY = "pcmedtermextension";
    public static final String PCREQUESTPUB_KEY = "pcrequestpub";
    public static final String PCREGISTERPUB_KEY = "pcregisterpub";
    public static final String PCEXPIRATIONPUB_KEY = "pcexpirationpub";
    public static final String PCMEDPLANTCERT_KEY = "pcmedplantcert";
    public static final String PCMEDTERMEXT_KEY = "pcmedtermext";
    public static final String PCISSUANCEPUB_KEY = "pcissuancepub";
    public static final String PCMEDTERMEXTPUB_KEY = "pctermextpub";
    public static final String PCREFUSALCOMPLAINT_KEY = "pcrefusalcomplaint";
    public static final String PCTERMINATIONCOMPLAINT_KEY = "pcterminationcomplaint";
    public static final String PCINVALIDITY_KEY = "pcinvalidity";
    public static final String PCPIS_KEY = "pcpis";
    public static final String PCCHANGECA_KEY = "pcchangeca";
    public static final String PCAPPLERR_KEY = "pcapplerr";
    public static final String PCREGERR_KEY = "pcregerr";
    public static final String PCBPOERR_KEY = "pcbpoerr";
    public static final String PCTRANSFER_KEY = "pctransfer";
    public static final String PCCHANGE_KEY = "pcchange";
    public static final String PCLICENCE_KEY = "pclicence";
    public static final String PCDUPLICATE_KEY = "pcduplicate";
    public static final String PCCHANGEII_KEY = "pcchangeII";
    public static final String PCRESPONSEI_KEY = "pcresponseI";
    public static final String PCRESPONSEII_KEY = "pcresponseII";

    //    SPC
    public static final String SPCEFILING_KEY = "spcefilingfee";
    public static final String SPCEXTENDTERM_KEY = "spcextendterm";
    public static final String SPCTRANSFER_KEY = "spctransfer";
    public static final String SPCCHANGE_KEY = "spcchange";
    public static final String SPCCHANGEREP_KEY = "spcchangerep";
    public static final String SPCPROVIDEPOW_KEY = "spcprovidepow";
    public static final String SPCPAIDTAXES_KEY = "spcpaidtaxes";
    public static final String SPCCHANGECA_KEY = "spcchangeca";
    public static final String SPCINVALIDITY_KEY = "spcinvalidity";
    public static final String SPCLICENCE_KEY = "spclicence";
    public static final String SPCREM_KEY = "spcrem";
    public static final String SPCSECURITY_KEY = "spcsecurity";
    public static final String SPCBANKRUPTCY_KEY = "spcbankruptcy";
    public static final String SPCGENERIC_KEY = "spcgeneric";

    //    SORTS AND VARIETIES
    public static final String SVEFILING_KEY = "svefilingfee";
    public static final String SVTRANSFER_KEY = "svtransfer";
    public static final String SVCHANGE_KEY = "svchange";
    public static final String SVCHANGEREP_KEY = "svchangerep";
    public static final String SVPROVIDEPOW_KEY = "svprovidepow";
    public static final String SVPAIDTAXES_KEY = "svpaidtaxes";
    public static final String SVCHANGECA_KEY = "svchangeca";
    public static final String SVLICENCE_KEY = "svlicence";
    public static final String SVCOMPULSORYLICENCE_KEY = "svcompulsorylicence";
    public static final String SVREM_KEY = "svrem";
    public static final String SVSECURITY_KEY = "svsecurity";
    public static final String SVBANKRUPTCY_KEY = "svbankruptcy";
    public static final String SVGENERIC_KEY = "svgeneric";

    //the message names
    private static final Map<String, String> tmApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put(TMEFILING_KEY, "tmefilingfee");
        aMap.put(TMOBJECTION_KEY, "tmobjection");
        aMap.put(TMOPPOSITION_KEY, "tmopposition");
        aMap.put(TMAPPEAL_KEY, "tmappeal");
        aMap.put(TMINVALIDITY_KEY, "tminvalidity");
        aMap.put(TMREVOCATION_KEY, "tmrevocation");
        aMap.put(TMWITHDRAWAL_KEY, "tmwithdrawal");
        aMap.put(TMSURRENDER_KEY, "tmsurrender");
        aMap.put(TMTRANSFER_KEY, "tmtransfer");
        aMap.put(TMLICENCE_KEY, "tmlicence");
        aMap.put(TMREM_KEY, "tmrem");
        aMap.put(TMSECURITY_KEY, "tmsecurity");
        aMap.put(TMBANKRUPTCY_KEY, "tmbankruptcy");
        aMap.put(TMRENEWAL_KEY, "tmrenewal");
        aMap.put(TMCHANGE_KEY, "tmchange");
        aMap.put(TMCHANGEREP_KEY, "tmchangerep");
        aMap.put(TMCHANGECA_KEY, "tmchangeca");
        aMap.put(TMPROVIDEPOW_KEY, "tmprovidepow");
        aMap.put(TMPAIDTAXES_KEY, "tmpaidtaxes");
        aMap.put(TMGENERIC_KEY, "tmgeneric");
        tmApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getTmApplicationTypes() {
        return tmApplicationTypes;
    }

    private static final Map<String, String> dsApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put(DSEFILING_KEY, "dsefiling");
        aMap.put(DSAPPEAL_KEY, "dsappeal");
        aMap.put(DSINVALIDITY_KEY, "dsinvalidity");
        aMap.put(DSWITHDRAWAL_KEY, "dswithdrawal");
        aMap.put(DSSURRENDER_KEY, "dssurrender");
        aMap.put(DSTRANSFER_KEY, "dstransfer");
        aMap.put(DSLICENCE_KEY, "dslicence");
        aMap.put(DSREM_KEY, "dsrem");
        aMap.put(DSSECURITY_KEY, "dssecurity");
        aMap.put(DSBANKRUPTCY_KEY, "dsbankruptcy");
        aMap.put(DSRENEWAL_KEY, "dsrenewal");
        aMap.put(DSCHANGE_KEY, "dschange");
        aMap.put(DSCHANGEREP_KEY, "dschangerep");
        aMap.put(DSPROVIDEPOW_KEY, "dsprovidepow");
        aMap.put(DSPAIDTAXES_KEY, "dspaidtaxes");
        aMap.put(DSCHANGECA_KEY, "dschangeca");
        aMap.put(DSGENERIC_KEY, "dsgeneric");
        dsApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getDsApplicationTypes() {
        return dsApplicationTypes;
    }

    private static final Map<String, String> ptApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put(PTEFILING_KEY, "ptefilingfee");
        aMap.put(PTDOCCHANGES_KEY, "ptdocchanges");
        aMap.put(PTTRANSFER_KEY, "pttransfer");
        aMap.put(PTCHANGE_KEY, "ptchange");
        aMap.put(PTCHANGEREP_KEY, "ptchangerep");
        aMap.put(PTPROVIDEPOW_KEY, "ptprovidepow");
        aMap.put(PTPAIDTAXES_KEY, "ptpaidtaxes");
        aMap.put(PTCHANGECA_KEY, "ptchangeca");
        aMap.put(PTLICENCE_KEY, "ptlicence");
        aMap.put(PTCOMPULSORYLICENCE_KEY, "ptcompulsorylicence");
        aMap.put(PTREM_KEY, "ptrem");
        aMap.put(PTSECURITY_KEY, "ptsecurity");
        aMap.put(PTBANKRUPTCY_KEY, "ptbankruptcy");
        aMap.put(PTAPPEAL_KEY, "ptappeal");
        aMap.put(PTINVALIDITY_KEY, "ptinvalidity");
        aMap.put(PTWITHDRAWAL_KEY, "ptwithdrawal");
        aMap.put(PTSURRENDER_KEY, "ptsurrender");
        aMap.put(PTINVDENIAL_KEY, "ptinvdenial");
        aMap.put(PTGENERIC_KEY, "ptgeneric");
//        aMap.put(PTPIS_KEY, "ptpis");
//        aMap.put(PTAPPLERR_KEY, "ptapplerr");
        ptApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getPtApplicationTypes() {
        return ptApplicationTypes;
    }

    private static final Map<String, String> umApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put(UMEFILING_KEY, "umefilingfee");
        aMap.put(UMDOCCHANGES_KEY, "umdocchanges");
        aMap.put(UMTRANSFER_KEY, "umtransfer");
        aMap.put(UMCHANGE_KEY, "umchange");
        aMap.put(UMCHANGEREP_KEY, "umchangerep");
        aMap.put(UMPROVIDEPOW_KEY, "umprovidepow");
        aMap.put(UMPAIDTAXES_KEY, "umpaidtaxes");
        aMap.put(UMCHANGECA_KEY, "umchangeca");
        aMap.put(UMLICENCE_KEY, "umlicence");
        aMap.put(UMREM_KEY, "umrem");
        aMap.put(UMSECURITY_KEY, "umsecurity");
        aMap.put(UMBANKRUPTCY_KEY, "umbankruptcy");
        aMap.put(UMAPPEAL_KEY, "umappeal");
        aMap.put(UMINVALIDITY_KEY, "uminvalidity");
        aMap.put(UMRENEWAL_KEY, "umrenewal");
        aMap.put(UMWITHDRAWAL_KEY, "umwithdrawal");
        aMap.put(UMSURRENDER_KEY, "umsurrender");
        aMap.put(UMINVDENIAL_KEY, "uminvdenial");
        aMap.put(UMGENERIC_KEY, "umgeneric");
//        aMap.put(UMPIS_KEY, "umpis");
//        aMap.put(UMAPPLERR_KEY, "umapplerr");
//        aMap.put(UMREGDECLINE_KEY, "umregdecline");
//        aMap.put(UMREGTERMINATION_KEY, "umregtermination");
//        aMap.put(UMPARTIALINVALIDITY_KEY, "umpartialinvalidity");
//        aMap.put(UMFULLINVALIDITY_KEY, "umfullinvalidity");
        umApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getUmApplicationTypes() {
        return umApplicationTypes;
    }

    private static final Map<String, String> epApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put(EPEFILING_KEY, "epefilingfee");
        aMap.put(EPTRANSFER_KEY, "eptransfer");
        aMap.put(EPCHANGE_KEY, "epchange");
        aMap.put(EPCHANGEREP_KEY, "epchangerep");
        aMap.put(EPPROVIDEPOW_KEY, "epprovidepow");
        aMap.put(EPPAIDTAXES_KEY, "eppaidtaxes");
        aMap.put(EPCHANGECA_KEY, "epchangeca");
        aMap.put(EPLICENCE_KEY, "eplicence");
        aMap.put(EPREM_KEY, "eprem");
        aMap.put(EPSECURITY_KEY, "epsecurity");
        aMap.put(EPBANKRUPTCY_KEY, "epbankruptcy");
        aMap.put(EPWITHDRAWAL_KEY, "epwithdrawal");
        aMap.put(EPSURRENDER_KEY, "epsurrender");
        aMap.put(EPGENERIC_KEY, "epgeneric");
        aMap.put(EPPIS_KEY, "eppis");
        aMap.put(EPPARTIALINVALIDITY_KEY, "eppartialinvalidity");
        aMap.put(EPFULLINVALIDITY_KEY, "epfullinvalidity");
        epApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getEpApplicationTypes() {
        return epApplicationTypes;
    }

    private static final Map<String, String> pcApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
//        aMap.put(PCISSUANCECERTIFICATE_KEY, "pcissuancecertificate");
//        aMap.put(PCMEDTERMEXTENSION_KEY, "pcmedtermextension");
//        aMap.put(PCREQUESTPUB_KEY, "pcrequestpub");
//        aMap.put(PCREGISTERPUB_KEY, "pcregisterpub");
//        aMap.put(PCEXPIRATIONPUB_KEY, "pcexpirationpub");
//        aMap.put(PCMEDPLANTCERT_KEY, "pcmedplantcert");
//        aMap.put(PCMEDTERMEXT_KEY, "pcmedtermext");
//        aMap.put(PCISSUANCEPUB_KEY, "pcissuancepub");
//        aMap.put(PCMEDTERMEXTPUB_KEY, "pctermextpub");
//        aMap.put(PCREFUSALCOMPLAINT_KEY, "pcrefusalcomplaint");
//        aMap.put(PCTERMINATIONCOMPLAINT_KEY, "pcterminationcomplaint");
//        aMap.put(PCINVALIDITY_KEY, "pcinvalidity");
//        aMap.put(PCPIS_KEY, "pcpis");
//        aMap.put(PCCHANGECA_KEY, "pcchangeca");
//        aMap.put(PCAPPLERR_KEY, "pcapplerr");
//        aMap.put(PCREGERR_KEY, "pcregerr");
//        aMap.put(PCBPOERR_KEY, "pcbpoerr");
//        aMap.put(PCTRANSFER_KEY, "pctransfer");
//        aMap.put(PCCHANGE_KEY, "pcchange");
//        aMap.put(PCLICENCE_KEY, "pclicence");
//        aMap.put(PCDUPLICATE_KEY, "pcduplicate");
//        aMap.put(PCCHANGEII_KEY, "pcchangeII");
//        aMap.put(PCRESPONSEI_KEY, "pcresponseI");
//        aMap.put(PCRESPONSEII_KEY, "pcresponseII");

        aMap.put(SPCEFILING_KEY, "spcefilingfee");
        aMap.put(SPCEXTENDTERM_KEY, "spcextendterm");
        aMap.put(SPCTRANSFER_KEY, "spctransfer");
        aMap.put(SPCCHANGE_KEY, "spcchange");
        aMap.put(SPCCHANGEREP_KEY, "spcchangerep");
        aMap.put(SPCPROVIDEPOW_KEY, "spcprovidepow");
        aMap.put(SPCPAIDTAXES_KEY, "spcpaidtaxes");
        aMap.put(SPCCHANGECA_KEY, "spcchangeca");
        aMap.put(SPCINVALIDITY_KEY, "spcinvalidity");
        aMap.put(SPCLICENCE_KEY, "spclicence");
        aMap.put(SPCREM_KEY, "spcrem");
        aMap.put(SPCSECURITY_KEY, "spcsecurity");
        aMap.put(SPCBANKRUPTCY_KEY, "spcbankruptcy");
        aMap.put(SPCGENERIC_KEY, "spcgeneric");
        pcApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getPcApplicationTypes() {
        return pcApplicationTypes;
    }

    private static final Map<String, String> svApplicationTypes;

    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put(SVEFILING_KEY, "svefilingfee");
        aMap.put(SVTRANSFER_KEY, "svtransfer");
        aMap.put(SVCHANGE_KEY, "svchange");
        aMap.put(SVCHANGEREP_KEY, "svchangerep");
        aMap.put(SVPROVIDEPOW_KEY, "svprovidepow");
        aMap.put(SVPAIDTAXES_KEY, "svpaidtaxes");
        aMap.put(SVCHANGECA_KEY, "svchangeca");
        aMap.put(SVLICENCE_KEY, "svlicence");
        aMap.put(SVCOMPULSORYLICENCE_KEY, "svcompulsorylicence");
        aMap.put(SVREM_KEY, "svrem");
        aMap.put(SVSECURITY_KEY, "svsecurity");
        aMap.put(SVBANKRUPTCY_KEY, "svbankruptcy");
        aMap.put(SVGENERIC_KEY, "svgeneric");
        svApplicationTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getSvApplicationTypes() {
        return svApplicationTypes;
    }
}
