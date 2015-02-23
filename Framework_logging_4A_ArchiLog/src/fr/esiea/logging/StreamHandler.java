/**
 * 
 */
package fr.esiea.logging;

/**
 * @author admin
 *
 */
public class StreamHandler extends Handler {
	
	@Override
	public void proceed(String msg) {
		System.out.println(msg);
	}
	
}
