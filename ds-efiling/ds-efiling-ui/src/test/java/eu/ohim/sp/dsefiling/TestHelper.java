package eu.ohim.sp.dsefiling;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelper
{

    public static <N> Answer<List<N>> setupDummyListAnswer(N... values)
    {
        final List<N> someList = new ArrayList<N>();

        someList.addAll(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<List<N>>()
        {
            public List<N> answer(InvocationOnMock invocation) throws Throwable
            {
                return someList;
            }
        };
        return answer;
    }

}
