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
	
	private SimpleDateFormat dateFormatter;
	
	public Formatter(){
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
	}
	
	public Formatter(String pattern){
		dateFormatter = new SimpleDateFormat(pattern);
	}
	
	public String formatMsg(Class<?> loggerID, Level currentLevel, String msg) {
		return dateFormatter.format(new Date()) + " [NAME=" + loggerID.getName()  + " LEVEL=" + currentLevel.name() + " MESSAGE= " + msg + "]";
	}
	
}
