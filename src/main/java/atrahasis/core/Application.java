package atrahasis.core;

import atrahasis.core.configurator.BasicConfigurator;
import atrahasis.core.configurator.IConfigurator;
import atrahasis.core.exception.MapApplicationException;
import atrahasis.core.logging.Logging;
import atrahasis.core.logging.LoggingSetter;
import atrahasis.core.manager.ApplicationManager;
import atrahasis.core.network.Request;
import atrahasis.core.network.Response;
import atrahasis.core.util.ArgsAnalyzer;

/**
 * 
 * Application is the facade that the user will use to interact with the
 * framework. This facade has a direct communication with the ApplicationManager
 * instance, this instance is the one that will do all the heavy work.
 * 
 */
public class Application{
	
	public static void launchApp() {
		Application.launchApp(new BasicConfigurator());
	}
	
	public static void launchApp(String[] args) {
		Application.launchApp(new BasicConfigurator(), args);
	}
	
	public static void launchApp(IConfigurator configurator) {
		Application.launchApp(configurator, new String[] {});
	}
	
	public static void launchApp(IConfigurator configurator, String[] args) {
		try {
			new LoggingSetter().setLogging();
			new ArgsAnalyzer(args).execute();
			long init = System.currentTimeMillis();
			ApplicationManager.initializeInstance(configurator);
			long end = System.currentTimeMillis();
			Logging.debug(String.format("System mapped in %d", end-init));
			ApplicationManager.getInstance().navigate("/");

		} catch (MapApplicationException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Response navigate(String url) {
		return ApplicationManager.getInstance().navigate(url);
	}
	
	public static Response navigate(String url, Request request) {
		return ApplicationManager.getInstance().navigate(url, request);
	}
	
	public static void set(String name, Object value) {
		ApplicationManager.getInstance().put(name, value);
	}
	
	public static Object get(String name) {
		return ApplicationManager.getInstance().get(name);
	}
	
	public static Object remove(String name) {
		return ApplicationManager.getInstance().remove(name);
	}
	
	public static boolean hasKey(String key) {
		return ApplicationManager.getInstance().containsKey(key);
	}

	
}
