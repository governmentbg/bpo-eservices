package eu.ohim.sp.dsefiling.ui.filters;

import eu.ohim.sp.dsefiling.ui.exceptions.InternalServerErrorException;
import eu.ohim.sp.dsefiling.ui.exceptions.NoSuchConversationException;
import eu.ohim.sp.dsefiling.ui.exceptions.PageNotFoundException;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;

/**
 * @author ionitdi
 */
public class ErrorAwareRequestWrapperTest
{
    ErrorAwareRequestWrapper errorAwareRequestWrapper;


    @Before
    public void setUp()
    {
        HttpServletResponse response = mock(HttpServletResponse.class);
        errorAwareRequestWrapper = new ErrorAwareRequestWrapper(response);
    }

    @Test(expected = PageNotFoundException.class)
    public void sendErrorTest1() throws IOException
    {
        errorAwareRequestWrapper.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test(expected = InternalServerErrorException.class)
    public void sendErrorTest2() throws IOException
    {
        errorAwareRequestWrapper.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test(expected = NoSuchConversationException.class)
    public void sendErrorTest3() throws IOException
    {
        errorAwareRequestWrapper.sendError(902);
    }
}
