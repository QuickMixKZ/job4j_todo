package ru.job4j.todo.controller;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/items")
    public String items(Model model, @RequestParam(defaultValue = "any", name = "status") String status) {
        List<Item> items;
        if ("any".equals(status)) {
            items = service.findAll();
        } else if ("new".equals(status)) {
            items = service.findByStatus(false);
        } else if ("done".equals(status)) {
            items = service.findByStatus(true);
        } else {
            throw new IllegalArgumentException();
        }
        model.addAttribute("status", status);
        model.addAttribute("items", items);
        return "itemsForm";
    }

    @PostMapping("/editItemForm")
    public String editItem(Model model, @ModelAttribute Item item) {
        return "editItemForm";
    }

    @GetMapping("/showItemForm/{itemId}")
    public String showItem(Model model, @PathVariable int itemId) {
        model.addAttribute("item", service.findById(itemId));
        return "showItemForm";
    }

    @PostMapping("/performItem")
    public String performItem(Model model, @ModelAttribute Item item) {
        item.setDone(true);
        service.performItem(item);
        return String.format("redirect:/showItemForm/%s", item.getId());
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        service.updateItem(item);
        return String.format("redirect:/showItemForm/%s", item.getId());
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@ModelAttribute Item item) {
        service.deleteItem(item);
        return "redirect:/items";
    }

    @GetMapping("/addItemForm")
    public String addItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "addItemForm";
    }

    @PostMapping("addItem")
    public String addItem(@ModelAttribute Item item) {
        item.setCreated(LocalDateTime.now());
        service.addItem(item);
        return "redirect:/items";
    }
}
