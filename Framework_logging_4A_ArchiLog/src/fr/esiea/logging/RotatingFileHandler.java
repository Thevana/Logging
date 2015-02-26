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
	
	private long fileMaxSize;
	
	public RotatingFileHandler(String logFileCanonicalPath, int fileMaxSize){
		super(logFileCanonicalPath);
		this.fileMaxSize = fileMaxSize;
	}
	
	@Override
	public void proceed(String msg) {
		if(logFile.exists()){//Si le fichier existe
			String logFileCanonicalPath;
			if(logFile.length() > fileMaxSize){
				//A chaque fois que ce fichier dépasse la taille maximale
				try {
					logFileCanonicalPath = logFile.getCanonicalPath();
					if(logFile.delete()) { //On le supprime
						logFile = new File(logFileCanonicalPath); //Puis on le recrée vide
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		super.proceed(msg);
	}
	
}
