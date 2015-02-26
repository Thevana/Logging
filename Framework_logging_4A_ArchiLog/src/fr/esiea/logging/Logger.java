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
	
	public void manageMsg(Level messageLevel, String msg){
		if(level == null) {
			System.out.println("\nLe niveau du logger 'level' n\'est pas renseigné !\n'level' fixé automatiquement à 'level.DEBUG'.\n");
			level = Level.DEBUG;
		}
		
		if(formatter == null) {
			System.out.println("\nLe formateur du logger 'formatter' n\'est pas renseigné !\n'formatter' fixé automatiquement à un formateur par défaut.\n");
			formatter = new Formatter();
		}
		
		if(handlers.size() == 0) {
			System.out.println("\nAucun cible 'handlers' n'est renseigné pour les sorties du logger !\nAjout automatique d'un 'StreamHandler' par défaut.\n");
			handlers.add(new StreamHandler());
		}
		
		msg = formatter.formatMsg(loggerName, messageLevel, msg);
		dispatchToHandlers(messageLevel, msg);
	}
	
	private void dispatchToHandlers(Level messageLevel, String msg) {
		if(messageLevel.getLevelValue() >= level.getLevelValue()){
			for(int i = 0; i < handlers.size(); i++)
				handlers.get(i).proceed(msg);
		}
	}
	
	private void readProperties() {
		FileInputStream input = null;
		Properties prop = new Properties();
		try {
			input = new FileInputStream(new File("logging.properties"));
			// chargement du fichier properties
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
					System.out.println("\nLe niveau du logger 'level' n\'est pas renseigné !\n'level' fixé automatiquement à 'level.DEBUG'.\n");
					level = Level.DEBUG;
				}
				
				//set formatter
				if((prop.getProperty("logger.formatter")).contains(",")) {
					String[] formatterInfos = (prop.getProperty("logger.formatter")).split(" *, *");
					if(formatterInfos.length == 2 && (formatterInfos[0]).equals("Formatter")) {
						formatter = new Formatter(formatterInfos[1]);
					}
					else {
						System.out.println("\nLe formateur du logger 'formatter' est mal renseigné !\n'formatter' fixé automatiquement à un formateur par défaut.\n");
						formatter = new Formatter();
					}
				}
				else {
					formatter = new Formatter();
				}
				
				//add handlers
				for(int i = 1; i <= Integer.parseInt(prop.getProperty("logger.handlers.count")); i++) {
					String handler = prop.getProperty("logger.handler" + i);
					if(handler.equals("StreamHandler")){
						handlers.add(new StreamHandler());
					}
					else {
						if(handler.contains(",")) {
							String[] handlerInfos = handler.split(" *, *");
							if(handlerInfos.length == 2 && (handlerInfos[0]).equals("FileHandler")) {
								handlers.add(new FileHandler(handlerInfos[1]));
							}
							else if(handlerInfos.length == 3 && (handlerInfos[0]).equals("RotatingFileHandler")) {
								handlers.add(new RotatingFileHandler(handlerInfos[1], Integer.parseInt(handlerInfos[2])));
							}
							else {
								if(handlers.size() == 0) {
									System.out.println("\nAucun cible 'handlers' n'est renseigné (ou est mal renseigné) pour les sorties du logger !\nAjout automatique d'un 'StreamHandler' par défaut.\n");
									handlers.add(new StreamHandler());
								}
							}
						}
						else{
							if(handlers.size() == 0) {
								System.out.println("\nAucun cible 'handlers' n'est renseigné (ou est mal renseigné) pour les sorties du logger !\nAjout automatique d'un 'StreamHandler' par défaut.\n");
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