package eu.ohim.sp.core.domain.converter.pt;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.filing.domain.pt.*;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;

/**
 * Created by Raya
 * 16.12.2019
 */
public class PatentEservicesTransactionConverter  extends DozerConverter<PTeServiceApplication, Transaction> implements MapperAware {

    private Mapper mapper;

    public PatentEservicesTransactionConverter() {
        super(PTeServiceApplication.class, Transaction.class);
    }

    @Override
    public Transaction convertTo(PTeServiceApplication core, Transaction ext) {
        Transaction transaction = new Transaction();
        transaction.setTransactionHeader(new TransactionHeaderType());
        transaction.getTransactionHeader().setSenderDetails(new SenderDetailsType());
        transaction.getTransactionHeader().getSenderDetails().setRequestXSDVersion("0.01");
        transaction.getTransactionHeader().getSenderDetails().setLoginInformation(new LoginInformationType(core.getUser(), null, null));
        transaction.setPatentTransactionBody(new ArrayList<>());
        TransactionBody transactionBody = new TransactionBody(null, new TransactionContentDetails());
        transactionBody.getTransactionContentDetails().setTransactionCode(ApplicationBasicKind.PATENT_SERVICES_EFILING.value());
        transactionBody.getTransactionContentDetails().setTransactionData(new TransactionData());
        eu.ohim.sp.filing.domain.pt.PatentServicesApplicationType extApp = mapper.map(core, eu.ohim.sp.filing.domain.pt.PatentServicesApplicationType.class);
        extApp.setApplicationFormName(core.getApplicationType());
        extApp.setRequestSoftware(
            new RequestSoftwareType("e-Services", "0.01"));
        transactionBody.getTransactionContentDetails().getTransactionData().setPatentServiceApplication(extApp);
        transaction.getPatentTransactionBody().add(transactionBody);

        return transaction;
    }

    @Override
    public PTeServiceApplication convertFrom(Transaction ext, PTeServiceApplication core) {
        PTeServiceApplication app = mapper.map(
            ext.getPatentTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getPatentServiceApplication(),
            PTeServiceApplication.class);
        app.setApplicationType(ext.getPatentTransactionBody().get(0).getTransactionContentDetails()
            .getTransactionData().getPatentServiceApplication().getApplicationFormName());
        return app;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
