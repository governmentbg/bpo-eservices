package bg.duosoft.documentmanager.service;

/**
 * Created by Raya
 * 19.01.2021
 */
public interface ReceiptUtilityService {

    byte[] generateReceipt(String appId, String receiptType);
    String getModule();

}
