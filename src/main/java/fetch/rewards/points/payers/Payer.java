package fetch.rewards.points.payers;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Payer {
    @Id
    private String payer;

    private int pointSum;

    protected Payer() {}

    public Payer(String payer, int pointSum) {
        this.payer = payer;
        this.pointSum = pointSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Payer toCompare = (Payer) o;
        return payer.equals(toCompare.payer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer);
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public int getPointSum() {
        return pointSum;
    }

    public void setPointSum(int pointSum) {
        this.pointSum = pointSum;
    }
}
