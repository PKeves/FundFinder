class Investment {
    private String investor;
    private int amount;
    private String productType;
    private int term;

    Investment(String name, int amount, String productType, int term){
        this.investor = name;
        this.amount = amount;
        this.productType = productType;
        this.term = term;
    }

    String getInvestor() {
        return investor;
    }

    int getAmount() {
        return amount;
    }

    String getProductType() {
        return productType;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    int getTerm() {
        return term;
    }
}