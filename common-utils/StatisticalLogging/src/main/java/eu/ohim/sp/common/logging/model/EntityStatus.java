package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public enum EntityStatus
{
    APPLICATION("Application"),
    REGISTRATION("Registration");

    private EntityStatus(final String text)
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
