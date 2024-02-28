package eu.ohim.sp.core.domain.converter.ol;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.other.OLeServiceApplication;
import eu.ohim.sp.filing.domain.ol.*;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;

/**
 * Created by Raya
 * 16.12.2019
 */
public class ObjectlessEservicesTransactionConverter extends DozerConverter<OLeServiceApplication, Transaction> implements MapperAware {

    private Mapper mapper;

    public ObjectlessEservicesTransactionConverter() {
        super(OLeServiceApplication.class, Transaction.class);
    }

    @Override
    public Transaction convertTo(OLeServiceApplication core, Transaction ext) {
        Transaction transaction = new Transaction();
        transaction.setTransactionHeader(new TransactionHeaderType());
        transaction.getTransactionHeader().setSenderDetails(new SenderDetailsType());
        transaction.getTransactionHeader().getSenderDetails().setRequestXSDVersion("0.01");
        transaction.getTransactionHeader().getSenderDetails().setLoginInformation(new LoginInformationType(core.getUser(), null, null));
        transaction.setObjectlessTransactionBody(new ArrayList<>());
        TransactionBody transactionBody = new TransactionBody(null, new TransactionContentDetails());
        transactionBody.getTransactionContentDetails().setTransactionCode(ApplicationBasicKind.OBJECTLESS_SERVICES_EFILING.value());
        transactionBody.getTransactionContentDetails().setTransactionData(new TransactionData());
        ObjectlessServicesApplicationType extApp = mapper.map(core, ObjectlessServicesApplicationType.class);
        extApp.setApplicationFormName(core.getApplicationType());
        extApp.setRequestSoftware(
            new RequestSoftwareType("e-Services", "0.01"));
        transactionBody.getTransactionContentDetails().getTransactionData().setObjectlessServiceApplication(extApp);
        transaction.getObjectlessTransactionBody().add(transactionBody);

        return transaction;
    }

    @Override
    public OLeServiceApplication convertFrom(Transaction ext, OLeServiceApplication core) {
        OLeServiceApplication app = mapper.map(
            ext.getObjectlessTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getObjectlessServiceApplication(),
                OLeServiceApplication.class);
        app.setApplicationType(ext.getObjectlessTransactionBody().get(0).getTransactionContentDetails()
            .getTransactionData().getObjectlessServiceApplication().getApplicationFormName());
        return app;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
