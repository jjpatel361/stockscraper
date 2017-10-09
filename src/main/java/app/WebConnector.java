/**
 * 
 */
package app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Jay Patel
 *
 */
public class WebConnector {
	
	public static Document fetchHTMLDocument(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
