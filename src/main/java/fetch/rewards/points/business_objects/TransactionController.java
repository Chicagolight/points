package fetch.rewards.points.business_objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {
    private final TransactionRepository transRepo;

    protected TransactionController (TransactionRepository repository) {
        transRepo = repository;
    }

    /**
     * Returns the total sum of points.
     *
     * @return Sum of points or 0 if there are no transactions
     */
    @GetMapping("/points")
    public List<Transaction> points() {
        //TODO FIX THIS. Points should return a list of all entries with points still active.
        //return transRepo.count() != 0 ? transRepo.pointSum() : 0;

        //Does this satisfy the requirement?
        return transRepo.findAll();
    }

    @PostMapping("/transaction")
    public Transaction addTransaction(@RequestBody TransactionDTO newTransaction) {
        return transRepo.save(new Transaction(newTransaction.payer, newTransaction.points, newTransaction.timestamp));
    }

    /**
     * Data transfer object version of Transaction
     *
     * Best practice for avoiding malicious input
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
    }
}
