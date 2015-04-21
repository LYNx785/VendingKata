import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static VendingMachine vendingMachine;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String input = "";

    public static void main (String[] args) throws IOException {
        System.out.println("VENDING MACHINE KATA!: ");
        System.out.println("Password: ");
        input = reader.readLine();

        if (input.equals("admin")) {
            vendingMachine = new VendingMachine("admin");
        } else {
            vendingMachine = new VendingMachine();
        }

        if (vendingMachine.getServiceMode()) {
            System.out.println("Service Mode = ADMIN");
            System.out.println();
        }

        while (!input.equals("exit")) {
            System.out.println("----Available Options:-----");
            System.out.println("  pay, vend, coin return   ");
            if (vendingMachine.getServiceMode()) { System.out.println("    mod item, mod bank     "); }
            System.out.println("---------------------------");
            System.out.println();

            input = reader.readLine();

            if (input.equals("pay")) {
                System.out.println("enter: dollar, quarter, dime, nickle or done)");
                while(!input.equals("done")) {
                    input = reader.readLine();
                    addCurrency(input);
                }
            }

            if (input.equals("vend")) {
                vendMe();
            }

            if (input.equals("coin return")) {
                System.out.println(vendingMachine.coinReturn());
            }

            if (input.equals("mod item") && vendingMachine.getServiceMode()) {
                editInventory();
            }

            if (input.equals("mod bank") && vendingMachine.getServiceMode()) {
                editCurrency();
            }
        }
    }

    private static void vendMe() throws IOException {
        System.out.println("----- Current Inventory -----");
        System.out.println(vendingMachine.getInventoryAsStrings());
        System.out.println("Select a Product:");
        input = reader.readLine();
        selectProduct(input);
    }

    private static void editInventory() throws IOException {
        String name;
        double price;
        int quantity;
        System.out.println("----- Update A Product -----");
        System.out.println(vendingMachine.getInventoryAsStrings());
        System.out.println("choose: name, price, quantity, or done");

        while(!input.equals("done")) {
            input = reader.readLine();
            if (input.equals("name")) {
                System.out.println("enter new name:");
                name = reader.readLine();
                vendingMachine.updateProductName(name, getProductSelection());
                break;
            }
            if (input.equals("price")) {
                System.out.println("enter new price:");
                price = Double.parseDouble(reader.readLine());
                vendingMachine.updateProductPrice(price, getProductSelection());
                break;
            }
            if (input.equals("quantity")) {
                System.out.println("enter new quantity:");
                quantity = Integer.parseInt(reader.readLine());
                vendingMachine.updateProductQuantity(quantity, getProductSelection());
                break;
            }
        }
    }

    private static String getProductSelection() throws IOException {
        System.out.println("choose product: A, B, or C");
        return reader.readLine();
    }

    private static void editCurrency() throws IOException {
        System.out.println("----- Update Currency -----");
        System.out.println(vendingMachine.getBankAsStrings());
        System.out.println("choose: dime, dollar, nickle, quarter, or done");

        while(!input.equals("done")) {
            input = reader.readLine();
            if (input.equals("dollar")) { vendingMachine.updateCurrencyQuantity(Currency.DOLLAR, getQuantitySelection()); }
            if (input.equals("quarter")) { vendingMachine.updateCurrencyQuantity(Currency.QUARTER, getQuantitySelection()); }
            if (input.equals("dime")) { vendingMachine.updateCurrencyQuantity(Currency.DIME, getQuantitySelection()); }
            if (input.equals("nickle")) { vendingMachine.updateCurrencyQuantity(Currency.NICKLE, getQuantitySelection()); }
        }
    }

    private static int getQuantitySelection() throws IOException {
        System.out.println("enter new quantity:");
        return Integer.parseInt(reader.readLine());
    }

    private static void selectProduct(String input) throws IOException {
        if (input.equals("A")) { System.out.println(vendingMachine.vend("A")); }
        if (input.equals("B")) { System.out.println(vendingMachine.vend("B")); }
        if (input.equals("C")) { System.out.println(vendingMachine.vend("C")); }
    }

    private static void addCurrency(String input) throws IOException {
        if (input.equals("dollar")) { vendingMachine.addFunds(Currency.DOLLAR); }
        if (input.equals("quarter")) { vendingMachine.addFunds(Currency.QUARTER); }
        if (input.equals("dime")) { vendingMachine.addFunds(Currency.DIME); }
        if (input.equals("nickle")) { vendingMachine.addFunds(Currency.NICKLE); }
        if (!input.equals("done")) { System.out.println("Total cash: " + vendingMachine.sumDepositedFunds()); }
    }
}
