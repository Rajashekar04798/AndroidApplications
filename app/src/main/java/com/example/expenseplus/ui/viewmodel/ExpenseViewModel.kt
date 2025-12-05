package com.example.expenseplus.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseplus.data.ExpenseDatabase
import com.example.expenseplus.ui.components.Expense
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    // This is what your UI will observe
    val expenses: SnapshotStateList<Expense> = mutableStateListOf()

    private val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()

    init {
        // Load from DB when ViewModel is created
        viewModelScope.launch {
            val listFromDb = expenseDao.getAllExpenses()
            expenses.clear()
            expenses.addAll(listFromDb)
        }
    }

    fun addExpense(expense: Expense) {
        expenses.add(expense)  // update UI immediately
        viewModelScope.launch {
            expenseDao.insertExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        expenses.remove(expense)
        viewModelScope.launch {
            expenseDao.deleteExpense(expense)
        }
    }

    fun editExpense(updatedExpense: Expense) {
        val index = expenses.indexOfFirst { it.id == updatedExpense.id }
        if (index != -1) {
            expenses[index] = updatedExpense
        }
        viewModelScope.launch {
            expenseDao.updateExpense(updatedExpense)
        }
    }
}
