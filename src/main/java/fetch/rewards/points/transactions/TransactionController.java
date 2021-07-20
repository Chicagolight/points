package fetch.rewards.points.transactions;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    protected TransactionController (TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create a new transaction from the HTTP body and send to the data access layer.
     *
     * @param newTransaction HTTP body consisting of payer (string), points (integer), timestamp (date)
     * @return Status of transaction.
     */
    @PostMapping("/transaction")
    public TransactionDTO addTransaction(@RequestBody TransactionDTO newTransaction) {
        return new TransactionDTO(transactionService.addTransaction(new Transaction(newTransaction.payer, newTransaction.points, newTransaction.timestamp)));
    }

    @PostMapping("/spend")
    public List<PointsDTO> spendPoints(@RequestBody PointsDTO pointsToSpend) {
        return transactionService.spendPoints(pointsToSpend.points).stream().map(PointsDTO::new).collect(Collectors.toList());
    }

    /**
     * Data transfer object version of Transaction.
     * Separates user input from database access.
     *
     * Best practice for avoiding malicious input.
     * SonarQube Rule java:S4684
     */
    public static class TransactionDTO {
        String payer;
        int points;
        Date timestamp;

        public TransactionDTO(String payer, int points, Date timestamp) {
            this.payer = payer;
            this.points = points;
            this.timestamp = timestamp;
        }

        public TransactionDTO(Transaction transaction) {
            this.payer = transaction.getPayer();
            this.points = transaction.getPoints();
            this.timestamp = transaction.getTimestamp();
        }

        public String getPayer() {
            return payer;
        }

        public int getPoints() {
            return points;
        }

        public Date getTimestamp() {
            return timestamp;
        }
    }

    /**
     * DTO for spend points.
     * Allows support for POST request to match sample input.
     *
     * Second argument is unused, but necessary for compatibility with jackson-databind deserialization.
     * @see <a href="https://github.com/FasterXML/jackson-databind/issues/1498">Single argument constructors not supported by default</a>
     */
    public static class PointsDTO {
        String payer;
        int points;

        public PointsDTO(String payer, int points) {
            this.payer = payer;
            this.points = points;
        }

        public PointsDTO(Transaction transaction) {
            this.payer = transaction.getPayer();
            this.points = transaction.getPoints();
        }

        public String getPayer() {
            return payer;
        }

        public int getPoints() {
            return points;
        }
    }
}
