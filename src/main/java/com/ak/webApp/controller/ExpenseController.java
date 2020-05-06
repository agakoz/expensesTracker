package com.ak.webApp.controller;

import com.ak.webApp.models.Expense;
import com.ak.webApp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ExpenseController {

    private Expense expense;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expense = new Expense();
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/allExpenses")
    public String showAll(Model model) {
        model.addAttribute("expenses", expenseRepository.findAll());
        model.addAttribute("date", expense.getDate());
        model.addAttribute("description", expense.getDescription());
        model.addAttribute("category", expense.getCategory());
        model.addAttribute("Amount", expense.getAmount());
        model.addAttribute("Type", expense.getType());
        model.addAttribute("user", expense.getUser());
        model.addAttribute("newExpense", new Expense());
        return "expenses/allExpenses";
    }

    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute Expense newExpense) {
        Expense lastExpense = expenseRepository.findAll().stream().reduce((a, b) -> b).orElse(null);
        newExpense.setExpenseId(getNewId(lastExpense));
        expenseRepository.save(newExpense);
        return "redirect:/allExpenses";
    }

    private int getNewId(Expense lastExpense) {
        return (lastExpense != null) ? (lastExpense.getExpenseId() + 1) : 1;
    }


    @GetMapping("/allExpenses/{userId}")
    public @ResponseBody
    List<Expense> getAllExpensesOfAUserById(@PathVariable int userId) {
        return expenseRepository.findAllByUSerId(userId);
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/tss_pl");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("ISO-8859-2");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }


}


