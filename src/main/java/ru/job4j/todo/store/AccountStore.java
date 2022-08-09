package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Account;

import javax.persistence.Query;
import java.util.Optional;

@Repository
public class AccountStore {

    private static final Logger LOG = LoggerFactory.getLogger(AccountStore.class.getName());

    private final SessionFactory sessionFactory;

    public AccountStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean add(Account account) {
        boolean result = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(account);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            LOG.error("Exception in AccountStore", e);
        }
        session.close();
        return result;
    }

    public Optional<Account> findByLoginAndPwd(Account account) {
        Optional<Account> result = Optional.empty();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Account where login = :fLogin and password = :fPassword");
        query.setParameter("fLogin", account.getLogin());
        query.setParameter("fPassword", account.getPassword());
        try {
            result = Optional.of((Account) query.getSingleResult());
        } catch (Exception e) {
            LOG.error("Exception in AccountStore", e);
        }

        session.close();
        return result;
    }

}
