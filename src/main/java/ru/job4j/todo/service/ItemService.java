package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemStore;

import java.util.List;

@Service
public class ItemService {

    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public void updateItem(Item item) {
        store.updateItem(item);
    }

    public void deleteItem(Item item) {
        store.deleteItem(item);
    }

    public void addItem(Item item) {
        store.addItem(item);
    }

    public List<Item> findByStatus(Boolean done) {
        return store.findByStatus(done);
    }
}
