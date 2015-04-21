import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Product> products;
    private Product product;

    Inventory() {
        products = new ArrayList<Product>();
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public String getProductName(String selector) {
        product = getProduct(selector);
        if (product == null) { return "No Product"; }
        return product.getName();
    }

    public double getProductPrice(String selector) {
        product = getProduct(selector);
        if (product == null) { return 0; }
        return product.getPrice();
    }

    public int getProductQuantity(String selector) {
        product = getProduct(selector);
        if (product == null) { return 0; }
        return product.getQuantity();
    }

    public void decrementQuantity(String selector) {
        int quantity = getProductQuantity(selector);
        if (quantity > 0) {
            product.setQuantity(quantity - 1);
        }
    }

    private Product getProduct(String selector) {
        for (Product product : products) {
            if (product.getSelector().equals(selector)) {
                return product;
            }
        }
        return null;
    }

    public void setProductName(String newName, String selector) {
        product = getProduct(selector);
        if (product != null) { product.setName(newName); }
    }

    public void setProductPrice(double newPrice, String selector) {
        product = getProduct(selector);
        if (product != null) { product.setPrice(newPrice); }
    }

    public void setProductQuantity(int newQuantity, String selector) {
        product = getProduct(selector);
        if (product != null) { product.setQuantity(newQuantity); }
    }
}
