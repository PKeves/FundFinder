import java.util.ArrayList;
import java.util.List;

/** Match represents a fully funded loan and the corresponding investors of this loan*/
class Match {
    private Loan loan;
    private List<Investment> investments  = new ArrayList<>();

    void add(Loan loan){
        this.loan = loan;
    }

    void add(Investment investment){
        investments.add(investment);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(loan.getProduct()).append(" Loan ");
        stringBuilder.append(" : ");
        for(Investment investment : investments){
            String investor = investment.getInvestor();
            stringBuilder.append("  ");
            stringBuilder.append(investor);
        }
        return stringBuilder.toString();
    }
}