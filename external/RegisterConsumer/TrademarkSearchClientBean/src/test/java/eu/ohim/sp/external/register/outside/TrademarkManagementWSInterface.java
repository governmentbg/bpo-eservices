package eu.ohim.sp.external.register.outside;

import eu.ohim.sp.external.domain.trademark.TradeMark;
import eu.ohim.sp.external.ws.exception.TradeMarkFaultException;
import eu.ohim.sp.external.domain.trademark.TradeMark;

import java.util.List;

public interface TrademarkManagementWSInterface {

    /**
     * Gets the trade mark.
     * 
     * @param office the office
     * @param tradeMarkId the trade mark id
     * @return the trade mark
     */
    public TradeMark getTradeMark(String office, String tradeMarkId) throws TradeMarkFaultException;
    
    
    public TradeMark getInternationalTradeMark(String office, String tradeMarkId, Boolean extraImport) throws TradeMarkFaultException;
    /**
     * Gets the trade mark autocomplete.
     * 
     * @param office the office
     * @param text the text
     * @param numberOfResults the number of results
     * @return json with the result of the autocomplete search
     */
    public String getTradeMarkAutocomplete(String office, String text, int numberOfResults) throws TradeMarkFaultException;

    /**
     * Gets the preclearance report.
     * 
     * @param office the office
     * @param trademark the trademark
     * @return the trademark list preclearance report
     */
    public List<TradeMark> getPreclearanceReport(String office, TradeMark trademark) throws TradeMarkFaultException;
}
