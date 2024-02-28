package eu.ohim.sp.core.domain.converter.ds;

import java.util.ArrayList;

import eu.ohim.sp.core.domain.design.DesignApplication;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.filing.domain.ds.LoginInformationType;
import eu.ohim.sp.filing.domain.ds.RequestSoftwareType;
import eu.ohim.sp.filing.domain.ds.SenderDetailsType;
import eu.ohim.sp.filing.domain.ds.Transaction;
import eu.ohim.sp.filing.domain.ds.TransactionBody;
import eu.ohim.sp.filing.domain.ds.TransactionContentDetails;
import eu.ohim.sp.filing.domain.ds.TransactionData;
import eu.ohim.sp.filing.domain.ds.TransactionHeaderType;


public class DesignEservicesTransactionConverter extends DozerConverter<DSeServiceApplication, Transaction> implements MapperAware
{
    private Mapper mapper;

    public DesignEservicesTransactionConverter()
    {
        super(DSeServiceApplication.class, Transaction.class);
    }

    /* Core to Filing */
    @Override
    public Transaction convertTo(DSeServiceApplication core, Transaction ext)
    {   	
        Transaction transaction = new Transaction();
        transaction.setTransactionHeader(new TransactionHeaderType());
        transaction.getTransactionHeader().setSenderDetails(new SenderDetailsType());
        transaction.getTransactionHeader().getSenderDetails().setRequestXSDVersion(TransactionInformation.DS_XSD_VERSION);
        transaction.getTransactionHeader().getSenderDetails().setLoginInformation(new LoginInformationType(core.getUser(), null, null));
        transaction.setDesignTransactionBody(new ArrayList<TransactionBody>());
        TransactionBody transactionBody = new TransactionBody(null, new TransactionContentDetails());
        transactionBody.getTransactionContentDetails().setTransactionCode(ApplicationBasicKind.DESIGN_SERVICES_EFILING.value());
        transactionBody.getTransactionContentDetails().setTransactionData(new TransactionData());

        core.consolidateDesigns();
        eu.ohim.sp.filing.domain.ds.DesignServicesApplicationType extApp = mapper.map(core, eu.ohim.sp.filing.domain.ds.DesignServicesApplicationType.class);
        core.divideDesigns();
        extApp.setApplicationFormName(core.getApplicationType());
        extApp.setRequestSoftware(
                new RequestSoftwareType(TransactionInformation.ES_REQUEST_SOFTWARE_NAME, TransactionInformation.REQUEST_SOFTWARE_VERSION));


        transactionBody.getTransactionContentDetails().getTransactionData().setDesignServicesApplication(extApp);
        transaction.getDesignTransactionBody().add(transactionBody);

        return transaction;
    }

    /* Filing to Core */
    @Override
    public DSeServiceApplication convertFrom(Transaction ext, DSeServiceApplication core)
    {
    	DSeServiceApplication app = mapper.map(
                ext.getDesignTransactionBody().get(0).getTransactionContentDetails().getTransactionData().getDesignServicesApplication(),
                DSeServiceApplication.class);
    	app.setApplicationType(ext.getDesignTransactionBody().get(0).getTransactionContentDetails()
    			.getTransactionData().getDesignServicesApplication().getApplicationFormName());
        app.divideDesigns();
    	return app;
    }

    @Override
    public void setMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }
}
