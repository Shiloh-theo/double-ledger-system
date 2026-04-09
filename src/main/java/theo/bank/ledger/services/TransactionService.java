package theo.bank.ledger.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import theo.bank.ledger.dto.TransactionDto;
import theo.bank.ledger.dto.TransactionHistoryDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.models.Transactions;
import theo.bank.ledger.repositories.AccountRepository;
import theo.bank.ledger.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public ResponseEntity<String> transfer(TransactionDto dto) {

        // Step 1: Fetch both accounts
        Accounts sender = accountRepository.findByAccountNo(dto.getDebitAccount());
        Accounts receiver = accountRepository.findByAccountNo(dto.getCreditAccount());

        // Step 2: Validate both accounts exist
        if (sender == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sender account not found");
        }
        if (receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Receiver account not found");
        }

        // Step 3: Parse the transfer amount as BigDecimal
        BigDecimal amount = dto.getAmount();

        // Step 4: Check sender has enough funds
        if (sender.getBalance().compareTo(amount) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Insufficient funds");
        }

        // Step 5: Deduct from sender, credit to receiver
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        // Step 6: Save both updated accounts
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Step 7: Record the transaction in the ledger
        Transactions record = new Transactions();
        record.setSenderAccount(dto.getDebitAccount());
        record.setReceiverAccount(dto.getCreditAccount());
        record.setAmount(amount);
        record.setDate(LocalDate.now());
        record.setTime(LocalTime.now());

        transactionRepository.save(record);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Transfer successful");
    }

    public List<TransactionHistoryDto> getTransactionHistory(String accountNo) {

        List<Transactions> transactions =
                transactionRepository.findBySenderAccountOrReceiverAccount(accountNo, accountNo);

        List<TransactionHistoryDto> history = new ArrayList<>();

        for (Transactions tx : transactions) {

            String type;

            if (tx.getSenderAccount().equals(accountNo)) {
                type = "DEBIT";
            } else {
                type = "CREDIT";
            }

            TransactionHistoryDto dto = new TransactionHistoryDto(
                    tx.getSenderAccount(),
                    tx.getReceiverAccount(),
                    tx.getAmount(),
                    tx.getDate(),
                    tx.getTime(),
                    type
            );

            history.add(dto);
        }

        return history;
    }
}