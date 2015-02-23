/**
 * 
 */
package fr.esiea.logging;

import java.io.File;
import java.io.IOException;

/**
 * @author admin
 *
 */
public class RotatingFileHandler extends FileHandler {
	
	private long fileMaxSize = 512;
	
	public RotatingFileHandler(){
		super("defaultRotatingFile.log");
	}
	
	public RotatingFileHandler(String logFileAbsolutePath){
		super(logFileAbsolutePath);
	}
	
	public RotatingFileHandler(String logFileAbsolutePath, int fileMaxSize){
		super(logFileAbsolutePath);
		this.fileMaxSize = fileMaxSize;
	}
	
	@Override
	public void proceed(String msg) {
		if(logFile.exists()){
			String logFileCanonicalPath;
			if(logFile.length() > fileMaxSize){
				try {
					logFileCanonicalPath = logFile.getCanonicalPath();
					if(logFile.delete())
						logFile = new File(logFileCanonicalPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		super.proceed(msg);
	}
	
}
