package fetch.rewards.points.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Provides CRUD JPA functionality through Spring
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
 *
 * Add additional queries below.
 */
interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT COALESCE(SUM(points), 0) FROM Transaction")
    int pointSum();

    //Spring JPA auto-implementation of "SELECT t FROM Transaction t WHERE t.payer = ?1 ORDER BY t.timestamp ASC"
    List<Transaction> findTransactionsByPayerOrderByTimestampAsc(String payer);
}
