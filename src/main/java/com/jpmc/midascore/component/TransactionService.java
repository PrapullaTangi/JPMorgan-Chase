package com.jpmc.midascore.component;

import com.jpmc.midascore.AppConfig;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionService {

    static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    public void process(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            System.out.println(">>> INVALID - user not found");
            return;
        }

        if (sender.getBalance() < transaction.getAmount()) {
            System.out.println(">>> INVALID - insufficient balance for senderId=" + transaction.getSenderId());
            return;
        }

        //CALL API
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive", //where to send
                transaction,  // what to send
                Incentive.class //what to expect back
        );
        float incentiveAmount = (incentive!=null) ? incentive.getAmount():0;

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());

        userRepository.save(sender);
        userRepository.save(recipient);
        transactionRepository.save(new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount));

        System.out.println(">>> Incentive: " + incentiveAmount + " | Wilbur balance: " + userRepository.findById(9).getBalance());
    }
}