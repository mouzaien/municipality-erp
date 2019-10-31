import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class ContextListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg) {
		logger.info("contextInitialized..............................................................");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		logger.info("contextDestroyed................................................................");

	}
}