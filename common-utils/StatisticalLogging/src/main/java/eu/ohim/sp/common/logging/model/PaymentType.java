package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public enum PaymentType
{
    BANK_TRANSFER("Bank Transfer"),
    CURRENT_ACCOUNT("Current Account"),
    CREDIT_CARD("Credit Card");

    private PaymentType(final String text)
    {
        this.text = text;
    }

    private final String text;

    public String getText()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
