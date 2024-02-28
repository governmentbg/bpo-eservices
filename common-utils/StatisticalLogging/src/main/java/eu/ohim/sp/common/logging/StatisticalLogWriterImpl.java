package eu.ohim.sp.common.logging;

/**
 * @author ionitdi
 */
public class StatisticalLogWriterImpl implements StatisticalLogWriter
{

    @Override
    public void writeLine(String line)
    {
        System.out.println(line);
    }
}
