package eu.ohim.sp.core.application.provided;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by marcoantonioalberoalbero on 24/10/16.
 */
@ApplicationPath("/")
public class ApplicationProvider extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ApplicationResourceProvider.class);
        return classes;
    }
}
