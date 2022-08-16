package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService service;
    private final CategoryService categoryService;

    public ItemController(ItemService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("/items")
    public String items(Model model,
                        @RequestParam(defaultValue = "any", name = "status") String status,
                        HttpSession session) {
        setUserToModel(model, session);
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
    public String editItem(Model model,
                           @ModelAttribute Item item,
                           HttpServletRequest request) {
        String[] categoriesId = request.getParameterValues("category_hidden");
        HashMap<Category, Boolean> categoriesMap = new HashMap<>();
        for (String sId : categoriesId) {
            categoriesMap.put(categoryService.findById(Integer.parseInt(sId)), true);
        }
        for (Category category : categoryService.getAll()) {
            categoriesMap.putIfAbsent(category, false);
        }
        model.addAttribute("categoriesMap", categoriesMap);
        return "editItemForm";
    }

    @GetMapping("/showItemForm/{itemId}")
    public String showItem(Model model, @PathVariable int itemId) {
        model.addAttribute("item", service.findById(itemId));
        return "showItemForm";
    }

    @PostMapping("/performItem")
    public String performItem(Model model, @ModelAttribute Item item) {
        service.performItem(item);
        return String.format("redirect:/showItemForm/%s", item.getId());
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, HttpServletRequest request) {
        String[] categories = request.getParameterValues("category");
        for (String sId : categories) {
            item.addCategory(categoryService.findById(Integer.parseInt(sId)));
        }
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
        model.addAttribute("categories", categoryService.getAll());
        return "addItemForm";
    }

    @PostMapping("addItem")
    public String addItem(@ModelAttribute Item item,
                          HttpSession session,
                          HttpServletRequest request) {
        item.setCreated(LocalDateTime.now());
        item.setAccount((Account) session.getAttribute("account"));
        String[] categories = request.getParameterValues("category");
        for (String sId : categories) {
            item.addCategory(categoryService.findById(Integer.parseInt(sId)));
        }
        service.addItem(item);
        return "redirect:/items";
    }

    private void setUserToModel(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("account", account);
    }
}
