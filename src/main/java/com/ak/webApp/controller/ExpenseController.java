package com.ak.webApp.controller;

import com.ak.webApp.models.Expense;
import com.ak.webApp.models.User;
import com.ak.webApp.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;


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
    public String getAllExpenses(Model model, HttpSession session) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int currentUserId;
        String username;
        User currentUser;
        if (principal instanceof UserDetails) {
            currentUser = (User)principal;
            currentUserId = ((User) (principal)).getUserId();
            username = ((User) (principal)).getUsername();
        } else {
            currentUser = null;
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
        session.setAttribute("currentUser", currentUser);
        return "expenses/expenses";
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

    @GetMapping("/expenses/add")
    public String add (Model model) {
        model.addAttribute("newExpense", new Expense());
        return "expenses/add";
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

    @PostMapping("/expenses/delete/{expenseId}")
    public String deleteExpenseById(@PathVariable int expenseId) {
        expenseRepository.deleteById(expenseId);
        return "redirect:/expenses";
    }

    @PostMapping("/expenses/edit/{expenseId}")
    public String editExpense(@PathVariable int expenseId, Model model) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        System.out.println(expense.get().toString());
        if (expense.isPresent()) {
            Expense exp = expense.get();
//            expense.get().setAvailable(true);
            model.addAttribute("expense", exp);
//            model.addAttribute("date", expense.get().getDate());
//            model.addAttribute("description", expense.get().getDescription());
//            model.addAttribute("category", expense.get().getCategory());
//            model.addAttribute("amount", expense.get().getAmount());
//            model.addAttribute("type", expense.get().getType());


        }
        return "expenses/edit";
    }

    @PostMapping("/expenses/update-expense")
    public String updateExpense(Expense changedExpense, Model model) {


            System.out.println(changedExpense.toString());
//            System.out.println(expense.get().toString());
        expenseRepository.save(changedExpense);

        return "redirect:/expenses";
    }
}


