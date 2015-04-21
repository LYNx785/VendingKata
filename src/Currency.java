public enum Currency {
    NICKLE(0.05),
    DIME(0.10),
    QUARTER(0.25),
    DOLLAR(1.00);

    private double value;

    Currency(double value) {
        this.value = value;
    }

    public double getValue () {
        return value;
    }
}
