import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**Contains method to read a csv file containing loan and investment data*/
class FileReader {
    private final static Logger logger = LoggerFactory.getLogger(FileReader.class);
    private DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");   //loan date format

    List<Loan> readLoanFile(String path) {
        List<Loan> loans = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReader(new java.io.FileReader(path));
            String[] values;    //temporary stores row of csv
            csvReader.readNext();   //skips the first row of csv (containing labels)

            while ((values = csvReader.readNext()) != null) {   //until we get to the end of a file
                loans.add(new Loan(Integer.valueOf(values[0]), Integer.valueOf(values[1]), values[2],
                        Integer.valueOf(values[3]), LocalDate.parse(values[4],formatter)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Sorting the loans generated from the loan file");
        loans.sort(Comparator.comparing(Loan::getCompletedDate));   //sort loans from the oldest to newest

        return loans;
    }

    List<Investment> readInvestmentFile(String path) {
        List<Investment> investments = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReader(new java.io.FileReader(path));
            String[] values;    //temporary stores row of csv
            csvReader.readNext();   //skips the first row of csv (containing labels)

            while ((values = csvReader.readNext()) != null) {   //until we get to the end of a file
                investments.add(new Investment(values[0], Integer.valueOf(values[1]), values[2], Integer.valueOf(values[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Sorting the investments generated from the investment file");
        investments.sort(Comparator.comparing(Investment::getAmount));     //sort investments from smallest to highest

        return investments;
    }
}