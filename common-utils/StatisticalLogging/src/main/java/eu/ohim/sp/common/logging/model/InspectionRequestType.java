package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public enum InspectionRequestType
{
    COPY_OF_THE_APPLICAITON_FOR_A_COMMUNITY_TRADE_MARK("Copy of the Application for a Community Trade Mark");

    private InspectionRequestType(final String text)
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
