import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

//*Builds JSON represantation of the matches obtained from CSV files */
class Matcher {
    private List<Match> matches = new ArrayList<>();
    private final static Logger logger = LoggerFactory.getLogger(Matcher.class);

    private Matcher(String loanFile, String investmentFile){
        FileReader fileReader = new FileReader();

        logger.info("Reading the loan file...");
        List<Loan> loans = fileReader.readLoanFile(loanFile);

        logger.info("Reading the investment file...");
        List<Investment> investments = fileReader.readInvestmentFile(investmentFile);

        logger.info("Getting the fixed loans from the loan file...");
        List<Loan> fixedLoans = filterLoans(loans, "FIXED");

        logger.info("Getting the tracker loans from the loan file...");
        List<Loan> trackerLoans = filterLoans(loans, "TRACKER");

        logger.info("Getting the fixed investments from the investment file...");
        List<Investment> fixedInvestments = filterInvestments(investments, "FIXED");

        logger.info("Getting the tracker investments from the investment file...");
        List<Investment> trackerInvestments = filterInvestments(investments, "TRACKER");

        logger.info("Matching the fixed loans to fixed investments...");
        match(fixedLoans, fixedInvestments);

        logger.info("Matching the tracker loans to tracker investments...");
        match(trackerLoans, trackerInvestments);

        logger.info("Generating JSON represtantion of the matches");
        createJSON();
    }

    /**Returns the subset of the loan list, containing only loans of specified type*/
    private List<Loan> filterLoans(List<Loan> loans, String type){
        return  loans.stream().filter(p -> p.getProduct().equals(type))
                    .collect(Collectors.toList());
    }

    private List<Investment> filterInvestments(List<Investment> investments, String type){
        return  investments.stream().filter(p -> p.getProductType().equals(type))
                .collect(Collectors.toList());
    }

    /**Matches loans to investments according to the matching rules specified in the business logic */
    private void match(List<Loan> loans, List<Investment> investments){
        int loanAmount;
        Loan matchedLoan = null;
        List<Investment> matchedInvestments = new ArrayList<>();

        for(Loan loan : loans){
            loanAmount = loan.getAmount();
            Match match = new Match();  //temporary match, if loan funded match will be added to the list of matches
            Investment partialInvestment = null;    //Contains subset of the investment that was used to fund the matched loan

            for(Investment investment : investments){
                if(investment.getTerm() < loan.getTerm()) { //business logic
                    continue;
                }
                int investmentAmount = investment.getAmount();

                if(investmentAmount < loanAmount){
                    matchedInvestments.add(investment);
                    loanAmount -= investmentAmount;
                }
                else if(investmentAmount > loanAmount){
                    matchedLoan = loan;
                    investment.setAmount(investmentAmount - loanAmount);
                    partialInvestment = new Investment(investment.getInvestor(),loanAmount, investment.getProductType(), investment.getTerm());
                    loanAmount = 0;
                    break; // load is fully funded, therefore we can't look at other investors
                }
            }
            if(loanAmount == 0) {   //if investment was successfully funded
                for (Investment matchedInvestment : matchedInvestments) {
                    match.add(matchedInvestment);
                    investments.remove(matchedInvestment);
                }
                matchedInvestments.clear();
                if(partialInvestment != null) { match.add(partialInvestment); }
                match.add(matchedLoan);

                logger.info("Generating new match - " + match);
                matches.add(match);
            }
        }
        if(matchedLoan != null){ loans.remove(matchedLoan); }
    }

    /**Creates JSON representation of the list of matches */
    private void createJSON(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        try (Writer writer = new FileWriter("output.json")){
            gson.toJson(matches,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Runs the program*/
    public static void main(String[] args) {
        new Matcher("data/loans-data.csv",
                    "data/investmentRequests-data.csv");
    }
}