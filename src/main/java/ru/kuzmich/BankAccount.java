package ru.kuzmich;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final String accountNumber;
    private BigDecimal balance;
    private final Lock lock;
    private static final AtomicInteger accountCounter = new AtomicInteger(1);

    public BankAccount(long initialBalance) {
        this.accountNumber = "Acc-" + accountCounter.getAndIncrement();
        this.balance = new BigDecimal(initialBalance);
        this.lock = new ReentrantLock();
    }

    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            return false;
        }

        lock.lock();
        try {
            balance.add(amount);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            return false;
        }

        lock.lock();
        try {
            if (balance.compareTo(amount) >= 0) {
                balance.subtract(amount);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public BigDecimal getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Lock getLock() {
        return lock;
    }
}
