import junit.framework.*;
import com.walmart.utils.PropertyReader;

public class TestPropertyReader extends TestCase {
    
    public void testInitializeProperties() {
    	PropertyReader.initializeProperties();
    	assertNotNull(PropertyReader.URL);
    	assertNotNull(PropertyReader.API_KEY);
    	assertNotNull(PropertyReader.DEBUG);
    }
}
