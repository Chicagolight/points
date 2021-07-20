package fetch.rewards.points.payers;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayerService {
    private final PayerRepository payerRepo;

    public PayerService(PayerRepository payerRepo) {
        this.payerRepo = payerRepo;
    }

    /**
     * Returns sum of points per payer.
     *
     * @return Sum of points per payer
     */
    public List<Payer> getPoints() {
        return payerRepo.findAll();
    }

    /**
     * Updates a payer if it exists, and adds it if it doesn't.
     * Checks for negative point balances exceeding payer's balance.
     *
     * @param payerName Payer to update
     * @param points Points to add
     *
     * @throws IllegalArgumentException New payer with a non-positive balance or existing payer with negative points exceeding the current number of points.
     */
    public void updatePayers(String payerName, int points) throws IllegalArgumentException {
        if (payerRepo.existsById(payerName)) {
            Payer payer = payerRepo.getById(payerName);

            if (points < 0 && points > payer.getPointSum()) {
                throw new IllegalArgumentException("Payer balance insufficient.");
            }

            payer.setPointSum(payer.getPointSum() + points);

            payerRepo.save(payer);
        }
        else if (points > 0) {
            payerRepo.save(new Payer(payerName, points));
        }
        else {
            throw new IllegalArgumentException("Payer does not exist. Initial balance must be positive.");
        }
    }
}
