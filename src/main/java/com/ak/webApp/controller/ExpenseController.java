package com.ak.webApp.controller;

import com.ak.webApp.models.Expense;
import com.ak.webApp.models.User;
import com.ak.webApp.repository.ExpenseRepository;
import com.ak.webApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ExpenseController {

    private Expense expense;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expense = new Expense();
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expenses")
    public String getAllExpenses(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int currentUserId ;
        String username;
        if (principal instanceof UserDetails) {
            currentUserId = ((User) (principal)).getUserId();
            username = ((User) (principal)).getUsername();
        } else {
            currentUserId = -1;
            username = principal.toString();
        }


        List<Expense> expenses = expenseRepository.findByUserId(currentUserId);
        Map<Integer, List<Object>> expensesMap = new HashMap<>();

        expenses.forEach(expense -> {
            List<Object> expenseObjects = getExpenseData(expense);
            expensesMap.put(expense.getExpenseId(), expenseObjects);
        });
        model.addAttribute("username", username);
        model.addAttribute("userId", currentUserId);
        model.addAttribute("expensesMap", expensesMap);
        model.addAttribute("newExpense", new Expense());
        return "/expenses/expenses";
    }

    private List<Object> getExpenseData(Expense expense) {
        Date date = expense.getDate();
        String description = expense.getDescription();
        String category = expense.getCategory();
        BigDecimal amount = expense.getAmount();
        String type = expense.getType();
//        User user = expense.getUser();
//        String username = user.getUsername();
        return Arrays.asList(date, description, category, amount, type);
    }

    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute Expense newExpense) {



        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newExpense.setUser((User) (principal));

        Expense lastExpense = expenseRepository.findAll().stream().reduce((a, b) -> b).orElse(null);
        newExpense.setExpenseId(getNewId());

        expenseRepository.save(newExpense);
        return "redirect:/expenses";
    }



    private int getNewId() {
        Expense lastExpense = expenseRepository.findAll().stream().reduce((a, b) -> b).orElse(null);
        return (lastExpense != null) ? (lastExpense.getExpenseId() + 1) : 1;
    }






    @GetMapping("/expenses/{userId}")
    public @ResponseBody
    List<Expense> getAllExpensesOfAUserById(@PathVariable int userId) {
        return expenseRepository.findByUserId(userId);
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


