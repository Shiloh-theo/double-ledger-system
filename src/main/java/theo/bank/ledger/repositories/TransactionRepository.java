package theo.bank.ledger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theo.bank.ledger.models.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {
}
