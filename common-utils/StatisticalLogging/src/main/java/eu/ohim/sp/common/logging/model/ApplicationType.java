package eu.ohim.sp.common.logging.model;

/**
 * @author ionitdi
 */
public enum ApplicationType
{

    CTM_EFILING_NORMAL_FORM("CTM e-filing NORMAL FORM"),
    CTM_EFILING_WIZARD_FORM("CTM e-filing WIZARD FORM"),
    DS_EFILING_NORMAL_FORM("DS e-filing NORMAL FORM"),
    DS_EFILING_WIZARD_FORM("DS e-filing WIZARD FORM"),    
    OTHER("e-filing OTHER");

    private ApplicationType(final String text)
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
