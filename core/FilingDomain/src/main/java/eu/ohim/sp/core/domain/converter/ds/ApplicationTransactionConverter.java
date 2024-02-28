package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.converter.TransactionSubCodeTypeConverter;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.filing.domain.ds.*;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;

/**
 * @author ionitdi
 */
public class ApplicationTransactionConverter extends DozerConverter<DesignApplication, Transaction> implements MapperAware
{
    private Mapper mapper;

    private TransactionSubCodeTypeConverter transactionSubCodeTypeConverter;

    public ApplicationTransactionConverter()
    {
        super(DesignApplication.class, Transaction.class);
        this.transactionSubCodeTypeConverter = new TransactionSubCodeTypeConverter();
    }

    @Override
    public Transaction convertTo(DesignApplication core, Transaction ext)
    {
        Transaction transaction = new Transaction();
        transaction.setTransactionHeader(new TransactionHeaderType());
        transaction.getTransactionHeader().setSenderDetails(new SenderDetailsType());
        transaction.getTransactionHeader().getSenderDetails().setRequestXSDVersion(TransactionInformation.DS_XSD_VERSION);
        transaction.getTransactionHeader().getSenderDetails().setLoginInformation(new LoginInformationType(core.getUser(), core.getUserEmail(), null));
        transaction.setDesignTransactionBody(new ArrayList<TransactionBody>());
        TransactionBody transactionBody = new TransactionBody(null, new TransactionContentDetails());
        transactionBody.getTransactionContentDetails().setTransactionCode(ApplicationBasicKind.DESIGN_EFILING.value());
        transactionBody.getTransactionContentDetails().setTransactionData(new TransactionData());
        transactionBody.getTransactionContentDetails().setTransactionSubCode((String) transactionSubCodeTypeConverter.convert(Boolean.class, core.getFastTrack()));

        eu.ohim.sp.filing.domain.ds.DesignApplication extApp = mapper.map(core, eu.ohim.sp.filing.domain.ds.DesignApplication.class);
        extApp.setDesignApplicationFormName(TransactionInformation.DS_FORM_NAME);
        extApp.setRequestSoftware(
                new RequestSoftwareType(TransactionInformation.REQUEST_SOFTWARE_NAME, TransactionInformation.REQUEST_SOFTWARE_VERSION));


        transactionBody.getTransactionContentDetails().getTransactionData().setDesignApplication(extApp);
        transaction.getDesignTransactionBody().add(transactionBody);

        return transaction;
    }

    @Override
    public DesignApplication convertFrom(Transaction ext, DesignApplication core)
    {
    	DesignApplication app = mapper.map(
                ext.getDesignTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getDesignApplication(),
                DesignApplication.class);
        app.setApplicationType(ext.getDesignTransactionBody().get(0).getTransactionContentDetails()
    			.getTransactionData().getDesignApplication().getDesignApplicationFormName());

        app.setFastTrack((Boolean)transactionSubCodeTypeConverter.convert(String.class, ext.getDesignTransactionBody().get(0).getTransactionContentDetails().getTransactionSubCode()));
        if(ext.getTransactionHeader() != null && ext.getTransactionHeader().getSenderDetails() != null && ext.getTransactionHeader().getSenderDetails().getLoginInformation() != null){
            app.setUserEmail(ext.getTransactionHeader().getSenderDetails().getLoginInformation().getLoginEmail());
            app.setUser(ext.getTransactionHeader().getSenderDetails().getLoginInformation().getLogin());
        }
        return app;
    }

    @Override
    public void setMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }
}
