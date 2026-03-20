package theo.bank.ledger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import theo.bank.ledger.models.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Integer> {

    Customers findByEmail(String email);
}
