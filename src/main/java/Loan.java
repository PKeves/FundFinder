import java.time.LocalDate;

class Loan {
    private int id;
    private int amount;
    private String product;
    private int term;
    private LocalDate completedDate;

    Loan(int id, int amount, String product, int term, LocalDate completedDate){
        this.id = id;
        this.amount = amount;
        this.product = product;
        this.term = term;
        this.completedDate = completedDate;
    }

    int getAmount() {
        return amount;
    }

    String getProduct(){
        return product;
    }

    int getTerm() {
        return term;
    }

    LocalDate getCompletedDate() {
        return completedDate;
    }
}