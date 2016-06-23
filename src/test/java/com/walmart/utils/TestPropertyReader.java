import junit.framework.*;
import com.walmart.utils.PropertyReader;

public class TestPropertyReader extends TestCase {
    
    public void testInitializeProperties() {
        PropertyReader.initializeProperties();
        assertNotNull(PropertyReader.API_URL);
        assertNotNull(PropertyReader.API_KEY);
        assertNotNull(PropertyReader.SEARCH_ENDPOINT);
        assertNotNull(PropertyReader.RECOMMENDATION_ENDPOINT);
        assertNotNull(PropertyReader.REVIEW_ENDPOINT);
        assertNotNull(PropertyReader.DEBUG);
        assertNotNull(PropertyReader.POSITIVE_KEYWORDS);
        assertNotNull(PropertyReader.NEGATIVE_KEYWORDS);
    }
}
