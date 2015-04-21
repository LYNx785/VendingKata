import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    public Inventory inventory;
    public Product andes;

    @Before
    public void setUp() throws Exception {
        inventory = new Inventory();

        andes = new Product("A", "Andes", 0.75, 3);
        inventory.addProduct(andes);
    }

    @Test
    public void testCanAddProduct() {
        assertEquals(1, inventory.getProducts().size());
    }

    @Test
    public void testCanGetProductNameBySelector() {
        assertEquals("Andes", inventory.getProductName("A"));
    }

    @Test
    public void testCanGetProductPriceBySelector() {
        assertEquals(0, Double.compare(0.75, inventory.getProductPrice("A")));
    }

    @Test
    public void testCanGetProductQuantityBySelector() {
        assertEquals(3, inventory.getProductQuantity("A"));
    }

    @Test
    public void testCanHandleNullGetProductNameBySelector() {
        assertEquals("No Product", inventory.getProductName("Z"));
    }

    @Test
    public void testCanHandleNullGetProductPriceBySelector() {
        assertEquals(0, Double.compare(0, inventory.getProductPrice("Z")));
    }

    @Test
    public void testCanHandleNullGetProductQuantityBySelector() {
        assertEquals(0, inventory.getProductQuantity("Z"));
    }

    @Test
    public void testCanReduceProductQuantity() {
        inventory.decrementQuantity("A");
        assertEquals(2, inventory.getProductQuantity("A"));
    }

    @Test
    public void testCanUpdateProductName() {
        inventory.setProductName("York Patties", "A");
        assertEquals("York Patties", inventory.getProductName("A"));
    }

    @Test
    public void testCanUpdateProductPrice() {
        inventory.setProductPrice(1.15, "A");
        assertEquals(0, Double.compare(1.15, inventory.getProductPrice("A")));
    }

    @Test
    public void testCanUpdateProductQuantity() {
        inventory.setProductQuantity(200, "A");
        assertEquals(200, inventory.getProductQuantity("A"));
    }
}