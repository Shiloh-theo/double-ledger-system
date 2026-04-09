package theo.bank.ledger.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

    public class TransactionHistoryDto {

        private String senderAccount;
        private String receiverAccount;
        private BigDecimal amount;
        private LocalDate date;
        private LocalTime time;
        private String type; // DEBIT or CREDIT

        public TransactionHistoryDto(
                String senderAccount,
                String receiverAccount,
                BigDecimal amount,
                LocalDate date,
                LocalTime time,
                String type
        ) {
            this.senderAccount = senderAccount;
            this.receiverAccount = receiverAccount;
            this.amount = amount;
            this.date = date;
            this.time = time;
            this.type = type;
        }

        // getters only (no setters needed)
        public String getSenderAccount() { return senderAccount; }
        public String getReceiverAccount() { return receiverAccount; }
        public BigDecimal getAmount() { return amount; }
        public LocalDate getDate() { return date; }
        public LocalTime getTime() { return time; }
        public String getType() { return type; }
}
