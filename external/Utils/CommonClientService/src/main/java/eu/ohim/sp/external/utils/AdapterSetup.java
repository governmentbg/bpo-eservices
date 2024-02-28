package eu.ohim.sp.external.utils;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * Created by marcoantonioalberoalbero on 16/9/16.
 */
public class AdapterSetup implements Serializable {

    private static final String ADAPTER_NAME = "ADAPTER_NAME";

    public static class Trademark {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "trademark");
            return context.proceed();
        }
    }

    public static class Design {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "design");
            return context.proceed();
        }
    }

    public static class Application {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "application");
            return context.proceed();
        }
    }

    public static class Nice {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "classification");
            return context.proceed();
        }
    }

    public static class Locarno {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "locarnoClassification");
            return context.proceed();
        }
    }

    public static class BPOClassification {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "bpoClassification");
            return context.proceed();
        }
    }

    public static class Payment {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "payment");
            return context.proceed();
        }
    }

    public static class Address {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "address");
            return context.proceed();
        }
    }

    public static class Designer {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "designer");
            return context.proceed();
        }
    }

    public static class Inventor {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "inventor");
            return context.proceed();
        }
    }

    public static class Applicant {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "applicant");
            return context.proceed();
        }
    }

    public static class Representative {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "representative");
            return context.proceed();
        }
    }

    public static class User {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "user");
            return context.proceed();
        }
    }

    public static class BackOffice {
        @AroundInvoke
        public Object adapterEnabled(InvocationContext context) throws Exception {
            /** The Constant ADAPTER_NAME. Used for deciding if the adapter should work or not */
            context.getContextData().put(ADAPTER_NAME, "backoffice");
            return context.proceed();
        }
    }


}
