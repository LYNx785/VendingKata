import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class VendingMachine {
    private List<Currency> depositedFunds;
    private Map<Currency, Integer> bank = new HashMap<Currency, Integer>();
    private Inventory inventory = new Inventory();
    private boolean serviceMode = false;

    VendingMachine(String password) {
        this();

        if (password.equals("admin")) {
            serviceMode = true;
        }
    }

    public boolean getServiceMode() {
        return serviceMode;
    }

    VendingMachine() {
        depositedFunds = new ArrayList<Currency>();

//        inventory = new Inventory();
        inventory.addProduct(new Product("A", "York", 0.65, 40));
        inventory.addProduct(new Product("B", "Snickers", 1.00, 30));
        inventory.addProduct(new Product("C", "MilkyWay", 1.50, 20));

//        bank = new HashMap<Currency, Integer>();
        bank.put(Currency.DOLLAR, 100);
        bank.put(Currency.QUARTER, 100);
        bank.put(Currency.DIME, 100);
        bank.put(Currency.NICKLE, 100);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<String> getInventoryAsStrings() {
        List<String> inventory = new ArrayList<String>();
        for (Product product : getInventory().getProducts()) {
            inventory.add(product.getSelector() + " : " + product.getName());
        }
        return inventory;
    }

    public List<Currency> getDepositedFunds() {
        return depositedFunds;
    }

    public Map<Currency, Integer> getBank() {
        return bank;
    }

    public List<String> getBankAsStrings() {
        List<String> bank = new ArrayList<String>();
        for (Map.Entry<Currency, Integer> currency : getBank().entrySet()) {
            bank.add(currency.getKey() + " : " + currency.getValue());
        }
        java.util.Collections.sort(bank);
        return bank;
    }

    public void addFunds(Currency funds) {
        depositedFunds.add(funds);
        toBank(funds);
    }

    public double sumDepositedFunds() {
        double total = 0;
        for (Currency currency : depositedFunds) {
            total += currency.getValue();
        }
        return format(total);
    }

    public void clearDepositedFunds() {
        depositedFunds = new ArrayList<Currency>();
    }

    public String coinReturn() {
        List<Currency> coins = getDepositedFunds();

        for (Currency currency : coins) {
            fromBank(currency);
        }

        clearDepositedFunds();

        if (coins.size() > 0) {
            return coins.toString();
        } else {
            return "Nothing to return";
        }
    }

    public String vend(String product) {
        List<Currency> change;
        if (isProductOutOfStock(product)) return "Out of stock";
        if (sumDepositedFunds() >= getInventory().getProductPrice(product)) {
            getInventory().decrementQuantity(product);
            change = makeChange(getInventory().getProductPrice(product));
            clearDepositedFunds();
            if (change.size() == 0) {
                return "GET-" + product;
            } else {
                return "GET-" + getInventory().getProductName(product) + ", change => " + change.toString();
            }
        } else {
            return "Not enough money!";
        }
    }

    public List<Currency> makeChange(double productPrice) {
        List<Currency> change = new ArrayList<Currency>();
        double leftToChange = format(sumDepositedFunds() - productPrice);

        while (leftToChange > 0) {
            if (leftToChange >= 1.00) {
                change.add(Currency.DOLLAR);
                leftToChange = format(leftToChange - 1.00);
            } else if (leftToChange >= 0.25) {
                change.add(Currency.QUARTER);
                leftToChange = format(leftToChange - 0.25);
            } else if (leftToChange >= 0.10) {
                change.add(Currency.DIME);
                leftToChange = format(leftToChange - 0.10);
            } else if (leftToChange >= 0.05) {
                change.add(Currency.NICKLE);
                leftToChange = format(leftToChange - 0.05);
            }
            leftToChange = format(leftToChange);
        }
        return change;
    }

    public double format(double number) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(format.format(number));
    }

    public void updateProductName(String newName, String product) {
        if (getServiceMode()) {
            getInventory().setProductName(newName, product);
        }
    }

    public void updateProductPrice(double newPrice, String product) {
        if (getServiceMode()) {
            getInventory().setProductPrice(newPrice, product);
        }
    }

    public void updateProductQuantity(int newQuantity, String product) {
        if (getServiceMode()) {
            getInventory().setProductQuantity(newQuantity, product);
        }
    }

    public void updateCurrencyQuantity(Currency currency, int newTotal) {
        if (getServiceMode()) {
            bank.replace(currency, newTotal);
        }
    }

    private void toBank (Currency currency) {
        bank.replace(currency, bank.get(currency) + 1);
    }

    private void fromBank (Currency currency) {
        bank.replace(currency, bank.get(currency) - 1);
    }

    private boolean isProductOutOfStock(String product) {
        return getInventory().getProductQuantity(product) == 0;
    }
}
