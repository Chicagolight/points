package fetch.rewards.points.business_objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Provides CRUD JPA functionality through Spring
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
 *
 * Add additional queries below.
 */
interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(points) FROM Transaction")
    int pointSum();
}
