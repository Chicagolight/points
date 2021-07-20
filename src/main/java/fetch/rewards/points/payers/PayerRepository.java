package fetch.rewards.points.payers;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides CRUD JPA functionality through Spring
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
 *
 * Add additional queries below.
 */
interface PayerRepository extends JpaRepository<Payer, String> {}
