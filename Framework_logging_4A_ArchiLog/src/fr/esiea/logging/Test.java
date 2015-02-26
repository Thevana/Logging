/**
 * 
 */
package fr.esiea.logging;

/**
 * @author admin
 *
 */
public class Test {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Test Logger */
		Logger logger = new Logger(Test.class);
		
		/* Test Level */
		logger.setLevel(Level.DEBUG);
		//logger.setLevel(Level.INFO);
		//logger.setLevel(Level.ERROR);
		
		/* Test Formatter */
		//logger.setFormatter(new Formatter());
		logger.setFormatter(new Formatter("DATE=%(acstime)% - [LOGGER_NAME=%(loggerName)% LEVEL_NUM=%(levelNum)% LEVEL_NAME=%(levelName)% MESSAGE= %(message)%]"));
		//logger.setFormatter(new Formatter("[LOGGER_NAME=%(loggerName)% LEVEL_NUM=%(levelNum)% LEVEL_NAME=%(levelName)% MESSAGE= %(message)%]"));
		//logger.setFormatter(new Formatter("DATE=%(acstime)% - [LEVEL_NUM=%(levelNum)% LEVEL_NAME=%(levelName)% MESSAGE= %(message)%]"));
		//logger.setFormatter(new Formatter("DATE=%(acstime)% - [LOGGER_NAME=%(loggerName)% LEVEL_NAME=%(levelName)% MESSAGE= %(message)%]"));
		//logger.setFormatter(new Formatter("DATE=%(acstime)% - [LOGGER_NAME=%(loggerName)% LEVEL_NUM=%(levelNum)% MESSAGE= %(message)%]"));
		//logger.setFormatter(new Formatter("DATE=%(acstime)% - [LOGGER_NAME=%(loggerName)% LEVEL_NUM=%(levelNum)% LEVEL_NAME=%(levelName)%]"));
		
		/* Test Handler */
		logger.addHandler(new StreamHandler());
		logger.addHandler(new FileHandler("C:\\Users\\admin\\Desktop\\LoggingTest\\logFile.log"));
		logger.addHandler(new RotatingFileHandler("C:\\Users\\admin\\Desktop\\LoggingTest\\logRotatingFile.log", 512));
		
		/* Execution des tests */
		logger.debug("Test log ESIEA : DEBUG");
		logger.info("Test log ESIEA : INFO");
		logger.error("Test log ESIEA : ERROR");
		
		/* Test RotatingFileHandler */
		logger.debug("Test log ESIEA : DEBUG");
		logger.info("Test log ESIEA : INFO");
		logger.error("Test log ESIEA : ERROR");
		logger.debug("Test log ESIEA : DEBUG");
		logger.info("Test log ESIEA : INFO");
		logger.error("Test log ESIEA : ERROR");
		logger.debug("Test log ESIEA : DEBUG");
		logger.info("Test log ESIEA : INFO");
		logger.error("Test log ESIEA : ERROR");
		
	}
	
}