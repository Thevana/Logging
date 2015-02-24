/**
 * 
 */
package fr.esiea.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author admin
 *
 */
public class Logger {
	
	private Class<?> loggerName;
	private Level level = null;
	private Formatter formatter = null;
	private ArrayList<Handler> handlers;
	private boolean codePriority = true;
	
	public Logger(Class<?> loggerName) {
		this.loggerName = loggerName;
		handlers = new ArrayList<Handler>();
		readProperties();
	}

	public void setLevel(Level level) {
		if(codePriority) {
			this.level = level;
		}
	}
	
	public void setFormatter(Formatter formatter) {
		if(codePriority) {
			this.formatter = formatter;
		}
	}
	
	public void addHandler(Handler handler) {
		if(codePriority) {
			handlers.add(handler);
		}
	}
	
	public void debug(String msg) {
		manageMsg(Level.DEBUG, msg);
	}
	
	public void info(String msg) {
		manageMsg(Level.INFO, msg);
	}
	
	public void error(String msg) {
		manageMsg(Level.ERROR, msg);
	}
	
	public void manageMsg(Level currentLevel, String msg){
		if(level == null)
			level = Level.DEBUG;
		
		if(formatter == null)
			formatter = new Formatter();
		
		if(handlers.size() == 0)
			handlers.add(new StreamHandler());
		
		msg = formatter.formatMsg(loggerName, currentLevel, msg);
		dispatchToHandlers(currentLevel, msg);
	}
	
	private void dispatchToHandlers(Level currentLevel, String msg) {
		if(currentLevel.getLevelValue() >= level.getLevelValue()){
			for(int i = 0; i < handlers.size(); i++)
				handlers.get(i).proceed(msg);
		}
	}
	
	private void readProperties() {
		FileInputStream input = null;
		Properties prop = new Properties();
		try {
			input = new FileInputStream(new File("logging.properties"));
			// charger le fichier properties
			prop.load(input);
			
			if(prop.getProperty("logger.priority").equals("conf")) {
				codePriority = false;
				//set level
				if(prop.getProperty("logger.level").equals("DEBUG")) {
					level = Level.DEBUG;
				}
				else if(prop.getProperty("logger.level").equals("INFO")) {
					level = Level.INFO;
				}
				else if(prop.getProperty("logger.level").equals("ERROR")) {
					level = Level.ERROR;
				}
				else {
					level = Level.DEBUG;
				}
				
				//set formatter
				String[] formatterInfos = (prop.getProperty("logger.formatter")).split(" *, *");
				if(formatterInfos.length == 2){
					formatter = new Formatter(formatterInfos[1]);
				}
				else{
					formatter = new Formatter();
				}
				
				//set handlers
				for(int i = 1; i <= Integer.parseInt(prop.getProperty("logger.handlers.count")); i++) {
					String handler = prop.getProperty("logger.handler" + i);
					if(handler.equals("StreamHandler")){
						handlers.add(new StreamHandler());
					}
					else {
						String[] handlerInfos = handler.split(" *, *");
						if(handlerInfos.length == 2){
							if((handlerInfos[0]).equals("FileHandler")) {
								handlers.add(new FileHandler(handlerInfos[1]));
							}
							else if((handlerInfos[0]).equals("RotatingFileHandler")) {
								handlers.add(new RotatingFileHandler(handlerInfos[1]));
							}
							else {
								if(handlers.size() == 0) {
									handlers.add(new StreamHandler());
								}
							}
						}
						else{
							if(handlers.size() == 0) {
								handlers.add(new StreamHandler());
							}
						}
					}
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}