package fetch.rewards.points.payers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PayerController {
    private final PayerService payerService;

    protected PayerController (PayerService payerService) {
        this.payerService = payerService;
    }

    /**
     * Returns the sum of points for each payer.
     *
     * @return Sum of points per payer.
     */
    @GetMapping("/points")
    public List<Payer> points() {
        return payerService.getPoints();
    }
}
