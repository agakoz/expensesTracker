package com.ak.webApp.controller.restControllers;

import com.ak.webApp.models.Expense;
import com.ak.webApp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseRestController {

    private Expense expense;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRestController(ExpenseRepository expenseRepository) {
        this.expense = new Expense();
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/rest/expenses")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/rest/expenses/{userId}")
    public List<Expense> getByUserId(@PathVariable int userId) {
        return expenseRepository.findByUserId(userId);
    }

}
