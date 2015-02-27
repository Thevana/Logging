/**
 * 
 */
package fr.esiea.logging;

import java.io.FileNotFoundException;

/**
 * @author admin
 *
 */
public class LoggerFactory {

	public static Logger getLogger(Class<?> loggerName) {
		try {
			return new Logger(loggerName);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
