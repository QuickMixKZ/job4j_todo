package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.service.CategoryService;

import java.util.List;
import java.util.function.Function;

@Repository
public class CategoryStore {

    private final SessionFactory sf;

    public CategoryStore(SessionFactory sessionFactory) {
        this.sf = sessionFactory;
    }

    public List<Category> getAll() {
        return this.tx(session -> session.createQuery("from Category ").getResultList());
    }

    public Category findById(int id) {
         return this.tx(session ->  session.get(Category.class, id));
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
