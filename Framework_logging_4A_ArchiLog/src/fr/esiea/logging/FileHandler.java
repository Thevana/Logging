/**
 * 
 */
package fr.esiea.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author admin
 *
 */
public class FileHandler extends Handler {
	
	protected File logFile;
	
	public FileHandler(String logFileCanonicalPath){
		logFile = new File(logFileCanonicalPath);
	}
	
	@Override
	public void proceed(String msg) {
		msg += "\n";
		FileWriter writer = null;
		try{
			writer = new FileWriter(logFile, true);
			writer.write(msg, 0, msg.length());
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
