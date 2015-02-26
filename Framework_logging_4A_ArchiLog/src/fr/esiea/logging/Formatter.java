/**
 * 
 */
package fr.esiea.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author admin
 *
 */
public class Formatter {
	
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
	private String pattern = null;
	
	public Formatter(){
		
	}
	
	public Formatter(String pattern){
		this.pattern = pattern;
	}
	
	public String formatMsg(Class<?> loggerName, Level messageLevel, String msg) {
		if(pattern == null) {
			return "DATE=" + dateFormatter.format(new Date())
					+ " - [LOGGER_NAME=" + loggerName.getName() 
					+ " LEVEL_NUM=" + Integer.toString(messageLevel.getLevelValue())
					+ " LEVEL_NAME=" + messageLevel.name() 
					+ " MESSAGE= " + msg + "]";
		}
		
		String formattedMsg = "";
		formattedMsg = pattern.replaceAll("\\%\\(acstime\\)\\%", dateFormatter.format(new Date()));
		formattedMsg = formattedMsg.replaceAll("\\%\\(loggerName\\)\\%", loggerName.getName());
		formattedMsg = formattedMsg.replaceAll("\\%\\(levelNum\\)\\%", Integer.toString(messageLevel.getLevelValue()));
		formattedMsg = formattedMsg.replaceAll("\\%\\(levelName\\)\\%", messageLevel.name());
		formattedMsg = formattedMsg.replaceAll("\\%\\(message\\)\\%", msg);
		return formattedMsg;
	}
	
}