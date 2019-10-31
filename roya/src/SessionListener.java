import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import utilities.Utils;

public class SessionListener implements HttpSessionListener {
	protected static final Logger logger = Logger.getLogger(SessionListener.class);
	private static int activeSessions;

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		activeSessions++;
		logger.info("session created by user id :" + "Utils.findCurrentUser().getUserjob() "+"   - total active sessions: " + activeSessions );
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		activeSessions--;
		//event.getSession().invalidate();
		logger.info("session destroyed - total active sessions: " + activeSessions);
	}

}