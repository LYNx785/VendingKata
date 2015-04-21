import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    public Product product;

    @Before
    public void setUp() throws Exception {
        product = new Product("A", "Snickers", 1.12, 5);
    }

    @Test
    public void testGetValues() {
        assertEquals("A", product.getSelector());
        assertEquals("Snickers", product.getName());
        assertEquals(0, Double.compare(1.12, product.getPrice()));
        assertEquals(5, product.getQuantity());
    }
}