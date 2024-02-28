package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public enum ActionType
{

    VISIT("Visit"),
    TRANSACTIONS_DONE("Transactions done"),
    DRAFTS_SAVED("Drafts saved"),
    TEST("Test"),
    TRANSACTIONS_FAILED("Transactions failed");

    private ActionType(final String text)
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