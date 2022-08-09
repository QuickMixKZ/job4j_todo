package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Item> findAll() {
        return this.tx(session -> session.createQuery("from Item").list());
    }

    public Item findById(int id) {
        return this.tx(session -> {
            Query query = session.createQuery("from Item  where id = :fId");
            query.setParameter("fId", id);
            Item result = (Item) query.getSingleResult();
            return result;
        });
    }

    public void updateItem(Item item) {
        this.tx(session -> {
            Query query = session.createQuery("update Item "
                    + "set name = :newName, "
                    + "description = :newDescription, "
                    + "done = :newDone "
                    + "where id = :fId");
            query.setParameter("newName", item.getName());
            query.setParameter("newDescription", item.getDescription());
            query.setParameter("newDone", item.isDone());
            query.setParameter("fId", item.getId());
            return query.executeUpdate() > 0;
        });
    }

    public void deleteItem(Item item) {
        this.tx(session -> {
            Query query = session.createQuery("delete from Item where id = :fId");
            query.setParameter("fId", item.getId());
            return query.executeUpdate() > 0;
        });
    }

    public void addItem(Item item) {
        this.tx(session -> {
            session.save(item);
            return true;
        });
    }

    public List<Item> findByStatus(Boolean done) {
        return this.tx(session -> {
            Query query = session.createQuery("from Item where done = :fIsDone");
            query.setParameter("fIsDone", done);
            List<Item> result = query.getResultList();
            return result;
        });
    }

    public void performItem(Item item) {
        this.tx(session -> {
            Query query = session.createQuery("UPDATE  Item set done = true WHERE id = :fId");
            query.setParameter("fId", item.getId());
            return query.executeUpdate() > 0;
        });
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
