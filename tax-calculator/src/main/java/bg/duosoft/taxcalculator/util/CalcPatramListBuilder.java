package bg.duosoft.taxcalculator.util;

import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeType;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalcPatramListBuilder {
    private static Logger LOG = LoggerFactory.getLogger(CalcPatramListBuilder.class);

    @Autowired
    private static MessageSource messageSource;

    public static List<Object> listBuilder(String applicationType, String params, Locale locale) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> parameters;
        try {
            parameters = mapper.readValue(params, Map.class);
        } catch (IOException e) {
            String message = messageSource.getMessage("error.convert_params", null, locale);
            LOG.error(message);
            throw new Exception(message);
        }


        switch (applicationType) {
            case ApplicationTypes.TMEFILING_KEY:
                return TmBuilder.buildTmefilingList(parameters);
            case ApplicationTypes.TMOBJECTION_KEY:
            case ApplicationTypes.TMREVOCATION_KEY:
            case ApplicationTypes.TMWITHDRAWAL_KEY:
            case ApplicationTypes.TMTRANSFER_KEY:
            case ApplicationTypes.TMREM_KEY:
            case ApplicationTypes.TMSECURITY_KEY:
            case ApplicationTypes.TMBANKRUPTCY_KEY:
            case ApplicationTypes.TMCHANGEREP_KEY:
            case ApplicationTypes.TMCHANGECA_KEY:
            case ApplicationTypes.TMPROVIDEPOW_KEY:
            case ApplicationTypes.TMPAIDTAXES_KEY:

            case ApplicationTypes.DSREM_KEY:
            case ApplicationTypes.DSWITHDRAWAL_KEY:
            case ApplicationTypes.DSBANKRUPTCY_KEY:
            case ApplicationTypes.DSCHANGEREP_KEY:
            case ApplicationTypes.DSCHANGECA_KEY:
            case ApplicationTypes.DSSECURITY_KEY:
            case ApplicationTypes.DSSURRENDER_KEY:
            case ApplicationTypes.DSTRANSFER_KEY:
            case ApplicationTypes.DSPROVIDEPOW_KEY:
            case ApplicationTypes.DSPAIDTAXES_KEY:

            case ApplicationTypes.PTBANKRUPTCY_KEY:
            case ApplicationTypes.PTREM_KEY:
            case ApplicationTypes.PTSECURITY_KEY:
            case ApplicationTypes.PTTRANSFER_KEY:
            case ApplicationTypes.PTSURRENDER_KEY:
            case ApplicationTypes.PTWITHDRAWAL_KEY:
            case ApplicationTypes.PTCHANGEREP_KEY:
            case ApplicationTypes.PTCHANGECA_KEY:
            case ApplicationTypes.PTPROVIDEPOW_KEY:
            case ApplicationTypes.PTPAIDTAXES_KEY:
            case ApplicationTypes.PTDOCCHANGES_KEY:
            case ApplicationTypes.PTINVDENIAL_KEY:

            case ApplicationTypes.UMBANKRUPTCY_KEY:
            case ApplicationTypes.UMREM_KEY:
            case ApplicationTypes.UMSECURITY_KEY:
            case ApplicationTypes.UMTRANSFER_KEY:
            case ApplicationTypes.UMSURRENDER_KEY:
            case ApplicationTypes.UMWITHDRAWAL_KEY:
            case ApplicationTypes.UMCHANGEREP_KEY:
            case ApplicationTypes.UMCHANGECA_KEY:
            case ApplicationTypes.UMPROVIDEPOW_KEY:
            case ApplicationTypes.UMPAIDTAXES_KEY:
            case ApplicationTypes.UMDOCCHANGES_KEY:
            case ApplicationTypes.UMINVDENIAL_KEY:

            case ApplicationTypes.EPBANKRUPTCY_KEY:
            case ApplicationTypes.EPREM_KEY:
            case ApplicationTypes.EPSECURITY_KEY:
            case ApplicationTypes.EPTRANSFER_KEY:
            case ApplicationTypes.EPSURRENDER_KEY:
            case ApplicationTypes.EPWITHDRAWAL_KEY:
            case ApplicationTypes.EPCHANGEREP_KEY:
            case ApplicationTypes.EPCHANGECA_KEY:
            case ApplicationTypes.EPPROVIDEPOW_KEY:
            case ApplicationTypes.EPPAIDTAXES_KEY:

            case ApplicationTypes.SVTRANSFER_KEY:
            case ApplicationTypes.SVCHANGEREP_KEY:
            case ApplicationTypes.SVPROVIDEPOW_KEY:
            case ApplicationTypes.SVPAIDTAXES_KEY:
            case ApplicationTypes.SVCHANGECA_KEY:
            case ApplicationTypes.SVREM_KEY:
            case ApplicationTypes.SVSECURITY_KEY:
            case ApplicationTypes.SVBANKRUPTCY_KEY:

            case ApplicationTypes.SPCEXTENDTERM_KEY:
            case ApplicationTypes.SPCTRANSFER_KEY:
            case ApplicationTypes.SPCCHANGEREP_KEY:
            case ApplicationTypes.SPCPROVIDEPOW_KEY:
            case ApplicationTypes.SPCPAIDTAXES_KEY:
            case ApplicationTypes.SPCCHANGECA_KEY:
            case ApplicationTypes.SPCREM_KEY:
            case ApplicationTypes.SPCSECURITY_KEY:
            case ApplicationTypes.SPCBANKRUPTCY_KEY:
                return new ArrayList<>();
            case ApplicationTypes.TMOPPOSITION_KEY:
                return TmBuilder.buildTmOppositionList(parameters);
            case ApplicationTypes.TMAPPEAL_KEY:
                return TmBuilder.buildTmAppealList(parameters);
            case ApplicationTypes.TMINVALIDITY_KEY:
                return TmBuilder.buildTmInvalidityList(parameters);
            case ApplicationTypes.TMSURRENDER_KEY:
                return TmBuilder.buildTmSurrenderList(parameters);
            case ApplicationTypes.TMLICENCE_KEY:
                return TmBuilder.buildTmLicenceList(parameters);
            case ApplicationTypes.TMRENEWAL_KEY:
                return TmBuilder.buildTmRenewalList(parameters);
            case ApplicationTypes.TMCHANGE_KEY:
                return TmBuilder.buildTmChangeList(parameters);
            case ApplicationTypes.TMGENERIC_KEY:
                return TmBuilder.buildTmGenericList(parameters);

            case ApplicationTypes.DSEFILING_KEY:
                return DsBuilder.buildDsefilingList(parameters);
            case ApplicationTypes.DSAPPEAL_KEY:
                return DsBuilder.buildDsAppealList(parameters);
            case ApplicationTypes.DSCHANGE_KEY:
                return DsBuilder.buildDsChangeList(parameters);
            case ApplicationTypes.DSGENERIC_KEY:
                return DsBuilder.buildDsGenericList(parameters);
            case ApplicationTypes.DSINVALIDITY_KEY:
                return DsBuilder.buildDsInvalidityList(parameters);
            case ApplicationTypes.DSLICENCE_KEY:
                return DsBuilder.buildDsLicenceList(parameters);
            case ApplicationTypes.DSRENEWAL_KEY:
                return DsBuilder.buildDsRenewalList(parameters);

            case ApplicationTypes.PTEFILING_KEY:
                return PtBuilder.buildPtefilingList(parameters);
            case ApplicationTypes.UMEFILING_KEY:
                return PtBuilder.buildUmefilingList(parameters);
            case ApplicationTypes.EPEFILING_KEY:
                return PtBuilder.buildEpefilingList(parameters);
            case ApplicationTypes.PTCHANGE_KEY:
            case ApplicationTypes.UMCHANGE_KEY:
            case ApplicationTypes.EPCHANGE_KEY:
            case ApplicationTypes.SVCHANGE_KEY:
            case ApplicationTypes.SPCCHANGE_KEY:
                return PtBuilder.buildPtChangeList(parameters);
            case ApplicationTypes.PTLICENCE_KEY:
            case ApplicationTypes.PTCOMPULSORYLICENCE_KEY:
            case ApplicationTypes.UMLICENCE_KEY:
            case ApplicationTypes.EPLICENCE_KEY:
            case ApplicationTypes.SVLICENCE_KEY:
            case ApplicationTypes.SVCOMPULSORYLICENCE_KEY:
            case ApplicationTypes.SPCLICENCE_KEY:
                return PtBuilder.buildPtLicenceList(parameters);
            case ApplicationTypes.PTAPPEAL_KEY:
                return PtBuilder.buildPtAppealList(parameters);
            case ApplicationTypes.UMAPPEAL_KEY:
                return PtBuilder.buildUmAppealList(parameters);
            case ApplicationTypes.PTGENERIC_KEY:
            case ApplicationTypes.UMGENERIC_KEY:
            case ApplicationTypes.EPGENERIC_KEY:
            case ApplicationTypes.SVGENERIC_KEY:
            case ApplicationTypes.SPCGENERIC_KEY:
                return PtBuilder.buildPtGenericList(parameters);
            case ApplicationTypes.PTINVALIDITY_KEY:
            case ApplicationTypes.UMINVALIDITY_KEY:
            case ApplicationTypes.SPCINVALIDITY_KEY:
                return PtBuilder.buildPtInvalidityList(parameters);
            case ApplicationTypes.UMRENEWAL_KEY:
                return PtBuilder.buildUmRenewalList(parameters);
            case ApplicationTypes.PTPIS_KEY:
            case ApplicationTypes.UMPIS_KEY:
            case ApplicationTypes.EPPIS_KEY:
            case ApplicationTypes.PCREGISTERPUB_KEY:
            case ApplicationTypes.PCEXPIRATIONPUB_KEY:
            case ApplicationTypes.PCPIS_KEY:
            case ApplicationTypes.PCCHANGECA_KEY:
            case ApplicationTypes.PCBPOERR_KEY:
                return buildBasicFee(0.0);
            case ApplicationTypes.PTAPPLERR_KEY:
            case ApplicationTypes.UMAPPLERR_KEY:
            case ApplicationTypes.PCAPPLERR_KEY:
            case ApplicationTypes.PCRESPONSEI_KEY:
                return buildBasicFee(20.0);
            case ApplicationTypes.PCDUPLICATE_KEY:
                return buildBasicFee(40.0);
            case ApplicationTypes.PCREQUESTPUB_KEY:
            case ApplicationTypes.PCMEDPLANTCERT_KEY:
            case ApplicationTypes.PCMEDTERMEXT_KEY:
            case ApplicationTypes.PCISSUANCEPUB_KEY:
            case ApplicationTypes.PCMEDTERMEXTPUB_KEY:
                return buildBasicFee(50.0);
            case ApplicationTypes.PCREGERR_KEY:
            case ApplicationTypes.PCTRANSFER_KEY:
            case ApplicationTypes.PCLICENCE_KEY:
            case ApplicationTypes.PCRESPONSEII_KEY:
                return buildBasicFee(60.0);
            case ApplicationTypes.UMREGTERMINATION_KEY:
            case ApplicationTypes.PCREFUSALCOMPLAINT_KEY:
            case ApplicationTypes.PCTERMINATIONCOMPLAINT_KEY:
                return buildBasicFee(90.0);
            case ApplicationTypes.UMREGDECLINE_KEY:
                return buildBasicFee(140.0);
            case ApplicationTypes.PCINVALIDITY_KEY:
                return buildBasicFee(150.0);
            case ApplicationTypes.UMPARTIALINVALIDITY_KEY:
            case ApplicationTypes.EPPARTIALINVALIDITY_KEY:
                return buildBasicFee(270.0);
            case ApplicationTypes.PCMEDTERMEXTENSION_KEY:
                return buildBasicFee(400.0);
            case ApplicationTypes.UMFULLINVALIDITY_KEY:
            case ApplicationTypes.EPFULLINVALIDITY_KEY:
                return buildBasicFee(450.0);
            case ApplicationTypes.PCISSUANCECERTIFICATE_KEY:
                return buildBasicFee(500.0);
//            case ApplicationTypes.EPTRANSLATIONEDIT_KEY:
//                return PtBuilder.buildTranslationFee(parameters, locale);
            case ApplicationTypes.PCCHANGE_KEY:
            case ApplicationTypes.PCCHANGEII_KEY:
                return PcBuilder.buildChangeFee(parameters);
            case ApplicationTypes.SVEFILING_KEY:
                return PtBuilder.buildSvefilingList(parameters);
            case ApplicationTypes.SPCEFILING_KEY:
                return PtBuilder.buildSpcefilingList(parameters);
            default:
                throw new Exception(messageSource.getMessage("error.unrecognized_type", null, locale));
        }
    }

    //    COMMON
    static List<Holder> fillHolders(Map<String, String> parameters) {
        List<Holder> holders = new ArrayList<>();

        int holdersCount = Integer.parseInt(parameters.get("holdersCount"));
        for (int i = 0; i < holdersCount; i++)
            holders.add(new Holder());
        return holders;
    }

    static List<Assignee> getAssignees(Map<String, String> parameters) {
        List<Assignee> assignees = new ArrayList<>();

        int assigneesCount = Integer.parseInt(parameters.get("assigneesCount"));
        for (int i = 0; i < assigneesCount; i++)
            assignees.add(new Assignee());
        return assignees;
    }

    //    MANUAL FEES
    private static List<Object> buildBasicFee(Double unitAmount) {
        Fee fee = new Fee();
        fee.setUnitAmount(unitAmount);
        fee.setQuantity(1);
        fee.setAmount(fee.getUnitAmount() * fee.getQuantity());
        FeeType feeType = new FeeType();
        feeType.setNameKey("basicFee");
        fee.setFeeType(feeType);
        ManualFee manualFee = new ManualFee();
        List<Fee> fees = new ArrayList<>();
        fees.add(fee);
        manualFee.setFees(fees);
        List<Object> objects = new ArrayList<>();
        objects.add(manualFee);
        return objects;
    }

}
