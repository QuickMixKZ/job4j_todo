package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryStore store;

    public CategoryService(CategoryStore store) {
        this.store = store;
    }

    public List<Category> getAll() {
        return store.getAll();
    }

    public Category findById(int id) {
        return store.findById(id);
    }
}
