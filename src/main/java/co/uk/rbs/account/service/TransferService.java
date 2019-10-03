package co.uk.rbs.account.service;

import co.uk.rbs.account.entity.Transaction;

import java.math.BigDecimal;

public interface TransferService {
    Transaction performTransfer(String srcAccNum, String destAccNum, BigDecimal amount);
}