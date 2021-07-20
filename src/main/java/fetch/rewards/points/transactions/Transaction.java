package fetch.rewards.points.transactions;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String payer;
    private int points;
    private Date timestamp;

    protected Transaction() {}

    public Transaction(String payer, int points) {
        this.payer = payer;
        this.points = points;
    }

    public Transaction(String payer, int points, Date timestamp) {
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction toCompare = (Transaction) o;
        return points == toCompare.points && payer.equals(toCompare.payer) && timestamp.equals(toCompare.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payer, timestamp, points);
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
