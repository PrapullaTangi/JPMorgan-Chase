package com.jpmc.midascore;

import com.jpmc.midascore.component.TransactionService;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final TransactionService transactionService;

    public KafkaConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        logger.info("Received transaction" + transaction);
        transactionService.process(transaction);
    }
}