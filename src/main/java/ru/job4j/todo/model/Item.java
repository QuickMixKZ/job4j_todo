package ru.job4j.todo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private LocalDateTime created;
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    public Item() {
    }

    public Item(String name, String description, LocalDateTime created, boolean done) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(int id, String name, String description, LocalDateTime created, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Item(int id, String name, String description, LocalDateTime created, boolean done, Account account) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
        this.account = account;
    }

    public Item(int id, String name, String description, LocalDateTime created, boolean done, Account account, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
        this.account = account;
        this.categories = categories;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && done == item.done
                && Objects.equals(name, item.name)
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created)
                && Objects.equals(account, item.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created, done, account);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + ", account=" + account
                + '}';
    }
}
