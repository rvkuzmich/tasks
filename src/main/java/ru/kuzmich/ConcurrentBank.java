package ru.kuzmich;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentBank {

    private final ConcurrentMap<String, BankAccount> accounts;
    private final ReentrantReadWriteLock readWriteLock;

    public ConcurrentBank() {
        this.accounts = new ConcurrentHashMap<>();
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public BankAccount createAccount(int initialBalance) {
        BankAccount account = new BankAccount(initialBalance);
        readWriteLock.writeLock().lock();
        try {
            accounts.put(account.getAccountNumber(), account);
            return account;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public boolean transfer(BankAccount from, BankAccount to, int amount) {
        if (from == null || to == null || amount <= 0) {
            return false;
        }

        BankAccount first =
            from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        BankAccount second = first == from ? to : from;

        first.getLock().lock();
        second.getLock().lock();

        try {
            if (!accounts.containsKey(from.getAccountNumber()) || !accounts.containsKey(
                to.getAccountNumber())) {
                return false;
            }
            if (from.withdraw(new BigDecimal(amount))) {
                return to.deposit(new BigDecimal(amount));
            }
            return false;
        } finally {
            second.getLock().unlock();
            first.getLock().unlock();
        }
    }

    public BigDecimal getTotalBalance() {
        readWriteLock.readLock().lock();
        try {
            return accounts.values().stream()
                .map(BankAccount::getBalance).reduce(new BigDecimal(0), BigDecimal::add);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}
