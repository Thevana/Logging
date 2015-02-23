/**
 * 
 */
package fr.esiea.logging;

/**
 * @author admin
 *
 */
public enum Level {
	
	DEBUG(1), INFO(2), ERROR(3);
	
	private int levelValue;
	
	private Level(int levelValue){
		this.levelValue = levelValue;
	}
	
	public int getLevelValue(){
		return levelValue;
	}
	
}
