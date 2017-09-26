/**
 * 
 */
package app;

import java.io.InputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Jay Patel
 *
 */
public class StockSelectedTest {

	private static StockSelector selector ;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		selector = new StockSelector();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		InputStream is = getClass().getClassLoader().getResource("./AAPL.html").openStream();
		Document d = Jsoup.parse(is,"UTF-8","");
		selector.htmlDocument = d;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link app.StockSelector}.
	 */
	@Test
	public final void testStockSelectorAPI() {
		
		assertThat(selector.getActualPrice()).isGreaterThan(-1);
		assertThat(selector.getStockName()).isNotNull();
		
		try {
			assertThat(selector.getChangeValue()).isNotNull();
			assertThat(selector.getChangePercent()).isNotNull();
		} catch (Exception e) {
			fail("Exception" + e.getMessage());
		}
			
	}	
	

}
