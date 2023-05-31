package com.firstapi.api;

import com.firstapi.domain.Expense;
import com.firstapi.helper.NullAwareBeanUtilsBean;
import com.firstapi.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ExpensesController {

    private ExpenseRepository expenseRepository;
    private NullAwareBeanUtilsBean beanUtilsBean;

    public ExpensesController(ExpenseRepository expenseRepository, NullAwareBeanUtilsBean beanUtilsBean){
        this.expenseRepository = expenseRepository;
        this.beanUtilsBean = beanUtilsBean;
    }

    @GetMapping("/expenses")
    public List<Expense> findAll() {
        return StreamSupport.stream(expenseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/expenses/{id}")
    public Expense findById(@PathVariable("id") Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expenses")
    public Expense save(@Valid @RequestBody Expense expense){
        return expenseRepository.save(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense update(@Valid @RequestBody Expense expense, @PathVariable("id") Long id) {
        findById(id);
        expense.setId(id);

        return expenseRepository.save(expense);
    }

    @PatchMapping("/expenses/{id}")
    public Expense patchUpdate(@RequestBody Expense expense, @PathVariable("id") Long id)
            throws InvocationTargetException, IllegalAccessException {
        Expense existingExense = findById(id);

        beanUtilsBean.copyProperties(existingExense, expense);

        return expenseRepository.save(existingExense);
    }

    @DeleteMapping("/expenses/{id}")
    public void delete(@PathVariable("id") Long id){
        findById(id);
        expenseRepository.deleteById(id);
    }
}


