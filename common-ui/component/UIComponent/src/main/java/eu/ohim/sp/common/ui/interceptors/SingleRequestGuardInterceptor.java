package eu.ohim.sp.common.ui.interceptors;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Makes sure that single user can execute one request at the time.
 *
 * This interceptor exists because of concurrency issues of {@link FlowScopeInterceptor}.
 *
 * {@link FlowScopeInterceptor} loads WebFlow session into request in preHandle method and saves them back in postHandle
 * which may result in loosing session data when more than one request is executed at the same time.
 *
 * SingleRequestGuardInterceptor creates a {@link Lock} for sessionId for the time of request processing
 *
 * @author Maciej Walkowiak
 */
public class SingleRequestGuardInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = Logger.getLogger(SingleRequestGuardInterceptor.class);

	private static Map<String, Lock> locks = new ConcurrentHashMap<String, Lock>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String sessionId = request.getSession().getId();
		
		LOG.debug("Trying to get lock: " + request.getRequestURI());
		if (!SingleRequestGuardInterceptor.locks.containsKey(sessionId)) {
			SingleRequestGuardInterceptor.locks.put(sessionId, new ReentrantLock());
		}

		SingleRequestGuardInterceptor.locks.get(sessionId).lock();

		LOG.debug("Got the lock: " + request.getRequestURI());
		LOG.debug("Locked request processing for sessionId: " + sessionId);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String sessionId = request.getSession().getId();

		if (SingleRequestGuardInterceptor.locks.containsKey(sessionId)) {
			LOG.debug("Lock released: " + request.getRequestURI());
			LOG.debug("Unlocking request processing for sessionId: " + sessionId);

			SingleRequestGuardInterceptor.locks.get(sessionId).unlock();
		}
	}
	
	/*
	 * Cleanup to gc unused locks
	 */
	public void removeLockForSessionId(String sessionId){
		if (SingleRequestGuardInterceptor.locks.containsKey(sessionId)) {
			SingleRequestGuardInterceptor.locks.remove(sessionId);
		}
	}
}
