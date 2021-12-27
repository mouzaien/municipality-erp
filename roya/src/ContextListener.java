import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.*;

public class ContextListener implements ServletContextListener {
	private static final Logger logger = LogManager.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg) {
		logger.info("contextInitialized..............................................................");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		logger.info("contextDestroyed................................................................");

	}
}