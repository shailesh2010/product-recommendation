import junit.framework.*;
import com.walmart.utils.URLUtils;
import com.walmart.utils.PropertyReader;


public class TestURLUtils extends TestCase{

	public void testGetAPIURL() {
		assertEquals("http://api.walmartlabs.com/v1/"+PropertyReader.SEARCH_ENDPOINT
			          +"?apiKey="+PropertyReader.API_KEY+"&format=json&numItems=1&query=hello", 
			          URLUtils.getAPIURL("search", "hello"));
	}
}