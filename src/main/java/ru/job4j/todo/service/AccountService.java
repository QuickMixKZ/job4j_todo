package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.store.AccountStore;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountStore store;

    public AccountService(AccountStore store) {
        this.store = store;
    }

    public boolean add(Account account) {
        return store.add(account);
    }

    public Optional<Account> findByLoginAndPwd(Account account) {
        return store.findByLoginAndPwd(account);
    }
}
