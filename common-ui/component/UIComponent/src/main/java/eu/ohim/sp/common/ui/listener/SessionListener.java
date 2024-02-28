package eu.ohim.sp.common.ui.listener;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.SingleRequestGuardInterceptor;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // TODO Auto-generated method stub
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {

        HttpSession session = sessionEvent.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());

        cleanUsedProvisionalNumbers(session, ctx);
        cleanSingleRequestLocks(session, ctx);
    }
    
    
    private void cleanUsedProvisionalNumbers(HttpSession session, ApplicationContext ctx){
    	FileServiceInterface fileService = ctx.getBean(FileServiceInterface.class);

        if (session.getAttribute("scopedTarget.usedProvisionalNumbers") != null
                && session.getAttribute("scopedTarget.usedProvisionalNumbers") instanceof ArrayList<?>) {
            ArrayList<String> usedProvisionalNumbers = (ArrayList<String>) session
                    .getAttribute("scopedTarget.usedProvisionalNumbers");

            for (String usedProvisionalNumber : usedProvisionalNumbers) {
                StoredFile file = new StoredFile();
                file.setDocumentId(usedProvisionalNumber);
                fileService.removeFile(file);
            }
        }
    }
    
    private void cleanSingleRequestLocks(HttpSession session, ApplicationContext ctx){
    	SingleRequestGuardInterceptor interceptor = (SingleRequestGuardInterceptor)ctx.getBean("guardInterceptor");
    	interceptor.removeLockForSessionId(session.getId());
    }

}
