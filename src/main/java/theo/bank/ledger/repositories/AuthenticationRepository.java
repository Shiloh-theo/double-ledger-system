package theo.bank.ledger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theo.bank.ledger.models.Authentication;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Integer> {
}
