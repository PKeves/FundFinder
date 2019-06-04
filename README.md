# FundFinder
Finds funds for loans by matching them with related investments.


Business rules:
- A valid "funded" loan must be fully funded from the investments.csv file. If a loan does not have all the funds necessary, then it cannot qualify.
- Partially funded loans are of no value (we can't give our borrowers less money than they need to buy the property)
- Over-funded loans are also not useful (we can't give our borrowers more money than they want as this means they will have to eventually pay more interest).
- If someone wants to invest in TRACKER, you can't place their money into a Loan that is FIXED - and vice-versa.
- Loans should be processed in the order of their completed date (oldest to newest)
- The term of the investment must be greater than the term of the loan (i.e. the investor needs to be willing to put money in for longer than the loan needs it for).


Input:
- InvestmentRequests-data.csv
- Loans-data.csv

Output:
- Output.json

To run:
- Execute main method located in the Matcher.java
