package fetch.rewards.points.transactions;

import fetch.rewards.points.payers.PayerService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transRepo;
    private final PayerService payerService;

    /**
     * Binds the transaction data access layer to the business logic layer.
     *
     * @param transRepo Transaction data access.
     */
    protected TransactionService(TransactionRepository transRepo, PayerService payerService) {
        this.transRepo = transRepo;
        this.payerService = payerService;
    }

    /**
     * Saves new transaction to the database.
     *
     * @param newTransaction Transaction to persist to the database
     * @return Status of the save
     */
    protected Transaction addTransaction(Transaction newTransaction) {
        //No points in transaction. Likely user error. Do not save and return error message.
        if (newTransaction.getPoints() == 0) {
            throw new IllegalArgumentException("Transaction has no points.");
        }
        //Negative points in transaction. Attempt to remove points from payer transactions starting with oldest.
        else if (newTransaction.getPoints() < 0) {
            int remainingNegativePoints = -newTransaction.getPoints();
            List<Transaction> payerTransactions = transRepo.findTransactionsByPayerOrderByTimestampAsc(newTransaction.getPayer());

            subtractPoints(remainingNegativePoints, payerTransactions);

            //Return original transaction. Negative transactions are not saved as an entry.
            return newTransaction;
        }
        //Payer doesn't exist. Create the payer and save the transaction.
        else {
            payerService.updatePayers(newTransaction.getPayer(), newTransaction.getPoints());
            return transRepo.save(newTransaction);
        }
    }

    /**
     * Spend points split among all transactions starting with oldest.
     *
     * @param pointsToSpend Number of points to spend
     * @return List of negative transactions.
     */
    protected List<Transaction> spendPoints(int pointsToSpend) {
        if (pointsToSpend <= 0) {
            throw new IllegalArgumentException("Number of points to spend must be positive.");
        }

        return subtractPoints(pointsToSpend, transRepo.findAll(Sort.by("timestamp").ascending()));
    }

    /**
     * Subtract points for negative balance transactions and spend point calls.
     *
     * @param pointsToSpend Number of points to remove
     *                      pointsToSpend > 0
     *                      pointsToSpend > transactionResultSet point sum
     *
     * @param transactionResultSet Set of transactions to remove points pre-sorted by timestamp:
     *                             Negative transactions    - Transactions of the same payer only
     *                             Spend Points call        - All transactions
     *
     * @return List of transactions used to fulfill negative transactions/spend call
     */
    private List<Transaction> subtractPoints(int pointsToSpend, List<Transaction> transactionResultSet) {
        //Check that there are enough points in the result set.
        if (pointsToSpend > transactionResultSet.stream().mapToInt(Transaction::getPoints).sum()) {
            throw new IllegalArgumentException("Points balance is insufficient");
        }

        //Create a record of negative transactions for fulfillment.
        List<Transaction> toReturn = new ArrayList<>();
        Iterator<Transaction> transactionIterator = transactionResultSet.iterator();

        while (pointsToSpend > 0) {
            Transaction nextTransaction = transactionIterator.next();

            //Negative balance exceeds/matches the next transaction, adjust remaining points and delete transaction.
            if (pointsToSpend >= nextTransaction.getPoints()) {
                pointsToSpend -= nextTransaction.getPoints();

                toReturn.add(new Transaction(nextTransaction.getPayer(), -nextTransaction.getPoints()));
                transRepo.delete(nextTransaction);
                payerService.updatePayers(nextTransaction.getPayer(), -nextTransaction.getPoints());
            }
            //Adjust transaction and set remaining pointsToSpend to 0.
            else {
                nextTransaction.setPoints(nextTransaction.getPoints() - pointsToSpend);
                transRepo.save(nextTransaction);
                toReturn.add(new Transaction(nextTransaction.getPayer(), -pointsToSpend));
                payerService.updatePayers(nextTransaction.getPayer(), -pointsToSpend);

                pointsToSpend = 0;
            }
        }

        return toReturn;
    }
}
