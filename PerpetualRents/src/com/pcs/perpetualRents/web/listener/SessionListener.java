package com.pcs.perpetualRents.web.listener;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pcs.perpetualRents.common.bean.ApplicationSession;
import com.pcs.perpetualRents.enm.SessionStatus;
import com.pcs.perpetualRents.manager.ApplicationSessionManager;
import com.pcs.perpetualRents.manager.impl.ApplicationSessionManagerImpl;

public class SessionListener implements HttpSessionListener {

	private static final Logger logger = Logger.getLogger(SessionListener.class.getName());
	
	public void sessionCreated(HttpSessionEvent sessionEvent) {
	   try {
		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   ServletRequestAttributes  obj = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession  session  = sessionEvent.getSession();
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
			ApplicationSessionManager sessionManager = (ApplicationSessionManager) ctx.getBean(ApplicationSessionManagerImpl.class);
			
			if(obj != null){
				logger.log(Level.INFO, "Session Created : " + session.getId() + " from IP [" + obj.getRequest().getRemoteAddr() + "]");
				if(authentication != null){
					String userName = authentication.getName();
					if(!userName.equalsIgnoreCase("anonymousUser")){
						ApplicationSession sessionObj = new ApplicationSession(authentication.getName(), session.getId(), obj.getRequest().getRemoteAddr());
						sessionManager.createSession(sessionObj);
					}
				}
				logger.log(Level.INFO,""+obj.getRequest());
		}else{
				logger.log(Level.INFO, "Session Created : " + session.getId());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage() + " - " + e.getCause());
		}
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		
		HttpSession session = sessionEvent.getSession();
		String sessionIdentity = session.getId();
		SessionStatus status ;
		String logoutAttempt = (String) session.getAttribute("logoutAttempt");
		if(logoutAttempt != null && logoutAttempt.length() > 0 && logoutAttempt.equalsIgnoreCase("applicationLogout")){
			status = SessionStatus.APPLICATION_DESTROYED;
		}else if(logoutAttempt != null && logoutAttempt.length() > 0 && logoutAttempt.equalsIgnoreCase("userLogout")){
			status = SessionStatus.USER_EVENT;
		}else{
			status = SessionStatus.AUTO_DESTROYED;
		}
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		ApplicationSessionManager sessionManager = (ApplicationSessionManager) ctx.getBean(ApplicationSessionManagerImpl.class);
		try 
			{
			sessionManager.destroySession(sessionIdentity, session, status);
			logger.log(Level.INFO, "Session Destroyed : [" + sessionIdentity + "]");
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}

}
