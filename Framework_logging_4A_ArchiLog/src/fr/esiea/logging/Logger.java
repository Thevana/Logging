/**
 * 
 */
package fr.esiea.logging;

import java.util.ArrayList;

/**
 * @author admin
 *
 */
public class Logger {
	
	private Class<?> loggerID;
	private Level level = null;
	private Formatter formatter = null;
	private ArrayList<Handler> handlers;
	
	public Logger(){
		loggerID = null;
		handlers = new ArrayList<Handler>();
	}
	
	public Logger(Class<?> loggerID) {
		this.loggerID = loggerID;
		handlers = new ArrayList<Handler>();
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}
	
	public void addHandler(Handler handler) {
		handlers.add(handler);
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
		
		msg = formatter.formatMsg(loggerID, currentLevel, msg);
		dispatchToHandlers(currentLevel, msg);
	}
	
	private void dispatchToHandlers(Level currentLevel, String msg) {
		if(currentLevel.getLevelValue() >= level.getLevelValue()){
			for(int i = 0; i < handlers.size(); i++)
				handlers.get(i).proceed(msg);
		}
	}
	
}
