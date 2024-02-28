package eu.ohim.sp.core.report.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Raya
 * 11.01.2021
 */
public class CustomTemplateExceptionHandler implements TemplateExceptionHandler {
    private static final Logger logger = Logger.getLogger(CustomTemplateExceptionHandler.class);

    public void handleTemplateException(TemplateException te, Environment env, java.io.Writer out)
        throws TemplateException {
        try {
            out.write("<span style='color:red'>error in receipt</span>");
            logger.error(te);
        } catch (IOException e) {
            throw new TemplateException("Failed to print error message. Cause: " + e, env);
        }
    }

}
