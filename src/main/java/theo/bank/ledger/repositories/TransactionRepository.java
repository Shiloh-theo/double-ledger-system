package theo.bank.ledger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theo.bank.ledger.models.Transactions;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

        List<Transactions> findBySenderAccountOrReceiverAccount(String senderAccount, String receiverAccount);
}
