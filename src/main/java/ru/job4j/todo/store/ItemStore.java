package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        Query query = session.createQuery("from Item");
        List<Item> result = query.getResultList();
        session.close();
        return result;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        Query query = session.createQuery("from Item  where id = :fId");
        query.setParameter("fId", id);
        Item result = (Item) query.getSingleResult();
        session.close();
        return result;
    }

    public void updateItem(Item item) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update Item "
                + "set name = :newName, "
                + "description = :newDescription, "
                + "done = :newDone "
                + "where id = :fId");
        query.setParameter("newName", item.getName());
        query.setParameter("newDescription", item.getDescription());
        query.setParameter("newDone", item.isDone());
        query.setParameter("fId", item.getId());
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void deleteItem(Item item) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from Item where id = :fId");
        query.setParameter("fId", item.getId());
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void addItem(Item item) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }

    public List<Item> findByStatus(Boolean done) {
        Session session = sf.openSession();
        Query query = session.createQuery("from Item where done = :fIsDone");
        query.setParameter("fIsDone", done);
        List<Item> result = query.getResultList();
        session.close();
        return result;
    }
}
