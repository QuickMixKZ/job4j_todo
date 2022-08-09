package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/registrationForm")
    public String registrationForm(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("account", new Account());
        model.addAttribute("fail", fail != null);
        return "registrationForm";
    }

    @PostMapping("/registerAccount")
    public String registerAccount(Model model, @ModelAttribute Account account) {
        boolean result = service.add(account);
        if (!result) {
            return "redirect:/registrationForm?fail=true";
        }
        return "redirect:/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("account", new Account());
        model.addAttribute("fail", fail != null);
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute Account account, HttpServletRequest req) {
        Optional<Account> accountDB = service.findByLoginAndPwd(account);
        if (accountDB.isEmpty()) {
            return "redirect:/loginForm?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("account", accountDB.get());
        return "redirect:/items";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginForm";
    }
}
