package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public enum EntityType
{
    CTM("CTM");

    private EntityType(final String text)
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
