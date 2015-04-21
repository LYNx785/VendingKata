import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VendingMachineTest {

    public VendingMachine vendingMachine;
    public VendingMachine vendingMachineAdmin;

    @Before
    public void setUp() throws Exception {
        vendingMachine = new VendingMachine();
        vendingMachineAdmin = new VendingMachine("admin");
    }

    @Test
    public void testNewServiceMode() {
        assertTrue(vendingMachineAdmin.getServiceMode());
    }

    @Test
    public void testNewVendingMachine() {
        assertEquals(0, vendingMachine.getDepositedFunds().size());
    }

    @Test
    public void testAddFunds_ShouldChangeTotalFunds() {
        vendingMachine.addFunds(Currency.DIME);
        assertEquals(0, Double.compare(0.10, vendingMachine.sumDepositedFunds()));
    }

    @Test
    public void testCoinReturn_ZerosTotalFundsAndReturnsList() {
        List<Currency> expected = new ArrayList<Currency>();
        expected.add(Currency.DOLLAR);
        expected.add(Currency.QUARTER);
        expected.add(Currency.NICKLE);
        vendingMachine.addFunds(Currency.DOLLAR);
        vendingMachine.addFunds(Currency.QUARTER);
        vendingMachine.addFunds(Currency.NICKLE);
        assertEquals(0, Double.compare(1.30, vendingMachine.sumDepositedFunds()));

        assertEquals("[DOLLAR, QUARTER, NICKLE]", vendingMachine.coinReturn());
        assertEquals(0, Double.compare(0, vendingMachine.sumDepositedFunds()));
    }

    @Test
    public void testGetStartingCurrencyInBank() {
        assertEquals(4, vendingMachine.getBank().size());
        assertEquals(100, (int) vendingMachine.getBank().get(Currency.DOLLAR));
        assertEquals(100, (int) vendingMachine.getBank().get(Currency.QUARTER));
        assertEquals(100, (int) vendingMachine.getBank().get(Currency.DIME));
        assertEquals(100, (int) vendingMachine.getBank().get(Currency.NICKLE));
    }

    @Test
    public void testAddingFundsAddedToBank() {
        vendingMachine.addFunds(Currency.DIME);
        assertEquals(101, (int) vendingMachine.getBank().get(Currency.DIME));
    }

    @Test
    public void testClearDepositedFunds() {
        vendingMachine.addFunds(Currency.DIME);
        vendingMachine.clearDepositedFunds();
        assertEquals(0, vendingMachine.getDepositedFunds().size());
    }

    @Test
    public void testVendingProductWithExactChange() {
        vendingMachine.addFunds(Currency.QUARTER);
        vendingMachine.addFunds(Currency.QUARTER);
        vendingMachine.addFunds(Currency.QUARTER);
        vendingMachine.addFunds(Currency.QUARTER);
        assertEquals("GET-B", vendingMachine.vend("B"));
        assertEquals(29, vendingMachine.getInventory().getProductQuantity("B"));
    }

    @Test
    public void testVendingProductWithChangeNeeded() {
        vendingMachine.addFunds(Currency.DOLLAR);
        assertEquals("GET-York, change => [QUARTER, DIME]", vendingMachine.vend("A"));
    }

    @Test
    public void testMakeChange() {
        List<Currency> expected = new ArrayList<Currency>();
        expected.add(Currency.QUARTER);
        expected.add(Currency.DIME);

        vendingMachine.addFunds(Currency.DOLLAR);
        assertEquals(expected, vendingMachine.makeChange(0.65));
    }

    @Test
    public void testFormatsDoubleValues() {
        double formattedNumber = vendingMachine.format(0.49999999);
        assertEquals(0, Double.compare(0.50, formattedNumber));
    }

    @Test
    public void testUpdateItemNameInInventory() {
        assertEquals("York",vendingMachineAdmin.getInventory().getProductName("A"));
        vendingMachineAdmin.updateProductName("York Patties", "A");
        assertEquals("York Patties", vendingMachineAdmin.getInventory().getProductName("A"));
    }

    @Test
    public void testUpdateItemPriceInInventory() {
        assertEquals(0, Double.compare(0.65, vendingMachineAdmin.getInventory().getProductPrice("A")));
        vendingMachineAdmin.updateProductPrice(0.70, "A");
        assertEquals(0, Double.compare(0.70, vendingMachineAdmin.getInventory().getProductPrice("A")));
    }

    @Test
    public void testUpdateItemQuantityInInventory() {
        assertEquals(40, vendingMachineAdmin.getInventory().getProductQuantity("A"));
        vendingMachineAdmin.updateProductQuantity(400, "A");
        assertEquals(400, vendingMachineAdmin.getInventory().getProductQuantity("A"));
    }

    @Test
    public void testCannotUpdateItemNameInInventoryNonServiceMode() {
        assertEquals("York", vendingMachine.getInventory().getProductName("A"));
        vendingMachine.updateProductName("York Patties", "A");
        assertNotEquals("York Patties", vendingMachine.getInventory().getProductName("A"));
    }

    @Test
    public void testGetsInventoryAsStrings() {
        List<String> inventory = vendingMachine.getInventoryAsStrings();
        assertEquals(inventory.size(), vendingMachine.getInventory().getProducts().size());
        assertEquals("A : York", inventory.get(0));
    }

    @Test
    public void testGetsBankAsStrings() {
        List<String> bank = vendingMachine.getBankAsStrings();
        assertEquals(bank.size(), vendingMachine.getBank().size());
        assertEquals("DIME : 100",bank.get(0));
    }

    @Test
    public void testCanUpdateDollarQuantity() {
        assertEquals(100, (int) vendingMachineAdmin.getBank().get(Currency.DOLLAR));
        vendingMachineAdmin.updateCurrencyQuantity(Currency.DOLLAR, 200);
        assertEquals(200, (int) vendingMachineAdmin.getBank().get(Currency.DOLLAR));
    }
}