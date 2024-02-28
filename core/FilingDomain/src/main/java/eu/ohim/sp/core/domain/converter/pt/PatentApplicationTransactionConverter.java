package eu.ohim.sp.core.domain.converter.pt;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.filing.domain.pt.*;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;

/**
 * Created by Raya
 * 05.07.2019
 */
public class PatentApplicationTransactionConverter extends DozerConverter<PatentApplication, Transaction> implements MapperAware {

    private Mapper mapper;

    public PatentApplicationTransactionConverter()
    {
        super(PatentApplication.class, Transaction.class);
    }


    @Override
    public Transaction convertTo(PatentApplication core, Transaction destination) {
        Transaction transaction = new Transaction();
        transaction.setTransactionHeader(new TransactionHeaderType());
        transaction.getTransactionHeader().setSenderDetails(new SenderDetailsType());
        transaction.getTransactionHeader().getSenderDetails().setLoginInformation(new LoginInformationType(core.getUser(), core.getUserEmail(), null));
        transaction.setPatentTransactionBody(new ArrayList<>());
        TransactionBody transactionBody = new TransactionBody(null, new TransactionContentDetails());
        transactionBody.getTransactionContentDetails().setTransactionCode(ApplicationBasicKind.PATENT_EFILING.value());
        transactionBody.getTransactionContentDetails().setTransactionData(new TransactionData());

        eu.ohim.sp.filing.domain.pt.PatentApplication extApp = mapper.map(core, eu.ohim.sp.filing.domain.pt.PatentApplication.class);
        transactionBody.getTransactionContentDetails().getTransactionData().setPatentApplication(extApp);
        transaction.getPatentTransactionBody().add(transactionBody);

        return transaction;
    }

    @Override
    public PatentApplication convertFrom(Transaction ext, PatentApplication destination) {
        PatentApplication app = mapper.map(
                ext.getPatentTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getPatentApplication(),
                PatentApplication.class);
        app.setApplicationType(ext.getPatentTransactionBody().get(0).getTransactionContentDetails()
                .getTransactionData().getPatentApplication().getApplicationFormName());
        if(ext.getTransactionHeader() != null && ext.getTransactionHeader().getSenderDetails() != null && ext.getTransactionHeader().getSenderDetails().getLoginInformation() != null){
            app.setUserEmail(ext.getTransactionHeader().getSenderDetails().getLoginInformation().getLoginEmail());
            app.setUser(ext.getTransactionHeader().getSenderDetails().getLoginInformation().getLogin());
        }

        return app;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
