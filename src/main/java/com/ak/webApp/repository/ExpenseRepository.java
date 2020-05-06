package com.ak.webApp.repository;

import com.ak.webApp.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT exp from Expense exp where exp.user.userId =:userId")
    List<Expense> findAllByUSerId(@Param("userId") int userId);
}
